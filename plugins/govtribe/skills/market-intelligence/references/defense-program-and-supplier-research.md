---
title: Defense Program and Supplier Research
description: Trace a defense program's awarded work, suppliers, government offices and ordering paths, distinguishing direct relationships from inferred connections.
---

# Defense Program and Supplier Research

## 1. Resolve the program and work of interest

Reuse the selected program or resolve it with [Search_Major_Defense_Programs](https://govtribe.com/docs/govtribe-for-agents/tools/search-major-defense-programs-mcp-tool), using its name, code or acronym. Read the description and summary for mission, systems and work terms; check the underlying source for material technical or acquisition claims. Distinguish the overall program from a component, platform variant or specific requirement.

## 2. Trace program awards and ordering paths

Retrieve awards with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) using `dod_acquisition_program_ids` set to the resolved program ID. The program row does not contain an award list. Inspect descriptions, awardees, funding and contracting offices, dates and direct instrument relationships. Retrieve all relevant pages before claiming a complete supplier or award inventory.

When ordering paths or spending activity matter, use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with `major_defense_program_ids`, `transaction_types: ["contract_award", "contract_idv"]` and the requested `transaction_date_range`. Summarize contract activity and recurring IDVs/vehicles, then use [Federal Contract and Vehicle Research](federal-contract-and-vehicle-research.md) to resolve those instruments. These are award-family and transaction relationships, not nested fields on the program row.

Use [Federal Buying Pattern Analysis](federal-buying-pattern-analysis.md) for value bases, period comparisons and avoiding duplicate amounts. A program's budget, award obligations and vehicle ceilings answer different questions.

If direct relationships leave a gap, search distinctive program/system terms or use the documented [similar-record search](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records) with the `dod_acquisition_program` target. Verify candidate scope in its sources and keep inferred program connections separate from direct linkage.

## 3. Establish supplier and product roles

Use [Search_Federal_Contract_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-sub-awards-mcp-tool) with the selected `federal_contract_award_ids` to find reported downstream work. Resolve supplier identities through [Company Profiles and Diligence](company-profiles-and-diligence.md). Separate the direct awardee, reported subcontractor, manufacturer, reseller and other claimed contributors.

Follow exact system, part or product identifiers into award documents, official program material and supplier disclosures. Attach each material supplier/product relationship to the evidence establishing its role, scope and date. A program-associated award does not prove that the recipient makes every component; absent subaward records do not establish an empty supply chain. Distinguish a vendor specification claim from confirmed specifications and tested compatibility.

## 4. Investigate funding, future work or offices as requested

Use [Federal Budget Data](federal-budget-data.md) for appropriations, quantities and acquisition plans. Use [Federal Procurement Status and History](federal-procurement-status-and-history.md) for a related forecast or notice, and [Find Federal Recompete Opportunities](find-federal-recompete-opportunities.md) for supported follow-on research. Derive office and contact context from the selected records; use [contact research](federal-buying-pattern-analysis.md#when-research-starts-from-a-contact) to verify a person's role.

Explain which relationships are established and what remains uncertain. Use `govtribe-capture-workflows` for competitor, partner or pursuit decisions and `govtribe-pricing-data` when the question turns to comparable prices or delivery costs.
