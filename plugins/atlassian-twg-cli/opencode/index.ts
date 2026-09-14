import type { Plugin } from "@opencode-ai/plugin";
import { setupGuidance } from "./generated/setup-guidance.ts";

export const TwgPlugin: Plugin = async () => {
  return {
    tool: {
      twg_setup: {
        description: "Install, authenticate, repair, or verify the Teamwork Graph CLI (twg).",
        args: {},
        async execute() {
          return setupGuidance;
        },
      },
    },
  };
};
