---
name: pacing
description: Month-to-date ad spend against budget for every connected account, client or brand, with the ones projected to over- or under-spend
---

# /pacing

1. Call `get_connectors` and list ad accounts on Google Ads, Microsoft Ads, Meta Ads, TikTok Ads, LinkedIn Ads and Amazon Ads.
2. Get budgets from the user, from a connected Google Sheet they name, or else use last month's total spend for that account as the reference, and say which you used.
3. For more than five accounts or several clients, delegate to the portfolio-analyst agent with the period set to month to date.
4. Projected month spend = spend to date / days elapsed x days in month. Flag accounts projected above 110% or below 90% of budget.
5. Output a table: client or brand | account | budget | spend to date | projected | % of budget | flag. Keep currencies separate. Do not change budgets from this command; offer /windsor-manage for any change the user wants.