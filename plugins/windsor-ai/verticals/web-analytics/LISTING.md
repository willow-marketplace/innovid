# Claude directory listing copy: Windsor.ai for SEO & Web Analytics

Exact values for the connector listing at
https://claude.ai/directory/manage/windsor-ai-for-web-analytics (endpoint
`https://mcp.windsor.ai/web_analytics`). Everything below is final: paste each
field as written.

The listing today names only Google Analytics 4 and Google Search Console. It
does not contain the words SEO, GA4, Bing or Google Business Profile, and
directory search matches the listing name, the one-liner and the tool names, so
the listing is not found for those searches.

## Order of work

1. **Edit 1, now.** Paste the fields under "Edit 1". They describe the endpoint as it is today, so nothing else has to change first.
2. **Endpoint.** Make `/web_analytics` expose exactly the 14 connectors under "Endpoint change".
3. **Edit 2, when step 2 is live.** Paste the fields under "Edit 2" and resync the tool list.
4. **Verification.** When Edit 2 is approved, ask Anthropic to verify this listing, as was done for the Social Media and Paid Media listings.

## Edit 1 (apply now)

**Name**

Windsor.ai for SEO & Web Analytics

**One-liner**

SEO and website traffic reports from Google Search Console and Google Analytics 4 (GA4): organic search queries, landing pages, traffic sources and AI referral traffic.

**Description**

Ask Claude about your organic search and website traffic in one chat. One question can show Google Search Console clicks next to GA4 sessions for each landing page. Use it for SEO reporting, content audits, traffic drop checks and monthly website reports.

Data you can query:
- Google Search Console: clicks, impressions, CTR and average position by query, page, country, device and date, for web, image, video and news search, plus branded and non-branded queries and sitemap status
- Google Analytics 4 (GA4): sessions, users, engagement rate, conversions and revenue by channel, source, landing page, country and device

AI assistant traffic: GA4 reports sessions by source, so you can ask for the visits that ChatGPT, Copilot, Perplexity and Gemini referred, by landing page.

Both sources are read-only. Claude reports on your data and cannot change your analytics or search settings. For ad platforms, social media, CRM and ecommerce data, use the main Windsor.ai connector.

To add a source, ask Claude for the sign-in link. Free forever plan for 1 data source with the last 30 days of data, no credit card. More sources and longer history need a paid plan, from $19/month (billed annually) for 3 sources. Windsor.ai is an independent product and is not affiliated with or endorsed by the platforms it connects to.

**Categories**

Sales and marketing, Data & Analytics

**Links** (unchanged)

- Documentation: https://windsor.ai/how-to-use-windsor-ai-for-web-analytics/
- Support: https://support.windsor.ai/
- Privacy policy: https://windsor.ai/privacy-statement/

**Primary use cases**

1. Show Search Console clicks and impressions by query for the last 28 days and list the queries that lost the most clicks against the 28 days before
2. Show GA4 organic search sessions and Search Console clicks by landing page for the last 28 days and list the pages that lost traffic
3. Show GA4 sessions referred by ChatGPT, Copilot, Perplexity and Gemini by landing page for the last 30 days
4. Split Search Console clicks into branded and non-branded queries by week

**What users need before connecting**

A Windsor.ai account (free forever plan, no credit card; created at first sign-in) and at least one source authorized in Windsor.ai with a login that can read the property or site.

**Reads or writes**

Reads only

**Tools**

No change.

## Endpoint change

`/web_analytics` exposes exactly these 14 connectors:

```
googleanalytics4
searchconsole
google_my_business
bing_webmaster
google_pagespeed
microsoft_clarity
matomo
plausible
piwik_pro
yandex_metrica
semrush
ahrefs
seranking
serpstat
```

The tool list stays as it is (18 tools). `execute_action` and `list_actions` are
needed for the 13 Google Business Profile write actions.

## Edit 2 (apply when the endpoint change is live)

**Name**

Windsor.ai for SEO & Web Analytics (unchanged from Edit 1)

**One-liner**

SEO, local search and website traffic reports from Google Search Console, Google Analytics 4 (GA4), Google Business Profile, Bing Webmaster Tools and PageSpeed Insights. Queries, pages and reviews.

**Description**

Ask Claude about your organic search, local search and website traffic in one chat. One question can show Google Search Console clicks next to GA4 sessions for each landing page, or list the queries that bring clicks from Bing with their Google position. Use it for SEO reporting, local SEO, content audits and traffic drop checks.

Data you can query:
- Google Search Console: clicks, impressions, CTR and average position by query, page, country, device and date, for web, image, video and news search, plus branded and non-branded queries and sitemap status
- Google Analytics 4 (GA4): sessions, users, engagement rate, conversions and revenue by channel, source, landing page, country and device
- Google Business Profile: Search and Maps impressions, search keywords, calls, website clicks, direction requests and reviews by location
- Bing Webmaster Tools: clicks, impressions and CTR by query and date
- PageSpeed Insights: mobile and desktop scores and Core Web Vitals (LCP, INP, CLS)
- Microsoft Clarity: scroll depth, rage clicks, dead clicks and quick backs by page and device
- Matomo, Plausible, Piwik PRO and Yandex Metrica: visits and page views
- Semrush, Ahrefs, SE Ranking and Serpstat, each with your own API access: keyword rankings, backlinks and domain metrics

AI assistant traffic: GA4 reports sessions by source, so you can ask for the visits that ChatGPT, Copilot, Perplexity and Gemini referred, by landing page.

Changes: on Google Business Profile, Claude can publish posts, reply to reviews and update hours, categories and listing details. Claude asks you to confirm each change first. Every other source here is read-only.

To add a source, ask Claude for the sign-in link. Free forever plan for 1 data source with the last 30 days of data, no credit card. More sources and longer history need a paid plan, from $19/month (billed annually) for 3 sources. Windsor.ai is an independent product and is not affiliated with or endorsed by the platforms it connects to.

**Categories and links**

Unchanged from Edit 1.

**Primary use cases**

1. Show Search Console clicks and impressions by query for the last 28 days and list the queries that lost the most clicks against the 28 days before
2. Show GA4 organic search sessions and Search Console clicks by landing page for the last 28 days and list the pages that lost traffic
3. Show GA4 sessions referred by ChatGPT, Copilot, Perplexity and Gemini by landing page for the last 30 days
4. Show Google Business Profile search keywords, calls and direction requests by location for last month
5. List the Google reviews from the last 30 days that have no reply and draft a reply to each for me to approve

**What users need before connecting**

A Windsor.ai account (free forever plan, no credit card; created at first sign-in) and at least one source authorized in Windsor.ai with a login that can read the property, site or location. To change a Google Business Profile listing, that login needs manager or owner access to the location.

**Reads or writes**

Both

**Tools**

Resync the tool list from the server.

## Field lengths

| Field | Limit | Edit 1 | Edit 2 |
|---|---|---|---|
| Name | 100 | 34 | 34 |
| One-liner | 200 | 168 | 197 |
| Description | 2,000 | 1,322 | 1,995 |

## Facts checked on 11 November 2026

| Claim | Source | Result |
|---|---|---|
| Search Console: query, country, device, date, position, search type (news, image, video, web), branded or non-branded, sitemaps | https://connectors.windsor.ai/searchconsole/fields | OK. No Discover and no URL inspection, so neither is claimed |
| GA4: sessions, engagement rate, landing page, source, source / medium, default channel group | `get_fields` for `googleanalytics4` | OK. Users, conversions, revenue, country and device are taken from the live listing text |
| Google Business Profile reads: Search and Maps impressions, search keywords, call clicks, website clicks, direction requests, reviews, posts | https://connectors.windsor.ai/google_my_business/fields | OK. The search keyword value is a monthly figure |
| Google Business Profile changes: posts, review replies, photos, description, website, phones, categories, services, service area, attributes, address, regular and special hours, open status | `list_actions` for `google_my_business` (13 actions) | OK. Address and service area changes can trigger Google re-verification and require explicit approval |
| Bing Webmaster Tools: query, clicks, impressions, CTR, date, site | `get_fields` for `bing_webmaster` | OK. One table. No page dimension and no Copilot or AI Performance data, so neither is claimed |
| PageSpeed Insights: mobile and desktop scores, LCP, INP, CLS | https://connectors.windsor.ai/google_pagespeed/fields | OK |
| Microsoft Clarity: scroll depth, rage, dead and quick back clicks, URL, device | https://connectors.windsor.ai/microsoft_clarity/fields | OK. No period is claimed |
| Semrush, Ahrefs, SE Ranking, Serpstat: keyword, ranking and backlink tables | public field lists | OK at table level |
| Read-only sources: `searchconsole`, `googleanalytics4`, `bing_webmaster`, `google_pagespeed`, `microsoft_clarity`, `semrush`, `ahrefs`, `seranking`, `serpstat`, `matomo`, `plausible`, `piwik_pro`, `yandex_metrica` | `list_actions` returned an empty list for each | OK |
| Free plan: 1 source, last 30 days of data. Basic: $19/month billed annually, 3 sources | https://windsor.ai/pricing/ | OK |
| Links | read from the live listing page | Documentation, support and privacy links are the ones the listing has today |
