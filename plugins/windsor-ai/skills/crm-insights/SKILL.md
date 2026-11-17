---
name: crm-insights
description: Report, rank, compare, and trend crm performance across HubSpot, Salesforce, Pipedrive, GoHighLevel, Zoho CRM, and ActiveCampaign through Windsor.ai. Use when the user asks about contacts, companies, deals and opportunities, pipeline stage, deal value, win rate, owners, activities, and campaign or email performance. Do not use to change anything, to connect an account, or to schedule exports - route those to the sibling skills.
---

# CRM insights

Read live crm data from HubSpot, Salesforce, Pipedrive, GoHighLevel, Zoho CRM, and ActiveCampaign through Windsor.ai and turn it
into a clear report, ranking, comparison, or trend. The main connector ids for this area are hubspot, salesforce, pipedrive, gohighlevel, zoho, activecampaign. Other sources in the same category work the same way; find their ids with get_connectors.

## Stay in scope
- Use this skill for metrics, rankings, comparisons, trends, and breakdowns of contacts, companies, deals and opportunities, pipeline stage, deal value, win rate, owners, activities, and campaign or email performance across pipeline, deal or opportunity, contact, company, and campaign.
- If the user asks to pause, enable, change a budget or bid, or update a record, use /crm-manage instead.
- If the user asks to connect, reconnect, or add an account, use /windsor-connect instead.
- If the user asks for a recurring, scheduled, or automated export, use /windsor-export instead.

## Resolve scope and time
- Call get_connectors first to see which of the six sources have connected accounts. If several accounts match the request, ask which one.
- Read field ids from get_fields for the chosen connector, and get_options for date-filter columns and connector options. Never guess ids or field names.
- Use the date range the user gave. If none, use a sensible preset (last_30d, this_month, last_7d), and state which one you used. Append T to a preset to include today.

## Choose the smallest useful read
- Pipeline by stage -> fields [pipeline, stage, deal count, amount] filtered to open deals; state the currency.
- Win rate -> closed-won / (closed-won + closed-lost) over the date range; say which date field defines the period.
- Email performance -> fields [campaign, sent, opens, clicks, date] over the preset.
- New contacts -> fields [created date, owner, source] filtered to the period.
- Keep field sets small and request only the accounts you need. Check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- Deal amounts are in the CRM's currency; state it. Stage names differ per CRM; use the ones returned, not generic labels.
- Do not merge records across CRMs by name; report per source unless the user asks for a cross-CRM view and accepts the caveat.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.
- If "best", "top", or "worst" lacks a metric, ask which metric should define the ranking.

## Answer with evidence
Lead with the requested result, then state the account or source, the time window, and the numbers behind the conclusion. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
After a report that surfaces an underperformer, offer once to open /crm-manage for it. Never offer plans, upgrades, or pricing. Never repeat an offer the user declined.