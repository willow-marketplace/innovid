---
title: Acquisition Target Scan
description: Find and compare GovCon companies that fit an acquisition thesis.
---

# Acquisition Target Scan

Use the requested dates; otherwise compare the last three completed federal fiscal years plus matching year-to-date periods, and examine contract expirations over the next 24 months.

## 1. Translate the thesis into search criteria

Identify the capabilities, customers, geography, scale and ownership preferences that matter. Distinguish a standalone platform from an add-on supplying a specific capability or customer relationship. Separate required characteristics and exclusions from preferences that improve fit; use these to define target profiles before naming companies.

## 2. Find companies through capabilities and awarded work

Combine [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool) capability searches with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) searches by requirement, agency, NAICS/PSC category or program. Use awardee aggregations and returned records to build the candidate list. Registered classifications help discovery; inspect descriptions of awarded work to establish experience.

Include subcontractors through [Search_Federal_Contract_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-sub-awards-mcp-tool). Add [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool) or [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool) when relevant to the thesis. Review candidates across searches before selecting the shortlist.

## 3. Resolve identity and current ownership

Retrieve vendor `govtribe_id`, `name`, `uei`, `cage_codes`, `dba`, `division` and `parent`. Check company websites, sponsor portfolio pages, filings and acquisition announcements for current ownership. Identify independent companies, portfolio companies and business units; apply the user's exclusions to that resolved entity.

Define the registrations included in each candidate. Award `vendor_ids` can match direct and ultimate awardees; inspect `awardee` and `parent_of_awardee` to avoid overlapping parent/subsidiary totals. See [entity roles](https://govtribe.com/docs/data-model/guides/vendor-awardee-recipient-and-subcontractor-roles).

## 4. Compare the government business

Use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with `vendor_ids`, `transaction_types: ["contract_award", "contract_idv"]` and one `transaction_date_range` per period. Request `dollars_obligated_stats`, funding/contracting-agency, set-aside and vehicle aggregations. Compare spending, growth, customer concentration and channel dependence; inspect whether one contract explains most apparent growth. Check latest transaction dates before interpreting growth or decline. Follow [Federal Buying Pattern Analysis](federal-buying-pattern-analysis.md) for calculation and retrieval details.

Reported transaction values measure government-market activity, not company revenue or EBITDA. Obtain financial sources for financial screens; leave unavailable figures unresolved. Keep grants and subcontract amounts separate from prime spending.

## 5. Investigate durability and acquisition complications

For leading candidates, inspect major awards' `completion_date` and `ultimate_completion_date`, associated [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) and [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool), ordering deadlines and actual task orders. Use [Find Federal Recompete Opportunities](find-federal-recompete-opportunities.md) for contracts approaching expiry. Vehicle ceilings are capacity, not company backlog.

For subcontractor candidates, use subaward `vendor_ids` and inspect `prime_contractor` to identify dependence on particular primes. To examine subcontracting under a target's prime contracts, use its `federal_contract_award_ids`. Subaward records provide relationships and counts; use transactions for reported amounts.

Where the thesis depends on certifications or purchasing access, investigate ownership-sensitive eligibility and contract-transfer requirements using current [SBA](https://www.sba.gov/certifications/) guidance. Read two rules with `Get_Regulation_Section`, in the edition the main buyers apply: novation (codified FAR subpart 42.12) and postaward size rerepresentation after a merger or acquisition (FAR 52.219-28). Make these specific diligence questions for the proposed acquisition.

## 6. Prioritize and explain the acquisition case

Rank candidates by thesis fit, capabilities and customer access added, durability, concentration and material unknowns. Use qualitative tiers unless numerical scoring helps. Explain what each leading target adds, what could undermine the case, and which diligence question would resolve it. For example: “Adds Navy engineering work, but recent activity depends heavily on one contract approaching recompete.” Separate strategic fit from evidence of willingness to sell. Use [Company Profiles and Diligence](company-profiles-and-diligence.md) to investigate a selected company.

## Report

Produce a GovCon acquisition-target report suited to the user's decision. Explain the strongest candidates, acquisition rationale, material concerns and next diligence questions. Choose the format that best communicates the findings.
