package com.atlassian.twg.intellij;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class InstallerCommandTest {
    @Test
    void downloadsThenRunsPowerShellInstallerAsFileOnWindows() {
        assertEquals(
                "powershell.exe -NoProfile -ExecutionPolicy Bypass -Command \""
                        + "Invoke-WebRequest -UseBasicParsing -Uri "
                        + "'https://teamwork-graph.atlassian.com/cli/install.ps1' "
                        + "-OutFile ([IO.Path]::Combine([IO.Path]::GetTempPath(), "
                        + "'twg-install.ps1')) -ErrorAction Stop; if ((Get-Item -LiteralPath "
                        + "([IO.Path]::Combine([IO.Path]::GetTempPath(), 'twg-install.ps1'))).Length "
                        + "-lt 1024) { throw 'Downloaded TWG installer is unexpectedly small.' }; "
                        + "powershell.exe -NoProfile -ExecutionPolicy Bypass -File "
                        + "([IO.Path]::Combine([IO.Path]::GetTempPath(), 'twg-install.ps1')) "
                        + "-Plugin intellij\"",
                InstallerCommand.forPlatform(true));
    }

    @Test
    void usesThePosixInstallerOnMacOsAndLinux() {
        assertEquals(
                "curl -fsSL --retry 2 https://teamwork-graph.atlassian.com/cli/install"
                        + " | bash -s -- --plugin intellij",
                InstallerCommand.forPlatform(false));
    }
}
