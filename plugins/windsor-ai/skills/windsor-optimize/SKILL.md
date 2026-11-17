---
name: windsor-optimize
description: Find and fix wasted ad spend across Google Ads, Meta Ads, TikTok Ads, LinkedIn Ads, Microsoft Ads and Amazon Ads through Windsor.ai - spend with no conversions, campaigns and ad sets far above the account's cost per conversion, non-converting search terms, and budgets on paused-worthy items. Use when the user asks where money is wasted, how to cut spend, or to optimize campaigns. Proposes changes and applies them only after explicit approval.
---

# Optimize ad spend

Find spend that is not paying off, show the evidence, and apply the fixes the user approves.

## 1. Scope
- Call `get_connectors` and list the connected ad accounts. Ask which accounts to include if there are several; default to all. If there are more than five accounts or several clients, delegate the read to the portfolio-analyst agent instead of reading them all here.
- Use the last 30 days unless the user gives a range. State the currency per account and never sum across currencies.

## 2. Find waste, per platform
For each ad connector, read field ids from `get_fields` first, then call `get_data` with campaign (and ad set or ad group) name and id, spend, clicks, impressions and conversions.
- **Zero-conversion spend:** items with spend above zero and no conversions, sorted by spend.
- **Expensive conversions:** items whose cost per conversion (spend / conversions) is more than twice the account average; show both numbers.
- **Search terms (Google Ads):** hand off to `/search-terms` for the negative keyword flow.
- Ignore items with too little data to judge (for example under 1,000 impressions) and say so.

## 3. Propose
Present one table per account: item, spend, conversions, cost per conversion, suggested action (pause, lower budget by an absolute amount, or add negatives), and the expected saving. Never propose touching brand campaigns or items the user marked as intentional.

## 4. Apply only what is approved
- Wait for an explicit yes to the exact list. Partial approval is fine.
- Call `list_actions` for each connector and build params from the returned schema (`pause_campaign`, `pause_adset`, `set_campaign_budget` and similar; the live set is what `list_actions` returns). Google Ads budgets are in micros; show both values.
- Call `execute_action` once per approved change and report what changed, before and after, and anything the platform rejected.
- If write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account and retry after they confirm.

## Hand-offs
- Reporting only -> /windsor-insights
- Creating replacement campaigns or ads -> /windsor-create
- More than five accounts or several clients at once -> portfolio-analyst agent