# Output template — the two-horizon tax report

Read with `output-modes.md` before rendering. This covers what goes on the page and how it reads. Palette, type and layout are yours per run, inside the firm's brand kit.

---

## Voice

This is **advisor intelligence, not client-facing advice, and never a tax opinion**. Every line should read like the sharpest planner in the firm telling a colleague where to look before the next meeting.

- **Never mention mechanics.** No "AI results", "payloads", "CY3 fired", "gate", "grade", "the coverage map". Internally it's CY3; on the page it's *"Texas Tech is a public university, and public universities frequently offer a 457(b) alongside the 403(b) — two separate ceilings. Nobody has asked."*
- **Name mechanisms, never statutes.** No limits, no thresholds, no bracket boundaries, no exemption amounts, no distribution ages stated as current law. Always instruct verification against current-year figures.
- **Every fact carries its meeting and its date**, linked. A tax fact with no source doesn't belong on the page.
- **Say "check", "confirm", "ask" wherever the record doesn't hold the fact.** Most of this report is a list of things to go and find out, and phrasing that honestly is what makes it usable.
- **Firm numbers are labelled firm numbers** — "across the firm", never "you".
- **Ranges stay ranges.** Approximations keep their "roughly".

| ✗ | ✓ |
|---|---|
| "CY5 fired: taxable account tax drag, Sized." | "Their Fidelity and Vanguard accounts threw roughly $4,000 in dividends last year — taxed at their marginal position, on money they're reinvesting anyway. It recurs every year until the location changes." |
| "Contribution below the 2026 limit of $X." | "He's contributing about $20,000 a year to the 403(b). Worth checking that against this year's ceiling and his catch-up eligibility — he turned 55 in March." |
| "Estate planning gap detected." | "The will hasn't been updated since Mark was born in 2008. Two children later, and one of them is now an adult." |
| "Recommend 529 analysis." | "529s came up in 9 of the firm's 24 meetings this quarter. This household has two children in college and no education account anywhere on record." |

---

## Section order

**1. Header.** Household display name, each member linked to their contact record, ages computed from DOB, filing posture and state where stated. Meetings read against meetings on record. Window covered. Date built. Firm logo and name from the brand kit. One line naming the scope: *this advisor's own book*.

**2. Household summary.** Six to eight lines, no more. What this household's tax shape actually is — the income sources, the account types, the dependants, the business, the events in flight. Written so an advisor who has never met them could walk into the meeting. **Not a meeting recap.**

Directly beneath it, the two coverage numbers as a single quiet line: Tax Disclosure Depth with the missing domains named, and the Raised-and-Dropped count.

**3. Current-year opportunities.** Ranked per the playbook's chain. Each item:

- **The insight in one sentence** — the thing itself, not the vehicle.
- **Impact** — Sized / Bounded / Unsized, with the arithmetic shown in full where Sized, or the exact document that would size it where not.
- **The deadline** — the date or the event, and what happens if it passes.
- **First surfaced** — the meeting it came from, linked, with its date, and where the household's own words carry it, the phrase itself. One phrase, never a paragraph.
- **What to verify** — the check that would confirm or kill it.

**4. Multi-year strategies, 3–5 years.** Same item structure. An out-of-horizon window (MY2) appears here with its dates stated plainly and is never dressed up as urgent.

**5. Compliance flags.** Only where CY14 or an equivalent fires. Separate from the opportunity lists, because it isn't one — it's an obligation. Omit the section entirely when nothing fires.

**6. Raised and dropped.** Each item: what the household raised, when, in whose words, and how many meetings have passed with nothing against it. Neutral tone. Omit the section if the count is zero — an empty one reads as a boast.

**7. Data gaps.** The register, written as **questions to ask**, not fields that are null. Ordered by how many opportunities each gap would unlock. The prior-year return usually leads on a young relationship, and saying so is more useful than any speculative item above it.

Where a needed professional isn't on record — a CPA on a household with business income, an attorney on an estate intent — that goes here and it is frequently the most actionable line in the report.

**8. Firm read.** One line, labelled firmwide, from insights. Any Book-Relative Vehicle Gaps go here or against the item they support.

**9. Actions.** The Zocks link on every fact and every meeting — those are real navigation and they always work.

The follow-up commands (`gaps`, `detail`, `handoff`) are handled by **feature detection, decided at render time**, and getting this wrong is the most common way this report loses trust:

- **`sendPrompt` is available** (inline widget) → render them as real buttons. Tapping genuinely sends the command, so they have earned the affordance.
- **`sendPrompt` is not available** (standalone HTML artifact) → render them as **plain inline text the reader can see is text**. Prose, or an obviously-typed command in a sentence like *"Ask for `handoff` and I'll write the CPA brief."* Never a pill, never a chip, never a bordered box, never anything with a hover state or a pointer cursor.

Write the artifact so it detects this at load and upgrades — one small script that swaps text for buttons when the host supports it. Do not pick one shape at authoring time and hope.

**A phrase styled like a button that isn't one is a broken control**, and `output-modes.md` bans those without exception. The advisor taps it once, nothing happens, and they stop trusting every other control on the page — including the Zocks links, which do work. If in any doubt at all, render it as text: an obviously-typed command loses nothing, and a dead button costs the whole report.

Nothing else in this section. No export, no email, no push-to-CRM, no copy-to-clipboard.

**Footer.** Meetings read against meetings on record, anything not reached, and the standing line: *nothing here is a tax opinion; verify all figures and eligibility against the current year and the household's own return.*

---

## Interactive behaviour

Three things have to work, and all three are audit affordances rather than decoration:

- **Each opportunity expands** to the full fact chain — every fact, its meeting, its date, and its age.
- **The fact pattern expands by domain**, nulls shown as unknown rather than hidden. The gaps are frequently the most actionable thing on the page.
- **Sized items show their arithmetic**, not just the result.

An advisor who can't audit a tax finding won't raise it with a client, and won't forward it to a CPA.

---

## Two failure modes to check before rendering

**Does any line assert a limit, threshold, age or eligibility as current law?** If so it's wrong, however right it looks. Replace with the mechanism plus the instruction to verify.

**Could a competent advisor have derived this from reading the last meeting's notes?** If the whole report could, it hasn't earned its place. The value is in the cross-meeting facts, the domains nobody has opened, the vehicle the rest of the book uses and this household has never been offered, and the thing they raised themselves that quietly went nowhere.
