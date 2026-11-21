---
name: threads
description: "Threads through Windsor.ai: post views, likes, replies, reposts, quotes and shares, profile views and followers, follower breakdowns, the replies people leave on the account's posts, and publishing a text or image post or a reply, always after the user confirms the exact text. Use for questions about the user's own Threads account. Do not use for Instagram, which is a separate connector."
---

# Threads via Windsor.ai

The connector id is `threads`. It covers the user's own Threads account: post and profile insights, the replies on its posts, and publishing. It has no ad or cost data.

Write actions today are `create_text_post`, `create_image_post` and `create_reply`. There are no actions to hide or delete replies; `reply_hide_status` can be read but not changed here. The live set and each action's parameters are whatever `list_actions` returns. Publishing follows the social-media-publish skill and replies follow the social-media-engage skill.

## Reading
1. Call `get_connectors` to pick the account; ask if several are connected.
2. Call `get_fields` for `threads` and use the exact ids it returns. Post fields (`post_*`), profile fields (`profile_*`) and reply fields (`reply_*`) come from different tables, so read them in separate calls.
3. Call `get_data` with the fields, the account and a date range.

Fields that are easy to misread:
- `date`: for posts it is the publish date, and the date range selects posts by it; for profile insights it is the day of the value.
- `profile_followers_count` is the lifetime total and ignores the date range. `profile_views` is a daily value.
- Follower breakdowns (`profile_followers_age`, `_city`, `_country`, `_gender`) need at least 100 followers, and only one breakdown can be requested at a time.
- `post_replies` on a top-level post counts all replies in the thread; on a reply it counts only direct replies. `profile_replies` counts top-level replies only.
- `post_media_type` values include TEXT_POST, IMAGE, VIDEO, CAROUSEL_ALBUM, AUDIO and REPOST_FACADE (a repost).

Useful post fields: `post_id`, `post_timestamp`, `post_text`, `post_permalink`, `post_media_type`, `post_views`, `post_likes`, `post_replies`, `post_reposts`, `post_quotes`, `post_shares`. Useful reply fields: `reply_id`, `reply_text`, `reply_username`, `reply_timestamp`, `reply_to_id`, `reply_root_post_id`, `reply_has_replies`.

## Publishing and replies
- `create_text_post` takes text up to 500 characters. `create_image_post` takes a public JPEG or PNG URL and optional text up to 500 characters.
- `create_reply` takes `reply_to_id` (a `post_id` or `reply_id` from a read, never a guessed id) and text up to 500 characters. The reply is public and attributed to the account.
- Show the exact text and wait for an explicit yes before calling `execute_action`.
- If write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account.

## Reference real data
On an error (unknown field or account), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent view counts, follower numbers, reply text or post ids.