# The client review presentation

Read alongside `letter-guidelines.md` — every redaction rule there applies here too. This file covers what is specific to a deck: the template, the slide order, and the exclusions that only matter when something is on a screen in front of the person it is about.

**A deck is not a letter with bullet points.** A letter is read alone, at the client's pace, and can carry nuance in a sentence. A deck is read *while the advisor is talking over it*, by someone who is also thinking about their own money. Every slide has to survive being understood in four seconds without the narration, because that is how long the client looks at it before listening again.

---

## The template

**The firm's template is the point.** An advisor's review deck has to look like their firm's work, not like a generated document. That is why this skill asks for a template file at all — the one upload it will ever request — and why it keeps it.

**Storage.** Saved to `~/.zocks-brand/templates/`, beside the brand kit, with its path and the date recorded in `brand.json`:

```json
{
  "firm_name": "Gilbert & Gilbert",
  "logo_path": "~/.zocks-brand/logo.png",
  "colors": { "primary": "#0B2C4A", "accent": "#016FD0" },
  "review_template": {
    "path": "~/.zocks-brand/templates/review-deck.pptx",
    "saved_at": "2026-08-13"
  }
}
```

It holds the firm's design assets and **no client data** — a template is layouts, fonts, colours and a logo, which is why it is safe to keep when nothing else in this library is.

**Using it properly.** Read the template's own layouts and masters and build onto them — do not approximate the look with hand-set fonts and colours. If the advisor uploaded a file and the output doesn't visibly use it, the upload was a waste of their time and they will not do it again. Where the template has a title layout, a section layout and a content layout, use each for its purpose. Where it has a chart style, match it.

**Replacing it.** Never re-ask once a template is saved. Mention it in one line when building — *"using your review template"* — with replacement available as an aside the advisor can ignore. Firms rebrand every few years, not every quarter.

**No template saved, advisor declined this time.** Build clean: the brand kit's colours if one exists, generous whitespace, no invented logo, no placeholder where a logo would go. **Do not write a declined marker** — unlike the brand kit, ask again on the next presentation, because an advisor without the file to hand today usually has it next quarter. Once per run, never twice in a conversation.

---

## Slide order

Eight to twelve slides. A review deck that runs longer stops being a conversation and becomes a presentation, which is the opposite of what a review meeting is for.

1. **Title** — household name, the period covered, the firm's mark from the template. Nothing else.
2. **The year in a sentence** — one line the advisor can open with. Not a summary of the deck; the honest headline of the year.
3. **Where your goals stand** — the then-versus-now pairs, as few as three and never more than five. The single most important slide, and the one the client came for.
4. **What we did** — decisions taken, dated. Decisions, not activities: "moved to the three-fund allocation", never "discussed allocation".
5. **What changed in your life** — the life events on a dated line, in the client's own framing. Omit the slide entirely if the window holds nothing; a padded life slide reads as intrusive.
6. **What we're watching** — live concerns and the domains now in play, phrased as shared attention rather than as gaps in the advice.
7. **What's next** — the proposed agenda for the year, three to five items, each one an action rather than a topic.
8. **Questions / discussion** — a closing slide that hands the meeting back to them.

Optional, only where the data genuinely supports it: one slide on a single theme that dominated the year — a retirement date that moved, a business sale, a new dependent. Build it only if the year actually had one. **Never pad to reach a slide count.**

---

## What the deck must never contain

Everything `letter-guidelines.md` prohibits, plus four exclusions specific to this format:

- **The commitment record, in any form.** Not the list, not a count, not a softened version. A client watching a slide about what their advisor promised and never confirmed is the worst outcome this skill can produce, and the deck is the only output where it could happen live.
- **The coordination gaps.** "Three items have no handoff on record" is an internal working note. On screen it reads as an admission.
- **The coverage-versus-firm comparison.** Benchmarking a client against other clients, however anonymously, does not belong in front of them.
- **Third-party professional names**, and anything flagged sensitive at extraction.

Also, for the format rather than the audience: **no internal labels of any kind** — no corroboration flags, no "status not recorded", no confidence markers. If a fact is too uncertain to state plainly to the client, it does not go on a slide.

---

## Writing the slides

- **Six lines a slide, ten words a line.** If it needs more, it is two slides or it is the advisor's narration.
- **Numbers only where the client already knows them** — figures they stated themselves, targets they set. Never a computed or estimated figure, and never a performance number: this deck is about the plan and the relationship, not returns.
- **Their language, not the firm's.** "Retiring at 65" beats "retirement horizon extended". If the client called it "the Maine place", the slide says the Maine place.
- **No jargon that needs the advisor to translate it.** They are talking; the slide should not need a footnote.
- **Every claim traceable to a meeting.** The same rule as everything else here: analysing what was said is the job, and a fact nobody said does not go on a slide the client is looking at.

---

## Delivery

Slide one carries **Draft — for advisor and compliance review** until a human removes it. The deck is produced as a file the advisor opens, edits and presents themselves. **It is never sent, never shared, and never presented on the advisor's behalf.** Say once, when producing it, that the file is a draft for their review — then stop; an advisor who has been told twice starts skipping the line that matters.
