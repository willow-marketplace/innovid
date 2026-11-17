---
name: windsor-manage
description: Make confirmed changes on platforms connected to Windsor.ai - pause or enable campaigns, ad sets, ad groups, and ads and set budgets and bids on Google Ads, Meta Ads, TikTok Ads, LinkedIn Ads, Microsoft Ads, and Amazon Ads; push negative keywords and manage Customer Match lists on Google Ads; cut wasted spend on underperforming campaigns; hide, unhide, reply to, or delete Instagram comments; edit Google Business Profile listings, hours, and posts and reply to reviews; update or delete Salesforce records and update GoHighLevel contacts; set Klaviyo flow status; update Amazon Seller Central listings. Use when the user wants to pause, enable, stop, resume, set, raise, lower, fix, optimize, update, or delete something. Always read first, confirm the exact change, then execute. Do not use for reporting only.
---

# Manage connected platforms

Make confirmed changes on connected accounts through Windsor.ai. Write actions currently exist on Meta Ads (facebook), Google Ads (google_ads), TikTok Ads (tiktok), LinkedIn Ads (linkedin), Microsoft Ads (bing), Amazon Ads (amazon_ads), Instagram (instagram), Facebook Pages (facebook_organic), LinkedIn Pages (linkedin_organic), Threads (threads), Google Business Profile (google_my_business), Klaviyo (klaviyo), Amazon Seller Central (amazon_sp), Salesforce (salesforce), GoHighLevel (gohighlevel), Stripe (stripe), WordPress (wordpress). The exact set is whatever list_actions returns for the connector; it changes over time, so never assume an action exists.

## Non-negotiable contract
- Interpret pause, enable, stop, resume, set, raise, lower, update, and change as a write request for this skill.
- Every write follows read, propose, confirm, execute. Never call execute_action without an explicit yes to the specific change.
- Do not improvise a write with a different tool. If list_actions has no matching action, say so and offer the dashboard link from get_windsor_login_url.
- One change per confirmation. For a batch, list every item and get one explicit approval for the exact list.

## Workflow
1. Call get_connectors to find the connector slug and account; ask if several accounts match.
2. Resolve the target with a get_data read (name plus id fields) so the change points at an id, not a name.
3. Call list_actions for the connector and build the params from the returned JSON schema.
4. Show the concrete change, for example "Pause campaign 'Summer Sale' (id ...) on account ...?" or "Set daily budget for campaign X to 50.00 (50000000 micros)?", and ask for confirmation.
5. After an explicit yes, call execute_action once and report the result, including anything the platform returned.

## Rules that prevent wrong writes
- Deletes (a Salesforce record, an Instagram comment, a Google Ads Customer Match list) cannot be undone; name the item and its id in the confirmation, and prefer hiding over deleting a comment unless the user asks to delete.
- Budgets, bids, and targets are absolute values, not deltas. To raise a budget by 20 percent, read the current value, compute the new absolute amount, and set that.
- Google Ads money is in micros (50.00 = 50000000). Convert the user's amount and show both in the confirmation.
- If the user says "pause the worst one", first run the read and ranking (or hand it to /windsor-insights), then name the specific item and confirm before writing.
- If execute_action reports that write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account to turn them on, then retry after they confirm.
- If it reports a missing permission (for example Instagram comments on an account connected before comment access existed), hand off to /windsor-connect to reconnect the account.

## Hand-offs
- Reporting, rankings, comparisons, trends -> /windsor-insights
- Creating or publishing something new (confirmed) -> /windsor-create
- Connecting, reconnecting, adding an account, or asking what is connected -> /windsor-connect
- Recurring exports and syncs -> /windsor-export

## Answer with evidence
State what changed, on which account and item, and the value before and after. Never claim a change succeeded without the execute_action result.