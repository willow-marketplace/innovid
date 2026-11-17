---
name: facebook-organic
description: Analyze Facebook Page (organic) performance through Windsor.ai. Use when the user asks about their Facebook Page's organic insights — reach, impressions, engagement, reactions, follows/fans, or the performance of individual posts, videos, and Reels, by date/week/month or by country. It can also publish a photo post or a text and link post to a Page, always after the user approves the full draft. Do not use for Facebook Ads (those live in the Meta/Facebook Ads connector), boosting posts, Instagram, or other platforms.
---

# Facebook Organic (Pages) via Windsor.ai

Windsor.ai pulls Facebook **Page organic** insights from the Facebook Graph API.
The connector id is `facebook_organic`. Pass `facebook_organic` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Reads are the main job. The connector also exposes write actions, currently
`create_post` and `create_photo_post` (the live set is whatever `list_actions`
returns). It has **no ad or cost data** (Facebook Ads live in the Meta/Facebook
Ads connector, so route ad-spend and boost requests there), it does not cover
Instagram, and it does not change Page settings; say those are out of scope
here.

Golden rules:

- **Never guess field, page, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- **Publishing is public and immediate.** Show the full post text (and the
  photo URL) and call `execute_action` only after an explicit yes.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which Facebook Pages are connected (each has an id
and usually a name). If none is connected, help the user connect:
`get_connector_authorization_url` (or `get_connector_connect_info` for the auth
type and steps) and give them the setup link — don't describe manual dashboard
navigation. If several Pages are connected and the request is ambiguous, ask
which one.

## 2. Reading Page insights

1. Call `get_fields` for `facebook_organic` to get valid field ids. These span
   Page-level metrics (reach, impressions, engaged users, follows/fans, video
   and Reels plays) and post-level fields (reactions, comments, shares, clicks,
   post reach/impressions). Use the exact ids returned, not these examples.
2. Call `get_data` with `fields`, the target Page/`account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset` like `last_7d`, `last_30d`,
   `this_month`; append `T` to include today). Add `filters` as needed.

Facebook Organic specifics to respect:

- **One broken-down (segmented) metric per request.** The connector can't return
  multiple broken-down metrics in one call — request them separately and combine
  in your answer rather than asking for several at once.
- **Weekly/monthly aggregation.** Requesting a week field (`week_iso`/
  `year_week_iso`, or `week`/`year_week`) returns one row per week; a month field
  (`month`/`year_month`) one row per month. Facebook **deduplicates** unique
  metrics (reach, unique impressions) over the period, so use the week/month
  field for weekly/monthly totals — do **not** sum daily rows, which over-counts
  uniques.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one. There is no cost/spend data here.

## 3. Publishing a post

1. Call `get_connectors` to pick the Page; ask if several are connected.
2. Call `list_actions` for `facebook_organic` and read the schema of
   `create_post` or `create_photo_post`; a photo post needs a publicly
   reachable image URL.
3. Draft the post as text first and show it in full, exactly as it will
   appear, then wait for an explicit yes. Never publish placeholder text.
4. Call `execute_action` once and report the result from the tool, including
   any post id or URL it returns.
5. If the action is refused because write actions are not enabled for the
   account, send the user to https://onboard.windsor.ai/app/settings/account
   and retry after they confirm.

## 4. Reference real data, don't fabricate

On an error (unknown field/connector/page), call the relevant discovery tool and
retry with correct ids, or report the specific error. Never invent reach,
engagement, follower counts, post ids, or post text.