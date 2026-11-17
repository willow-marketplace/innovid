---
name: tiktok-organic
description: Analyze TikTok account (organic) performance through Windsor.ai. Use when the user asks about their TikTok personal or business account's organic insights — video views, likes, comments, shares, followers, profile views, or audience breakdowns by country, age, gender, or active hour. Do not use for TikTok Ads (those live in the TikTok Ads connector), other platforms, or publishing videos.
---

# TikTok Organic via Windsor.ai

Windsor.ai pulls TikTok **organic** account insights from the TikTok Accounts
API (personal and business accounts). The connector id is `tiktok_organic`. Pass `tiktok_organic` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This connector is **read-only** organic analytics. It has **no ad or cost data**
(TikTok Ads live in the TikTok Ads connector, so route ad-spend questions there),
and it does **not** publish videos or change the account — there are no write
actions. If asked to post or change settings, say that's out of scope here.

Golden rules:

- **Never guess field, account, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- Read-only connector; there are no live write actions to run.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which TikTok accounts are connected (each has an id
and usually a username). If none is connected, help the user connect:
`get_connector_authorization_url` (or `get_connector_connect_info` for the auth
type and steps) and give them the setup link — don't describe manual dashboard
navigation. If several accounts are connected and the request is ambiguous, ask
which one.

## 2. Reading TikTok data

1. Call `get_fields` for `tiktok_organic` to get valid field ids — account and
   video metrics (video views, likes, comments, shares, followers, profile
   views, reach) and audience breakdowns (by country, age, gender, active hour).
   Use the exact ids returned, not these examples.
2. Call `get_data` with `fields`, the target `account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset` like `last_7d`, `last_30d`,
   `this_month`; append `T` to include today). Add `filters` as needed.

TikTok Organic specifics to respect:

- **Three kinds of fields behave differently:**
  - *Real-time / general* metrics (e.g. username, current follower count) are
    **not date-filterable** — TikTok returns the latest value, placed on a row
    with the current date. Don't present them as period-specific.
  - *Per-day* metrics (e.g. likes, video views) vary by date over the range.
  - *Breakdown* metrics (`audience_activity`, `audience_countries`,
    `audience_genders`, and the ages/cities breakdowns) each return on their
    **own row(s)** — request a breakdown on its own and read it separately
    rather than expecting it inline with per-day metrics.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one. There is no cost/spend data here.

## 3. Reference real data, don't fabricate

On an error (unknown field/connector/account), call the relevant discovery tool
and retry with correct ids, or report the specific error. Never invent view
counts, follower numbers, engagement figures, or video ids.