---
name: web-analytics-insights
description: Report, rank, compare, and trend web analytics performance across Google Analytics 4 and Google Search Console through Windsor.ai. Use when the user asks about sessions, users, engagement rate, conversions, and revenue from GA4; clicks, impressions, CTR, and average position from Search Console. Do not use to change anything, to connect an account, or to schedule exports - route those to the sibling skills.
---

# Web Analytics insights

Read live web analytics data from Google Analytics 4 and Google Search Console through Windsor.ai and turn it
into a clear report, ranking, comparison, or trend. The main connector ids for this area are googleanalytics4 (GA4), searchconsole (Google Search Console). Other sources in the same category work the same way; find their ids with get_connectors.

## Stay in scope
- Use this skill for metrics, rankings, comparisons, trends, and breakdowns of sessions, users, engagement rate, conversions, and revenue from GA4; clicks, impressions, CTR, and average position from Search Console across property or site, page, channel, and query.
- If the user asks to connect, reconnect, or add an account, use /windsor-connect instead.
- If the user asks for a recurring, scheduled, or automated export, use /windsor-export instead.

## Resolve scope and time
- Call get_connectors first to see which of the six sources have connected accounts. If several accounts match the request, ask which one.
- Read field ids from get_fields for the chosen connector, and get_options for date-filter columns and connector options. Never guess ids or field names.
- Use the date range the user gave. If none, use a sensible preset (last_30d, this_month, last_7d), and state which one you used. Append T to a preset to include today.

## Choose the smallest useful read
- Traffic comparison -> GA4 fields [date, sessions, users, conversions] for both periods; compare in the answer.
- Top queries -> Search Console fields [query, clicks, impressions, ctr, position], rank by clicks unless told otherwise.
- Landing pages -> GA4 fields [landing page, sessions, engagement rate, conversions].
- Page movers -> Search Console fields [page, clicks] for two periods; report gains and losses.
- Keep field sets small and request only the accounts you need. Check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- GA4 and Search Console count different things (sessions vs clicks); never add them together.
- Search Console data lags about two days and average position is an average of averages; say so when precision matters.
- These sources expose no write actions; never imply a site or property setting can be changed from here.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.
- If "best", "top", or "worst" lacks a metric, ask which metric should define the ranking.

## Answer with evidence
Lead with the requested result, then state the account or source, the time window, and the numbers behind the conclusion. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
After a report the user is likely to want regularly, offer once to set it up as a recurring export with /windsor-export. Never offer plans, upgrades, or pricing. Never repeat an offer the user declined.