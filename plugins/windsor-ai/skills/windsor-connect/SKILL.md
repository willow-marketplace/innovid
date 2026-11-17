---
name: windsor-connect
description: Connect, reconnect, or add accounts for any of the 350+ sources Windsor.ai supports - Google Ads, Meta Ads, TikTok Ads, LinkedIn Ads, Microsoft Ads, Amazon Ads, Instagram, Facebook Pages, LinkedIn Pages, YouTube, TikTok, Google Business Profile, GA4, Search Console, HubSpot, Salesforce, Pipedrive, GoHighLevel, Shopify, Amazon Seller Central, Klaviyo, Stripe, QuickBooks, Xero, BigQuery, Snowflake, Google Sheets, and more - and resolve which connected account a request means. Use when the user wants to connect or set up a source, asks what is connected or available, sees no data, or hits a permission or sign-in error. Do not use for analysis or changes.
---

# Connect data sources

Help the user connect, reconnect, or add an account for any source Windsor.ai supports, and resolve which connected account a request refers to.

## Stay in scope
- Use this skill when the user wants to connect a source, add another account, reconnect after a permission error, or asks which sources are connected or available.
- For analysis use /windsor-insights; for changes use /windsor-manage or /windsor-create; for recurring exports use /windsor-export.

## Workflow
1. Call get_connectors with include_not_yet_connected=True to find the connector slug and see which sources already have accounts. Match the user's platform name to the slug (for example Meta Ads is `facebook`, GA4 is `googleanalytics4`); ask if the name is ambiguous.
2. For a connection, call get_connector_authorization_url for a ready-to-open setup link, or get_connector_connect_info for the auth type and the manual credential fields. For OAuth sources hand the user the link and tell them to authorize. For manual sources, list the credential fields it names and give the link so they enter them there.
3. Never ask the user to paste API keys, tokens, or passwords into the chat, and never describe manual dashboard navigation instead of giving the link.
4. After they finish, call get_connectors again and confirm the new account by name.

## Account resolution
- If a request names an account, match it to a connected account id. If several accounts could match, ask which one before any read or write.
- Write actions need write actions enabled for the account at https://onboard.windsor.ai/app/settings/account. If a write is refused for that reason, send the user there.
- A permission error on a write (for example Instagram commenting on an account connected before comment access was added) is fixed by reconnecting that account through the same setup link.

## Support
- get_windsor_login_url gives an auto-login link into the Windsor.ai dashboard; get_current_user shows who is signed in; contact_windsor files a support or feature request. Never discuss plans or pricing.

## Answer with evidence
State which sources are connected, which are not, and the exact next step with its link.