import assert from "node:assert/strict";
import test from "node:test";
import {
  CLI_AVAILABLE_CONTEXT,
  isTwgCliAvailable,
  refreshTwgCliAvailability,
  type CliProbeEnvironment,
  type CliProbeRunner,
} from "../cli-state";

const linuxEnvironment: CliProbeEnvironment = {
  platform: "linux",
  homeDir: "/Users/test",
};

test("reports the CLI as available when the version probe succeeds", async () => {
  const commands: string[] = [];
  const run: CliProbeRunner = (command, args, options, callback) => {
    commands.push(command);
    assert.deepEqual(args, ["--version"]);
    assert.deepEqual(options, { timeout: 2_000, windowsHide: true });
    callback(null);
  };

  assert.equal(await isTwgCliAvailable(run, linuxEnvironment), true);
  assert.deepEqual(commands, ["twg", "/Users/test/.local/bin/twg"]);
});

test("reports the CLI as unavailable when the version probe fails", async () => {
  const run: CliProbeRunner = (_command, _args, _options, callback) => {
    callback(new Error("not found"));
  };

  assert.equal(await isTwgCliAvailable(run, linuxEnvironment), false);
});

test("refreshes the walkthrough after a default-path install outside the original PATH", async () => {
  const commands: string[] = [];
  const run: CliProbeRunner = (command, _args, _options, callback) => {
    commands.push(command);
    callback(command === "/Users/test/.local/bin/twg" ? null : new Error("not found"));
  };
  const contextUpdates: Array<[string, boolean]> = [];

  const available = await refreshTwgCliAvailability(
    (key, value) => {
      contextUpdates.push([key, value]);
    },
    run,
    linuxEnvironment
  );

  assert.equal(available, true);
  assert.deepEqual(commands, ["twg", "/Users/test/.local/bin/twg"]);
  assert.deepEqual(contextUpdates, [[CLI_AVAILABLE_CONTEXT, true]]);
});

test("finds a default Windows install outside the original PATH", async () => {
  const commands: string[] = [];
  const run: CliProbeRunner = (command, _args, _options, callback) => {
    commands.push(command);
    callback(
      command === "C:\\Users\\test\\AppData\\Local\\Programs\\twg\\bin\\twg.exe"
        ? null
        : new Error("not found")
    );
  };

  assert.equal(
    await isTwgCliAvailable(run, {
      platform: "win32",
      homeDir: "C:\\Users\\test",
      localAppData: "C:\\Users\\test\\AppData\\Local",
    }),
    true
  );

  assert.deepEqual(commands, [
    "twg",
    "C:\\Users\\test\\AppData\\Local\\Programs\\twg\\bin\\twg.exe",
  ]);
});
