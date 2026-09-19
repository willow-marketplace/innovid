---
name: next-best-action
description: Deep single-household analysis naming the one highest-value next action and proving why — unused tax space, a closing Roth conversion window, RMD and IRMAA sequencing, Social Security and survivor math, irreversible pension elections, tax drag, protection and estate gaps. Never the advisor's own open tasks read back. Shows the runner-ups it rejected and names who outside the firm has to act. Use whenever the user asks what to do for a client, the best move, the biggest opportunity, what they are missing with someone, or wants a household's situation analysed. For a household's full tax picture, tax-opportunity-scan is the skill. Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Next Best Action

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Next Best Action — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

One household at a time, analyzed properly. The advisor names a client; the skill rebuilds that household's entire financial fact pattern from every analyzed meeting on record, sweeps it against the playbook, subtracts everything already handled, and returns **the single highest-value next move with the reasoning laid out** — the facts, the mechanism, what it's worth in dollars or risk, what would change the answer, who outside the firm has to act, and the runner-ups it considered and rejected.

Open tasks and raw mentions are never the recommendation. "They mentioned an inheritance, do something about it" is the failure mode this skill is built against: the advisor already wrote that down. The value is in what they didn't — the conversion window closing a year at a time, the 457(b) nobody checked for, the pension election with a deadline, the survivor's tax cliff nobody has modelled.

The unit is the **household** — spouses or partners are analyzed as one (say "spouses" or "partners", never "husband and wife"), because almost every rule in Domains 7 and 8 is a two-person question.

**The boundary with `tax-opportunity-scan` is deliberate.** That skill is exhaustive inside the tax domain — two ranked horizons, a gap register, a CPA handoff — and it keeps items the advisor has already raised where the tax angle adds something. This one names the single best move across every planning domain, tax included, and subtracts what's already in motion. "Best move" or "biggest opportunity" → here; "tax review", "tax scan", or "what am I missing on their taxes" → there. An advisor should never get two different single-best answers from two skills.

---

## Ground rules

**`references/advisor-playbook.md` is the skill's brain** — the fact-pattern schema, 33 rules across eight domains, life-stage routing, the triage order, and the coverage-subtraction discipline. Read it in **two passes**, not one: the schema and the life-stage routing table before extraction, then the rule domains once the household's stage is known. Loading all eight domains before knowing whether the household is 34 or 74 spends the advisor's context on rules that cannot fire.

**Nothing is stored.** Every analysis is rebuilt from Zocks on demand, which is what keeps it honest: a saved recommendation goes stale the moment the next meeting happens, and a stale recommendation presented with confidence is worse than none.

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it, ordering it, and drawing conclusions from it is exactly what this skill is for. Introducing a fact that Zocks did not return — a balance, a date, an age, a name, a quote, a relationship — is not, however plausible it looks. A gap is reported as a gap; where a conclusion needs a fact the record lacks, the action is phrased as *verify, check, or ask*. Full rule in `references/data-budget.md`.

**Identity rule — never ask who the user is, and never call `users_list_users` to find out.** Pass `advisor_id="{user_id}"` (that literal token) and the MCP scopes every call to the signed-in advisor. Scope is always the advisor's own book; firmwide insights appear in aggregate only, never another advisor's client detail.

## Before the first tool call

Read `references/run-setup.md` and `references/data-budget.md`.

**Cost shape.** This is the deepest skill in the library and the one where depth is genuinely worth paying for — the whole product is that it read *everything*. But "everything on record" on a ten-year relationship is a lot of meetings, so the read is staged: full extraction on the most recent twelve meetings, then **fact-pattern fields only** (financial profile, life events, planning domains, tasks) on the rest. That's enough, because the older meetings contribute facts and coverage, not sentiment. Say in the header how many meetings were read in full against how many are on record.

If the household's history is long enough that this will take a while, say so in one line before starting, so the advisor can narrow the window if they meant something quicker.

This skill's opening widget:

> **Who should this be about?** — three household candidates as taps, most imminent first, plus "Someone else"
> **How much history?** — **Everything on record (default)** / Last 2 years / Last 5 meetings
> **Output** — **Interactive analysis (default)** / Document

Fetch the candidates before asking — the pattern is in `references/run-setup.md`. Never analyze a whole book: the contract is depth on one household. If the advisor already named someone ("next best action for the Reynolds"), the first question is answered — don't ask it again.

**Web enrichment is off unless asked for.** When the selected action turns on a named employer or business — plan features, layoffs, a sale, equity comp — offer it in one line after rendering: *"Want me to check public news on Texas Tech's plan options?"* One search, at most one sourced line, only on the selected action, only on companies the household itself named. It is never a stored preference.

---

## Command — `next_best_action [client or household]`

**Step 1 — Resolve the household.** `contacts_search_contacts(term)`. One match → `contacts_get_contact_details(contact_id)` for the record, integration identifiers, and linked external accounts. Several matches → one tap showing name and role; never guess between two clients. Zero → say so plainly and offer recent client meetings to pick from.

Then extend to the household: `meetings_list_past_meetings(contact_id, advisor_id="{user_id}", size=15)` and read the participant lists. Co-attending clients sharing a surname, or identified as spouses or partners in the family payload, join the household (rules in `references/ai-result-shapes.md`). Resolve each additional member through contacts too.

**Real books carry duplicate contact records for the same person** — the same spouse can appear under two ids across two meetings, sometimes with a surname on one and not the other. Normalise by name and email and merge before any arithmetic, or the household's own history reads as two shorter ones and the survivor math is computed on half the facts.

**Step 2 — Sweep the full relationship history.** For every meeting across all members, deduped by session, newest first: `ai_results_list_results(room_id, session_id, meeting_summary_size=0)`. Classify each payload by shape per `references/ai-result-shapes.md`, and split what you extract into two piles.

**Stage the read.** The twelve most recent meetings get full extraction. Beyond that, pull only the payloads that carry facts and coverage — financial profile, planning domains, life events, tasks — and skip concerns, outlook, and topic initiation, which say how the household felt three years ago and change no rule. Deduping members' contact records first (below) is what keeps this from doubling.

- **Fact pattern** — ages and DOBs, employment and *employer type*, tenure, income sources, every account with type, sponsor, contribution and balance, taxable holdings and the income they throw, liabilities, children and their stages, goals with horizons, estate and insurance status, life events with timing, health facts, charitable giving, business ownership, who initiated which topics and what they asked about. Schema in `references/advisor-playbook.md`. **Facts only** — a field with no evidence stays null and cannot fire a rule.
- **Coverage map** — territory already **raised**, not territory already finished: open tasks, agreed next steps, substantively discussed topics, booked follow-ups, each with its date. Tasks are **never** candidates; they only ever demote opportunities. Mark parking language ("flag as a future priority") as a deferral.

  **Zocks does not track task completion.** A task on a past meeting proves a task was created and nothing more. So a task demotes an opportunity out of the top recommendation — the advisor already wrote it down, and restating it is this skill's core failure mode — but it never licenses the claim that the work is done or underway. Anything demoted this way is reported as *raised, status unknown, worth confirming*, never as handled or in motion.

Facts are cumulative and dated. A balance stated 18 months ago is a fact with an age, and that staleness is itself worth noting. Where a later meeting supersedes an earlier fact, the newer one wins and the change is worth a line — a retirement age that moved is a bigger finding than the age itself.

**Step 3 — Firmwide pattern support.** One `insights_query_insights` call, trailing 30 days. Firm-level numbers, so every line built from them says "across the firm", never "you". Read `topic_stats` and `investment_product_stats` (is this territory something clients are raising themselves right now), `client_outlook` (sentiment on the asset classes in play), and the `held_away_assets` funnel (the 30-day suggestion-to-agreement rate, which backs any consolidation recommendation). If insights answers a question, never re-derive it from the meetings tools.

**Step 4 — Life-stage routing, then sweep the rules.** Place the household on the arc — accumulation, pre-retirement, transition, decumulation — per the table at the top of the playbook. Spouses can sit in different stages, which is itself a rule. Now read the rule domains and run the fact pattern through **every rule whose preconditions the fact pattern can actually meet** — which is most of them, and deliberately includes off-stage rules, because the misses that matter are usually off-stage. What it does not include is a rule whose required facts are all null: that rule cannot fire, and reading it closely is spent context. Each firing rule carries the facts that fired it, the mechanism, the stakes, and its taxonomy mapping.

Rules never fire on assumed facts. Where a rule depends on something plausible but unverified — does the employer offer a 457(b), is there existing coverage, what is actually in the pension election — phrase the action as **verify, check, or ask**. Recommending the investigation is correct; asserting the conclusion is not.

**Step 5 — Subtract coverage.** Demote every opportunity whose territory the coverage map already covers: an open task, a substantive discussion, an agreed step, a booked meeting. Deferrals stop counting after 90 days — territory parked twice is territory dying, and the analysis says so out loud. List demoted opportunities compactly under **"Already raised — worth confirming"**, with what raised them and when, so the advisor can see the skill checked rather than missed. Never label them "in motion", "handled" or "in progress": Zocks has no completion status, and asserting one is the one claim this skill must not make. **An open task restated is never the Next Best Action.**

**Step 6 — Merge, then order.** Order survivors by the triage chain in `references/priority.md` — no score is computed. Merge rules sharing one deliverable — catch-up plus 457(b) is one deferral analysis; conversion window plus IRMAA plus the widow's penalty is one multi-year tax plan, and that merge is often the strongest recommendation available. Apply the playbook's triage order for near-ties: irreversible deadlines, then expiring windows, then unpriced catastrophic risk, then compounding inefficiency, then relationship and documentation.

**Top survivor is the Next Best Action. Keep the next three as considered-and-rejected** — showing the work is half the value, because an advisor trusts a recommendation more when they can see what it beat.

**Step 7 — Quantify.** For the selected action and each runner-up, size the stakes from stated facts: the balance the mechanism acts on, the annual or lifetime magnitude, the deadline. "On the ~$120k taxable position, the December distributions are a recurring tax cost they don't need to pay" is usable; "consider reviewing tax efficiency" is not. Where the record can't support a number, say exactly what data would produce one — that gap is often the deliverable. **Never quote contribution limits, IRMAA thresholds, or bracket boundaries as current law.** Name the mechanism and instruct verification against current-year figures.

**Step 8 — Name who has to act.** Most high-value actions execute outside the firm. For the selected action, build the chain: **what we do first → who outside the firm acts → what they need from us → what it unblocks → and what is blocked until they do it.** Professionals come from the family-extraction payload, from tasks assigned outside the firm, and from planning-domain payloads referencing outside execution.

Where the action plainly needs a professional the household **doesn't have on record**, say that. "This needs an estate attorney and there isn't one on file for them" converts a follow-up into a referral conversation, and it is one of the most useful lines this skill produces.


**Step 9 — Render** per `references/output-template.md` and `references/output-modes.md`. Read the voice rules first. Two deep-link patterns everywhere:

- Facts and evidence → `https://console.zocks.io/dashboard/workspace/past/room/{room_id}/session/{session_id}/`
- Member names → `https://console.zocks.io/dashboard/library/manage-contacts/contacts/{contact_id}`

Never plain chat text. Chat gets one line naming the recommendation.

---

### `why this` — expand the reasoning

Invoked as **`why this`** or **`why this action`**, never as bare `why [client]` — bare `why` is ambiguous about what is being explained, and two commands answering the same phrase is how an advisor gets the wrong answer confidently.

Every fact with its meeting and date, the mechanism spelled out, the coverage check ("here's what I looked for and didn't find"), each factor of the priority call in words, the firm read, and every rejected candidate with why it lost. Works from the analysis already in the conversation; only re-fetch if nothing is loaded.

### `what else for [client]` — the pushback

The advisor said "not this". Promote the top rejected candidate and render its full reasoning. If nothing material remains, say so plainly rather than reaching for something.

Because nothing persists, a dismissal holds for this conversation, not forever. Say so in a line the first time it's used — an advisor who thinks a rejection is permanent and then sees it return next week stops trusting the skill. What *does* carry across runs is the record itself: once the advisor acts, the next analysis sees the task or the discussion in the coverage map and subtracts it on its own, which is the version that can't go stale.

---

## How candidates are ordered — no score

**This skill does not compute a score.** The Action Confidence Score is gone. Full reasoning and the signal list are in `references/priority.md` — read before ordering.

A score was especially wrong here. This skill's output is one recommendation an advisor may act on with a client's money; a "62" implies a precision that extracted conversation facts of varying age cannot support, and it flattens the only thing that really decides the answer — whether the window closes. An irreversible pension election and a tax-drag inefficiency are not comparable quantities, and forcing them onto one axis is how the tax-drag item wins on volume.

**Order by the playbook's triage chain, in sequence:**

1. **Irreversible, with a deadline** — once it passes, no later advice recovers it.
2. **A window that closes on a date** — a conversion year, an enrolment period, a tax year end.
3. **Unpriced catastrophic risk** — a protection or estate gap where the downside is ruin, not inefficiency.
4. **Compounding inefficiency** — real, quantified, still there next quarter.
5. **Relationship and documentation** — beneficiary sweeps, risk-tolerance refreshes, compliance touches.

Within a tier, order by the stated exposure the mechanism acts on, largest first. **Merge before ordering** — rules sharing one deliverable are one recommendation.

**Two overrides.** A hard dated deadline always surfaces, however minor it otherwise looks. And where the last meeting carried an unresolved concern or an unacknowledged life event, an unrelated recommendation is demoted below one that engages what is already on their mind — a household with something on their mind isn't ready for an unrelated recommendation, however good it is, and leading with one spends trust the advisor needs. If nothing engages it, say so plainly rather than leading with something else.

**The words:** **do this now** · **this month** · **raise at the next review** · **nothing material outstanding** — the last being a valid and sometimes correct output, with its reason. Never padded with generic advice.

---

## The taxonomy

Every recommendation is delivered as exactly one of: **send analysis or document · follow-up meeting · specialist intro · consolidation or account proposal · life-event outreach · compliance touch · nothing material outstanding**. The playbook rule supplies the content; the taxonomy supplies the form. Lead with the insight, never the vehicle. Actions outside the playbook's rules are never invented.

---

## Output

Render per `references/output-modes.md`. Both modes carry the same content and the same reasoning.

| Section | Content |
|---|---|
| Header | Household, members linked to their contact records, life stage, meetings analyzed against meetings on record, the date built |
| **The recommendation** | The insight in one sentence, its urgency label, and the taxonomy form. The most prominent thing on the page |
| Why this, why now | The reasoning chain: the facts, the mechanism, the stakes quantified, the deadline. Every fact links to the meeting it came from |
| What would change this | The verification that could overturn it, stated as the thing to check |
| Who has to act | The dependency chain from step 8, including any professional the household lacks. Omit if the action is entirely internal |
| Considered and rejected | The three runner-ups, each with its urgency label and the one reason it lost |
| Already raised — worth confirming | The territory the coverage map demoted, compactly, each with what raised it and when. Status is unknown by design — never rendered as handled or in motion. This is the section that proves the skill looked |
| Firm read | One line, labelled as firmwide |
| Actions | **Open the meeting in Zocks** on every fact. `sendPrompt` buttons for **Why this?** and **Show me the next one** |

**Interactive extras:** the fact pattern expands by domain so an advisor can audit what the analysis knew; rejected candidates expand to their full reasoning; the coverage-subtraction list expands to what demoted each item. Nothing here is decorative — an advisor who can't audit a recommendation won't act on it.

---

## Degradation

- **Client not resolvable** → offer recent client meetings to pick from. Never analyze a guess.
- **Several contact matches** → one tap with name and role.
- **Household ambiguity** (different surnames, no relationship payload) → analyze the named individual only and say so. A wrong merge produces wrong survivor math, which is worse than a narrower analysis.
- **Duplicate contact records for one person** → merge before analyzing, and never present the same spouse twice.
- **Thin history** (one meeting, or a short one) → fewer rules fire, and that is correct. Name the gaps blocking the biggest rules: "no ages or balances on record, which blocks most of the tax-space analysis". **Never pad a thin record with generic advice.**
- **Stale facts** (newest balance or employment fact older than roughly 12 months) → still analyze, flag the staleness, and make "confirm the current picture" the recommendation where it genuinely gates everything else.
- **Insights empty or erroring** → the firm read is unavailable for every candidate; say so and drop the firm line. Never rebuild firm numbers from the meetings tools.
- **Meeting with no AI results** → skip it, footnote it. Results can lag by hours.
- **Oversized payloads** → the list endpoint may ignore paging and return everything. Classify shapes first, extract only what the fact-pattern schema needs, never quote payloads wholesale.
- **Pagination** → exhaust it before analyzing. A partial history produces a confident recommendation built on half the facts.

---

## Compliance

- Every meetings and AI-results call is locked to the invoking advisor. Insights are firm-level aggregates, labelled "across the firm", never attributed to an individual advisor or another advisor's client.
- Recommendations name mechanisms and instruct verification against current-year figures. They never assert tax thresholds, limits, or eligibility as settled fact, and never substitute for the firm's own compliance review on suitability-relevant moves.
- Compliance touches — beneficiary sweeps, risk-tolerance shifts, missed-RMD exposure — are flagged as documentation obligations, not sales opportunities.
- Every output is internal decision support for the licensed advisor, who is the decision-maker. It is never written for client distribution, and it says so in its header.
- Web enrichment is off unless asked for, public sources only, and touches only companies the household named.
- **Nothing persists between runs.**

---

## Scheduling — one-off only, never recurring

This skill is scoped to one named household, and a recurring run would have to decide on its own whose life to analyse tonight. It doesn't. **There is no recurring mode.**

What it does support is a **one-off scheduled run ahead of a specific meeting**: the advisor names the household when the schedule is created, and the analysis is waiting for them the morning of. That keeps the rule intact — the household was named by a human, just earlier.

Offer it in one line only when the advisor has a booked meeting with the household they just analysed and the meeting is more than a few days out: *"Want me to re-run this the morning of the 14th, so it's current?"* Create it with the household named explicitly in the prompt, and every other decision at its default: everything on record, interactive output, no web enrichment. Read `references/scheduling.md` first.

Running this skill across every household on a day's calendar is the most expensive thing this library can do — day-breadth questions are a different product, never a loop over this one.

---

## References

- `references/run-setup.md` — brand kit, the household question, opening decisions. Read first.
- `references/data-budget.md` — staged reads on long histories, the no-transcript rule, stop conditions. Read first.
- `references/advisor-playbook.md` — the fact-pattern schema, all 33 rules across eight domains, life-stage routing, triage order, coverage subtraction. **Read in full before every analysis.**
- `references/priority.md` — the signals, the triage chain, the labels, and why there is no score. Read before ordering.
- `references/ai-result-shapes.md` — payload classification, household assembly, endpoint quirks. Read before parsing.
- `references/output-template.md` — section order, reasoning chain layout, voice rules. Read before rendering.
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.
- `references/scheduling.md` — one-off scheduled runs only. Read before setting one up.