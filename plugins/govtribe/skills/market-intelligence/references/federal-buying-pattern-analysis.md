---
title: Federal Buying Pattern Analysis
description: Research federal agencies, vendors, markets and contact-linked activity through awards, grants, subawards, vehicles and current opportunities.
---

# Federal Buying Pattern Analysis

## 1. Define the research scope

- Resolve agencies and offices with [Search_Federal_Agencies](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-agencies-mcp-tool), and companies with [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool). Both guides include keyword, semantic, and filtered search examples. Request `govtribe_id`, `name`, and identifying fields such as agency `acronym` or vendor `uei`; reuse returned IDs in subsequent searches. For vendors, distinguish one registration from the corporate family and state which UEIs are included.
- Preserve the selected agency or office scope. A thin agency record does not establish its hierarchy or mission; use official organizational sources when needed and distinguish office findings from broader department activity.
- Use [Search_Naics_Categories](https://govtribe.com/docs/govtribe-for-agents/tools/search-naics-categories-mcp-tool), [Search_Psc_Categories](https://govtribe.com/docs/govtribe-for-agents/tools/search-psc-categories-mcp-tool), or [Search_Contacts](https://govtribe.com/docs/govtribe-for-agents/tools/search-contacts-mcp-tool) when the request starts from an industry, product/service, or contracting contact. Seed awards and returned descriptions can also identify the relevant work categories.
- Apply the resolved `vendor_ids`, `contracting_federal_agency_ids`, or `funding_federal_agency_ids` where supported. Contracting agencies conduct acquisitions; funding agencies supply the money. Choose the role that answers the question.
- Use the requested work categories, geography, and dates; default to the past 24 months. For an agency-wide profile, use initial rollups to identify major segments. See [query construction](https://govtribe.com/docs/govtribe-for-agents/guides/choose-a-search-mode-and-write-queries) [related-record filters](https://govtribe.com/docs/govtribe-for-agents/guides/filter-by-related-records-and-hierarchies), and [date filtering](https://govtribe.com/docs/govtribe-for-agents/guides/date-filtering).

Adapt searches to what the government is buying: use exact product identifiers, named systems, service functions, or project and facility names. Compare similar purchasing models and supplier roles—for example, manufacturers versus resellers, software subscriptions versus implementation services, and construction versus facilities operations.

Use [Company Profiles and Diligence](company-profiles-and-diligence.md) for current ownership, capabilities and business relationships beyond the spending profile. When assisted acquisition matters, compare funding and contracting agency views to identify who funds the work and who conducts the procurement.

### When research starts from a contact

- Resolve the person with `Search_Contacts` using the known ID, email or name. Distinguish namesakes, individuals and functional mailboxes; verify affiliation and current role against source records. For agency personnel research, find contacts with the resolved `federal_agency_ids` or selected `referenced_govtribe_ids` first.
- Trace relevant activity with `contact_ids` in [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool) or [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool). Separate historical work from upcoming notices. For grant or state/local activity, select the matching tools through [Grant Funding Research](grant-funding-research.md) or [State and Local Buying Pattern Analysis](state-local-buying-pattern-analysis.md), retaining the contact anchor.
- Inspect the actual notice, transaction or source file to establish how the contact participates. `Search_Contacts` with `referenced_govtribe_ids` confirms association; `reference_types` describes a contact's references anywhere and does not establish its role on that particular record. A listed POC alone does not establish decision authority or an existing relationship.
- Answer a contact-only question from these records. Expand to the organization's wider buying pattern when useful; use `govtribe-capture-workflows` for company-specific engagement planning.

## 2. Initial research

Check contracts, grants, and downstream relationships. Start with `per_page: 0` plus the relevant `aggregations` where supported, or include a few records alongside the summaries.

| Research | Tool and useful aggregations |
| --- | --- |
| Prime contracts | [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool): `dollars_obligated_stats`, `top_awardees_by_dollars_obligated`, `top_contracting_federal_agencies_by_dollars_obligated`, `top_set_aside_types_by_dollars_obligated`, `top_idvs_by_dollars_obligated`. |
| Grants | [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool): `dollars_obligated_stats`, `top_awardees_by_dollars_obligated`, `top_funding_federal_agencies_by_dollars_obligated`, `top_federal_grant_programs_by_dollars_obligated`. |
| Subcontracts | [Search_Federal_Contract_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-sub-awards-mcp-tool): `top_awardees_by_doc_count`, `top_contracting_federal_agencies_by_doc_count`; retrieve records for prime/subcontractor relationships. |
| Grant subawards | [Search_Federal_Grant_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-sub-awards-mcp-tool): retrieve records for prime/subrecipient relationships; this tool has no aggregation selector. |

For vendor research, subaward `vendor_ids` finds the subrecipient side. To examine that vendor as a prime, first retrieve its prime awards, then pass their IDs as `federal_contract_award_ids` or `federal_grant_award_ids` to the matching subaward tool.

Retrieve associated [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) and [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool) during this pass. Use vendor/agency filters where supported and follow the parent IDs returned with awards to find vehicles the agency uses. Inspect ordering dates, single/multiple-award structure, holders, and ceilings. For vehicles, `funding_federal_agency_ids` identifies program context, not every agency ordering through them. Parent/child IDV buckets can contain the same awards; compare one hierarchy level or deduplicate awards/transactions before calculating shares.

Keep contracts, grants, and subawards separate. Subaward search records lack dollar fields; use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with `transaction_types: ["contract_subaward"]` or `["assistance_subaward"]` for downstream amounts. Ceilings are potential capacity, and subawards are downstream activity—neither adds to prime obligations to produce an unduplicated market total. See [aggregation examples](https://govtribe.com/docs/govtribe-for-agents/guides/aggregations-and-leaderboards) and [award values and transactions](https://govtribe.com/docs/data-model/guides/federal-award-values-and-transactions).

## 3. Retrieve useful records and expand as needed

Use progressive disclosure to manage context: summarize the market, inspect focused records, then expand the records that explain the findings. Small result sets can be retrieved with rich fields immediately.

For `Search_Federal_Contract_Awards`, keep the research filters and start with:

```json
{
  "per_page": 10,
  "fields_to_return": [
    "govtribe_id", "name", "govtribe_url", "award_date",
    "dollars_obligated", "awardee", "contracting_federal_agency",
    "federal_contract_idv", "federal_contract_vehicle"
  ]
}
```

Repeat the tool with selected `govtribe_ids`, adding fields such as `descriptions`, `govtribe_ai_summary`, `set_aside_type`, `extent_competed`, `number_of_offers_received`, and `completion_date`. Apply the same approach to grants, subawards, IDVs, and vehicles using each tool's available fields.

Omitting `fields_to_return` or passing `[]` returns default fields (`govtribe_id`), not everything. To request all available fields, list them explicitly; there is no `*` selector. Field selection controls detail per record; `page` and `per_page` control how many records are retrieved. See [Manage search context](https://govtribe.com/docs/govtribe-for-agents/guides/manage-search-context).

For file analysis, use the host's file tools to write retrieved rows as CSV or JSON. `total` caps at 10,000; split searches reaching that cap into nonoverlapping date windows with fewer than 10,000 matches. Retrieve every page per window, deduplicate by `govtribe_id` (transaction IDs for transactions), and reconcile each window with its `total`.

## 4. Check current agency demand

For agency research, always run [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool) with the resolved `federal_agency_ids` and work filters. Find open solicitations using `opportunity_states: ["Posted", "Updated"]`, `opportunity_types: ["Solicitation"]`, and `due_date_range` starting today. Also check recent pre-solicitations and [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool); include [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool) when the agency's grant activity is relevant. Report upcoming work, dates, set-asides, and connections to past awards.

For vendor profiles, skip this step unless the user also wants opportunities to pursue. Current agency demand is a separate finding alongside historical activity.

## 5. Compare periods using the same scope

- For the default 24-month window, compare two consecutive 12-month periods. For a requested window, compare equal periods that together cover the requested dates, without adding dates outside it. For example, compare FY2024 (`2023-10-01`–`2024-09-30`) with FY2025 (`2024-10-01`–`2025-09-30`). For year-to-date comparisons, use the same elapsed interval in the prior year.
- For awards made in each period, repeat the award searches with `award_date_range` changed and other filters fixed. Compare record counts, reported award values, leading vendors/agencies, set-aside shares, and IDV/vehicle use.
- For spending activity during each period, use `Search_Federal_Transactions` with `transaction_date_range`, the same buyer/work filters, and matching `transaction_types`; omit the earlier `award_date_range`. Prime contracts use `["contract_award", "contract_idv"]`. Request `dollars_obligated_stats`, `positive_value_stats`, and `negative_value_stats` with `per_page: 0`. A modification to an older award belongs in that period's transactions, even though the award falls outside its award-date range.
- Report absolute change, percentage change `(recent - prior) / prior * 100` for a meaningful nonzero baseline, and percentage-point changes in shares. Transaction totals include negative adjustments and any reported non-federal amounts; label the value basis and keep transaction totals separate from award-summary obligations. See [transaction date and value examples](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool).

## 6. Explain the buying pattern

Turn the evidence into business implications. For example:

- If three vendors hold 70% of obligations, describe a concentrated supplier base and identify those vendors.
- If most award value comes through IDV orders, identify the vehicles and holders that shape market access.
- If one large award drives apparent growth, show its contribution and explain how the remaining market changed.

Check representative descriptions for unrelated work. Explain gaps that limit a conclusion, such as few awards or missing subaward amounts.

## Report

Produce a business-analyst-style GovCon report suited to the user's decision. Explain the market structure, funding channels, key buyers/vendors, changes over time, and implications for competition or market access, plus current demand for agency research. Include supporting record links, scope, dates, and relevant coverage gaps; choose the format that best communicates the findings.
