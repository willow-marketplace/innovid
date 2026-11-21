---
name: youtube
description: "YouTube channel, playlist and video analytics through Windsor.ai: views, watch time, average view duration and percentage, subscribers gained and lost, likes, comments, shares, traffic sources, countries, viewer age and gender, and Shorts compared with regular videos. Use for questions about the user's own YouTube channel. Read-only; it cannot upload, edit or comment. Do not use for YouTube Ads, which run through Google Ads."
---

# YouTube via Windsor.ai

The connector id is `youtube`. It covers the user's own YouTube channels through the YouTube Analytics and Data APIs. It is read-only: there are no write actions, so it cannot upload or edit videos, reply to comments or change the channel. It has no ad or cost data; YouTube Ads run through Google Ads (`google_ads`), a separate connector.

## Reading
1. Call `get_connectors` to pick the channel; ask if several are connected.
2. Call `get_fields` for `youtube` and use the exact ids it returns. If the analysis needs segmentation controls, call `get_options`.
3. Call `get_data` with the fields, the channel and a date range.

Fields that are easy to misread:
- `views` without the `video` field is the channel total, which keeps views of videos deleted since; per-video rows (`video` field) exclude deleted videos, so they can sum to less than the channel total.
- `subscribers_gained` and `subscribers_lost` are attributed to the videos in the report. For the channel-wide daily series, which also counts subscriptions not made on a video, use `subscribers_gained_channel` and `subscribers_lost_channel`; these come from a separate channel report and cannot be requested together with `views`, `estimated_minutes_watched` or other video metrics, so read them in their own call. The current total is `subscriber_count`.
- `video_view_count` is a video's lifetime views, not views in the range; use `views` with the `video` field for the range.
- `creator_content_type` separates Shorts (`shorts`) from regular videos (`videoOnDemand`), live streams and stories.

Common reads:
- Channel trend: `date` plus `views` and `estimated_minutes_watched`, then a second call with `date` plus `subscribers_gained_channel` and `subscribers_lost_channel`.
- Top videos: `video`, `video_title`, `videourl` plus the ranking metric (for example `views` or `estimated_minutes_watched`).
- Shorts against regular videos: `creator_content_type` plus `views`, `average_view_percentage`.
- Traffic sources: `traffic_source` plus `views`.

## Reference real data
On an error (unknown field or channel), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent view counts, subscriber numbers, watch time or video ids.