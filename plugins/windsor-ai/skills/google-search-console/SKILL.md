---
name: google-search-console
description: Analyze Google Search Console (organic search) data through Windsor.ai. Use when the user asks about their site's organic Google Search performance — clicks, impressions, CTR, or average position, broken down by search query, page/URL, country, device, search appearance, or date. Do not use for paid search / Google Ads cost, other webmaster tools (e.g. Bing), web analytics like GA4, or changing Search Console settings.
---

# Google Search Console via Windsor.ai

Windsor.ai pulls live **read-only** Google Search Console data through the Search
Analytics API. The connector id is `searchconsole`. Pass `searchconsole` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This connector is **read-only** organic-search analytics. It reports Search
Console metrics and dimensions; it does **not** cover paid search or ad cost
(that lives in the Google Ads connector), and it does **not** submit sitemaps or
change any Search Console setting — there are no write actions. If asked to
submit a sitemap or change settings, say that's out of scope here.

Golden rules:

- **Never guess field, site, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- Read-only connector; there are no live write actions to run.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which Search Console sites/properties are connected
(each has an id and usually a name). If none is connected, help the user connect:
`get_connector_authorization_url` (or `get_connector_connect_info` for the auth
type and steps) and give them the setup link — don't describe manual dashboard
navigation. If several sites are connected and the request is ambiguous, ask
which one.

## 2. Reading Search Console data

1. Call `get_fields` for `searchconsole` to get valid field ids. Search Console
   splits into **dimensions** (e.g. query, page/URL, country, device, search
   appearance, date) and **metrics** (clicks, impressions, CTR, average
   position). Use the exact ids returned, not these examples.
2. Call `get_data` with the `fields` you need (dimensions + metrics), the target
   site/`account`(s), and a date range — `date_from`/`date_to` (`"2026-02-01"`)
   or a `date_preset` like `last_7d`, `last_28d`, `last_30d`, `this_month`.
   Add `filters` for conditions (e.g. branded vs non-branded queries, a country).

Common requests → recipe:

- **Search performance overview / trend:** `date` + clicks/impressions/CTR/
  position over a preset range.
- **Top queries or pages:** the query or page dimension + clicks/impressions,
  then rank in your answer.
- **By country/device:** that dimension + the metrics.
- Position is an **average position** (lower is better); CTR is clicks ÷
  impressions. Compute derived figures from returned fields.

Watch out: Search Console data typically lags by **~2–3 days** (very recent days
may be missing or partial) and history is limited to about **16 months**. It is
**organic** search only — there is no cost/spend data. If a field the user wants
isn't in `get_fields`, say so rather than substituting a different metric.

## 3. Reference real data, don't fabricate

On an error (unknown field/connector/site), call the relevant discovery tool and
retry with correct ids, or report the specific error. Never invent click counts,
positions, query strings, or site ids.