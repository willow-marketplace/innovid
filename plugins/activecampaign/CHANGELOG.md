# Changelog

All notable changes to the ActiveCampaign plugin are documented here.
This project follows [Semantic Versioning](https://semver.org/).

## [Unreleased]

### Added
- **Send and schedule campaigns.** Your agent can check a one-time or A/B campaign is ready, show you exactly who it goes to and what it says, and send or schedule it after your yes. It can also import an email you already have, undo a change for free, set the audience, and tell you whether a send was actually delivered.
- **Build and edit automations.** Describe a workflow and your agent can draft it for review (nothing saved) or build and save it, then edit it later. It reports emails that are still being written instead of calling the automation done.
- **Campaign rankings and rates.** Reports now use ActiveCampaign's campaign performance report, so "which campaigns did best last quarter" and bounce or unsubscribe trends get real numbers.
- **Automation goal rates.** Reports and `/automation-audit` show each automation goal's completion rate and average time to complete.
- **Domain authentication checks.** For admins, the Deliverability Advisor checks DKIM and DMARC for your From domain directly.
- **Shared safety rules** in every skill: explicit yes before any change, account content treated as data (a note or imported file can't trigger a change), and no repeat sends or imports when a result is unclear.
- **ChatGPT export** (`scripts/export-openai.sh`) that builds the ChatGPT package from these skills while keeping the existing listing.

### Changed
- **Campaign creation** follows the current tools: brand is applied automatically, images come from your Content Manager by name, and page links go straight to the generator.
- **Large contact changes** go through one bulk import instead of contact-by-contact batches. The preview now names the automations an import could start and offers to skip them.
- **Untagging is no longer offered.** No tool removes tags, so the plugin says so instead of promising it.
- **Contacts without an email** are supported when your account allows phone-only contacts.
- **Custom objects** ask whether the schema should be private or public, and never fall back to public if private fails.

### Fixed
- `/campaign-report` used a campaign status that doesn't exist ("sent"); it now reads the performance report directly.
- Deal values are treated as cents, and deals in pipelines you can't access are labeled instead of counted as empty.
- Skills no longer claim that reads are pre-approved or that a client's permission prompt counts as your consent. Their `allowed-tools` lists, which never matched the plugin's tool names, are removed.
- Removed references to retired tools (`list_brand_kits`, `search_content_manager_images`, `list_custom_object_schemas`, `create_custom_object_schema`).

## [0.3.2] — Cursor support and campaign creation

### Added
- **Cursor support.** The plugin now installs as a full plugin in Cursor, with the ActiveCampaign skills and the MCP connection included. Sign in with your ActiveCampaign login on first use; no account URL to look up.
- **ActiveCampaign icon** so marketplace listings show the official ActiveCampaign mark.

### Fixed
- **Cursor now connects to ActiveCampaign on install.** Previously Cursor loaded the skills but not the MCP connection, so nothing worked until a server URL was added by hand. Installing now prompts you to sign in to ActiveCampaign and the connection is live after that.

### Changed
- **Campaign creation.** Your agent can now draft a new email campaign from a prompt, using your brand kit and images, rewrite the copy or tone of an existing campaign, copy a past campaign as a new draft, and change the subject line, preheader, or sender. Drafts land in your ActiveCampaign account for review. Sending and scheduling still happen in ActiveCampaign.

## [0.3.1] — claude.ai loader compatibility

### Fixed
- `.mcp.json` server type corrected from `"url"` to `"http"` (streamable HTTP), the valid remote type in the MCP config format. `"url"` is the Messages API connector format and was rejected by claude.ai's plugin loader; Claude Code tolerated it. Same endpoint, same transport — no server or install behavior changes.

## [0.3.0] — Agent Plugins standard adoption

### Added
- **Portable `plugin.json` manifest** at the repo root, conforming to the open [Agent Plugins specification v1.0.0](https://agent-plugins.org/), so the plugin is recognizable by any compliant client — not just Claude.
- **Portable `mcp.json`** at the repo root (Agent Plugins v1.0.0 MCP config) connecting compliant clients to ActiveCampaign's shared MCP endpoint, `https://mcp.app-us1.com/http`, via streamable HTTP; OAuth links the connection to your account on first use.
- `license` and `compatibility` frontmatter on all six skills per the [Agent Skills specification](https://agentskills.io/), flagging the ActiveCampaign MCP server requirement for clients installing the skills standalone.
- README: compatibility matrix (portable vs. Claude Code-specific components) and setup instructions for Codex, Cursor, and other Agent Skills / MCP clients.

### Changed
- README reframed from Claude-only to the cross-client Agent Plugins format.

### Notes
- Claude Code behavior is unchanged — `.claude-plugin/plugin.json`, `.mcp.json` (per-account URL via `userConfig`), commands, agents, and skills all work exactly as in 0.2.0. The portable `mcp.json` is used by Agent Plugins-compatible clients; Agent Skills-only clients configure the MCP server manually (see README Setup).

## [0.2.0] — launch preparation

### Added
- **Deals & CRM skill** — create/update deals, build and reorganize pipelines and stages, move deals between stages, bulk-reassign owners, manage deal notes, and model data with custom objects. All writes use a preview → confirm → execute → verify contract.
- **Write capabilities** across Contact Operations and the Data Operations agent, aligned to the production server's actual write tools (contacts, tags, lists, fields, bulk import, deals/CRM, custom objects).
- `LICENSE` (MIT), `CHANGELOG.md`, support contact, and a data-handling note (see README).
- Extracted into a **standalone single-plugin repo** (`amurrey/activecampaign-plugin`) with the plugin promoted to the repo root, in preparation for `claude-community` submission.

### Changed
- Reports (`/campaign-report`, `/weekly-digest`, `/automation-audit`, `/audience-health`, `/deal-pipeline-review`) and the Reporting Analyst skill now comply with the server's no-aggregate rules: they present per-record counts and top-N sorted records instead of computing win rate, completion rate, averages, totals, or conversion. They point to AC's native reporting for true roll-ups.
- Skill `allowed-tools` now pre-approve each skill's **read** tools (smooth UX) while leaving **write** tools to the native permission prompt (safety gate).
- README rewritten to match the real server (60 tools), the actual file layout, the true capability boundaries, and Anthropic-directory distribution.
- Author identity set to ActiveCampaign (official); licensed under MIT.

### Fixed
- Corrected nonexistent tool names `list_pipelines` / `list_stages` to the real `list_deal_pipelines` / `list_deal_stages` across the marketing-planner agent, weekly-digest and deal-pipeline-review commands, and the reporting-analyst skill.
- Removed references to nonexistent config files (`.mcp.production.json`, `.mcp.dev.json`) and an incorrect install path from the README.

## [0.1.0] — initial internal version
- First version: 5 skills, 5 commands, 2 agents, MCP connection via `userConfig` URL.
