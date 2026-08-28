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
| **Reporting Analyst** | Performance questions, metrics, reports | Pulls your campaign, automation, and deal records and produces structured analysis grounded in your real data |
| **Campaign Strategist** | Campaign planning, email content, targeting | Guides campaign creation with audience selection, content strategy, and timing backed by your account data |
| **Automation Builder** | Workflow design, triggers, drip sequences | Recommends automation patterns and triggers; can enroll contacts and manage the tags that trigger workflows |
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
Contacts, tags, custom fields, campaigns, email activities, automations, lists, deals, pipelines, stages, deal activities, custom objects, and groups.

### Can change (with preview-and-confirm)
- **Contacts & data:** create/update contacts, create and apply tags, list membership, custom fields and values, bulk import contacts.
- **CRM / deals:** create and update deals, deal notes, bulk owner reassignment, create/update pipelines and stages, move deals between stages, create custom objects.
- **Automation membership:** enroll/remove contacts from automations, and manage the tags that trigger them.

Every change follows a **read → preview → confirm → execute → verify** flow, and each one also triggers Claude's own permission prompt — a deliberate second check before anything is written to your live account.

### Can't do
- **Create, edit, or send campaigns** — the plugin designs the campaign and preps the audience; you send it in the ActiveCampaign UI.
- **Create automations or edit automation steps** — the plugin designs the flow; you build it in ActiveCampaign's automation builder.
- **Compute aggregate metrics** (win rate, completion rate, average deal value, totals) — reports show the underlying records and counts; use ActiveCampaign's native reporting for true roll-ups.
- **Verify DNS / inbox placement** — the Deliverability Advisor guides SPF/DKIM/DMARC but can't read your DNS directly.

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

Reading your data is seamless. Anything that **changes** your account is always previewed for your confirmation first and gated by a permission prompt — so nothing is modified without your explicit go-ahead.

---

## Examples

- **Analyze:** "How are my recent campaigns performing?"
- **Report:** `/activecampaign:weekly-digest`
- **Plan:** "Build me a 30-day re-engagement plan for contacts who haven't opened in 90 days."
- **Organize contacts:** "Tag everyone who hasn't opened an email in 90 days as `cold`." → Claude shows you how many match and warns if it would trigger an automation, then applies it on your confirmation.
- **Build CRM:** "Set up a Partnerships pipeline with stages Outreach, Demo, Negotiation, Closed." → Claude previews the pipeline, then creates it on your confirmation and verifies the result.
- **Troubleshoot:** "Why are my emails going to spam?"

---

## Data Handling & Privacy

- **What the plugin is.** Configuration only — skills, commands, agents, and an MCP connection. It contains no code of its own and stores none of your data.
- **Where your data goes.** When you use the plugin, Claude calls ActiveCampaign's MCP server using your account URL and your authorization. Your ActiveCampaign data flows between your account and Claude to answer your requests, governed by ActiveCampaign's Terms of Service and Privacy Policy and Anthropic's terms for Claude.
- **Authentication.** Access is via OAuth against your own ActiveCampaign account. The plugin never asks for or stores API keys; the only setting it collects is your MCP Server URL.
- **Changes are gated.** Every write to your account is previewed for your confirmation and additionally gated by Claude's permission prompt.
- **Scope.** The plugin can only do what the MCP server exposes and what your account permissions allow. It cannot reach other accounts or data outside your ActiveCampaign account.

---

## Known Limitations

1. **Campaigns and automations are guided, not built** — the plugin can read them and design new ones, but creating/sending campaigns and building automations is done in the ActiveCampaign UI.
2. **No aggregate metrics** — reports present records and counts; use ActiveCampaign's native reporting for win rates, averages, and totals.
3. **Per-account URL** — you enter your subdomain URL during setup.
4. **No DNS verification** — the Deliverability Advisor guides authentication but can't read your DNS records directly.

---

## Support

Questions or issues: `amurrey@activecampaign.com`

ActiveCampaign — [activecampaign.com](https://www.activecampaign.com)
