# Journey report — structure, geometry, interaction

Read with `references/output-modes.md` before rendering. This file covers what has to be on the page and how the two fiddly visuals are built. It says nothing about palette or typeface — those are yours per report, within the firm's brand kit.

---

## Section order

1. **Header** — firm logo and name from the brand kit, household display name, advisor name, "working together since 2019 · 7 years", meeting coverage ("34 meetings, 31 analyzed"), and a link to each member's contact record in Zocks.
2. **Stage timeline** — the five lifecycle stages horizontally, current position marked.
3. **Relationship tree** — the centrepiece.
4. **Journey timeline** — dated events with turning points called out.
5. **Achievements** — the wins, 4–8 of them, or fewer honestly.
6. **Motivators, worries and interests** — in the main flow, not behind a tab.
7. **Trusted team** — the outside professionals.
8. **Money** — accounts, custodians, held-away, obligations.
9. **Commitments** — what's owed, by whom, how old, discussed-but-unbooked meetings named.
10. **Lifecycle activity table** — all 36 items grouped by stage, each expanding to its evidence.
11. **Unknowns and blind spots** — the questions to ask, and the beneficiaries never met.

A section with nothing behind it is omitted, not rendered empty. The exception is the unknowns panel, whose emptiness would itself be the finding — say "nothing the firm routinely covers is missing here", which is rare enough to be worth printing.

---

## Stage timeline

Five stages left to right in fixed order, each showing its name and its counts in words — evidenced, no, never covered — per the stage-status rule in `lifecycle-stages.md`; never a percentage. The active stage is visually distinct from complete and future — by weight, size, or marker, whatever suits the report. Print the blank count somewhere honest so a full-looking count on a thin stage can't mislead.

Clicking a stage filters the lifecycle activity table to that stage. That's the whole interaction; don't build a stage-by-stage carousel.

Where a household is genuinely in two stages at once (Ongoing, and back in Goal Setting after a divorce), mark both and let the journey timeline explain why. Forcing one clean position produces a picture the client will correct out loud.

---

## Relationship tree

An SVG in a 600×420 viewBox, laid out by generation. Node positions are set explicitly — automatic layout produces collisions and crossed connectors on exactly the households that matter most.

Each person:

```js
{
  id: "p1",
  name: "John Harrison",
  role: "Primary Client",              // Primary Client, Spouse, Daughter, Son, Ex-spouse, Trustee…
  relationship: "Self",                // relationship to the primary client
  age: 58,                             // optional
  is_client: true,                     // primary client(s) render larger
  spouse_id: "p2",                     // draws the spouse line
  parent_ids: ["p1","p2"],             // draws the parent connector
  occupation: "Engineering Director",
  beneficiary: "Primary on 401(k), IRA",
  dependent: false,
  financial_role: "Joint decision-maker",
  never_met: true,                      // has never attended a meeting — drives the blind-spot flag
  confidence: "confirmed",              // confirmed | stated once | stale
  sensitive: false,                     // health, estrangement, conflict
  tagline: "Driving the retirement timeline",
  notes: "Wants to retire at 62. Risk-averse on the portfolio, growth-tolerant in the 529s.",
  x: 200, y: 110
}
```

**Geometry:**

- Older generation: `y = 50`. Beside a primary client (`x ≈ 525`) or directly above one at the same `x`.
- Primary clients and spouses: `y = 130`, spaced around `x = 200` and `x = 400`.
- Children: `y = 320`, spread evenly from about `x = 120` to `x = 480` by count.
- Grandchildren: `y = 410`.
- Node radius 28–32px. Keep at least 100px between centres horizontally or the name labels collide.
- The parent-to-children connector drops about 100px below the spouse line before running horizontally, so it routes around the parent labels on its own — just keep children at least 150px below their parents.

**Rules:**

- **Never invent a family member.** Only people referenced in the harvested payloads. A household that is two nodes and a spouse line is a correct tree.
- Skip a generation entirely when the data doesn't support it.
- `never_met` nodes are marked distinctly and counted into the Beneficiary Blind Spot — this is the flag an advisor acts on.
- Corroboration shows on the node, not just in the profile. A daughter's name heard once in 2023 should not look as solid as a spouse in every meeting.
- `sensitive` nodes and sensitive fields inside a profile are **hidden by default in any client-facing render**, per the compliance rules in SKILL.md.

**Interaction:** hover or tap gives the tagline; clicking opens the full profile. On a phone the tree scrolls horizontally inside its own container while the page does not.

---

## Journey timeline

Dated, oldest to newest, with the turning points from the supersession pass distinguished from ordinary events — a retirement date moving from 62 to 65 is the interesting entry, not the mention of retirement. Each entry links to the meeting it came from.

Clicking a point filters the fact panels below to that moment, which is how an advisor answers "what else was going on when they said that".

---

## Achievements

Per achievement: a date or period, a title of five to eight words, one line of description, and an impact line where the data carries one ("$45,000 buffer in place"). Between four and eight. **Fewer is fine.** Padding this section with generic wins is the most visible way this report can look automated.

---

## Trusted team

One card per outside professional, grouped or labelled by category: Tax, Legal, Insurance, Medical, Business, Banking, Other. Per person: name and credential, firm, their specific role, what they handle for the family, last touchpoint, cadence, status (active, periodic, inactive), and any coordination note ("coordinates with us on Roth conversion timing each November").

Sources: named mentions in the payloads, professional referrals, next steps addressed to a CPA or attorney, document mentions ("the estate documents we did with Hartwell Law"). Where only a name and a role exist, fill those and leave the rest blank — the cards handle missing fields.

No outside professionals mentioned in the whole window is itself worth saying once: it usually means the coordination conversation has never happened.

---

## Lifecycle activity table

All 36 items grouped under their stage, in checklist order, each with its Yes / No / blank value. Every Yes and No **expands** to its evidence: the plain-English why, the short quote where one exists with the speaker named, the date, and a link to that meeting in Zocks.

Filterable to a stage (from the stage timeline), and to "answered only" so an advisor can see the substance without scrolling past blanks.

In a document, the expansion becomes an indented paragraph under the row and the link becomes a hyperlink on the meeting date.

---

## Fact panels

Motivators and worries, interests, life and health, money. Each fact shows its statement, its corroboration, and its age. Filterable by confidence — an advisor about to walk a client through this should be able to show only what's confirmed.

Superseded facts stay visible, marked, next to what replaced them. Conflicting facts with no chronology show both dates and no winner.
