package com.atlassian.twg.intellij;

import com.intellij.execution.configurations.PathEnvironmentVariableUtil;
import com.intellij.ide.plugins.InstalledPluginsState;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.SystemInfo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class TwgOnboardingActivity implements StartupActivity.DumbAware {
    private static final PluginId PLUGIN_ID = PluginId.getId("com.atlassian.twg.cli");
    private static final String NOTIFICATION_GROUP_ID = "Teamwork Graph setup";
    private static final String ONBOARDING_SHOWN_KEY = "com.atlassian.twg.onboarding.shown";

    @Override
    public void runActivity(@NotNull Project project) {
        showIfEligible(project);
    }

    static void showIfEligible(@NotNull Project project) {
        InstalledPluginsState installationState = InstalledPluginsState.getInstance();
        PropertiesComponent properties = PropertiesComponent.getInstance();
        showIfEligible(
                new PluginInstallationState() {
                    @Override
                    public boolean wasInstalled() {
                        return installationState.wasInstalled(PLUGIN_ID);
                    }

                    @Override
                    public boolean wasUpdated() {
                        return installationState.wasUpdated(PLUGIN_ID);
                    }
                },
                new OnboardingState() {
                    @Override
                    public boolean wasShown() {
                        return properties.getBoolean(ONBOARDING_SHOWN_KEY, false);
                    }

                    @Override
                    public void markShown() {
                        properties.setValue(ONBOARDING_SHOWN_KEY, true);
                    }
                },
                () -> isCliAvailable(
                        PathEnvironmentVariableUtil.findExecutableInPathOnAnyOS("twg") != null,
                        SystemInfo.isWindows,
                        Path.of(System.getProperty("user.home")),
                        Optional.ofNullable(System.getenv("LOCALAPPDATA"))
                                .filter(value -> !value.isBlank())
                                .map(Path::of),
                        candidate -> Files.isRegularFile(candidate)
                                && (SystemInfo.isWindows || Files.isExecutable(candidate))),
                () -> notifyOnboarding(project));
    }

    static void showIfEligible(
            @NotNull PluginInstallationState installationState,
            @NotNull OnboardingState onboardingState,
            @NotNull CliAvailability cliAvailability,
            @NotNull Runnable notifier) {
        synchronized (TwgOnboardingActivity.class) {
            if (!shouldShowOnboarding(
                    installationState.wasInstalled(),
                    installationState.wasUpdated(),
                    onboardingState.wasShown(),
                    cliAvailability.isAvailable())) {
                return;
            }
            onboardingState.markShown();
        }

        notifier.run();
    }

    static boolean isCliAvailable(
            boolean foundOnPath,
            boolean windows,
            @NotNull Path homeDirectory,
            @NotNull Optional<Path> localAppDataDirectory,
            @NotNull Predicate<Path> isInstalledBinary) {
        if (foundOnPath) {
            return true;
        }

        Optional<Path> defaultBinary = windows
                ? localAppDataDirectory.map(directory ->
                        directory.resolve("Programs").resolve("twg").resolve("bin").resolve("twg.exe"))
                : Optional.of(homeDirectory.resolve(".local").resolve("bin").resolve("twg"));
        return defaultBinary.filter(isInstalledBinary).isPresent();
    }

    static boolean shouldShowOnboarding(
            boolean freshlyInstalled, boolean updated, boolean alreadyShown, boolean cliAvailable) {
        return freshlyInstalled && !updated && !alreadyShown && !cliAvailable;
    }

    private static void notifyOnboarding(@NotNull Project project) {
        new Notification(
                        NOTIFICATION_GROUP_ID,
                        "Set up Teamwork Graph CLI",
                        "Bring Jira, Confluence, Bitbucket, and connected work context into your terminal and coding agents.",
                        NotificationType.INFORMATION)
                .addAction(NotificationAction.createSimpleExpiring(
                        "Set up Teamwork Graph CLI", () -> TwgSetupAction.requestSetup(project)))
                .notify(project);
    }

    interface PluginInstallationState {
        boolean wasInstalled();

        boolean wasUpdated();
    }

    interface OnboardingState {
        boolean wasShown();

        void markShown();
    }

    @FunctionalInterface
    interface CliAvailability {
        boolean isAvailable();
    }
}
