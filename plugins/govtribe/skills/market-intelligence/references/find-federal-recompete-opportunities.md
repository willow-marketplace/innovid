---
title: Find Federal Recompete Opportunities
description: Find follow-on forecasts, contract opportunities, or grant competitions for an existing opportunity, award, IDV, or vehicle.
---

# Find Federal Recompete Opportunities

Start from the supplied contract or grant opportunity, award, IDV, or vehicle. Use the requested look-forward window; otherwise **Upcoming** is today through 24 months: `{"from": "now/d", "to": "now+24M/d"}`. Work ending in this window may have a follow-on competition. Retrieve the starting record and exact-identifier matches without date filters, then assess their timing. See [date filtering](https://govtribe.com/docs/govtribe-for-agents/guides/date-filtering).

## 1. Read the starting record

Use [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool) to resolve an unfamiliar link or identifier, then retrieve the record with the matching tool and `govtribe_ids`:

- **Contract opportunity:** [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool); known solicitation numbers also support `solicitation_numbers`. Read `solicitation_number`, `descriptions`, `federal_agency`, and `federal_meta_opportunity_id`.
- **Contract award, IDV, or vehicle:** [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool), or [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool). Awards/IDVs also accept `piids`. Read the requirement, buyer, incumbent/holders, parent instruments, and originating opportunity.
- **Grant opportunity or award:** [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool) or [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool). Search raw announcement/grant numbers with `query`; read `solicitation_number` or `grant_number`, `description`, agency, recipient, and `federal_grant_programs` (opportunities) or `federal_grant_program` (awards).

Request identifiers, names and record/source URLs alongside these fields. Expand the descriptions to capture program names, acronyms, distinctive work, geography, eligibility, and predecessor references.

## 2. Establish the current work and end dates

For an awarded contract opportunity, retrieve linked awards/IDVs with `federal_contract_opportunity_ids`. Follow `federal_meta_opportunity_ids` for related notices; these describe the original procurement, while the successor may have a different number and chain. See [notice-chain relationships](https://govtribe.com/docs/data-model/guides/federal-contract-opportunity-notice-chains).

Check awards' `completion_date` and `ultimate_completion_date`, or IDVs'/vehicles' `last_date_to_order`, against Upcoming. Inspect options, extensions and bridge arrangements in the source documents. Use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) filtered by `federal_contract_award_ids` or `federal_contract_idv_ids` when recent modifications clarify them. For vehicles/IDVs, distinguish replacement of the purchasing instrument from recompetes of individual task orders.

For construction, distinguish recurring service recompetes from project progression. Design-to-construction or construction-to-maintenance may create follow-on work without recompeting the original requirement.

For grants, read the project period and original announcement to determine whether the target is a new program round, competitive renewal, or noncompeting continuation. A shared funding program alone does not link an award to a specific announcement.

## 3. Search for the successor

Search the old contract/announcement number in `query`, plus separate searches for the program/acronym and distinctive requirement wording. Narrow by buyer or grant program as useful. Begin without date/type restrictions so earlier announcements, closed RFIs and undated forecasts remain discoverable.

- **Contract forecasts:** use [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool). If narrowing by Upcoming, search `estimated_solicitation_release_date_range` and `anticipated_award_start_date_range` separately; merge by `govtribe_id`.
- **Contract notices:** use `Search_Federal_Contract_Opportunities`, including pre-solicitations, special notices and solicitations. Read `opportunity_type`, `opportunity_state`, `posted_date`, `due_date`, and descriptions to establish the latest stage.
- **Grant competitions:** use `Search_Federal_Grant_Opportunities` with `federal_grant_program_ids` and program/announcement wording. Posted opportunities use `opportunity_types: ["Posted"]`; forecasts use `["Forecasted"]`. For large result sets, narrow to Upcoming with `due_date_range`, or separate `forecast_posting_date_range` / `forecast_due_date_range` searches. Read the announcement for recurring application rounds.

When wording changes, use semantic search or [similarity](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records) on the target tool with `similar_filter: {"govtribe_type": "<seed type>", "govtribe_id": "<seed ID>"}`. Cross-type similarity supports discovery; inspect candidates for actual succession. Review remaining relevant pages before concluding no match was found.

## 4. Verify the relationship and current status

Read candidate descriptions and original announcements for explicit replacement, reissue, predecessor-number or next-round statements. Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) with notice `govtribe_id`s in `federal_contract_opportunity_ids` or `federal_grant_opportunity_ids`, or use `federal_contract_idv_ids`, to find attachments. Read `download_url` files with native tools or use [vector-store retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval).

Search official agency, SAM.gov and Grants.gov sources by the original number, program, buyer and incumbent when linkage or status remains unclear. Retrieve any newly identified successor in GovTribe by its number/title. Check amendments, extensions, cancellations, award notices, protests and corrective action; distinguish an actionable competition from an already-awarded successor or an extension of existing work.

## 5. Assess follow-on quality and prioritize

Use descriptions, source documents, lineage, task orders and supporting transaction evidence to assess continuity of scope, buyer/program, timing and eligibility. Prefer explicit successor links; label inference when only those attributes align. Exclude weakly relevant or structurally mismatched records supported only by keyword overlap. Rank credible matches by relevance, relationship strength and timing.

If no supported match remains, report that no follow-on forecast or opportunity was found in the sources checked, with any unresolved candidate or missing evidence.

## Report

Explain the relationship, availability, timing, supporting links and gaps in the requested format. For each unverified vendor role, report “unresolved.” Without a buyer source identifying the next procurement, limit follow-on implications to unresolved route and eligibility plus retrieval steps, omitting probable methods and categorical exclusions.
