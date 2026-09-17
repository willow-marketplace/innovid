# Run setup — branding, and the questions asked before any work

Read this before the first tool call of any run, alongside `data-budget.md`. Three things happen here: the firm brand kit (once, ever), the run's decision points (every run, as taps), and the household question (every household-scoped run, without exception).

---

## Nothing is stored except the brand kit

This library keeps no state. No config file for scoping, no saved profiles, no watchlists, no prior-run snapshots, no "where should I save this" question — ever. Every run reads Zocks fresh and computes from scratch, so a report is always true at the moment it was made rather than true whenever it was last refreshed.

The one exception is the brand kit below, which exists because an advisor should not re-upload their logo every morning. A skill may keep one further small preference file next to it at the same fixed path — but only where the skill documents it, only for things the advisor typed themselves, and never for report data, computed metrics, or run history. **If it could be recomputed from Zocks, it is not stored.**

Where a skill compares "now" against "before", it does that **inside the data it just pulled** — this week's meetings against the previous weeks, the first half of a window against the second — never against a saved file from a previous run.

---

## The brand kit — asked once, library-wide

Check `~/.zocks-brand/brand.json` at the start of every run.

**If the file exists**, load it and apply it. Ask nothing.

**If it does not exist**, ask exactly one question, as a tappable input, before producing anything:

> These reports can carry your firm's branding. Want to set that up? It takes a moment and applies to every Zocks skill from now on.
> — **Add my firm's colours** / **Add a logo too** / **Not now**

- **Not now** → write `{"branding":"declined"}` and never ask again. A declined kit is a valid kit; nagging an advisor every morning is worse than plain output.
- **Add my firm's colours** → ask for hex codes as free text ("Paste your brand colours — hex codes, or the name of the firm and I'll ask for them one at a time"). Accept one colour or several.
- **Add a logo too** → ask them to upload the logo or any image carrying the firm's palette (a brochure page, a website screenshot, a business card). Save the file into `~/.zocks-brand/`, then sample the palette from it: load with PIL, resize to ~100px, quantize to 5–8 colours, drop near-white and near-black, and take the two most-used remaining colours as primary and accent. Show the advisor what you extracted and let them correct it before writing the file.

Then write:

```json
{
  "firm_name": "Gilbert & Gilbert",
  "logo_path": "~/.zocks-brand/logo.png",
  "colors": { "primary": "#0B2C4A", "accent": "#016FD0" },
  "source": "image",
  "configured_at": "2026-07-31"
}
```

**Applying it.** Logo top-left of the report header, firm name beside it, brand colours on headers, rules, and accents. Never on the data itself — a client's sentiment must not be rendered in the firm's brand colour, or the reader can no longer tell branding from meaning. If a brand colour is too light or too dark to read text against, keep it as a background or a rule and pick a legible text colour instead; a logo-matched header that can't be read is worse than no branding.

**Where branding matters most.** `client-journey` and the `household-review-pack` letter are the two outputs an advisor may put in front of a client, so both should look like the firm's work. The internal-only skills still read the kit — a firm that set it up expects to see it everywhere — but nothing breaks when it's absent.

**Unbranded is a complete, finished output**, not a degraded one. Never leave a placeholder where a logo would go.

**In an unattended run**, skip this entirely. Apply the kit if it exists; if it doesn't, produce unbranded output and don't write a declined file — the advisor never saw the question, so they haven't declined anything.

---

## Branding a client-facing artifact — always offered, never assumed

Most outputs in this library are internal, and for those the kit above is enough: apply it if it exists, produce clean output if it doesn't, and never nag.

**Client-facing artifacts are different, and they get their own offer at the moment of production.** A journey report, a year-in-review letter, and a review presentation all carry the firm's name in front of the client. An advisor who declined branding months ago — on a morning brief, when it didn't matter — has not decided that the letter going to the Reynolds should be unbranded. Those are different decisions and this library must not conflate them.

So, whenever a **client-facing** artifact is about to be produced:

- **A kit exists** → apply it. Say so in one line — *"carrying your firm's branding"* — and move on. Never ask.
- **No kit, or a declined kit** → offer once, at that moment, in a single tappable line:

  > **This one's going in front of the client. Want it on your firm's branding?** — **Add colours and a logo** / **Add colours only** / **Send it plain**

  A previous decline never suppresses this offer, because it was made about a different kind of output. **Send it plain** applies to this artifact only and is not written back to the kit — never turn a one-off choice into a standing preference on the strength of a single tap.

- **In an unattended run** → apply a kit if one exists, produce clean output if not, and never ask. Unbranded is a finished output, not a degraded one.

**Where branding goes, and where it does not.** Logo and firm name in the header or on the title slide; brand colours on headers, rules, section dividers and accents. **Never on the data itself** — a client's sentiment, a goal status, or a temperature must never be rendered in the firm's brand colour, or the reader can no longer tell branding from meaning. If a brand colour is too light or too dark to carry text, keep it as a background or a rule and pick a legible text colour: a logo-matched header nobody can read is worse than no branding at all.

**Never leave a placeholder where a logo would go.** Unbranded means the design closes up around the absence, not that it displays a gap.

---

## Who this is about — asked first, and never guessed

**This is the hardest rule in the library.** Any skill scoped to one client or household establishes *which* household before it does anything else. Not inferred from context, not assumed from the most recent meeting, not resolved by picking the best of several search matches. Named, or asked.

A report confidently built on the wrong household is the worst output this library can produce. It is worse than no report, because it looks right.

The question is asked as **taps rather than free text**, which means the candidates have to be fetched *before* the question is asked — two cheap listing calls, and they buy the difference between tapping a name and typing one.

**Order of operations:**

1. Check the brand kit (silent when it exists).
2. **Fetch candidates.** `meetings_list_upcoming_meetings(advisor_id="{user_id}", from_date=now)` — whoever they're seeing next is the likeliest subject — then `meetings_list_past_meetings(advisor_id="{user_id}", size=20)` for the most recently seen. Drop meetings with no external participants.
3. **Assemble and dedupe into households** before offering anything: co-attendance plus shared surname, spouses or partners from the family payload, and normalise duplicate contact records for the same person. Offering "Paula" and "Paula Reynolds" as two separate options makes the skill look broken, and they are the same person under two contact ids more often than you'd expect.
4. **Offer the three strongest candidates plus an escape hatch**, ordered: anyone on today's or tomorrow's calendar first, then most recently seen.

> **Who should this be about?** — **Peter & Paula Reynolds** (today, 2pm) / Margaret Chen (seen 3 Jun) / Michael & Marie Williams (seen 26 May) / Someone else — I'll type the name

"Someone else" takes a name as free text and resolves it through `contacts_search_contacts` as normal.

**Skip the question only when the request already names someone unambiguously.** "Next best action for the Reynolds" has answered it; asking again is the exact friction this pattern exists to remove. Resolve the name and go straight to the remaining questions — or straight to work, if the request answered those too.

**Ambiguity is always a tap, never a choice you make:**

- **Zero matches** → say so and stop. Suggest a spelling check. Never profile a guess.
- **Several matches** → offer them as taps, each labelled with something that distinguishes them — last meeting date, email domain. Never pick the first.
- **A first name only, and the book has three** → ask. "Peter" is not an answer.
- **Weak household evidence** — different surnames, no relationship payload → keep them separate and say so. A wrong merge produces a confident report on a household that doesn't exist.

**Reuse the fetch.** The candidate call is not throwaway: the upcoming and past meeting lists it returns are the same ones the analysis needs in its first step, so hold them and don't call again.

When the book is genuinely empty — no analyzed meetings at all — say that instead of presenting an empty picker.

**The one deliberate exception** is `client-attrition-risk`, which is book-wide by design: the whole point is that the advisor doesn't yet know who to worry about, so asking them to name someone would invert the skill. Where its request *does* name a household, it drops straight to the single-household brief.

---

## The rest of the run's decisions

Everything else that changes the output gets asked too. An advisor should never discover after the fact that the skill quietly chose a two-year window, or produced an internal document when they wanted something client-ready.

**Ask together, in one widget, at most three:**

1. **Who** — per above. Always first when the skill is household-scoped.
2. **Window or threshold** — the timeframe, or the "how long is too long". Skip when the skill has one sensible window and the request implies it.
3. **Output** — **Interactive report** (default) or **Document**.

If they pick Document, ask the format as a second, separate widget: **Word** / **PDF** / **Markdown**. Ask this per run; don't remember it.

**A fourth question, asked separately and only where it applies:** whether the output is internal or client-facing. Any skill that can produce something an advisor might hand to a client — a journey report, a review letter — must establish which it is before writing a word, because the two are different documents, not the same document with different styling. Never let a client-facing artifact be the default.

**Rules that hold everywhere:**

- Two to four short option labels, mutually exclusive, the sensible one marked default.
- No jargon in an option label. If a first-time user wouldn't recognise a term, explain it in the line above the question, never inside the label.
- Free text only where options genuinely can't carry the answer — a hex code, a household name, a custom day count.
- **Never ask what can be inferred** from the request, the conversation, or data already pulled. The best question is the one that turned out to be unnecessary. This does not override the household rule — inference is for windows and formats, never for whose life you're about to summarise.
- Mid-run disambiguation is a tap too — which household you meant when a search returns four, whether to include a health note in a client-facing letter.
- **Follow-up commands are not questions.** What an advisor can do next lives on the artifact itself. Never close with a prose menu of commands to type.
- **In an unattended run, none of this is asked.** Documented defaults apply, silently. See `scheduling.md`.
