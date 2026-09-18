import assert from "node:assert/strict";
import { existsSync, readFileSync } from "node:fs";
import { resolve } from "node:path";
import test from "node:test";

interface WalkthroughStep {
  id: string;
  description: string;
  media: { markdown: string };
  completionEvents: string[];
  when?: string;
}

interface ExtensionManifest {
  activationEvents: string[];
  contributes: {
    commands: Array<{ command: string }>;
    walkthroughs: Array<{
      id: string;
      steps: WalkthroughStep[];
    }>;
  };
}

const extensionRoot = resolve(__dirname, "../..");
const manifest = JSON.parse(
  readFileSync(resolve(extensionRoot, "package.json"), "utf8")
) as ExtensionManifest;

test("contributes an install-time Teamwork Graph walkthrough", () => {
  assert.deepEqual(manifest.activationEvents, ["onWalkthrough:twg.getStarted"]);
  assert.equal(
    manifest.contributes.commands.some((command) => command.command === "twg.refreshCliStatus"),
    true
  );
  assert.equal(manifest.contributes.walkthroughs.length, 1);

  const walkthrough = manifest.contributes.walkthroughs[0];
  assert.equal(walkthrough.id, "twg.getStarted");

  const missingStep = walkthrough.steps.find((step) => step.id === "twg.setupMissing");
  assert.ok(missingStep);
  assert.equal(missingStep.when, "!twg.cliAvailable");
  assert.match(missingStep.description, /\(command:twg\.setup\)/);
  assert.match(missingStep.description, /\(command:twg\.refreshCliStatus\)/);
  assert.deepEqual(missingStep.completionEvents, ["onContext:twg.cliAvailable"]);

  const detectedStep = walkthrough.steps.find((step) => step.id === "twg.setupDetected");
  assert.ok(detectedStep);
  assert.equal(detectedStep.when, "twg.cliAvailable");
  assert.deepEqual(detectedStep.completionEvents, ["onContext:twg.cliAvailable"]);

  const exploreStep = walkthrough.steps.find((step) => step.id === "twg.explore");
  assert.ok(exploreStep);
  assert.match(exploreStep.description, /developer\.atlassian\.com\/cloud\/twg-cli/);

  for (const step of walkthrough.steps) {
    assert.equal(existsSync(resolve(extensionRoot, step.media.markdown)), true);
  }
});
