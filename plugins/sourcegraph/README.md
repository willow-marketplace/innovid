# sourcegraph-claudecode-plugin

Sourcegraph plugin for Claude Code that adds:

- Sourcegraph MCP server connectivity (code search, navigation, Deep Search, Code Finder, evaluator)
- `searching-sourcegraph` skill for disciplined search workflows (with supporting workflows and query patterns)

## Prerequisites

- [Claude Code](https://code.claude.com) version 1.0.33 or later (`claude --version`)
- A Sourcegraph instance with MCP enabled
- A Sourcegraph access token with `mcp` scope

Set the required environment variables in your shell profile (`.zshrc`, `.bashrc`, etc.):

```bash
export SOURCEGRAPH_ENDPOINT="https://sourcegraph.example.com"
export SOURCEGRAPH_ACCESS_TOKEN="<your-token>"
```

## Install (permanent)

Add this repository as a marketplace, then install the plugin:

```bash
# From a local clone
claude plugin marketplace add ./sourcegraph-claudecode-plugin

# Or directly from GitHub
claude plugin marketplace add sourcegraph-community/sourcegraph-claudecode-plugin
```

Then install:

```bash
claude plugin install sourcegraph@sourcegraph-claudecode-plugin
```

Restart Claude Code after installing.

## Install (per-session, for development)

Load the plugin for a single session without installing permanently:

```bash
claude --plugin-dir ./sourcegraph-claudecode-plugin
```

Run `/reload-plugins` after making changes during a session.

## Alternative: add MCP server only

If you only want the Sourcegraph MCP tools without the skills, add the server directly:

```bash
claude mcp add --transport http sourcegraph \
  "${SOURCEGRAPH_ENDPOINT}/.api/mcp/all" \
  --header "Authorization: token ${SOURCEGRAPH_ACCESS_TOKEN}"
```

## Verify

After installing, confirm everything loaded:

- `/help` should list `sourcegraph:searching-sourcegraph`
- `/mcp` should show the `sourcegraph` MCP server as connected

## Structure

```text
.
├── .claude-plugin/
│   ├── plugin.json
│   └── marketplace.json
├── .mcp.json
├── skills/
│   └── searching-sourcegraph/
│       ├── SKILL.md
│       ├── query-patterns.md
│       ├── examples/
│       │   └── common-searches.md
│       └── workflows/
│           ├── implementing-feature.md
│           ├── understanding-code.md
│           ├── debugging-issue.md
│           ├── fixing-bug.md
│           └── code-review.md
└── README.md
```

## Skill

The plugin installs one skill: `searching-sourcegraph`. Claude auto-invokes it when you need to search or navigate code via Sourcegraph. It includes workflows for implementing features, understanding code, debugging issues, fixing bugs, and code review.

## MCP Details

- Endpoint: `${SOURCEGRAPH_ENDPOINT}/.api/mcp/all` (full tool suite: code search, navigation, Deep Search, Code Finder, evaluator)
- Transport: HTTP (`"type": "http"`)
- Auth header: `Authorization: token <token>`

Sourcegraph's MCP server exposes several endpoints depending on how many tools you want:

| Endpoint | Tools exposed |
|----------|----------------|
| `/.api/mcp` | Curated default set: `read_file`, `list_files`, `keyword_search`, `nls_search`, `list_repos`, `commit_search`, `diff_search`, `deepsearch_read` |
| `/.api/mcp/all` | Full suite — everything above, plus `go_to_definition`, `find_references`, `compare_revisions`, `get_contributor_repos`, `deepsearch`, `code_finder`, `evaluator` |
| `/.api/mcp/deepsearch` | Deep Search only: `deepsearch`, `deepsearch_read` |
| `/.api/mcp/v1` | **Deprecated.** Full suite under legacy `sg_`-prefixed tool names, kept for backward compatibility |

This plugin uses `/.api/mcp/all` because the `searching-sourcegraph` skill and its workflows rely on code navigation and Deep Search tools that aren't in the curated default set.

## Built from

- [sourcegraph-cursor-plugin](https://github.com/sourcegraph-community/sourcegraph-cursor-plugin)
- [sourcegraph-gemini](https://github.com/sourcegraph-community/sourcegraph-gemini)
- [sourcegraph-skill](https://github.com/sourcegraph-community/sourcegraph-skill)

## Docs

- [Claude Code plugins](https://code.claude.com/docs/en/plugins)
- [Sourcegraph MCP](https://sourcegraph.com/docs/api/mcp)
