---
name: linkedin-pages
description: "LinkedIn company pages (organic) through Windsor.ai: follower growth, page views, post impressions, clicks, reactions, comments, shares and engagement rate, follower and visitor breakdowns by industry, job function, seniority, company size and country, and publishing a text, link or image post to the company page after the user approves the full draft. Use for questions about the user's own LinkedIn company page. Do not use for LinkedIn Ads, personal profiles, or replying to LinkedIn comments (no such action exists here)."
---

# LinkedIn Pages via Windsor.ai

The connector id is `linkedin_organic`. It covers the user's own LinkedIn company pages: page and post analytics, follower and visitor breakdowns, and publishing. It has no ad or cost data; LinkedIn Ads are in the LinkedIn Ads connector (`linkedin`), a separate connector. Personal profiles are not covered.

Write actions today are `create_post`, `create_image_post` and `upload_image`. There are no actions to reply to or moderate comments. The live set and each action's parameters are whatever `list_actions` returns. Publishing follows the social-media-publish skill.

## Reading insights
1. Call `get_connectors` to pick the page; ask if several are connected.
2. Call `get_fields` for `linkedin_organic` and use the exact ids it returns. Post fields (`share_*`), page view fields, follower breakdowns and account analytics come from different tables, so read them in separate calls.
3. Call `get_data` with the fields, the page and a date range.

Fields that are easy to misread:
- `organization_follower_count` is the current total; `followers_gain_organic` and `followers_gain_paid` are daily gains.
- `share_engagement_rate` is LinkedIn's own rate: organic clicks, likes, comments and shares divided by impressions. Say so when you show it next to a rate computed on reach.
- Follower and visitor breakdowns (industry, function, seniority, staff count range, country, region) describe the page audience, not single posts. Request them on their own.

Useful post fields: `share_id`, `share_published_time`, `share_text`, `share_url`, `share_impression_count`, `share_unique_impressions_count`, `share_clicks_count`, `share_like_count`, `share_comment_count`, `share_count`, `share_total_engagements`, `share_engagement_rate`.

## Publishing
- `create_post` takes commentary (up to 3,000 characters), a link, or both. A link is shared as an article card and needs `link_title`, because LinkedIn does not fetch the page title; `link_description` is optional.
- `create_image_post` takes a public https image URL, or an `image_urn` from `upload_image`, plus optional commentary.
- `upload_image` takes one local image file (JPEG, PNG or GIF) through `upload_files` and publishes nothing.
- If write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account.

## Reference real data
On an error (unknown field or page), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent follower counts, engagement figures, post ids or post text.