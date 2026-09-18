package com.atlassian.twg.intellij;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.terminal.ui.TerminalWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.TerminalToolWindowManager;

public final class TwgSetupAction extends DumbAwareAction {
    private static final String TERMINAL_TITLE = "TWG Setup";
    private static final String CONSENT_MESSAGE =
            "TWG setup downloads the official CLI, installs its skills, and starts "
                    + "interactive browser authentication in a terminal.";

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }

        requestSetup(project);
    }

    static void requestSetup(@NotNull Project project) {
        int choice = Messages.showYesNoDialog(
                project,
                CONSENT_MESSAGE,
                "Set Up Teamwork Graph",
                "Continue",
                "Cancel",
                Messages.getWarningIcon());
        if (choice != Messages.YES) {
            return;
        }

        TerminalWidget terminal = TerminalToolWindowManager.getInstance(project)
                .createShellWidget(project.getBasePath(), TERMINAL_TITLE, true, true);
        TerminalCommandDispatcher.sendWhenReady(
                project, terminal, () -> terminal.sendCommandToExecute(InstallerCommand.forCurrentPlatform()));
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(event.getProject() != null);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
