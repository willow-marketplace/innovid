# How this is ordered and labelled — no score

**There is no score in this skill.** No index, no points, no 0–100, no weighted components. Earlier versions defined one; it is gone deliberately.

**What is banned, and what isn't.** The problem is *weighted multi-variable indexes* — a number built by multiplying or summing five or nine components with invented weights, then surfaced to the advisor. Those are gone everywhere, permanently. Any figure of the form "this is worth twice that" is a guess presented as measurement, the first thing anyone shown it asks is how it was calculated, and the honest answer — "we picked those weights" — destroys the credibility of everything else on the page.

**Plain counts, dates, labels and priority order are welcome.** "Four of the six items need someone outside the firm" · "raised in four of their last five meetings" · "168 days, about three times their usual rhythm" · "cooling" · "act now" · "confirm this first" — all fine, all useful, all instantly checkable by the advisor. The test is simple: **can the advisor verify it by looking, and re-decide it themselves if they disagree?** A count of things they can see passes. An index derived from weights they can't see does not.

What replaces it: **named signals, an explicit priority order, and plain-language labels assigned by condition.** Deterministic, and explainable in the sentence that is also the rule.

---

## Corroboration — how well a fact is supported

Three words, not a confidence figure:

- **Confirmed** — stated in more than one meeting, or stated by the client and reflected in a later decision.
- **Stated once** — said in one meeting and never repeated. Perfectly usable; just labelled honestly.
- **Stale** — last stated more than roughly 18 months ago and never restated. Still shown, with the date, because staleness is itself the finding.

Show the date and the meeting behind every fact. A fact with its date attached needs no confidence score — the reader can judge it, which is better than being told a number.

## Superseded facts

Where a later meeting contradicts an earlier one, the newer wins and **the change is the finding**: a retirement age that moved from 67 to 64 is more interesting than either figure. Show both with their dates and say which is current.

## What they've never been asked

Compare the household's covered territory against the domains the rest of the advisor's book routinely covers. Report it as **a plain count plus the named gaps**: *"six of the nine domains your other reviews routinely cover have come up with them; estate, long-term care and education never have."* The count orients, the names are what the advisor acts on.

Not a coverage index or a percentage of a firmwide benchmark — those imply a weighting and a denominator the data doesn't really support, and they turn a useful prompt into a grade.

## Beneficiaries never met

List them: named as a beneficiary or a dependent in the record, with no meeting the advisor ever attended. Count them plainly. No index.

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
