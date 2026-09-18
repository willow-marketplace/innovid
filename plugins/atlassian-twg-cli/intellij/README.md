# Atlassian Teamwork Graph CLI for JetBrains IDEs

Bring your Atlassian work context into JetBrains IDEs with Teamwork Graph CLI.
It gives you access to Jira issues, Confluence pages, Bitbucket pull requests,
and other connected work data from your terminal and coding agents.

After a fresh installation, the plugin shows a one-time setup notification when
Teamwork Graph CLI is not yet available.

## Set up Teamwork Graph

1. Open **Find Action** (`Cmd+Shift+A` on macOS or `Ctrl+Shift+A` on Windows
   and Linux), or open the **Tools** menu.
2. Select **TWG: Set Up**.
3. Review the confirmation, then select **Continue**.
4. Complete the installation and sign-in steps in the **TWG Setup** terminal.

## What Teamwork Graph CLI does

Teamwork Graph CLI is Atlassian's agent-first interface to your entire work
context. Use it from your terminal or coding agent to:

- Search Jira, Confluence, JSM, Assets, Bitbucket, goals, projects, and
  connected data.
- Map ownership, experts, dependencies, and related work.
- Summarize status, triage reviews, and prepare handoffs.
- Create and update work without losing the context behind it.

For example, ask your coding agent: “What PRs are waiting on me, and which
reviews are stale?”

## What the plugin does

- Opens a visible, interactive terminal and sends the official Teamwork Graph
  CLI installer command.
- Explicitly invokes PowerShell on Windows and uses the normal terminal shell
  on macOS and Linux.
- Leaves installation, OAuth sign-in, credential storage, skills, and health
  checks to Teamwork Graph CLI.

The plugin never collects credentials or runs a background installer.

## Requirements

- A JetBrains IDE based on IntelliJ Platform 2024.1 or later with the bundled
  Terminal plugin enabled.
- An internet connection to download and authenticate Teamwork Graph CLI.

The action uses the IDE backend's terminal, so it also supports remote
development when the plugin is installed on the remote host.

Learn more about [Teamwork Graph CLI](https://developer.atlassian.com/cloud/twg-cli/).

## Development

Build and test the plugin:

```shell
./gradlew test verifyPlugin buildPlugin
```

Start an IntelliJ development instance with the plugin installed:

```shell
./gradlew runIde
```

The installable ZIP is written to `build/distributions/`.

## Release signing

Release signing uses an Atlassian-owned certificate and private key supplied
through secured environment variables. Never commit signing credentials to the
repository.

Set the following variables to the PEM content or its single-line Base64
encoding:

- `CERTIFICATE_CHAIN`: X.509 certificate chain.
- `PRIVATE_KEY`: private key in PEM format.
- `PRIVATE_KEY_PASSWORD`: password used to decrypt the private key.

Build, sign, and verify the release archive:

```shell
./gradlew clean test verifyPlugin signPlugin verifyPluginSignature
```

The signed ZIP is written to `build/distributions/` with a `-signed` suffix.
The `publishPlugin` task will select this signed archive automatically when
signing credentials and Marketplace publishing configuration are present.

For the first manual Marketplace submission:

- Select Atlassian's organisation Vendor profile.
- Upload the signed ZIP, not the unsigned development archive.
- Select an Atlassian-approved proprietary Developer EULA. Do not select an
  open-source license or provide a public source-code URL.
- Use `support@atlassian.com` as the vendor/support email in the Marketplace listing.
- Choose the appropriate Marketplace tags and release channel.

See the [JetBrains plugin-signing documentation](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html)
for certificate creation, CI encoding, and signature verification details.
