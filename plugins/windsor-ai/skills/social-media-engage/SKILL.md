---
name: social-media-engage
description: "Handle comments, replies and reviews through Windsor.ai: read Instagram comments and reply to, comment on, hide, unhide or delete them; read the replies on the account's Threads posts and answer them; and read Google Business Profile reviews and publish or replace the owner's reply. Use when the user asks to answer comments, moderate spam, find unanswered comments, replies or reviews, or reply to a review. Read first, draft each reply, act only after an explicit yes. Facebook Page and TikTok comments can be read but not answered here."
---

# Comments, replies and reviews

Read and answer comments, replies and reviews on connected accounts through Windsor.ai. Write actions exist for Instagram comments (`instagram`), Threads replies (`threads`) and Google Business Profile reviews (`google_my_business`). Facebook Pages (`facebook_organic`) and TikTok (`tiktok_organic`) expose comment text for reading only; LinkedIn Pages (`linkedin_organic`) and YouTube (`youtube`) report comment counts only. Say so if asked to reply there. The exact set is whatever list_actions returns; never assume an action exists.

## Non-negotiable contract
- Replies and comments are public and attributed to the account. Show the original comment, reply or review next to the exact reply text, and act only after an explicit yes.
- One action per confirmation. For a batch (for example ten replies or hiding five spam comments), list every item with its text and get one explicit approval for the exact list.
- Prefer `hide_comment` over `delete_comment`. Deleting is permanent and removes other people's words; delete only when the user asks for deletion.
- Never invent the content of a comment, reply or review. Read it first with get_data and quote it.

## Instagram comments
1. Read comments with get_data on `instagram`: `comment_id`, `comment_text`, `comment_timestamp`, `comment_username`, `comment_parent_id`, `media_id` and `media_permalink`. The date range selects comments by the date their post was published, not the date of the comment, so use a range that covers the posts in question (for example last_365d; `last_1y` and `last_12m` do not cover recent posts) and filter on `comment_timestamp` for recent comments. Comments on posts published before the range are not returned.
2. Replies are returned as comment rows with a `comment_parent_id`, which is always the top-level comment of the thread. `comment_username` is filled only when the connected user can moderate the linked Facebook Page, and in practice only on the account's own comments; other people's comments usually come back without a username.
3. Actions (check list_actions for the current schema): `reply_to_comment` (comment_id, message), `create_comment` (media_id, message, a new top-level comment on the post), `hide_comment`, `unhide_comment` and `delete_comment` (comment_id). Messages are up to 2,200 characters. Comments on live videos are not supported, and the post must have comments enabled (`media_is_comment_enabled`).
4. A permission error on a comment action means the account was connected before comment access was added: hand off to /windsor-connect to reconnect it.

## Threads replies
1. Read replies with get_data on `threads`: `reply_id`, `reply_text`, `reply_username`, `reply_timestamp`, `reply_to_id`, `reply_root_post_id` and `reply_has_replies`. The account's own replies carry its username (`profile_username`). A reply with `reply_has_replies` false has no answer yet. If the read returns only the account's own replies, say that other people's replies are not available for this account.
2. `create_reply` (reply_to_id, text up to 500 characters) answers a post or a reply. There is no action to hide or delete a reply; `reply_hide_status` can be read only.

## Google Business Profile reviews
1. Read reviews with get_data on `google_my_business`: `review_id`, `review_reviewer`, `review_star_rating`, `review_comment`, `review_create_time`, `review_reply_comment` and `review_reply_update_time`. By default, review rows cover only reviews created in the requested range, so use a wide range to find older unanswered reviews.
2. An unanswered review has an empty `review_reply_comment`.
3. `reply_to_review` (review_id, comment up to 4,096 characters) publishes the owner's reply. Replying to a review that already has a reply replaces it; show the existing reply in the confirmation.
4. Address what the reviewer wrote. Do not offer refunds, discounts or other commitments the user has not approved, and do not include personal data about the reviewer.

## Drafting replies
Match the account's tone from its earlier replies when they exist. Keep replies short and specific to the comment. Flag comments that look like complaints, legal threats or personal data for the user to handle personally instead of drafting a reply.

## Hand-offs
- Publishing a new post -> /social-media-publish
- Reports on engagement -> /social-media-insights
- Connecting or reconnecting an account -> /windsor-connect

## Answer with evidence
State what was replied to, hidden or deleted, on which post or review, using the execute_action result. Never claim an action succeeded without that result.