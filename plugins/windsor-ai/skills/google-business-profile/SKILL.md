---
name: google-business-profile
description: "Google Business Profile through Windsor.ai: location views, clicks, calls, direction requests and search keywords, reviews and ratings, local posts and photos, and confirmed changes; publish or update a local post, reply to a review, add a photo, update the description, website, phone numbers, categories, attributes, services, service area or address, and set regular hours, holiday hours or temporary closure. Use for questions about a business location on Google Search and Maps. Do not use for Google Ads, Search Console or GA4."
---

# Google Business Profile via Windsor.ai

The connector id is `google_my_business`. It covers the user's own Google Business Profile locations: performance, reviews, local posts and photos, and listing management. It has no ad or website search data; Google Ads (`google_ads`) and Search Console (`searchconsole`) are separate connectors.

Write actions today are `create_local_post`, `update_local_post`, `reply_to_review`, `upload_media`, `update_location`, `update_categories`, `update_attributes`, `update_service_items`, `update_service_area`, `update_address`, `set_regular_hours`, `set_special_hours` and `set_open_status`. The live set and each action's parameters are whatever `list_actions` returns. New posts follow the social-media-publish skill and review replies follow the social-media-engage skill.

## Reading
1. Call `get_connectors` to pick the location; ask if several are connected.
2. Call `get_fields` for `google_my_business` and use the exact ids it returns.
3. Call `get_data` with the fields, the location and a date range.

Fields that are easy to misread:
- `impressions` is Maps plus Search, desktop plus mobile; the split is in `impressions_desktop_maps`, `impressions_desktop_search`, `impressions_mobile_maps` and `impressions_mobile_search`. `clicks` is `call_clicks` plus `website_clicks`.
- By default, review rows and local post rows cover only reviews and posts created in the requested range. Use a wide range to list older ones.
- `review_average_rating` and `review_count` are limited to the range; `review_average_rating_total` and `review_total_count` are all-time.
- `location_*` fields describe the listing's current state (address, categories, hours, attributes, services, service area).

## Changing the listing
Every change follows read, propose, confirm, execute:
1. Read the current value with `get_data` (the matching `location_*`, `post_*` or `review_*` fields).
2. Call `list_actions` and build params from the schema.
3. Show the current value and the new value side by side, and wait for an explicit yes.
4. Call `execute_action` once and report the result. Google may review listing edits before they appear.

Actions that replace a whole set (resend everything the listing should keep):
- `update_service_items` replaces all services; read `location_service_items` first.
- `update_categories` sets the primary category and replaces every additional category (at most 9); omitting `additional_category_ids` removes them all. Read `location_primary_category_id` and `location_additional_categories` first.
- `update_service_area` replaces all places (at most 20); read `location_service_area` first.
- `set_regular_hours` replaces all weekly hours; a day with no period shows as closed. Read `location_regular_hours_periods` first.
- `set_special_hours` replaces all special dates. Read `location_special_hours_periods` first and resend every upcoming exceptional date the listing should keep together with the new ones.
- `update_attributes` is the exception: it changes only the attributes named.

Actions that can unpublish the listing:
- `update_address`, and `update_service_area` with `hide_business_address=true`, can send the listing back into Google's verification, which may unpublish it until the business verifies again. They refuse to run unless `acknowledge_reverification_risk` is true. State this risk in the confirmation and set it only after the user approves that risk explicitly.
- Run `update_address`, `update_service_area` and `update_location` with `validate_only=true` first when the user is unsure; success means Google accepted the request for validation, not that anything changed.
- `update_address` replaces the whole address. Use `keep_map_pin=true` only when the business has not moved.
- `update_categories` can also trigger re-verification in some cases; say so.

Other notes:
- `upload_media` with category COVER or PROFILE replaces the current cover or profile photo. Photos must be public JPG or PNG URLs, at least 250x250 px and 10 KB.
- `update_location` changes the description (up to 750 characters), website and phone numbers. Additional phone numbers are kept unless `additional_phones` is sent; `[]` removes them.
- `set_open_status` switches between OPEN and CLOSED_TEMPORARILY; it is reversible.
- `reply_to_review` replaces an existing reply.
- If write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account.

## Reference real data
On an error (unknown field or location), call the relevant discovery tool and retry with correct values, or report the specific error. Never invent view counts, ratings, review text or listing details.