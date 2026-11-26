---
name: campaign-report
description: Generate a performance report for recent email campaigns with metrics, trends, and recommendations.
---

# /campaign-report

Generate a performance report for recent email campaigns.

## Instructions

When the user runs `/campaign-report`, produce a campaign performance summary from `run_campaign_performance_report`, the tool built for campaign metrics. It returns per-campaign counts and rates and can rank campaigns, so you don't need to call `get_campaign` once per campaign.

> **Server rule:** Show the numbers and rates the report returns. Don't compute a blended "average open rate" or "total sends" across campaigns yourself.

### Steps

1. **Campaign performance**: One call to `run_campaign_performance_report` with `module=campaigns`, `explore=all_campaigns_performance`, columns `campaign_id`, `campaign_name`, `last_sent_date`, `total_sends`, `unique_total_opens`, `open_rate`, `unique_subscriber_clicks`, `click_rate`, `total_bounces`, `bounce_rate`, `total_unsubscribes`, `unsubscribe_rate`, sorted by `last_sent_date` DESC, `limit` 10 (or what the user asked for).
   - A date range goes in `params` (`view_distinct_campaigns_event_date_start` / `_end`, inclusive).
   - A campaign name goes in `filter` (`campaign_name` CONTAINS).
   - "Best" or "worst" by a metric: sort by that metric instead.

2. **Link performance**: For the one or two campaigns with the most clicks, call the `link_clicks` explore with that `campaign_id` (EQ filter), sorted by `unique_subscriber_clicks`.

3. **Present the report**:

```
## Campaign Performance Report

### Campaigns ([N], [date range or "most recent"])
| Campaign | Last sent | Sends | Open rate | Click rate | Bounce rate | Unsub rate |
|----------|-----------|-------|-----------|------------|-------------|------------|
| ...      | ...       | ...   | ...       | ...        | ...         | ...        |

(Values as returned by the report.)

### Standouts
1. **[Campaign]**: highest [metric] in this set
   - Top link: [URL] ([X] unique clicks)

### Patterns
- [Observation tied to specific campaigns, e.g. "the three most recent sends each opened lower than the one before"]

### Recommendations
- [Specific suggestion tied to the campaigns above]
```

### Handling edge cases
- No campaigns in the range: say so and offer a wider range.
- A campaign with unusual numbers: flag it and suggest causes (deliverability, list quality), and offer the deliverability-advisor skill.
- Questions about automation performance aren't covered by this report; say so.

### Arguments
- Optional campaign name, count, or date range
- Examples: `/campaign-report last 5`, `/campaign-report "March Newsletter"`, `/campaign-report June`