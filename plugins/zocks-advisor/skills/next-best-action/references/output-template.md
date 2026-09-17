# Output template — the analysis

Read with `references/output-modes.md` before rendering. This covers what goes on the page, in what order, and how it should read. It says nothing about palette or typeface — those are yours per run, inside the firm's brand kit.

---

## Voice — write for the advisor, not the engineer

Every line should read like the sharpest colleague in the firm explaining what they'd do and why. Hard rules:

- **Never mention mechanics.** No "AI results", "payloads", "rules fired", "coverage map", "score", "domain 7", "R32". The advisor sees a recommendation and its reasoning, never the machinery that produced it. Internally it's rule R32; on the page it's *"if Peter dies first, Paula's income drops and her bracket compresses — nobody has modelled that."*
- **No naked scores.** The band, never the number.
- **Lead with the insight, never the vehicle.** "Their will predates their son" beats "specialist intro recommended". The taxonomy is how it gets delivered, not what it is.
- **Firm data is labelled firm data.** "Across the firm", never "you" or "your book", for anything from insights.
- **Internal document, advisor decides.** The header carries one line: *Internal decision support for the advisor's review — not tax or investment advice, not for client distribution.* The advisor, not the skill, decides what reaches a client.
- **Name mechanisms, not statutes.** "The years between stopping work and required distributions are the cheapest tax years they'll ever have" — never a limit, a threshold, or a bracket boundary stated as current law. Always instruct verification against current-year figures.
- **Every claim carries its evidence.** A fact with no meeting behind it doesn't belong on the page.

Before and after:

| ✗ | ✓ |
|---|---|
| "R19 fired: taxable account tax drag detected, this month." | "Their Fidelity and Vanguard accounts threw about $4,000 in dividends last year, taxed at their marginal rate, on money they're reinvesting anyway." |
| "Coverage map killed 3 opportunities." | "Estate, education funding and the retirement-account gap have all been raised already — each has a task or a booked review against it, so they're worth confirming rather than re-raising." |
| "Insights indicate elevated client_driven_pct for Investment Products." | "Investment products are the one area clients across the firm are raising themselves this month." |
| "Recommend specialist intro." | "This needs an estate attorney, and there isn't one on file for them." |

---

## Section order

**1. Header** — household display name, each member linked to their contact record in Zocks, life stage per member, years as a client, meetings analyzed against meetings on record, date built. Firm logo and name from the brand kit.

**2. The recommendation** — the single most prominent thing on the page. The insight in one sentence, in plain language, plus its band and its delivery form. If the advisor reads nothing else, this line has to be enough to act on.

**3. Why this, why now** — the reasoning chain, in this order:

- **The facts** — each one with the meeting it came from and its date, linked. Where a fact is stale, its age shows.
- **The mechanism** — what actually happens, in a sentence or two. No jargon that an advisor would have to translate for a client.
- **The stakes** — the number, or an honest statement of what data would produce one.
- **The deadline** — where one exists, and what happens if it passes.

**4. What would change this** — the verification that could overturn the recommendation, phrased as the thing to go and check. Every analysis has one; an analysis that claims none is overconfident.

**5. Who has to act** — the dependency chain: what the advisor does first, who outside the firm acts, what they need, what it unblocks, what's blocked until they do. Include any professional the household **doesn't have on record**. Omit the section entirely when the action is wholly internal.

**6. Considered and rejected** — the three runner-ups, each with its band and the one reason it lost. Expandable to full reasoning. This section is half the trust in the output: an advisor believes a recommendation more when they can see what it beat.

**7. Already in motion** — the territory that died to coverage, compactly, each with what killed it and when. This is the first thing a sceptical advisor checks, because it's the proof the analysis looked rather than missed. Where something was deferred twice, say so — that's a finding, not a dismissal.

**8. Firm read** — one line, labelled firmwide. Where insights were thin or empty, say that instead.

**9. Actions** — the Zocks link on every fact and every piece of evidence. `sendPrompt` buttons for **Why this?** and **Show me the next one**. Nothing else — no send, no export, no push-to-CRM.

---

## Interactive behaviour

Three things have to work, and they're all audit affordances rather than decoration:

- **The fact pattern expands by domain**, so an advisor can see exactly what the analysis knew and what it didn't. Null fields show as unknown rather than being hidden — the gaps are frequently the most actionable thing on the page.
- **Rejected candidates expand** to their full reasoning chain.
- **Already-in-motion items expand** to what killed them and when.

An advisor who can't audit a recommendation won't act on it, and this is the skill where that matters most: it's making a claim about money with a deadline attached.

---

## Document mode

Same sections, same order. Expansions become indented subsections; Zocks links become live hyperlinks on meeting dates and member names. The recommendation still leads, and the reasoning chain still runs facts → mechanism → stakes → deadline.

One deliverable per run, saved to the outputs folder and presented.

---

## The two failure modes to check before rendering

**Did it recommend a task?** If the recommendation restates something already on the advisor's list, the coverage subtraction failed. Go back to step 5.

**Could a competent advisor have derived this from the meeting notes in five minutes?** If yes, it isn't the next best action — it's a summary. The output has to earn its place by naming the thing nobody wrote down.
