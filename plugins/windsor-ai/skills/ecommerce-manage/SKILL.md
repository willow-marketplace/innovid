---
name: ecommerce-manage
description: Make confirmed changes on Shopify, Amazon Seller Central, WooCommerce, BigCommerce, Klaviyo, and Google Merchant Center through Windsor.ai - update Amazon product listings; create Klaviyo flows and set a flow's status; other sources expose write actions only if list_actions returns them. Use when the user wants to pause, enable, set, or update something. Always read first, confirm the exact change, then execute. Do not use for reporting only.
---

# Manage e-commerce

Make confirmed changes on connected e-commerce accounts through Windsor.ai:
update Amazon product listings; create Klaviyo flows and set a flow's status; other sources expose write actions only if list_actions returns them. The main connector ids for this area are shopify, amazon_sp (Amazon Seller Central), woocommerce, bigcommerce, klaviyo, google_merchant. Other sources in the same category work the same way; find their ids with get_connectors.

## Non-negotiable contract
- Interpret pause, enable, stop, resume, set, raise, lower, update, and change as a write request for this skill.
- Every write follows read, propose, confirm, execute. Never call execute_action without an explicit yes to the specific change.
- The set of actions is whatever list_actions returns for the connector. Do not assume an action exists, and do not improvise a write with a different tool.
- One change per confirmation. For a batch, list every item and get one explicit approval for the exact list.

## Workflow
1. Resolve the target with a get_data read (name plus id fields) so the change points at an id, not a name. If several items match, ask which one.
2. Call list_actions for the connector and build the params from the returned JSON schema.
3. Show the concrete change and ask for confirmation, for example "Pause campaign 'Summer Sale' (id ...) on account ...?" or "Set daily budget for campaign X to 50.00 (50000000 micros)?".
4. After an explicit yes, call execute_action once and report the result, including anything the platform returned.

## Rules that prevent wrong writes
- Resolve the exact listing or flow with a get_data read before proposing a change.
- Show the current value, the proposed value, and the target account in the confirmation; write only after an explicit yes.
- Listing edits can affect live storefront content; say so in the confirmation.
- If the user says "pause the worst one", first run the read and ranking (or hand the ranking to /ecommerce-insights), then name the specific item and confirm before writing.
- If execute_action reports that write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account to turn them on, then retry after they confirm.

## Stay in scope
- Reporting without a change -> /ecommerce-insights.
- Creating a Klaviyo flow follows the same read, propose, confirm, execute steps in this skill; keep it in draft unless the user asks for live.
- Connecting or reconnecting an account -> /windsor-connect.

## Answer with evidence
State what changed, on which account and item, and the value before and after. Never claim a change succeeded without the execute_action result.