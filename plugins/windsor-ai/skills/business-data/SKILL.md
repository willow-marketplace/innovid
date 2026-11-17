---
name: business-data
description: Pull and combine business data from any of the 350+ sources connected to Windsor.ai inside a coding workflow, for example writing campaign or CRM data to a local JSON or CSV file, generating typed data for a dashboard, or joining ad spend with revenue. Use when the user wants Windsor.ai data in code or files rather than a one-off report.
---

# Windsor.ai Business Data Skill

Use this skill whenever the user needs business data — marketing analytics, sales metrics, CRM data, ecommerce transactions, financial data, or any other data available through Windsor.ai's 350+ connectors.

## When to Use

- User is building a dashboard, report, or data visualization that needs business data from any source
- User asks about ad spend, campaign performance, ROAS, CTR, CPC, or conversion metrics
- User needs CRM, sales, ecommerce, or financial data in their codebase
- User needs to explore what data sources or fields are available
- User wants to seed a project with real data (e.g. for testing, prototyping, or generating fixtures)
- User is building an integration with any platform supported by Windsor.ai
- User asks about Google Ads search terms, keywords, quality score, impression share or negative keywords
- User asks about Instagram post reach, saves, engagement or follower growth
- User wants to change something on an ad platform or Instagram (pause, enable, budgets, bids, new campaigns, negative keywords, posts, comments)

## Available Tools

The four read tools are below. Write actions are covered in the next section.

### `get_connectors`
Lists all connected platforms and their account IDs. Always call this first if you don't know what accounts are available.

### `get_options`
Returns available fields, date filters, and options for a specific connector. Use this to discover what data can be queried before calling `get_data`.

**Parameters:**
- `connector` (required): Platform ID like `"google_ads"`, `"facebook"`, `"tiktok"`, `"linkedin"`, `"googleanalytics4"`, `"hubspot"`, `"salesforce"`, `"searchconsole"`, `"instagram"`, `"youtube"`, `"google_my_business"`, `"shopify"`, `"stripe"`, `"quickbooks"`, and 300+ more
- `accounts` (required): List of account IDs from `get_connectors`

### `get_fields`
Returns detailed metadata about specific fields — data types, descriptions, available values. Use this when you need to understand the schema before writing code that processes the data.

**Parameters:**
- `connector` (required): Platform ID
- `fields` (required): List of field IDs like `["campaign", "spend", "clicks"]`

### `get_data`
Retrieves actual data. This is the main query tool.

**Parameters:**
- `connector` (required): Platform ID
- `accounts` (required): List of account IDs
- `fields` (required): Fields to retrieve, e.g. `["campaign", "date", "spend", "clicks", "impressions"]`
- `date_from` / `date_to`: Date range as `"YYYY-MM-DD"`
- `date_preset`: Shorthand like `"last_7d"`, `"last_30d"`, `"this_month"`, `"last_3m"`
- `filters`: Conditions like `[["spend", "gt", 100], "and", ["campaign", "contains", "Sale"]]`
- `options`: Connector-specific options like `{"attribution_window": "7d_view,1d_click"}`

## Write Actions

Windsor.ai can also make changes on some connected platforms:

- **Google Ads (`google_ads`):** create campaigns, ad groups and responsive search ads; pause or enable campaigns, ad groups and ads; set campaign budget, bidding strategy, target CPA, target ROAS, max CPC and CPC bid ceiling; add, update or remove keywords; push or remove negative keywords at campaign or ad group level; set geo, language and ad schedule targeting; manage Customer Match lists
- **Meta Ads (`facebook`):** create, update, pause or enable campaigns, ad sets and ads; duplicate ad sets; set campaign and ad set budgets; edit ad creatives; upload ad images and videos; boost an organic post
- **Instagram (`instagram`):** publish image posts, carousels, video posts (reels) and stories; comment on a post; reply to, hide, unhide or delete comments
- **TikTok Ads (`tiktok`):** pause or enable campaigns, ad groups and ads; set campaign and ad group budgets
- **LinkedIn Ads (`linkedin`):** pause or enable campaign groups, campaigns and creatives; set campaign group and campaign budgets; rename campaigns; set campaign schedule and targeting; create a creative from an existing post
- **Microsoft Ads (`bing`):** pause or enable campaigns and ad groups; set campaign budget

The live list is whatever `list_actions` returns for a connector, so never assume an action exists.

1. Call `list_actions` with the connector ID and read the parameter schema of the action you need.
2. Show the user exactly what will change (account, object, old value, new value) and wait for an explicit yes.
3. Call `execute_action` with the action name and parameters.
4. Report the result, including any error the platform returns.

Create actions default to paused (Google Ads, Meta Ads) or draft (LinkedIn creatives). Keep that default unless the user asks otherwise. Instagram accounts connected before comment access was added return a permission error on comments until they are reconnected in Windsor.ai.

## Workflow Pattern

1. **Discover** → Call `get_connectors` to see what's connected
2. **Explore** → Call `get_options` to see available fields for a connector
3. **Understand** → Call `get_fields` for field metadata if building typed interfaces
4. **Query** → Call `get_data` to pull the actual data

## Common Field Patterns

Fields vary by connector type. Here are some common examples:

**Marketing/Ads connectors:** `campaign`, `adgroup`, `ad`, `date`, `device`, `country`, `spend`, `clicks`, `impressions`, `conversions`, `revenue`, `ctr`, `cpc`, `cpm`, `roas`

**CRM connectors:** `deal`, `contact`, `company`, `stage`, `owner`, `amount`, `close_date`

**Ecommerce connectors:** `order_id`, `product`, `quantity`, `price`, `customer`, `status`

Always check `get_options` first since available fields vary by connector.

## Tips

- When building dashboards or charts, pull data with `get_data` and write it to a local JSON/CSV file the app can read
- For TypeScript projects, use `get_fields` to generate accurate type definitions
- Use `date_preset` for quick queries: `"last_7d"`, `"last_30d"`, `"this_month"`
- Combine filters for focused queries: `[["spend", "gt", 0], "and", ["campaign", "ncontains", "test"]]`
- You can join data from different connectors (e.g. ad spend + CRM revenue) by pulling from each and merging in code