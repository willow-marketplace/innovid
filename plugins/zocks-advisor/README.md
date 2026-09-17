# Zocks Advisor Intelligence

**Seven Claude skills that turn a financial advisor's own meeting history into daily working intelligence — built exclusively on the [Zocks](https://zocks.io) MCP connector.**

Zocks captures and structures every client conversation an advisory firm has: extracted concerns and sentiment, who raised which topics, financial profiles, life events, family relationships, commitments and tasks, and firmwide conversation analytics. This plugin gives Claude a set of advisor workflows defined entirely over that structured layer — so an advisor can ask, in their own words, the questions they actually ask every day, and get an evidence-linked answer drawn from their whole book.

## The skills

| Skill | The question it answers | What the advisor gets |
|---|---|---|
| **`client-behavioral-profile`** | *"What is this client like — and is the relationship cooling or warming?"* | A fast temperature read before a call, or a full behavioral profile before a meeting that matters: motivations, fears, decision pace, stated-versus-revealed risk tolerance, and a concrete stance for the next conversation. |
| **`client-attrition-risk`** | *"Who am I quietly losing, and who do I call this week?"* | A ranked re-engagement list of households silent past their own rhythm, weighted by how the relationship was already behaving — with a suggested opener for each, and a draft re-engagement email on request. |
| **`held-away-radar`** | *"What outside money came up in my meetings recently?"* | A household-grouped pipeline of held-away assets — old 401(k)s, outside accounts, inheritances — labeled Act now / Worth raising / Keep an eye on, with money already in motion filtered out. |
| **`client-journey`** | *"Show me everything we know about this household."* | A client-presentable dossier: a five-stage lifecycle timeline, a relationship tree that flags beneficiaries never met, a dated timeline of life events, and every fact labeled with its corroboration and age. |
| **`household-review-pack`** | *"Prep the annual review — and everything that follows it."* | A 12–24 month synthesis: goals then-versus-now, decisions, commitments honestly marked — plus, on request, a client letter, a review deck on the firm's own template, and briefs for each outside professional. |
| **`next-best-action`** | *"What's the single highest-value move for this household?"* | One evidence-linked recommendation from a 33-rule planning playbook, with everything the advisor already raised subtracted, the runner-ups shown, and the outside actors named. |
| **`tax-opportunity-scan`** | *"What am I missing on this household's taxes?"* | The household's complete tax picture across two horizons — current-year items with deadlines and multi-year strategies — plus the tax questions nobody has asked, and a facts-and-questions handoff brief for the client's CPA. |


## Design principles

**Zocks-exclusive, by design.** If the Zocks MCP tools are not available in the session, the skill refuses to run and says so. Uploaded transcripts, other meeting or notetaker connectors, CRM and calendar sources are explicitly rejected as substitutes — these analyses are defined over Zocks' structured meeting intelligence and would not be trustworthy built on anything else.

**Every claim is auditable.** Facts carry the meeting they came from and its date, deep-linked into the Zocks console. Reports say what they read and what they didn't reach.

**No invented numbers.** The skills rank and label with deterministic ordering rules and plain-language labels — never fabricated scores. A fact Zocks did not return is never invented; a gap is reported as a gap.

**Nothing is stored.** Every run is rebuilt live from Zocks, so results are never stale. The single exception is a firm brand kit (logo, colours, and — for the review pack — an uploaded presentation template) kept under `~/.zocks-brand/`; client meeting data is never written to disk.

**The advisor decides.** Outputs are internal decision support for a licensed professional. Client-facing drafts are clearly marked, routed through compliance review, and never sent by the skills. Figures and eligibility are flagged for verification, never asserted as current law.

## Requirements

- A Claude surface that supports plugins (Claude Code, Cowork, or claude.ai with plugins enabled)
- An active **Zocks** account with the Zocks MCP connector — the skills are inoperative without it, by design
- For Word/PDF/PowerPoint outputs: the platform document skills (present by default in Claude's products; outputs degrade to Markdown when absent).

## Installing

Once the plugin is published in the Claude plugin directory, install it from there — it bundles the Zocks connector, so Claude will prompt you to connect and sign in to Zocks the first time a skill needs it.

To try it before then, clone this repo and load it as a local plugin in Claude Code (see the [Claude Code plugin docs](https://docs.claude.com/en/docs/claude-code/plugins) for loading a plugin from a local path). Either way, the first run walks you through connecting Zocks if it isn't already.

## Support

- Zocks — https://zocks.io
- Issues and questions — https://github.com/zocks-communications/zocks-agent-plugins/issues

## License

Source-available under a modified Apache License 2.0 (commercial use requires written consent from Zocks). See [LICENSE.md](LICENSE.md).
