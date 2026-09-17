---
name: client-journey
description: Builds a client-ready Client Journey for one household — a five-stage lifecycle timeline marking where the family sits today, a relationship tree including beneficiaries never met, their outside professional team, a dated timeline of life events and turning points, and every motivator, worry and consequential fact on record with its corroboration and age — plus what they have never been asked that the rest of the book covers. Use whenever the user wants everything known about a household, a family tree or lifecycle report, who is who in a family, what is new on a client, or a comprehensive picture polished enough to walk a client through. Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Client Journey

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Client Journey — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

An advisor's knowledge of a household lives in fragments: a daughter's name from a meeting in 2023, a brother nobody speaks to, a boat, a bad experience with a previous advisor, a 403(b) at a former employer, a health scare that never came up again, a retirement date that has quietly moved twice. Zocks captured all of it, scattered across dozens of meetings. This skill assembles it into one navigable picture of **who they are and how they got here**, then does the three things a pile of facts can't do on its own: it shows the relationship as a **lifecycle with a current position**, it says **how much to trust each fact**, and it says **what's missing**.

This is one of two outputs in the library an advisor may turn their screen around and walk a client through — `household-review-pack`'s letter and presentation are the other — so it can carry the firm's branding and it has to look like the firm's work.

Four ideas make it worth opening before a meeting rather than after:

- **The relationship has a shape.** Five stages — Onboarding → Goal Setting → Action → Milestones → Ongoing — with the family's position marked and each stage showing what actually got done against what never came up. A client who has never seen their own plan as a journey usually finds this the most reassuring thing in the review.
- **The journey is chronological.** Facts have dates. "Retiring at 62" from 2024 followed by "retiring at 65" from 2026 isn't a contradiction, it's a turning point — and only the order tells you which is current and that something moved.
- **A fact heard once, three years ago, is not a fact confirmed in four meetings.** Every entry carries its corroboration and its age, so nothing gets repeated back to a client with more confidence than it earned.
- **The gaps are the product.** Benchmarking this household against what the firm routinely discusses turns "here's what we know" into "here's what to ask", and that's the part that changes the next meeting.

Rebuilt live from Zocks every time. Nothing is stored, so it can't go stale and there's no cached copy to reconcile.

---

## Ground rules

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it, ordering it, and drawing conclusions from it is exactly what this skill is for. Introducing a fact that Zocks did not return — a balance, a date, an age, a name, a quote, a relationship — is not, however plausible it looks. A gap is reported as a gap; where a conclusion needs a fact the record lacks, the action is phrased as *verify, check, or ask*. Full rule in `references/data-budget.md`.

**Identity rule — never ask who the user is.** Pass `advisor_id="{user_id}"` (that literal token) to the meetings tools. `users_list_users` is not needed here.

**Cost shape.** This skill reads more meetings than any other, because "everything we know" means everything. That's the right trade — but the read is staged, not uniform: recent meetings get every payload, older ones get the fact-bearing payloads only. A dossier assembled from raw transcripts is a liability, not an asset — which is one reason no skill in this library pulls one. Read `references/data-budget.md` before the first call.

## Before the first tool call

Read `references/run-setup.md` and `references/data-budget.md`. Branding matters more here than anywhere else in the library: this is the report that may face a client.

This skill's opening widget:

> **Who should this be about?** — three household candidates as taps, most imminent first, plus "Someone else"
> **How far back?** — **3 years (default)** / 5 years / All history
> **Output** — **Interactive journey (default)** / Document

Fetch the candidates before asking — the pattern is in `references/run-setup.md`. **The household is never guessed**; several matches is a tap, zero is a stop. Skip the question only when the request already named a household unambiguously.

**Then, as a separate second question, before anything is rendered:**

> **Who's going to see this?** — **Just me (default)** / I'll show this to the client

This is not a styling choice. The internal version carries health details, estrangements, the beneficiary blind spot, and the corroboration flags; the client-facing version carries none of that and is a different document. Getting this wrong puts an estranged brother on screen during a review. Ask it every run, and never let client-facing be the default. In an unattended run, internal is the only answer.

**If they pick client-facing, branding is offered at that point** — per the client-facing branding rule in `references/run-setup.md`. A kit already saved is simply applied and named in a line. No kit, or one declined months ago on an internal output, gets a single offer here: **add colours and a logo · add colours only · send it plain**. A decline made about a morning brief was never a decision about the document this family is going to be shown, and this is the output where the firm's name matters most.

**What the client-facing version drops**, in full: every fact flagged sensitive at extraction, all corroboration labels, the unknowns panel, the beneficiary blind-spot count, and anything about a third party beyond their name and role. Where dropping leaves a panel empty, **the panel is omitted, not padded** — the rules are in `references/extraction.md` and they are not negotiable per-run.

The interactive version is the point of this skill — the tree, the stage timeline, and the evidence behind every row only work when they can be clicked. Say that in one line if they pick Document, then build the document without arguing.

---

## Command — `journey [client or household]`

**Step 1 — Resolve the household.** `contacts_search_contacts(term)` → zero matches: say so and stop. Several: ask, never guess. Then `contacts_get_contact_details(contact_id)` for every member → names, email, phone, linked external accounts, segment, held-away pipeline, integration identifiers. Say **"systems linked"**, never "CRM linked". Household assembly rules in `references/ai-result-shapes.md`.

Derive the header metadata here: household display name ("Nate & Sarah Harrison", or both surnames where they differ), the advisor's name from meeting participants, **relationship start** (earliest meeting in Zocks, or a contact-record date where one exists), and **years together** as a whole number, minimum one.

**Step 2 — Full meeting spine.** `meetings_list_past_meetings(advisor_id="{user_id}", contact_id=…, from_date=today − window, size=100)`, paginated to exhaustion, once per household member, deduplicated by session, ordered **oldest → newest**. This ordering is the spine of the entire skill: the stage scoring, the timeline, the supersession pass, and the "what's new" view all depend on it.

**Step 3 — Harvest every payload.** `ai_results_list_results(room_id, session_id, meeting_summary_size=0)` per meeting. Classify by payload shape — there is no type discriminator, see `references/ai-result-shapes.md` — extract per `references/extraction.md`, and route each into a panel:

| Payload | Panel | What it yields |
|---|---|---|
| `{Information[], Relationship, Name}` family extraction | **Relationship tree** | Spouses, children, parents, siblings, ex-spouses, in-laws, and the non-family named repeatedly |
| Same, plus repeated professional mentions | **Trusted team** | CPA, estate attorney, insurance broker, banker, business counsel |
| Object with life-event keys | **Journey timeline**, **Achievements** | Births, deaths, moves, jobs, retirements, inheritances, diagnoses |
| `{relatedTo, assignedTo, task, description}` | **Journey timeline**, **Commitments**, **lifecycle evidence** | Decisions taken, what's owed and by whom |
| String `# Financial Profile:` | **Money**, **lifecycle evidence** | Custodians, balances, account types, income, property, debt |
| Object with planning-domain keys | **Money**, **lifecycle evidence** | Domain-by-domain positions; cross-check against the profile |
| Array of client-voiced concerns | **Motivators & worries** | What worries them, close to their own words |
| `{topic, subTopic, initiatedBy, initiatorName}` | **Motivators & worries** | What they care enough to raise unprompted — the strongest motivation signal in the dataset |
| `{overallOutlook, outlooks[]}` | **Motivators & worries** | Attitudes by asset class, risk temperament over time |
| Array of plain strings (personal facts) | **Interests**, **Life & health** | Hobbies, travel, sports, pets, community, faith, health mentions |
| `{meeting name: date}` | **Commitments** | Next meetings that were discussed; a passed date with no meeting since means none has happened — say that, never "broken promise" |

Each extracted fact becomes a keyed record — statement, panel, first seen, last seen, source sessions, sensitivity flag. **Facts are keyed and merged, never appended blindly**, or a client who mentions their boat in nine meetings gets nine boats.

**Step 4 — Place the lifecycle stages.** Read `references/lifecycle-stages.md` and work the 36-item checklist against everything harvested in step 3. Every item gets Yes, No, or blank, and **every Yes or No carries its evidence** — the meeting it came from, and the client's or advisor's own phrasing where the structured payloads carry it. Derive each stage's status word and its plain count of evidenced / no / never-covered items, plus the household's current stage. No percentage.

This is the part a client sees first, so it has to be defensible: an item marked Yes with no evidence behind it is the fastest way to be contradicted in a review.

**Step 5 — Supersession pass.** Within each panel, find facts that contradict an earlier fact on the same subject — retirement age, target amounts, job, address, marital status, an account that moved. Mark the older superseded by the newer, keep both, and **write the change into the journey timeline as a turning point**. That's the moment the plan actually moved, and it's usually the most interesting thing in the report. Never drop the old value.

**Step 6 — Corroboration.** Label every fact per `references/metrics.md`: **confirmed**, **stated once**, or **stale**. A name or account that also appears in the contact record or a linked system counts as a second source.

**Step 7 — Achievements.** Pull four to eight genuine wins from the harvest — explicit completion language, funded goals, debts cleared, documents executed, milestones hit. Each gets a date or a period, a short title, one line of description, and the concrete impact where the data carries one. **Never pad this section to fill a grid.** Two real achievements beat six invented ones, and a client knows which of their own wins are real.

**Step 8 — The unknowns panel.** One `insights_query_insights` with no date params — firmwide context, so any line built from it reads "across the firm" (30-day firmwide default) and one scoped with `user_id_list=["{user_id}"]`. The topic-coverage output names the planning domains the firm and this advisor actually discuss. Any domain with meaningful firm coverage and **zero evidence** in this household is an unknown — printed as the question to ask, not as a scolding. Report it as a plain count plus the named gaps per `references/metrics.md` — never as a coverage score. Never supplement these calls with meetings-tool aggregation; the tool contract forbids it.

**Step 9 — Beneficiary Blind Spot.** From the relationship tree, count the financially consequential named people — beneficiaries, dependents, decision influencers, successors — who have **never appeared as a meeting participant**. Participation comes free from the step 2 spine. Four beneficiaries the advisor has never met is a succession risk they can act on this quarter.

**Step 10 — Render** per `references/output-template.md` and `references/output-modes.md`. Nothing is saved.

---

### `what's new on [client]`

Same build, filtered: facts whose earliest mention falls after a boundary, plus every turning point since, plus any lifecycle item that changed state. The boundary is whatever the advisor names ("since April", "since the last review") and defaults to **the meeting before the most recent one** — so straight after a meeting, this answers "what did I learn today". The dates come from Zocks; no stored state is involved.

### Adding context during a run

An advisor can correct or add a fact in conversation — "her mother moved in with them in June" — and it appears in that run's report, marked as advisor-added rather than blended into what Zocks heard, because the provenance is the point. It does not persist to the next run; Zocks stays the system of record.

---

## Named metrics

Definitions and the labelling rules are in `references/metrics.md` — read before labelling. **Counts and labels, never indexes:** no weighted confidence figure, no completion percentage, no coverage index. A count the advisor can verify by looking is fine and encouraged; a number built from invented weights is not.

- **Corroboration** — per fact, one of three words: **confirmed** (two or more independent meetings, or one meeting plus a contact or linked-system record) · **stated once** · **stale** (nothing in 18 months). Shown on every fact with its date. This is what makes the report safe to speak from in front of the client — and a word plus a date does that better than a confidence figure, which invites the question of how it was computed.
- **Lifecycle stage** — per stage, a status word from `references/lifecycle-stages.md` plus a plain count of which checklist items have evidence and which are blank ("2 evidenced, 1 no, 4 never covered"). A count the advisor can check, not a completion percentage.
- **What they've never been asked** — a count of the planning domains covered against the domains the advisor's book routinely covers, plus the named gaps: "six of nine; estate, long-term care and education never have." The names are the deliverable.
- **Beneficiary Blind Spot** — count and names of financially consequential people who have never attended a meeting.

---

## Output

Read `references/output-template.md` for the section order, the relationship-tree geometry, the stage timeline, and the interaction behaviour, then `references/output-modes.md` for mode rules and link patterns.

Sections, in order: header with brand and years together → **stage timeline** with current position → **relationship tree** → **journey timeline** with turning points → **achievements** → **motivators, worries and interests** → **trusted team** → **money** → **commitments** → **lifecycle activity table** with evidence behind every row → **unknowns and blind spots**.

Motivators, worries and interests stay **in the main view**, not tucked behind a tab. They are the part an advisor finds genuinely new about a household they've had for a decade.

The interactivity that has to work: tree nodes open a person's profile; the stage timeline filters everything below to that stage; timeline points filter to that moment; lifecycle rows expand to their evidence and link to the meeting in Zocks; the fact panels filter by confidence so an advisor can see only what's confirmed before a client-facing conversation.

---

## Degradation

- **No analyzed meetings** → don't render an empty report. Say what the contact record holds and note that Zocks lists meetings only once analyzed.
- **Very large history (over 40 meetings)** → process the 40 most recent plus the earliest 3 in the window. Origin facts anchor the timeline and are disproportionately valuable. State the coverage in the header; never truncate silently.
- **Long history within the cap** → read the 15 most recent meetings for every payload, and the rest for the fact-bearing payloads only — family, life events, financial profile, planning domains, tasks, personal facts. Older meetings contribute facts and turning points, not sentiment, and outlook from four years ago changes nothing on this report.
- **A meeting returns no AI results** → skip it, count it, report the count in the header ("34 meetings, 31 analyzed").
- **Thin history for lifecycle scoring** → mark items blank rather than No. "No" asserts something didn't happen; blank says nobody has looked. With fewer than three analyzed meetings, present the stage timeline as provisional and say so on the report.
- **Conflicting facts with no clear chronology** → show both, marked conflicting, with both dates. Never pick a winner arbitrarily.
- **Empty insights** → the unknowns panel can't be benchmarked. Render only the structurally obvious gaps (no beneficiaries recorded, no estate documents mentioned) and say the firm comparison was unavailable. Never invent a coverage percentage.
- **Contact search zero or several matches** → say so, or ask.
- **Weak household evidence** → keep people separate and say so. A wrong merge puts one family's health facts on another family's report, which is the worst failure available to this skill.
- **Pagination** → exhaust every page in step 2. A partial spine produces a timeline that looks complete and isn't.

Never fill a gap with a plausible-sounding fact. An invented detail in a report titled "everything we know" is indistinguishable from a real one, and this one may be on screen in front of the client.

---

## Compliance

- Scope is the invoking advisor's own book. The only firmwide calls are aggregate insights, which return no other advisor's client content.
- **Nothing is stored.** The report lives as long as the conversation, or as long as the document the advisor saved. Zocks stays the system of record — this skill deliberately avoids creating a shadow copy of a household's most sensitive data in a Drive folder.
- **No raw transcript text.** Facts are short structured statements; where phrasing matters, keep the phrase, not the passage.
- **This report may be shown to the client**, which is why the audience question is asked before anything is built rather than after. Health details, estrangements, corroboration flags, the blind-spot count, and internal reads are marked sensitive and **never appear in a client-facing render or export**. An estranged brother appearing on screen during a review is a serious failure, not a formatting slip.
- Named third parties are not clients and never consented to being recorded. Limit them to what is financially consequential: relationship, role, relevance. A non-client's health or personal life is dropped at extraction, not stored and filtered later.
- **A document export is the one lasting copy this skill creates**, and it carries names, balances, family structure and health-adjacent facts. Say so in one line when the advisor chooses Document, so the choice to keep it is a deliberate one.

---

## No scheduled mode

**This skill is on-demand only.** It is scoped to one named household and there is no recurring mode — a scheduled run would have to choose a family on its own, and this library does not.

---

## References

- `references/run-setup.md` — brand kit, the household question, opening decisions. Read first.
- `references/data-budget.md` — staged reads on long histories, the no-transcript rule, the context budget, stop conditions. Read first.
- `references/lifecycle-stages.md` — the five stages, the 36-item checklist, evidence rules, stage status. Read before step 4, not before starting.
- `references/metrics.md` — corroboration labels, the coverage count, the blind-spot count, supersession and turning-point rules. Read before labelling.
- `references/ai-result-shapes.md` — payload classification, household assembly, endpoint quirks. Read before parsing.
- `references/extraction.md` — fact granularity, fact keying, attribution, sensitivity flagging, third-party rules. Read before extracting.
- `references/output-template.md` — section order, tree geometry, stage timeline, interactions. Read before rendering.
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.