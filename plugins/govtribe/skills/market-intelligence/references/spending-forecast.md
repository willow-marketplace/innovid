---
title: Spending Forecast
description: Estimate future federal contract obligations from historical spending and known procurement changes, with explicit timing and value scenarios.
---

# Spending Forecast

Use when the user asks how much a federal buyer, program, or market may obligate over the next 24–36 months and when that activity is likely to occur. Use [Early Federal Procurement Signals](./find-early-federal-procurement-signals.md) to discover planned requirements.

## 1. Define the measure and scope

Specify the buyer, work categories, horizon, and dollar basis. Set an as-of date and explicit horizon end; include only the part of each boundary quarter inside that horizon, keeping elapsed actuals separate. Use known funding timing to allocate partial quarters; otherwise state the assumption, such as prorating by calendar days. Keep planned procurement values, obligations, outlays, and contractor revenue distinct. For federal contracting, estimate quarterly net obligations and report the planned-procurement pipeline separately.

## 2. Build a historical baseline

Use [Federal Buying Pattern Analysis](./federal-buying-pattern-analysis.md#5-compare-periods-using-the-same-scope) for comparable periods and [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with:

- `transaction_types: ["contract_award", "contract_idv"]` and consistent buyer/work filters.
- `transaction_date_range` for activity in each period, without an `award_date_range` that would exclude modifications to older awards.
- Prefer three complete years. Compare the last complete fiscal year's quarterly pattern with earlier years and current year-to-date activity; compare partial years with equivalent periods.

For federal-only totals, retrieve all matching transaction pages with `govtribe_id`, `date`, `federal_value`, `transaction_type`, and `parent_record`. Split large searches into nonoverlapping date windows with fewer than 10,000 matches so pagination can cover each window. Deduplicate by transaction ID, retain negative adjustments, and group into nonoverlapping fiscal quarters. Reconcile retrieved coverage with the search total; missing amounts remain unknown.

For reported total-value analysis, `dollars_obligated_stats`, `positive_value_stats`, and `negative_value_stats` with `per_page: 0` summarize signed amounts, including non-federal shares. Use separate, nonoverlapping quarterly date ranges; there is no transaction date-histogram aggregation. Label this amount basis explicitly. See [award values and transactions](https://govtribe.com/docs/data-model/guides/federal-award-values-and-transactions).

Repeat the latest representative full year's quarterly pattern as the initial baseline. Identify one-time awards or structural changes before carrying them forward.

## 3. Replace baseline activity with known changes

Match incumbents, forecasts, notices, and successors into one requirement using identifiers and source documents. For each material requirement, estimate future quarterly obligations from funding schedules, incumbent transactions, or comparable awards.

Forecast = baseline + sum of (future requirement obligations − its contribution already included in the baseline).

Add new work, subtract supported closures or reductions, and replace incumbent spending with successor spending. For partial consolidations, subtract only incumbent spending attributable to the successor's scope. A same-size recompete may leave the market total unchanged. For example, a $20m annual baseline containing an $8m incumbent becomes $22m if the successor is expected to obligate $10m annually: $20m − $8m + $10m.

Preserve forecast value ranges and obtain duration or funding timing from source material. A multi-year value is not annual spending, and unused contract or vehicle ceiling is not an expected obligation. Where an even annual allocation is necessary, label it as an assumption and test different timing.

## 4. Estimate scenarios and report

Build low/base/high scenarios using supported changes in start dates, continuation, scope, and obligation timing. Explain which assumptions drive the range; use historical variation or comparable requirements rather than arbitrary stage probabilities.

Report each future year's estimate, the cumulative 24-/36-month range, periods of concentrated activity, major adjustments, and requirements with unknown values or dates. Sum each complete scenario over the horizon; do not combine incompatible yearly extremes. Describe these as scenario ranges, not statistical confidence intervals.

If history or funding timing cannot support a dollar estimate, report the dated demand outlook and the missing inputs. Keep documented procurement dates separate from modeled obligation timing.
