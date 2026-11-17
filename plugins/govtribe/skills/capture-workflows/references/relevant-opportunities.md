---
title: Relevant Opportunities
description: Find and qualify government opportunities and forecasts using company experience, capabilities and pipeline context; set up saved searches for ongoing monitoring.
---

# Relevant Opportunities

## 1. Build the company profile from its work

Reuse established company research and fill only material gaps. For new research, use the past five years of awards, plus older active work and held vehicles, unless the user specifies another period.

- Resolve the company with [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool). Review identity, registrations, `sba_certifications`, `business_types` and certification dates. Supplement with the company website and supplied context.
- Review [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool), [Search_Federal_Contract_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-sub-awards-mcp-tool) and [Search_Federal_Grant_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-sub-awards-mcp-tool) using `vendor_ids`. Use representative scopes across buyers and work types, dates, scale and prime/subrecipient roles to identify demonstrated capabilities beyond registered classifications. Confirm the company is the subrecipient in subaward records.
- Retrieve held [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) and [Search_Federal_Contract_Vehicles](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-vehicles-mcp-tool) with `vendor_ids`; check holder identity and ordering dates for usable access.
- For the company's workspace, resolve its [Search_Pipelines](https://govtribe.com/docs/govtribe-for-agents/tools/search-pipelines-mcp-tool) and [Search_Stages](https://govtribe.com/docs/govtribe-for-agents/tools/search-stages-mcp-tool). Use [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool) with `pipeline_ids`/`stage_ids` to review active, won, lost and abandoned pursuits. Read relevant [Search_Pursuit_Discussions](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuit-discussions-mcp-tool) and [Search_Pursuit_Comments](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuit-comments-mcp-tool) for reasons, constraints and buyer experience; corroborate reported wins with linked awards.
- Review a reasonable selection of relevant, already-visible company files, focusing on gaps in capabilities and past performance. For indexed files, scope [Search_User_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-user-files-mcp-tool) to their `user_file_ids` with the keyword query `quals | qualification* | "past performance" | "capability statement"`. Read names/snippets before contents; read supplied files directly. Skip when no relevant files are visible.

## 2. Set the scope and dates

Establish capabilities sought, buyers, geography, eligibility, value/scale, vehicle access and response time. Resolve buyers with [Search_Federal_Agencies](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-agencies-mcp-tool) or [Search_Jurisdictions](https://govtribe.com/docs/govtribe-for-agents/tools/search-jurisdictions-mcp-tool); resolve grant programs with [Search_Federal_Grant_Programs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-programs-mcp-tool). Use documented classification-code filters.

Search open opportunities from today (`due_date_range.from: "now/d"`); apply required preparation time to the earliest mandatory deadline. Use `posted_date` for requested recency and check rolling/undated calls separately. Also search forecasts for the next 24 months: `{"from":"now/d","to":"now+24M/d"}`, unless the user specifies another horizon or only wants open bids.

### Starting from a pipeline

For seed/grow requests, use the pipeline's `name`, `description` and `tags`, plus existing pursuits and relevant outcomes, to set its intended market. Request these through the workspace searches in step 1 using `fields_to_return`, including pursuit `stage` and `associated_records`. An empty pipeline can start from its description and known company capabilities.

After steps 3–4, compare candidates with active and relevant closed pursuits by linked records and business scope. Keep the same acquisition's forecast and solicitation together. Recommend suitable additions to Triage with a next qualification action; use [Manage Pipelines and Pursuits](workspace-pipeline-operations.md) when the user asks to add them.

## 3. Find and expand candidates

- [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool): use `opportunity_states: ["Posted", "Updated"]`, dates, buyers, NAICS/PSC, set-asides, geography and vehicle access. Use `data_sources` only when the user asks for or against a source, or asks for SBIR or STTR topics. For those topics, search `data_sources: ["DoD SBIR/STTR (DSIP)"]` and add `research_phase` (which also separates SBIR from STTR), `technology_areas`, `focus_areas`, `itar` or `cmmc_levels` as needed. DSIP covers DoD only and only its topics carry those five fields, so unless the user limits the request to DoD, also search SAM.gov by keyword without them, check the phase in the returned records, and say which sources you searched. If the user names another source, use only that source, without those fields.
- [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool): use jurisdictions, classifications, scope and due dates; verify current status from the record/source.
- [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool): use `opportunity_types: ["Posted"]`, dates, programs, `applicant_types` and `funding_instrument_types`. Check source eligibility, including for `Other` applicant classifications.
- **Forecasts:** use [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool) with `estimated_solicitation_release_date_range` or `anticipated_award_start_date_range`. For grants, use `Search_Federal_Grant_Opportunities` with `opportunity_types: ["Forecasted"]` and `forecast_posting_date_range` or `forecast_due_date_range`. Apply the forecast window in separate queries for each date field and merge by `govtribe_id`.

Use progressive disclosure: request IDs, names, URLs, deadlines, scope and eligibility through `fields_to_return`, then retrieve fuller records and source files for promising matches. Use aggregations to narrow broad results; broaden thin results with documented `similar_filter` or semantic search while retaining essential constraints.

Use `govtribe-market-intelligence`’s **Federal Buying Pattern Analysis** for buyer/market context and **Find Early Federal Procurement Signals** for deeper forecast, follow-on and funding research.

## 4. Assess fit and recommend next actions

Compare scope and delivery needs with actual award experience, certifications, access, capacity, location and timing. Verify status and timing against current source documents; explain strong matches and material gaps. Keep credible partner-dependent opportunities; use [Likely Bidders](likely-bidders.md) and [Past Performance Match](past-performance-match.md) to assess complementary partners. Carry forward relevant loss/abandonment reasons; distinguish missing information from a confirmed mismatch. Use [Bid / No-Bid Review](conduct-bid-no-bid-review.md) for a pursuit decision.

Prepare an appropriate GovCon report distinguishing open bids from forecasts, with recommended opportunities, source links, fit rationale, material gaps and next actions. Let the requested use determine the format.

## 5. Set up recurring checks

For ongoing monitoring:

1. Find existing searches with [Search_Saved_Searches](https://govtribe.com/docs/govtribe-for-agents/tools/search-saved-searches-mcp-tool), using the relevant `scope`. Request `govtribe_id`, `name`, `q`, `filters`, `sorts`, `searches_govtribe_type`, `owner`, `frequency` and `govtribe_url` through `fields_to_return`. Compare owner and criteria to avoid duplicates.
2. Reuse a step 3 `search_results_id` whose `search_results_id_can_generate_saved_search` is true. Use rolling date filters for ongoing monitoring; rerun with revised dates if needed. Keep separate monitors for distinct search types/date branches.
3. Reuse an equivalent saved search. Updating replaces an existing search's criteria and changes its alerts, so refine one only when the user asks for or approves that replacement: call [Update_Saved_Search](https://govtribe.com/docs/govtribe-for-agents/tools/update-saved-search-mcp-tool) with its `govtribe_id` as `saved_search_id` and the new `search_results_id`. Otherwise call [Create_Saved_Search](https://govtribe.com/docs/govtribe-for-agents/tools/create-saved-search-mcp-tool) with `search_results_id`, a descriptive `name` and `frequency`.
4. Use the requested email cadence: `Instant`, `Daily`, `Weekly`, or `Never` for no email. Preserve existing cadence unless changing it is requested. Return the saved-search link. These are GovTribe email alerts; recurring agent analysis uses a separate host schedule.
