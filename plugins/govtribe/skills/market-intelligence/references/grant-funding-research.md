---
title: Grant Funding Research
description: Research a federal grant opportunity, award or program through eligibility, source requirements, recipients and funding history.
---

# Grant Funding Research

## 1. Resolve the grant record

Retrieve the supplied record with [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool), [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool), or [Search_Federal_Grant_Programs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-programs-mcp-tool). Use `govtribe_ids` for known records; search source numbers or titles with `query`, checking the agency and description before choosing a match. Retrieve the starting record without date restrictions.

Retain the announcement number, award `grant_number`, or program Assistance Listing Number (ALN, formerly CFDA) alongside its GovTribe ID. These identify different records. Establish the assistance instrument: grants, cooperative agreements and other financial assistance have different terms. See the [grant data model](https://govtribe.com/docs/data-model/guides/federal-grant-data-model).

## 2. Follow the relevant branch

- **Opportunity:** establish posted versus forecast status, application deadline, applicant types, geography and project period. Check the latest announcement and amendments for eligibility, registration, cost share, scoring, required forms, reporting and submission instructions. Separate confirmed eligibility blockers from unanswered questions. Keep per-award floor/ceiling, total available funding and expected award count distinct; a forecast or historical appropriation does not guarantee current funding.
- **Award:** identify the exact recipient, funding agency, assistance type, purpose, place of performance and project period, including extensions. Separate the recipient from its parent or affiliates. Read federal and non-federal obligations separately and distinguish accumulated award funding from an individual funding action or required cost share. Investigate funding changes or subrecipients when they answer the question.
- **Program:** establish purpose, administering agency, statutory context, assistance types and eligible recipients. Use `federal_grant_program_ids` with the opportunity and award tools to examine connected announcements and awards. Separate forecast, open and closed rounds; use the requested dates for historical comparisons. Read official program material when structured descriptions do not establish the rules.

## 3. Read sources and verify relationships

Find announcement attachments with [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) using `federal_grant_opportunity_ids`. Read originals through `download_url` with native file tools, or follow [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval). Check Grants.gov and agency sources when terms, amendments or linkage remain unclear.

A shared ALN or program establishes funding context, not proof that an award came from a particular announcement. Verify that relationship from source identifiers and documents. Compare prior recipients by program, recipient type, geography, funding scale and period; past receipt alone does not establish eligibility or intent to apply again.

For funding changes, use [Search_Federal_Transactions](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-transactions-mcp-tool) with `transaction_types: ["assistance_award"]` and relevant program, recipient or agency filters. Match each returned `parent_record` to the resolved award before attributing its activity. Use `transaction_date_range` for funding activity during a period. Keep signed changes, federal/non-federal amounts and award summaries distinct; follow [award values and transactions](https://govtribe.com/docs/data-model/guides/federal-award-values-and-transactions).

For reported pass-through relationships, use [Search_Federal_Grant_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-sub-awards-mcp-tool) with the prime's `federal_grant_award_ids`. Keep prime recipients and subrecipients separate; downstream amounts do not add to prime obligations as new funding.

## 4. Extend the research for the decision

Use [Federal Buying Pattern Analysis](./federal-buying-pattern-analysis.md) for recipient and funding comparisons, [Early Federal Procurement Signals](./find-early-federal-procurement-signals.md) for funding discovery, and [Find Federal Recompete Opportunities](./find-federal-recompete-opportunities.md) for new rounds, competitive renewals or noncompeting continuations. Label inferred recurrence; historical awards do not establish a future competition. Use `govtribe-capture-workflows` for pursuit or partner decisions and `govtribe-proposal-workflows` for an application or response package.
