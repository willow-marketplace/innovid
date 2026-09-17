# The five stages and the 36-item checklist

Read before scoring. This is the frame that turns a pile of household facts into something a client recognises as their own journey: **Onboarding → Goal Setting → Action → Milestones → Ongoing**, always in that order.

---

## Marking an item

Every item gets one of three values:

| Value | Means | Requires |
|---|---|---|
| **Yes** | It happened | Evidence in the harvest: a task, a decision, a planning-domain position, a financial-profile field, a life event, or a client statement |
| **No** | It hasn't happened, and we know that | Evidence of absence — the topic came up and there was nothing in place, or a related item makes it clear |
| **blank** | Nobody has looked | The topic genuinely never came up |

**Blank is not a failure state and No is not a default.** "No" asserts something about the family's affairs; use it only when the data supports the assertion. Guessing No across a thin history produces a report that tells a fifteen-year client they've done nothing, which is both wrong and unrecoverable in a review.

**Every Yes and every No carries an evidence object:**

```js
{ quote: "Exact words spoken", speaker: "Sarah Harrison", why: "One or two plain sentences", session: {room_id, session_id}, date: "2026-04-06" }
```

The quote is optional and short — one phrase, not a passage. Where no clean quote exists, `why` alone is enough, drawn from the structured payloads. The `session` reference is what makes the row clickable through to Zocks, and it is never optional for a Yes or a No.

## Stage status — a count and a word, never a percentage

**Report a plain count, not a completion percentage.** "Four of the seven onboarding items have evidence, three are blank" is checkable at a glance and the advisor can act on the three. "71% complete" is the same information turned into a grade, invites the question of how the denominator was chosen, and reads badly if the report is shown to a client. Show the count and name the blanks.

The count excludes blanks from neither side — say how many have evidence, how many are explicitly No, and how many were never covered. A stage with two Yes, one No and four blanks is *"2 evidenced, 1 no, 4 never covered"*, which is honest in a way any single figure isn't.

Each stage also gets one status word, decided by evidence rather than arithmetic:

| Status | Rule |
|---|---|
| **complete** | The stage's substantive items have evidence, and the stage is behind them |
| **active** | The current focus — some items evidenced, some plainly in progress |
| **future** | Not meaningfully started |

Alongside the status and the count, always name **which items are blank** — the blank list is the useful half, because it is the set of things to ask about. Never let a status read as confident off a nearly empty stage: where most items are blank, say the stage is largely unevidenced rather than calling it future.

Typically one stage is active, everything before it complete, everything after future. The household's **current stage** is the active one. Use judgement: a family can be in Ongoing and back in Goal Setting after a divorce, and the timeline should show that honestly rather than forcing a single clean position.

---

## Onboarding

*Foundational discovery — getting to know the family and their financial life.*

1. Initial discovery meeting completed and documented
2. Risk tolerance and investment philosophy established
3. Financial documents gathered and reviewed (statements, tax returns, insurance)
4. Account access and consolidation plan in place
5. Family situation and dependents documented

## Goal Setting

*Defining what success looks like across time horizons.*

6. Short-term goals defined (1–3 years)
7. Mid-term goals defined (3–10 years)
8. Long-term goals defined (10+ years)
9. Retirement vision and target timeline established
10. Legacy and inheritance intentions documented
11. Education funding goals set (529 or equivalent)
12. Major-purchase goals defined (home, business, other)

## Action

*Implementation — putting the plan into motion.*

13. Investment policy statement created and signed
14. Portfolio allocation aligned to goals and risk tolerance
15. Emergency fund established (3–6 months)
16. Tax-advantaged accounts maximised (401(k), IRA, HSA)
17. Insurance coverage gaps closed (life, disability, LTC, umbrella)
18. Estate documents executed (will, POA, healthcare directive)
19. Beneficiaries reviewed across all accounts
20. Debt management or payoff plan in place
21. Cash flow and budgeting framework established
22. Charitable giving strategy implemented (DAF, direct, other)

## Milestones

*Major wins along the way.*

23. First major savings goal reached
24. Retirement on-track milestone hit
25. Debt-free milestone achieved, or major debt eliminated
26. College or education funding milestone reached
27. Net worth or portfolio target achieved
28. Tax optimisation milestone (Roth conversion, harvesting, other)
29. Major life event navigated (marriage, birth, sale, inheritance)
30. Major estate or legacy decision finalised

## Ongoing

*Stewardship — keeping the plan alive year after year.*

31. Annual review meeting completed
32. Tax projection updated for current year
33. Estate plan reviewed (every 3–5 years)
34. Insurance reviewed annually
35. Goals revisited and adjusted to life changes
36. Next-generation or family communication initiated

---

## Where the evidence comes from

| Items | Richest source |
|---|---|
| 2, 14 | Planning-domain payload (investment management), plus the stated-versus-revealed risk signals if the behavioural profile has been run |
| 3, 15, 16, 20, 21, 27 | Financial-profile payload — accounts, balances, custodians, income, debt |
| 5, 10, 19, 36 | Family extraction payload and the relationship tree |
| 6–9, 11, 12, 35 | Goals in the planning-domain payload; client-voiced statements; topic initiation |
| 13, 17, 18, 22, 30 | Tasks and decisions referencing documents, policies, or execution |
| 23–29 | Life events and explicit completion language ("fully funded", "paid off", "signed", "closed") |
| 31–34 | The meeting spine itself for annual reviews, plus planning-domain positions for the review cycles |

An item can be satisfied by a life event alone — a family that navigated a business sale satisfies item 29 whether or not anyone called it a milestone.

## Milestones and Ongoing read low on healthy households

Expect it, and don't over-correct. These two stages are cumulative: a family five years in has genuinely not hit every milestone, and a family two years in has no annual-review history to speak of. A thin Ongoing count on a young relationship is accurate, not an indictment, and the report should read that way. If the arithmetic makes a good relationship look neglected, the fix is naming the years together prominently next to the stages — never quietly inflating the count.
