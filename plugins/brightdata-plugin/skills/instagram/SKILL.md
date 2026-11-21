---
name: instagram
description: "Instagram (organic) through Windsor.ai: account and post insights (reach, views, likes, comments, saves, shares, follower growth), comments, and publishing image, carousel, Reel and story posts, plus commenting on posts and replying to, hiding, unhiding or deleting comments, always after the user confirms the exact change. Use for questions about the user's own Instagram Business or Creator account. Do not use for Instagram Ads (Meta Ads) or for other people's public accounts."
---

# Instagram (organic) via Windsor.ai

The connector id is `instagram`. It covers the user's own Instagram Professional account (Business or Creator): profile and post insights, comments, publishing and comment moderation. It has no ad or cost data; Instagram Ads are reported in the Meta Ads connector (`facebook`), which is a separate connector.

Write actions today are `create_image_post`, `create_carousel_post`, `create_video_post`, `create_story`, `create_comment`, `reply_to_comment`, `hide_comment`, `unhide_comment` and `delete_comment`. The live set and each action's parameters are whatever `list_actions` returns. Publishing follows the social-media-publish skill and comment work follows the social-media-engage skill.

## Reading insights
1. Call `get_connectors` to pick the account; ask if several are connected.
2. Call `get_fields` for `instagram` and use the exact ids it returns. Post rows and account rows come from different tables, so read them in separate calls.
3. Call `get_data` with the fields, the account and a date range.

Fields that are easy to misread:
- `date`: for post, story and comment rows it is the date the post was published, and the date range selects posts by it. Comments on an older post appear only when the range covers that post's publish date.
- `followers_count` is today's total; it has no history. `follower_count_1d` (also `follower_count`) is new followers per day, available for the last 30 days excluding today and not at all for accounts with fewer than 100 followers.
- `reach` is an alias for `reach_1d`, unique accounts per day: do not add days together for a period total. `impressions` and `impressions_1d` return empty; use the account-level `views` for the number of times content was seen.
- `media_reach` is unique accounts reached by one post; `media_views` counts every time it was seen.
- `media_type` describes the format. Read the values from the data (for example IMAGE, CAROUSEL_ALBUM, REELS; the field description says REEL, but the data returns REELS) rather than assuming them.
- Story fields (`story_*`) cover stories from the last 24 hours only.
- `comment_username` is filled only when the connected user can moderate the linked Facebook Page, and in practice only on the account's own comments.

## Reels
Filter post rows on `media_type` REELS and add the Reels fields:
- `media_views` (times shown) and `media_reach` (unique accounts). `media_plays` and `media_all_plays` return empty; use `media_views` for plays.
- `media_reel_avg_watch_time` and `media_reel_total_watch_time` are in milliseconds; convert to seconds or minutes before showing.
- `media_reel_skip_rate` is the share of views skipped within the first 3 seconds. The data returns it as a fraction (0.75 = 75%).
- `media_reel_total_interactions` is likes, saves, comments and shares minus unlikes, unsaves and deleted comments.
- `media_follows`, `media_profile_visits` and `media_profile_activity` are not supported for Reels and return 0 or empty for them; do not report those as real figures. Compare formats on reach, views and interactions instead.

Useful post fields: `timestamp`, `media_id`, `media_type`, `media_permalink`, `media_caption`, `media_reach`, `media_views`, `media_like_count`, `media_comments_count`, `media_saved`, `media_shares`. Useful comment fields: `comment_id`, `comment_text`, `comment_timestamp`, `comment_username`, `comment_parent_id`, `media_id`.

## Publishing and comments
- Media must be publicly reachable URLs. Feed images are JPEG, at most 8 MB, aspect ratio between 4:5 and 1.91:1; a carousel takes 2 to 10 such images and no videos; a video post (MP4 or MOV, 3 seconds to 15 minutes, at most 1 GB, 9:16 recommended) is published as a Reel, with `share_to_feed` deciding whether it also shows in the main feed; a story takes exactly one image (JPEG, at most 8 MB) or one video (3 to 60 seconds, at most 100 MB) and no caption.
- Comment actions need a `media_id` or `comment_id` from a `get_data` read, never a guessed id.
- `delete_comment` is permanent; prefer `hide_comment` unless the user asks to delete.
- A permission error on a comment action means the account was connected before comment access was added: reconnect it with `get_connector_connect_info`.
- If write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account.

## Reference real data
On an error (unknown field or account), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent follower counts, engagement numbers, comment text or media ids.