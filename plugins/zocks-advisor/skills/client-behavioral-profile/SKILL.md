---
name: client-behavioral-profile
description: Reads one advisory client or household two ways — a fast temperature check on whether they are cooling, steady or warming, and a full behavioral profile covering what motivates them, what they are afraid of, how they decide, whether their stated risk tolerance matches their behaviour, and how engaged they have been — then a calibrated stance for the next conversation. Use whenever the user asks what a client is like, how to approach them, what motivates them, how a meeting went, what the mood is, why they won't commit, or whether a relationship is cooling or warming. Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Client Behavioral Profile

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Client Behavioral Profile — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

Two reads of one household, answering a question no CRM field can — what is this client actually like to sit across from, and how should you handle the next hour with them. It reads every structured signal Zocks holds — flagged concerns, sentiment and outlook, who raises which topics, life events, follow-up tasks kept and owed, coverage against the rest of the book — and turns it into an emotional read and a stance.

It does that at two depths, because advisors ask two different questions:

- **`temperature`** — is this relationship cooling, steady, or warming? A read before a phone call. Works from a **single meeting**, sharpens with history, takes seconds.
- **`build_profile`** — who is this household, what drives them, what are they afraid of, and how do I handle the room? A read before a meeting that matters. Needs **two or more analyzed meetings**.

Both are computed fresh on every run. Nothing is saved, so nothing is ever stale and there is no "last updated" to distrust.

---

## Ground rules

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it, ordering it, and drawing conclusions from it is exactly what this skill is for. Introducing a fact that Zocks did not return — a balance, a date, an age, a name, a quote, a relationship — is not, however plausible it looks. A gap is reported as a gap; where a conclusion needs a fact the record lacks, the action is phrased as *verify, check, or ask*. Full rule in `references/data-budget.md`.

## Before the first tool call

Read `references/run-setup.md` and `references/data-budget.md`.

**Which command.** Route on what was asked, not on what's cheapest:

- Mood, direction, "how did that go", "are we losing them", "why won't they commit" → `temperature`.
- "What are they like", "how do I approach", "what motivates", "risk tolerance", "build a profile" → `build_profile`.
- Ambiguous → ask, as two taps: **A quick temperature read** / **The full profile**. Say what each costs in one clause ("a moment" / "reads their whole history").
- Only one analyzed meeting exists → `temperature` is the only honest answer. Run it, and say why the profile isn't available.

**The opening widget:**

> **Who should this be about?** — three household candidates as taps, most imminent first, plus "Someone else"
> **How far back?** — `temperature`: **Last 6 meetings (default)** / Last 3 / Just their last meeting · `build_profile`: **All history (default)** / Last 2 years / Last 12 months
> **Output** — **Interactive report (default)** / Document

Fetch the candidates before asking — the pattern is in `references/run-setup.md`. **The household question is never skipped unless the request named someone unambiguously**, and it is never resolved by guessing between search matches. Skip whatever else the request already answered: "how should I approach the Reynolds before Thursday" answers who and implies the window, so build it and go.

---

## Command — `temperature [household]`

A relationship rarely announces that it's ending. It cools quietly: the client is worried about something they haven't named, they nod at the recommendation and don't commit to it, they're bracing for a change they haven't told you the whole of, and they've stopped bringing their own agenda. Every one of those is legible in a single meeting's structured record.

**One meeting is enough.** Temperature is an emotional state, not a statistical trend — a client who spent an hour deferring every decision is cooling, and it does not take four meetings to know that. Where history exists the read gets sharper: sentiment acquires a direction, concerns acquire recurrence, hesitancy acquires a pattern. The skill never refuses to read. It states what the read rests on and gets on with it. The one thing it won't do is manufacture a trend from thin evidence — a single-meeting read is labelled as one.

**Step 1 — Resolve the household.** `contacts_search_contacts(term)` → `contacts_get_contact_details(contact_id)` per member: name, linked external accounts, segment, held-away pipeline. Say **"systems linked"**, never "CRM linked". Household assembly rules in `references/ai-result-shapes.md`.

**Step 2 — Meeting spine.** `meetings_list_past_meetings(advisor_id="{user_id}", contact_id, from_date=today − 24 months, size=100)`, paginated, once per household member, deduplicated by session, ordered oldest → newest. Listings only — no AI-result calls yet.

- The **latest** meeting is the read. It gets the full deep dive.
- Up to **4 prior** meetings supply direction and recurrence. Fewer is fine. None is fine.
- Where ≥3 meetings exist, compute **personal cadence** — the median gap between their own consecutive meetings — as enrichment only.

**Step 3 — The latest meeting, in full.** `ai_results_list_results(room_id, session_id, meeting_summary_size=0)`. Classify payloads by shape before reading — there is no type discriminator.

| Signal | Payload | What it tells you |
|---|---|---|
| What worries them | Concerns array | The named anxieties, close to their own words |
| What they wouldn't commit to | Topic initiation × tasks × decisions | Topics discussed that acquired no next step — **deferral** |
| How they're feeling | `{overallOutlook, outlooks[]}` | Overall tone, and which asset classes specifically soured |
| What they're bracing for | Life-events object | Anticipated or in-flight change — retirement, a sale, a diagnosis, a move |
| Whether they're still engaged | `initiatedBy` on each topic | Share of the agenda that was theirs |
| Whether there's a next step | Next-meeting reference | Forward commitment, or its absence |

**Step 4 — Prior meetings, read thin.** Same call per prior meeting, but pull **only** outlook, concerns, deferred topics, and the date. Four fields, then discard. This is the single biggest saving in the skill: reading four priors in full to establish a direction that four fields would have shown is pure waste.

**Step 5 — Forward commitment.** `meetings_list_upcoming_meetings(advisor_id="{user_id}", contact_id)` — is anything booked? A next-meeting date from step 3 that has passed with no meeting since means **a meeting was discussed and none has happened**: print it in those words — never as a broken promise, because Zocks cannot tell whether the advisor handled it another way — and cap the read at steady.

**Step 6 — Firm baseline.** One `insights_query_insights`, no date params. **Temperature is relative.** A household that cooled the month the market did is not slipping away, and the recommended move is completely different from the one you'd give an idiosyncratic cooler. Per the tool contract, never supplement this call with meetings-tool aggregation.


**Step 7 — Decide and render** per `references/temperature-read.md`.

### The temperature read

Signals, the ordering rule and the labels are in `references/temperature-read.md` — **read it before deciding**. **There is no score, no index, and no numeric bands.** Every earlier version computed one; it is gone, because its weights were invented and an advisor shown a −31 will reasonably ask why it isn't a −24.

Five signals, each read from a single meeting and each resolving to a **direction** — negative, neutral, or positive — never to a value:

- **What's worrying them.** How heavy, whether it recurs, and whether anything is being done about it.
- **What they wouldn't commit to.** The sharpest signal in the set: topics discussed that acquired no decision and no next step. The client who agrees with everything and commits to nothing is the one who leaves.
- **Sentiment.** Overall outlook, and which asset classes moved. Gains a direction when a prior meeting exists.
- **Transition pressure.** A life change anticipated or underway, and whether the advisor is visibly engaged in it. An unsupported transition is where relationships get reconsidered.
- **Whether they bring their own agenda.** Whose topics they were, and whether anything is booked.

**The verdict — first rule that fits wins.** Two or more signals negative, or a recommendation declined outright → **cooling**. Two or more positive and none negative → **warming**. Otherwise → **steady**. A discussed meeting that never happened caps the read at steady however good everything else looks — it never triggers cooling on its own.

**Read confidence** is stated every time and never used to withhold a read: **too early to tell** (one meeting) · **tentative** (two meetings, or signals thin) · **firm** (three or more).

The card names the two or three signals that decided the verdict, with their evidence and dates. Never a number, and never a number as a headline.

### The temperature card

Short — this is a read before a phone call, not a report. Cooling, steady and warming are a temperature, not a grade; whatever visual treatment carries them should read that way, and one line on the card should say so, so nobody mistakes a cool read for a bad client.

**The three-step track** is the element to build well: cooling · steady · warming as three discrete stops, with this read's stop marked and the verdict above it. Three stops, not a continuous scale — there is no score behind the verdict, so a marker sitting at some precise point along a bar would imply a position the data doesn't have.

Where prior meetings place them, mark their earlier stops on the same track so the direction reads at a glance. A household at steady that was warming three meetings ago is a different conversation from one that has been steady all year, and this is the only element that makes that instantly visible.

| Element | Content |
|---|---|
| Header | Household name · read-confidence label · window covered ("their last meeting, 11 July" or "last 5 meetings, Nov–Jul") |
| Temperature track | Verdict and the marked stop, plus earlier stops where history allows, per above |
| Driving signals | 2–3 rows, the signals that decided the verdict first, each naming its evidence: *"Three of the five topics you covered ended without a next step — the rollover, the trust review, and the insurance question."* A count of things the advisor can check is welcome; a component letter or an index value is not |
| Firm context | One line: is the book moving the same way, or is this specific to them |
| The move | One action, concrete and small enough to do today, in their own words where the structured results carry them. The most prominent thing on the card after the verdict |
| Action | **"Open the meeting in Zocks →"** — ids come from step 2 at no extra cost |

If a signal has nothing behind it, omit the row.

---

## Command — `build_profile [household]`

Each step feeds the next. Don't skip ahead; the firm comparison in step 6 is meaningless without the concern set from step 3.

**Step 1 — Resolve the person and the household.** `contacts_search_contacts(term)` → `contacts_get_contact_details(contact_id)`. Read linked external accounts and integration identifiers, email, phone, any held-away or segment metadata. One household is one profile — spouses and partners get a single read, because the decision is made jointly and the anxieties are usually not the same on both sides. Note where they diverge.

**Step 2 — The meeting spine and cadence.** `meetings_list_past_meetings(advisor_id="{user_id}", contact_id, from_date, size=100)`, paginated. Per meeting: date, `room_id`, `session_id`, participants. Compute meeting count, median days between meetings, recency, and whether a second household member attends the decision meetings. **Client-initiated ratio** is the cheapest anxiety proxy available — a ratio that has jumped in the last 90 days is telling you something before you read a single word of content.

**Step 3 — Structured AI results, the richest layer.** `ai_results_list_results(room_id, session_id, meeting_summary_size=0)` per meeting in the window. Pull: concerns, topic initiation (`initiatedBy`), outlook and sentiment, open tasks, life events, next-meeting references, planning-domain positions, personal facts.

- **Concern persistence** per concern: `times_raised / (times_raised + times_resolved)`. Near 1.0 means the topic keeps coming up and never gets put to bed — that is what the client walks in carrying.
- **Coverage gap**: a concern the client raised that no task, decision, or subsequent discussion ever answered. These become the stance's must-address list, and a stack of them is what fragile trust actually looks like in data.

This is the expensive step. The window the advisor chose is the budget; if it is "all history" and the household has thirty meetings, read the most recent twelve in full and the rest for concerns, outlook, and life events only — then say so in the footer.

**Step 4 — Meeting summaries for coverage patterns.** `ai_results_get_meeting_summary(meeting_summary_id)` on the most recent 10–15, and only where step 3 left coverage ambiguous. These carry advisor topic-coverage data. Use them to separate three different things: topics the client always raises unprompted, topics that only ever get covered when the advisor initiates, and topics that appear and then never get followed up.


**Step 5 — Firm context.** `insights_query_insights` with no date params for the 30-day firmwide picture, plus a scoped call with `user_id_list=["{user_id}"]` for this advisor's own coverage. For each of this client's concerns: firmwide theme, or theirs alone? A worry shared by most of the book is a market-conditions conversation. The same worry held alone is a personal one, and framing it as market conditions makes the client feel unheard.

**Step 6 — Derive and render.**

### Named metrics

**Concern persistence — as a count, not a ratio.** Per concern, across the window: how many meetings it was raised in, and in how many of those the record shows it addressed. Report it that way — *"raised in four of their last five meetings, addressed in none"* — because the count is checkable and a 0.78 is not. A concern raised in three or more consecutive meetings with nothing attached is an unresolved anxiety, not a passing question.

**Who asks for the meetings — as a count.** How many of the recent meetings the client asked for, against how many they asked for before: *"they asked for one of the last six; before that it was four of six."* Always against the client's own earlier pattern, never against a firmwide average — some households simply call more.

**Risk gap — stated versus revealed** — the signature read of the profile, and deliberately a comparison rather than a number. Clients describe themselves as progressive and then behave nothing like it.

Set what they have **said** about risk — from the planning-domain payload, the financial profile, or their own words — beside what their **behaviour** across the window shows:

- growth or volatility recommendations accepted with a decision or task attached, versus discussed and deferred with nothing attached
- client-initiated topics about opportunity and increasing exposure, versus loss, drawdown or volatility concerns in the concerns payload
- negative outlook on equity or market asset classes

Then say which of three things is true, in words, with the evidence for it:

- **They describe more appetite than they show.** Expect a signed-off strategy they quietly don't sleep with — worth checking they understood the downside they agreed to.
- **Stated and revealed line up.**
- **They show more appetite than their paperwork suggests** — often a client who has outgrown a risk questionnaire filled in years ago.

**Requires a stated posture on record and at least three behaviour signals.** Below that it reads "not measurable — no stated risk posture in Zocks" and the profile says so. **A flag, not a figure — no 1–5 bands and no gap number.** A fabricated risk mismatch is worse than a blank field, because someone will act on it — and a number invites exactly that.

**Relative anxiety** — how heavily this client's concerns read against the firmwide picture from step 5, in plain language ("higher than most of the book right now"), never as a percentile the advisor has to interpret.

### Profile sections

Section order is fixed; an advisor reading their fourth profile should know where to look.

**Who this household is** — two to four sentences. How they relate to money, what drives them, how they decide, the life fact that colours every conversation. Written the way a senior advisor briefs a colleague they're handing the relationship to.

**Emotional readiness** — the top block:

- **What motivates them** — what they have actually said they're working toward, in their terms, not a goals-field paraphrase. The thing they light up about
- **What they're afraid of** — their financial fears, plus the personal fear underneath where it's on record. "Running out" and "being a burden to my daughter" need different handling
- **Readiness** — are they in a position to hear a recommendation right now, or is something in the way
- **Trust** — high, building, or fragile, with the one-line reason from the data
- **Decision pace** — deliberate, needs-time, avoidant, action-oriented
- **Communication style** — data-heavy, narrative, reassurance-seeking
- **Who decides** — one voice, joint, or one nominal and one actual decision-maker
- **Risk gap** — which of the three reads is true and what it implies, or the honest "not measurable"
- **Current temperature** — the `temperature` verdict, computed from the data already loaded at no extra cost, with its confidence label

**What's on their mind** — a short paragraph per active concern: what it is, how long it's been running, whether it's getting better or worse, and their own words where the structured results carry them. A concern raised four times unresolved gets four times the space of a passing question.

**Life context** — retirement horizon, health, wealth transfer intent, professional change, pending decisions. Plus open commitments: what was promised, when, and how many days it has been open.

**How they engage** — meeting count and median cadence, client-initiated ratio with its interpretation, topics they always raise, topics that wait for the advisor, topics the advisor consistently skips.

**Held-away** — known outside assets, how warm, when last discussed, what the next step is. Omit the section entirely when nothing has ever come up.

**Against the firm** — shared concerns with the firmwide figure, concerns unique to them, relative anxiety, and the date the firm data covers.

**Your stance** — the payoff, and the part read on the way into the room:

- **Tone**, with a phrase of rationale
- **Open with** — a suggested first sentence in the client's own language
- **Bring** — specific materials
- **Must address** — open commitments and coverage gaps; they are expecting these
- **Handle carefully** — topics with a negative reaction on record
- **Consolidation opening** — held-away, only when the moment is right: pipeline warm, no negative sentiment last meeting, no high-intensity concern open

**Interactive extras.** Concerns expand to reveal the meeting behind them, each linking to Zocks. The engagement figures filter by period. The Revealed Risk Gap shows both bands and the signals that produced them on tap — an advisor will not act on a mismatch they can't audit.

### Deriving the behavioural reads

These are inferred, and the rules matter more than the labels.

**Readiness** — not ready when a concern has been raised repeatedly with nothing attached and the territory is still uncovered, or a life event is flagged with nothing done about it. Ready when open commitments are current and the last meeting's outlook wasn't negative.

**Trust** — fragile when two or more commitments have been open past 30 days and the client keeps re-raising the same themes. High when follow-through is visible in the summaries and no sentiment flag suggests pushback. Otherwise building.

**Decision pace** — avoidant when the same decision has been open across three or more meetings with no progress. Action-oriented when tasks close inside two weeks. Needs-time when they close slowly and the client asks to revisit.

**Who decides** — a second household member attending more than half the major decision meetings (rebalancing, estate, drawdown, consolidation) means the profile is a household profile and a solo meeting won't produce a decision.

---

## Output

Render per `references/output-modes.md`, never as chat text — a plain chat paragraph is a failed render, and chat text around the artifact is at most one line. Both commands offer the same two modes and carry the same header and footer disclosure.

Both outputs are **internal**. Neither is written for a client to read, and neither should be handed to one.

---

## Degradation

- **Zero contact matches** → say so, suggest a spelling check. Never profile a guess.
- **Several matches** → the disambiguation is a tap, showing name plus last meeting date.
- **No analyzed meetings at all** → the only case that blocks both commands. Say what the contact record holds, and note that Zocks lists meetings only once they've been AI-analyzed.
- **Exactly one analyzed meeting** → `temperature` runs normally, with read confidence **too early to tell**. `build_profile` does not: return what the one meeting holds, name what a profile would need, and stop. Two meetings cannot carry persistence, cadence, or a risk gap.
- **The latest meeting has no AI results** → read the most recent meeting that does, and say which meeting the read came from. An advisor asking straight after a meeting should be told the analysis isn't in yet, not handed a stale read presented as current.
- **Outlook payloads absent** → sentiment is unreadable. Decide on the remaining signals, per `references/temperature-read.md`, and say sentiment wasn't available.
- **Fewer than 2 topics in a meeting** → hesitancy is **unknown, not absent**. A one-topic meeting can't show a pattern of deferral, and treating it as neutral quietly reports "no hesitancy" when the truth is "no evidence". Say which.
- **Empty insights** → drop the firm-comparison section with a one-line note. Never substitute meetings-tool aggregation; the tool contract forbids it.
- **Weak household evidence** → keep them separate and say so.
- **Pagination** → follow it to the end of the window. A profile built on page one of a long history silently misses the beginning of the relationship.

Never fail silently; never fill a gap with plausible-sounding content.

---

## Compliance

Health details, family conflict, and bereavement land in these outputs because they change how the meeting should go. Two rules: both outputs are internal and never shown to the client, and exported documents carry the minimum phrasing that makes the point — never a transcript excerpt.

Scope is the invoking advisor's own book (`advisor_id="{user_id}"`). This skill never reads another advisor's meetings; the only firmwide call is aggregate insights. Nothing persists between runs.

**This skill is on-demand only.** It is scoped to one named household, so it is never scheduled — a recurring run would have to assume a household, and this library does not assume households.

---

## References

- `references/run-setup.md` — brand kit, the household question, opening decisions. Read first.
- `references/data-budget.md` — what to fetch, what to skip, when to stop. Read first.
- `references/ai-result-shapes.md` — payload classification, deferral detection, household assembly, endpoint quirks. Read before parsing.
- `references/temperature-read.md` — the five signals, the verdict rule, the caps, read confidence, and the next-meeting shape table. Read before computing a temperature.
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.