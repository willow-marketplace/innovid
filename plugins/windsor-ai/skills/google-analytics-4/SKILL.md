---
name: google-analytics-4
description: Analyze Google Analytics 4 (GA4) data through Windsor.ai. Use when the user asks about their GA4 website or app analytics — sessions, users, new users, engagement, conversions/key events, bounce/engagement rate, pageviews, or breakdowns by channel, source/medium, campaign, landing page, device, country, or date. Do not use for advertising cost/ROAS (those live in the ad-platform connectors like Google Ads), other analytics products, or changing GA4 settings.
---

# Google Analytics 4 (GA4) via Windsor.ai

Windsor.ai pulls live **read-only** GA4 report data through the Google Analytics
Data API. The connector id is `googleanalytics4`. Pass `googleanalytics4` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This connector is **read-only analytics**: it reports GA4 metrics and dimensions.
It has **no advertising cost/ROAS data** (that lives in the ad-platform
connectors such as Google Ads), and it does **not** change any GA4 setting — there
are no write actions. If asked to create a conversion event, change settings, or
spend data, say that's out of scope here.

Golden rules:

- **Never guess field, property, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first; GA4 has many
  dimensions and metrics with exact API ids.
- Read-only connector; there are no live write actions to run.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which GA4 properties are connected (each has an id
and usually a name). If none is connected, help the user connect:
`get_connector_authorization_url` (or `get_connector_connect_info` for the auth
type and steps) and give them the setup link — don't describe manual dashboard
navigation. If several properties are connected and the request is ambiguous,
ask which one.

## 2. Reading GA4 data

1. Call `get_fields` for `googleanalytics4` to get valid field ids. GA4 splits
   into **dimensions** (e.g. date, `sessionDefaultChannelGroup`, source/medium,
   campaign, landing page, device category, country) and **metrics** (e.g.
   sessions, total/active/new users, `conversions` (key events), engagement
   rate, bounce rate, pageviews, average session duration, event count,
   revenue). Use the exact ids returned, not these examples.
2. If the analysis needs specific controls, call `get_options` for the
   connector's options and valid values.
3. Call `get_data` with the `fields` you need (a mix of dimensions and metrics),
   the target property/`account`(s), and a date range — `date_from`/`date_to`
   (`"2026-02-01"`) or a `date_preset` like `last_7d`, `last_30d`, `this_month`,
   `last_year` (append `T` to include today). Add `filters` for conditions.

Common requests → recipe:

- **Traffic overview / trend:** `date` + sessions/users over a preset range.
- **By channel:** `sessionDefaultChannelGroup` + sessions/conversions; rank in
  your answer.
- **Top source or campaign:** the source/medium or campaign dimension + the
  metric, then rank; return only what the user asked for (e.g. just the channel
  name) if they constrained the output.
- **Conversions:** GA4 "conversions" are key events. Cost-per-conversion or ROAS
  need ad spend, which this connector does not have — say so and point to the
  ad-platform connector.

Watch out: GA4 sampling/thresholding and property timezone can shift numbers;
metric availability depends on the property. If a field the user wants isn't in
`get_fields`, say so rather than substituting a different metric.

## 3. Reference real data, don't fabricate

On an error (unknown field/connector/property), call the relevant discovery tool
and retry with correct ids, or report the specific error. Never invent session
counts, conversion numbers, channel names, or property ids.