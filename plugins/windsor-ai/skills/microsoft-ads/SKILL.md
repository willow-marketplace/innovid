---
name: microsoft-ads
description: Analyze and manage Microsoft Advertising (Bing Ads) through Windsor.ai. Use when the user asks about their Microsoft Ads performance — pulling live campaign, ad group, keyword, or ad metrics (spend, impressions, clicks, CTR, CPC, conversions, CPA, ROAS), comparing date ranges, or finding top or underperforming campaigns or ad groups — or when they want to pause or enable a campaign or ad group, or change a campaign's daily budget. Do not use for other ad platforms (Google Ads, Meta/Facebook Ads, TikTok Ads, LinkedIn Ads), organic Bing or web search rankings, or website analytics.
---

# Microsoft Ads (Bing) via Windsor.ai

Windsor.ai exposes live Microsoft Advertising (Bing Ads) data (read) and a
small set of campaign and ad group management actions (write) through MCP
tools. The connector id is `bing`. Pass `bing` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Golden rules:

- **Never guess field, account, action, or option names.** They come from
  `get_fields`, `get_connectors`, `list_actions`, and `get_options`. Call those
  first; do not invent identifiers.
- **Reads are safe; writes change live campaign/ad group status or budget.**
  Only call `execute_action` after the user has confirmed the specific change.
- Report numbers the user asked for, concisely. Don't dump raw rows unless asked.

## 1. Ground yourself first

Call `get_connectors` to see which Microsoft Ads accounts are connected (each
has an id and, usually, a name). If none are connected, help the user connect
one: call `get_connector_authorization_url` (or `get_connector_connect_info`
for the auth type and steps) and give them the setup link — do not describe
manual dashboard navigation.

If the user names an account, match it to a connected account id. If several
are connected and the request is ambiguous, ask which account.

## 2. Reading Microsoft Ads data

1. Call `get_fields` for `bing` to get valid field ids (e.g. campaign,
   ad_group, keyword, ad, date, spend/cost, impressions, clicks, CTR, CPC,
   conversions, CPA, ROAS — use the exact ids returned, not these examples).
2. If the analysis needs segmentation or attribution controls, call
   `get_options` to see the connector's options and valid values.
3. Call `get_data` with:
   - `fields`: the ids you actually need (keep the set small).
   - `accounts`: the target account id(s); omit for all connected accounts.
   - A date range: either `date_from`/`date_to` (`"2026-07-01"`), or
     `date_preset` — `last_7d`, `last_30d`, `last_Xd`/`last_Xw`/`last_Xm`,
     `this_month`, `this_year`, `last_year`. Append `T` to include today
     (e.g. `last_7dT`, `this_monthT`).
   - `options`: only if `get_options` showed relevant ones.
   - `filters`: optional conditions, e.g. `[["cost", "gt", 100]]`; combine with
     `"and"`/`"or"` and nest as needed.

Common requests → recipe:

- **Overview / trend:** fields `date` + the metrics, over a preset range.
- **By campaign or ad group:** fields `campaign`/`ad_group` + metrics;
  sort/aggregate in your answer.
- **Best / worst campaign or ad group:** pull the entity + the metric, then
  rank; return only what was asked (e.g. just the name) if the user
  constrained the output format.
- **Efficiency:** CPA = cost ÷ conversions; ROAS = conversion value ÷ cost.
  Compute from returned fields; note if conversions are zero.

Watch out: Microsoft Ads money fields are in the account currency (state it if
known). If a field the user wants isn't in `get_fields`, say so rather than
substituting a different metric.

## 3. Managing campaigns and ad groups (write actions)

Microsoft Ads exposes a small set of write actions via `list_actions` /
`execute_action`. Always call `list_actions` first for the exact ids and JSON
schemas — treat the summary below as guidance, not gospel. They cover:

- **Status:** `pause_campaign` / `enable_campaign` (identify the campaign by
  exactly one of `campaign_id` or `campaign_name`), and `pause_ad_group` /
  `enable_ad_group` (identify the ad group by exactly one of `ad_group_id` or
  `ad_group_name`, plus the parent campaign by exactly one of `campaign_id` or
  `campaign_name` — required because ad group names are unique only within a
  campaign).
- **Budget:** `set_campaign_budget` sets a campaign's **daily** budget in the
  account currency **major unit** (e.g. `50.00` means 50.00 USD on a USD
  account — not cents, not micros). Microsoft Ads only supports daily budgets
  at the campaign level; there is no lifetime or ad-group-level budget action.
  If the campaign uses a shared budget, the action is **rejected** unless
  `apply_to_shared_budget` is set to `true`, because the change would also
  apply to every other campaign sharing that budget.

There are no campaign or ad group **creation** actions, and no keyword or
ad-level write actions — keyword and ad metrics are readable via `get_data`,
but writes here cover only campaign/ad group status and campaign budgets.

Action names accept an id or a name directly (the action resolves
`campaign_name` / `ad_group_name` at execution time), so a separate lookup
call usually isn't needed — but if the target is ambiguous (e.g. "the
underperforming ad group"), read and rank first, then confirm the specific
one.

Budget mechanics:

- Amounts are in the account currency **major unit**, not cents or micros.
- `set_campaign_budget` sets an **absolute** value, not a delta. To "raise by
  20%", read the current daily budget first (via `get_data`), compute the new
  absolute amount, and set that.
- Before setting a budget, be ready to check the shared-budget response: if
  `execute_action` reports sibling campaigns sharing the budget, tell the user
  which other campaigns would be affected and get their explicit go-ahead
  before retrying with `apply_to_shared_budget=true`.

Write workflow — every time:

1. Identify the target campaign (and ad group, if applicable) by id or name.
2. Build the exact params from the `list_actions` schema.
3. **Show the user the concrete change and ask them to confirm** — e.g.
   "Pause ad group 'Brand Terms' in campaign 'Q3 Sale'?" or "Set campaign
   'Q3 Sale' daily budget to $75.00?".
4. Only after an explicit yes, call `execute_action` and report the result,
   including any shared-budget side effects it reports.

If the user says "pause the worst-performing ad group", first do the read +
ranking, then name the specific ad group and confirm before pausing — don't
act on the strength of the aggregate alone.

## 4. Reference the user's data, don't fabricate

If a tool returns an error (unknown field/connector/account), call the
relevant discovery tool (`get_fields`, `get_connectors`, `list_actions`) and
retry with correct ids. Never fill gaps with made-up numbers, campaign names,
or ad group names.