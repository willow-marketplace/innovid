---
name: instagram-report
description: Report on Instagram posts from the last 30 days by reach, saves and follows, against the account average
---

# /instagram-report

Report on the Instagram account's posts from the last 30 days: reach, saves, engagement and follower growth, with each post compared to the account average.

## Instructions

1. Call `get_connectors` and pick the user's `instagram` account. If there are several, ask which one.
2. Use these fields: `timestamp`, `media_type`, `media_permalink`, `media_caption`, `media_reach`, `media_like_count`, `media_comments_count`, `media_saved`, `media_shares`, and for the account `followers_count` and `follower_count_1d`. If any is rejected, check it with `get_fields` or `get_options` instead of guessing a name.
3. Call `get_data` with `date_preset: "last_30d"` twice: once with the post fields, once with the account fields.
4. Show a table of posts sorted by reach, with saves per 1,000 reached next to each post, and mark the posts more than 50% above or below the account average on reach or saves.
5. Summarise by post type (reel, carousel, image): number of posts, average reach, average saves.
6. Show follower growth over the period if the account-level data covers it.

If the user then asks to publish a post, reel, carousel or story, or to comment on, reply to, hide or delete a comment, call `list_actions` for `instagram`, read the action's parameter schema, show the exact post or comment text, and wait for an explicit yes before calling `execute_action`. Accounts connected before comment access was added return a permission error on comments until they are reconnected in Windsor.ai.