---
title: Federal Procurement Status and History
description: Trace a selected federal notice or forecast through amendments, posted solicitations, cancellations, awards and unresolved outcomes.
---

# Federal Procurement Status and History

## 1. Read the selected record

Reuse the active target and establish whether the user wants current status or history as of a particular date. Resolve unfamiliar identifiers or links with [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool), then retrieve the exact record with [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool) or [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool). Use `govtribe_ids`, or `solicitation_numbers` for known solicitations, without date filters that could hide the selected record.

Read the requirement, buyer, source URL, relevant dates and direct relationships. Distinguish a notice's response deadline from its posting date and a forecast's estimated release/award timing. Preserve the source's date precision and estimated value range.

## 2. Trace notice history

Follow the [notice-chain guidance](https://govtribe.com/docs/data-model/guides/federal-contract-opportunity-notice-chains). For a non-MAS notice with `federal_meta_opportunity_id`, retrieve related opportunities using `federal_meta_opportunity_ids`. Compare the selected notice, oldest useful scope/attachments and later notices. Include amendments, cancellations and award notices when establishing the outcome; the newest solicitation alone may not resolve it. Compare dates, scope, set-aside, vehicle and amendment text to establish which instructions control.

When `part_of_mas` is true, its chain may contain unrelated schedule context. Prefer the selected notice's direct relationships, identifiers and files. Missing chain IDs do not prevent identifier-based research. RFI and Sources Sought are source terminology; Sources Sought maps to the supported `Pre-Solicitation` type rather than a separate filter enum.

A past response deadline does not establish cancellation, award or abandonment. For closed market research, explain what the government was testing—capability, capacity, set-aside, vehicle access, commercial availability, pricing structure or acquisition method—and identify supported next-stage signals. Do not suggest an immediate response to an expired window.

## 3. Check a forecast's progression

Forecasts expose neither a direct opportunity-chain link nor attached `government_files`. Compare agency, requirement/program wording, dates, location and contacts with posted notices and their source documents. Search identifiers and distinctive wording first; use [similarity](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records) when these are insufficient. Include already-issued or closed notices when the estimated release date has passed.

The forecast lane changes the next step:

- **Recompete:** resolve possible predecessor work and use [Find Federal Recompete Opportunities](find-federal-recompete-opportunities.md) to verify succession.
- **Exercise of Option:** resolve the current award and examine [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with its `federal_contract_award_ids`, plus source documents, for actual option or extension evidence. A forecast alone does not establish exercise.
- **New Requirement:** look for posted notices or changed scope; adjacent work supplies context, not an assumed incumbent.

When portfolio context matters, search nearby forecasts from the same agency and relevant timing window, excluding the target. Use [Early Federal Procurement Signals](find-early-federal-procurement-signals.md) for broader discovery. Resolve surfaced award, vendor and parent-instrument IDs before widening research.

## 4. Establish award-side outcomes and fill gaps

Opportunity rows do not contain their award/IDV lists. Retrieve them with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) and [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool), using `federal_contract_opportunity_ids` for the selected notice, including MAS. Use `federal_meta_opportunity_ids` when researching the wider, appropriate notice chain. Review relevant remaining pages. A linked record may be a new award or vehicle holder rather than the predecessor.

If links are empty or ambiguous, follow contract numbers and requirement references from descriptions/files; constrain further searches by relevant buyer, program, site, vehicle or scope. Check official agency and SAM.gov sources when necessary. Label semantic matches as possible related work until source evidence establishes the relationship. State which exact linkage was checked; an empty result does not establish that no related work exists. A successor can have a different solicitation number and chain.

Use [Federal Contract and Vehicle Research](federal-contract-and-vehicle-research.md) for awarded-instrument detail. Use `govtribe-capture-workflows` when the question requires proving the current incumbent or assessing company pursuit choices.

## 5. Resolve material source details

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) with the relevant notice IDs in `federal_contract_opportunity_ids`. Read originals through native file tools or follow [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval). Earlier notices may hold decisive attachments. Inspect scope, amendments, Q&A, acquisition schedules and evaluation or pricing instructions as needed; disclose missing material file coverage.

Explain the supported chronology and status with source links, distinguishing confirmed events, inferred connections and unresolved outcomes. Keep historical conclusions tied to their requested date. Use `govtribe-proposal-workflows` for response preparation, `govtribe-pricing-data` for price/cost analysis, and Capture for shaping or bid decisions while preserving the selected target and findings.
