---
name: instagram
description: Analyze Instagram organic performance through Windsor.ai. Use when the user asks about their Instagram Business or Creator account's insights — followers, reach, impressions, profile or website clicks, engagement, or the performance of individual media/posts. It can also publish image, carousel, video (Reels) and story posts and reply to, hide, unhide or delete comments, always after the user confirms the exact change. Do not use for Instagram Ads (those live in the Meta/Facebook Ads connector), other platforms, or GA4/website analytics.
---

# Instagram (organic) via Windsor.ai

Windsor.ai pulls Instagram **organic** insights from the Instagram Graph API. The
connector id is `instagram`. Pass `instagram` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This connector covers **organic** Instagram: profile and media insights, plus
publishing and comment moderation. It has **no ad or cost data** (Instagram Ads
live in the Meta/Facebook Ads connector, so route ad-spend questions there).
Write actions today are `create_image_post`, `create_carousel_post`,
`create_video_post`, `create_story`, `create_comment`, `reply_to_comment`,
`hide_comment`, `unhide_comment` and `delete_comment`; the live set is whatever
`list_actions` returns.

Golden rules:

- **Never guess field, account, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- **Publishing and comment changes are public and immediate.** Show the exact
  caption, media URLs, or reply text and call `execute_action` only after an
  explicit yes. `delete_comment` cannot be undone; prefer `hide_comment` unless
  the user asks to delete.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which Instagram accounts are connected (each has an
id and usually a name/username). The account must be an Instagram Professional
account (Business or Creator) linked to a Facebook Page. If none is connected,
help the user connect: `get_connector_authorization_url` (or
`get_connector_connect_info` for the auth type and steps) and give them the
setup link — don't describe manual dashboard navigation.

If several accounts are connected and the request is ambiguous, ask which one.

## 2. Reading insights

1. Call `get_fields` for `instagram` to get valid field ids. Typical ones
   include account-level metrics (e.g. `followers_count`, `follows_count`,
   `media_count`, reach, impressions, profile/website clicks, engagement) and
   per-media metrics — use the exact ids returned, not these examples.
2. Call `get_data` with `fields`, the target `account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset` like `last_7d`, `last_30d`,
   `this_month`; append `T` to include today).

Instagram date behaviour to keep in mind:

- **Lifetime metrics** (e.g. total followers) ignore the date range — the API
  always returns the latest value. Don't present them as period-specific.
- **Daily metrics** are only available for roughly the **last 30 days**. Requests
  for older daily data will return nothing.
- **Stories** insights exist only for the **last 24 hours**.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one. There is no cost/spend data here.

## 3. Publishing and moderating comments

1. Call `get_connectors` to pick the account; ask if several are connected.
2. Call `list_actions` for `instagram` and build the params from the returned
   schema. Media must be publicly reachable URLs (images JPEG, videos MP4 or
   MOV); a carousel takes 2 to 10 images; a story takes exactly one image or
   video. Comment actions need a `media_id` or `comment_id` from a `get_data`
   read, never a guessed id.
3. Show the post or the comment change in full, exactly as it will appear, and
   wait for an explicit yes. Never publish placeholder text.
4. Call `execute_action` once and report the result from the tool, including
   any media or comment id it returns.
5. If commenting is refused with a permission error, the account was connected
   before comment access was added: ask the user to reconnect it with
   `get_connector_authorization_url`. If write actions are not enabled for the
   account, send the user to https://onboard.windsor.ai/app/settings/account.

## 4. Reference real data, don't fabricate

On an error (unknown field/connector/account), call the relevant discovery tool
and retry with correct values or report the specific error. Never invent
follower counts, engagement numbers, or media ids.