# Tax extraction and the computed metrics

Read after `ai-result-shapes.md` and before building the fact pattern. That file classifies payloads for the whole library; this one says which fields carry **tax** signal and what to derive from them.

---

## The two payloads that carry most of the tax value

**`Tax Planning` and `Equity Compensation` are named keys in the planning-domain payload.** This is the skill's primary source and the single hardest thing to replicate from anywhere else — a structured, per-meeting, advisor-context record of what the tax conversation actually established. Read them first, every time.

The planning-domain payload is an object whose keys are domain names. Observed live: `Profile & Goals`, `Retirement Forecasting`, `Investment Management`, `Tax Planning`, `Equity Compensation`, `Education Planning`, `Estate Planning`, `Cash Flow & Debt Management`, `Insurance & Risk Management`. Each maps to an array of statements.

**An empty array is a finding, not an absence of data.** `"Equity Compensation": []` on a household whose employer type commonly grants equity means the domain was carried on the template and nothing was recorded against it. That is a coverage gap with a name, and it belongs in the gap register. Distinguish three states and never collapse them:

| State | Means |
|---|---|
| Key present, array populated | Territory covered, with content |
| Key present, array empty | Territory on the template, nothing established. **A gap** |
| Key absent entirely | The template didn't carry it. Not evidence either way |

**The per-client financial profile** (`# Financial Profile: …`) is the richest single payload for domains 1–4: employment, contributions, custodians, balances and ranges. Two quirks, both live:

- **It duplicates.** The same meeting commonly returns two near-identical profiles for one member. Deduplicate by member before extracting or every balance appears twice.
- **It is written per member, and a member can be missing.** A meeting may carry a profile for one spouse and none for the other. Absence of a profile is not absence of facts about that person — check the other payloads before concluding anything.
- **Its stated age is computed at analysis time and goes stale.** Take the DOB and compute the age yourself against today. Never take the stated age; catch-up bands and distribution ages turn on it.

---

## Where each of the nine domains comes from

| Domain | Primary | Secondary |
|---|---|---|
| 1 Income events | Financial profile; `Tax Planning`; life events | Concerns |
| 2 Compensation | Financial profile (employer, role, tenure) | `Profile & Goals`; personal facts |
| 3 Equity | **`Equity Compensation`** | Topic initiation `subTopic: "Stock Options"` |
| 4 Retirement contributions | Financial profile; `Retirement Forecasting` | Topic `subTopic`: `403b`, `401k`, `IRAs` |
| 5 Charitable | `Tax Planning`; `Estate Planning`; family payload | Personal facts |
| 6 Real estate | Financial profile; `Cash Flow & Debt Management`; life events (`Relocation`) | Personal facts (address) |
| 7 Business | `Tax Planning`; life events (`Career - Entrepreneurship`) | Topic `Business Ownership` |
| 8 Estate intent | `Estate Planning`; family payload | Life events (`Estate Planning`) |
| 9 Life events | Life-events payload | Family payload; concerns |

**Life-event keys are not a stable vocabulary.** The same book returns `Inheritance Received` and `Inheritance`, `Children - College Enrollment` and `Children - College`, across two meetings weeks apart. Match on the **stem**, never on an exact key, and never assume a key you saw last meeting will be there this one.

**The family payload carries tax facts about people who are not clients.** Dependants' ages, schooling and cost splits; a deceased parent's estate and what came from it; who is a beneficiary. Domains 5, 8 and 9 are frequently richer here than anywhere else.

**Two payload shapes to skip outright:** the narrative meeting summary and the follow-up email draft. Everything in them exists structured elsewhere, shorter. The advisor coaching scorecard is also skipped — it grades the advisor, not the household.

**One shape is not in the shared file:** an object with `topicQuestions` and `faqQuestions`. `faqQuestions` holds questions the household actually asked, in their own words. Where one is about tax it is the strongest possible evidence that this territory is live for them, and it is quotable. Use it; don't force it.

---

## The four computed metrics

These are the derivation layer. Each is computed from Zocks data only, each is named on the report, and none of them can be produced from a folder of transcripts.

### 1. Raised and Dropped

**The headline metric.** For each tax-relevant topic or fact the *household* surfaced — `initiatedBy` matching a member's contact id, a concern in their own words, a question in `faqQuestions` — check whether anything ever landed against it:

- a task in that meeting or any later one whose `relatedTo` or text references it
- a statement recorded under the matching planning domain, in that meeting or later
- a next-meeting reference whose stated purpose is that territory

```
raised_and_dropped = tax topics the household surfaced
                     with no task, no domain statement, and no meeting purpose
                     against them in that meeting or any subsequent one
```

Report as a count and a list, each with the date it was raised and the number of meetings that have passed since. **This is the section a sceptical advisor reads first**, and it is the one thing in the report that is unambiguously about the firm's own follow-through rather than the client's situation. Write it neutrally — it is a prompt, not an indictment, and a topic can be dropped for perfectly good reasons.

**Do not count the current meeting alone as dropped** if it is the most recent one and the follow-up email or task set hasn't been generated yet. One meeting of silence is not a pattern; two is.

### 2. Tax Disclosure Depth

```
depth = domains (of the 9) with at least one dated fact on record / 9
```

Report as a fraction with the missing domains **named**: *"5 of 9 — nothing on record for equity, charitable giving, real estate or business structure."* The named absences are the skill's question list for the next meeting, and on a young relationship this metric is frequently the most useful line in the report.

A domain counts as covered only with a **dated fact**, never with a null or an empty planning-domain array.

### 3. Record Currency

The age, in months, of the newest load-bearing fact **per domain** — computed against the meeting date it came from.

Two thresholds worth stating out loud:

- **Any income, contribution or balance fact older than about 12 months** makes every current-year opportunity that rests on it *provisional*. Say so on the item, not just in the footer.
- **Where the newest fact in a domain predates a known life event** in another domain, flag the contradiction. A contribution rate stated before a business started is a fact about a different household.

### 4. Book-Relative Vehicle Gap

The cross-entity join, and the strongest single moat item in the skill. One `insights_query_insights` call gives `investment_product_stats` — how many of the firm's meetings in the window touched each vehicle: `529s`, `IRAs`, `401k`, `403b`, `Annuities`, `Stock Options`, and others.

```
for each vehicle used across the firm's book in the window:
    if this household's fact pattern opens the gate for it
    and the vehicle appears nowhere on their record
        → a Book-Relative Vehicle Gap
```

Rendered as one line per gap: *"529s came up in 9 of the firm's 24 meetings this quarter. This household has two children in college and no education account anywhere on record."*

**Firm numbers are labelled as firm numbers, always** — "across the firm", never "you" or "your book". And the gap is evidence that a *conversation* hasn't happened, never evidence about what the household owns: they may well hold the vehicle and have never mentioned it. Phrase it as a question to ask, not a fact established.

---

## Household assembly — the trap this book actually contains

The shared household rules in `ai-result-shapes.md` apply in full. One pattern is worth calling out because it is live in the data and it silently halves a tax analysis:

**The same person appears under two contact ids** — one complete record with surname and email, one partial with a first name only and neither. They co-attend, so both ids appear in participant lists and in `relatedTo` fields on tasks. Merge on normalised first name plus co-attendance before any arithmetic. Unmerged, the household's meeting history reads as two shorter ones, tasks assigned to the partial id look unowned, and Raised-and-Dropped fires on topics that were in fact followed up under the other id.

**Shared integration identifiers are a household signal.** Where two contacts carry the same external document-store id, that is corroboration for a household merge — weaker than co-attendance plus surname, but it holds up where a surname differs.

---

## What never enters the fact pattern

- **A structure, a rate, an election or a balance that was never stated.** Business entity type is the one this skill will be most tempted by, because half the rules would be sharper with it. It is never inferred. The absence *is* CY12.
- **A midpointed range.** "$120,000 to $130,000" stays as it was said, in every line it appears in.
- **An age from a stated age.** DOB, or approximate and labelled.
- **A professional the household never mentioned.** Where a rule needs a CPA and none is on record, "there is no tax preparer on file for them" is the finding — and on a household with business income it is a significant one.
- **A residence or filing status inferred from anything other than a stated fact.** An address on record is a stated fact. A retirement destination mentioned as a holiday preference is not.
