package com.atlassian.twg.intellij;

import com.intellij.openapi.util.SystemInfo;

final class InstallerCommand {
    private static final String INSTALLER_URL =
            "https://teamwork-graph.atlassian.com/cli/install";
    private static final String POWERSHELL_INSTALLER_PATH =
            "([IO.Path]::Combine([IO.Path]::GetTempPath(), 'twg-install.ps1'))";
    private static final int MINIMUM_POWERSHELL_INSTALLER_BYTES = 1024;

    private InstallerCommand() {}

    static String forCurrentPlatform() {
        return forPlatform(SystemInfo.isWindows);
    }

    static String forPlatform(boolean windows) {
        if (windows) {
            // IntelliJ uses the user's configured terminal shell, which is not
            // necessarily PowerShell even on Windows.
            return "powershell.exe -NoProfile -ExecutionPolicy Bypass -Command \""
                    + "Invoke-WebRequest -UseBasicParsing -Uri '"
                    + INSTALLER_URL
                    + ".ps1' -OutFile "
                    + POWERSHELL_INSTALLER_PATH
                    + " -ErrorAction Stop; if ((Get-Item -LiteralPath "
                    + POWERSHELL_INSTALLER_PATH
                    + ").Length -lt "
                    + MINIMUM_POWERSHELL_INSTALLER_BYTES
                    + ") { throw 'Downloaded TWG installer is unexpectedly small.' }; "
                    + "powershell.exe -NoProfile -ExecutionPolicy Bypass -File "
                    + POWERSHELL_INSTALLER_PATH
                    + " -Plugin intellij"
                    + "\"";
        }

        return "curl -fsSL --retry 2 "
                + INSTALLER_URL
                + " | bash -s -- --plugin intellij";
    }
}
