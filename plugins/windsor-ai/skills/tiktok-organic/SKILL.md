---
name: tiktok-organic
description: "TikTok account (organic) insights through Windsor.ai: video views, likes, comments, shares, reach and watch time per video, daily account views, profile views and follower change, comments, and audience breakdowns by country, city, age, gender and active hour. Use for questions about the user's own TikTok account. Read-only; it cannot publish videos or reply to comments. Do not use for TikTok Ads."
---

# TikTok (organic) via Windsor.ai

The connector id is `tiktok_organic`. It covers the user's own TikTok account. It is read-only: there are no write actions, so it cannot publish videos, reply to comments or change the account. It has no ad or cost data; TikTok Ads are a separate connector (`tiktok`).

## Reading
1. Call `get_connectors` to pick the account; ask if several are connected.
2. Call `get_fields` for `tiktok_organic` and use the exact ids it returns. Account fields, video fields (`video_*`), comment fields (`comment_*`) and audience breakdowns (`audience_*`) come from different tables: read them in separate calls. Mixing a video field with account fields returns account rows with the video fields empty.
3. Call `get_data` with the fields, the account and a date range.

Fields that are easy to misread:
- `date`: for video and comment rows it is the date the video was created, and the date range selects videos by it. A video posted before the range does not appear.
- `video_views` is the account's daily views of all its videos. Per-video views are `video_views_count`.
- `followers_count` is the net number of followers gained on that day, not the total. The current total is `total_followers_count`; daily changes are also in `daily_total_followers` and `daily_lost_followers`.
- `likes`, `comments`, `shares` and `profile_views` are daily account values; `video_likes`, `video_comments` and `video_shares` are per video.
- `video_reach` arrives 24 to 48 hours after the views and can be under-reported for high-volume videos.
- Audience breakdowns (`audience_*`) describe the account's followers, not the viewers of single videos. Do not use fields marked (Internal) in get_fields, such as the `_non_aggregated` variants.

Useful video fields: `video_id`, `video_create_datetime`, `video_caption`, `video_share_url`, `video_views_count`, `video_likes`, `video_comments`, `video_shares`, `video_reach`, `video_average_time_watched`, `video_full_watched_rate`, `video_new_followers`.

## Reference real data
On an error (unknown field or account), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent view counts, follower numbers or video ids.