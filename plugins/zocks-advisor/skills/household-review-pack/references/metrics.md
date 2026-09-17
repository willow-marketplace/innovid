# How this is ordered and labelled — no score

**There is no score in this skill.** No index, no points, no 0–100, no weighted components. Earlier versions defined one; it is gone deliberately.

**What is banned, and what isn't.** The problem is *weighted multi-variable indexes* — a number built by multiplying or summing five or nine components with invented weights, then surfaced to the advisor. Those are gone everywhere, permanently. Any figure of the form "this is worth twice that" is a guess presented as measurement, the first thing anyone shown it asks is how it was calculated, and the honest answer — "we picked those weights" — destroys the credibility of everything else on the page.

**Plain counts, dates, labels and priority order are welcome.** "Four of the six items need someone outside the firm" · "raised in four of their last five meetings" · "168 days, about three times their usual rhythm" · "cooling" · "act now" · "confirm this first" — all fine, all useful, all instantly checkable by the advisor. The test is simple: **can the advisor verify it by looking, and re-decide it themselves if they disagree?** A count of things they can see passes. An index derived from weights they can't see does not.

What replaces it: **named signals, an explicit priority order, and plain-language labels assigned by condition.** Deterministic, and explainable in the sentence that is also the rule.

---

## Commitments — recorded, never graded

**There was a Commitment Kept Rate here. It is deleted, and it could never have worked.** It required knowing which commitments were completed, and Zocks does not track task completion — no field anywhere says an action item was done. A "kept rate" computed from that data would have been a number about the advisor's reliability derived from data that cannot support it, printed in a document that may reach a client. That is the single worst output this library could produce.

What replaces it: **a dated list of what was said.** For the review window, every commitment the advisor made, with the meeting and date, and where a later meeting explicitly discusses one as done, that mention shown as the evidence. Everything else is simply undetermined, said out loud: *"status not recorded — confirm before the meeting."*

The advisor supplies the status. The pack's job is to make sure nothing was forgotten, not to mark their homework.

## Goals — then versus now

Set the goals as stated at the start of the window beside the goals as stated most recently, with dates. Where one changed, say what changed and quote both. Where one disappeared from the conversation, say that too — a goal that stopped being mentioned is a finding.

**No drift figure.** "Retirement moved from 67 to 64, stated May 2025 and again July 2026" tells the advisor everything a drift percentage would have, and it survives being read out loud in a meeting.

## Coverage against the book

Which planning domains came up with this household over the window, and which the advisor's other reviews routinely cover. A plain two-column comparison, ending in a short list of what never came up. No coverage score, no benchmark index — the gap list *is* the deliverable.

## Coordination — what the handoffs actually are

For each item needing someone outside the firm: what it is, who has to act, what they need from us, what it unblocks, and what is blocked until they do it. Where the household has no such professional on record, say so — "this needs an estate attorney and there isn't one on file" is one of the most useful lines the pack produces.

Order the chain by what blocks the most other items, then by whichever has a real external date. **No debt figure** — a dependency chain that reads correctly needs no number, and a "coordination debt of 7" tells nobody what to do on Monday.

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
