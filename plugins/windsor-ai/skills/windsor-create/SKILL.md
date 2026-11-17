---
name: windsor-create
description: Create and publish new items on platforms connected to Windsor.ai - campaigns, ad groups, and responsive search ads on Google Ads; campaigns, ad sets, ads, and boosted posts on Meta Ads; image, carousel, video (Reels), and story posts and comments on Instagram; posts on Facebook Pages, LinkedIn Pages, Threads, and Google Business Profile; flows on Klaviyo; records on Salesforce; contacts and invoices on GoHighLevel; customers and subscriptions on Stripe; pages and posts on WordPress. Use when the user wants to create, make, build, launch, draft, publish, post, or schedule something new. Collect inputs, show the full draft, and create only after explicit confirmation.
---

# Create on connected platforms

Create new items on connected accounts through Windsor.ai. Create actions currently exist on a subset of Meta Ads (facebook), Google Ads (google_ads), TikTok Ads (tiktok), LinkedIn Ads (linkedin), Microsoft Ads (bing), Amazon Ads (amazon_ads), Instagram (instagram), Facebook Pages (facebook_organic), LinkedIn Pages (linkedin_organic), Threads (threads), Google Business Profile (google_my_business), Klaviyo (klaviyo), Amazon Seller Central (amazon_sp), Salesforce (salesforce), GoHighLevel (gohighlevel), Stripe (stripe), WordPress (wordpress); the available create actions and their required fields are whatever list_actions returns for the connector.

## Non-negotiable contract
- Interpret create, make, build, publish, post, and launch as a creation request for this skill.
- Gather every required input first, show the complete draft, and create only after an explicit yes. Never create with placeholder values.
- Build params from the list_actions schema, not from memory. Create one item per confirmation unless the user approved an explicit list.

## Workflow
1. Call get_connectors to pick the connector and target account; ask if several match.
2. Call list_actions for the connector and read the schema of the create action.
3. Collect the inputs the schema requires, then present the full draft (every field, the account, and what will be live afterwards).
4. After an explicit yes, call execute_action once and report the created item and its state.

## Rules
- Created ad objects start paused where the platform supports it; say so, and offer /windsor-manage to enable them once reviewed.
- Responsive search ads need 3 to 15 headlines, 2 to 4 descriptions, and a final URL; collect them before building params.
- Invoices and subscriptions bill a real customer; never create one with placeholder amounts, and show the amount, currency, and customer in the confirmation.
- Instagram image posts need a publicly reachable image URL and a caption; Google Business Profile posts need the location and the post text. Show the exact text that will be published.
- Never invent budgets, bids, or copy; ask for them, then confirm the exact values. If the user wants creative help, draft it as text for approval first.
- If execute_action reports that write actions are not enabled, send the user to https://onboard.windsor.ai/app/settings/account and retry after they confirm.

## Hand-offs
- Reporting, rankings, comparisons, trends -> /windsor-insights
- Confirmed changes (pause, enable, budgets, bids, audiences, flows, listing updates) -> /windsor-manage
- Connecting, reconnecting, adding an account, or asking what is connected -> /windsor-connect
- Recurring exports and syncs -> /windsor-export

## Answer with evidence
Report exactly what was created, where, and its status, using the execute_action result.