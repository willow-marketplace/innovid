---
name: tiktok-ads
description: Analyze and manage TikTok Ads through Windsor.ai. Use when the user asks about their TikTok Ads performance — pulling live campaign, ad group, or ad metrics (spend, impressions, clicks, conversions, CPA, ROAS), comparing date ranges, or finding top or underperforming campaigns — or when they want to pause or enable a campaign, ad group, or ad, or change a campaign or ad group budget. Do not use for TikTok organic (that's the TikTok Organic connector — no ad or cost data there), or other ad platforms (Meta/Facebook, Google, LinkedIn, Microsoft Ads).
---

# TikTok Ads via Windsor.ai

Windsor.ai exposes live TikTok Ads data (read) and a small set of campaign
management actions (write) through MCP tools. The connector id is `tiktok`.
Pass `tiktok` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Golden rules:

- **Never guess field, account, action, or option names.** They come from
  `get_fields`, `get_connectors`, `list_actions`, and `get_options`. Call those
  first; do not invent identifiers.
- **Reads are safe; writes change live ad spend.** Only call `execute_action`
  after the user has confirmed the specific change.
- Report numbers the user asked for, concisely. Don't dump raw rows unless asked.

## 1. Ground yourself first

Call `get_connectors` to see which TikTok Ads accounts are connected (each has
an id and, usually, a name). If none are connected, help the user connect one:
call `get_connector_authorization_url` (or `get_connector_connect_info` for the
auth type and steps) and give them the setup link — do not describe manual
dashboard navigation.

If the user names an account, match it to a connected account id. If several are
connected and the request is ambiguous, ask which account.

## 2. Reading performance data

1. Call `get_fields` for `tiktok` to get valid field ids (e.g. campaign,
   ad_group, ad, date, spend, impressions, clicks, conversions, ROAS, CPA — use
   the exact ids returned, not these examples).
2. If the analysis needs attribution or segmentation controls, call
   `get_options` to see the connector's options and valid values.
3. Call `get_data` with:
   - `fields`: the ids you actually need (keep the set small).
   - `accounts`: the target account id(s); omit for all connected accounts.
   - A date range: either `date_from`/`date_to` (`"2026-07-01"`), or
     `date_preset` — `last_7d`, `last_30d`, `last_Xd`/`last_Xw`/`last_Xm`,
     `this_month`, `this_year`, `last_year`. Append `T` to include today
     (e.g. `last_7dT`, `this_monthT`).
   - `options`: only if get_options showed relevant ones.
   - `filters`: optional conditions, e.g. `[["spend", "gt", 100]]`; combine with
     `"and"`/`"or"` and nest as needed.

Common requests → recipe:

- **Overview / trend:** fields `date` + the metrics, over a preset range.
- **By campaign:** fields `campaign` + metrics; sort/aggregate in your answer.
- **Best / worst campaign:** pull campaign + the metric, then rank; return only
  what was asked (e.g. just the campaign name) if the user constrained the
  output format.
- **Efficiency:** CPA = spend ÷ conversions; ROAS = conversion value ÷ spend.
  Compute from returned fields; note if conversions are zero.

Watch out: TikTok Ads money fields are in the account currency (state it if
known). If a field the user wants isn't in `get_fields`, say so rather than
substituting a different metric.

## 3. Managing campaigns (write actions)

TikTok Ads exposes a small set of write actions via `list_actions` /
`execute_action`. Always call `list_actions` first for the exact ids and JSON
schemas — treat the summary below as guidance, not gospel. They cover:

- **Status:** `pause_campaign` / `enable_campaign`, `pause_ad_group` /
  `enable_ad_group`, `pause_ad` / `enable_ad` — each reversible via its
  counterpart.
- **Budget:** `set_campaign_budget` (needs `campaign_id` and `amount`) and
  `set_ad_group_budget` (needs `ad_group_id` and `amount`). There is no
  ad-level budget action.

Budget mechanics:

- Amounts are in the **account currency major unit**, not a subunit — `100`
  means one hundred, not cents or micros.
- The budget type (daily, lifetime, or dynamic daily) is fixed when the
  campaign or ad group was created and is detected automatically; you only
  supply the new amount, not the type.
- `set_campaign_budget` and `set_ad_group_budget` set an **absolute** value,
  not a delta. To "raise by 20%", read the current budget first (via
  `get_data` or the platform), compute the new absolute amount, and set that.
- A campaign whose budget is unlimited (`BUDGET_MODE_INFINITE`) or managed at
  the ad group level has no campaign budget to set — use
  `set_ad_group_budget` instead, and vice versa for an ad group whose budget
  is managed at the campaign level (Campaign Budget Optimization). The action
  reports this rather than silently doing nothing.
- Lifetime budget changes take effect immediately. Daily and dynamic daily
  budget changes take effect from 00:00 the following day in the ad account's
  time zone — say so if the user expects an instant change.

Write workflow — every time:

1. Resolve the campaign/ad group/ad to its id (via a `get_data` read on the
   corresponding name and id fields if the user gave a name).
2. Build the exact params from the `list_actions` schema.
3. **Show the user the concrete change and ask them to confirm** — e.g.
   "Pause campaign 'Summer Sale' (id 123…) on account 456…?" or "Set daily
   budget for ad group X to €50.00?".
4. Only after an explicit yes, call `execute_action` and report the result.

If the user says "pause the worst campaign", first do the read + ranking, then
name the specific campaign and confirm before pausing — don't pause on the
strength of the aggregate alone.

## 4. Reference the user's data, don't fabricate

If a tool returns an error (unknown field/connector/account), call the relevant
discovery tool (`get_fields`, `get_connectors`, `list_actions`) and retry with
correct ids. Never fill gaps with made-up numbers or campaign names.