---
name: google-business-profile
description: Analyze Google Business Profile (local listing) performance and manage it through Windsor.ai. Use when the user asks about a business location's insights — views, clicks, and impressions — reviews and ratings, or local post performance. It can also publish or update a local post, reply to a review, upload a photo, update location details (address, categories, service area), or update hours and open status, always after the user confirms the exact change. Do not use for paid Google Ads, organic Google Search Console, GA4, or other platforms.
---

# Google Business Profile via Windsor.ai

Windsor.ai pulls Google Business Profile insights, reviews, and local posts, and
can manage the listing, through the Google Business Profile APIs. The connector
id is `google_my_business`. Pass `google_my_business` as the connector on every
tool call; the Windsor.ai server serves all connectors, so it is never implied.

Reads are the main job. The connector also exposes write actions, currently
`create_local_post`, `update_local_post`, `reply_to_review`, `upload_media`,
`update_location`, `update_address`, `update_categories`, `update_service_area`,
`update_attributes`, `update_service_items`, `set_regular_hours`,
`set_special_hours`, and `set_open_status` (the live set is whatever
`list_actions` returns). It has no ad or cost data (that lives in the Google
Ads connector) and no organic search data (that's Google Search Console);
route those questions there.

Golden rules:

- **Never guess field, location, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- **Changes are public and immediate.** A local post, a review reply, or a
  listing edit is visible to anyone looking up the business. Show the exact
  change and call `execute_action` only after an explicit yes.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which locations/accounts are connected (each has
an id and usually a name). If none is connected, help the user connect:
`get_connector_authorization_url` (or `get_connector_connect_info` for the auth
type and steps) and give them the setup link — don't describe manual dashboard
navigation. If several locations are connected and the request is ambiguous,
ask which one.

## 2. Reading performance, reviews, and posts

1. Call `get_fields` for `google_my_business` to get valid field ids. These
   span location insights (views, clicks, impressions, by date), location and
   account metadata (address, categories, hours, labels, website), reviews
   (count, average rating, review text and update time), and local posts and
   media. Use the exact ids returned, not these examples.
2. Call `get_data` with `fields`, the target location/`account`(s), and a date
   range (`date_from`/`date_to` or a `date_preset` like `last_7d`, `last_30d`,
   `this_month`; append `T` to include today) for time-series fields; location,
   review, and post metadata are typically current-state and don't need a date
   range.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one.

## 3. Managing the listing (write actions)

1. Call `get_connectors` to pick the location; ask if several are connected.
2. Call `list_actions` for `google_my_business` and read the schema of the
   action the user wants (a local post, a review reply, an attribute, address,
   category, hours, or open-status change, or a photo upload — a photo needs a
   publicly reachable image URL).
3. For a review reply, read the review first with `get_data` so the reply
   addresses what the reviewer actually wrote. For any edit to existing
   content (a post, the address, hours), show the current value and the
   proposed value together.
4. Draft the exact text or value and show it in full, then wait for an
   explicit yes. Never publish or save placeholder content.
5. Call `execute_action` once and report the result from the tool, including
   anything it returns (e.g. a post id or URL).
6. If the action is refused because write actions are not enabled for the
   account, send the user to
   https://onboard.windsor.ai/app/settings/account and retry after they
   confirm.

## 4. Reference real data, don't fabricate

On an error (unknown field/connector/location), call the relevant discovery
tool and retry with correct ids, or report the specific error. Never invent
view counts, ratings, review text, or location details.