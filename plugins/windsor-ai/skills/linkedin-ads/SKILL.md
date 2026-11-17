---
name: linkedin-ads
description: Analyze and manage LinkedIn Ads through Windsor.ai. Use when the user asks about their LinkedIn Ads performance — spend, impressions, clicks, CTR, CPC, conversions, or leads at campaign, campaign-group, or creative level, with member demographic breakdowns — or wants to pause/enable a campaign, campaign group, or creative, or change a campaign or campaign-group budget. Do not use for LinkedIn organic company-page data (that's the LinkedIn Organic connector), other ad platforms, or GA4/web analytics.
---

# LinkedIn Ads via Windsor.ai

Windsor.ai exposes live LinkedIn Ads data (read) and campaign-management actions
(write) through MCP tools. The connector id is `linkedin`. Pass `linkedin` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Golden rules:

- **Never guess field, account, action, or option names** — get them from
  `get_fields`, `get_connectors`, `list_actions`, and `get_options`. Call those
  first.
- **Reads are safe; writes change live ad spend.** Only call `execute_action`
  after the user confirms the specific change.
- Report the numbers the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which LinkedIn ad accounts are connected. If none is
connected, help the user connect: `get_connector_authorization_url` (or
`get_connector_connect_info`) and give them the setup link. If several accounts
are connected and the request is ambiguous, ask which one.

## 2. Reading performance data

1. Call `get_fields` for `linkedin` to get valid field ids — metrics (spend/cost,
   impressions, clicks, CTR, CPC, CPM, conversions, leads/form completions) and
   dimensions (campaign, campaign group, creative, date, plus `member_*`
   demographics like industry, job title, seniority, company size). Use the
   exact ids returned.
2. Call `get_data` with `fields`, the target `account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset`; add `date` to the fields for a
   daily breakdown, omit it for aggregated totals).

LinkedIn report specifics:

- **Report levels:** campaign, campaign group, and creative. Some fields require
  a specific level (get_fields/get_options indicate this). Including an ad/
  creative field returns creative-level data; otherwise it defaults to campaign
  level.
- **Demographic fields (`member_*`)** use their own pivot — request **one
  demographic breakdown at a time**, on its own, rather than combining several.
- The API allows up to ~20 metrics and up to 3 pivots per query; keep field sets
  focused.

## 3. Managing campaigns (write actions)

LinkedIn Ads exposes these actions via `list_actions` / `execute_action`. Always
call `list_actions` first to confirm the exact ids and schemas — treat the list
below as guidance:

- **`pause_campaign` / `enable_campaign`** — params: `campaign_id`.
- **`pause_campaign_group` / `enable_campaign_group`** — params:
  `campaign_group_id`.
- **`pause_creative` / `enable_creative`** — params: `creative_id`.
- **`set_campaign_budget`** — params: `campaign_id`, `budget_type`
  (`daily` or `total`), `amount`. A **total** budget requires the campaign to
  have a schedule end date.
- **`set_campaign_group_budget`** — params: `campaign_group_id`, `amount`
  (total budget only; the campaign group must have a schedule end date).
- Newer actions such as `rename_campaign`, `set_campaign_schedule`,
  `set_campaign_targeting`, and `create_creative` are also listed today; build
  their params only from the schema `list_actions` returns, and confirm first.

Budget mechanics:

- `amount` is in the **account currency major unit** — e.g. `100.00` means one
  hundred (not cents, not micros). The currency is resolved automatically from
  the account. `set_*_budget` sets an **absolute** value, not a delta: to raise
  by a percentage, read the current budget, compute the new absolute amount, and
  set that.

Write workflow — every time:

1. Resolve the campaign/campaign group/creative to its id (via a `get_data` read
   if the user gave a name).
2. Build the exact params from the `list_actions` schema.
3. **Show the user the concrete change and ask them to confirm** — e.g.
   "Pause campaign 'Q3 Leads' (id 123…)?" or "Set daily budget for campaign X to
   €100.00?".
4. Only after an explicit yes, call `execute_action` and report the result.

If the user says "pause the worst campaign", first do the read + ranking, then
name the specific campaign and confirm before pausing.

## 4. Reference real data, don't fabricate

On an error (unknown field/connector/account/action), call the relevant
discovery tool and retry with correct ids. Never invent spend, lead counts,
campaign names, or ids.