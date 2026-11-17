---
name: finance-insights
description: Report, rank, compare, and trend finance performance across Stripe, QuickBooks, PayPal, Xero, Chargebee, and Braintree through Windsor.ai. Use when the user asks about revenue, charges, refunds, payouts, fees, subscriptions and MRR, invoices, and balances. Do not use to change anything, to connect an account, or to schedule exports - route those to the sibling skills.
---

# Finance insights

Read live finance data from Stripe, QuickBooks, PayPal, Xero, Chargebee, and Braintree through Windsor.ai and turn it
into a clear report, ranking, comparison, or trend. The main connector ids for this area are stripe, quickbooks, paypal_transaction (PayPal), xero, chargebee, braintree. Other sources in the same category work the same way; find their ids with get_connectors.

## Stay in scope
- Use this skill for metrics, rankings, comparisons, trends, and breakdowns of revenue, charges, refunds, payouts, fees, subscriptions and MRR, invoices, and balances across account, charge or transaction, subscription, and invoice.
- If the user asks to connect, reconnect, or add an account, use /windsor-connect instead.
- If the user asks for a recurring, scheduled, or automated export, use /windsor-export instead.

## Resolve scope and time
- Call get_connectors first to see which of the six sources have connected accounts. If several accounts match the request, ask which one.
- Read field ids from get_fields for the chosen connector, and get_options for date-filter columns and connector options. Never guess ids or field names.
- Use the date range the user gave. If none, use a sensible preset (last_30d, this_month, last_7d), and state which one you used. Append T to a preset to include today.

## Choose the smallest useful read
- Revenue and refunds -> fields [date, gross revenue, refunds] over the preset; report net as gross minus refunds.
- Provider comparison -> the same fields per connector, compared in the answer with the caveat that fee and timing conventions differ.
- Outstanding invoices -> QuickBooks or Xero invoice fields filtered to unpaid or overdue status.
- Subscription health -> Chargebee or Stripe fields for active subscriptions, MRR, churn.
- Keep field sets small and request only the accounts you need. Check for truncated or paginated results before claiming complete coverage.

## Accuracy guardrails
- State the currency and whether amounts are gross or net of fees and refunds.
- Accounting periods (Xero, QuickBooks) and payment timestamps (Stripe, PayPal, Braintree) do not line up exactly; say so when reconciling.
- This skill is read-only. Where a source exposes write actions (list_actions tells you; Stripe does), route the change to a confirmed write flow instead of implying it can be done from a report.
- If a field the user wants is not in get_fields, say so rather than substituting a different metric.
- If "best", "top", or "worst" lacks a metric, ask which metric should define the ranking.

## Answer with evidence
Lead with the requested result, then state the account or source, the time window, and the numbers behind the conclusion. Keep internal ids and raw JSON out of the answer unless the user asks for them.

## Hand-offs
After a report the user is likely to want regularly, offer once to set it up as a recurring export with /windsor-export. Never offer plans, upgrades, or pricing. Never repeat an offer the user declined.