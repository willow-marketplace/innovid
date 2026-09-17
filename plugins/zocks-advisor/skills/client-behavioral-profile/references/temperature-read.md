# How this is ordered and labelled — no score

**There is no score in this skill.** No index, no points, no 0–100, no weighted components. Earlier versions defined one; it is gone deliberately.

**What is banned, and what isn't.** The problem is *weighted multi-variable indexes* — a number built by multiplying or summing five or nine components with invented weights, then surfaced to the advisor. Those are gone everywhere, permanently. Any figure of the form "this is worth twice that" is a guess presented as measurement, the first thing anyone shown it asks is how it was calculated, and the honest answer — "we picked those weights" — destroys the credibility of everything else on the page.

**Plain counts, dates, labels and priority order are welcome.** "Four of the six items need someone outside the firm" · "raised in four of their last five meetings" · "168 days, about three times their usual rhythm" · "cooling" · "act now" · "confirm this first" — all fine, all useful, all instantly checkable by the advisor. The test is simple: **can the advisor verify it by looking, and re-decide it themselves if they disagree?** A count of things they can see passes. An index derived from weights they can't see does not.

What replaces it: **named signals, an explicit priority order, and plain-language labels assigned by condition.** Deterministic, and explainable in the sentence that is also the rule.

---

## The five signals

Each is read from one meeting and sharpened by history where it exists. Each resolves to a direction — **negative · neutral · positive** — never to a value.

| Signal | Negative | Positive |
|---|---|---|
| **What they're worried about** | Concerns that are recurring, unresolved, or existential (running out, losing the house, outliving the money) | Few concerns, or concerns raised and visibly addressed |
| **What they wouldn't commit to** | A recommendation deferred, hedged, or declined — especially the same one across meetings | Decisions taken in the room |
| **Sentiment** | Cautious or anxious, or *better in absolute terms but worse than last time* | Positive, or improving from a worse starting point |
| **Transition pressure** | Bracing for a life change the advisor is not visibly part of | A change in progress with the advisor inside it |
| **Whether they bring their own agenda** | Advisor carrying the airtime; client asking little | Client initiating topics and asking questions |

**Asset-class outlook entries are not part of the read.** They name *what* soured, which belongs in the signals shown to the advisor, not in the direction itself.

---

## The verdict — first rule that fits wins

1. **Cooling** — two or more signals negative; or a recommendation declined outright.
2. **Warming** — two or more signals positive and none negative.
3. **Steady** — everything else.

Two caps, applied after the above:

- **A discussed meeting that never happened caps the read at steady**, however positive everything else looks. Say it plainly as the calendar fact it is — never as a broken promise, since Zocks cannot tell whether the advisor handled it another way.
- **One analyzed meeting caps confidence, not direction.** Read it, then say the read rests on a single conversation. Never present a one-meeting read as a trend.

## Read confidence, in words

**Firm** — three or more analyzed meetings, several signals present. **Tentative** — two meetings, or signals thin. **Too early to tell** — one meeting, or almost nothing on record. Say which, in a clause, every time. A confident-sounding read built on one thin meeting is the failure mode this replaces.

## Driving signals

Always name the one or two signals that decided the verdict, with their evidence and dates. The verdict without its drivers is an opinion; with them it is a summary the advisor can check and disagree with.

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
