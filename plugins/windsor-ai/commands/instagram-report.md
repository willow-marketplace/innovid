---
name: instagram-report
description: Report on Instagram posts and Reels from the last 30 days by reach, saves and shares, against the account average, with a summary by format
---

# /instagram-report

Report on the Instagram account's posts from the last 30 days: reach, saves, shares and engagement per post, compared with the account average, plus watch time for Reels.

## Instructions

1. Call `get_connectors` and pick the user's `instagram` account. If there are several, ask which one.
2. Call `get_data` with `date_preset: "last_30d"` and the post fields `timestamp`, `media_id`, `media_type`, `media_permalink`, `media_caption`, `media_reach`, `media_like_count`, `media_comments_count`, `media_saved` and `media_shares`. If any is rejected, check it with `get_fields` instead of guessing a name.
3. If there are Reels (`media_type` REELS), read `media_id`, `media_views`, `media_reel_avg_watch_time`, `media_reel_skip_rate` and `media_reel_total_watch_time` for them in a second call. Watch times are in milliseconds and the skip rate is a fraction (0.75 = 75%).
4. In a third call, read `follower_count_1d` for the same range for daily new followers, and `followers_count` for today's total. `follower_count_1d` is empty for accounts with fewer than 100 followers; say so if it is.
5. Show a table of posts sorted by reach, with saves and shares per 1,000 accounts reached, and mark posts more than 50% above or below the account average on reach or saves. For Reels, add average watch time in seconds and skip rate.
6. Summarise by `media_type` as returned (for example REELS, CAROUSEL_ALBUM, IMAGE): number of posts, average reach, average saves.

If the user then asks to publish or to handle comments, follow the social-media-publish or social-media-engage skill: show the exact post or reply and wait for an explicit yes before calling `execute_action`.