---
title: Build, Review and Revise a Pricing Model
description: Assemble supplied and researched pricing inputs, review model economics, and revise selected workbooks while preserving formulas and buyer requirements.
---

# Build, Review and Revise a Pricing Model

## 1. Establish the action and working file

Identify whether the user wants a new model, a review, or specified changes. A review inspects and reports findings without changing the supplied workbook or selecting final prices. Reuse the active opportunity, buyer pricing instructions, company assumptions, BOE and selected working revision. Sufficient supplied inputs need no fresh research or broad file search.

Find a missing named saved file with [Search_User_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-user-files-mcp-tool). Use [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval) for supporting passages; obtain the exact original through native file capabilities to inspect formulas, complete tables and workbook structure. Vector excerpts cannot establish those properties. Distinguish reused company assumptions from dated market evidence requiring refresh.

Use `govtribe-file-templates` for library masters and template revision guidance. Continue the selected working file for revisions, retaining the original; do not reset it to a master or populated example. Use native spreadsheet capabilities for execution.

## 2. Trace inputs through costs and prices

Before formulas, classify rate inputs as wages, loaded costs, bill prices, ceilings or derived ratios, and their denominators as paid, productive, billable or other units. Establish quantities, included costs, currency, source dates, rate year, periods, options and buyer-required CLINs. Link inputs and any conversion between bases to sources or explicit model assumptions; keep unresolved inputs separate from calculated costs and prices. Use [Staffing and Labor Costs](staffing-wage-and-labor-category-workflow.md) for labor bases and [Comparable Prices and Cost Drivers](comparable-prices-and-cost-drivers.md) for appropriate comparisons.

Trace applicable WBS elements through quantity → productivity → hours → rate → cost → escalation/reserve → price → period and CLIN totals. Products, subscriptions, construction quantities and other nonlabor items need their own relevant units and charges. Use the matching labor-hour volume for each cost or revenue formula, showing any bridge from staffing capacity to paid or billable hours. Label assumed bridges and their results as conditional scenarios. Keep participant payments or similar compensation separate from staff labor.

Separate wage, loaded cost and proposed bill price. Apply indirect rates to the company's stated allocation bases; sequential multiplication is appropriate only when those bases justify it. Avoid burdening already loaded inputs. Identify fixed, variable and conditional charges, whether fees are cumulative or alternative, and which survive zero volume. Distinguish blank inputs from zero offers; check renewals, taxes and payment terms when they affect totals. Apply escalation once to its named basis and period; show reserves explicitly.

Distinguish fixed fee or percentage fee on a named eligible base from markup and gross margin:

- Price with cost markup: `cost × (1 + markup)`.
- Price at target gross margin: `cost ÷ (1 − margin)`.

Use formulas for derived amounts and rollups, keeping inputs and assumptions visible. Observed comparables, company costs and modeled prices retain their separate meanings; cost arithmetic alone cannot establish a winning price.

## 3. Preserve the requested revision scope

Identify allowed input cells, sheets and output ranges before editing. Preserve buyer forms, table identity, formulas, validations, historical tabs and unrelated content. For rate-only changes, compare formula maps, validations and non-target inputs before and after: an upstream quantity or productivity formula change is a defect even if current totals match. If a business-logic correction would expand the requested scope, explain it before making that additional change.

## 4. Reconcile and check behavior

Independently recompute material quantities, hours, costs, fee and price totals. Reconcile detail, summary, CLINs, base/option periods and any narrative. A workbook's own `OK` label is insufficient evidence.

For material drivers, calculate expected sensitivity changes independently before testing scratch copies. Include zero volume where relevant, retaining only justified unconditional charges. If the model relies on a reconciliation check, verify it detects a deliberately broken CLIN rollup. Keep review-only checks separate from changes to the supplied file.

Recalculate and inspect affected formulas, errors, dependencies, required output ranges and readable summaries with native capabilities. Checks must cover the delivered revision; repeat affected checks after changes. Explain material findings, assumptions and missing inputs in the requested format. If original-file access, calculation or inspection is unavailable, identify the unverified checks and provide supported analysis or a labeled model draft.

If requested, separately update a saved GovTribe file's description or pursuit association using [Update_User_File](https://govtribe.com/docs/govtribe-for-agents/tools/update-user-file-mcp-tool), resolving the named pursuit with [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool) and checking the returned file. This changes saved-file metadata, not workbook bytes; a host-only artifact has no implied saved User File identity.
