---
title: Comparable Prices and Cost Drivers
description: Find comparable awarded prices and select the economic units, included costs and adjustments that make a GovCon price comparison meaningful.
---

# Comparable Prices and Cost Drivers

## 1. Establish what is being compared

Reuse the selected work, item, source schedule and supplied prices. Before normalization, classify each amount as a wage, loaded cost, bill rate or item price, ceiling, derived ratio or other stated basis. Record its denominator, included costs, scope, quantities, locations and period; for hourly figures, distinguish paid, productive and billable hours where known. Leave an unknown basis unresolved. For mixed procurements, compare equipment, installation, licenses and operations on their own economic bases.

Confirm units from the source schedule: [Each can be a fallback](https://govtribe.com/docs/data-model/attributes/unit-of-measure-attribute), not proof of an hourly unit or of a unit error. Establish the quantity, packaging or service conversion before treating the item as a comparable.

## 2. Find the relevant price evidence

- **State/local awarded prices:** use [Search_Line_Items](https://govtribe.com/docs/govtribe-for-agents/tools/search-line-items-mcp-tool) for item descriptions, quantities, units and prices. Start with a known parent or search the specific product/service and narrow to comparable quantities and units. Resolve parent context with [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool) or [Search_State_And_Local_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-idvs-mcp-tool), following available relationships.
- **Federal prices:** use [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) for comparable awards/orders and [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) for established parents. Follow [award-value guidance](https://govtribe.com/docs/data-model/guides/federal-award-values-and-transactions): obligations, action amounts and ceilings are different measures. Award totals do not establish unit prices or amounts paid; a parent ceiling is not an order price.

Read supporting pricing schedules, disclosed bids and award documents. Find government attachments with [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool); for federal awards, use linked opportunity notices. Use native file tools for originals or [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval) for passages. Keep awarded, quoted, catalog/list and retail prices separately labeled; record quote validity and included scope. A bid tabulation alone does not establish which price was awarded.

## 3. Select the economic basis and cost drivers

Use the applicable rows to identify material differences and missing inputs.

| Work or item | Basis and differences to check |
| --- | --- |
| Products, manufacturing and equipment | Exact part/model/revision, package size and quantity breaks; freight, inspection, installation, warranty and spares. For manufactured lots, separate setup/tooling from recurring materials, labor and yield. Use current supplier quotes and source quantities; distinguish acquisition price from evaluated lifecycle cost. |
| Software subscriptions and cloud | Named/concurrent users, devices, sites or usage; entitlement, term, tiers, minimum commitments, storage/egress, support and renewal. Separate implementation/migration from recurring licenses, consumption and operations. Labor benchmarks do not supply commercial product or cloud prices. |
| Labor, development and managed services | Labor-hour/FTE, team/month, fixed deliverable, ticket/case/session or service outcome. Compare qualifications, workload, productivity, coverage, service levels and transition. Separate development from recurring delivery. Use [Staffing and Labor Costs](staffing-wage-and-labor-category-workflow.md) for labor research and productive-hours assumptions. |
| Construction and design-build | Quantity takeoff by trade, local quotes, wage basis, site conditions, phasing and schedule; include relevant general conditions, mobilization, bonds and escalation. Keep design and construction scope visible. Square-foot prices require comparable facility, quality and site scope; unit-price books require the actual version and coefficient rules. |
| Architect-engineer services | Discipline hours, deliverables, subconsultants, surveys, design reviews and construction-phase services. For [federal A-E services](https://www.acquisition.gov/far/36.606), fee negotiation follows qualifications-based selection. Construction low bids do not establish a comparable design fee. |
| Facilities, repair and emergency services | Asset inventory, work orders, condition/repair mix, response times and coverage. Separate included materials from reimbursable materials/project work, and routine service from standby, activation and surge. |
| Transportation and logistics | Shipment, storage position, order or mile/route/hour. Convert rates only through a supported operating profile, including utilization, empty travel, fuel, minimum volume and fixed-facility costs. |
| Energy and environmental services | Separate utility/disposal pass-through costs from service and risk. Energy savings comparisons need a documented baseline, tariff, financing, measurement and guarantee basis. |

Add mission-specific drivers only when sources establish them: credentialed coverage or cold chain, a defined cyber assessment boundary, hazardous disposal, or R&D iterations and milestone uncertainty. Obtain missing engineering, workload, supplier or company inputs before treating these as quantified costs.

## 4. Normalize and explain the comparison

Form comparable groups using scope, included costs, units, geography, period and delivery terms. Show supported conversions and adjustments, retaining original prices and their source bases. Keep unexplained bundles and missing quantities unresolved rather than inventing unit prices.

For a fixed fee `P > 0` and actual use `0 < N <= Nmax`, `P/N >= P/Nmax`: full use gives the minimum effective unit price; “up to” caps quantity, not that effective price. Keep the original billing unit and leave actual usage and its effective unit price unknown without usage data. A quantity cap bounds the extension, not the firm fixed unit price; identify a rate ceiling only when the source defines one.

Useful numeric differences or quotients between unmatched figures may be reported as arithmetic context, with their differing bases visible. A conditional conversion explores its stated assumption; it does not verify the actual unit or scope. A comparable-price range or outlier judgment requires an economically matched group, not just a large difference or a numerical span between unlike figures. If no such group exists, report the missing comparison basis and the specific evidence needed.

Use [Pricing Model Workflow](pricing-model-workflow.md) when calculations or workbook changes are requested. Carry supported comparisons and unresolved bases into [Price-to-Win Research and Modeling](price-to-win-analysis.md) when assessing a proposed bid or developing a target price.
