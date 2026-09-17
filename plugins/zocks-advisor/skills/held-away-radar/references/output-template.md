# Output template — the radar

Two modes, one structure, per `references/output-modes.md`. Never deliver the radar as plain chat text. This file covers what goes on the page and how it should read; it says nothing about palette or typeface — those are yours per run, within the firm's brand kit.

## Voice — write for the advisor, not the engineer

Every rendered line must read like a sharp colleague briefing an advisor between meetings. Hard rules:

- **Never mention mechanics**: no "AI results", "datapoints", "meeting templates", "detection", "payloads", "booster", "insights coverage". The advisor sees what happened in their book, never how the radar found it.
- **No naked scores.** There is no score in this skill and no number ever renders as one — no deltas, no position numbers. Advisors see the plain label (**Act now / Worth raising / Keep an eye on**) and one sentence of why. "42" means nothing to anyone; "they keep bringing it up and just changed jobs" means everything.
- **Firm data is labeled firm data.** Insights numbers describe the whole firm — say "across the firm", never "you"/"your book" for anything sourced from insights. Only the meeting-scan content (the cards) is personal.
- **Plain labels for numbers**: "Held-away money mentioned", "A move was suggested", "Client said yes" — not "asset_mentioned" or funnel jargon.
- **Say account types by name**: "high-yield savings", "old 401(k)" — never "product class" or "asset class" in body copy.
- **Every line earns its place** by telling the advisor something they can act on or feel. If a line only explains the system, delete it.

Before → after, from failed drafts:

| ✗ Engineer voice | ✓ Advisor voice |
|---|---|
| "Funnel counts meeting-template datapoints; detection below reads the full AI results." | *(delete — see rule below on the zero-funnel case)* |
| "No suggestions recorded in the last 30 days — nothing to boost from yet (booster +0)." | "No held-away move has been suggested in your meetings this window." |
| "Book context: HYS was the only product class clients raised themselves this month." | "High-yield savings was the only account type clients brought up on their own this month, across the firm." |
| "WARM · score 42" / "42 → 74 ▲" | "Worth raising — fresh mention and they engaged with it" / "Act now — raised again this week, and they just changed jobs" |

## The one visual worth getting right

**A column of labels, not a bar.** There is no score in this skill, so there is nothing to fill a bar in proportion to — a bar implies a magnitude the data does not have. What ranked down the page makes the pipeline readable is the **label plus its one-line reason**, consistently placed on every card: "Act now — they've raised it twice this fortnight and just changed jobs."

Every label and every state is **written out in words** — "Act now", "waiting on you 3 weeks", "in motion" — because colour alone fails colour-blind readers and dies in print.

Carry the firm's brand kit on the header if one is set (`references/run-setup.md`), and keep brand colour off the labels themselves: an advisor must be able to tell the firm's blue from an act-now flag.

## Structure (top to bottom)

**1. Header** — "Held-Away Radar", week range of the meeting scan, advisor first name.

**2. Top strip (the advisor's own window).** Sourced from the advisor-scoped insights call, never the firmwide one. Two cases — pick one, never both:
- *Funnel has data* → three big numbers, plain labels, no trend arrows: **"Held-away money mentioned" → "A move was suggested" → "Client said yes"**, subtitle "in your meetings, [window]". Read them as a handful of conversations, not a statistical funnel — no rates, no multipliers. Below it, at most one line:
  - *Gap line* (when mentions exceed suggestions): "3 clients mentioned outside money and never heard a suggestion." When suggestions = 0: "No held-away move has been suggested in your meetings this window."
- *Funnel is empty or all zeros while the cards below have content* → **drop the funnel entirely** and show this week's own summary as the big numbers instead: **[N] held-away assets · [N] households · [N] meetings this week**. No explanation of why the tracked stats differ — that's plumbing, and the advisor doesn't care.

**3. Firm context line** — one plain-English line from `investment_product_stats` / `topic_stats`, naming account types outright and labeled as firm-level (e.g. "Old 401(k)s came up in 11 meetings across the firm this month — clients almost never raise them first"). Omit if nothing notable.

**4. State line** — "2 new · 1 raised again · 1 nothing new since · 2 task created" (omit zero categories; count assets; the states are the five in the SKILL.md state table). A client-agreed asset gets one celebration line here, then retires from the ranking.

**5. "Nothing new since" strip (if any)** — one line each: "[Household] — [asset], raised early in the window, nothing since; no task or agreement on record." Worded as silence in the record, never as advisor inaction — the advisor may well have dealt with it off-platform.

**6. Ranked household cards** — one card per household, ranked by its strongest single asset per `references/ranking.md`; households with any act-now or worth-raising asset first, keep-an-eye-on-only households last as compact rows. Per card:

| Element | Content |
|---|---|
| Title row | Household name ("Peter & Paula Reynolds") + relationship tag ("spouses" / "partners" — never "husband and wife") + badge: NEW / RAISED AGAIN |
| Asset rows (one per opportunity) | Band label chip (**Act now** / **Worth raising** / **Keep an eye on** — never a number) · asset type + custodian + stated context + owner attribution ("Peter's 403(b) at Texas Tech"). Raised-again assets say "they brought it up again — repetition is intent" instead of any delta |
| Their words ("Act now" assets only) | The one stored client sentence, quoted, with a heavy ink left rule |
| Why | One plain sentence per act-now/worth-raising asset naming the strongest driver in human terms ("third meeting running, and they just changed jobs") — never a number, never the formula |
| Action evidence (if any) | "Task open since Jul 9: explore rollover options" — auto-detected, shows the stale clock is stopped |
| Prior-mention trail | Linked dates: `[Jul 9](…/past/room/{room_id}/session/{session_id}/) · [May 13](…)` — every date is a Zocks deep link |
| Next action | Visually dominant strip: the single recommended move for the top asset |
| Primary CTA | Filled dark-ink button **"Open meeting in Zocks →"** → `https://console.zocks.io/dashboard/workspace/past/room/{room_id}/session/{session_id}/` (most recent mention) |

Omit any row with nothing behind it — every line must be earned by data.

**7. "Keep an eye on" list** — households whose best asset is Keep an eye on: one line each — household name, asset summary, last mention (linked). No scores.

**8. Footer** — plain and short: "[N] meetings reviewed · [date]". If meetings are still processing: "Notes from [N] meeting(s) weren't ready yet — they'll show up next run." If something was excluded as business-in-motion, one human line: "Not included: [thing] — you're already on it." If scheduling was declined this run: "Say 'schedule the radar' anytime — default Friday 2 PM."

## Docx mapping

Same order. The funnel becomes a three-column table; household cards become bordered single-cell tables with a left rule whose weight tracks the label tier; asset rows sit in a compact inner table; the Zocks link becomes a bold hyperlink. Because every band is labelled in words as well, it still prints correctly in mono.
