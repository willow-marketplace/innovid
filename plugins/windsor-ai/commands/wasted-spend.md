---
name: wasted-spend
description: Find ad spend that is not converting across all connected ad platforms and fix it after you approve
---

# /wasted-spend

Run the windsor-optimize workflow across every connected ad account for the last 30 days (or the range the user gives): list zero-conversion spend and conversions costing more than twice the account average, propose pauses, budget cuts or negative keywords with the expected saving, and apply only the changes the user explicitly approves, one `execute_action` per change. For Google Ads search terms, follow `/search-terms`.