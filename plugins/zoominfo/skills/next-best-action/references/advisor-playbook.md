# The advisor playbook

Read in full before every analysis. This file is what separates a recommendation from a restated task: the fact pattern the analysis is built from, the 33 rules that fire against it, the order to break ties in, and the discipline that stops the skill recommending something already underway.

---

## Part 1 — The fact pattern

Build this from the structured payloads (`references/ai-result-shapes.md`). Every field carries **the value, the meeting it came from, and its date**. A field with no evidence stays null. **A null field cannot fire a rule** — that single discipline is what keeps the output defensible.

```
household
  members[]            name, contact_id, DOB or age, role
  stage                accumulation | pre-retirement | transition | decumulation  (per member)
  years_as_client      from the earliest meeting on record

employment[]           per member: employer, employer_type, role, tenure, self-employed?,
                       business entity, expected end date
income[]               source, approximate amount, stability, per member

accounts[]             type (401k/403b/457b/IRA/Roth/HSA/529/taxable/HYS/pension/annuity),
                       sponsor or custodian, owner, contribution rate, balance, as_of date
holdings_character     what taxable accounts throw off: dividends, distributions, concentration
liabilities[]          type, balance, rate, term

children[]             name, age, stage, education cost and funding split, dependency
goals[]                statement, target amount, target date, horizon, whose goal, confidence
lifestyle_goals[]      the things the money is for, in their words

estate                 will exists?, last updated, POA, healthcare directive, trust,
                       beneficiaries reviewed?, stated legacy intent
insurance              life, disability, LTC, umbrella, group vs individual, gaps stated
life_events[]          event, date or window, financial consequence, handled?
health[]               only where financially consequential, per member
charitable             giving pattern, vehicle, intent
professionals[]        name, role, category, firm — the outside team
engagement             who initiated which topics, what they asked about, sentiment by asset class,
                       unresolved concerns
```

**Facts are cumulative and dated.** A balance stated 18 months ago is a fact with an age. Where a later meeting supersedes an earlier fact, the newer wins — and the *change* is often a bigger finding than the value: a retirement age that moved from 62 to 65 changes every rule in Domains 2, 3 and 4 at once.

---

## Part 2 — Life-stage routing

| Stage | Marker | Rules that usually bite |
|---|---|---|
| **Accumulation** | Working, retirement more than roughly 15 years out | D1, D5, D6, D7 |
| **Pre-retirement** | Retirement inside roughly 10 years, peak earnings | D1, D2, D4, D6, D8 |
| **Transition** | Within a few years either side of stopping work | D2, D3, D4, D5, D8 |
| **Decumulation** | Drawing income, past or approaching required distributions | D3, D4, D7, D8 |

**Spouses can sit in different stages** — that's R31, and it's one of the most under-recognised planning situations in a real book.

Route to know what's *likely*, then **sweep all 33 rules anyway**. The misses that matter are almost always off-stage: the accumulation household with no beneficiaries recorded, the decumulation household with unused deferral room from consulting income.

---

## Part 3 — The rules

Each rule lists what fires it, the mechanism, how to size the stakes, and its taxonomy form. Two disciplines apply to every one:

- **Verify, don't assert.** Where a rule depends on something plausible but unconfirmed — whether the employer offers a plan, what's in the pension election, whether coverage exists — the action is to *check*. Recommending the investigation is correct; asserting the conclusion is malpractice with a nice interface.
- **Never state current-year figures as law.** No contribution limits, no IRMAA thresholds, no bracket boundaries, no required-distribution ages, no filing thresholds. Name the mechanism and instruct verification against current-year figures for the household's own situation. Limits change; the mechanism doesn't.

### Domain 1 — Deferral and tax space (R1–R5)

**R1 — Unused employer plan capacity.** Fires when an earner has a sponsored plan with a contribution rate below the plan's own ceiling, or contributions absent entirely. Mechanism: deferral reduces current taxable income and compounds untaxed. Stakes: the gap between current contributions and capacity, times marginal rate, times years remaining. → *send analysis*

**R2 — Missing catch-up capacity.** Fires when a member is in or near the age band where additional deferral is permitted and contributions sit below the standard ceiling. Mechanism: age-based additional deferral. Verify the current-year band and amount. Stakes: unclaimed deferral per year, compounding. → *send analysis*

**R3 — Unchecked supplemental plan.** Fires when the employer type suggests a second deferral vehicle may exist that the record never mentions — public universities, school districts, municipalities, hospital systems and non-profits often offer a 457(b) alongside a 403(b), and the two have separate ceilings. **This is a verify rule and one of the highest-value misses in the book**: the household may be able to roughly double deferral without knowing it. Stakes: a second full ceiling per year. → *specialist intro or follow-up meeting*

**R4 — Self-employment with no retirement vehicle.** Fires when a member has business or consulting income and no account attached to it. Mechanism: SEP-IRA, solo 401(k), or defined-benefit options, each with different capacity and administrative weight. The right answer depends on net income and whether they have employees — so the action is the analysis, not the product. Stakes: deferral capacity as a share of business income. → *send analysis*

**R5 — HSA treated as a spending account.** Fires when a high-deductible plan and an HSA exist and the balance is being drawn rather than invested. Mechanism: the only vehicle with untaxed contribution, growth and qualified withdrawal. Stakes: the long-horizon value of paying current medical costs from cash flow instead. → *send analysis*

### Domain 2 — Bracket and conversion windows (R6–R9)

**R6 — The conversion window.** Fires when there's a gap between stopping work and the start of required distributions or Social Security, with meaningful pre-tax balances. Mechanism: years of artificially low taxable income are the cheapest years the household will ever have to move pre-tax money to Roth. Stakes: the pre-tax balance, the number of window years, and the difference between filling brackets now and being forced later. **The window closes a year at a time and cannot be recovered.** → *send analysis*

**R7 — Bracket-filling opportunity in a low-income year.** Fires on a sabbatical, business loss, gap year, severance year, or a year with unusually low income. Mechanism: realise income deliberately — conversions, gains harvesting — into space that will not exist next year. Stakes: the space, times the rate differential. → *send analysis*

**R8 — Loss harvesting never mentioned.** Fires when taxable holdings exist and no harvesting appears anywhere in the record. Mechanism: realise losses to offset gains and carry forward. Stakes: unquantifiable without holdings detail — say so, and name the statement that would produce the number. → *send analysis*

**R9 — Charitable giving from the wrong pocket.** Fires when giving appears alongside appreciated taxable holdings, or alongside required distributions. Mechanism: giving appreciated shares, or a qualified distribution directly from a pre-tax account, changes the tax character of a gift the household is already making. Stakes: the annual gift, times the avoided gain or the excluded income. → *send analysis*

### Domain 3 — Decumulation sequencing (R10–R14)

**R10 — No withdrawal order on record.** Fires in transition or decumulation with multiple account types and nothing about drawdown sequence. Mechanism: the order money comes out of taxable, pre-tax and Roth changes lifetime tax materially. Stakes: frame as multi-year, name the account mix. → *send analysis*

**R11 — Required distributions approaching, unmodelled.** Fires when a member is inside roughly ten years of required distributions with substantial pre-tax balances. Mechanism: distributions become mandatory and stack on other income. Verify the applicable start age for their birth year. Stakes: projected distribution against current income. → *send analysis*

**R12 — Missed or under-taken distribution exposure.** Fires on any hint a required distribution was missed or partially taken. Mechanism: penalty exposure and a correction process with its own deadlines. **Treat as a compliance obligation, not an opportunity.** → *compliance touch*

**R13 — Premium surcharge sequencing.** Fires when income-based Medicare premium surcharges could be triggered by a planned conversion, sale, or distribution, given the two-year lookback. Mechanism: a large realisation can raise premiums for both spouses two years later. Verify current thresholds. Stakes: the surcharge, both members, and how long it persists. → *send analysis*

**R14 — Sequence-of-returns exposure at the worst moment.** Fires when drawdown starts within a few years and the allocation carries growth-weighted risk with no cash or bond ladder mentioned. Mechanism: withdrawals during an early decline permanently impair the portfolio. Stakes: express as the size of the early-withdrawal years against liquid reserves. → *send analysis or follow-up meeting*

### Domain 4 — Irreversible elections (R15–R18)

**R15 — Pension election with a deadline.** Fires whenever a pension or annuitisation choice is live. Mechanism: single life against joint-and-survivor, lump sum against income — usually **permanent once made**, and it determines the surviving spouse's income for life. **Highest urgency class in the playbook.** Stakes: the income difference, and the survivor's position under each option. → *follow-up meeting*

**R16 — Social Security timing unexamined.** Fires in pre-retirement or transition with no claiming discussion. Mechanism: claiming age changes the benefit permanently, and for couples the two decisions interact — the higher earner's decision sets the survivor benefit. Verify current rules. Stakes: lifetime differential, and explicitly the survivor's benefit. → *send analysis*

**R17 — Employer benefit election never reviewed.** Fires on open enrolment proximity, a job change, or coverage that has never been examined: group life multiples, disability definitions, whether coverage is portable. Mechanism: elections are annual and easy to miss for a year at a time. → *follow-up meeting*

**R18 — Equity compensation with dates attached.** Fires on any mention of options, RSUs, ESPP or a vesting schedule. Mechanism: expiry, vesting-driven concentration, and the tax character of each. Deadlines here are absolute. Stakes: value at risk, concentration as a share of net worth. → *send analysis*

### Domain 5 — Portfolio structure (R19–R22)

**R19 — Tax drag in taxable accounts.** Fires when taxable accounts hold funds throwing distributions or dividends the household doesn't spend. Mechanism: a recurring tax cost on income they're reinvesting anyway; asset location fixes it. Stakes: the annual distribution figure, times marginal rate, recurring. **The Reynolds-shaped finding: $4,000 of dividends in a taxable brokerage is a real, quantified, annual leak.** → *send analysis*

**R20 — Asset location never considered.** Fires when both taxable and tax-advantaged accounts exist and nothing addresses which assets sit where. Mechanism: income-producing assets belong in shelter, growth in taxable. Stakes: multi-year, proportional to the taxable balance. → *send analysis*

**R21 — Concentration risk.** Fires when one holding, one employer's stock, or one property dominates. Mechanism: idiosyncratic risk unrelated to the plan's goals. Stakes: the concentrated share of net worth. → *follow-up meeting*

**R22 — Idle cash beyond a reserve.** Fires when cash or savings materially exceeds a stated or implied emergency reserve with no purpose attached. Mechanism: opportunity cost against the household's own horizon — but check first whether it's earmarked, because recommending someone invest their tuition money is a trust-destroying error. → *send analysis*

### Domain 6 — Protection (R23–R26)

**R23 — Dependants without adequate life coverage.** Fires when dependent children or a financially dependent spouse exist and coverage is absent, group-only, or unquantified against obligations. Mechanism: the gap between obligations — education, mortgage, income replacement — and coverage. Stakes: the gap itself, which is usually the largest number the analysis will produce. → *send analysis or specialist intro*

**R24 — Income unprotected.** Fires when earned income funds the plan and disability coverage is absent or group-only. Mechanism: definition of disability, benefit period, portability, and whether the benefit is taxable. Stakes: income times years to retirement. → *specialist intro*

**R25 — Long-term care unaddressed.** Fires in pre-retirement or later with no LTC discussion, and fires harder where a parent's care has already touched the family — the household has seen the cost and will engage. Stakes: regional cost of care against portfolio. → *follow-up meeting*

**R26 — Liability exposure.** Fires on rental property, a business, a pool, teen drivers, or public-facing work with no umbrella coverage mentioned. Mechanism: a single event exceeding underlying limits reaches assets. → *specialist intro*

### Domain 7 — Estate and beneficiaries (R27–R30)

**R27 — Stale or absent estate documents.** Fires when no will exists, or the will predates a material change: a birth, a death, a move between states, a marriage, a divorce, a business. Mechanism: an outdated will distributes an outdated life. Stakes: frame in terms of what would actually happen today — guardianship of a minor, an intestate share, probate in the wrong state. **A will not updated since a child was born is a live finding, not housekeeping.** → *specialist intro*

**R28 — Beneficiaries unreviewed.** Fires when retirement or insurance accounts exist and no beneficiary review appears. Mechanism: beneficiary designations override the will. An ex-spouse on a 401(k) defeats every other document. **Compliance obligation as much as planning.** → *compliance touch*

**R29 — Legacy intent with no mechanism.** Fires when a stated intention — equal split, a specific child, a charity, keeping the house — has no document or structure behind it. Mechanism: intent without a mechanism is not a plan. → *specialist intro*

**R30 — Wealth transfer without a conversation.** Fires when a transfer is likely inside a decade and the next generation has never attended a meeting or been discussed. Mechanism: heirs who have never met the advisor rarely stay. Also the relationship risk the firm most consistently under-manages. → *life-event outreach*

### Domain 8 — Household resilience (R31–R33)

**R31 — Spouses in different life stages.** Fires when members sit in different stages: one retiring while the other keeps working, one with a pension and one self-employed, one intending to stop and one intending never to. Mechanism: staggered income changes conversion windows, claiming order, coverage continuity and cash flow all at once — and the plan built for one member will be wrong for the other. Stakes: name the specific mismatch and what it changes. → *send analysis or follow-up meeting*

**R32 — Survivor position unmodelled.** Fires in pre-retirement or later where income is uneven between members, or one member holds the pension, the coverage, or the earnings. Mechanism: on a first death, income falls, filing status changes, brackets compress, and premium surcharges can rise — the survivor can be worse off on less income. Stakes: the projected survivor income against current spending. **The most commonly skipped analysis in advisory work, and the one clients are most grateful for.** → *send analysis*

**R33 — Single point of financial knowledge.** Fires when one member holds all the accounts, all the logins, all the relationship, and the other has never attended a decision meeting. Mechanism: operational, not tax — the surviving or divorcing member cannot run their own financial life. → *follow-up meeting*

---

## Part 4 — Coverage subtraction

Before scoring, kill every opportunity whose territory is already owned. An opportunity dies when the coverage map holds any of:

- an **open task** referencing that territory — a task is never a candidate, only ever a killer
- a **substantive discussion** of it, meaning more than a passing mention
- an **agreed next step or decision** covering it
- a **booked follow-up** whose purpose is that territory

**Deferrals expire.** Parking language — "flag as a future priority", "revisit after the plan is done", "we'll look at it in the spring" with nothing attached — kills the opportunity for **90 days**, then stops counting. Territory parked twice is territory dying, and the analysis should say that in words: "estate has now been deferred in two consecutive meetings."

List killed opportunities compactly as *already in motion*. This section is what proves the analysis looked rather than missed, and it's the first thing a sceptical advisor checks.

---

## Part 5 — Merging

Rules sharing one deliverable merge into one recommendation. The merge is frequently stronger than any component:

| Merge | Becomes |
|---|---|
| R1 + R2 + R3 + R4 | One deferral-capacity analysis across the household |
| R6 + R13 + R32 | One multi-year tax plan — the window, the surcharge, and the survivor together. **Usually the single most valuable output this skill produces** |
| R10 + R11 + R14 | One drawdown and sequencing plan |
| R15 + R16 + R32 | One irreversible-elections review, survivor-first |
| R27 + R28 + R29 | One estate refresh |
| R19 + R20 | One asset-location analysis |
| R23 + R24 + R26 | One protection audit |

Merged recommendations take the **highest urgency** and the **summed materiality** of their components.

## Part 6 — Triage order for near-ties

1. **Irreversible deadlines** — pension elections, option expiry, enrolment windows. Once gone, gone.
2. **Expiring windows** — conversion years, low-income years, harvesting inside a tax year.
3. **Unpriced catastrophic risk** — no coverage against dependants, uninsured liability, no estate documents with minor children.
4. **Compounding inefficiency** — tax drag, unused deferral, asset location.
5. **Relationship and documentation** — beneficiary sweeps, next-generation contact, knowledge concentration.

Within a tier, materiality decides.

## Part 7 — When the answer is "nothing"

If every rule either fails to fire or dies to coverage, the correct output is **nothing material outstanding** — with the reason, the territory checked, and the one thing that would change the picture (usually a document or a figure). A household in active onboarding with a plan review already booked frequently lands here, and saying so is a stronger result than reaching for the ninth-best idea. **Never invent an action outside these rules to avoid an empty answer.**
