---
name: pipeline-report
description: CRM pipeline report by stage, value and owner from HubSpot, Salesforce, Pipedrive, GoHighLevel, Zoho or ActiveCampaign
---

# /pipeline-report

Show where the pipeline stands and what moved.

## Instructions

1. Call `get_connectors` and pick the connected CRM (`hubspot`, `salesforce`, `pipedrive`, `gohighlevel`, `zoho`, `activecampaign`). Ask if several are connected.
2. Read field ids with `get_fields` (deal or opportunity name, stage, amount, owner, created and close dates) and call `get_data` for open deals plus deals closed in the last 30 days.
3. Report open deals by stage with count and total value, deals won and lost in the period with value, the largest open deals, and deals with a close date in the past that are still open. State the currency.
4. If the user asks to update a deal or contact, hand off to the crm-manage skill; nothing is changed from this command.