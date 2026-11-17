---
title: Find Early Federal Procurement Signals
description: Find upcoming federal contracts and grants through forecasts, notices, expiring contracts, recurring programs, and funding plans.
---

# Find Early Federal Procurement Signals

## 1. Define the agency, work, and timeframe

For cross-government research, start with topic terms and synonyms; identify buyers from the results. Resolve agencies or contract categories when needed with [Search_Federal_Agencies](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-agencies-mcp-tool), [Search_Naics_Categories](https://govtribe.com/docs/govtribe-for-agents/tools/search-naics-categories-mcp-tool), or [Search_Psc_Categories](https://govtribe.com/docs/govtribe-for-agents/tools/search-psc-categories-mcp-tool). For grants, use [Search_Federal_Grant_Programs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-programs-mcp-tool) and scope by program, topic, and applicant eligibility. Reuse supplied record IDs.

Use the requested dates; otherwise apply these windows:

- **Upcoming:** today through the next 24 months: `{"from": "now/d", "to": "now+24M/d"}`.
- **Recent:** the previous six months through now: `{"from": "now-6M/d", "to": "now"}`.
- **History:** the last three completed U.S. federal fiscal years. Calculate explicit October 1–September 30 bounds; exclude the current partial fiscal year.

See [date filtering](https://govtribe.com/docs/govtribe-for-agents/guides/date-filtering).

## 2. Find planned contracts and grants

**Contracts:** use [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool) using topic wording; add `federal_agency_ids` or NAICS when narrowing.

- Use the upcoming window for expected releases (`estimated_solicitation_release_date_range`) or awards (`anticipated_award_start_date_range`).
- Request `govtribe_id`, `name`, `govtribe_url`, `source_url`, `forecast_type`, `estimated_solicitation_release_date`, `estimated_award_start_date`, `estimated_award_value`, `set_aside`, and `federal_agency` in `fields_to_return`. Preserve estimated value ranges; expand promising records with `descriptions` and `points_of_contact`.
- For broad searches, use `top_federal_agencies_by_doc_count`, `top_naics_codes_by_doc_count`, or `top_set_aside_types_by_doc_count` to identify concentrations.

**Grants:** use [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool) with `opportunity_types: ["Forecasted"]`, scoped by `federal_agency_ids`, `federal_grant_program_ids`, topic, or `applicant_types`. Use the upcoming window for expected announcements (`forecast_posting_date_range`) or application deadlines (`forecast_due_date_range`). Retrieve `forecast_posting_date`, `forecast_due_date`, `applicant_types`, `funding_instruments`, `award_floor`, `award_ceiling`, `description`, `govtribe_url`, and `source_url`.

For either forecast search, run release and deadline/award windows separately and merge by `govtribe_id` when either date qualifies.

## 3. Find notices and check current status

Use [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool):

- Early notices: `opportunity_types: ["Pre-Solicitation", "Special Notice"]`, with `posted_date` in the recent window. Search RFI and sources-sought wording in `query`; neither is an opportunity-type enum. Early notices may lack due dates.
- Active solicitations: `opportunity_types: ["Solicitation"]`, `opportunity_states: ["Posted", "Updated"]`, and `due_date_range` in the upcoming window.

Request `govtribe_id`, `name`, `govtribe_url`, `source_url`, `solicitation_number`, `opportunity_type`, `opportunity_state`, `posted_date`, `due_date`, `federal_agency`, and `federal_meta_opportunity_id`. For broad results, `posted_date_histogram`, `due_date_histogram`, and `top_opportunity_states_by_doc_count` summarize timing and status.

For posted grants, use `Search_Federal_Grant_Opportunities` with `opportunity_types: ["Posted"]` and `due_date_range` in the upcoming window. Check eligibility and application instructions in the announcement.

## 4. Identify follow-on contracts and recurring grants

Search the upcoming window with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) using `ultimate_completion_date_range`, and [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) or [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool) using `last_date_to_order_range`.

Search the web by contract/solicitation number, program, agency, and incumbent for agency forecasts, SAM.gov notices, industry-day materials, extensions, and successor plans. Use these sources to establish whether expiring work will continue. See [Find Federal Recompete Opportunities](./find-federal-recompete-opportunities.md).

For recurring grants, use [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool) with `federal_grant_program_ids` and `award_date_range` in the history window to examine previous recipients and their awards. Use `Search_Federal_Grant_Opportunities` with the same program IDs and no date/type filters to check the latest announcement, including closed rounds, for future funding or competitions.

Look for industry-specific demand signals, such as equipment replacement, product end-of-support, software renewal or migration, service bridges, and funded project phases.

## 5. Read source material and connect related records

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) with `federal_contract_opportunity_ids` or `federal_grant_opportunity_ids` to find attachments. Read original files through `download_url` with native file tools, or follow [vector-store content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval). Look for scope, eligibility, response dates, acquisition schedules, and funding details.

Group notices for the same requirement or event even when their IDs differ; retain distinct deadlines. Connect contract notices through `federal_meta_opportunity_ids` and solicitation numbers; retrieve related awards/IDVs with `federal_contract_opportunity_ids`. Contract forecasts lack direct notice/award links: compare agency, requirement text, dates, and source documents. Use [similarity](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records) or semantic queries when links or wording are insufficient. For grants, a shared program connects funding history; confirm any specific opportunity-to-award relationship from source documents.

## 6. Explain funding, timing, and implications

Use [Federal Budget Data](./federal-budget-data.md) for official funding sources.

For DoD requests, use [Search_Major_Defense_Programs](https://govtribe.com/docs/govtribe-for-agents/tools/search-major-defense-programs-mcp-tool) for relevant acquisition context and review official budget justification books for funding changes, new initiatives, planned solicitations/awards, and acquisition milestones. Use the latest available publication cycle for fiscal years covering the upcoming window; compare those same fiscal years with the previous cycle. Retain units and distinguish actual, enacted, requested, and projected funding. Retrieve budget amounts from the source books.

For grants, distinguish `award_floor`/`award_ceiling` per award from total available funding and expected award counts in the announcement. Historical obligations describe money already awarded.

Search reporting with [Search_Government_Related_News_Articles](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-related-news-articles-mcp-tool), using relevant terms in `query` and `date_published` in the recent window. Connect funding decisions to announced procurement or grant schedules.

## Report

Produce a GovCon business report organized by requirement or funding program. Explain current stage, expected release/award timing or application deadline, value range, incumbent/vehicle or applicant eligibility, and useful next action. Preserve original sources' date precision (year, quarter, or day) and links. Identify gaps affecting conclusions.

For a contract-spending estimate over the next 24–36 months, use [Spending Forecast](./spending-forecast.md) to turn historical obligations and known changes into scenarios.
