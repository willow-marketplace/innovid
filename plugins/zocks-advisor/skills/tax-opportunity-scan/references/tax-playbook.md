# The tax playbook

Read in **two passes**, never one. Part 1 and Part 2 before extraction. Parts 3 and 4 — the rules — once the household's tax shape is known, and only the rules whose preconditions the fact pattern can actually meet. Loading 28 rules before knowing whether anyone in the household has business income spends the advisor's context on rules that cannot fire.

This file is what separates a tax report from a restated meeting note.

---

## The two disciplines that apply to every rule

**Verify, don't assert.** Most tax rules here depend on something the conversation never confirmed — whether the plan permits a second vehicle, what the entity election actually is, what the basis is. Where that's true the opportunity is phrased as *check*, *confirm*, or *ask*. Recommending the investigation is correct. Asserting the conclusion is malpractice with a nice interface.

**Never state current-year figures as law.** No contribution limits, no catch-up amounts, no bracket boundaries, no IRMAA thresholds, no required-distribution ages, no exclusion amounts, no gift or estate exemptions, no phase-out ranges. Name the **mechanism**; instruct verification against current-year figures for this household's own filing position. Limits change every year and a skill that hardcodes one is wrong on a schedule.

A third discipline is specific to this skill: **this is advisor intelligence, not advice, and not a return.** The output tells an advisor where to look and what to ask. It never computes a liability, never asserts an eligibility, and never substitutes for the household's tax preparer.

---

## Part 1 — The tax fact pattern

Nine domains. Every field carries **the value, the meeting it came from, and the date of that meeting**. A field with no evidence stays null, and **a null field cannot fire a rule** — that single discipline is what keeps the output defensible.

```
1  income_events[]        source, approximate amount, one-off or recurring, whose,
                          year attributed, stability, expected change
2  compensation[]         employer, employer_type, role, tenure, W-2 or self-employed,
                          bonus or variable pay, deferred comp, benefit elections
3  equity[]               instrument (options/RSU/ESPP/units), grant date, vest schedule,
                          expiry, exercised?, concentration, employer
4  retirement_contrib[]   account type, sponsor, owner, annual contribution, balance,
                          employer match, plan features known?, catch-up eligible?
5  charitable[]           amount, cadence, recipient, vehicle (cash/DAF/appreciated/QCD),
                          stated intent, legacy intent
6  real_estate[]          property, role (primary/rental/second/land), purchase or sale,
                          timing, basis known?, improvements, mortgage, exchange intent
7  business[]             entity name, structure (unknown is the common case), owner,
                          revenue or net income, employees?, exit intent, timing
8  estate_intent[]        will status and last update, trust, POA, beneficiaries reviewed?,
                          stated legacy intent, expected inheritance in or out
9  life_events[]          event, date or window, tax consequence, whose, handled?

household_context         members with DOB, filing posture, state of residence,
                          dependants and their stages, advisor-known professionals (CPA?)
```

**Ages come from DOB, never from a stated age.** Zocks' profile payloads carry an age computed at the time of analysis and it goes stale — a profile written last year can say 50 for someone who is now 52. Catch-up bands, distribution ages and education timing all turn on age, so compute from the DOB and the current date every run. Where only a stated age exists with no DOB, treat it as approximate and say so.

**Facts are cumulative and dated.** A balance stated 18 months ago is a fact with an age. Where a later meeting supersedes an earlier one, the newer wins — and the *change* is frequently the bigger finding. Income that moved, a retirement date that slipped, a business that grew: each one re-fires rules the old value didn't.

**Amounts stay in the form they were said.** "$120,000 to $130,000" is a range and stays a range. "Roughly $4,000 in dividends" keeps its "roughly". Never midpoint a range to make arithmetic tidier — the tidy number is the one that ends up in front of a client.

---

## Part 2 — Routing: which rules can fire

Read only the rule blocks whose gate the fact pattern opens.

| Gate — true if the fact pattern holds… | Read |
|---|---|
| Any earned income or sponsored plan | CY1–CY4 |
| Any taxable investment account | CY5–CY7 |
| Any charitable giving or stated giving intent | CY8–CY9 |
| Any equity instrument, or an employer type that grants them | CY10 |
| Any dependant in or approaching post-secondary education | CY11 |
| Any business or self-employment income | CY12, MY7, MY8 |
| Any property owned, sold, or planned | CY13, MY9 |
| Any member near or past distribution age, or with a dated election | CY14, MY5, MY11 |
| A gap between stopping work and drawing income | MY1–MY3 |
| Multiple account tax-types, or income starting inside 5 years | MY4, MY6 |
| Any inheritance expected, received, or estate intent on record | MY10, MY12 |
| Members with differing work horizons, or a stated move | MY13, MY14 |

**Sweep every gate the pattern opens, including the ones that look off-shape.** The misses that matter are usually the ones nobody expected to find: the academic with an unchecked second deferral vehicle, the household with two children in college and no education account anywhere on record.

---

## Part 3 — Current-year rules (CY1–CY14)

Horizon: this tax year and the elections that close with it. Everything here has a **deadline inside twelve months**, which is what earns it the first half of the report.

**CY1 — Sponsored plan capacity unused.** Fires when an earner has a sponsored plan and a stated contribution below the plan's own ceiling, or a plan with no contribution figure at all. Mechanism: deferral reduces this year's taxable income and compounds untaxed; the year's capacity does not carry forward. Sizing: the gap between the stated contribution and the current-year ceiling, times their marginal position. → **Sized** where a contribution figure exists.

**CY2 — Catch-up band unclaimed.** Fires when a member's DOB puts them in or near the age band permitting additional deferral and their stated contribution sits at or below the standard ceiling. Mechanism: an extra tranche of deferral available on age alone, unclaimed each year it isn't used. Verify the current-year band and amount. → **Bounded**.

**CY3 — A second deferral vehicle nobody checked.** Fires on an employer type that commonly offers a second plan alongside the first — public universities, school districts, state and municipal employers, hospital systems, large non-profits — where the record mentions only one. Mechanism: where a 457(b) sits alongside a 403(b), the two carry **separate** ceilings, and a household can roughly double deferral without ever having been told the second plan exists. **This is a verify rule and among the highest-value misses in a real book, because it is invisible to everyone including the client.** → **Bounded** — a second full ceiling per year.

**CY4 — Self-employment income with no vehicle attached.** Fires when a member has business, consulting, freelance or royalty income and no retirement account attached to it. Mechanism: SEP-IRA, solo 401(k), and defined-benefit structures each carry different capacity, different deadlines to *establish* versus to *fund*, and different administrative weight; the right answer turns on net income and whether there are employees. The deliverable is the analysis, never the product. → **Bounded**.

**CY5 — Recurring distributions taxed in a taxable account.** Fires when taxable holdings throw dividends, interest or fund distributions the household reinvests rather than spends. Mechanism: a recurring, avoidable tax cost on income they never took; asset location is the fix. Sizing: the stated annual distribution figure, times their marginal position, **recurring every year until fixed**. → **Sized** whenever a distribution figure was stated.

**CY6 — Losses never harvested inside the year.** Fires when taxable holdings exist and harvesting appears nowhere on record. Mechanism: realise losses against gains, carry the excess forward. Sizing needs positions, not balances — name the statement that would produce the number. → **Unsized** without holdings detail.

**CY7 — Gains unharvested in a low-rate year.** The mirror of CY6, and the one advisors skip. Fires when taxable appreciated holdings coincide with an unusually low income year — a sabbatical, a business loss, a gap between roles, a first year of retirement. Mechanism: realise gains deliberately into space that will not exist next year, resetting basis at low or no cost. → **Bounded**.

**CY8 — Giving from the wrong pocket.** Fires when charitable giving appears alongside appreciated taxable holdings, or alongside a member at distribution age. Mechanism: the same gift, made in appreciated shares or directly from a pre-tax account, changes its tax character entirely — the household gives the same amount and keeps more. Sizing: the annual gift, against the avoided gain or the excluded income. → **Sized** where a giving figure exists.

**CY9 — Giving spread thin across years.** Fires when regular modest giving coincides with a high-income year. Mechanism: concentrating several years of intended giving into one high-income year, often through a donor-advised vehicle, while giving to the recipients on the household's own schedule. → **Bounded**.

**CY10 — An equity date inside the year.** Fires on any mention of options, restricted units, purchase plans, or a vesting schedule. Mechanism: vest dates, exercise windows, expiry, purchase-plan enrolment and post-grant elections all carry **absolute** deadlines, and the tax character differs by instrument. Expiry is unrecoverable. Sizing: value at risk, and concentration as a share of what's on record. → **Sized** where a value exists; always **highest urgency in this horizon**.

**CY11 — Education funding with no tax-advantaged vehicle.** Fires when dependants are in or approaching post-secondary education and no education account appears anywhere on record. Mechanism: dedicated education vehicles change the tax treatment of money the household is *already spending*, and some carry a state-level benefit even on funds passing straight through. Credits and the coordination between them are a current-year filing question for their preparer. **Where the firm's own book uses these vehicles and this household has none, say so** — see the Book-Relative Vehicle Gap in `extraction.md`. → **Bounded**.

**CY12 — Business income with no structure on record.** Fires when business or consulting income exists and the entity structure was never stated. Mechanism: structure determines self-employment tax treatment, deduction eligibility and deferral capacity, and some elections must be made for a year rather than after it. **Never infer the structure** — the whole point is that nobody asked. → **Unsized**, and the ask is a single question.

**CY13 — A property transaction with a current-year consequence.** Fires on a property bought, sold, converted, inherited or listed this year. Mechanism: exclusion tests, basis and improvement records, exchange clocks and installment treatment all attach to the transaction year and some to a window measured in days. → **Bounded**.

**CY14 — A distribution or election due this year, unconfirmed.** Fires when a member is at or past the age where distributions become mandatory, or holds an inherited account, or has a benefit or enrolment election with a date this year. Mechanism: a missed or under-taken required distribution carries penalty exposure and a correction process of its own. **Treat as a compliance obligation, not an opportunity** — it goes in the report even when nothing else does. → **compliance flag**, always surfaced.

---

## Part 4 — Multi-year rules, 3–5 years (MY1–MY14)

Horizon: decisions whose value comes from *sequencing across years*. Nothing here has a deadline this month, and everything here gets worse by waiting.

**MY1 — The conversion window.** Fires where the fact pattern shows a gap between employment income stopping and mandatory income starting, with meaningful pre-tax balances. Mechanism: years of artificially low taxable income are the cheapest years this household will ever have for moving pre-tax money into Roth treatment. **The window closes one year at a time and no later advice recovers a year that passed.** → **Sized** where a pre-tax balance exists.

**MY2 — The window is outside the horizon, and that is the finding.** Fires when MY1's shape exists but the window opens beyond five years. Mechanism: nothing to do this year — but the decisions taken *now* about where new savings go determine how much pre-tax balance will need moving later. Say the window's dates plainly and put it on the record rather than silently dropping it. → **Bounded**. Never inflate an out-of-horizon window into an urgent one.

**MY3 — Surcharge lookback sequencing.** Fires when a planned conversion, sale or large distribution sits within the lookback period used to set income-related premium surcharges. Mechanism: a single large realisation can raise both members' premiums two years later, which is exactly when nobody remembers why. Verify current thresholds and the applicable lookback. → **Bounded**.

**MY4 — No withdrawal order on record.** Fires with multiple account tax-types and nothing anywhere about the order money will come out. Mechanism: the sequence across taxable, pre-tax and Roth changes lifetime tax materially and compounds; it is also one of the few analyses a household cannot do for themselves. → **Bounded**.

**MY5 — The distribution ramp, unmodelled.** Fires when a member is inside roughly ten years of mandatory distributions with substantial pre-tax balances. Mechanism: distributions become compulsory and stack on top of everything else, often pushing a household into a higher position in retirement than they occupied while working. Verify the applicable start age for their birth year. → **Sized** where a pre-tax balance exists.

**MY6 — The survivor's position, unmodelled.** Fires in pre-retirement or later where income, pension, or account ownership is uneven between members. Mechanism: on a first death the survivor keeps much of the income, loses the joint filing position, and can face a *higher* rate on *less* money — plus a surcharge that follows two years behind. **The single most commonly skipped analysis in advisory work.** → **Bounded**.

**MY7 — Entity structure with a multi-year consequence.** Fires when business income exists at a scale where structure changes the answer. Mechanism: election choice affects treatment for years, some elections are dated, and changing later is not always free. Requires the preparer. → **Unsized** without net income.

**MY8 — A business exit inside the horizon.** Fires on any stated intent to sell, wind down, hand over or step back from a business. Mechanism: deal structure, timing across tax years, installment treatment and basis all move the outcome by more than the price negotiation usually does — and every one of them is decided *before* a sale, not after. → **Bounded**, and it needs the CPA and the attorney early.

**MY9 — A planned property transaction.** Fires on a stated intent to sell, buy, convert to rental, or relocate a property inside the horizon. Mechanism: holding-period and residency tests, basis and improvement records, exchange structure. The records that establish basis are also the ones that get lost. → **Bounded**.

**MY10 — An inheritance in or out.** Fires when an inheritance has been received, is expected, or the household intends to leave one. Mechanism: basis treatment on inherited assets differs sharply by asset type, inherited retirement accounts carry their own distribution horizon, and money already received carries a character worth knowing before it's spent or invested. → **Bounded**.

**MY11 — A dated, irreversible election.** Fires on any pension, annuitisation, deferred-compensation distribution, or benefit election with a date attached. Mechanism: usually **permanent once made**, and frequently it sets a surviving member's income for life. **Highest urgency class in the playbook regardless of horizon** — if one of these exists it leads the report. → **Bounded**, always.

**MY12 — Estate intent with no structure behind it.** Fires when a stated intention — an equal split, a specific child, a charity, keeping a property in the family — has no document or vehicle behind it, or the documents predate a material change. Mechanism: intent without a mechanism is not a plan, and the tax and basis consequences of the default outcome are usually not the ones intended. A will that predates a child is a live finding, not housekeeping. → **Bounded**.

**MY13 — Members on different work horizons.** Fires when members intend to stop at different times, or one never intends to stop. Mechanism: staggered income changes the conversion window, the claiming order, coverage continuity and the household's rate position in every year of the gap — and a plan built around one member's date is wrong for the other. → **Bounded**.

**MY14 — A stated move with a tax consequence.** Fires on any stated intent to relocate, or a current residence whose treatment differs materially from a likely destination. Mechanism: residency changes the state-level treatment of income, conversions and sales, and part-year rules turn on dates and documentation. **Fires on a stated intent only — never on a guess about where someone might retire.** → **Bounded**.

---

## Part 5 — Grading impact

**There is no score.** A number implies a precision that facts of varying age, extracted from conversation, cannot support — and it flattens the only thing that actually decides order, which is whether a door is closing. Instead every opportunity carries one of three grades:

| Grade | Meaning | What must be shown |
|---|---|---|
| **Sized** | A stated amount the mechanism acts on, so a magnitude can be stated | The arithmetic, in full, with the source fact and its date. "Roughly $4,000 of dividends, taxed at their marginal position, every year until the location changes" |
| **Bounded** | Real, and the shape of the number is known, but the figure needs a document | The bound, and the exact document that would size it |
| **Unsized** | Real opportunity, no number possible from the record | The one question or document that converts it to Sized |

**Never promote a grade to look more useful.** An Unsized item honestly labelled is worth more than a Sized one built on a midpointed range, because the advisor can act on the first and will eventually be embarrassed by the second.

---

## Part 6 — Ordering within each horizon

No score, no weighting formula. Order by this chain, in sequence:

1. **Irreversible with a date** — an equity expiry, a pension or distribution election, an exchange clock. Once passed, no later advice recovers it.
2. **Closes at year end** — this year's deferral capacity, a giving decision, a realisation into this year's rate position.
3. **Compliance exposure** — a required distribution unconfirmed, a filing obligation created by an event. Not an opportunity; it still outranks one.
4. **Compounding and quantified** — tax drag, unclaimed deferral, asset location. Real, sized, and still there next quarter.
5. **Structural** — entity elections, withdrawal order, survivor position. Highest lifetime value, lowest urgency.

Within a tier, **Sized outranks Bounded outranks Unsized**, and within a grade, the larger stated exposure leads.

**One override:** a dated irreversible election surfaces at the top of the report regardless of horizon or size. It is the only thing in this skill that jumps the queue.

---

## Part 7 — Merging

Rules sharing one deliverable merge into one opportunity. The merged item is almost always stronger than any component, and merging is what stops the report reading as a list of nineteen small things.

| Merge | Becomes |
|---|---|
| CY1 + CY2 + CY3 + CY4 | One household deferral-capacity review for the year |
| CY5 + CY6 + CY7 | One taxable-account review — location, losses and gains together |
| CY8 + CY9 + MY12 | One giving-structure conversation, current year and legacy |
| MY1 + MY3 + MY6 | One multi-year plan: the window, the surcharge and the survivor. **Usually the most valuable thing this skill produces** |
| MY4 + MY5 | One drawdown-sequencing analysis |
| CY12 + MY7 + MY8 | One business-structure and exit review |
| CY13 + MY9 | One property review |

A merged opportunity takes the **highest urgency** and the **best grade** of its components, and lists every component fact.

---

## Part 8 — When the answer is thin

If few rules fire, that is the correct output and it is not a failure. Say which gates never opened and what single document or question would open them — on a new relationship that is usually the prior-year return, and naming it is more useful than nineteen speculative lines.

**Never pad.** No generic tax advice, no "consider reviewing your withholding", nothing that could have been written without reading a single meeting. A short report that names three real things and four honest gaps is the product. A long one padded to look thorough is the failure this skill is built against.
