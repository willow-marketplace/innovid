# Vertical plugins

A vertical is a smaller plugin for one of Windsor.ai's use-case MCP endpoints
(for example `https://mcp.windsor.ai/social_media`). It ships a subset of the
skills, commands and agents maintained in this repository, plus a few files of
its own, and lives in its own GitHub repository because the Claude directory
lists one plugin per repository folder.

Nothing in a vertical repository is edited by hand. The shared files are
copied from here, so a correction lands in every vertical on the next build.

## Layout

```
verticals/<vertical>/
  vertical.json        name, description, keywords, MCP endpoint, which shared
                       skills, commands and agents to include, text replacements
  README.md            the vertical's README, copied as is
  LISTING.md           the Claude directory listing copy for its connector
  skills/<name>/       skills that exist only in this vertical
```

`replacements` in `vertical.json` map hand-offs that only make sense in the
main plugin (for example `/windsor-connect`) to their counterpart in the
vertical. The tool-name prefix of the agent `disallowedTools` lists is rewritten
automatically.

## Build

```bash
git clone git@github.com:windsor-ai/claude-windsor-social-media-plugin.git ../claude-windsor-social-media-plugin
python3 scripts/build_vertical.py social-media --output ../claude-windsor-social-media-plugin
claude plugin validate ../claude-windsor-social-media-plugin
```

Then review the diff in the vertical repository and commit it there. The build
fails when a copied file still names the main server, still uses the main
plugin's tool prefix, or refers to a skill or command that the vertical does
not ship.

## Verticals

| Vertical | Endpoint | Repository |
|----------|----------|------------|
| `social-media` | `https://mcp.windsor.ai/social_media` | https://github.com/windsor-ai/claude-windsor-social-media-plugin |
