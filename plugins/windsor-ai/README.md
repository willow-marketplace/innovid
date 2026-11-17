# Windsor.ai Plugin for Claude Code

Manage and analyze Google Ads, Meta Ads, Instagram, TikTok Ads, LinkedIn Ads and Microsoft Ads from Claude Code through [Windsor.ai](https://windsor.ai). The same install reads GA4, Search Console, HubSpot, Salesforce, Shopify, Stripe, QuickBooks and 350+ other sources.

## What This Plugin Does

- **Report on ad accounts:** campaigns, ad groups, keywords, search terms, quality score, impression share, spend, conversions and cost per conversion, across one platform or several at once
- **Report on Instagram:** post reach, saves, engagement and follower growth
- **Make changes after you confirm:** pause or enable campaigns, change budgets, set Google Ads bids and bidding strategies, create Google Ads campaigns and responsive search ads, push negative keywords, publish Instagram posts, carousels, reels and stories, and reply to, hide or delete Instagram comments
- **Combine sources:** ad spend next to GA4 sessions, CRM deals or Shopify orders
- **Use the data in code:** seed dashboards, generate fixtures and TypeScript types, build data pipelines

Claude shows every change and waits for your yes before it runs. New Google Ads and Meta Ads campaigns, ad groups, ad sets and ads are created paused by default, and new LinkedIn Ads creatives are created as drafts.

## Installation

```bash
/plugin install windsor-ai@claude-plugin-directory
```

Or install from this repository:

```bash
claude plugin marketplace add https://github.com/windsor-ai/claude-windsor-ai-plugin
claude plugin install windsor-ai
```

## Setup

After installing, you'll be prompted to authenticate with your Windsor.ai account via OAuth. This connects Claude Code to the same data sources you've set up in Windsor.ai.

## Slash Commands

| Command | Description |
|---------|-------------|
| `/campaign-report` | Quick 30-day campaign performance summary |
| `/search-terms` | Google Ads search terms that spend without converting, pushed as negative keywords after you approve |
| `/instagram-report` | Instagram posts from the last 30 days by reach, saves and follows, against the account average |
| `/windsor-sources` | List all connected platforms and available fields |
| `/windsor-types` | Generate TypeScript type definitions for a connector |
| `/setup` | Sign in, connect a first account and run a first report |
| `/performance-review` | Cross-platform review of ads, organic, analytics, CRM and revenue against the previous period |
| `/wasted-spend` | Non-converting spend across all ad platforms, fixed after you approve |
| `/social-report` | Organic results across Instagram, Facebook Pages, LinkedIn Pages, TikTok and YouTube |
| `/pipeline-report` | CRM pipeline by stage, value and owner |
| `/revenue-report` | Weekly revenue, orders and refunds from commerce and finance sources |
| `/optimization-review` | Read-only review of every connected ad account ending in numbered proposals; safe to run unattended |
| `/pacing` | Month-to-date spend against budget for every account, client or brand |

## Skills

Claude loads these automatically when a request matches, or you can call them by name.

| Skill | Use it for |
|-------|------------|
| `/windsor-get-started` | Sign in, see what is connected, connect a first account, first report |
| `/windsor-insights` | Reports, rankings, comparisons and trends from any connected source |
| `/windsor-optimize` | Find and fix wasted ad spend across all ad platforms, with approval |
| `/windsor-manage` | Confirmed changes: pause or enable, budgets, bids, audiences, records, comments |
| `/windsor-create` | Campaigns, ads, posts, flows, CRM records, always after you approve the draft |
| `/windsor-connect` | Connecting or reconnecting accounts and checking what is connected |
| `/windsor-export` | Recurring exports to Sheets, BigQuery, Snowflake and other destinations |
| `/google-ads`, `/facebook-ads`, `/tiktok-ads`, `/linkedin-ads`, `/microsoft-ads` | Paid ads reporting and campaign management |
| `/instagram`, `/facebook-organic`, `/linkedin-pages`, `/tiktok-organic`, `/youtube`, `/google-business-profile` | Organic social and local-listing insights, publishing and comment/review moderation |
| `/instagram-public` | Public/competitor Instagram profile and post lookup |
| `/social-media-insights`, `/social-media-create` | Cross-platform organic reporting and posting |
| `/google-analytics-4`, `/google-search-console`, `/web-analytics-insights` | Web analytics and search performance |
| `/hubspot`, `/gohighlevel`, `/crm-insights`, `/crm-manage` | CRM pipeline, contacts and confirmed record changes |
| `/klaviyo`, `/google-merchant-center`, `/ecommerce-insights`, `/ecommerce-manage` | E-commerce, email flows, product feeds and listings |
| `/finance-insights` | Revenue, payments and accounting (Stripe, QuickBooks, Xero and more) |
| `/business-data` | Windsor.ai data inside code: files, typed data, dashboards |

## Agents

- **Marketing Analyst** handles multi-step performance work for one business or client across ads, organic, analytics and revenue: diagnosing a drop, finding what drives results, weekly reviews with recommended changes.
- **Portfolio Analyst** runs the same review across many accounts at once — every client of an agency, or every brand, site, location or market of one business — and returns one row per entity with flags and proposed changes. Read-only.
- **Attribution Analyst** joins ad spend to analytics, CRM, store and accounting records to show which channels produce leads, customers and revenue, and reports how much of the data actually matched. Read-only.
- **Business Data Analyst** handles developer work: cross-platform data pulls, ETL pipelines and dashboard data integration.

## Supported Data Sources

350+ connectors across marketing, sales, CRM, ecommerce, finance, analytics, and more:

**Ads:** Google Ads · Meta Ads · TikTok Ads · LinkedIn Ads · Microsoft Ads · Amazon Ads · and more

**Social and search:** Instagram · Facebook Pages · TikTok Organic · LinkedIn Pages · Google Search Console · YouTube · Google My Business · Pinterest · Snapchat · Twitter/X · and more

**CRM & Sales:** HubSpot · Salesforce · Pipedrive · Zoho CRM · and more

**Analytics & Data:** Google Analytics 4 · Google Sheets · BigQuery · Snowflake · and more

**Ecommerce & Payments:** Shopify · Stripe · WooCommerce · and more

**Finance & Operations:** QuickBooks · Xero · and more

[Browse all 350+ connectors](https://windsor.ai/connectors/)

## Examples

```
> Which Google Ads search terms spent money last month without a conversion?

> Add "free" and "jobs" as negative keywords to the Brand Search campaign

> Pause every Meta ad set with a cost per purchase above $40 this week

> Which Instagram posts in the last 30 days got the most saves per 1,000 reached?

> Compare spend and ROAS across Google Ads, Meta Ads and TikTok Ads for this month

> Show me HubSpot deals closed this month next to the ad spend by channel

> Pull last 7 days of Google Ads campaign data and write it to src/data/campaigns.json

> Generate TypeScript types for our Salesforce data

> Check all my clients' Google Ads accounts for anything that broke this week

> Which of our brands will overspend this month?

> Cost per new customer by channel last quarter, matching HubSpot deals to QuickBooks invoices
```

## Troubleshooting

- **Sign-in fails or tools stop responding:** clear the Windsor.ai authentication in Claude Code (`/mcp`, select `windsor-ai`, re-authenticate) and sign in again.
- **A connector shows no accounts:** connect the account in Windsor.ai first at [onboard.windsor.ai](https://onboard.windsor.ai), then run `/windsor-sources`.
- **Instagram comments return a permission error:** the account was connected before comment access was added. Reconnect it in Windsor.ai.
- **A field is rejected:** field names differ by connector. Ask Claude to check them with `get_fields`, or run `/windsor-sources`.
- **Anything else:** email [support@windsor.ai](mailto:support@windsor.ai).

## Privacy

The plugin contains no hooks and no local code. Claude Code sends requests to Windsor.ai's MCP server at `mcp.windsor.ai`, which reads data from, and makes approved changes on, the platforms you have connected in Windsor.ai. Windsor.ai processes these requests under its [Privacy Policy](https://windsor.ai/privacy-policy/).

## Links

- [Windsor.ai](https://windsor.ai)
- [Windsor.ai Connector for Claude.ai](https://claude.com/connectors/windsor-ai)
- [MCP Server](https://mcp.windsor.ai)
- [Documentation](https://windsor.ai/documentation/)
- [Privacy Policy](https://windsor.ai/privacy-policy/)

## License

MIT
