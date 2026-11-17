---
name: klaviyo
description: Analyze Klaviyo email and SMS performance and manage flows through Windsor.ai. Use when the user asks about their Klaviyo campaigns, flows, lists, or metrics — opens, clicks, conversions, revenue, unsubscribes — or wants to create a flow or change a flow's status (draft / manual / live). Do not use for other email platforms (Mailchimp, Brevo, …), ad campaigns, or writing email copy.
---

# Klaviyo via Windsor.ai

Windsor.ai exposes live Klaviyo data (read) and flow management (write) through
MCP tools. The connector id is `klaviyo`. Pass `klaviyo` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Golden rules:

- **Never guess field, account, action, or option names** — get them from
  `get_fields`, `get_connectors`, `list_actions`, and `get_options`. Call those
  first.
- **Reads are safe; setting a flow live starts sending real messages.** Only call
  `execute_action` after the user confirms the specific change.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see connected Klaviyo accounts. If none is connected,
help the user connect: `get_connector_authorization_url` (or
`get_connector_connect_info`) and give them the setup link.

## 2. Reading performance

1. `get_fields` for `klaviyo` — campaign, flow, list, and metric fields (opens,
   clicks, conversions, revenue, unsubscribes, added-to-cart, subject, status).
   Use the exact ids returned.
2. `get_data` with the fields, target `account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset`). Add `filters` as needed.
   Recipes: revenue by campaign/flow; open & click rates over time; top flows by
   conversions.

## 3. Managing flows (write actions)

Call `list_actions` first for exact ids/schemas. Klaviyo exposes:

- **`create_flow`** — create a flow from a trigger (metric event or list-add)
  plus a graph of steps (time-delay, send-email referencing an existing
  template, send-sms with inline body, conditional-split). Created in **draft**,
  so nothing sends until activated. Klaviyo rate-limits flow creation (~100/day).
- **`update_flow_status`** — set a flow to `draft` (off), `manual` (profiles
  enter, messages held for review), or `live` (sends automatically).

Write workflow — every time: build the params from the `list_actions` schema,
**show the user the concrete change and confirm** (especially setting a flow
`live`, which begins sending to subscribers), then call `execute_action` and
report the result.

## 4. Reference real data, don't fabricate

On an error, call the relevant discovery tool and retry with correct ids, or
report the specific error. Never invent open rates, revenue, flow ids, or names.