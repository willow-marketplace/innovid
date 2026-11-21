---
name: google-ads
description: Analyze and manage Google Ads through Windsor.ai. Use when the user asks about their Google Ads performance — pulling live campaign, ad group, or keyword metrics (cost, impressions, clicks, conversions, CPA, ROAS), comparing date ranges, or finding top or underperforming campaigns — or when they want to pause or enable a campaign or change a campaign budget. Do not use for other ad platforms (Meta/Facebook, TikTok, LinkedIn, Microsoft Ads), organic social, or GA4/website analytics.
---

# Google Ads via Windsor.ai

Windsor.ai exposes live Google Ads data (read) and a small set of campaign
management actions (write) through MCP tools. The connector id is
`google_ads`. Pass `google_ads` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Golden rules:

- **Never guess field, account, action, or option names.** They come from
  `get_fields`, `get_connectors`, `list_actions`, and `get_options`. Call those
  first; do not invent identifiers.
- **Reads are safe; writes change live ad spend.** Only call `execute_action`
  after the user has confirmed the specific change.
- Report numbers the user asked for, concisely. Don't dump raw rows unless asked.

## 1. Ground yourself first

Call `get_connectors` to see which Google Ads accounts are connected (each has
an id and, usually, a name). If none are connected, help the user connect one:
call `get_connector_authorization_url` (or `get_connector_connect_info` for the
auth type and steps) and give them the setup link — do not describe manual
dashboard navigation.

If the user names an account, match it to a connected account id. If several are
connected and the request is ambiguous, ask which account.

## 2. Reading performance data

1. Call `get_fields` for `google_ads` to get valid field ids (e.g. campaign,
   ad_group, date, cost/spend, impressions, clicks, conversions, conversion
   value — use the exact ids returned, not these examples).
2. If the analysis needs attribution or segmentation controls, call
   `get_options` to see the connector's options and valid values.
3. Call `get_data` with:
   - `fields`: the ids you actually need (keep the set small).
   - `accounts`: the target account id(s); omit for all connected accounts.
   - A date range: either `date_from`/`date_to` (`"2026-02-01"`), or
     `date_preset` — `last_7d`, `last_30d`, `last_Xd`/`last_Xw`/`last_Xm`,
     `this_month`, `this_year`, `last_year`. Append `T` to include today
     (e.g. `last_7dT`, `this_monthT`).
   - `options`: only if get_options showed relevant ones.
   - `filters`: optional conditions, e.g. `[["cost", "gt", 100]]`; combine with
     `"and"`/`"or"` and nest as needed.

Common requests → recipe:

- **Overview / trend:** fields `date` + the metrics, over a preset range.
- **By campaign:** fields `campaign` + metrics; sort/aggregate in your answer.
- **Best / worst campaign:** pull campaign + the metric, then rank; return only
  what was asked (e.g. just the campaign name) if the user constrained the
  output format.
- **Efficiency:** cost per conversion = cost ÷ conversions; ROAS = conversion
  value ÷ cost. Compute from returned fields; note if conversions are zero.

Watch out: Google Ads money fields are in the account currency (state it if
known). Conversions can be fractional. If a field the user wants isn't in
`get_fields`, say so rather than substituting a different metric.

## 3. Managing campaigns (write actions)

Google Ads exposes a broad set of write actions via `list_actions` /
`execute_action`. Always call `list_actions` first for the exact ids and JSON
schemas — treat the summary below as guidance, not gospel. They cover:

- **Create / structure:** `create_campaign` (Search or Display, with a daily
  budget; created paused), `create_ad_group`, `create_responsive_search_ad`
  (3–15 headlines, 2–4 descriptions, a final URL; created paused).
- **Status:** `pause_campaign` / `enable_campaign`, `pause_ad_group` /
  `enable_ad_group`, `pause_ad` / `enable_ad`.
- **Keywords:** `push_keywords` (add positive keywords to an ad group),
  `update_keywords`, `remove_keywords`, and `push_negative_keywords`
  (campaign- or ad-group-level).
- **Budget & bidding:** `set_campaign_budget` (`daily` or `lifetime`, with
  `apply_to_shared_budget` for shared budgets), `set_campaign_bidding_strategy`
  (manual CPC, target CPA/ROAS, maximize conversions/clicks, …),
  `set_target_cpa`, `set_target_roas`, `set_cpc_bid_ceiling`, `set_max_cpc`.
- **Customer Match audiences:** `create_customer_match_list`,
  `upload_customer_match_list` (async — poll `get_customer_match_upload_status`),
  `rename_customer_match_list`, `delete_customer_match_list`, and
  `attach_user_list_to_ad_group` / `detach_user_list_from_ad_group`. Upload
  members as raw email/phone/address — the action hashes them; matching runs in
  the background and the list isn't targetable until it completes.

Budget mechanics:

- Budgets, bids and target CPA are in **micros**: 1,000,000 micros = 1 unit
  of the account currency, so `50000000` = 50.00. Convert the user's amount
  to micros. Target ROAS is a ratio instead (`4.0` means 400%). Reported
  fields such as `spend`, `cost`, `cpc` and `cost_per_conversion` are already
  in currency units; only fields whose id ends in `_micros` are micros.
- `set_campaign_budget` / targets set an **absolute** value, not a delta. To
  "raise by 20%", read the current value first, compute the new absolute
  amount, and set that.
- `create_*` actions produce **paused** campaigns/ad groups/ads by default, so
  nothing serves until the user enables it.

Write workflow — every time:

1. Resolve the campaign to its `campaign_id` (via a `get_data` read on
   `campaign` + id fields if the user gave a name).
2. Build the exact params from the `list_actions` schema.
3. **Show the user the concrete change and ask them to confirm** — e.g.
   "Pause campaign 'Summer Sale' (id 123…) on account 456…?" or "Set daily
   budget for campaign X to €50.00 (50000000 micros)?".
4. Only after an explicit yes, call `execute_action` and report the result.

If the user says "pause the worst campaign", first do the read + ranking, then
name the specific campaign and confirm before pausing — don't pause on the
strength of the aggregate alone.

## 4. Reference the user's data, don't fabricate

If a tool returns an error (unknown field/connector/account), call the relevant
discovery tool (`get_fields`, `get_connectors`, `list_actions`) and retry with
correct ids. Never fill gaps with made-up numbers or campaign names.