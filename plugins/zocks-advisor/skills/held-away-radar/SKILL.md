---
name: held-away-radar
description: Ranked pipeline of held-away asset opportunities from the advisor's own recent meetings — old 401(k)s, outside brokerage accounts, inheritances, business sale proceeds, real estate, spouse assets — grouped by household and marked new, raised again, nothing new since, task created, or client agreed. Use whenever the user asks what held-away or outside money came up recently, wants their consolidation pipeline or money in motion, mentions old 401(k)s or outside accounts, or says things like "run the radar" or "held-away opportunities". Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Held-Away Radar

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Held-Away Radar — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

Every week, clients mention money that lives somewhere else — an old 401(k), a brokerage account from before they met you, an inheritance that just landed. Most of it evaporates because nobody wrote it down with a next step. This skill scans **the advisor's own recent meetings** through Zocks' structured AI results, groups what it finds by **household**, scores each asset for move likelihood, and hands back a ranked, linked pipeline.

The unit of the pipeline is the **household**, not the individual. Spouses or partners who meet together are one card, with their assets tracked one by one inside it. Say "spouses" or "partners" in every output; never "husband and wife".

## Ground rules

**1 — The pipeline is the advisor's own book, over a short window.** Never firmwide, and never months of history. The radar answers "what came up in my meetings recently that I should act on", so every number and every card comes from the invoking advisor's own meetings inside the chosen window. The window is **14 days by default and 30 days at the absolute maximum** — there is no long carryover pass, no six-month sweep, no multi-month pipeline. A held-away lead that surfaced four months ago is not radar material; it is history, and a stale pipeline is what made advisors stop trusting spreadsheets in the first place. The advisor-scoped insights call uses `user_id_list=["{user_id}"]`.

**The one firmwide thing is context, and it is labelled as such.** A single unscoped insights read gives the advisor a bird's-eye view of what is happening across the firm — is held-away territory hot this month, are clients raising it themselves — which is genuinely useful precisely because it is not about their clients. It renders as **one line, always labelled "across the firm"**, and it never enters a score, a rank, or a card. The rule is simple: *firmwide for context, the advisor's own book for anything about a client.*

**2 — Zocks does not track task completion.** A task attached to a past meeting proves a task was *created*. Nothing anywhere says whether it was done. So the radar reports that a task exists and asks the advisor to confirm the status; it never states or implies that an asset is being handled, in progress, or resolved. Wording that asserts completion is a factual claim the data cannot support.

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it, ordering it, and drawing conclusions from it is exactly what this skill is for. Introducing a fact that Zocks did not return — a balance, a date, an age, a name, a quote, a relationship — is not, however plausible it looks. A gap is reported as a gap; where a conclusion needs a fact the record lacks, the action is phrased as *verify, check, or ask*. Full rule in `references/data-budget.md`.

**Identity rule — never ask who the user is, and never call `users_list_users` to find out.** Pass `advisor_id="{user_id}"` (that literal token) and the MCP scopes to the signed-in advisor.

**Never pull a transcript.** `meetings_get_transcript` is not used by this skill under any circumstances. Everything the radar needs is in the structured AI results, and a transcript pull costs more than every other call in the run combined. If a phrasing detail is not in the structured payloads, the radar does without it.

---

## Before the first tool call

Read `references/data-budget.md`. `references/run-setup.md` covers the brand kit — this skill is book-wide, so its household question does not apply here.

This skill's opening widget, interactive runs only:

> **What should I look at?** — Last 7 days / **Last 14 days (default)** / Last 30 days
> **Output** — **Interactive pipeline (default)** / Document

Two questions. **30 days is the ceiling** — never offer or accept a longer window, including when the request asks for one; say the radar covers a month at most and run 30 days.

**Scheduled runs ask nothing** and take every default. After rendering, if no schedule exists, offer one in a single line: *"Want this waiting for you every Friday afternoon? Say 'schedule the radar'."* Then create it, invocation phrase **"run held-away radar"** — do not leave the advisor with instructions.

---

## Command — `radar`

**Step 1 — Insights, twice: once firmwide for context, once scoped to the advisor for the numbers.**

- **Firmwide bird's-eye** — `insights_query_insights()` with no user scoping, trailing 30 days. One line, always prefixed "across the firm": whether held-away territory is running hot, and whether clients are raising it themselves. Context only. Never scored, never on a card, never attributed to this advisor or to another advisor's clients.
- **The advisor's own numbers** — `insights_query_insights(user_id_list=["{user_id}"], from_date = window start, to_date = today)`. Everything below comes from this call.

Because this is scoped to one advisor over at most a month, the counts are small — read them as a handful of conversations, not a statistical funnel. Say "in your meetings", never "across the firm".

- `held_away_assets[]` — `asset_mentioned_*`, `suggestion_made_*`, `client_agreed_*`. Report as plain counts: **mentioned in N meetings → you suggested a move in M → the client agreed in K**. The gap between mentioned and suggested is the opportunity, in one line. No trend arrows — a two-week window against a two-week window is noise.
- `client_outlook[]` — sentiment per asset class, feeding the emotion component.
- `topic_stats[]` / `investment_product_stats[]` — mentions, questions, and client-driven share for held-away-adjacent classes (401k, IRAs, HYS, business ownership, estate). One line, only if something stands out.

Never re-derive any of this from the meetings tools.

**Step 2 — One meeting pass.** `meetings_list_past_meetings(advisor_id="{user_id}", from_date = window start, size=100)`, paginated, keeping `room_id` and session id for the deep links, and dropping meetings with no external participants. Group into households and dedupe duplicate contact records **here**, before spending a single AI-result call — a book with duplicate records will otherwise read the same meeting twice.

One window, one pass. If the window returns more than 40 meetings, deep-read the 40 most recent **by breadth across households** rather than by date, and state the coverage in the header. Never truncate silently.

**Step 3 — Detect mentions from structured AI results.** Per meeting, `ai_results_list_results(room_id, session_id, meeting_summary_size=0)` — the narrative summary carries nothing the structured payloads do not, and it is the largest thing this endpoint returns. Items arrive with no type discriminator, so classify each payload by shape per `references/ai-result-shapes.md`, then scan against the **held-away taxonomy** in `references/extraction.md`.

Signal sources per payload: financial profiles (named outside custodians and balances), life events (inheritance, job change meaning an orphaned 401(k), business sale, retirement timing), concerns (anxiety touching an outside asset), topic initiation (`initiatedBy` — did the client raise it, did they ask questions), tasks (a task referencing the asset — see the task rule below).

Then apply the **disposition test to every candidate, one at a time** — this is where the radar earns trust, so be strict. An asset qualifies only if it is sitting somewhere else and nothing in the record shows it already being dealt with. If the record shows an agreed decision or next step covering it, statements already collected, or a transfer or account opening underway, it is **not** radar material.

This matters most in first and onboarding meetings: clients lay out their accounts there precisely because they want help, so most of what is named already has a next step attached. Scan them like any other meeting and expect the test to exclude most of what they surface.

**When disposition is ambiguous, do not flag.** A false positive costs more than a missed one, because the radar's credibility is the product. Assets seen but excluded get one short line in the footer, aggregated beyond two, so the advisor knows the radar saw them and why it stayed quiet.

**The task rule.** A task in the AI results that references the asset means **a task was created about it, and its status is unknown**. That is the only thing the data supports. Such an asset stays on the card, badged so the advisor can close the loop themselves — *"a task was created for this on 4 Aug; worth checking it has been handled."* Never phrase it as in progress, in motion, being worked, or resolved, and never let a task's existence silently drop the asset from the pipeline. The one exception is an explicit client agreement to move the asset, which is its own recorded field and is reported as exactly that.

**Step 4 — Confirm households** per `references/ai-result-shapes.md`: client participants who attend together and share a surname are one household; participants the relationships payload identifies as spouses or partners are one household even with different surnames. Display name "Peter & Paula Reynolds". Weak evidence keeps them separate — a wrong merge puts one family's money on another family's card.

**Step 5 — Join to contact records.** `contacts_search_contacts(term)` → `contacts_get_contact_details(contact_id)` per member. The household key is the sorted member contact ids.

**Step 6 — Build each asset's short trail.** Within the window: first raised, how many meetings it came up in, whether a task references it, and whether the client agreed to move it. That is the whole trail — the window is short by design, so this is cheap.

**Step 7 — Order and state.** Order assets and assign labels per `references/ranking.md` — no score is computed. A household ranks by its strongest single asset. Then assign each asset a state, all of it derived from the window's data:

| State | Rule |
|---|---|
| **New** | Only came up once, in this window |
| **Raised again** | Came up in two or more meetings in this window — repetition is intent, so it ranks higher |
| **Nothing new since** | Came up early in the window and not in the most recent meeting with that household, with no task and no agreement on record. Word it as silence in the record, never as advisor inaction — the advisor may well have dealt with it off-platform |
| **Task created** | A task references it. Status unknown; ask the advisor to confirm. Keep it visible |
| **Client agreed** | The client agreed to move it. Note it once in the header and retire it from the ranking |

**Step 8 — Render** per `references/output-template.md` — read its voice rules first, since every rendered line is advisor language, not system language — and `references/output-modes.md`. Every mention links to its meeting: `https://console.zocks.io/dashboard/workspace/past/room/{room_id}/session/{session_id}/`.

---

## How assets are ranked — no score

**This skill does not compute a score.** The Move Likelihood Score is gone: its weights were invented, and an advisor shown a 68 will reasonably ask why it isn't a 71. Full signal list and ordering rule in `references/ranking.md` — read before ranking.

**Order assets by the first rule that separates them:** a life event with timing attached · the client raised it themselves and asked questions · it came up in more than one meeting in the window · a concern flag or bearish outlook on it · larger stated value · most recent mention. A household ranks by its strongest single asset.

**Three labels, by condition:** **Act now** (a life event with timing, or client-raised with questions) · **Worth raising** (came up more than once, or carries a concern flag) · **Keep an eye on** (everything else that survived the disposition test).

Each label carries one sentence naming the strongest factor in human terms: *"they've brought it up twice this fortnight and just changed jobs."* Print the reason, never a position number.

**No conversion rate, no agreement rate, no firmwide multiplier in the order.** Over one advisor's fortnight the denominator is usually zero or one, so any rate built from it is noise. The firmwide line stays in the header as labelled context and never touches the ranking.

---

## Degradation

- **Insights empty** → drop the counts line and show the window's own totals instead (assets, households, meetings). Never reconstruct insights numbers from the meetings tools, and never explain the data plumbing to the advisor.
- **No suggestions recorded in the window** → say it as a nudge in one line, not as a mechanics note.
- **No meetings in the window** → say so plainly and offer to widen to 30 days. Never invent opportunities, and never reach past 30 days.
- **Contact resolution, zero matches** → keep the item, badge it "unlinked — no Zocks contact", and say recurrence could not be scored.
- **Contact resolution, several matches** → match on the participant's email; still ambiguous in an interactive run, ask once; in a scheduled run, badge it ambiguous.
- **Weak household evidence** → keep individuals separate rather than guessing. A wrong merge is worse than two cards.
- **Meeting with no AI results** → skip it and footnote it ("1 meeting not yet analyzed — rerun later"). Results can lag even though only analyzed meetings appear in listings.
- **Oversized payloads** → the list endpoint may return everything regardless of paging parameters. Classify shapes first, extract only held-away-relevant fields, never quote payloads wholesale.
- **A request asking for a longer window** → run 30 days and say in one line that the radar covers a month at most, and why: older mentions belong in a household review, not a radar.

Never fail silently, and never fill a gap with plausible-sounding content.

---

## Compliance

- Every meeting and AI-result call is locked to the invoking advisor. This skill never reads another advisor's meetings. The one firmwide number is an aggregate, labelled "across the firm", and never attributed to an individual advisor or to another advisor's clients.
- Outputs carry the **minimum**: asset type, custodian name, and approximate value as stated. Never transcript content — no transcript is pulled.
- Nothing persists between runs.

---

## Scheduled mode

A pipeline that has to be asked for is a pipeline that goes cold. Read `references/scheduling.md` before setting one up.

**Cadence:** weekly, Friday afternoon, so the pipeline is current for Monday. Offer it once, after a successful manual run, in one line — then create it rather than leaving the advisor with instructions.

**Invocation:** "run held-away radar". Zero questions.

| Decision | Default |
|---|---|
| Window | 14 days |
| Scoping | The invoking advisor's own meetings |
| Insights | One firmwide context line, plus the advisor's own numbers for the same window |
| Deep-read cap | 40 meetings, by breadth across households |
| Transcripts | Never |
| Output | Interactive pipeline |
| Branding | Applied if a kit exists; unbranded otherwise |

Present it with one summary line: hot count, new, raised again, tasks to confirm. **A quiet week is a valid result** — say so in a line and stop; never invent an opportunity to justify the run. Ambiguous contact matches are badged, never guessed.

Re-running the same day just recomputes the same day — with nothing stored, idempotence is free.

---

## References

- `references/data-budget.md` — the single-pass structure, deep-read cap, the no-transcript rule. Read first.
- `references/extraction.md` — held-away taxonomy, the disposition test, asset keys. Read before detecting.
- `references/ai-result-shapes.md` — payload classification, household assembly, endpoint quirks. Read before parsing.
- `references/ranking.md` — the signals, the ordering rule, the labels, and why there is no score. Read before ordering, not before starting.
- `references/output-template.md` — layout, card structure, voice rules. Read before rendering.
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.
- `references/run-setup.md` — brand kit only; the household question does not apply to this book-wide skill.
- `references/scheduling.md` — recurring runs and unattended behaviour. Read before setting one up.