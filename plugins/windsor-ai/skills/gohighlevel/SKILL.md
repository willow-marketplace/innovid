---
name: gohighlevel
description: Analyze GoHighLevel CRM data through Windsor.ai. Use when the user asks about their GoHighLevel account — contacts, opportunities and pipeline stages, deal value, appointments, campaigns, lead sources, or conversion rates. It can also create or update a contact and create an invoice, always after the user confirms the exact change. It does not send emails or SMS, book or change appointments, or modify pipeline or campaign settings. Do not use for other CRMs (HubSpot, Salesforce), outbound messaging, or ad-platform performance data.
---

# GoHighLevel via Windsor.ai

Windsor.ai pulls live GoHighLevel CRM data — contacts, opportunities, pipeline
stages, deal value, appointments, campaigns, lead sources, and conversion
rates. The connector id is `gohighlevel`. Pass `gohighlevel` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

Reads are the main job. The connector also exposes write actions, currently
`create_contact`, `update_contact`, and `create_invoice` (the live set is
whatever `list_actions` returns). It does **not** send emails or SMS, book or
modify appointments, or change pipeline or campaign settings; say those are
out of scope here.

Golden rules:

- **Never guess field, account, or option names** — get them from `get_fields`,
  `get_connectors`, and `get_options`. Call those first.
- **Writes change live CRM data.** Read first, show the exact proposed
  change, and call `execute_action` only after an explicit yes.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which GoHighLevel accounts (locations) are
connected (each has an id and usually a name). If none is connected, help the
user connect: call `get_connector_authorization_url` (or
`get_connector_connect_info` for the auth type and steps) and give them the
setup link — don't describe manual dashboard navigation. If several accounts
are connected and the request is ambiguous, ask which one.

## 2. Reading GoHighLevel data

1. Call `get_fields` for `gohighlevel` to get valid field ids — contact
   fields, opportunity and pipeline-stage fields, deal value, appointment
   fields, and campaign or lead-source fields. Use the exact ids returned,
   not these examples.
2. If the analysis needs segmentation or attribution controls, call
   `get_options` to see the connector's options and valid values.
3. Call `get_data` with `fields`, the target `account`(s), and a date range
   (`date_from`/`date_to` or a `date_preset` like `last_7d`, `last_30d`,
   `this_month`; append `T` to include today, e.g. `last_7dT`). Add `filters`
   as needed, e.g. to isolate a pipeline, stage, or campaign.

GoHighLevel specifics to respect:

- **Opportunities and pipeline stages:** an opportunity's stage and the time
  it has spent there depend on whatever stage/date fields `get_fields`
  actually returns. Don't assume a "days in stage" field exists — check first,
  and compute it from the returned dates if needed, noting the assumption.
- **Deal value** is in the account's currency; state that if it's known,
  rather than assuming USD.
- **Campaigns and lead sources:** lead counts and conversion rates are
  computed from whatever fields `get_fields` exposes (e.g. leads and
  conversions by campaign or source) — conversion rate = conversions ÷ leads.
  Note if a rate can't be computed because one side is zero or missing.
- **Appointments** carry a status (e.g. booked, completed, no-show) as
  returned by the connector — report it as-is, don't infer outcomes it
  doesn't state.

If a field the user wants isn't in `get_fields`, say so rather than
substituting a different one.

## 3. Creating or updating contacts and invoices

1. Resolve the target with a `get_data` read (contact name plus id fields) so
   the write points at an id, not a name; ask if several contacts match.
2. Call `list_actions` for `gohighlevel` and build the params from the returned
   schema. Never assume an action or a field exists.
3. Show the concrete change ("Update phone of contact X (id ...) to ...?" or
   the full invoice draft with amount, currency, and customer) and wait for an
   explicit yes.
4. Call `execute_action` once and report the result. Updates overwrite the
   existing field values; invoices are issued to a customer, so never create
   one with placeholder amounts.
5. If the action is refused because write actions are not enabled for the
   account, send the user to https://onboard.windsor.ai/app/settings/account
   and retry after they confirm.

## 4. Reference the user's data, don't fabricate

On an error (unknown field/connector/account), call the relevant discovery
tool (`get_fields`, `get_connectors`) and retry with correct ids, or report
the specific error. Never invent contact counts, deal values, appointment
statuses, or campaign names.