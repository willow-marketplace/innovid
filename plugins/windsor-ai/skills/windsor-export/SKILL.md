---
name: windsor-export
description: Set up and review recurring exports and syncs of data from any source connected to Windsor.ai (ads, organic social, analytics, CRM, e-commerce, finance) to warehouses, databases, spreadsheets, BI tools, or cloud storage - BigQuery, Snowflake, Redshift, PostgreSQL, MySQL, Google Sheets, Looker Studio, Amazon S3, and more. Use for scheduled, daily, hourly, weekly, or automated exports, data pipelines, and reporting feeds, and to check the status of existing export tasks. Do not use for a one-off answer in the conversation.
---

# Export connected data on a schedule

Set up recurring exports of connected data to a warehouse, database, spreadsheet, or cloud storage through Windsor.ai, for example daily ad spend to BigQuery, a weekly GA4 summary to Google Sheets, or Shopify orders to Snowflake.

## Stay in scope
- Use this skill for recurring, scheduled, daily, weekly, or automated exports and syncs, and for questions about existing export tasks.
- A one-off answer is /windsor-insights, not an export.

## Workflow
1. Call get_destination_tasks to see existing exports and avoid duplicates.
2. Call get_connectors to confirm the source connector and accounts to export.
3. Call get_destinations to list destinations; note which have create_in_chat true and any reusable credentials.
4. Call get_destination_setup_info for the chosen destination to get its target fields, allowed schedules, and credential_id options.
5. Call get_fields for the source connector to get the exact field ids to export.
6. Confirm source, accounts, fields, destination target, credential, and schedule with the user, then call create_destination_task once. If create_in_chat is false, hand the user the setup_url instead.

## Rules
- Prefer a rolling date_preset (for example last_7d) over fixed dates so each run stays fresh.
- Never put secrets in config; sensitive values are entered by the user in the setup_url form.
- Report the created task's id, schedule, and manage_url from the tool result.

## Hand-offs
- Reporting -> /windsor-insights. Connecting a source -> /windsor-connect. Changing platform state -> /windsor-manage.