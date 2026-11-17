---
name: linkedin-pages
description: Analyze LinkedIn company page (organic) performance through Windsor.ai. Use when the user asks about their LinkedIn page's organic insights — follower growth, impressions, reach, clicks, reactions, comments, shares, engagement rate, or visitor demographics by industry, job function, seniority, or company size. It can also publish a text or image post to the company page, always after the user approves the full draft. Do not use for LinkedIn Ads (those live in the LinkedIn Ads connector) or other platforms.
---

# LinkedIn Pages via Windsor.ai

Windsor.ai pulls LinkedIn **organic** company page insights (page and post
metrics, plus visitor demographics). The connector id is `linkedin_organic`.
Pass `linkedin_organic` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Reads are the main job. The connector also exposes write actions, currently
`create_post` and `create_image_post` (the live set is whatever `list_actions`
returns). It has **no ad or cost data** (LinkedIn Ads live in the LinkedIn Ads
connector, so route ad-spend questions there) and does not change page
settings; say that is out of scope here.

Golden rules:

- **Never guess field, account, or option names** — get them from
  `get_fields`, `get_connectors`, and `get_options`. Call those first.
- **Publishing is public and immediate.** Show the full post text (and the
  image URL) and call `execute_action` only after an explicit yes.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which LinkedIn Pages are connected (each has an
id and usually a page name). If none is connected, help the user connect:
`get_connector_authorization_url` (or `get_connector_connect_info` for the
auth type and steps) and give them the setup link — don't describe manual
dashboard navigation. If several pages are connected and the request is
ambiguous, ask which one.

## 2. Reading LinkedIn Pages data

1. Call `get_fields` for `linkedin_organic` to get valid field ids — page and
   post metrics (follower growth, impressions, reach, clicks, reactions,
   comments, shares, engagement rate) and visitor demographic breakdowns (by
   industry, job function, seniority, and company size). Use the exact ids
   returned, not these examples.
2. Call `get_data` with `fields`, the target `account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset` like `last_7d`, `last_30d`,
   `this_month`; append `T` to include today). Add `filters` as needed.

LinkedIn Pages specifics to respect:

- **Page-level metrics** (e.g. follower counts) and **post-level metrics**
  (e.g. per-post reactions, comments, shares) live at different granularity —
  don't mix them into one row unless the fields returned support it.
- **Demographic breakdowns** (industry, job function, seniority, company
  size) describe page visitors or followers over the period, not individual
  posts — request them on their own rather than expecting them inline with
  post-level engagement fields.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one. There is no cost/spend data here.

## 3. Publishing a post

1. Call `get_connectors` to pick the page; ask if several are connected.
2. Call `list_actions` for `linkedin_organic` and read the schema of
   `create_post` or `create_image_post`; an image post needs a publicly
   reachable image URL.
3. Draft the post as text first and show it in full, exactly as it will
   appear, then wait for an explicit yes. Never publish placeholder text.
4. Call `execute_action` once and report the result from the tool, including
   any post id or URL it returns.
5. If the action is refused because write actions are not enabled for the
   account, send the user to https://onboard.windsor.ai/app/settings/account
   and retry after they confirm.

## 4. Reference real data, don't fabricate

On an error (unknown field/connector/account), call the relevant discovery
tool and retry with correct ids, or report the specific error. Never invent
follower counts, engagement figures, or post ids.