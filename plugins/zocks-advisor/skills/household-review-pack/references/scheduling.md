# Scheduling — which skills recur, and how they behave unattended

Read this only when a skill declares itself schedulable, or when the advisor asks for a recurring run.

---

## Which skills schedule, and which don't

**Recurring runs are for book-wide skills only.** A skill that sweeps the whole book has a sensible thing to say every week without being told anything. A skill scoped to one household does not — a scheduled run of it would either need a household hardcoded at setup or would have to guess one, and guessing a household is the thing this library never does.

| Skill | Recurring | Sensible cadence |
|---|---|---|
| `client-attrition-risk` | Yes | Weekly — Monday morning is the natural slot |
| `held-away-radar` | Yes | Weekly, end of week, so the pipeline is current for Monday |
| `client-behavioral-profile` | No — household-scoped | — |
| `client-journey` | No — household-scoped | — |
| `next-best-action` | **One-off only** | Scheduled ahead of a specific booked meeting, with the household named at setup |
| `tax-opportunity-scan` | **One-off only** | Scheduled ahead of a booked review or CPA handoff, with the household named at setup |
| `household-review-pack` | **One-off only** | Scheduled a few days before a specific booked review, with the household named at setup |

The one-off case is the exception worth having: an advisor with a review booked for the 14th can have the pack waiting on the 11th, and `next-best-action` supports the same ahead of a booked meeting. The household is named when the schedule is created, not inferred at run time, so the rule holds.

---

## Offering it — once, and only when it's earned

Never open a run by asking whether the advisor wants this on a schedule. Offer it **after** a successful run of a schedulable skill, as one line at the end, and only the first time:

> Want this every weekday at 7am?

If they decline, or ignore it, don't raise it again in the conversation. A skill that asks to be scheduled every morning is a skill that gets uninstalled.

Where the client renders tappable options, the offer is a tap. Where it doesn't, it's one sentence.

---

## Setting one up

Use the scheduled-task tooling available in the session — the tools that create a recurring task which starts a fresh session at fire time. Do not build a scheduler out of anything else, and do not use an in-process timer: those die with the session and the advisor's Monday brief silently never runs.

When creating one:

- **Write the prompt as a complete standalone instruction.** Each firing starts a fresh session with no memory of this conversation. "Run the daily book brief for my own book, interactive output" works; "run it again like we just did" does not.
- **Convert the advisor's local time correctly.** They said 7am; they meant 7am where they are.
- **Confirm what you created** in one line — the skill, the cadence, the time, and how to stop it.

---

## Unattended behaviour — the hard rules

A scheduled run has nobody to answer a question. Every skill that declares itself schedulable must be runnable end to end with zero input.

**Every decision point has a documented default.** The skill's body names them explicitly — window, threshold, scoping, output shape. In unattended mode those defaults apply silently. No opening widget, no clarifying question, no "which of these did you mean".

**Scoping defaults to the invoking advisor's own book** — `advisor_id="{user_id}"`. A scheduled run never widens to firmwide on its own; that requires explicit intent, and there is nobody present to give it.

**Ambiguity resolves conservatively, and says so.** A contact search returning four matches in an interactive run is a tap. In an unattended run it is a line in the output — "four contacts matched 'Chen'; not scored" — and the run continues with the rest. It never picks one.

**Output is the artifact, same as interactive.** Nothing is emailed, nothing is sent to a client, nothing is written to a system of record. The run produces the same report it would have produced interactively, minus the questions.

**Idempotent by construction.** These skills store nothing, so a second run on the same day recomputes from Zocks and produces a current report rather than appending to yesterday's. There is no state to duplicate and no snapshot to go stale. If a run is repeated, the newer one is simply the true one.

**Empty is a valid result, and it is reported.** A Monday attrition scan that finds nobody past the threshold produces a short healthy-book note. It does not manufacture a list to justify having run, and it does not fail silently.

---

## What a scheduled run says at the top

One line, before the content: what ran, over what window, scoped to whom, and when. An advisor opening a report they didn't trigger needs to know what they're looking at within a second — otherwise the first thing they do is ask, and the point of the schedule was to save them that.
