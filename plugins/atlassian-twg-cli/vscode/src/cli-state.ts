import { execFile } from "node:child_process";
import { homedir } from "node:os";
import path from "node:path";

export const CLI_AVAILABLE_CONTEXT = "twg.cliAvailable";

interface ProbeOptions {
  timeout: number;
  windowsHide: boolean;
}

export interface CliProbeEnvironment {
  platform: NodeJS.Platform;
  homeDir: string;
  localAppData?: string;
}

export type CliProbeRunner = (
  command: string,
  args: readonly string[],
  options: ProbeOptions,
  callback: (error: Error | null) => void
) => void;

export type CliAvailabilityContextSetter = (
  key: string,
  available: boolean
) => unknown | PromiseLike<unknown>;

const defaultProbeRunner: CliProbeRunner = (command, args, options, callback) => {
  execFile(command, args, options, (error) => callback(error));
};

const defaultProbeEnvironment: CliProbeEnvironment = {
  platform: process.platform,
  homeDir: homedir(),
  localAppData: process.env.LOCALAPPDATA,
};

function cliProbeCommands(environment: CliProbeEnvironment): string[] {
  const commands = ["twg"];

  if (environment.platform === "win32") {
    if (environment.localAppData) {
      commands.push(path.win32.join(environment.localAppData, "Programs", "twg", "bin", "twg.exe"));
    }
  } else {
    commands.push(path.join(environment.homeDir, ".local", "bin", "twg"));
  }

  return commands;
}

function probeCommand(command: string, run: CliProbeRunner): Promise<boolean> {
  return new Promise((resolve) => {
    run(command, ["--version"], { timeout: 2_000, windowsHide: true }, (error) => {
      resolve(error === null);
    });
  });
}

/** Probe only for the local executable; do not trigger auth or network checks. */
export async function isTwgCliAvailable(
  run: CliProbeRunner = defaultProbeRunner,
  environment: CliProbeEnvironment = defaultProbeEnvironment
): Promise<boolean> {
  const results = await Promise.all(
    cliProbeCommands(environment).map((command) => probeCommand(command, run))
  );
  return results.some(Boolean);
}

export async function refreshTwgCliAvailability(
  setContext: CliAvailabilityContextSetter,
  run: CliProbeRunner = defaultProbeRunner,
  environment: CliProbeEnvironment = defaultProbeEnvironment
): Promise<boolean> {
  const available = await isTwgCliAvailable(run, environment);
  await setContext(CLI_AVAILABLE_CONTEXT, available);
  return available;
}
