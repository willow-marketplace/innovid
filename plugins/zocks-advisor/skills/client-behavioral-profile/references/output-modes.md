# Output modes

Every skill in this library offers the same two shapes, chosen at the start of the run: an **interactive report** or a **document**. Both carry the same content and the same computed metrics. Neither is ever a wall of text in the chat window — chat prose around the artifact is at most a line or two.

---

## There are no house style rules

Deliberately. There is no library palette, no mandated typeface, no signature-element rule, no restraint clause. An earlier version of this library specified all of that and every report came out looking the same and looking dead.

Make it good-looking the way you would if nobody had handed you a spec. Colour, type, and layout are yours to choose per report, in service of the thing being read. The only constraints below are functional ones — they're about the report working, not about how it looks.

---

## Interactive mode (default)

An inline visual widget where the client supports one, otherwise a standalone HTML artifact with identical content.

**Interactive means controls that do something.** Every control has to change what's on screen or open something real:

- tabs or section switching between parts of the report
- expand-to-reveal — a row that opens to show the meeting evidence behind it
- filter and sort — by household, by urgency, by date, by category
- search across the households or facts on screen
- hover or tap detail on a chart point, a timeline event, a family-tree node
- **deep links into Zocks** (patterns below)
- a button that asks the next question for the advisor — `sendPrompt("brief Laura Harley")` in a widget genuinely sends that message, so it counts as an action and belongs on the card. In a standalone artifact where `sendPrompt` isn't available, print the phrase instead of rendering a button that does nothing

**Never ship a control that doesn't work.** No button that only looks like an action. Specifically: no "Copy this text", no "Email the client", no "Push to CRM", no "Schedule meeting", no "Add to tasks", no "Export", no `alert()` stubs, nothing wired to a handler that does nothing. If the text is on screen the advisor can select it; a button that pretends to be an integration is worse than the absence of one, because the advisor taps it once and stops trusting the whole report.

The corollary: when a real capability arrives in the MCP — scheduling, task write-back — the button gets added then, not in anticipation of it.

**Functional floor.** Readable on a phone: single column below 640px, tap targets at least 44px, no horizontal scroll. Visible keyboard focus. Text legible against whatever it sits on. No `localStorage` or `sessionStorage` — hold session state in JS variables. Where colour carries meaning, back it with a label or a shape as well, because roughly one advisor in twelve won't separate two hues reliably.

---

## Zocks deep links

Wherever the report names a meeting or a person that exists in Zocks, link it. The ids come free in the listing responses — no extra calls.

| Target | URL |
|---|---|
| Past meeting | `https://console.zocks.io/dashboard/workspace/past/room/{room_id}/session/{session_id}/` |
| Upcoming meeting | `https://console.zocks.io/dashboard/workspace/upcoming/room/{room_id}/session/{session_id}/` |
| Contact record | `https://console.zocks.io/dashboard/library/manage-contacts/contacts/{contact_id}` |

Label them for what they are — "Open the March review in Zocks", "Prep this meeting in Zocks" — not "click here". These are the primary and often the only actions on a report, which is the point: the report ends where the advisor's real work starts.

Links go in documents too, as live hyperlinks on the same labels.

---

## Document mode

Word, PDF, or Markdown, as chosen. Read the `docx` or `pdf` skill before writing anything in those formats.

Same content, same section order, same metrics as the interactive report. What was expand-to-reveal becomes a nested paragraph or a footnote; what was a filter becomes a sorted table. Nothing gets dropped for being un-clickable except the interactive affordances themselves.

Save to the outputs folder and present the file. One deliverable per run.

---

## Every report says what it's made of

Two lines that appear on every output, in the header and the footer respectively — they are what makes the report trustworthy rather than merely confident.

**Header — what this is.** The window covered, the scoping (this advisor's own book, unless the advisor explicitly widened it), and the date the report was made. On a scheduled run, also what triggered it.

**Footer — what it didn't reach.** Any depth cut, with what's below it. Households with no analyzed meetings. Anything the advisor asked for that the data couldn't answer. One or two lines, plainly worded: *"Deep-read the top 10 of 34 households past the threshold; the rest are listed below with their last contact date."*

Both matter more than they look. An advisor who can see the shape of what they got will act on it. An advisor who finds out later that it was partial stops using the skill.

---

## Internal or client-facing — decide before writing, not after

Two skills can produce something an advisor may hand to a client. Those outputs are a different document, not the same document restyled:

- **Internal** carries the behavioural read, the risk ranking, the competitive mentions, the health note, the commitment the advisor didn't keep.
- **Client-facing** carries none of that. It is written to be read by the family it's about, it goes to compliance before it goes to them, and it says so on the artifact.

Never let client-facing be the default, and never let a single artifact try to be both.

---

## Two rules about the content itself

**Never fabricate to fill a layout.** An empty section says what's missing and why — "no held-away assets came up in these six meetings" — and the report is shorter. A plausible-looking invented figure in an advisory report is the most expensive thing this library can produce. Omit a row entirely rather than printing "none identified": an empty row reads as reassurance, and it usually isn't one.

**Minimum viable client language.** Quote a client's exact words only where the phrasing itself is the point, and only the phrase — never paragraphs of transcript, never a transcript dump into a stored or exported file. Everything else is paraphrase.
