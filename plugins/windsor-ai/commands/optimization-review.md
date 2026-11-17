---
name: optimization-review
description: Read-only review of every connected ad account that ends in a numbered list of proposed changes; safe to run as a scheduled task because it never applies anything
---

# /optimization-review

Review connected ad accounts for the last 7 days against the previous 7 (or the range given) and propose changes. Never call `execute_action` in this command, even if write actions are enabled and even when running unattended.

1. Call `get_connectors` and list the ad accounts. If there are more than five accounts or several clients, delegate the review to the portfolio-analyst agent.
2. For each account apply the windsor-optimize checks: spend with zero conversions, cost per conversion more than twice the account average, and, for Google Ads, search terms worth excluding. Also flag campaigns limited by budget that convert below the account's average cost, where impression-share fields exist in `get_fields`.
3. Output one numbered list across all accounts: account, item, evidence (spend, conversions, cost per conversion), proposed action, expected effect. State the currency for each account.
4. End with: "Reply with the numbers to apply (for example: apply 1 3) and I'll run them through /windsor-manage, confirming each one."