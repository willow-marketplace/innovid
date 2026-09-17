# Data budget — how much Zocks to pull, and when to stop

Read this before the first tool call of any run, alongside `run-setup.md`.

Every skill in this library runs inside a conversation the advisor is paying for. A skill that pulls a hundred meetings to produce eight lines has spent the advisor's whole session on one report, and the next thing they ask will fail. A skill that pulls two meetings and calls it a book scan has lied to them. This file is the line between those.

There is no fixed call ceiling. The rule is **every call has to be able to change the answer** — if you can already say what a call will return, or the report reads the same with or without it, don't make it.

---

## The hierarchy — cheapest sufficient source wins

1. **`insights_query_insights`** — one call, firmwide or per-advisor analytics. The cheapest real answer in the system. Per the tool's own contract: **if it answers the question, do not call the meetings tools to supplement or validate it.**
2. **Meeting listings** (`meetings_list_past_meetings` / `..._upcoming_meetings`) — cheap, and they carry the `room_id` / `session_id` / participant ids everything else needs. Cheap enough to paginate to exhaustion when the skill is book-wide.
3. **`ai_results_list_results`** — the expensive one, and the one that carries the value. Costed per meeting. This is where a run gets away from you.
4. **`ai_results_get_meeting_summary`** — only when the narrative adds something the structured payloads can't. Usually it doesn't.
5. **`contacts_get_contact_details`** — cheap, but per contact. Fetch for households that survived the cut, not for everyone the search returned.
6. **`meetings_get_transcript`** — never. See below.

---

## Ask the two size params to do the work

`ai_results_list_results` takes independent sizes for its two sections. Use them:

- **`meeting_summary_size=0`** on every call where you want structured payloads. The narrative summary is the single largest thing this endpoint returns and it carries nothing the structured payloads don't carry better and shorter. This one parameter is most of the saving available in this library.
- **`ai_result_size=0`** on the rare call where you genuinely want only the narrative summary.
- Never leave both at their defaults out of habit.

**The endpoint may ignore paging params and return everything** — 75k+ characters is normal on a long meeting. So the discipline is not "request less", it's **extract narrowly and discard immediately**. Pull the fields the skill scores, hold those, and let the rest go. Never carry a full payload forward "in case".

---

## Stage the run: wide and cheap, then narrow and deep

Every book-wide skill runs in two passes, and the split is what keeps it affordable.

**Pass 1 — wide, listings only.** Sweep meeting listings across the whole book, paginated to exhaustion. Dedupe contact records into households *here*, before any per-meeting call, because duplicate CRM records are the most common way a run doubles its own cost. Rank on what listings alone can tell you — recency, cadence, count, who's booked.

**Pass 2 — deep, on the shortlist.** Only now spend `ai_results_list_results`, and only on the households that survived pass 1, and only on the meetings that matter within them.

The depth cut is a judgement call the skill declares in its own body. Sensible starting points:

| Shape of skill | Deep-read |
|---|---|
| Book-wide ranking (attrition, held-away) | The top households by the pass-1 ranking, per the depth cut the skill's own body declares (attrition caps it at 10), 1–3 meetings each |
| Today's calendar | Every household on today's calendar — but 1–2 meetings each, not their history |
| Single household, fast read | The latest meeting in full, up to ~4 priors read thinly |
| Single household, deep synthesis | Every analyzed meeting in the window, which is the point of the skill and is worth it |

**Below the cut is visible, not deleted.** Households that didn't make the depth cut appear in a footer with what pass 1 knew about them. Never let a depth cut present itself as the whole book.

---

## Read thin on the second and third meeting

The latest meeting usually earns a full read. Priors usually don't. When a prior meeting exists only to establish direction — is sentiment moving, is this concern recurring, is this the third time this decision was dodged — pull it and read **only the two or three fields that carry direction**, then drop the rest. Reading five meetings in full to establish a trend that two fields would have shown is the most common waste in this library.

---

## Transcripts — never

`meetings_get_transcript` is not used by any skill in this library. Not as a last resort, not for one sentence of client phrasing, not to "be thorough".

Two reasons, and the first is enough. A transcript pull is by far the most expensive call available and it routinely costs more than every other call in a run combined — one pull can make an otherwise cheap skill unusable. And the whole premise of this library is that Zocks' structured layer has already read the transcript: if a fact matters, it is in the AI results, and if it is not in the AI results, quoting raw conversation back at the advisor is not the fix.

Where the client's own wording would genuinely have helped, do without it. Paraphrase from the structured payload, or say less. A recommendation that stands on structured fields alone is the correct output, not a degraded one.

---

## Never pay twice

- Candidate-picker calls are not throwaway. The upcoming and past meeting lists fetched to build the "who is this about?" widget are the same lists the analysis needs in step 1 — hold them.
- One `room_id`/`session_id` gets one `ai_results_list_results` call per run. If two commands in the same conversation need it, reuse what's loaded.
- Drill-down commands (`brief`, `detail`, `why`) run on data already in the conversation. Refetch only the specific thing that's genuinely missing.
- One `insights_query_insights` call per run covers the firm-context line for every household in the report. It is not per household.

---

## Filter before you spend

Drop these at the listings stage, before any AI-result call:

- meetings with no external participant (internal, tests, solo)
- meetings whose only non-advisor participants share the advisor's email domain
- duplicate contact records for the same human — normalise on name and email
- meetings outside the window the advisor chose

Every one of these is a per-meeting call saved for nothing lost.

---

## Stop conditions

Stop and render when any of these is true:

- the ranking has stopped moving — three consecutive deep reads that don't change the top of the list mean the tail won't either
- the remaining candidates are all below the lowest label the skill would act on
- the depth cut is reached

And when you stop early, **say what you didn't reach**, in one line, in the footer. "Deep-read the top 10 of 34 households past the threshold; the rest are listed below with their last contact date." An advisor who knows the shape of what they got can trust it. An advisor who finds out later cannot.

---

## What the advisor is told

Never narrate the fetching. No "let me pull the meetings", no running commentary on pagination. The report says what window it covers, how many meetings it read, and what it didn't reach — that's the whole disclosure, and it belongs in the artifact, not in chat.

If a run is genuinely going to be long — a full book sweep, a two-year single-household synthesis — say so in one line before starting, so the advisor can narrow it if they meant something quicker.

---

## Context budget — the user's window is not free

A skill that loads its whole library of reference files before its first tool call can burn a large share of the user's context before doing any work. That cost is invisible to the advisor and it is charged to them: it shortens the conversation they can have afterwards, and on a long session it is the difference between a skill that helps and a skill that has to be restarted.

Two rules:

1. **Load a reference at the step that needs it, not up front.** Only files genuinely needed before the first tool call — this one, and whatever defines the opening questions — are read first. Scoring specs are read before scoring. Output templates are read before rendering. A file marked "read before rendering" that gets read at step 1 is a bug, not diligence.
2. **Prefer routing over completeness.** Where a reference is organised into sections that apply conditionally — rule domains by life stage, payload shapes by what actually came back — read the routing table, then the sections in play. Reading every section to be thorough is the expensive way to be no more accurate.

If a run genuinely needs deep reads across a long history, say so in one line before starting so the advisor can narrow the scope. Spending their context without telling them is the part that is not acceptable.

---

## Inference is the job. Invention is not.

This is the hardest rule in the library, and the one most worth getting right, because the failure it prevents is invisible.

**Every fact in an output must trace to something Zocks actually returned.** A name, a balance, a date, an employer, an account, a diagnosis, a relationship, a quote, a professional's existence, a next meeting — if it did not come back in a payload, it does not go in the output. Not as a rounded number, not as a reasonable assumption, not as a plausible detail that makes the sentence read better.

**What is fully allowed, and is in fact the whole point:**

- **Analysing** what was returned — counting, comparing, ordering, spotting that the same concern has come up four times.
- **Deriving conclusions** from it — that a relationship is cooling, that a conversion window is open, that a household's stated risk posture and their behaviour disagree.
- **Reasoning across meetings** — noticing that a retirement age moved, that a goal stopped being mentioned, that a decision has been dodged three times.
- **Recommending** an action the facts support, including recommending that someone go and *find out* something the record doesn't hold.

The line is simple: **a conclusion drawn from stated facts is analysis; a fact that was never stated is fabrication.** "They're cooling, because three of five topics ended without a next step" is a derived conclusion resting on real countable evidence, and it is exactly what these skills exist to produce. "Their 401(k) is roughly $200k" when no balance was ever mentioned is fabrication, even if the figure is plausible, even if it is probably about right.

**Practical consequences, all of them non-negotiable:**

- **A gap is reported as a gap.** "No balance on record for the Fidelity account" is a useful output. A guessed balance is not, and it is worse than silence because the advisor cannot tell the two apart.
- **Never round, midpoint, or tidy a number.** A range stays a range. An approximation stays an approximation, in the form it was said.
- **Never infer an unstated attribute** — an age from a career stage, an employer's plan features from the employer's size, a relationship from a shared surname, a professional from the fact that someone at that wealth level usually has one.
- **Never invent phrasing and attribute it to the client.** Quotes come from the structured payloads or they do not appear. Paraphrase is fine and must read as paraphrase.
- **Never fill a section to make it look complete.** An empty achievements panel, a one-line brief, a "nothing material outstanding" — these are correct outputs. Padding one is the most common way invention enters, because it happens while trying to be helpful.
- **Where a rule or recommendation needs a fact the record lacks, phrase the action as verify, check, or ask.** Recommending the investigation is correct. Asserting the conclusion is not.
- **Say which meeting each fact came from.** A fact that can be traced is a fact that can be checked, and the discipline of attaching a source is what stops invention at the point it would happen.

An invented detail is indistinguishable from a real one to the advisor reading it — and on any output that reaches a client, it is indistinguishable to them too. That asymmetry is why this rule outranks completeness, polish, and helpfulness every time.
