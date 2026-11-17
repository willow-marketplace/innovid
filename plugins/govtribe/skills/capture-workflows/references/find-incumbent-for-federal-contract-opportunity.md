---
title: Find Incumbent for a Federal Contract Opportunity
description: Identify who performs the work behind a federal contract opportunity and the supporting contract or task order.
---

# Find Incumbent for a Federal Contract Opportunity

## 1. Read the opportunity and its documents

Resolve the supplied notice with [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), using `govtribe_ids` or `solicitation_numbers`. Establish whether the question concerns today's performer or the predecessor to a particular procurement; use the requested date, otherwise today.

Read descriptions, buyer, location and related notices. Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) with `federal_contract_opportunity_ids` to find the latest solicitation, controlling amendments, Q&A and transition documents. Look for incumbent names, existing contract/order numbers, distinctive programs and work being replaced. Read full files through native download/text tools or [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval) when snippets are insufficient.

## 2. Retrieve the named contracts and related awards

Look up contract/order numbers with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) and `piids`; use [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) for parent-instrument numbers. Search exact identifiers without date restrictions.

For related awards/IDVs, use `federal_contract_opportunity_ids` for the selected notice or `federal_meta_opportunity_ids` for its notice chain. Follow the [notice-chain guide](https://govtribe.com/docs/data-model/guides/federal-contract-opportunity-notice-chains); MAS-level chains can include unrelated schedule context. A linked award may be the new winner or a vehicle holder; establish its role before calling it the incumbent.

When a parent IDV is identified, search its orders through `Search_Federal_Contract_Awards` with `federal_contract_idv_ids`. Request `govtribe_id`, `contract_number`, `awardee`, `descriptions`, `contracting_federal_agency`, `place_of_performance`, `award_date`, `completion_date`, `ultimate_completion_date`, `federal_contract_idv` and `govtribe_url` through `fields_to_return` to compare the actual work.

## 3. Fill gaps with targeted research

Search awards using distinctive program, system, site or requirement wording and the relevant buyer/office. Follow newly discovered contract numbers and predecessor references immediately. Use semantic search or `similar_filter` when wording has changed. Refine searches and review relevant remaining results before concluding no match was found.

Use classifications, vehicle, geography and dates where they help; the follow-on's NAICS, set-aside, vehicle or contracting office may differ. Avoid recent-award cutoffs that hide older contracts still performing the work. Search official agency forecasts, procurement announcements, SAM.gov and other web sources for unresolved incumbent or predecessor details, then retrieve any identified awards in GovTribe.

## 4. Verify who performs the work and when

Compare scope, location, buyer, awardee and performance dates. Use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool), filtered by `federal_contract_award_ids`, and source documents when modifications, extensions or bridge contracts affect the answer. An IDV's ordering deadline and an award's possible option end date do not establish current performance.

Distinguish the current performer, historical predecessor and successor/new winner. Allow multiple incumbents for consolidated work or a documented new requirement without an incumbent. If only an inferred match or historical award is supported, explain the missing link; an empty search does not establish that no incumbent exists.

Prepare an appropriate GovCon report identifying the supported performer and contract/order, relevant dates, supporting links and material gaps. Let the requested use determine the format.
