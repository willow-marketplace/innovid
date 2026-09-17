# Extraction rules

Read alongside `ai-result-shapes.md`, which carries the payload classification table, the household rules, and the endpoint quirks. This file is what is specific to this skill.

## Extracting goals for the diff

- A goal needs an **owner, a subject, and a target** (amount, date, or age). "They'd like to retire comfortably" is a sentiment, not a goal, and it has no endpoints to diff.
- Normalise to a **goal key** — `retirement:date`, `education:target`, `business:exit`, `legacy:amount` — so the earliest and latest statements of the same goal actually pair up. Two keys for one goal produces two half-goals and a pack that looks careless.
- **Preserve the source form.** A range stays a range; "around $1.5M" stays approximate. Never convert an approximation into a precise figure, and never compute a delta between two approximations as if it were exact — say "up roughly $200k".
- Where the profile and the planning-domain payload disagree in the same meeting, prefer the **financial profile** and note the discrepancy internally. Never average them.

## Extracting commitments

- `assignedTo` decides the owner. Advisor or firm → a commitment the pack tracks, reported with its meeting and date and its status honestly undetermined unless closure is on record. Client → tracked, listed separately, never scored.
- Match a commitment to its closure across meetings on subject, not wording. "Run the rollover analysis" and "walked through the rollover numbers" are the same item.
- The **first** meeting in the window matters disproportionately: commitments made there have had the whole window to close, and an open item from month one is the pack's most important line.
