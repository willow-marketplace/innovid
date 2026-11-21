---
name: windsor-insights
description: Report, rank, compare, and trend data from any of the 350+ sources connected to Windsor.ai - paid ads (Google Ads, Meta Ads, TikTok Ads, LinkedIn Ads, Microsoft Ads, Amazon Ads, Pinterest Ads, Snapchat Ads), organic social (Instagram, Facebook Pages, LinkedIn Pages, TikTok, YouTube, Threads, Google Business Profile), web analytics and SEO (GA4, Google Search Console, Adobe Analytics, Mixpanel, Amplitude), CRM (HubSpot, Salesforce, Pipedrive, GoHighLevel, Zoho CRM), e-commerce and email (Shopify, Amazon Seller Central, WooCommerce, Google Merchant Center, Klaviyo), finance and payments (Stripe, QuickBooks, Xero, PayPal), and data warehouses. Use for spend, ROAS, CPA, clicks, conversions, reach, engagement, followers, sessions, search rankings, pipeline, deals, orders, revenue, invoices, or any other metric, for cross-channel comparisons and weekly reviews, and when the user asks what Windsor.ai can do. Do not use to change anything, to connect an account, or to schedule exports.
---

# Windsor.ai insights

Read live data from any connected source through Windsor.ai and turn it into a clear report, ranking, comparison, or trend. The Windsor.ai server is not scoped to one platform: every tool takes a `connector` slug (for example `google_ads`, `facebook` for Meta Ads, `googleanalytics4`, `shopify`, `stripe`), so discover the slug and the accounts before reading.

## Resolve scope and time
- Call get_connectors first. It lists connected accounts per connector with their options and supported actions. If the source is not connected, hand off to /windsor-connect instead of guessing. If several accounts match, ask which one, unless the user asked about all accounts, all clients, every brand or location, or a named group of more than five accounts. In that case delegate to the portfolio-analyst agent.
- Read field ids from get_fields for the chosen connector, and get_options for date-filter columns and connector options. Never guess connector slugs, account ids, field ids, or option ids.
- Use the date range the user gave. If none, use a sensible preset (last_30d, this_month, last_7d) and state which one you used. Append T to a preset to include today.

## Choose the smallest useful read
- Channel or source comparison -> one get_data call per connector with a small shared field set (date plus the metric asked for), then compare in the answer and say the numbers are platform-reported.
- Best or worst item -> the entity field (campaign, post, page, product, customer) plus the ranking metric; name the metric used. If "best" or "worst" has no metric, ask which one.
- Trend -> date plus the metric over the preset; report direction and magnitude.
- Efficiency -> compute from the raw fields (cost per conversion = spend / conversions; ROAS = conversion value / spend; engagement rate = engagements / reach) and say when a denominator is zero.
- Keep field sets small, request only the accounts you need, and check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- Money fields are in the account currency; state it. On Google Ads only fields whose id ends in `_micros` are in micros (1,000,000 = 1.00); `spend`, `cost`, `cpc` and `cost_per_conversion` are already in currency units.
- Conversions can be fractional. Do not round them into integers silently.
- Attribution windows and metric definitions differ per platform; do not present cross-platform numbers as like for like.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.

## Platform notes
- Google Ads: `spend`, `cost`, `cpc`, `cost_per_conversion` and `budget_amount` are in the account currency; only fields whose id ends in `_micros` are in micros (1,000,000 = 1.00). Search terms cannot be requested together with keyword fields, so read them in separate calls.
- Meta Ads, Facebook Pages, Instagram: reach and other unique metrics are deduplicated over the period; use `year_week_iso` or `year_month` for weekly or monthly totals instead of summing daily rows (the plain `week` and `month` fields merge years). Facebook Pages returns one broken-down metric per request.
- GA4: conversions are key events; there is no cost data, so join ad spend from the ad connectors for ROAS.
- Search Console: data lags about two to three days; position is an average (lower is better) and CTR is clicks / impressions.
- CRM (HubSpot, Salesforce, Pipedrive, GoHighLevel): deal value is in the account currency; stage and close-date fields differ per CRM, so read them from get_fields.
- E-commerce and finance: a store (Shopify, Amazon) and an accounting system (QuickBooks, Xero) count the same sales twice; report them separately and keep currencies apart.

## Answering "what can you do"
Call get_connectors with include_not_yet_connected=True and answer from it: which sources are connected, which are available to connect, and per connector which options and write actions exist. Do not list capabilities the tool result does not show. Reads are available on the free plan.

## Answer with evidence
Lead with the requested result, then state the source and account, the time window, and the numbers behind the conclusion. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
- Confirmed changes (pause, enable, budgets, bids, audiences, flows, listing updates) -> /windsor-manage
- Creating or publishing something new (confirmed) -> /windsor-create
- Connecting, reconnecting, adding an account, or asking what is connected -> /windsor-connect
- Recurring exports and syncs -> /windsor-export
- All clients, many accounts at once, or more than five accounts on a platform -> portfolio-analyst agent
- Joining CRM, store, or accounting records to ad spend -> attribution-analyst agent
After a report that surfaces an underperformer, offer once to open /windsor-manage for it. Never offer plans, upgrades, or pricing.