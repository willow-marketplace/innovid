---
title: Federal Contract and Vehicle Research
description: Explain a federal award, order, IDV or vehicle through its instrument chain, transactions, holders, ordering terms and source notices.
---

# Federal Contract and Vehicle Research

## 1. Resolve the instrument and question

Reuse the selected record and requested period. Resolve an unfamiliar link or identifier with [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool), then use [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) or [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool). Use `govtribe_ids`; awards and IDVs also support `piids`. Retrieve exact matches without historical cutoffs.

Establish the work, buyer, awardee/holder, dates and [instrument type](https://govtribe.com/docs/data-model/guides/federal-contract-data-model) from source fields. Distinguish an order, holder contract, BPA, schedule/FSS contract and vehicle/program rather than relying on titles. Retain the exact legal entity; use [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool) when identity needs resolution, expanding the corporate family only when relevant to the requested scope.

## 2. Follow the relevant parent and child relationships

- **Award or order:** inspect its returned parent IDV and vehicle when they explain ordering terms, scope or value. The parent does not replace the order as evidence of who performed the specific work.
- **IDV:** resolve its parent vehicle and retrieve orders with `Search_Federal_Contract_Awards` using `federal_contract_idv_ids`. Examine order count, obligations, buyers, categories and timing; distinguish the legal holder from downstream performers.
- **Vehicle:** retrieve holder contracts with `Search_Federal_Contract_IDVs` using `federal_contract_vehicle_ids`, and downstream awards with that filter on `Search_Federal_Contract_Awards`. Distinguish active, expired and low-activity holder contracts. A holder list is different from observed order performance; adjacent vendors are not automatically holders.

Use source terms to explain pools/domains, eligibility, competition, task-order paths, on-ramps and ordering periods. A vehicle seat does not establish eligibility for every order or performance in every category. Parent/child rollups can include the same orders: deduplicate before calculating activity or shares. Use [Federal Buying Pattern Analysis](federal-buying-pattern-analysis.md) for broader portfolios, concentration or period comparisons.

## 3. Explain modifications, dates and value

Use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with the exact `federal_contract_award_ids` or `federal_contract_idv_ids` when funding cadence, modifications, exercised options, extensions or deobligations matter. Read action dates, modification numbers/reasons and source documents; distinguish an initial action from later changes. Sparse history does not establish unreported options or extensions.

Separate award `completion_date` and `ultimate_completion_date` from an IDV/vehicle's `last_date_to_order`. A possible option end date or ordering deadline does not prove current performance. Use [Find Federal Recompete Opportunities](find-federal-recompete-opportunities.md) for successor research; replacing a vehicle differs from recompeting an individual order.

Follow [award values and transactions](https://govtribe.com/docs/data-model/guides/federal-award-values-and-transactions). Keep summary obligations, action values, award/IDV potential value and shared vehicle ceilings separate. Transaction totals can include signed adjustments and reported non-federal amounts. Do not add summary obligations to the same award's transactions or count parent and child amounts twice. Obligations are not revenue or backlog; a shared ceiling is not realized demand or market size. Establish the scope of the ceiling and usage evidence before describing remaining capacity.

## 4. Read originating notices and material terms

Inspect a returned `originating_federal_contract_opportunity` first. When the broader chain matters, pass `originating_federal_meta_opportunity_id` as `federal_meta_opportunity_ids` to [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool). Missing originating-notice or predecessor links leave lineage unresolved. Search contract numbers and distinctive requirement wording in notices and forecasts, then inspect candidate source files and notice chains. Follow [Federal Procurement Status and History](federal-procurement-status-and-history.md) for chronology, missing linkage and MAS caveats. Matching scope, buyer, location, vendor or timing supports inference, not confirmation. Keep the awarded instrument as the source for actual awardee, values, transactions and ordering structure.

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) with confirmed notice IDs in `federal_contract_opportunity_ids` or the relevant `federal_contract_idv_ids`. Read exact ordering, scope, amendment and on-ramp language through native tools or [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval). Disclose material unavailable attachments.

For schedule terms, inspect direct `price_lists`. For MAS/FSS labor rates, use [Search_GSA_Labor_Rates](https://govtribe.com/docs/govtribe-for-agents/tools/search-gsa-labor-rates-mcp-tool) with `federal_contract_idv_ids` set to the resolved IDV. These are ceiling rates, not proven task-order winning rates; use `govtribe-pricing-data` for rate comparisons and pricing economics.

Explain the instrument facts and requested implications with supporting links and material gaps. Keep incumbent verification, teaming and company-specific access or bid decisions with `govtribe-capture-workflows`, and response preparation with `govtribe-proposal-workflows`. Preserve the resolved instruments and source findings across those handoffs.
