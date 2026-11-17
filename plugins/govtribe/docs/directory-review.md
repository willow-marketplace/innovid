# Directory review notes

The plugin declares one remote MCP connection to GovTribe. Users authorize it
through their Claude client's OAuth flow. It has no install hooks or local MCP
server and does not read credentials from the user's environment.

From 1.2.1 the plugin folder contains only Markdown, JSON, YAML and text files
plus two PNG images. It ships no scripts, Office files or other binaries. The
template guides in `govtribe-file-templates` describe document structure in
Markdown; Claude creates files with its own tools.

The pinned Claude CLI commands in the GitHub Actions workflow validate the
repository in CI; they are not plugin installation or runtime commands.

The manifest declares the GovExec privacy policy, the support page and an existing
1000-pixel GovTribe PNG icon. Directory listing fields are ignored by Claude Code at
runtime.

See [Anthropic's pre-submission checklist](https://claude.com/docs/plugins/pre-submission-checklist)
and [directory manifest fields](https://code.claude.com/docs/en/plugins-reference#directory-listing-fields).
