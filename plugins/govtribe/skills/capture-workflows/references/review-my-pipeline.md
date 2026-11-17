---
title: Review My Pipeline
description: Review GovTribe pipelines for capture progress, deadlines, estimates, outcomes and relevant opportunities missing from the pipeline.
---

# Review My Pipeline

## 1. Load the pipeline and its work

Reuse known context or find the requested pipeline with [Search_Pipelines](https://govtribe.com/docs/govtribe-for-agents/tools/search-pipelines-mcp-tool), requesting `govtribe_id`, `name`, `description` and `tags` through `fields_to_return`. Retrieve its actual stage `govtribe_id`, `name`, `description` and `type` through [Search_Stages](https://govtribe.com/docs/govtribe-for-agents/tools/search-stages-mcp-tool), using `pipeline_ids` and `fields_to_return`.

Use [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool) with `pipeline_ids`. Start with active work by excluding terminal `stage_ids` using `stage_ids_operator: "not_in"`. Request IDs, links, `name`, `description`, `stage`, `owner`, `associated_records`, `due_date`, `estimated_award_date`, `estimated_duration`, `estimated_value`, `pwin` and `updated_at` through `fields_to_return`. Follow pagination for the requested scope. Inspect relevant closed pursuits for duplicate work, outcomes or lessons.

## 2. Check commitments and blockers

Use [Search_Tasks](https://govtribe.com/docs/govtribe-for-agents/tools/search-tasks-mcp-tool), scoped by `pipeline_ids` or `pursuit_ids`, with `fields_to_return` for IDs, links, `name`, `comment`, `owner`, `due_date`, `priority`, `completed_at`, `reopened_at` and `pursuit`. Check `completed_at` before flagging overdue work; `reopened_at` is history. Identify unfinished tasks past their due date, conflicting deadlines and active pursuits without an assigned next action.

Read relevant [Search_Pursuit_Discussions](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuit-discussions-mcp-tool) by `pursuit_ids`, projecting `govtribe_id`, `name`, `message`, `comments_count` and `updated_at`. Retrieve replies with [Search_Pursuit_Comments](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuit-comments-mcp-tool), passing discussion `govtribe_id` values as `discussion_ids` and requesting `message`, `creator`, `created_at` and `updated_at`. Look for bid decisions, buyer feedback, partner commitments and blockers. A recent edit alone does not establish progress.

## 3. Assess progress against the stage and procurement

Refresh linked records when current buyer information could change the recommendation. Use [Search_Federal_Forecasts](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-forecasts-mcp-tool), [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool) or [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool) with the linked `govtribe_ids`. Request each tool's supported status, date, scope and source fields through `fields_to_return`; check current deadlines and amendments. For award evidence, use [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool) or [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool), requesting scope, dates and recipient fields to match the work and recipient.

Apply these expectations to the team's actual stages:

| Stage | What to assess |
| --- | --- |
| Triage / Qualification | Fit, eligibility, vehicle access where required, and the next qualification question. |
| Capture | Buyer knowledge, competitive position, partners, solution and whether to invest in a proposal. |
| Proposal | Assigned response work, reviews, current requirements and submission readiness. |
| Submitted | Submission receipt, buyer follow-up and actual award outcome. |

Check estimate assumptions: `estimated_value` is whole USD dollars, duration is months and `pwin` is the team's win assessment. Distinguish subcontract workshare from total contract value; a vehicle ceiling is not expected revenue. Missing precise dates or values may be reasonable for an early forecast. Use [Bid / No-Bid Review](conduct-bid-no-bid-review.md) when scope, eligibility or available resources change the pursue decision.

## 4. Find missing opportunities

Use the pipeline's name, description, tags, pursuits and relevant outcomes to establish its intended market. Carry known company capabilities and constraints into [Relevant Opportunities](relevant-opportunities.md) to find matching opportunities and forecasts, reusing completed research.

Compare candidates with active and relevant closed pursuits by linked records and business scope. Keep the same acquisition's forecast and solicitation together; account for still-relevant loss or abandonment reasons.

Recommend suitable additions to Triage with fit and a next qualification action. When adding work is requested, use [Manage Pipelines and Pursuits](workspace-pipeline-operations.md) to create linked pursuits in the resolved Triage stage.

## 5. Recommend practical next actions

Prioritize approaching deadlines and blocked work, then unsupported estimates, duplicate pursuits and stage/outcome corrections. For each material issue, explain the finding and useful next action, including an owner or date when known.

Recommend Won for a confirmed win, Lost for an unsuccessful submitted response, and Abandoned for work the team stops pursuing or a canceled procurement. Verify the outcome concerns this pursuit and the team's role; a linked incumbent award or elapsed deadline alone is insufficient.

Produce an appropriate GovCon pipeline review. Use [Manage Pipelines and Pursuits](./workspace-pipeline-operations.md) when the user wants the recommendations applied.
