---
title: Likely Bidders
description: Identify likely bidders or grant applicants for a government opportunity.
---

# Likely Bidders

## 1. Identify the opportunity and read its requirements

Reuse the resolved opportunity. For a supplied pursuit, use [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool) to retrieve its linked opportunity. Otherwise use [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool), or [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool), using `govtribe_ids` for known records. Use [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool) when the record type is unclear.

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) for the latest solicitation or funding notice, amendments and Q&A. Establish current status, scope, buyer, eligibility, vehicle access, scale and timing. Compare file IDs, posting dates and amendment text when versions disagree. Read full files through native download/text tools or [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval) when snippets are insufficient.

## 2. Find comparable performers

Unless the user specifies another period, use the past five years of awards plus older active work and directly relevant predecessor experience. For federal contracts, use [Find Incumbent](find-incumbent-for-federal-contract-opportunity.md) where relevant.

- **Federal contracts:** [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), comparing work scope, buyer/office, scale and dates. Also use [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) to find holders with relevant scope and buyer relationships, including firms without comparable task-order wins. Assess delivery experience through awarded orders.
- **Grants:** [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool), comparing program, funded activities, recipient type and geography; assess eligibility against the current funding notice.
- **State/local contracts:** [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool), using scope, buyer, state and classifications. Retrieve suppliers from `awardee_name`; `contracting_organization` identifies the buyer.

Use the opportunity as a [similarity anchor](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records), or search distinctive work-scope terms. Inspect results before adding comparison filters that could exclude credible firms. Request IDs, source URLs and relevant award details through `fields_to_return`. For large federal award cohorts, `top_awardees_by_dollars_obligated` can surface candidates; inspect their actual awards before ranking.

Use exact item, part/NSN, platform and facility identifiers where relevant. Consider manufacturers, authorized channels, platform specialists and local trades alongside award incumbents; verify claimed approvals and relationships.

## 3. Find additional credible candidates

Use [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool) with target-opportunity `similar_filter` or capability terms. Review company websites and relevant source documents for entrants beyond previous winners.

Where relevant to the acquisition approach, use [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool) to discover applicable vehicles, then `Search_Federal_Contract_IDVs` with `federal_contract_vehicle_ids` to find holders. For competitions under a vehicle, check the required pool/SIN and current ordering access.

## 4. Check experience, eligibility and interest

Resolve candidate identities with `Search_Vendors` and deduplicate by `govtribe_id`; inspect supporting awards, relevant certifications, actual bidding entities and prime/subcontractor or recipient/subrecipient roles. Compare demonstrated scope and scale, buyer experience, delivery location and access. Keep missing information distinct from a confirmed mismatch.

Check public bidder lists, planholder/pre-bid attendance records, company announcements and other available sources for participation signals. Explain what each signal establishes: a submitted bid, expressed interest, or only relevant capability. For grants, historical recipients are leads for potential applicants.

## 5. Compare candidates

Explain each candidate's strongest supporting work, competitive advantages, material gaps and evidence of interest. For partner research, adapt discovery to complementary capabilities and buyer access; use the research steps in [Past Performance Match](past-performance-match.md) to assess the proposed workshare.

Prepare an appropriate GovCon report with a reasoned shortlist, supporting links and material uncertainties. Let the requested use determine the format.
