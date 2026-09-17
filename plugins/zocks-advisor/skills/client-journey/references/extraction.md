# Extraction rules

Read alongside `ai-result-shapes.md`, which carries the payload classification table, the household rules, and the endpoint quirks. This file is what is specific to this skill: **what becomes a fact, how it is written, and — the part that matters most — what never enters the map at all.**

This skill produces the one report in the library that may be turned around on screen in front of a client. Everything below exists because of that.

---

## Writing a fact

- **One fact per claim, not per mention.** "They have a boat and they sail every summer in Maine" is three facts (boat, sailing, Maine) with three keys, not one long string. Granularity is what makes the map searchable.
- **Statements, not sentences.** Store "403(b) at Texas Tech, former employer, ~$120–130k" — not the paragraph it came from. Short, structured, self-contained.
- **Attribute to the person.** A fact about Paula lives under Paula. Household-level facts — the house, the trust, the joint account — are attributed to the household.
- **Never store a number you did not see.** Ranges and approximations from the source stay ranges and approximations. No rounding, no midpointing, no tidying "about a hundred and twenty, maybe thirty thousand" into $125,000.
- **Never write a fact Zocks did not return.** Not an age inferred from a career stage, not a plan feature inferred from an employer, not a relationship inferred from a shared surname, not a professional inferred from someone's wealth. The full rule is in `data-budget.md`; on this report it is the difference between a document an advisor can defend and one that contradicts the client to their face.
- **Every fact carries its source session and date.** A fact that cannot be traced does not go on a report titled "everything we know".

---

## Third parties — the judgement that matters most

Named third parties are not clients. They never sat in the meeting, never agreed to be recorded, and have no relationship with the firm. They appear on this map **only** where they are financially consequential to the household — and that test is narrower than it feels while extracting.

**Financially consequential means the person has a role in the household's money or plan.** Concretely, one of:

- **A stake in the estate** — beneficiary, heir, trustee, executor, successor, someone deliberately excluded.
- **A financial dependency in either direction** — a dependent, someone being supported, someone the household expects to support, someone whose care costs land on this plan.
- **Shared ownership or liability** — co-owner, co-signer, business partner, joint account holder, co-borrower.
- **Decision influence** — someone who is consulted before the household commits, or who has been named as the reason a decision went one way.
- **A professional role** — CPA, attorney, insurance broker, banker, business counsel.
- **A named cause of a plan change** — the person whose situation moved a retirement date, a gift, an estate structure, or a goal.

**Everything else is context about a stranger, and it does not enter the map.** The client's brother's divorce is not a fact unless the brother is a beneficiary, a co-owner, or the reason for an estrangement that shapes the estate plan. A colleague's job loss, a friend's second marriage, a neighbour's renovation, a sibling's parenting — none of these become records, however clearly the client said them.

**Where a third party does qualify, store only what the qualifying role requires:** who they are, their relationship to the household, the role that made them consequential, and the fact that carries the financial consequence. Not their finances, not their marriage, not their character, not the client's opinion of them beyond what the plan turns on. *"Dan — Nate's brother, estranged, deliberately excluded from the trust"* is the whole record. His divorce, his job, and why the estrangement happened are not on it.

**Drop non-qualifying detail at extraction, not at render.** Filtering later means it was stored, and stored means it can leak through an export, a document, or a bug. If it does not pass the test above, it never becomes a record in the first place.

---

## Sensitivity flags

`sensitive: true` is set **at extraction**, never retrofitted. A flagged fact is usable internally and is **excluded entirely from any client-facing render or export** — not softened, not summarised, excluded.

**Always sensitive:**

- **Health** — diagnoses, treatment, prognosis, cognitive decline, disability, mental health, addiction, fertility, end-of-life planning. Including a third party's health where it is financially consequential: a dependent's care needs, a spouse's diagnosis driving a retirement date.
- **Family rupture** — estrangement, exclusion from an estate, disinheritance, family conflict, a relationship the client described as difficult.
- **Relationship instability** — separation, divorce in progress, an affair, a prenuptial dispute, second-family tension.
- **Legal and financial distress** — bankruptcy, litigation, tax disputes, creditor pressure, a criminal matter.
- **Anything about a dependent's capacity** — a special-needs situation, a child's addiction or instability, a beneficiary the client does not trust with money.
- **The client's own words about another family member's character or competence.**

**When in doubt, flag it.** A fact wrongly marked sensitive costs the advisor one absent line in a client-facing render. A fact wrongly left unflagged puts a diagnosis or an estrangement on screen during a review. Those are not comparable errors, and the flag is free.

---

## What the client-facing version cannot contain

Beyond every `sensitive: true` fact, a client-facing render also drops:

- **Corroboration labels.** "Stated once" and "stale" are internal quality signals. A client reading that a fact about their own life is poorly corroborated learns only that the firm keeps unreliable notes.
- **The blind-spot count and the unknowns panel.** "Six of nine domains covered" and "we've never met your daughter" are internal prompts. Facing the client they read as an admission of neglect.
- **Advisor-side reads** — anything about how to approach them, what they respond to, or how they decide.
- **Anything about a third party beyond their name and role**, even where it passed the consequential test.

Where dropping a fact leaves a panel empty in the client-facing version, **the panel is omitted, not padded**. An empty section is honest; a filled one is invention.
