---
title: Price-to-Win Research and Modeling
description: Research comparable prices and delivery costs to assess a proposed bid or develop a supported target price.
---

# Price-to-Win Research and Modeling

## 1. Resolve the target and pricing requirements

Reuse the active opportunity, order, files, proposed price and company assumptions, including for a terse `ptw` follow-up. Resolve missing targets with [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool), or [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool). Use [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool) if the record type is unknown; awards and IDVs use step 2's tools.

Find missing solicitation files, amendments and pricing schedules with [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool); use [Search_User_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-user-files-mcp-tool) for relevant company inputs. Read originals with native file tools or use [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval) for passages. Inspect workbook formulas and sheets with spreadsheet tools.

Establish the controlling quantities, units, locations, performance period, options, contract type and evaluation method. Apply the buyer's actual pricing instructions; for [federal A-E services](https://www.acquisition.gov/far/36.606), fee negotiation follows qualifications-based selection.

Some regulatory rules change the pricing basis: certified cost or pricing data and its exceptions, cost or price analysis, and cost realism. For these, read the governing FAR text and any agency supplement with `Get_Regulation_Section`, in the edition the buyer applies. Dollar thresholds are inflation-adjusted and some sections moved in the Overhaul, so read the current figure instead of recalling it. `govtribe-proposal-workflows` reference `references/acquisition-regulations.md` covers editions and citations.

## 2. Research predecessor and comparable prices

Start with the predecessor, then similar work for the same office or vehicle, then broader buyers with comparable scope.

- **Federal:** [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) for awards/orders; [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) for an established parent. For modification history, use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with the award's `federal_contract_award_ids` and `transaction_types: ["contract_award"]`; use `transaction_date_range` for action-period questions. Follow [award-value and transaction guidance](https://govtribe.com/docs/data-model/guides/federal-award-values-and-transactions); keep parent ceilings, award obligations and action amounts separate.
- **State/local:** [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool) and [Search_State_And_Local_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-idvs-mcp-tool) for reported awards and parent relationships. Use [Search_Line_Items](https://govtribe.com/docs/govtribe-for-agents/tools/search-line-items-mcp-tool) for awarded unit prices, quantities and units; verify the parent award and source schedule.

Expand useful records with `fields_to_return` and read their pricing attachments. For federal award files, resolve linked opportunity notices and pass their notice IDs to `Search_Government_Files` as `federal_contract_opportunity_ids`. For federal unit prices, use source schedules or disclosed bid/award documents. Keep supplier quotes and published catalog prices distinct from awarded prices. Missing history remains a research gap for the selected target.

## 3. Make the comparisons meaningful

Use [Comparable Prices and Cost Drivers](comparable-prices-and-cost-drivers.md) to classify the evidence and establish equivalent scope, specifications, quantities, units, geography, contract terms, dates and base/option periods. Document supported adjustments for inflation, delivery, installation or changed requirements; retain unmatched records as context. Federal price-analysis guidance describes comparable-price adjustments. It's codified at FAR 15.404-1(b); read it with `Get_Regulation_Section` in the buyer's edition.

Label amounts by their actual basis: announced award amount, evaluated price, unit price, obligation, ceiling or rate. A parent ceiling or partial funding history cannot establish an order's competitive price. Determine whether a matched group supports a proposed-price assessment or target range. If none does, retain useful arithmetic observations and identify the evidence needed instead of constructing a range from unmatched signals.

## 4. Estimate delivery costs

Use company costs and the proposed delivery approach: quantities, labor, materials, subcontractors, transition, travel, escalation and margin. For labor, read [Staffing and Labor Costs](staffing-wage-and-labor-category-workflow.md) and separate productive capacity used for staffing from paid compensation hours and billable pricing hours. Apply each rate to its matching hour basis, using an explicit conversion where needed. If missing bases prevent a cost estimate, report that gap; a scenario may still show the effect of clearly stated assumptions.

Select supporting sources as needed: [BLS_Occupational_Wage_Data](https://govtribe.com/docs/govtribe-for-agents/tools/bls-occupational-wage-data-mcp-tool) for occupation/location wages; [Search_GSA_Labor_Rates](https://govtribe.com/docs/govtribe-for-agents/tools/search-gsa-labor-rates-mcp-tool) for published ceilings; [Search_Service_Contract_Inventory](https://govtribe.com/docs/govtribe-for-agents/tools/search-service-contract-inventory-mcp-tool) for reported labor hours, FTEs and workshare. These inform assumptions; they do not disclose a contractor's actual costs or bid. Show the basis of modeled burden, wrap and margin, distinguishing margin from markup.

## 5. Assess the price and explain the result

Assess the requested decision using the established bases: delivery costs or feasibility from the disclosed company model, compliance with an applicable ceiling, and competitive position from matched price evidence. Keep these findings separate when their evidence differs. Test material staffing, quantity, escalation or discount assumptions with conditional scenarios, identifying what each scenario can establish.

Return the supported price assessment or company-cost scenario, or state that a cost estimate or competitive range cannot yet be established. Retain supported staffing calculations and price observations and name the inputs or research that would resolve the remaining decision. A cost scenario does not establish a winning price. Historical awarded rates are a reference; recommending them as a lowest-risk offer requires current delivery-cost and competitive support. Use `govtribe-capture-workflows`' Capture Price-to-Win Review for pursuit, teaming and competitive-position decisions, carrying forward the analysis and assumptions.

Report the selected target and source revision, parent agreement identifier, geography and inclusions, and base/option dates and status alongside the price assessment. Explain sources, assumptions, material uncertainties and next actions in a GovCon format suited to the requested use.
