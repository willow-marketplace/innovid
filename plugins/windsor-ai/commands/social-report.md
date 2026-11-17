---
name: social-report
description: Organic social report across Instagram, Facebook Pages, LinkedIn Pages, TikTok and YouTube for the last 30 days
---

# /social-report

Compare organic performance across every connected social account.

## Instructions

1. Call `get_connectors` and pick the connected organic sources: `instagram`, `facebook_organic`, `linkedin_organic`, `tiktok_organic`, `youtube`. Ask if an account is ambiguous.
2. For each one, read field ids with `get_fields` and call `get_data` for the last 30 days (or the user's range): account-level reach or impressions, engagement, and follower change, plus post-level fields for the top posts.
3. Report per platform: followers gained, reach, engagement and engagement rate (engagements / reach, say when reach is zero), and the three best posts with their numbers. Use each platform's week or month field for period totals of unique metrics instead of summing daily rows.
4. Close with what worked across platforms (format, topic, timing) based only on the returned posts. Offer to draft a post in the style of the best performer; publishing happens only after the user approves the full text.