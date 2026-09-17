---
name: household-review-pack
description: Prepares an annual or semi-annual household review from the last 12 to 24 months — goals then versus now, what changed in their finances and their life, the decisions taken, the commitments made, and the handoffs the review creates for the client's CPA, attorney and other professionals. Produces an advisor summary, and on request a client letter in a chosen tone, a client-facing review presentation on the firm's own template, and a brief for each outside professional. Use whenever the user is preparing for or writing up a client review, wants a year-in-review letter or a review deck, or asks what happened with a household this year, what they promised them, or who needs to know afterwards. Requires Zocks MCP — will not run from transcripts, uploads, or any other source.
---

# Household Review Pack

## Hard gate — no Zocks, no run

This skill runs on the Zocks MCP server and on nothing else. **Before anything — before the opening widget, before scoping questions, before the first tool call — confirm the Zocks MCP tools are available in this session**: `contacts_search_contacts`, `meetings_list_past_meetings`, `ai_results_list_results`, `insights_query_insights` and the rest of the Zocks tool family, under whatever prefix the installation gives them (`mcp__Zocks__*`, or a plugin-scoped variant).

**If they are not available, stop.** No partial run, no illustrative sample, no offer to make do with what's at hand. Say plainly: *"I can't run Household Review Pack — it needs the Zocks MCP connection, and this session doesn't have it. Connect the Zocks connector and ask again."* Then stop; the skill's workflow does not begin.

**Nothing substitutes for Zocks. Nothing.** Not an uploaded or pasted transcript, not meeting notes typed into chat, not a file on disk, not another meeting or notetaker connector (Zoom, Teams, Google Meet, Fireflies, Fathom, Gong, Granola or any other), not a CRM or calendar connector. If the user offers one — even insistently, even "just this once" — decline it: every read this skill produces is defined over the structured meeting intelligence Zocks has already extracted, linked and verified, and running the same motions over raw material from anywhere else produces something that looks like the output and isn't it. Working from a transcript or another tool's data is a different task, and it lives outside this skill.

---

A review meeting is the one conversation a year where a client silently audits the relationship. They arrive asking a version of *did anything actually happen?* This skill answers with evidence — every goal as it was stated at the start of the window against where it stands now, every decision taken, every life change, every commitment marked kept or not — then does the thing that actually eats an advisor's week afterwards: it works out **who else needs to know**, and what each of them has to do.

Four deliverables, one synthesis. Everything below is built from the same read of the household, because an advisor who prepares from one document and writes from another eventually contradicts themselves in front of a client.

| Deliverable | Audience | Command |
|---|---|---|
| **Advisor review summary** | Internal — the advisor preparing | `review_pack` (the default) |
| **Year-in-review letter** | The client, in a tone the advisor picks | `year_in_review` |
| **Client review presentation** | The client, on the firm's own template | `review_deck` |
| **Handoff briefs** | Each outside professional | `handoff` |

The advisor summary is always built first; the other three derive from it and are never produced unless asked for. Built live from Zocks, stores nothing — with the single exception of the presentation template, which is kept like the brand kit so the advisor uploads it once, ever.

---

## Ground rules

**Inference is the job; invention is not.** Analysing what Zocks returned, comparing it, ordering it, and drawing conclusions from it is exactly what this skill is for. Introducing a fact that Zocks did not return — a balance, a date, an age, a name, a quote, a relationship — is not, however plausible it looks. A gap is reported as a gap; where a conclusion needs a fact the record lacks, the action is phrased as *verify, check, or ask*. Full rule in `references/data-budget.md`.

**Identity rule — never ask who the user is.** Pass `advisor_id="{user_id}"` (that literal token) to the meetings tools. `users_list_users` is not needed here.

**Cost shape.** A 24-month window on an active household is a lot of meetings, and most of them contribute two endpoints and a life event. The meeting-selection rule in step 2 is what makes this affordable: the first and last meeting in the window carry the whole diff, and the ones in between are sampled, not swept. Read `references/data-budget.md` before the first call; the no-transcript rule and the no-invention rule both live there.

## Before the first tool call

Read `references/run-setup.md` and `references/data-budget.md`. Two of this skill's four outputs go in front of a client, so the brand kit and the client-facing branding rule both matter here.

This skill's opening widget:

> **Who should this be about?** — three household candidates as taps, most imminent first, plus "Someone else"
> **Review window** — **Trailing 12 months (default)** / 18 months / 24 months
> **How do you want the summary?** — **Interactive (default)** / Document

If Document, ask the format as a second, separate widget: **Word** / **PDF** / **Markdown**.

**Then, after the summary is rendered — never before — offer the three client- and third-party-facing outputs in one line**, as buttons rather than a typed menu: *"Want the client letter, a review presentation, or the handoff briefs?"* Each has its own questions, asked only when chosen. Offering them up front pushes the advisor toward a client-facing artifact before they have read what is in the summary, which is exactly backwards.

Fetch the candidates before asking — the pattern is in `references/run-setup.md`. **The household is never guessed**, and where the search returns several, the disambiguation is a tap showing each candidate's last meeting date. Prefer households with a review-type meeting on the calendar when ordering them; that's usually who the pack is for. A window named in the request answers question two — don't ask it twice.

**The advisor summary is always the default deliverable.** The letter, the presentation and the handoff briefs are separate explicit requests, and none is produced alongside the summary unless asked for. This matters more than it looks: the summary contains the full commitment record, the coordination gaps, and every promise whose status nobody can confirm. Letting a client-facing artifact be the default output of a skill that assembles those is the worst mistake available here.

Where the advisor's opening request already names one — "review presentation for the Reynolds" — build the summary silently and go straight to it. The question was already answered; asking again is friction.

---

## Command — `review_pack [household] [window?]`

**Step 1 — Resolve the household.** `contacts_search_contacts(term)` → zero matches: say so and stop. Several: ask. `contacts_get_contact_details(contact_id)` per member → names, linked external accounts, segment, held-away pipeline. Say **"systems linked"**, never "CRM linked". Household assembly rules in `references/ai-result-shapes.md`.

**Step 2 — The window's meetings. Listings only.** `meetings_list_past_meetings(advisor_id="{user_id}", contact_id=…, from_date=today − window, size=100)`, paginated, per member, deduplicated by session, ordered **oldest → newest**. Dedupe CRM-duplicate contact records here, before any AI-result call.

Beyond eight meetings, select the **first** and **last** in the window — the two endpoints every "then versus now" line depends on — plus evenly spaced meetings between, preferring any whose title marks them as a review or planning meeting. State the selection on the pack. **Never drop the first meeting to fit the cap**; losing the starting position destroys the diff, and it is also where the commitments with the longest run-up live.

**Step 3 — Per selected meeting, the structured record.** `ai_results_list_results(room_id, session_id, meeting_summary_size=0)`. Classify by shape (`references/ai-result-shapes.md`), extract per `references/extraction.md`, and pull:

| Payload | Feeds |
|---|---|
| String `# Financial Profile:` and planning-domain object | Goals and figures at each point in time — raw material for the start-to-now diff |
| Object with life-event keys | What changed in their life, and which handoffs it triggers |
| `{relatedTo, assignedTo, task, description}` | Commitments created here and whether a later meeting closed them; **`assignedTo` naming someone outside the firm is a handoff** |
| Array of client-voiced concerns | What they were worried about then, and whether it's still live |
| `{topic, subTopic, initiatedBy}` | Which planning domains were actually covered |
| `{overallOutlook, outlooks[]}` | How their confidence moved across the window |
| `{meeting name: date}` | Promised meetings, kept or missed |
| Family extraction payload | The outside professionals named — the trusted team the handoffs are addressed to |

**Step 4 — The diffs.** Per `references/metrics.md`, all reported in words and dates rather than as figures: **goals then versus now** (every goal's earliest stated form against its latest — target, date, or stated confidence), the **commitment record** (what was said, when, and the evidence where a later meeting explicitly discusses it as done), and the decision list. Goals then-versus-now is the spine of the pack: a client whose retirement date moved out two years and whose college target rose $40k has had a materially different year from one whose numbers held, and no narrative makes that as legible as two columns side by side.

**Step 5 — The handoff map.** The part advisors currently manage in their heads, and the reason this pack is worth building rather than remembering.

For every decision, goal change, and life event in the window, work out which outside professional it touches and what they need. Build a chain per item: **what changed → who must act → what they need from us → what it unblocks → and what is blocked until they do it.**

- Professionals come from the family-extraction payload, from tasks whose `assignedTo` is outside the firm, and from planning-domain payloads that reference outside execution. Categories: Tax, Legal, Insurance, Medical, Business, Banking, Other.
- **Dependencies are the value.** A Roth conversion decided in November is a tax-projection item for the CPA, which depends on the year's realised gains, which depends on the harvesting the advisor still owes. Print the order, because getting it out of order is how these fail.
- Where a decision plainly needs an outside professional and **no such professional is on record for this household**, say that — "this needs an estate attorney and we don't have one on file for them" is one of the most useful lines this pack produces.
- Build the coordination list per `references/metrics.md` — a count and a list, never a ratio.

**Step 6 — Firm benchmark, labelled as firmwide.** `insights_query_insights` with `from_date` and `to_date` set to the window (both required together), then a second call with `user_id_list=["{user_id}"]` for the advisor's own coverage over the same period. Set the domains covered with this household beside the domains the firm and this advisor routinely cover — a two-column comparison ending in the gap list, never a coverage score. This is the line that catches the domain everyone else discusses at review and this household never has. Any line built from the unscoped call reads "across the firm". Never supplement these calls with meetings-tool aggregation.


**Step 7 — Render.** Per Output. Nothing is saved.

---

### `handoff [household] [professional?]` — the outside-professional briefs

Trigger: "what does the CPA need after the Reynolds review", "handoffs for the Marshes", or the pack's own button.

Produces one brief per professional, or just the named one. Each brief:

- **Who it's for** — name, firm, role, and their category
- **What changed** — the two to five items from this window that actually affect their work, in their professional register. A CPA needs the tax consequence, not the family narrative
- **What we need from them** — specific, with the reason
- **What we're sending** — the documents or figures the advisor has to attach, listed so nothing is forgotten
- **Sequence** — what has to happen before their piece, and what waits on it
- **Timing** — driven by real deadlines where the data implies one: a tax year end, an open enrolment date, a closing date

Written as text the advisor can paste into their own email, in a professional-to-professional register. Rules: **no client account numbers or balances unless the professional demonstrably already has them**, no health details unless the handoff is genuinely medical or insurance-related and the client has consented on the record, first names for family members other than the client, and no advice on the other professional's own discipline — flag the issue, don't do their job.

**Nothing is ever sent, and there is no send button.** The advisor sends from their own client, from their own address, having read it.

### `year_in_review [household]` — the client letter

Requires a summary; build one silently first if there isn't one in the conversation.

**Ask the tone before writing, as one widget.** The same facts read completely differently depending on how a firm talks to its clients, and this is not a preference the skill may assume:

> **How should this read?** — **Warm** (default) / Professional / Friendly / Brief and factual

- **Warm** — personal without being casual. Uses their first names, acknowledges the year they've had, and reads as though a person who knows them wrote it. The right default for most advisory relationships.
- **Professional** — measured and precise, closer to a formal client communication. For firms whose house voice is reserved, or clients who prefer it.
- **Friendly** — conversational and light, contractions and short sentences, the register of someone who genuinely enjoys these people.
- **Brief and factual** — the shortest defensible letter: what happened, where things stand, what's next. For clients who want the summary and not the sentiment.

Tone changes the register only. **It never changes which facts appear, what gets redacted, or what is claimed** — a friendly letter is not a looser letter, and a brief one still carries the same disclaimer block. Ask it every run; don't remember it, because it can differ by client within the same firm.

1. **Read `references/letter-guidelines.md` before writing a word.** Structure, redaction, prohibited content. A client will read this and may keep it.
2. Draft four sections: **What we did · Where your goals stand · What changed · What's next.**
3. Redact by default: no internal metrics, no third-party names, no health details, no firmwide or other-client comparison, no figures unless the advisor asks.
4. **No branding on the letter.** Per `references/letter-guidelines.md`: no logo, no colour, no letterhead — firms have their own, and a plain draft drops into it. (The review deck is the branded surface; the client-facing branding rule in `references/run-setup.md` applies there, not here.)
5. Append the placeholder disclaimer block for the advisor to replace.
6. Produce the document; PDF on request.
7. **Never send.** Page one opens with *Draft for advisor and compliance review — not sent.*

### `review_deck [household]` — the client review presentation

Requires a summary; build one silently first if there isn't one in the conversation.

This is the artifact that goes on screen in the review meeting, so it is client-facing and governed by the same redaction rules as the letter: `references/letter-guidelines.md` for what may and may not appear, `references/deck-guidelines.md` for the slide structure and the template handling. **Read both before building.**

**Step 1 — The template.** Check `~/.zocks-brand/templates/` for a saved review template.

- **A template exists** → use it. Say which one in a single line ("using your review template"), and offer replacement only as an aside the advisor can ignore. Never re-ask.
- **No template exists** → ask for one, once:

  > **Do you have a presentation template I should build this on?** — **Upload a template** / Use a clean layout this time
  >
  > *A .pptx or .potx from your firm. I'll keep it and use it for every review presentation from now on.*

  - **Upload** → save the file into `~/.zocks-brand/templates/`, record its path and the date in `brand.json`, confirm in one line, and build on it. **This is the only upload the advisor should ever be asked for**, so it must be worth it — which means actually using the template's own layouts, fonts and colours rather than approximating them.
  - **Use a clean layout** → build clean and apply the brand kit's colours if one exists; where no kit exists, offer one per the client-facing branding rule in `references/run-setup.md` before falling back to plain. **Do not** write a "declined" marker. An advisor without the file to hand today will have it next quarter, so ask again next time — but only once per run, and never twice in a conversation.

**In an unattended run, never ask.** Use the saved template if there is one; otherwise build clean and say so.

**Step 2 — Build it.** Follow the `pptx` skill for the mechanics of writing onto an existing template — reading its layouts, respecting its masters, and not inventing decorative elements the firm didn't design. Slide order and content rules are in `references/deck-guidelines.md`.

**Step 3 — Deliver as a draft.** Same standing as the letter: **the deck is drafted, never sent and never presented on the advisor's behalf.** It carries a first slide marked *Draft — for advisor and compliance review* until a human removes it.

**What the deck never contains**, beyond everything the letter excludes: the commitment record in any form, the coordination gaps, the coverage-versus-firm comparison, third-party professional names, and anything marked sensitive. A client sitting in front of a slide listing what their advisor promised and never confirmed is the single worst outcome this skill can produce, and the deck is the output where it would happen.

---

## What the summary reports — no scores

Definitions and labelling rules in `references/metrics.md` — read before writing. **Nothing in this pack is a rate, a percentage, or an index.** Earlier versions computed a Commitment Kept Rate, a Goal Drift figure, a Coordination Debt ratio and a Coverage score. All four are gone.

The Commitment Kept Rate is worth naming as the reason. It required knowing which commitments were completed, and **Zocks does not track task completion** — no field says an action item was done. A "kept rate" would therefore have been a number about the advisor's reliability derived from data that cannot support it, printed in a pack that sometimes becomes a client letter. That is the worst output this library could produce.

- **Commitments** — every commitment the advisor made in the window, with the meeting and date. Where a later meeting explicitly discusses one as done, that mention is shown as the evidence. Everything else is undetermined, said plainly: *"status not recorded — confirm before the meeting."* The advisor supplies the status; the pack's job is to make sure nothing was forgotten, not to mark their homework.
- **Goals, then versus now** — each goal's earliest stated form beside its latest: target amount, target date, or stated confidence, with the meeting where it moved. Named in words, never as a drift figure. A goal that stopped being mentioned is itself a finding.
- **Coordination** — the items whose execution sits with someone outside the firm (CPA, attorney, insurance broker, banker, physician), and which of them have evidence of a handoff: a task assigned outside the firm, a recorded referral, or a meeting reference to having contacted them. Reported as a count and a list — *"four of the six items from this year need someone outside the firm, and three have no handoff on record"* — which is actionable in a way "50% coordination debt" is not. Decided in the room and never executed anywhere is the quiet failure mode of good advice.
- **Coverage against the book** — planning domains covered with this household beside the domains the advisor's other reviews routinely cover, as a two-column comparison ending in a list of what never came up. No coverage score; the gap list is the deliverable, and it reads as agenda items rather than failures.

---

## Output — the advisor review summary

Render per `references/output-modes.md`. Every row links to the meeting behind it: an advisor challenged on any line should be one click from the conversation it came from.

| Section | Content |
|---|---|
| Header | Household · window · meetings in window, analyzed, deep-dived · date built |
| Where their goals stand | Each goal as a then-versus-now pair with the delta named in words, linked to the meeting where it moved. Reading down this list should tell an advisor what kind of year it was before they read a sentence |
| What we did | Decisions taken, dated, each linked to its meeting. Decisions, not activities — "moved to the three-fund allocation", not "discussed allocation" |
| Commitments | Every commitment the advisor made in the window, with the date it was said. Where a later meeting shows one explicitly discussed as done, that evidence is shown; otherwise the status is undetermined and says so. Never a rate, never labelled overdue. Anything said more than 90 days ago is called out for confirmation |
| What changed | Life events on a dated line, and the shift in their outlook across the window |
| **Who else needs to know** | One card per outside professional: what changed that affects them, what they need to do, what it depends on, what it unblocks, timing. Plus any decision that needs a professional the household doesn't have. The coordination items stated as a count with the list |
| Coverage vs the firm | Domains covered here against firm rates; gaps named as agenda items, not failures |
| What's next | Open commitments, live concerns, flagged domains, and pending handoffs, assembled as a proposed agenda |
| Actions | `sendPrompt` buttons: **Draft the year-in-review letter**, **Build the client review presentation**, **Draft the handoff briefs** |

**Interactive extras:** the handoff map is where interactivity earns its place — clicking a decision highlights every downstream item and the professional who owns it, so an advisor can see at a glance what the review has just set in motion. Commitments filter to open-only. Goals filter to moved-only.

---

## Degradation

- **Fewer than two analyzed meetings in the window** → a diff needs two endpoints. Say what the window contains, offer to widen it, and never produce a "year in review" from one meeting.
- **No financial profile or planning-domain payloads** → goal drift is uncomputable. Build from decisions, commitments, and life events, and say plainly that goals couldn't be tracked. Never infer a goal figure from a concern.
- **A goal appears only at the end of the window** → it's new, not drifted. Show it under what changed, with its origin date.
- **A commitment with no visible close** → open, with its age. Never assume closure because it stopped being mentioned. Silence is not completion, and this is the number the advisor most needs to be honest about.
- **No outside professionals on record** → the handoff section says so plainly and lists the items that need one. Never invent a CPA, and never address a brief to "your accountant" as if a person were known.
- **A meeting has no AI results** → drop it from the deep dive, pull the next candidate, report the counts in the header.
- **Empty insights for the window** → drop the coverage comparison with a note. Never invent a firm rate.
- **Contact search zero or several matches** → say so, or ask.
- **Pagination** → exhaust every page in step 2 before selecting meetings.

Never fill a gap with plausible-sounding content. A fabricated figure in a review pack becomes a fabricated figure in a client letter.

---

## Compliance

- Scope is the invoking advisor's own book. The only firmwide calls are aggregate insights, which return no other advisor's client content.
- **The client letter is the highest-risk output in this library** — client-facing, in the firm's voice, about money. `references/letter-guidelines.md` governs it: no performance figures, no forward-looking assurance, no comparison to other clients or the firm, no third-party names, no health details, no advice not already on the record from a real meeting. Read it every time.
- The letter, the presentation and the handoff briefs are **drafted, never sent**. The letter and the deck each carry a review header until a human removes it.
- **The presentation is the highest-exposure client-facing surface here** — it is displayed, discussed live, and often left behind. It carries the letter's redaction rules plus its own exclusions in `references/deck-guidelines.md`: no commitment record, no coordination gaps, no firm comparison, no third-party names, nothing flagged sensitive.
- **The saved presentation template and the shared brand kit are the only files this skill writes** — the firm's own design assets, never client data. The template lives beside the brand kit at `~/.zocks-brand/templates/` and is reused until the advisor replaces it.
- **Handoff briefs leave the firm.** They are the only output here addressed to a third party, so they carry the tightest data rules: minimum necessary, no balances or account numbers unless the recipient already holds them, no health details without recorded consent, and never a copy of the internal pack forwarded wholesale.
- The firm disclaimer is never cached between runs. Compliance language changes, and quietly reusing last year's block is a failure mode worth designing out.
- **Nothing is stored.** The only artifacts that persist are files the advisor exports, which land in Downloads folders — say so once when producing one.
- The commitment list and the handoff chain describe the firm's own follow-through. Neither ever appears in a client letter, however good the number is.

---

## Scheduling — one-off, ahead of a booked review

A review pack has an obvious moment: a few days before the review itself. That is worth scheduling, and it is the only scheduling this skill does — there is no recurring mode, because a recurring run would have to choose a household on its own.

Offer it once, and only when the household actually has a review-type meeting on the calendar: *"Want this rebuilt on the 11th, so it's current for the 14th?"* Create it with **the household named explicitly in the prompt**, plus the window and the advisor summary as defaults. **Only the advisor summary is ever scheduled.** The letter and the presentation are client-facing, they go to compliance, and they need a human to ask for them — a deck appearing unbidden in a scheduled run is a compliance artifact nobody reviewed.

Read `references/scheduling.md` before setting one up.

---

## References

- `references/run-setup.md` — brand kit, the household question, opening decisions. Read first.
- `references/data-budget.md` — meeting selection, the no-transcript rule, stop conditions. Read first.
- `references/metrics.md` — the commitment record, goals then-versus-now, the coordination list, the coverage comparison, and why none of them is a rate. Read before writing.
- `references/ai-result-shapes.md` — payload classification, household assembly, endpoint quirks. Read before parsing.
- `references/extraction.md` — goal keys, commitment matching, the endpoint discipline for the diff. Read before extracting.
- `references/letter-guidelines.md` — structure, the four tones, redaction, prohibited content. **Read before drafting any client-facing text.**
- `references/deck-guidelines.md` — slide order, template handling and storage, what the deck excludes. **Read before building a presentation.**
- `references/output-modes.md` — interactive and document modes, Zocks link patterns, disclosure lines. Read before rendering.
- `references/scheduling.md` — one-off scheduled runs only. Read before setting one up.