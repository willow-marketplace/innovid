# How assets are ranked — signals, order, and words

**There is no score in this skill.** The Move Likelihood Score is gone: weights like "recurrence is worth thirty and emotion fifteen" were invented, and the moment an advisor sees a 68 they want to know why it isn't a 71. The answer would be "because we chose those weights", which is not an answer.

What replaces it is an ordering rule and three plain labels. Deterministic, and explainable in the sentence that is also the rule.

---

## The signals, per asset

| Signal | What it is |
|---|---|
| **Recurrence** | How many meetings in the window it came up in. Repetition is intent |
| **Money already in motion** | A life event attached to the asset with timing: inheritance landed, job change orphaning a 401(k), business sale, retirement date. Money that is moving anyway moves; parked money doesn't |
| **Who raised it** | Client raised it *and* asked questions · raised it or asked · advisor-initiated passing mention |
| **Feeling** | A concern flag on the asset, or a bearish outlook on its class. Anxiety about an account is an invitation to fix it |
| **Size** | Approximate value as the client stated it. Never estimated, never inferred |
| **Recency** | When in the window it last came up |

---

## The order — first rule that separates two assets wins

1. **A life event with timing attached.** The window is open right now and will close.
2. **Client raised it themselves and asked questions about it.** They are inviting the conversation.
3. **Came up in more than one meeting in the window.** They keep returning to it.
4. **A concern flag or bearish outlook on it.** Discomfort is a reason to act.
5. **Larger stated value first.**
6. **Most recently mentioned first** — the final deterministic tie-break.

A household ranks by its strongest single asset. Print the reason, never a position number.

## The words

- **Act now** — a life event with timing, or the client raised it themselves and asked questions.
- **Worth raising** — came up more than once, or carries a concern flag.
- **Keep an eye on** — everything else that survived the disposition test.

One sentence naming the strongest factor in human terms accompanies every label: "they have brought it up twice this fortnight and just changed jobs."

## What is deliberately not ranked on

**No conversion rate, no agreement rate, no firmwide multiplier.** Over one advisor's fortnight the denominator is usually zero or one, so any rate computed from it is noise. The firmwide insights line is context, printed once in the header and labelled "across the firm" — it never touches the order.
