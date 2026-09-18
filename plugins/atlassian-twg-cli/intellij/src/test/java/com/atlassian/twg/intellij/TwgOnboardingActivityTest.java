package com.atlassian.twg.intellij;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class TwgOnboardingActivityTest {
    @Test
    void claimsAndNotifiesOnlyOnceAcrossRepeatedEligibleInvocations() {
        AtomicBoolean shown = new AtomicBoolean(false);
        AtomicInteger marked = new AtomicInteger();
        AtomicInteger notified = new AtomicInteger();
        TwgOnboardingActivity.PluginInstallationState installationState =
                new TwgOnboardingActivity.PluginInstallationState() {
                    @Override
                    public boolean wasInstalled() {
                        return true;
                    }

                    @Override
                    public boolean wasUpdated() {
                        return false;
                    }
                };
        TwgOnboardingActivity.OnboardingState onboardingState =
                new TwgOnboardingActivity.OnboardingState() {
                    @Override
                    public boolean wasShown() {
                        return shown.get();
                    }

                    @Override
                    public void markShown() {
                        shown.set(true);
                        marked.incrementAndGet();
                    }
                };

        TwgOnboardingActivity.showIfEligible(
                installationState, onboardingState, () -> false, notified::incrementAndGet);
        TwgOnboardingActivity.showIfEligible(
                installationState, onboardingState, () -> false, notified::incrementAndGet);

        assertEquals(1, marked.get());
        assertEquals(1, notified.get());
    }

    @Test
    void suppressesOnboardingForAPosixDefaultPathInstallOutsidePath() {
        Path homeDirectory = Path.of("/Users/test");

        assertDefaultPathInstallSuppressesOnboarding(
                false,
                homeDirectory,
                Optional.empty(),
                homeDirectory.resolve(".local").resolve("bin").resolve("twg"));
    }

    @Test
    void suppressesOnboardingForAWindowsDefaultPathInstallOutsidePath() {
        Path localAppDataDirectory = Path.of("/windows/local-app-data");

        assertDefaultPathInstallSuppressesOnboarding(
                true,
                Path.of("/unused-home"),
                Optional.of(localAppDataDirectory),
                localAppDataDirectory
                        .resolve("Programs")
                        .resolve("twg")
                        .resolve("bin")
                        .resolve("twg.exe"));
    }

    private static void assertDefaultPathInstallSuppressesOnboarding(
            boolean windows,
            Path homeDirectory,
            Optional<Path> localAppDataDirectory,
            Path installedBinary) {
        AtomicInteger marked = new AtomicInteger();
        AtomicInteger notified = new AtomicInteger();
        TwgOnboardingActivity.OnboardingState onboardingState =
                new TwgOnboardingActivity.OnboardingState() {
                    @Override
                    public boolean wasShown() {
                        return false;
                    }

                    @Override
                    public void markShown() {
                        marked.incrementAndGet();
                    }
                };

        TwgOnboardingActivity.showIfEligible(
                freshInstallation(),
                onboardingState,
                () -> TwgOnboardingActivity.isCliAvailable(
                        false,
                        windows,
                        homeDirectory,
                        localAppDataDirectory,
                        installedBinary::equals),
                notified::incrementAndGet);

        assertEquals(0, marked.get());
        assertEquals(0, notified.get());
    }

    private static TwgOnboardingActivity.PluginInstallationState freshInstallation() {
        return new TwgOnboardingActivity.PluginInstallationState() {
            @Override
            public boolean wasInstalled() {
                return true;
            }

            @Override
            public boolean wasUpdated() {
                return false;
            }
        };
    }
}
