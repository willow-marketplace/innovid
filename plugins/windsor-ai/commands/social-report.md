---
name: social-report
description: Organic social report across Instagram, Facebook Pages, LinkedIn Pages, Threads, TikTok and YouTube for the last 30 days, with the best posts per platform
---

# /social-report

Compare organic performance across every connected social account for the last 30 days (or the range the user gives).

## Instructions

1. Call `get_connectors` and pick the connected sources among `instagram`, `facebook_organic`, `linkedin_organic`, `threads`, `tiktok_organic` and `youtube`. If the user named a platform or account, report on that one only. If there are several clients or brands, delegate to the social-media-analyst agent.
2. For each source, read field ids with `get_fields` and make two `get_data` calls: one for account-level values (daily reach or views, engagement, follower change) and one for post or video rows (id, date, caption or title, permalink, reach or views, likes, comments, shares). The per-platform skills list the right fields and the ones that are easy to misread.
3. Report per platform: follower change, views or reach, engagements, engagement rate (engagements divided by reach or impressions; say which, and say when it is zero), and the three best posts or videos with their numbers and links. Where the platform has Reels or Shorts, show how they did against the other formats.
4. Do not add unique metrics across days or across platforms. Each platform counts reach and views differently; say so in one line.
5. Close with what the best posts have in common (format, topic, day posted), based only on the returned rows. Offer to draft a post in the style of the best performer; publishing happens only after the user approves the full draft.

If a platform fails to read, list it under "Not read" with the reason (for example an expired connection) and continue with the others.