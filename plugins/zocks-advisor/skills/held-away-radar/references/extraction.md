# Extraction rules

Read alongside `ai-result-shapes.md`, which carries the payload classification table, the household rules, and the endpoint quirks. This file is what is specific to this skill.

## Held-away taxonomy

**The test: does an asset already exist, held somewhere outside the advisor's management, that nobody has moved on?** Being *discussed* in a meeting is not enough — the radar hunts for parked money, not conversations about money.

### Counts as held-away

- Employer plans at a current or former employer: 401(k), 403(b), 457, pension — *especially* orphaned by a job change
- Outside brokerage accounts at named custodians (Fidelity, Vanguard, Schwab, Robinhood…)
- High-yield savings / CDs / significant cash at outside banks
- Inheritances — received, or concretely expected with timing
- Business ownership value and sale proceeds (pending or closed)
- Real estate beyond the primary residence, or equity freed by a transaction in motion
- A spouse's or partner's existing accounts not under management (their employer plan, their old brokerage)
- Equity compensation held at the employer's plan administrator (RSUs, ESPP, options)
- Old annuities or whole-life policies with cash value at another carrier

### The disposition test — apply to every candidate, asset by asset

An asset qualifies only when it sits outside the firm **with no agreed next step covering it**. Check the same meeting's structured record for disposition evidence about *that specific asset*:

- a task or action item referencing it (tasks payload: "rollover analysis", "incorporate the inheritance into the plan", "review the brokerage accounts")
- an agreed decision or next step that covers it — including statements/documents being collected on it for planning or management
- a transfer, rollover, or account opening already underway or agreed

Any of these → **not held-away**; it's business in motion, and the advisor already owns it. This bites hardest in first and onboarding meetings: clients enumerate their accounts there *because they want help with them*, so a new planning engagement whose document checklist names the brokerage, retirement, and bank statements has a next step against nearly everything mentioned. Scan those meetings normally — don't skip them — but expect the disposition test to exclude most of what they surface. What survives even there is real: an account mentioned in passing that no task, decision, or document request touches (the spouse's old 401(k) nobody wrote down a step for) is exactly the forgotten money this radar exists to catch.

**Ambiguity rule: when you cannot point to the specific evidence that an asset has no next step — or you're unsure whether an agreed step covers it — do not flag it.** Precision over recall, always: one wrongly flagged "opportunity" the advisor is already working costs more credibility than three missed ones. Excluded-but-seen assets get one short footer line (aggregated past two items) so the advisor knows the radar saw them.

### Also never held-away

- **New vehicles that don't exist yet**: a discussion about opening a SEP-IRA, solo 401(k), spousal IRA, or 529 is new-business advice, not a held-away asset. Nothing is held anywhere.
- **Assets already managed by the firm** — even when discussed at length.
- The primary residence itself (absent a transaction), income streams, and debts.
- Hypotheticals and generic education ("have you thought about what happens to old 401(k)s?") with no actual client asset behind them.

Borderline rule: an established client's outside accounts that keep appearing in regular meetings with nothing done about them **do** count — that's exactly the consolidation motion this radar exists for.

Each detected item gets an `asset_key` for state-keying: `{asset_type}:{custodian_or_context}` lowercased (e.g. `brokerage:fidelity`, `401k:former-employer`, `inheritance:parents-estate`). Match loosely across weeks — "the Fidelity account" and "brokerage at Fidelity" are the same key.
