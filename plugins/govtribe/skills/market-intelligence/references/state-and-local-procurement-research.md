---
title: State and Local Procurement Research
description: Research a state or local solicitation, award, parent contract or vehicle through original sources, exact relationships and purchased items.
---

# State and Local Procurement Research

## 1. Resolve the procurement

Retrieve the supplied record with [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool), [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool), [Search_State_And_Local_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-idvs-mcp-tool), or [Search_State_And_Local_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-vehicles-mcp-tool). Use `govtribe_ids` for known records, or search the source number/title with `query` and confirm the buyer, state, jurisdiction and dates. Retrieve exact matches without date restrictions.

Check the [state/local data model and coverage](https://govtribe.com/docs/data-model/guides/state-and-local-contract-data-model). Opportunity coverage is broader than awarded-record coverage; missing records or links do not establish that no procurement occurred. Retain source numbers and portal links because field depth and relationships vary by source.

## 2. Read the controlling source material

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) with `state_local_contract_opportunity_ids` for notices, or `govtribe_ids` for exact returned government-file IDs. For awards, IDVs and vehicles, follow returned file or notice links; inspect the source portal when no file identity is available. Award and IDV parent filters are not currently supported by this tool. Read originals through `download_url` with native tools, or follow [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval); inspect the source portal when attachments or status are incomplete.

- **Notice:** establish current scope, deadline, submission route and mandatory pre-bid meetings. Check addenda, bid forms and pricing schedules, local preferences or participation goals, bonding, insurance and licensing. Resolve conflicts against the latest controlling notice or amendment.
- **Awarded work:** establish who holds the contract, what it covers, its term, options and renewals. For IDVs and vehicles, read ordering eligibility, participation rules, scope exclusions and catalogs. Separate permission to use a contract from evidence that a buyer actually used it. Preserve the source meanings of estimated annual value, contract amount and cumulative value; do not add overlapping figures or label them federal obligations.

## 3. Follow the awarded structure

Use returned opportunity, IDV, vehicle and child-record links first. A vehicle groups IDVs; an IDV can connect to awarded work. Retrieve IDVs with `state_local_contract_vehicle_ids`, then awards with `state_local_contract_idv_ids`. Inspect verified descendants for holders, ordering activity and usage; deduplicate awards before combining results across parents. Use the relevant full result set for totals, and label conclusions based on a sample.

Reuse a returned vendor ID when available. When only an awardee name is present, resolve it with [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool), corroborating identity rather than merging similarly named firms.

When links are missing, compare source contract/solicitation numbers, buyer, state/jurisdiction, dates, scope, categories and original files. Use [similarity](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records) to discover candidates; semantic matches and their aggregations do not establish actual vehicle usage or a confirmed predecessor. NIGP/UNSPSC categories help find related work; returned category lists may be incomplete even when the record matches a category filter.

## 4. Inspect purchased items and expand as needed

Use [Search_Line_Items](https://govtribe.com/docs/govtribe-for-agents/tools/search-line-items-mcp-tool) with IDs from returned `line_items` as `line_item_ids`, or parent `state_local_contract_award_ids` / `state_local_contract_idv_ids`, when quantities, goods or prices matter. Read description, quantity, unit price and unit of measure against the source schedule, including package size and exclusions. `Each` can be a fallback for a missing source unit. State/local line items are item details, not obligation transactions; there is no shared federal-style transaction history.

Use `govtribe-pricing-data` for comparable prices, staffing economics or a pricing model, and `govtribe-proposal-workflows` for submission requirements or a response package. For broader buyer activity, use [State and Local Buying Pattern Analysis](./state-local-buying-pattern-analysis.md). Expand contacts, peers or categories only where they help answer the procurement question.
