package com.atlassian.twg.intellij;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intellij.terminal.ui.TtyConnectorAccessor;
import com.jediterm.terminal.TtyConnector;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class TerminalCommandDispatcherTest {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @AfterEach
    void stopScheduler() {
        scheduler.shutdownNow();
    }

    @Test
    void waitsForTheTerminalConnectorAndSettledOutputBeforeSending() throws Exception {
        TtyConnectorAccessor connectorAccessor = new TtyConnectorAccessor();
        TestOutputEvents outputEvents = new TestOutputEvents();
        AtomicInteger sends = new AtomicInteger();
        CountDownLatch sent = new CountDownLatch(1);

        TerminalCommandDispatcher.sendWhenReady(
                connectorAccessor,
                outputEvents,
                scheduler,
                40,
                500,
                () -> {
                    sends.incrementAndGet();
                    sent.countDown();
                });
        assertEquals(0, sends.get());

        connectorAccessor.setTtyConnector(terminalConnector());
        outputEvents.emit();
        assertFalse(sent.await(20, TimeUnit.MILLISECONDS));
        outputEvents.emit();

        assertTrue(sent.await(1, TimeUnit.SECONDS));
        assertEquals(1, sends.get());
        assertEquals(0, outputEvents.listenerCount());
    }

    @Test
    void waitsForTheConnectorAndBoundedSettleDelayWithoutOutputEvents() throws Exception {
        TtyConnectorAccessor connectorAccessor = new TtyConnectorAccessor();
        AtomicInteger sends = new AtomicInteger();
        CountDownLatch sent = new CountDownLatch(1);

        TerminalCommandDispatcher.sendAfterConnectorDelay(connectorAccessor, scheduler, 40, () -> {
            sends.incrementAndGet();
            sent.countDown();
        });
        assertEquals(0, sends.get());

        connectorAccessor.setTtyConnector(terminalConnector());
        assertFalse(sent.await(20, TimeUnit.MILLISECONDS));
        assertTrue(sent.await(1, TimeUnit.SECONDS));
        assertEquals(1, sends.get());
    }

    @Test
    void sendsOnceAfterTheBoundedFallbackWhenThePromptHasNoVisibleOutput() throws Exception {
        TtyConnectorAccessor connectorAccessor = new TtyConnectorAccessor();
        connectorAccessor.setTtyConnector(terminalConnector());
        TestOutputEvents outputEvents = new TestOutputEvents();
        AtomicInteger sends = new AtomicInteger();
        CountDownLatch sent = new CountDownLatch(1);

        TerminalCommandDispatcher.sendWhenReady(
                connectorAccessor,
                outputEvents,
                scheduler,
                10,
                20,
                () -> {
                    sends.incrementAndGet();
                    sent.countDown();
                });

        assertTrue(sent.await(1, TimeUnit.SECONDS));
        outputEvents.emit();

        assertEquals(1, sends.get());
    }

    private static TtyConnector terminalConnector() {
        return (TtyConnector) Proxy.newProxyInstance(
                TtyConnector.class.getClassLoader(),
                new Class<?>[] {TtyConnector.class},
                (proxy, method, args) -> null);
    }

    private static final class TestOutputEvents implements TerminalCommandDispatcher.OutputEvents {
        private final List<Runnable> listeners = new ArrayList<>();

        @Override
        public synchronized Runnable addListener(Runnable listener) {
            listeners.add(listener);
            return () -> remove(listener);
        }

        @Override
        public boolean hasOutput() {
            return false;
        }

        void emit() {
            List<Runnable> current;
            synchronized (this) {
                current = List.copyOf(listeners);
            }
            current.forEach(Runnable::run);
        }

        synchronized int listenerCount() {
            return listeners.size();
        }

        private synchronized void remove(Runnable listener) {
            listeners.remove(listener);
        }
    }
}
