# How the ranking works — signals, order, and words

**There is no score in this skill.** No index, no points, no 0–100, no weighted formula. Every earlier version of this file defined an Attrition Risk Score; it is gone deliberately.

The reason is not that arithmetic is hard — it is that the arithmetic was invented. Weights like "anxious counts double" and "competitive mention adds fifteen" are somebody's guess dressed as measurement, and the first thing any advisor asks when shown a 74 is "why 74?". The honest answer is "because we picked those numbers", which destroys the credibility of everything else on the card. Worse, a number invites the advisor to argue with the model instead of reading the client.

What replaces it is an **explicit ordering rule and plain-language labels**. Both are deterministic — two scans of the same book produce the same order — and both are explainable in a sentence, because the sentence *is* the rule.

---

## The signals

Collect these per household. Each is a fact or a plainly-classified state, never a number on a scale.

| Signal | What it is |
|---|---|
| **Silence against their own rhythm** | Days since the last meeting, compared to the median gap between their own consecutive meetings. Report it as a ratio in words — "168 days, about three times their usual" — because a monthly client at 80 days is in more trouble than a semi-annual client at 100 |
| **How they left the room** | The emotional read at last contact: content · neutral · concerned · anxious |
| **Sentiment direction** | Across the last two or three meetings: rising · flat · falling · **falling then flat**. The last is the most dangerous shape in the book — a client who stopped saying what they think |
| **Ownership** | Whether the client still brings their own agenda. Their share of topic initiation in the last meeting against their own earlier share: steady · reduced · gone |
| **Unresolved load** | Commitments the advisor made and concerns raised that the record never shows closed, each with the date it was said. Status is unknown by design — Zocks does not track task completion |
| **A discussed meeting that never happened** | A next-meeting date that passed with no meeting since. Calendar fact, not a task claim |
| **Competitive exposure** | An actual mention of another advisor, another firm, an outside product, or a capability the client wants and the firm doesn't offer. **Never inferred from silence** — it requires a real mention |
| **An elapsed life event** | An event flagged then whose window has since passed |
| **Value and segment** | From the contact record and linked systems |

---

## The order — first rule that separates two households wins

Sort descending, applying these in sequence. Stop at the first one that distinguishes the pair:

1. **Competitive exposure on record.** Someone else is already talking to them. Nothing else outranks this.
2. **Anxious or concerned at last contact.** How they felt when they went quiet decides how urgent the silence is.
3. **Sentiment falling, or falling then flat.** The relationship was already moving the wrong way before it went silent.
4. **Ownership gone.** A household that used to bring three questions and brought none has already partly left.
5. **An elapsed life event, or a discussed meeting that never happened.** Something concrete has moved while nobody was talking.
6. **Silence against their own rhythm**, largest multiple first.
7. **Unresolved load**, most items first.
8. **Value**, highest first.
9. **Days silent**, longest first — the final deterministic tie-break, so ordering is never arbitrary.

This is a priority chain, not a sum. Rule 1 can never be outvoted by rules 2–9 combined, which is the intended behaviour: a client with a competitor in the picture is a different problem from a client who is merely overdue, and averaging the two produces a ranking that is wrong about both.

---

## The words

Three labels, assigned by condition rather than by threshold:

- **Call today** — competitive exposure, or (anxious or concerned at last contact) combined with silence past their own rhythm.
- **This week** — silence past their own rhythm plus any one of: falling sentiment, ownership gone, an elapsed life event, a discussed meeting that never happened.
- **Worth a note** — past the threshold, nothing else firing.

**Print the reason, never a rank number.** "Third because a competitor was named in May" is the whole justification, and it is one an advisor can check.

## Cadence, precisely

Personal cadence is the **median** gap between a household's own consecutive meetings — median, not mean, so one six-month gap in an otherwise regular relationship doesn't distort it. Fewer than three meetings in the window means cadence is undefined: fall back to the threshold and say "new relationship — cadence not yet established". Never present a cadence computed from two meetings as their rhythm.

## Concern persistence, without arithmetic

A concern raised repeatedly and never shown resolved is the worry they have been sitting with in silence. Report it as what it is — "raised in four of their last five meetings, never resolved" — rather than as a persistence figure. The count is the evidence; a ratio derived from it adds nothing an advisor can use.

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
