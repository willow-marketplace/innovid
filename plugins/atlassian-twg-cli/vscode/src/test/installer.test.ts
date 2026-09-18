import assert from "node:assert/strict";
import test from "node:test";
import { installerCommandFor } from "../installer";

test("uses the PowerShell installer on Windows", () => {
  assert.equal(
    installerCommandFor("win32"),
    "$installer=Join-Path ([IO.Path]::GetTempPath()) 'twg-install.ps1'; irm https://teamwork-graph.atlassian.com/cli/install.ps1 -OutFile $installer -ErrorAction Stop; if (-not (Test-Path -LiteralPath $installer) -or (Get-Item -LiteralPath $installer).Length -lt 1024) { throw 'Downloaded TWG installer is unexpectedly small.' }; powershell.exe -NoProfile -ExecutionPolicy Bypass -File $installer -Plugin vscode"
  );
});

test("uses the POSIX installer on macOS and Linux", () => {
  const expected =
    "curl -fsSL --retry 2 https://teamwork-graph.atlassian.com/cli/install | bash -s -- --plugin vscode";

  assert.equal(installerCommandFor("darwin"), expected);
  assert.equal(installerCommandFor("linux"), expected);
});
