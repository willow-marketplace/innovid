---
name: client-attrition-risk
description: Finds the clients an advisor is quietly losing from their own book, ranking the households that have gone silent past their usual rhythm by how they felt last time, whether their sentiment was already falling, whether they stopped driving the conversation, and whether they have named another advisor. Says who to call first and how to open. Use whenever the user asks who is at risk, who has gone quiet, who they haven't spoken to in a while, who to call this week, or whether they are losing anyone — and for a re-engagement list, a brief on one household, or a draft re-engagement email. Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Client Attrition Risk

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Client Attrition Risk — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

The clients who leave are rarely the ones who complained. They are the ones who went quiet after a conversation that never resolved, whose sentiment had been sliding for two meetings, who stopped bringing their own agenda, and who mentioned once that someone else's advisor offers something you don't. Every one of those is a structured field in Zocks. This skill sweeps the advisor's own book, finds everyone past their re-engagement threshold, and ranks them by weighing the silence against how the relationship was already behaving before it went quiet — an explicit ordering rule and plain-language labels, never a score.

An anxious client silent for 90 days is a different emergency than a content one, and the ranking says so.

Detection runs on **past meetings only** — attrition is what already happened, not what's booked. Households with a meeting on the calendar are surfaced separately; they usually shouldn't count, because re-engagement is already scheduled.

---

## Ground rules

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it, ordering it, and drawing conclusions from it is exactly what this skill is for. Introducing a fact that Zocks did not return — a balance, a date, an age, a name, a quote, a relationship — is not, however plausible it looks. A gap is reported as a gap; where a conclusion needs a fact the record lacks, the action is phrased as *verify, check, or ask*. Full rule in `references/data-budget.md`.

**Identity rule — never ask who the user is.** Pass `advisor_id="{user_id}"` (that literal token) to the meetings tools and the MCP scopes everything to the signed-in advisor. `users_list_users` is not needed here.

**Cost shape.** This is the most expensive skill in the library if run carelessly, because a book sweep can touch every meeting the advisor has ever had. It is affordable because of the two-pass structure below: listings across the whole book, then AI results on the shortlist only. Read `references/data-budget.md` before the first call — the transcript rule and the depth-cut discipline live there.

## Before the first tool call

Read `references/run-setup.md` and `references/data-budget.md`. Nothing about this scan is stored; every run sweeps the book fresh.

This skill's opening widget:

> **How long is too long without a conversation?** — 60 days / **90 days (default)** / 120 days / custom
> **How far back should I look?** — **2 years (default)** / 12 months / all history
> **How many households should I read in depth?** — **10 (default)** / 5 — everyone else past the threshold is still listed from the meeting listings, just not deep-read
> **Output** — **Interactive report (default)** / Document

A threshold already in the request ("who haven't I seen in six months") answers the first question — don't ask it again.

**There's no "who" question here, and that's deliberate.** This is the one household-facing skill in the library that is book-wide by design: the whole point is that the advisor doesn't yet know who to worry about. Asking them to name someone would invert the skill. Where the request *does* name a household ("am I losing the Reynolds?"), skip the scan and go straight to `brief [client]` for that one household.

The drill-downs are still taps, never typing: **the ranked cards are the picker**. `brief` and `draft` are reached from a card, and if an advisor asks for a brief without naming anyone, offer the top three at-risk households from the scan just run rather than asking them to type a name.

---

## Command — `attrition_scan [threshold?]`

**Step 1 — Book sweep. Listings only, no AI-result calls.** `meetings_list_past_meetings(advisor_id="{user_id}", from_date=today−lookback, size=100)`, paginated to exhaustion — never rank from a partial sweep. Listings are cheap and this is the one place the skill is genuinely exhaustive. Keep client meetings: drop solo meetings and meetings whose non-advisor participants are all colleagues (advisor's own email domain) or unidentifiable. Every meeting dropped here is a per-meeting AI-result call saved later for nothing lost.

Participant ids in the listing **are** contact ids, but real books carry duplicate records for the same human from CRM sync. Group into households by normalised name and email before any arithmetic; a household's last touch is the latest meeting across all its records.

Per household: last meeting (date, `room_id`, `session_id`), meeting count, and **personal cadence** — the median gap between their own consecutive meetings. A client who meets monthly and goes 80 days quiet is in more trouble than a semi-annual client at 100 days, and a fixed threshold cannot see that.

**Step 2 — Threshold filter.** Keep households where `days_silent > threshold`. Zero of them → healthy-book card, and stop. Never manufacture urgency.

**Step 3 — Booked-meeting check.** `meetings_list_upcoming_meetings(advisor_id="{user_id}")`, paginated. Intersect with the filtered set. If any overlap, one tap: "N of these already have a meeting booked — include them?" **Exclude (recommended)** / Include. Included households carry a "booked [date]" chip.

**Step 4 — Depth cut. Ten households at most, ever.** Order by silence against each household's own rhythm, and deep-dive the top **10** — or the top **5** where the advisor chose the lighter scan in the opening widget. **Ten is a hard ceiling, not a default to negotiate**: however large the book and however many households pass the threshold, never deep-read an eleventh — the deep reads are where this skill's cost lives. The rest appear in a footer with name and days silent, straight from the contact and meeting listings — visible, just not deep-read. Stop earlier still when three consecutive deep reads haven't changed the top of the ranking, or when the remaining candidates would all land at **Worth a note**; the tail of a silence-ranked list does not surprise you. Whatever the cut lands at, the footer says so.

**Step 5 — Read the last two or three meetings, not only the last one.** `ai_results_list_results(room_id, session_id, meeting_summary_size=0)` per meeting. Classify payloads by shape before reading — `references/ai-result-shapes.md`.

The **last** meeting gets a full read. The one or two before it exist only to establish trajectory, so pull **only** outlook, concerns, topic initiation, and the date from those — four fields, then discard. Reading three meetings in full per household across fifteen households is the difference between a scan an advisor runs every Monday and one they run once.

This is where the risk signals come from:

- **Emotional state at last contact** → how they left the room: content · neutral · concerned · anxious. Second rule in the ordering chain.
- **Sentiment trajectory** → the outlook payload across the last two or three meetings. A client who left *content* after two meetings of falling sentiment is not a content client; they are a client who stopped saying what they think. Falling-then-flat is the most dangerous shape in the book.
- **Disengagement** → topic initiation. Compute the client's share of `initiatedBy` in their last meeting against their own earlier share. A household that used to bring three questions and brought none has already partly left. Engagement analytics payloads corroborate where present.
- **Competitive exposure** → mentions of another advisor, another firm, an outside product, or a capability the client wants that the firm doesn't offer. These surface in the concerns payload, in topic initiation on outside topics, in planning-domain payloads, and in held-away discussion. This is the signal that separates "we have been neglecting them" from "someone else is already talking to them", and it changes what the call has to accomplish.

Also collect: commitments the advisor made, unresolved concerns, flagged life events, extracted next-meeting dates.

**Zocks does not track task completion, and silence is not proof of inaction.** No meeting since the last one means nothing in the record has closed these items — it does not mean the advisor never dealt with them, and the scan must never say or imply that they didn't. Treat them as *open in the record*, count them toward the unresolved load, and word them as things that were said, with dates.

**A next-meeting date that passed with no meeting since** is the exception worth naming, because it is an observation about the calendar rather than a claim about a task: "a plan presentation was discussed for Dec 16 and no meeting has happened since." That phrasing is supportable and it was the most common attrition signal in live testing. "Broken promise" is not supportable — never use it, in output or in reasoning.

**Step 6 — Value weight.** `contacts_get_contact_details` → linked external accounts, held-away pipeline, segment metadata → the value-and-segment signal, a late tie-break in the ordering. Say **"systems linked"**, never "CRM linked".

**Step 7 — Firm context, firmwide and labelled as such.** One `insights_query_insights`, no date params. This is the deliberate bird's-eye view: firm-level numbers give the advisor context their own book cannot, so every line built from it reads "across the firm" and never "you" or "your clients". It is context only — it never enters the score or the ranking. Match current firmwide themes against each household's last-known concerns: a client who went quiet worrying about something that has since become a firmwide theme has been sitting in it alone, and that is the opener. Never supplement this call with meetings-tool aggregation — the tool contract forbids it.


**Step 8 — Order and render** per `references/metrics.md`. No score is computed.

---

### `brief [client]` — full re-engagement brief

1. Resolve the household with `contacts_search_contacts`. Zero or several matches is a tap, never a guess.
2. `contacts_get_contact_details` for value and linked systems.
3. `meetings_list_past_meetings(contact_id, size=10)` — the arc, not just the last touch.
4. `ai_results_list_results` on the five most recent meetings. Build the commitment ledger with ages, concern persistence per concern (`times_raised / (times_raised + times_resolved)` — near 1.0 is the worry they have been sitting with in silence), the sentiment and ownership trend across meetings, the life-event timeline with elapsed windows, and every competitive mention with its date.
5. One `insights_query_insights` for where their concerns sit against the book now — firmwide, so any line built from it says "across the firm".
6. Sections: **Where the relationship stands** → **How they left the room** → **What was already sliding** → **Who else is in the picture** (omitted entirely when there are no mentions) → **What you still owe them**, each with age → **What's had time to develop** → **How to re-open**: the opener in their words, two or three talking points, and the concrete ask.

### `draft [client]` — re-engagement email draft

Reuse loaded data; only refetch the last meeting's results if nothing is loaded. Subject plus 120–180 words. Echo the client's own words for exactly one concern or life event. Acknowledge the gap plainly, with no apology theatre. No product pitch. No account numbers, balances, or sensitive details — first name only. One concrete ask offering two specific windows. Sign off `[Your name]`.

Deliver the draft in chat so it can be copied as-is, or inside the document when that is the chosen mode. **The skill never sends anything, and there is no send button.**

---

## How the ranking works — no score

**This skill does not compute a score.** There is no Attrition Risk Score, no index, no 0–100. Full signal list, ordering rule and labels in `references/metrics.md` — read it before ranking, so two scans of the same book agree.

The reason is worth stating in the file, because it will be tempting to add one back: every weight in a score like that is invented, the first thing an advisor asks is how the number was built, and "we chose those weights" is not an answer an advisor can act on. A number also invites them to argue with the model instead of reading the client.

**Order households by the first rule that separates them:** competitive exposure on record · anxious or concerned at last contact · sentiment falling or falling-then-flat · ownership gone · an elapsed life event or a discussed meeting that never happened · silence against their own rhythm · unresolved load · value · days silent.

That is a priority chain, not a sum. A client with a competitor in the picture is a different problem from one who is merely overdue, and averaging the two produces a ranking that is wrong about both.

**Three labels, by condition:** **call today** (competitive exposure, or concerned/anxious plus silence past their rhythm) · **this week** (silence past their rhythm plus one other negative signal) · **worth a note** (past the threshold, nothing else firing).

**Print the reason, never a number.** "Third because a competitor was named in May" is the whole justification, and the advisor can check it.

Silence is always expressed against the client's own rhythm — "168 days, about three times their usual" — never as a bare day count, because a monthly client at 80 days is in more trouble than a semi-annual client at 100. Competitive exposure is **never inferred from silence**; it requires an actual mention.

---

## Output — the attrition brief

Render per `references/output-modes.md`. Both modes carry the same content.

**Header:** date, threshold used, at-risk count against book size, one firm-pulse line, and a note that the scan covers Zocks-analyzed meetings only.

**One card per household, in rank order:**

| Element | Content |
|---|---|
| Title row | Rank, household name, the label in the heaviest weight, days silent expressed against their rhythm ("142 days — 3× their usual"), "booked [date]" chip where relevant |
| Why they're here | One plain-English line naming the dominant drivers. Never the formula, never a component letter |
| How they left the room | The emotional read from the last meeting, with their phrasing where the structured results carry it |
| What was already sliding | The sentiment and ownership trend in words: "71% of topics were theirs in the autumn, none in the last meeting" |
| Who else is in the picture | Competitive mentions with dates and what was wanted. Omit the row entirely when there are none — an empty row here reads as reassurance, and it isn't one |
| What you told them you'd do | Each commitment made, with when it was said. Never labelled done, outstanding or overdue — Zocks has no completion status. Where a next meeting was discussed and none has happened since, say exactly that |
| Time has passed on | Life events flagged then, with the elapsed window: "daughter's college decision — flagged 5 months ago, decided by now" |
| Firm context | One line where their concern has since become a firmwide theme |
| Open with | The suggested opener, grounded in their last conversation |
| Actions | **Open last meeting in Zocks** → `https://console.zocks.io/dashboard/workspace/past/room/{room_id}/session/{session_id}/`, ids from Step 1. Plus `sendPrompt` buttons for **Full re-engagement brief** and **Draft re-engagement email** |

Omit any row with nothing behind it. Every line is earned by data.

**Interactive extras:** sort by rank, days silent, or value; filter to competitive-exposure cases, or to households where a discussed meeting never happened; each card expands to its evidence.

**Footer:** households beyond the depth cut, with days silent.

---

## Scheduled mode

This skill schedules well — the whole point is catching what an advisor wouldn't have thought to look for. Read `references/scheduling.md` before setting one up.

**Cadence:** weekly, Monday morning. Offer it once, at the end of a successful manual run, as a single line — never as an opening question, and never twice.

**Unattended defaults, applied silently:**

| Decision | Default |
|---|---|
| Threshold | 90 days |
| Lookback | 2 years |
| Scoping | The invoking advisor's own book. Never widens to firmwide |
| Booked meetings | Excluded from the ranking, listed separately |
| Depth cut | Top 10 households (5 if chosen in the widget). 10 is a hard ceiling, including unattended runs |
| Output | Interactive report |
| Branding | Applied if a kit exists; unbranded otherwise, and no kit is written |

**Ambiguity resolves conservatively and says so.** A contact search returning several matches becomes a footer line, not a guess. **An empty result is a valid result** — a Monday scan that finds nobody past the threshold produces the healthy-book card and stops. It never lowers the threshold to justify having run.

Nothing is stored, so a repeated run on the same day recomputes rather than duplicating. The newer report is simply the true one.

---

## Degradation

- **Zero at-risk households** → healthy-book card naming the threshold, the book size, and the three closest to the line with days remaining.
- **Fewer than three meetings in the window** → personal cadence undefined. Fall back to the threshold and say "new relationship — cadence not yet established".
- **Last meeting has no AI results** → fall back to the next most recent and say so. If nothing in the window has results, the household cannot be ranked: list it in the footer with the date of its last analyzed meeting.
- **Households with no analyzed meetings at all** are invisible to this scan by construction, since only analyzed meetings appear in listings. State that once in the footer.
- **Empty insights** → drop the firm-context lines with a note. Never substitute meetings-tool aggregation.
- **Contact search zero or many matches** → ask. Never guess a household.
- **Pagination** → every page, in steps 1 and 3. A partial sweep presenting itself as the whole book is the worst failure available to this skill.

Never fail silently. Never fill a gap with something plausible.

---

## Compliance

Scope is the invoking advisor's own book; this skill never reads another advisor's meetings. Competitive mentions are commercially sensitive and stay internal. Nothing persists between runs. Email drafts are never sent.

---

## References

- `references/run-setup.md` — brand kit, the household question, opening decisions. Read first.
- `references/data-budget.md` — the two-pass structure, depth cuts, the no-transcript rule, stop conditions. Read first.
- `references/metrics.md` — the signals, the ordering chain, the labels, cadence, and why there is no score. Read before ranking.
- `references/ai-result-shapes.md` — payload shape classification, household assembly, endpoint quirks. Read before parsing results.
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.
- `references/scheduling.md` — recurring runs and unattended behaviour. Read before setting one up.