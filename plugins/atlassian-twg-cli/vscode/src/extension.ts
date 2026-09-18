import * as vscode from "vscode";
import { refreshTwgCliAvailability } from "./cli-state";
import { installerCommandFor } from "./installer";

const SETUP_COMMAND = "twg.setup";
const REFRESH_CLI_STATUS_COMMAND = "twg.refreshCliStatus";
const CONTINUE = "Continue";

export function activate(context: vscode.ExtensionContext): void {
  const refreshCliAvailability = () =>
    refreshTwgCliAvailability((key, available) =>
      vscode.commands.executeCommand("setContext", key, available)
    );

  context.subscriptions.push(
    vscode.commands.registerCommand(SETUP_COMMAND, async () => {
      const choice = await vscode.window.showWarningMessage(
        "TWG setup downloads the official CLI, installs its skills, and starts interactive browser authentication in a terminal.",
        { modal: true },
        CONTINUE
      );

      if (choice !== CONTINUE) {
        return;
      }

      // The Windows installer uses PowerShell syntax, while a user's default
      // terminal may be Command Prompt, Git Bash, or WSL.
      const terminal = vscode.window.createTerminal(
        process.platform === "win32"
          ? { name: "TWG Setup", shellPath: "powershell.exe" }
          : { name: "TWG Setup" }
      );
      terminal.show();
      terminal.sendText(installerCommandFor(process.platform), true);
    }),
    vscode.commands.registerCommand(REFRESH_CLI_STATUS_COMMAND, refreshCliAvailability)
  );

  void refreshCliAvailability();
}

export function deactivate(): void {
  // The terminal stays available after extension deactivation so users can finish setup.
}
