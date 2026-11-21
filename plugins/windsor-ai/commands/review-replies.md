---
name: review-replies
description: Find Google Business Profile reviews without an owner reply, draft replies, and publish them after you approve
---

# /review-replies

Find reviews on the user's Google Business Profile locations that have no owner reply, and publish approved replies.

## Instructions

1. Call `get_connectors` and pick the `google_my_business` location(s); ask if the request is ambiguous.
2. Call `get_data` on `google_my_business` with `review_id`, `review_reviewer`, `review_star_rating`, `review_comment`, `review_create_time` and `review_reply_comment`, and `date_preset: "last_90d"` (or the user's range). By default, review rows cover only reviews created in the range; widen it for older reviews.
3. Keep the reviews with an empty `review_reply_comment`. Sort low ratings first.
4. Draft a short reply for each that addresses what the reviewer wrote. Do not promise refunds, discounts or other commitments the user has not approved, and do not include personal data. For a review with no text, draft a brief thank-you. Mark reviews that mention legal action, safety or personal data for the user to handle personally.
5. Show each review next to its draft and ask the user to approve, edit or skip it. After an explicit yes to the final list, call `execute_action` with `reply_to_review` once per review and report each result.

A reply is public on Google Search and Maps. Replying again later replaces the earlier reply.