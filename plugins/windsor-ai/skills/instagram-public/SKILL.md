---
name: instagram-public
description: Look up public Instagram profile and post data for any Business or Creator account through Windsor.ai — follower, following, and media counts, bio, website, and recent post performance (likes, comments) for competitor or benchmark accounts. Use for public or competitor Instagram analysis. Do not use for the user's own connected Instagram account's private insights or publishing (use /instagram for that), Instagram Ads, or other platforms.
---

# Instagram Public (business discovery) via Windsor.ai

Windsor.ai pulls public Instagram profile and media data through Meta's
Business Discovery API. The connector id is `instagram_public`. Pass
`instagram_public` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This is **not** the user's own account data. It looks up any **public**
Instagram **Business or Creator** account — a competitor, a partner, an
influencer — by username, and returns what is publicly visible: profile stats
and recent post performance. It cannot see private accounts, Stories, or
anything not publicly visible, and it does not publish or change anything —
there are no write actions. For the user's own connected Instagram account's
private insights, publishing, or comment moderation, use `/instagram` instead;
for Instagram Ads, use `/facebook-ads`.

Golden rules:

- **Never guess field, account, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- Read-only connector; there are no live write actions to run.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which target accounts are already configured
(each represents a public username being monitored, with an id and usually a
name). If the user wants to look up a username that isn't configured yet, help
them add it: `get_connector_authorization_url` (or `get_connector_connect_info`
for the setup steps) and give them the setup link at
https://onboard.windsor.ai/app/instagram_public — don't describe manual
dashboard navigation. If several target accounts are configured and the
request is ambiguous, ask which one.

## 2. Reading public profile and post data

1. Call `get_fields` for `instagram_public` to get valid field ids. These span
   profile-level fields (follower count, follows count, media count,
   biography, website, username, display name) and per-post fields (type,
   caption, permalink, like count, comment count, timestamp), plus
   per-post-average fields (likes per post, comments per post). Use the exact
   ids returned, not these examples.
2. Call `get_data` with `fields`, the target `account`(s), and a date range for
   post-level fields (`date_from`/`date_to` or a `date_preset` like `last_30d`;
   profile-level fields reflect the account's current state and ignore the
   date range).

Common requests → recipe:

- **Profile snapshot:** follower/follows/media counts, bio, website.
- **Recent post performance:** per-post like and comment counts over a date
  range, ranked or averaged.
- **Benchmark against another account:** run the same read for each target
  username and compare.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one. There is no story, reach, or impressions data —
Business Discovery only exposes what's publicly visible.

## 3. Reference real data, don't fabricate

On an error, the target account is most likely private, not a Business or
Creator account, or not yet added — call the relevant discovery tool and
retry, or report the specific error. Never invent follower counts, engagement
numbers, or post content.