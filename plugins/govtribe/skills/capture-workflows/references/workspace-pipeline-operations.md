---
title: Manage Pipelines and Pursuits
description: Organize GovCon pursuits from qualification through award using GovTribe pipelines, capture stages, tasks and shared records.
---

# Manage Pipelines and Pursuits

Use pipelines for capture workflows and pursuits for individual pieces of business. Apply the sections needed for the request.

## 1. Create or update a pipeline

Find pipelines with [Search_Pipelines](https://govtribe.com/docs/govtribe-for-agents/tools/search-pipelines-mcp-tool). Use one pipeline per team/process; separate pipelines when workflows differ. Use tags for agency, fiscal year or prime/sub role.

Adapt this starting structure:

| Stage | Work to manage |
| --- | --- |
| Triage | Screen new leads for basic fit and decide which deserve research. |
| Qualification | Establish eligibility, buyer need, relevant experience and a credible route to compete. |
| Capture | Develop customer knowledge, competitive position, partners, solution and pricing; decide whether to invest in a proposal. |
| Proposal | Assign requirements, prepare and review the response, and meet the submission deadline. |
| Submitted | Track receipt, amendments, discussions and the award decision. |

For a new pipeline, [Create_Pipeline](https://govtribe.com/docs/govtribe-for-agents/tools/create-pipeline-mcp-tool) requires `name` and `stageCount`, with optional `description`. This example needs four user stages; Triage, Won, Lost and Abandoned are automatic. Retrieve IDs, names and `type` with [Search_Stages](https://govtribe.com/docs/govtribe-for-agents/tools/search-stages-mcp-tool), filtered by `pipeline_ids`. Rename using [Update_Stage](https://govtribe.com/docs/govtribe-for-agents/tools/update-stage-mcp-tool), passing `pursuit_index_map: []` to preserve pursuit order; use [Create_Stage](https://govtribe.com/docs/govtribe-for-agents/tools/create-stage-mcp-tool) for additions. Retain useful existing stages. [Update_Pipeline](https://govtribe.com/docs/govtribe-for-agents/tools/update-pipeline-mcp-tool) requires `name`, including for description-only changes.

## 2. Build a useful pursuit record

Find contracts, grants and forecasts through [Relevant Opportunities](relevant-opportunities.md). Use [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool), using `pipeline_ids` and `fields_to_return` for IDs, name, stage, owner, associated records and relevant planning fields. Check linked records and scope, including closed pursuits; keep a forecast and its solicitation in the same pursuit.

[Create_Pursuit](https://govtribe.com/docs/govtribe-for-agents/tools/create-pursuit-mcp-tool) takes `stage_id` plus `linked_item_id`/`linked_item_model_class`, or `name` for an unlinked lead. Inspect auto-filled fields. [Update_Pursuit](https://govtribe.com/docs/govtribe-for-agents/tools/update-pursuit-mcp-tool) changes the description, stage and planning fields:

- Record the buyer's need, why the work fits, intended role, competitors, partners and unresolved questions.
- Keep the response deadline, expected award date and `estimated_duration` in months current. Put internal milestones on tasks.
- Explain the `estimated_value` basis in whole USD dollars, including subcontract workshare. A vehicle ceiling is not expected revenue. Set `pwin` (0–100) from the team's assessment of win prospects.

Give each pursuit an accountable owner. Owner changes and adding related records to an existing pursuit use the [pursuit page](https://govtribe.com/docs/govtribe-user-guide/capture/pursuits); the MCP update tool does not expose them.

## 3. Turn capture work into assigned actions

Use [Bid / No-Bid Review](conduct-bid-no-bid-review.md) for qualification decisions and `govtribe-proposal-workflows` for requested response preparation; use [Likely Bidders](likely-bidders.md), [Teaming](team-on-an-opportunity.md) and [Past Performance Match](past-performance-match.md) for specific research. Record the decision and reasoning in the pursuit description or a [Create_Pursuit_Discussion](https://govtribe.com/docs/govtribe-for-agents/tools/create-pursuit-discussion-mcp-tool).

Make each active pursuit's next action concrete: confirm vehicle access, validate a past-performance reference, settle partner workshare or review an amendment. Resolve existing actions with [Search_Tasks](https://govtribe.com/docs/govtribe-for-agents/tools/search-tasks-mcp-tool) and teammates with [Search_Users](https://govtribe.com/docs/govtribe-for-agents/tools/search-users-mcp-tool). [Create_Task](https://govtribe.com/docs/govtribe-for-agents/tools/create-task-mcp-tool) requires `pipeline_id`, `pursuit_id`, `owner_model_id`, `name` and `due_date`; [Update_Task](https://govtribe.com/docs/govtribe-for-agents/tools/update-task-mcp-tool) requires the task, pipeline and pursuit IDs. Reuse tasks for continuing work. Task completion uses the [Tasks interface](https://govtribe.com/docs/govtribe-user-guide/capture/tasks); MCP does not expose it.

Use [Search_Tags](https://govtribe.com/docs/govtribe-for-agents/tools/search-tags-mcp-tool) for filtering and [Search_Custom_Field_Definitions](https://govtribe.com/docs/govtribe-for-agents/tools/search-custom-field-definitions-mcp-tool) for recurring structured information. `tag_ids` replaces the full set; preserve existing tags. `custom_fields` updates named `custom_field_id`/`value` entries; `null` clears a value. Find saved files with [Search_User_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-user-files-mcp-tool) and associate them using [Update_User_File](https://govtribe.com/docs/govtribe-for-agents/tools/update-user-file-mcp-tool); a file links to one pursuit.

## 4. Review progress and retain outcomes

Review active work weekly or at the team's cadence, and after material buyer changes. Use [Review My Pipeline](review-my-pipeline.md) to identify stale dates, missing next actions and unrealistic estimates. Move pursuits when the work supports the next stage; recheck the bid decision when scope, eligibility or resources change. [Saved searches](relevant-opportunities.md#5-set-up-recurring-checks) supply new leads for Triage.

Use the actual Won, Lost or Abandoned stage with `Update_Pursuit`. Record the reason and lessons: Won for a confirmed win, Lost for an unsuccessful submitted response, Abandoned for work the team stops pursuing or a canceled procurement. An elapsed deadline alone does not establish an outcome. Keep closed records for future qualification and lessons learned.

For requested cleanup, [deleting a stage](https://govtribe.com/docs/govtribe-for-agents/tools/delete-stage-mcp-tool) or [pipeline](https://govtribe.com/docs/govtribe-for-agents/tools/delete-pipeline-mcp-tool) removes contained pursuits; [deleting a pursuit](https://govtribe.com/docs/govtribe-for-agents/tools/delete-pursuit-mcp-tool) removes its discussions, tasks and associated user-file records. Before deleting, identify the exact stage, pipeline or pursuit and the pursuits, discussions, tasks and file records it removes, and confirm that removal with the user. Move work that must remain before deleting its stage. Inspect results, retain completed changes after partial success, and summarize remaining actions.

Approach adapted from [APMP's capture planning guidance](https://apmp-western.org/wp-content/uploads/2021/04/APMP-California-Capture-Adventure-Session-1.pdf), [Shipley's federal capture guidance](https://www.shipleywins.com/training/capturing-federal-business) and [GovTribe's pipeline guide](https://govtribe.com/docs/govtribe-user-guide/capture/pipelines).
