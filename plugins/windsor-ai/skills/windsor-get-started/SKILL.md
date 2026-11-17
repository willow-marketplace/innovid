---
name: windsor-get-started
description: Set up Windsor.ai in Claude Code and get to a first answer - sign in to the Windsor.ai MCP server, check which sources are connected, connect the first ad, social, analytics, CRM or finance account, and run a first report. Use when the user has just installed the plugin, says "set up Windsor", "get started", "connect my accounts", or tool calls fail because they are not signed in.
---

# Get started with Windsor.ai

Get the user from a fresh install to a first real answer in as few steps as possible.

## 1. Check the connection
- Call `get_current_user`. If it fails with an authentication error, tell the user to run `/mcp`, select `windsor-ai`, and sign in with their Windsor.ai account (a free account works; sign-up happens in the same browser flow). Retry after they confirm.
- If `/mcp` does not list `windsor-ai`, the plugin is not loaded: ask them to run `/plugin` and check that the Windsor.ai plugin is enabled, then restart Claude Code.

## 2. See what is connected
- Call `get_connectors`. Summarise it by area: paid ads, organic social, web analytics and SEO, CRM, e-commerce, finance and payments, warehouses and files.
- If nothing is connected, ask which platform matters most and continue with step 3.

## 3. Connect the first source
- Call `get_connector_authorization_url` for the chosen connector and give the user the link. For API-key sources, call `get_connector_connect_info` and show the exact steps it returns.
- After they confirm, call `get_connectors` again and name the account that appeared.

## 4. First answer
Offer one concrete first report for what is connected, and run it on a yes:
- Ads: spend, clicks, conversions and cost per conversion by campaign for the last 30 days (`/campaign-report`).
- Organic social: best posts by engagement for the last 30 days (`/social-report`).
- CRM: pipeline by stage and value (`/pipeline-report`).
- E-commerce or finance: revenue by week for the last 90 days (`/revenue-report`).

## Hand-offs
- More sources or a permission error -> /windsor-connect
- Any report -> /windsor-insights
- Changes on a platform -> /windsor-manage