# ActiveCampaign Agent Plugin

Bring your ActiveCampaign marketing automation, CRM, and email marketing into your AI agent. This plugin adds ActiveCampaign domain expertise, guided workflows, intelligent reporting, and safe write operations on top of ActiveCampaign's MCP server, so your agent can analyze your account, plan your marketing, and make changes for you, all in plain conversation.

**Packaged in the open [Agent Plugins](https://agent-plugins.org/) format.** The full experience runs in Claude Code and Claude Co-Work; the skills and MCP connection are portable to any client that supports [Agent Skills](https://agentskills.io/) and MCP — including Codex and Cursor. See [Compatibility](#compatibility).

---

## Table of Contents

- [What's in the Plugin](#whats-in-the-plugin)
- [Compatibility](#compatibility)
- [What the Plugin Can and Can't Do](#what-the-plugin-can-and-cant-do)
- [Setup](#setup)
- [How It Works](#how-it-works)
- [Examples](#examples)
- [Data Handling & Privacy](#data-handling--privacy)
- [Known Limitations](#known-limitations)
- [Support](#support)

---

## What's in the Plugin

### Skills (used automatically)

Skills activate on their own when Claude detects relevant context — you don't need to invoke them. Just describe what you want.

| Skill | Helps with | What it does |
|-------|------------|--------------|
| **Reporting Analyst** | Performance questions, metrics, reports | Ranks and compares campaigns by opens, clicks, bounces, and unsubscribes over any date range, and reads automation and deal records, grounded in your real data |
| **Campaign Strategist** | Campaign planning, email content, targeting, sending | Plans the campaign from your account data, then generates it from a prompt or web page, imports an email you already have, edits or copies past sends, undoes changes, and sends or schedules after you confirm the summary |
| **Automation Builder** | Workflow design, triggers, drip sequences | Designs automations, drafts them for review or builds and saves them, edits existing ones, and enrolls contacts |
| **Contact Operations** | Segmentation, bulk updates, list/field management | Advises on organization strategy and executes contact/tag/list/field changes with a preview-and-confirm step |
| **Deals & CRM** | Pipelines, stages, deals, owners, custom objects | Builds and operates your CRM — creates pipelines, stages, and deals, moves deals, reassigns owners, models custom objects, all with preview-and-confirm |
| **Deliverability Advisor** | Spam issues, bounces, authentication | Diagnoses deliverability problems and provides SPF/DKIM/DMARC guidance |

### Commands (you trigger)

Type these directly. They produce read-only reports.

| Command | What it produces |
|---------|-----------------|
| `/activecampaign:campaign-report` | Performance report for your recent campaigns |
| `/activecampaign:weekly-digest` | Weekly summary across campaigns, automations, contacts, and deals |
| `/activecampaign:automation-audit` | Automation health read — entry/completion counts, likely drop-off, stale workflows |
| `/activecampaign:audience-health` | Contact database health — status counts, list sizes, engagement, data quality |
| `/activecampaign:deal-pipeline-review` | Pipeline structure, deals by stage, top deals by value, likely stuck deals |

### Agents (multi-step tasks)

| Agent | What it does |
|-------|-------------|
| **Marketing Planner** | Creates 30/60/90-day marketing plans grounded in your account data. Read-only — it produces the plan, it doesn't execute. |
| **Data Operations** | Runs bulk contact operations (mass tagging, list migration, field cleanup, bulk import) with safety previews and automation-trigger warnings. |

---

## Compatibility

This repository follows the [Agent Plugins specification v1.0.0](https://agent-plugins.org/) — an open, vendor-neutral format for packaging agent extensions.

| Component | Portability | Notes |
|-----------|-------------|-------|
| `plugin.json` | Portable ([Agent Plugins](https://agent-plugins.org/) manifest) | Identifies the plugin for any compliant client |
| `skills/` | Portable ([Agent Skills](https://agentskills.io/)) | The six skills work in any skills-capable client — Claude, Codex, Cursor, and others |
| `mcp.json` | Portable ([Agent Plugins](https://agent-plugins.org/) MCP config) | Connects compliant clients to ActiveCampaign's shared MCP endpoint; OAuth links it to your account |
| `commands/` | Claude Code only | Slash commands aren't part of the portable standard yet |
| `agents/` | Claude Code only | Subagents aren't part of the portable standard yet |
| `.claude-plugin/`, `.mcp.json` | Claude Code only | Claude Code's native plugin manifest and MCP config |

The portable `mcp.json` points at ActiveCampaign's shared MCP endpoint (`https://mcp.app-us1.com/http`); OAuth authorization links the connection to your account on first use. Claude Code uses its own `.mcp.json` and collects your account-specific MCP Server URL at install time. Clients that support Agent Skills but not the full plugin format can add the server manually (see [Setup](#setup)).

---

## What the Plugin Can and Can't Do

### Can read
Contacts, tags, custom fields, campaigns and their performance reports, email activities, automations, lists, deals, pipelines, stages, deal activities, custom objects, groups, and (for admins) your sending domain's DKIM and DMARC status.

### Can change (with a preview and your explicit yes)
- **Contacts & data:** create/update contacts, create and apply tags, list membership, custom fields and values, bulk import contacts.
- **CRM / deals:** create and update deals, deal notes, bulk owner reassignment, create/update pipelines and stages, move deals between stages, create custom objects.
- **Automations:** draft an automation for review, build and save one from a description, edit an existing one, and enroll or remove contacts.
- **Campaigns:** generate a campaign from a prompt or web page (brand is applied automatically), import an email you already have, edit copy or design, copy a past campaign with changes, undo a change, set the audience, and send now or schedule a one-time or A/B campaign. Sending always shows you a summary first and waits for your yes.

Every change follows a **read, preview, confirm, execute, verify** flow. Your client may also show its own permission prompt; that's an extra check, not a replacement for your yes.

### Can't do
- **Send RSS, recurring, or date-based campaigns**, verify a sender domain, or lift contact limits. Those stay in ActiveCampaign.
- **Compute automation or deal aggregates** (automation-wide completion rate, win rate, average deal value, totals). Reports show the records and counts; use ActiveCampaign's native reporting for those roll-ups. Campaign rates and rankings, and per-goal automation rates, are available.
- **See inbox placement.** The Deliverability Advisor checks authentication and campaign metrics, not where mail lands.

Which tools are available can vary by client and account permissions; the skills only use tools the connected server lists.

---

## Setup

### Claude Code / Claude Co-Work

1. **Install** the ActiveCampaign plugin.
2. **Enter your ActiveCampaign MCP Server URL** when prompted. Find it in your ActiveCampaign account under **Settings > Developer > MCP**. It looks like `https://yoursubdomain.activehosted.com/api/agents/mcp/http`.
3. **Authorize** Claude to access your ActiveCampaign account (OAuth).
4. **Start using it** — ask a question, run a command, or ask Claude to set something up.

### Agent Plugins-compatible clients

If your client supports the [Agent Plugins](https://agent-plugins.org/) format, install the plugin and you're set: the bundled `mcp.json` connects to ActiveCampaign's shared MCP endpoint (`https://mcp.app-us1.com/http`), and OAuth links it to your account on first use.

### Codex, Cursor, and other Agent Skills clients

If your client supports skills but not the full plugin format, the portable pieces are the six skills and the MCP connection:

1. **Install the skills.** Each folder under `skills/` is a self-contained [Agent Skill](https://agentskills.io/). Copy the folders into your client's skills directory (for example `~/.codex/skills/` for Codex — check your client's documentation for the exact location).
2. **Connect the MCP server** through your client's MCP configuration, using the shared endpoint. For example, in Cursor's `mcp.json`:

   ```json
   {
     "mcpServers": {
       "activecampaign": {
         "url": "https://mcp.app-us1.com/http"
       }
     }
   }
   ```

   Or in Codex's `~/.codex/config.toml`:

   ```toml
   [mcp_servers.activecampaign]
   url = "https://mcp.app-us1.com/http"
   ```

   Syntax varies by client and version — see your client's MCP documentation. Your account-specific MCP URL (**Settings > Developer > MCP** in ActiveCampaign) works here too.
3. **Authorize** access (OAuth) when your client first connects to the server.

> The `/activecampaign:*` commands and the two agents are Claude Code features and won't appear in other clients; the skills carry the core expertise everywhere.

---

## How It Works

The plugin connects your agent to ActiveCampaign through ActiveCampaign's MCP server. From there:

- **Skills** add ActiveCampaign expertise so the agent uses the right data in the right order, with marketing best practices built in.
- **Commands** run pre-built reporting workflows (Claude Code).
- **Agents** handle multi-step tasks (Claude Code).

Reading your data is seamless. Anything that **changes** your account is previewed first and waits for your explicit yes, so nothing is modified without your go-ahead. Text inside your account data (contact notes, imported files, email HTML) is treated as data and can never authorize a change on its own.

---

## Examples

- **Analyze:** "How are my recent campaigns performing?"
- **Report:** `/activecampaign:weekly-digest`
- **Plan:** "Build me a 30-day re-engagement plan for contacts who haven't opened in 90 days."
- **Organize contacts:** "Tag everyone on the Webinar list as `event:webinar-2026`." → Claude shows you how many match and warns if it would trigger an automation, then applies it on your confirmation.
- **Create and send:** "Write a campaign announcing our new pricing page, link: …" → Claude asks about the image, generates the draft, and when you're ready, shows the send summary and sends only after your yes.
- **Build CRM:** "Set up a Partnerships pipeline with stages Outreach, Demo, Negotiation, Closed." → Claude previews the pipeline, then creates it on your confirmation and verifies the result.
- **Troubleshoot:** "Why are my emails going to spam?"

---

## Data Handling & Privacy

- **What the plugin is.** Configuration only: skills, commands, agents, and an MCP connection. It stores none of your data. The `scripts/` folder holds maintainer tooling (consistency checks and the ChatGPT export); the plugin never runs it.
- **Where your data goes.** When you use the plugin, Claude calls ActiveCampaign's MCP server using your account URL and your authorization. Your ActiveCampaign data flows between your account and Claude to answer your requests, governed by ActiveCampaign's Terms of Service and Privacy Policy and Anthropic's terms for Claude.
- **Authentication.** Access is via OAuth against your own ActiveCampaign account. The plugin never asks for or stores API keys; the only setting it collects is your MCP Server URL.
- **Changes are gated.** Every write to your account is previewed and waits for your explicit yes. Your client's own permission prompt, if it shows one, is an additional check.
- **Scope.** The plugin can only do what the MCP server exposes and what your account permissions allow. It cannot reach other accounts or data outside your ActiveCampaign account.

---

## Known Limitations

1. **Some sends stay in ActiveCampaign.** RSS, recurring, and date-based campaigns, sender domain verification, and contact limits are handled in the ActiveCampaign UI.
2. **No automation or deal aggregates.** Campaign rates and rankings and automation goal rates are available; automation-wide completion rates, win rates, averages, and totals come from ActiveCampaign's native reporting.
3. **Per-account URL.** You enter your subdomain URL during setup in Claude Code.
4. **Authentication checks are admin-only.** Non-admin users get guidance instead of a live DKIM/DMARC check.

## Maintaining the plugin

- `shared/safety.md` holds the safety rules every skill shares. Edit it there, then run `scripts/sync-shared.sh` to copy it into each skill.
- `scripts/check-tool-refs.sh` fails if a skill, agent, or command names a tool the server doesn't define (`tools/server-tools.txt`). Refresh that list with `scripts/refresh-tool-snapshot.sh <path-to-ac-mcp-server>`.
- `scripts/export-openai.sh <previous-chatgpt-package.zip> <version> [release-notes.txt]` builds the ChatGPT package in `dist/`, keeping the existing listing's identity.
- CI runs the first two checks on every pull request.

---

## Support

Questions or issues: `amurrey@activecampaign.com`

ActiveCampaign — [activecampaign.com](https://www.activecampaign.com)
