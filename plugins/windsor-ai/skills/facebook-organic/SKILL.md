---
name: facebook-organic
description: "Facebook Pages (organic) through Windsor.ai: Page and post insights (views, reach, engagement, reactions, follows, video and Reels plays), post comments for reading, and publishing a text, link or photo post to a Page after the user approves the full draft. Use for questions about the user's own Facebook Page. Do not use for Facebook Ads (Meta Ads), boosting posts, Instagram, or replying to Facebook comments (no such action exists here)."
---

# Facebook Pages (organic) via Windsor.ai

The connector id is `facebook_organic`. It covers the user's own Facebook Pages: Page and post insights, post comments (read only), and publishing. It has no ad or cost data; Facebook Ads and boosts are in the Meta Ads connector (`facebook`), a separate connector.

Write actions today are `create_post`, `create_photo_post` and `upload_photo`. There are no actions to reply to, hide or delete Facebook comments; say so if asked. The live set and each action's parameters are whatever `list_actions` returns. Publishing follows the social-media-publish skill.

## Reading insights
1. Call `get_connectors` to pick the Page; ask if several are connected.
2. Call `get_fields` for `facebook_organic` and use the exact ids it returns. Page-level fields (`page_*`), post fields (`post_*`), video fields and comment fields (`comment_*`) come from different tables, so read them in separate calls.
3. Call `get_data` with the fields, the Page and a date range.

Fields that are easy to misread:
- Unique metrics (reach and unique views) are deduplicated per period. For weekly or monthly unique values, request `year_week_iso` or `year_month` together with `period`, and check that `period` is week or month in the result; do not sum daily rows.
- `post_impressions_unique` and `page_impressions_unique` are deprecated; use `post_total_media_view_unique` and `page_total_media_view_unique`, which return the same values.
- `page_follows` is the lifetime total; `page_daily_follows_unique` and `page_daily_unfollows_unique` are daily changes.
- `post_engagements` is reactions plus comments plus shares on a post.

Reels are read from their own table (no Reel publishing action exists here):
- `reels_description`, `reels_permalink_url`, `reels_post_type` (video or reel).
- `fb_reels_total_plays` (plays including replays), `blue_reels_play_count` (plays without replays), `fb_reels_replay_count`.
- `video_post_impressions_unique` (people who saw the Reel; `reels_post_impressions_unique` is deprecated).
- `post_video_avg_time_watched` and `post_video_view_time` are in milliseconds and include replays.
- `post_video_total_reactions`, `post_video_social_actions_comment`, `post_video_social_actions_share`, `post_video_followers` (estimated follows within 24 hours of watching).

Useful post fields: `post_id`, `post_created_time`, `post_message`, `permalink_url`, `post_total_media_view_unique`, `post_engagements`, `post_reactions_total`, `post_comments_total`, `post_clicks`. Useful comment fields: `comment_id`, `comment_text`, `comment_timestamp`, `comment_is_hidden`.

## Publishing
- `create_post` takes a message, a link, or both. `create_photo_post` takes a public image URL, or a `photo_id` from `upload_photo`, plus an optional caption.
- `upload_photo` takes one local image file (JPEG, PNG or GIF, at most 4 MB) through `upload_files` and publishes nothing. Facebook deletes an unpublished photo after about 24 hours, so publish it in the same session.
- If a read or write fails with a message asking the user to log in to facebook.com, the connection has expired: reconnect it with `get_connector_connect_info`.
- If write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account.

## Reference real data
On an error (unknown field or Page), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent reach, engagement, follower counts, post ids or post text.