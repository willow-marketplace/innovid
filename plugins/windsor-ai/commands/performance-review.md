---
name: performance-review
description: Cross-platform performance review of every connected ad, organic, analytics, CRM and revenue source for a period
---

# /performance-review

Review how the business did across all connected sources, compared with the previous period.

## Instructions

1. Call `get_connectors`. Group the connected accounts into paid ads, organic social, web analytics, CRM, and revenue (e-commerce, payments, accounting). Skip groups with nothing connected.
2. Use the period the user gives, else the last 30 days, and compare it with the previous period of the same length.
3. For each connector, read field ids with `get_fields` and call `get_data` for a small field set: ads (spend, clicks, conversions, conversion value), organic (reach, engagement, follower growth), analytics (sessions, users, conversions), CRM (new deals or opportunities, pipeline value, won value), revenue (orders or invoices and revenue).
4. Report one section per group: the headline numbers, the change against the previous period in percent, and the top and bottom item (campaign, post, channel, deal stage or product). State currencies and never add up different ones.
5. End with three findings that deserve action, each with the numbers behind it. Offer `/wasted-spend` if ad efficiency dropped, but do not change anything from this command.