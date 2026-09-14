# Atlassian Teamwork Graph CLI for OpenCode

This plugin targets stable OpenCode V1 and registers one custom tool, `twg_setup`, which returns the canonical TWG installation, authentication, repair, and verification guidance.

## Install

Add the npm package to the `plugin` array in `opencode.json`:

```json
{
  "$schema": "https://opencode.ai/config.json",
  "plugin": [
    "@atlassian/opencode-twg"
  ]
}
```

Restart OpenCode, then ask it to set up Teamwork Graph, install `twg`, or repair the TWG setup. OpenCode discovers `twg_setup` from the request; there is no slash command.

## Security and runtime boundary

The plugin returns generated guidance only. It does not install software, execute `twg`, call the Teamwork Graph API, collect credentials, implement OAuth, or run unattended login flows. The user must approve installation, and interactive setup or login runs in a controlling terminal owned by the existing TWG CLI.

The generated guidance tells the agent to verify the completed setup with `twg doctor`.

## Maintainer release

Create and push `opencode-v<package-version>` from an approved commit on `main`, then run the repository's `Publish OpenCode package` GitHub workflow from `main` with that tag as the `release_tag` input. The workflow validates and checks out the tagged commit before sending the package to Atlassian Artifactory's `npm-public` repository for controlled forwarding to npmjs. Do not publish directly to `registry.npmjs.org`.

Learn more: https://developer.atlassian.com/cloud/twg-cli/
