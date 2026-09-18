# Chronograph LP — Claude plugin

Bring your trusted private capital portfolio data on Chronograph into Claude. This plugin adds
purpose-built skills for LPs (investors) that work directly against your permissioned
Chronograph data.

> **Who this is for:** Chronograph LP clients with an active account. The skills read your
> private Chronograph data through a secure OAuth connection, so a Chronograph login with
> LP-client access is required. If you're a GP client, use the
> [Chronograph GP plugin](https://github.com/chronograph-pe/chronograph-gp-claude-plugin) instead.

## Before you install: add the Chronograph connector

**This plugin does not connect to Chronograph on its own, and the skills will not work until
the connector is in place.** The plugin ships skills and sub-agents only; the connector is what
grants access to your data.

Connect it from Claude's connectors directory:

1. Open **Settings → Connectors** in Claude.
2. Find **Chronograph** in the list and click **Connect**.
3. Log in to your Chronograph account in the tab that opens.

## What's inside

| Skill | What it does |
|---|---|
| `chronograph-cashflow-forecast` | Forecast LP-level private capital cashflows for existing portfolios using Chronograph MCP data and a Takahashi-Alexander style model. |
| `chronograph-commitment-pacing-planner` | Plan forward fund commitments to reach and hold a target private capital allocation, using Chronograph portfolio data and a cashflow-projection model. |
| `chronograph-gp-meeting-prep` | Prepare an LP (investor) to meet with their fund manager (GP) — an LP-side skill where the user is the LP, not the GP. Review the fund's latest reporting, surface what changed since last period, and draft the questions worth raising. Draws on Chronograph fund and portfolio data when connected — captured reporting such as fund performance, schedules of investments, and portfolio company KPI profiles — and asks the LP to provide anything else it needs, such as a capital account statement, investor letter, or AGM or board deck. |
| `chronograph-look-through-exposure-scan` | Aggregate an LP's look-through portfolio exposure across funds — by company, sector, geography, vintage, strategy, and currency — and surface concentration, using the Chronograph top-exposures tool. |

Each skill ships with a matching sub-agent of the same name for running the task end-to-end,
plus a **chronograph-lp-analyst** orchestrator that chains the skills together for a full
LP portfolio workup.

## Requirements

- A **Chronograph account** connected as an **LP client**, with the **Chronograph connector**
  added in Claude (see above).
- A **paid Claude plan** (Pro, Max, Team, or Enterprise) — plugins aren't available on the free plan.
- **Claude Code** or **Cowork**. Plugins run in those two surfaces; they aren't available in
  standard claude.ai chat.

## Getting started

### Claude Code

```bash
# Add the Chronograph LP marketplace
claude plugin marketplace add chronograph-pe/chronograph-lp-claude-plugin

# Install the plugin
claude plugin install chronograph-lp@chronograph-lp
```

### Cowork

1. Open **Customize** in the left sidebar and select the **Plugins** tab.
2. Add the Chronograph LP marketplace, then click **Install** on the Chronograph LP plugin.

## Using the skills

- Type `/` and pick a Chronograph skill, or just describe what you need.
- The skill resolves the relevant funds or portfolios from your Chronograph data, applies the
  appropriate model or template, and returns the analysis or a finished artifact.
- Skills never fabricate figures: unavailable values are shown as `—`, and every result is
  labeled with its source, currency, and as-of date.

## Privacy

This plugin ships skills and sub-agents only. It bundles no server, no hooks, and no
telemetry, and it makes no network calls of its own. It collects, stores, and retains
nothing itself.

Your Chronograph data reaches Claude through the **Chronograph connector**, which you add
and authorize separately over OAuth. Skills read only the data your Chronograph login is
already permissioned to see.

- Chronograph privacy policy: <https://www.chronograph.pe/legal/privacy-policy/> (contact details are in the policy)

## How it works

```
.claude-plugin/plugin.json        Plugin manifest
.claude-plugin/marketplace.json   Marketplace catalog (single plugin)
skills/                           The skills, each with on-demand reference files where needed
agents/                           A matching sub-agent per skill, plus the chronograph-lp-analyst orchestrator
```
