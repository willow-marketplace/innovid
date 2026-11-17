---
name: ecommerce-insights
description: Report, rank, compare, and trend e-commerce performance across Shopify, Amazon Seller Central, WooCommerce, BigCommerce, Klaviyo, and Google Merchant Center through Windsor.ai. Use when the user asks about orders, revenue, average order value, units, refunds, top products, listing and product-feed status, and email-flow performance. Do not use to change anything, to connect an account, or to schedule exports - route those to the sibling skills.
---

# E-commerce insights

Read live e-commerce data from Shopify, Amazon Seller Central, WooCommerce, BigCommerce, Klaviyo, and Google Merchant Center through Windsor.ai and turn it
into a clear report, ranking, comparison, or trend. The main connector ids for this area are shopify, amazon_sp (Amazon Seller Central), woocommerce, bigcommerce, klaviyo, google_merchant. Other sources in the same category work the same way; find their ids with get_connectors.

## Stay in scope
- Use this skill for metrics, rankings, comparisons, trends, and breakdowns of orders, revenue, average order value, units, refunds, top products, listing and product-feed status, and email-flow performance across store or marketplace account, order, product or listing, and flow.
- If the user asks to pause, enable, change a budget or bid, or update a record, use /ecommerce-manage instead.
- If the user asks to connect, reconnect, or add an account, use /windsor-connect instead.
- If the user asks for a recurring, scheduled, or automated export, use /windsor-export instead.

## Resolve scope and time
- Call get_connectors first to see which of the six sources have connected accounts. If several accounts match the request, ask which one.
- Read field ids from get_fields for the chosen connector, and get_options for date-filter columns and connector options. Never guess ids or field names.
- Use the date range the user gave. If none, use a sensible preset (last_30d, this_month, last_7d), and state which one you used. Append T to a preset to include today.

## Choose the smallest useful read
- Sales overview -> fields [date, orders, revenue] over the preset; add average order value = revenue / orders.
- Top products -> fields [product, units, revenue], rank by the metric the user names.
- Listing health -> Merchant Center or Amazon fields for status and disapprovals; report counts and reasons.
- Flow performance -> Klaviyo fields [flow, sent, opens, clicks, revenue] over the preset.
- Keep field sets small and request only the accounts you need. Check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- Revenue is in the store currency; state it. Refunds may be reported separately; say whether figures are gross or net.
- Marketplace and store data are not deduplicated across sources; report per source unless asked otherwise.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.
- If "best", "top", or "worst" lacks a metric, ask which metric should define the ranking.

## Answer with evidence
Lead with the requested result, then state the account or source, the time window, and the numbers behind the conclusion. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
After a report that surfaces an underperformer, offer once to open /ecommerce-manage for it. Never offer plans, upgrades, or pricing. Never repeat an offer the user declined.