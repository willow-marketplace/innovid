---
name: tax-opportunity-scan
description: Builds a household's full tax opportunity picture from every analyzed meeting on record, across two horizons — current-year items with deadlines (deferral capacity, a second plan nobody checked, taxable-account drag, giving structure, equity dates, education vehicles) and multi-year strategies 3-5 years out (conversion windows, drawdown sequencing, the survivor's position, business structure, property sales). Ranks by impact, cites the meeting each item came from, names the tax questions nobody has asked, and flags what the household raised that went nowhere. Use whenever the user asks about tax planning, tax opportunities or tax exposure for a client, wants a tax review or CPA handoff, asks what they are missing on someone's taxes, mentions Roth conversions, deferral capacity, tax drag, harvesting, charitable giving or equity comp timing, or asks what to cover before year end or tax season. Advisor intelligence, not advice. Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Tax Opportunity Scan

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Tax Opportunity Scan — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not a tax return uploaded to the conversation, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

One household at a time. The advisor names a client; the skill rebuilds that household's entire **tax fact pattern** from every analyzed meeting on record, sweeps it against a tax playbook, and returns two ranked lists — what closes this year, and what has to be sequenced over the next three to five — with every item traced to the meeting it came from and graded honestly for how much of it the record can actually size.

It is deliberately **not** the one-recommendation skill. `next-best-action` picks a single move across every planning domain and subtracts anything already raised. This one does the opposite: it is exhaustive inside one domain, it surfaces items *already* on the advisor's list where the tax angle adds something, and it is built for a different moment — before a year-end review, before a CPA handoff, before tax season, or the first time anyone has looked at a household's tax picture properly.

The most valuable thing it produces is frequently not an opportunity at all. It's the list of tax questions nobody has ever asked this household, and the things they raised themselves that quietly went nowhere.

The unit is the **household** — spouses or partners are analyzed as one (say "spouses" or "partners", never "husband and wife"), because filing position, the survivor's position, and every window in the multi-year horizon are two-person questions.

---

## Ground rules

**`references/tax-playbook.md` is the skill's brain.** Read it in **two passes**: the fact pattern and the routing table before extraction, then only the rule blocks whose gates the household's facts actually open. Loading 28 rules before knowing whether anyone has business income spends the advisor's context on rules that cannot fire.

**Nothing is stored.** Every scan is rebuilt from Zocks on demand. A saved tax finding goes stale the moment the next meeting happens or the year turns, and a stale tax finding presented with confidence is worse than none.

**This is intelligence, not advice, and never a return.** The output tells an advisor where to look and what to ask. It never computes a liability, never asserts eligibility, and never states a limit, threshold, bracket, exemption or distribution age as current law. Name the mechanism; instruct verification against current-year figures. Every report carries that line in its footer.

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it across meetings, ordering it and drawing conclusions is exactly what this skill is for. Introducing a fact Zocks did not return — a balance, a rate, an entity structure, a filing status, a date — is not, however plausible. Business entity type is the temptation this skill will feel most often, because half the rules would be sharper with it; it is never inferred, and its absence is itself a finding. Full rule in `references/data-budget.md`.

**Identity rule — never ask who the user is, and never call `users_list_users` to find out.** Pass `advisor_id="{user_id}"` (that literal token) and the MCP scopes every call to the signed-in advisor. Scope is always the advisor's own book; firmwide insights appear in aggregate only, never another advisor's client detail.

## Before the first tool call

Read `references/run-setup.md` and `references/data-budget.md`.

**Cost shape.** Depth is the product here — a tax fact pattern built on the last two meetings is not a tax fact pattern. Every analyzed meeting in the window gets read, but staged: full extraction on the twelve most recent, then **fact-pattern payloads only** on the rest (financial profile, planning domains, life events, tasks, topic initiation), skipping outlook and coaching entirely. Older meetings contribute facts and coverage, not sentiment. Say in the header how many meetings were read against how many are on record.

If the household's history is long enough that this will take a while, say so in one line before starting so the advisor can narrow the window.

This skill's opening widget:

> **Who should this be about?** — three household candidates as taps, most imminent first, plus "Someone else"
> **How much history?** — **Everything on record (default)** / Last 3 years / Last 5 meetings
> **Output** — **Interactive report (default)** / Document

Fetch the candidates before asking — the pattern is in `references/run-setup.md`. If the advisor already named someone ("tax opportunities for the Reynolds"), the first question is answered; don't ask it again. Never scan a whole book — the contract is depth on one household.

---

## Command — `tax_scan [client or household]`

**Step 1 — Resolve the household.** `contacts_search_contacts(term)`. One match → `contacts_get_contact_details(contact_id)` for the record, integration identifiers and linked external accounts. Several matches → one tap showing name and something that distinguishes them. Zero → say so plainly and offer recent client meetings to pick from.

Then extend to the household: `meetings_list_past_meetings(contact_id, advisor_id="{user_id}", size=25)` for each known member, and read the participant lists. Co-attending clients sharing a surname, or identified as spouses or partners in the family payload, join the household.

**Real books carry duplicate contact records for the same person** — the same spouse appears under two ids, one complete and one holding a first name and nothing else. Merge on normalised name plus co-attendance **before any arithmetic**. Unmerged, the household's history reads as two shorter ones, tasks assigned to the partial id look unowned, and the Raised-and-Dropped metric fires on topics that were followed up under the other id. Rules in `references/ai-result-shapes.md` and `references/extraction.md`.

**Step 2 — Build the tax fact pattern.** For every meeting across all members, deduped by session, newest first: `ai_results_list_results(room_id, session_id, meeting_summary_size=0)`. Classify each payload by shape per `references/ai-result-shapes.md`, then extract per `references/extraction.md` — which names the fields that carry tax signal and, critically, points at the two payloads that carry most of the value.

**Read `Tax Planning` and `Equity Compensation` in the planning-domain payload first, every meeting.** They are named keys in Zocks' structured layer and they are the closest thing to a per-meeting record of what the tax conversation actually established. An **empty array is a finding** — the domain was on the template and nothing landed against it.

Build the nine domains: income events, compensation, equity, retirement contributions, charitable giving, real estate, business, estate intent, life events. Every field carries its value, its meeting, and that meeting's date. **A null field cannot fire a rule.**

Two extraction rules that decide whether the whole report is right:

- **Ages come from DOB, computed against today.** The profile payload's stated age is computed at analysis time and goes stale — it will say 50 for someone who is now 52. Catch-up bands, distribution ages and education timing all turn on age.
- **Amounts stay in the form they were said.** A range stays a range in every line it appears in.

**Step 3 — Build the coverage layer.** In the same pass, and from the same payloads: what the *household* surfaced themselves (`initiatedBy` matching a member's contact id, concerns in their own words, questions in `faqQuestions`), and what ever landed against it — tasks, planning-domain statements, a next meeting whose purpose is that territory. This feeds Raised-and-Dropped.

**Zocks does not track task completion.** A task proves a task was created, nothing more. So a task means territory was *raised*, never that it is handled — and this skill, unlike `next-best-action`, does not demote an opportunity for having a task against it. It notes the task, and says the status is unknown.

**Step 4 — Firm context.** One `insights_query_insights` call. `Tax Planning` is a first-class topic in `topic_stats` — read `meetings_with_topic`, `client_driven_pct` and `questions` for the firm read. Then `investment_product_stats` for the **Book-Relative Vehicle Gap**: vehicles the firm's book uses that this household's facts would open a gate for and that appear nowhere on their record. Firm numbers are labelled "across the firm", never "you". If insights answers something, never re-derive it from the meetings tools.

**Step 5 — Route, then sweep.** Take the routing table at Part 2 of the playbook, open only the gates the fact pattern actually opens, and read those rule blocks. Sweep every rule behind an open gate, including the ones that look off-shape for this household — the misses that matter usually are. Each firing rule carries the facts that fired it, the mechanism, its deadline, and its impact grade.

Rules never fire on assumed facts. Where a rule depends on something plausible but unverified — whether the plan offers a second vehicle, what the entity election is, whether coverage exists — the item is phrased as **verify, check, or ask**.

**Step 6 — Merge, grade, order.** Merge rules sharing one deliverable per Part 7 of the playbook; a merged item is almost always stronger than any component and it stops the report reading as a list of nineteen small things. Grade each surviving item **Sized / Bounded / Unsized** per Part 5 — never promote a grade to look more useful. Then order within each horizon by the chain at Part 6: irreversible-with-a-date, then closes-at-year-end, then compliance exposure, then compounding and quantified, then structural. A dated irreversible election jumps the queue regardless of horizon or size.

**Step 7 — Build the gap register.** Every domain with no dated fact, written as **a question to ask** rather than a field that is null, ordered by how many opportunities each gap would unlock. On a young relationship the prior-year return usually leads, and naming it beats any speculative item above it. Where a rule needs a professional the household doesn't have on record — a CPA on a household with business income, an attorney on an estate intent — that goes here too, and it is frequently the most actionable line in the report.

**Step 8 — Render** per `references/output-template.md` and `references/output-modes.md`. Read the voice rules first. Two deep-link patterns everywhere:

- Facts and evidence → `https://console.zocks.io/dashboard/workspace/past/room/{room_id}/session/{session_id}/`
- Member names → `https://console.zocks.io/dashboard/library/manage-contacts/contacts/{contact_id}`

Never a wall of text in chat. Chat gets one line naming the strongest item and the depth of the record.

**The follow-up commands below are rendered by feature detection, never by assumption.** Where `sendPrompt` exists they are real buttons; in a standalone artifact they are plain inline text that reads as text. A chip or pill that looks tappable and does nothing is a broken control, and one of those costs the advisor's trust in the Zocks links too. Rules in `references/output-template.md`.

---

### `gaps` — the open questions on their own

The gap register as its own output: every domain with no dated fact, written as the question that would fill it, ordered by how much each one unlocks, with what it would answer listed against it. Runs on data already in the conversation; only re-fetch if nothing is loaded.

**This is a standalone report, not meeting prep.** Don't group the questions by which meeting they'd fit, don't frame it as an agenda, and don't tie it to an upcoming conversation — a list of what isn't known about a household's tax position is a complete and useful thing to hand someone on its own terms, and the advisor decides what to do with it. On a thin record it is frequently more valuable than the scan itself.

### `detail [item]` — expand one opportunity

Every fact with its meeting and date, the mechanism in full, the arithmetic where the item is Sized, what would confirm or kill it, and who outside the firm has to act. Works from the analysis already in the conversation.

### `handoff` — the note for the outside professional

The subset of the scan that belongs to someone else — the CPA, the attorney, the business advisor — written as a brief for them rather than for the advisor. Facts and questions only; no recommendations, no household commentary, no sentiment, and nothing about the relationship.

**This is the one output of this skill that leaves the firm**, so it is treated as client-facing: offer the brand kit at the moment of production per `references/run-setup.md`, and never include the Raised-and-Dropped section.

---

## The metrics

Four, all named on the report, all defined in `references/extraction.md`. **No score is computed** — a number would imply a precision that facts of varying age, extracted from conversation, cannot support, and it would flatten the only thing that decides order, which is whether a door is closing.

| Metric | What it says |
|---|---|
| **Raised and Dropped** | Tax territory the household surfaced themselves with no task, no recorded position, and no meeting purpose against it since. The section a sceptical advisor reads first |
| **Tax Disclosure Depth** | How many of the nine domains have a dated fact, with the missing ones named. On a young relationship this is often the most useful line in the report |
| **Record Currency** | Age of the newest load-bearing fact per domain. Anything over roughly 12 months makes the current-year items resting on it provisional, and the item says so |
| **Book-Relative Vehicle Gap** | A vehicle the firm's book uses that this household's facts open a gate for and that appears nowhere on their record. Evidence a conversation hasn't happened — never evidence about what they own |

---

## Degradation

- **Client not resolvable** → offer recent client meetings to pick from. Never scan a guess.
- **Several contact matches** → one tap with name and something distinguishing.
- **Duplicate contact records for one person** → merge before analyzing, and never present the same spouse twice. This is live in real books and it silently halves the analysis.
- **Household ambiguity** (different surnames, no relationship payload) → analyze the named individual only and say so. A wrong merge produces a wrong filing position and a wrong survivor analysis.
- **Thin history** (one or two meetings, a new relationship) → fewer gates open, and that is correct. Lead with the gap register, name the document that would open the most gates, and **never pad with generic tax advice**. A short report naming three real things and four honest gaps is the product.
- **Stale facts** → still analyze, flag the age on the item itself, and where a whole horizon rests on facts over a year old say that at the top of it.
- **A planning domain present but empty** → a gap with a name, not missing data. Say which.
- **A member with no financial profile payload** → check the other payloads before concluding anything about them; profiles are written per member and one can be absent while the facts exist elsewhere.
- **Life-event keys vary between meetings** → match on the stem (`Inheritance` / `Inheritance Received`), never on an exact key.
- **Insights empty or erroring** → drop the firm line and the vehicle-gap section, say the firm read was unavailable. Never rebuild firm numbers from the meetings tools.
- **Meeting with no AI results** → skip it, footnote it. Analysis can lag a meeting by hours.
- **Oversized payloads** → the list endpoint may ignore paging and return everything. Classify shapes first, extract only what the nine domains need, never carry a payload forward.
- **Pagination** → exhaust it. A partial history produces a confident report built on half the facts, and in a tax report that is the expensive kind of wrong.

---

## Compliance

- Every meetings and AI-results call is locked to the invoking advisor. Insights are firm-level aggregates, labelled "across the firm", never attributed to an individual advisor or another advisor's client.
- **Nothing here is a tax opinion.** No limits, thresholds, brackets, exemptions or distribution ages stated as current law; mechanisms only, with verification against current-year figures instructed on every item. The footer says so on every output.
- **Internal by default.** The scan is advisor intelligence. `handoff` is the only output that leaves the firm, and it carries facts and questions only.
- Compliance flags — an unconfirmed required distribution, a filing obligation created by an event — are rendered as obligations, separate from the opportunity lists. Never as sales opportunities.
- **Minimum viable client language.** Quote a client's phrase only where the phrasing is the point, and only the phrase. Transcripts are never read by this skill.
- **Nothing persists between runs.**

---

## Scheduling — one-off only, never recurring

Household-scoped, so there is **no recurring mode**: a scheduled run would have to decide on its own whose taxes to analyse tonight, and it doesn't.

What it supports is a **one-off scheduled run** where the advisor names the household when the schedule is created — the morning of a year-end review, a few days before a plan meeting, or ahead of a CPA handoff. The household was named by a human, just earlier, so the rule holds.

Offer it in one line only when the advisor has a booked meeting with the household they just scanned and it's more than a few days out: *"Want me to re-run this the morning of the 14th, so it's current?"* Create it with the household named explicitly in the prompt and every other decision at its default — everything on record, interactive output. Read `references/scheduling.md` first.

---

## References

- `references/run-setup.md` — brand kit, the household question, opening decisions. Read first.
- `references/data-budget.md` — staged reads, the no-transcript rule, stop conditions, the inference/invention line. Read first.
- `references/tax-playbook.md` — the nine-domain fact pattern, the routing table, 28 tax rules across two horizons, impact grading, ordering, merging. **Read in two passes: Parts 1–2 before extraction, the rule blocks after routing.**
- `references/extraction.md` — which payloads carry tax signal, the four computed metrics, the duplicate-contact trap. Read before building the fact pattern.
- `references/ai-result-shapes.md` — payload classification, household assembly, endpoint quirks. Read before parsing.
- `references/output-template.md` — section order, voice rules, the two failure modes. Read before rendering.
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.
- `references/scheduling.md` — one-off scheduled runs only. Read before setting one up.