package com.atlassian.twg.intellij;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.terminal.JBTerminalWidget;
import com.intellij.terminal.ui.TerminalWidget;
import com.intellij.terminal.ui.TtyConnectorAccessor;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.jediterm.terminal.model.TerminalModelListener;
import com.jediterm.terminal.model.TerminalTextBuffer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

final class TerminalCommandDispatcher {
    private static final long OUTPUT_QUIET_PERIOD_MILLIS = 500;
    private static final long MAXIMUM_WAIT_MILLIS = 15_000;

    private TerminalCommandDispatcher() {}

    static void sendWhenReady(Project project, TerminalWidget terminal, Runnable sendCommand) {
        ScheduledExecutorService scheduler = AppExecutorUtil.getAppScheduledExecutorService();
        Runnable dispatch =
                () -> ApplicationManager.getApplication().invokeLater(sendCommand, project.getDisposed());
        JBTerminalWidget terminalWidget = JBTerminalWidget.asJediTermWidget(terminal);
        if (terminalWidget == null) {
            sendAfterConnectorDelay(
                    terminal.getTtyConnectorAccessor(), scheduler, OUTPUT_QUIET_PERIOD_MILLIS, dispatch);
            return;
        }

        TerminalTextBuffer textBuffer = terminalWidget.getTerminalTextBuffer();
        OutputEvents outputEvents = new OutputEvents() {
            @Override
            public Runnable addListener(Runnable listener) {
                TerminalModelListener adapter = listener::run;
                textBuffer.addModelListener(adapter);
                return () -> textBuffer.removeModelListener(adapter);
            }

            @Override
            public boolean hasOutput() {
                textBuffer.lock();
                try {
                    return !textBuffer.getScreenLines().isBlank();
                } finally {
                    textBuffer.unlock();
                }
            }
        };

        sendWhenReady(
                terminal.getTtyConnectorAccessor(),
                outputEvents,
                scheduler,
                OUTPUT_QUIET_PERIOD_MILLIS,
                MAXIMUM_WAIT_MILLIS,
                dispatch);
    }

    static void sendAfterConnectorDelay(
            TtyConnectorAccessor connectorAccessor,
            ScheduledExecutorService scheduler,
            long settleDelayMillis,
            Runnable sendCommand) {
        connectorAccessor.executeWithTtyConnector(ignored ->
                scheduler.schedule(sendCommand, settleDelayMillis, TimeUnit.MILLISECONDS));
    }

    static void sendWhenReady(
            TtyConnectorAccessor connectorAccessor,
            OutputEvents outputEvents,
            ScheduledExecutorService scheduler,
            long quietPeriodMillis,
            long maximumWaitMillis,
            Runnable sendCommand) {
        AtomicBoolean sent = new AtomicBoolean();
        AtomicReference<ScheduledFuture<?>> pending = new AtomicReference<>();
        AtomicReference<Runnable> removeOutputListener = new AtomicReference<>(() -> {});
        Runnable[] outputListener = new Runnable[1];

        java.util.function.LongConsumer scheduleSend = delayMillis -> {
            ScheduledFuture<?> previous = pending.getAndSet(scheduler.schedule(
                    () -> {
                        if (sent.compareAndSet(false, true)) {
                            removeOutputListener.get().run();
                            sendCommand.run();
                        }
                    },
                    delayMillis,
                    TimeUnit.MILLISECONDS));
            if (previous != null) {
                previous.cancel(false);
            }
        };
        outputListener[0] = () -> scheduleSend.accept(quietPeriodMillis);

        connectorAccessor.executeWithTtyConnector(ignored -> {
            removeOutputListener.set(outputEvents.addListener(outputListener[0]));
            if (outputEvents.hasOutput()) {
                scheduleSend.accept(quietPeriodMillis);
            } else {
                // A normal shell renders a prompt. Keep a bounded fallback for
                // unusual prompt configurations that produce no visible text.
                scheduleSend.accept(maximumWaitMillis);
            }
        });
    }

    interface OutputEvents {
        Runnable addListener(Runnable listener);

        boolean hasOutput();
    }
}
