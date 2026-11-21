---
name: comment-inbox
description: Instagram comments and Threads replies that have no answer from the account, with drafted replies, posted or hidden after you approve
---

# /comment-inbox

Find Instagram comments and Threads replies from the last 7 days (or the range the user gives) that the account has not answered, and handle them after approval.

## Instructions

1. Call `get_connectors` and pick the `instagram` and `threads` accounts; ask if there are several of either.
2. **Instagram:** call `get_data` on `instagram` with `comment_id`, `comment_text`, `comment_timestamp`, `comment_username`, `comment_parent_id`, `media_id` and `media_permalink`, and `date_preset: "last_365d"` (or the user's wider range; `last_1y` and `last_12m` do not cover recent posts). The range selects comments by their post's publish date, so comments on posts published before the range are not returned; say how far back posts were covered. Keep comments whose `comment_timestamp` is in the requested window.
3. Rows whose `comment_username` equals the account's username are the account's own comments; drop them from the list (in practice only the account's own comments carry a username). A top-level comment is answered when an own comment has `comment_parent_id` equal to its `comment_id`. A reply by someone else is answered when an own comment in the same thread (same `comment_parent_id`) has a later `comment_timestamp`. If no row has a `comment_username`, own and other comments cannot be told apart: say so and list all comments.
4. **Threads:** call `get_data` on `threads` with `reply_id`, `reply_text`, `reply_username`, `reply_timestamp`, `reply_to_id`, `reply_root_post_id` and `reply_has_replies` for the same post range as the Instagram read (`last_365d`), keeping replies whose `reply_timestamp` is in the requested window, and read `profile_username` in a separate call. Drop the account's own replies. A reply is answered when an own reply has `reply_to_id` equal to its `reply_id`; a reply with `reply_has_replies` false is certainly unanswered. If the read returns only the account's own replies, say that other people's replies are not available for this account and skip Threads.
5. Show the unanswered items grouped by post, with a drafted reply for each (Threads replies at most 500 characters), and mark Instagram comments that look like spam (suggest hiding) and any item that looks like a complaint, legal threat or personal data (leave for the user to handle personally). Threads replies cannot be hidden here.
6. Ask the user to approve, edit or skip each item. After an explicit yes to the final list, call `execute_action` once per item: `reply_to_comment` or `hide_comment` on Instagram (`delete_comment` only if the user asks to delete), `create_reply` with `reply_to_id` on Threads. Report each result.

If an Instagram comment action returns a permission error, the account was connected before comment access was added: send the user to /windsor-connect to reconnect it.