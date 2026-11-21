---
name: search-terms
description: Find Google Ads search terms that spend without converting and push them as negative keywords after you confirm
---

# /search-terms

Review Google Ads search terms from the last 30 days, list the ones that spend without converting, and add the ones you approve as negative keywords.

## Instructions

1. Call `get_connectors` and pick the user's `google_ads` account. If there are several, ask which one.
2. Call `get_data` on `google_ads` with `["campaign", "campaign_id", "ad_group_name", "ad_group_id", "search_term", "spend", "clicks", "impressions", "conversions"]` and `date_preset: "last_30d"`. Google Ads cannot return `search_term` together with keyword fields, so keep keywords out of this call. Compute cost per conversion as spend divided by conversions.
3. In a separate call, get the account's keywords with `["campaign_id", "ad_group_id", "keyword_text", "keyword_match_type", "spend"]` and the same date range.
4. Skip rows with no search term (Performance Max campaigns return spend without search terms, and this action does not add negatives to them). Group the rest by search term and list the terms with spend above zero and zero conversions, sorted by spend descending. Show campaign, ad group, spend, clicks and impressions. Separately, list converting terms whose cost per conversion is more than twice the account average.
5. Suggest which non-converting terms to add as negatives, and at which level (campaign or ad group). Do not suggest terms that contain the brand name, that match one of the keywords from step 3, or that are the target of a competitor or category ad group the user runs on purpose.
6. Stop and wait for the user to approve the list. Never add negatives without an explicit yes.
7. After approval, call `list_actions` for `google_ads` and read the schema of `push_negative_keywords`. Call `execute_action` once per target: `level: "campaign"` with `campaign_id`, or `level: "ad_group"` with `ad_group_id`, and set each term's match type (EXACT unless the user chose otherwise).
8. Report what was added, where, and anything the API rejected.