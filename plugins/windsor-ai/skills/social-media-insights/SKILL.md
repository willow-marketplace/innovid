---
name: social-media-insights
description: "Report, rank, compare, and trend organic social performance across Instagram, Facebook Pages, LinkedIn Pages, Threads, TikTok, YouTube, and Google Business Profile through Windsor.ai: reach, impressions, views, engagement (likes, comments, shares, saves, reposts), follower growth, watch time, top posts, Reels and videos, and Google Business Profile views, clicks and ratings. Use for cross-platform comparisons, best and worst posts, Reels against other formats, weekly reviews, and when the user asks what Windsor.ai can do for organic social. Do not use to publish or change anything; use social-media-publish, social-media-engage or google-business-profile for that."
---

# Social media insights

Read live organic social data through Windsor.ai and turn it into a clear report, ranking, comparison, or trend. The organic social connectors are Instagram (`instagram`), Facebook Pages (`facebook_organic`), LinkedIn Pages (`linkedin_organic`), Threads (`threads`), TikTok (`tiktok_organic`), YouTube (`youtube`) and Google Business Profile (`google_my_business`). Pass the slug as the connector on every data and action tool call.

## Resolve scope and time
- Call get_connectors first. If the platform is not connected, hand off to /windsor-connect instead of guessing. If several accounts match, ask which one, unless the user asked about all accounts, all clients or every brand; then delegate to the social-media-analyst agent.
- Read field ids from get_fields for the chosen connector, and get_options for connector options. Never guess connector slugs, account ids, field ids, or option ids. The per-platform skills (instagram, facebook-organic, linkedin-pages, threads, tiktok-organic, youtube, google-business-profile) list the fields that behave unexpectedly.
- Use the date range the user gave. If none, use last_30d and say so. To include today, use a preset ending in T (for example last_7dT or this_monthT).

## Choose the smallest useful read
- Post or video ranking -> the post-level fields (id, date, caption or title, permalink) plus the ranking metric; name the metric used. If "best" or "worst" has no metric, ask which one.
- Reels -> on Instagram, post rows with `media_type` REELS and the `media_reel_*` fields; on Facebook Pages, the Reels fields (`reels_*`, `fb_reels_*`); on YouTube, `creator_content_type` shorts. See the platform skills.
- Account trend -> date plus the account-level metric over the range; report direction and size of the change.
- Platform comparison -> one get_data call per connector, then compare in the answer.
- Post rows and account rows come from different tables on every platform: read them in separate calls rather than mixing post fields and account fields in one request.
- On Instagram and TikTok the date range selects posts and videos by their publish date, and comments by the publish date of their post. On Threads it selects posts by publish date; check whether reply rows follow the same rule. On Google Business Profile it selects, by default, reviews and local posts by their creation date. A post published before the range does not appear, nor do its comments; widen the range when older posts matter.
- Keep field sets small, request only the accounts you need, and check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- Each platform defines reach, impressions, views and engagement in its own way. Say so when comparing platforms, and do not add them together as if they were the same metric.
- Unique metrics (reach, unique impressions, unique viewers) are deduplicated per day. Do not sum daily rows for a weekly or monthly total. On Facebook Pages, request `year_week_iso` or `year_month` together with `period` and check that `period` is week or month before reporting a deduplicated total. On other platforms, report daily values or say a deduplicated period total is not available.
- Engagement rate needs a denominator. Use reach or impressions (or the platform's own rate field, such as LinkedIn's `share_engagement_rate`), say which, and say when the denominator is zero.
- Follower fields differ: some are the current total, some are a daily change. Check the description in get_fields before reporting growth.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.

## Answering "what can you do"
Call get_connectors with include_not_yet_connected=True and answer from it: which of the seven platforms are connected, which can be connected, and per connector which write actions exist (call list_actions for the detail). Do not list capabilities the tool results do not show. For ads, analytics, CRM and other sources use /windsor-insights.

## Answer with evidence
Lead with the requested result, then state the platform and account, the time window, and the numbers behind the conclusion. Link posts by their permalink when the data has one. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
- Publishing a new post -> /social-media-publish
- Replying to comments, replies and reviews, hiding or deleting comments -> /social-media-engage
- Google Business Profile hours, address, categories and other listing edits -> /google-business-profile
- All clients or many accounts at once -> social-media-analyst agent
- Connecting or reconnecting an account -> /windsor-connect
After a report that surfaces a top post, offer once to draft a post in the same format; publishing happens only after the user approves the full draft. Never offer plans, upgrades, or pricing.