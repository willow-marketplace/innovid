---
name: google-merchant-center
description: Analyze Google Merchant Center product feed and Shopping data through Windsor.ai. Use when the user asks about their Merchant Center product feed — product status, disapprovals and issues, availability, clicks, impressions, CTR, or price benchmarks by product, brand, or category. This connector is read-only — it has no write actions and cannot edit the product feed. Do not use for Google Ads Shopping campaign spend or ROAS (those live in the Google Ads connector), other platforms (e.g. Amazon), or fixing/updating feed data.
---

# Google Merchant Center via Windsor.ai

Windsor.ai pulls Google Merchant Center product feed and Shopping data. The
connector id is `google_merchant`. Pass `google_merchant` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This connector is **read-only** product feed and Shopping analytics. It has
**no Google Ads Shopping campaign data** (spend and ROAS live in the Google
Ads connector, so route those questions there), and it does **not** edit the
product feed or fix disapproved products — there are no write actions. If
asked to change GTINs, prices, or other feed data, say that's out of scope
here.

Golden rules:

- **Never guess field, account, or option names** — get them from
  `get_fields`, `get_connectors`, and `get_options`. Call those first.
- Read-only connector; there are no live write actions to run.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which Merchant Center accounts are connected
(each has an id and, usually, a name). If none is connected, help the user
connect one: call `get_connector_authorization_url` (or
`get_connector_connect_info` for the auth type and steps) and give them the
setup link — do not describe manual dashboard navigation.

If the user names an account, match it to a connected account id. If several
are connected and the request is ambiguous, ask which account.

## 2. Reading Merchant Center data

1. Call `get_fields` for `google_merchant` to get valid field ids — product
   and feed attributes (product status, disapproval reasons/issues,
   availability, price, price benchmark, brand, category) and Shopping
   performance metrics (clicks, impressions, CTR). Use the exact ids
   returned, not these examples.
2. If the analysis needs segmentation or attribution controls, call
   `get_options` to see the connector's options and valid values.
3. Call `get_data` with:
   - `fields`: the ids you actually need (keep the set small).
   - `accounts`: the target account id(s); omit for all connected accounts.
   - A date range: either `date_from`/`date_to` (`"2026-02-01"`), or
     `date_preset` — `last_7d`, `last_30d`, `this_month`; append `T` to
     include today (e.g. `last_7dT`, `this_monthT`).
   - `filters`: optional conditions, e.g. filtering to disapproved products or
     to a price-benchmark threshold; combine with `"and"`/`"or"` as needed.

Merchant Center specifics to respect:

- Feed/status attributes (approval status, disapproval reasons, availability,
  price, price benchmark) describe the **current state of the feed**, while
  clicks, impressions, and CTR are **per-day performance metrics** over the
  requested range. Don't present a current feed attribute as if it varied
  over a historical date range.
- When surfacing disapprovals, include the issue/reason field alongside the
  product so the user knows what to fix — even though this connector can't
  fix it for them.

Common requests → recipe:

- **Disapproved products:** fields for product, status, and disapproval
  issues; filter to the disapproved status.
- **High impressions, low CTR:** fields for product, impressions, clicks
  (or CTR), over a date range; rank by impressions and CTR in your answer.
- **Price benchmarking:** fields for product price and price benchmark;
  compare and flag products priced above or below benchmark.

If a field the user wants isn't in `get_fields`, say so rather than
substituting a different metric. There is no ad spend or campaign data here.

## 3. Reference real data, don't fabricate

On an error (unknown field/connector/account), call the relevant discovery
tool and retry with correct ids, or report the specific error. Never invent
product statuses, disapproval reasons, click or impression counts, or price
figures.