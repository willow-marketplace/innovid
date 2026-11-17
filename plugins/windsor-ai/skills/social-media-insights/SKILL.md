---
name: social-media-insights
description: Report, rank, compare, and trend social media performance across Instagram, Facebook Pages, TikTok, YouTube, LinkedIn Pages, and Google Business Profile through Windsor.ai. Use when the user asks about reach, impressions, engagement (likes, comments, shares, saves), follower growth, video views, and watch time. Do not use to change anything, to connect an account, or to schedule exports - route those to the sibling skills.
---

# Social Media insights

Read live social media data from Instagram, Facebook Pages, TikTok, YouTube, LinkedIn Pages, and Google Business Profile through Windsor.ai and turn it
into a clear report, ranking, comparison, or trend. The main connector ids for this area are instagram, facebook_organic, tiktok_organic, youtube, linkedin_organic, google_my_business. Other sources in the same category work the same way; find their ids with get_connectors.

## Stay in scope
- Use this skill for metrics, rankings, comparisons, trends, and breakdowns of reach, impressions, engagement (likes, comments, shares, saves), follower growth, video views, and watch time across account or page, and post or video.
- If the user asks to create or publish something new, use /social-media-create instead.
- If the user asks to connect, reconnect, or add an account, use /windsor-connect instead.
- If the user asks for a recurring, scheduled, or automated export, use /windsor-export instead.

## Resolve scope and time
- Call get_connectors first to see which of the six sources have connected accounts. If several accounts match the request, ask which one.
- Read field ids from get_fields for the chosen connector, and get_options for date-filter columns and connector options. Never guess ids or field names.
- Use the date range the user gave. If none, use a sensible preset (last_30d, this_month, last_7d), and state which one you used. Append T to a preset to include today.

## Choose the smallest useful read
- Channel comparison -> get_data per connector with fields [date, reach, engagement, followers], date_preset last_30d.
- Best posts -> fields [post or media id, caption or title, engagement, reach], rank by the metric the user names (engagement rate = engagement / reach).
- Follower trend -> fields [date, followers] over the preset; report net change.
- Keep field sets small and request only the accounts you need. Check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- Organic metrics are platform-reported and not directly comparable across networks; say so when comparing.
- Engagement rate needs a denominator; state whether you used reach or followers.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.
- If "best", "top", or "worst" lacks a metric, ask which metric should define the ranking.

## Answer with evidence
Lead with the requested result, then state the account or source, the time window, and the numbers behind the conclusion. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
After a report the user is likely to want regularly, offer once to set it up as a recurring export with /windsor-export. Never offer plans, upgrades, or pricing. Never repeat an offer the user declined.