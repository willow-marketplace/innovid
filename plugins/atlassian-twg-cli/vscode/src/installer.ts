const INSTALLER_URL = "https://teamwork-graph.atlassian.com/cli/install";
const WINDOWS_INSTALLER_URL = `${INSTALLER_URL}.ps1`;
const MINIMUM_WINDOWS_INSTALLER_BYTES = 1024;

/**
 * Return a command intended to be visibly executed in VS Code's shell-backed
 * terminal. The CLI installer owns every subsequent interactive step.
 */
export function installerCommandFor(platform: NodeJS.Platform): string {
  if (platform === "win32") {
    return `$installer=Join-Path ([IO.Path]::GetTempPath()) 'twg-install.ps1'; irm ${WINDOWS_INSTALLER_URL} -OutFile $installer -ErrorAction Stop; if (-not (Test-Path -LiteralPath $installer) -or (Get-Item -LiteralPath $installer).Length -lt ${MINIMUM_WINDOWS_INSTALLER_BYTES}) { throw 'Downloaded TWG installer is unexpectedly small.' }; powershell.exe -NoProfile -ExecutionPolicy Bypass -File $installer -Plugin vscode`;
  }

  return `curl -fsSL --retry 2 ${INSTALLER_URL} | bash -s -- --plugin vscode`;
}
