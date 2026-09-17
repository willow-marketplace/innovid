# AI-result payload classification, household rules, endpoint quirks

Read before parsing anything from `ai_results_list_results`. This file is identical across every skill in the library — if it changes, it changes everywhere.

`ai_results_list_results` returns items that are all `type: "structuredSummary"` — there is **no type discriminator**. Classify each item by the shape of its `result` field before reading it. Verified against the live Zocks MCP (July 2026); re-verify if classification starts missing.

Call it with **`meeting_summary_size=0`** unless the narrative summary is specifically what you need. See `data-budget.md`.

---

## Shape classification table

| Payload shape (in `result.result`) | What it is | Typically carries |
|---|---|---|
| Array of client-voiced statements | **Concerns** | The named anxieties, close to the client's own words. Competitive mentions and unmet product wants surface here too |
| Array of `{topic, subTopic, initiatedBy, initiatorName}` | **Topic initiation** | What was discussed and whose agenda it was. `initiatedBy` matching a household member's contact id is client-driven |
| Array of `{relatedTo, assignedTo, task, description}` | **Open tasks** | Commitments, and the evidence that a topic was settled rather than deferred. `assignedTo` says whose move it is |
| Array of `{overallOutlook, outlooks[], clientName, clientId}` | **Client outlook** | Overall tone, plus which specific asset classes moved and in which direction |
| Object with life-event keys (`Inheritance Received`, `Career - …`, `Relocation`, health, bereavement) | **Life events** | Anticipated or in-flight change — the transitions that reset a plan |
| Object of `{meeting name: date}`, or an array of `{date, dateHint, name}` | **Next-meeting reference** | Forward meeting reference. Two shapes — see below |
| Object with planning-domain keys (`Investment Management`, `Estate Planning`, `Retirement Forecasting`, …) | **Planning domains** | Recorded positions per domain. A domain with a position was decided, not deferred. Secondary source for custodians and balances |
| String starting `# Financial Profile:` | **Per-client financial profile** | Accounts, custodians, balance ranges, stated risk posture. The richest single payload for held-away and fact-pattern work |
| Array of `{Information[], Relationship, Name}` | **Family / relationships** | Household assembly, beneficiaries, spouses and partners, outside professionals |
| Array of plain strings | Personal facts | Context only — colour, not evidence |
| Long string starting "Client Information" | Narrative meeting summary | **Skip** — everything in it exists structured elsewhere, shorter |
| String starting "Dear …" | Follow-up email draft | **Skip** |
| Object with `Scheduling` / `Engagement` / `Workflow` keys | Advisor coaching scorecard | **Skip** unless the skill is explicitly about advisor coaching — it grades the advisor, not the relationship |
| Array of `{clients[{referees, referralAsked}], advisorId}` | Referrals record | **Skip** unless the skill is about referrals |
| Array of `{Percentage, Topic}` | Topic share of the meeting | **Skip** — share of airtime *by topic*, not by speaker. It does not measure engagement, and reading it as engagement is a live trap |
| `result: null` | Empty / failed result | **Skip silently** |

**Read priority when context is tight:** topic initiation → tasks → concerns → outlook → life events → next-meeting reference → financial profile. Extract the fields the skill scores and discard the rest immediately; never carry a whole payload forward.

---

## Next-meeting references have two shapes

Verified live, 31 July 2026. The same book returns both:

```js
// Shape A — structured, and the date is often a range, not a date
[{ "date": null,
   "dateHint": { "edtf": "2026-08-05/2026-08-31",
                 "text": "two to three weeks after all documents are received" },
   "name": "Financial Plan Review Meeting" }]

// Shape B — flat map, and the value may not be a date at all
{ "Financial Plan Review Meeting": "Date not mentioned." }
```

Four cases, handled explicitly:

| Case | Treatment |
|---|---|
| `date` in the future | A meeting is on the books |
| `date` passed, no later meeting on record | **A next meeting was discussed and none has happened since.** Report it in those words; never as a discussed-but-unbooked meeting, and name it plainly |
| `dateHint.edtf` is a **range** | Nothing to say until the **end** of the range has passed. Never parse the start and treat it as the deadline — that fabricates an overdue commitment weeks early |
| No usable date — `"Date not mentioned."`, or `date: null` with no hint | **A promise with no date.** Not scoreable as broken, but say it out loud: something was committed to and never anchored. It is the quietest way a commitment dies, and it is invisible to anyone reading a transcript |

Never treat an unparseable value as satisfied. Silence in this field is a finding, not a pass.

---

## Deferral detection — was the topic settled, or dodged?

Several skills turn on this distinction. A topic is **deferred** when it was discussed and nothing came of it. For each topic in the initiation payload, look for any of:

- a task in the same meeting whose `relatedTo` or text references it
- a decision or agreed next step covering it — including "we'll look at it in the spring" **with a date attached**, or documents being collected against it
- a recorded position in the planning-domain payload for that domain
- work already underway against it from a previous meeting

Any of these → **settled**. None → **deferred**.

Boundary cases that matter:

- **"Let's revisit at the annual review" with nothing scheduled against it is deferral**, not planning. A next step needs an owner or a date to count.
- **Informational topics aren't deferrals.** A market update or a performance walkthrough with no action attached was never a decision. Exclude topics that carry no decision from both sides of any ratio — including them inflates hesitancy on perfectly healthy review meetings.
- **Advisor-side backlog isn't client hesitancy.** A topic where the advisor took the action ("I'll model both options") is settled. Deferral measures the client's readiness to commit, not the advisor's follow-through.
- **When you can't tell either way, exclude the topic** rather than counting it as deferred. A false hesitancy signal tells an advisor a committed client is drifting, which is the most expensive way these skills can be wrong.

Repeat deferral — the same topic deferred in consecutive meetings — needs prior meetings and is enrichment only. When present it is the loudest signal available and should be quoted with dates: "the rollover has now been discussed in three meetings without a decision."

---

## Household rules

**One household = one report, always.** Assemble from two signals, in order:

1. **Meeting co-attendance + shared last name** — non-advisor participants in the same meeting sharing a surname are one household.
2. **Relationships payload** — participants the family extraction identifies as spouses or partners are one household even with different surnames.

Rules of the household:

- Display name: "Peter & Paula Reynolds" (alphabetical by first name when unclear).
- Household key: sorted member contact ids joined with `+`.
- **Real books contain duplicate contact records for the same human**, from CRM sync. Group by normalised name + email *before any arithmetic*, or cadence reads as twice as fast as it is and the run costs twice as much.
- An asset, concern, or commitment belongs to the household even when only one member owns it ("Peter's 403(b)" lives on the Reynolds card, attributed to Peter).
- Meetings attended by one member still belong to the household; engagement counts **any** member as client-driven.
- Spouses and partners get a single read, because the decision is made jointly — but the anxieties usually are not the same on both sides, so note where they diverge.
- **Language: "spouses" or "partners" — never "husband and wife."**
- **Weak evidence** (different surnames, no relationship payload, two unrelated clients in a group session) → keep them separate and say so. A wrong merge produces a confident read on a household that doesn't exist.

---

## Language and identity conventions

- Say **"systems linked"**, never "CRM linked" — the advisor may have several, and may not think of them that way.
- **Never ask who the user is.** Pass `advisor_id="{user_id}"` — that literal token — to the meetings tools and the MCP scopes everything to the signed-in advisor. `users_list_users` is only needed when a skill genuinely works across advisors.

---

## Known endpoint quirks

- **`ai_result_size` and paging params may be ignored** — the endpoint can return **all** items, 75k+ characters on a long meeting. Never rely on pagination to bound the payload. Classify shapes and extract only the fields the skill scores.
- **Meetings appear in listings only after AI analysis.** A meeting held hours ago may be absent. An advisor asking straight after a meeting should be told the analysis isn't in yet — never handed the previous meeting's read presented as current.
- **Some meetings have no client participant** (internal, tests). Filter them at the meeting-listing step, before spending AI-result calls.
- **`insights_query_insights` requires `from_date` and `to_date` together, or neither.** Passing neither takes the 30-day default, which is what most skills want.
- **The insights `held_away_assets` funnel counts meeting-template datapoints, not AI detection** — it can read 0 for a period where per-meeting payloads contain real mentions. Report both without "correcting" either.
