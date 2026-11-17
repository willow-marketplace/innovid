---
title: Team On An Opportunity
description: Manage GovTribe teaming interests, match requests, conversations and teams.
---

# Team On An Opportunity

Use the section matching the requested action. For broader partner research, use [Likely Bidders](likely-bidders.md) and [Past Performance Match](past-performance-match.md).

## 1. Resolve the target and existing interest

Reuse the resolved target or a pursuit's linked record from [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool). Resolve contract opportunities with [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), grants with [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool), or an unclear record type with [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool).

Opportunity teaming uses `federal_meta_opportunity_id`; IDVs, vehicles, forecasts and awards use their own `govtribe_id`. Grant search currently omits the wrapper ID; use a verified wrapper if already available, otherwise report the missing lookup.

Use [Search_Teaming_Interests](https://govtribe.com/docs/govtribe-for-agents/tools/search-teaming-interests-mcp-tool) without `teamable_ids` to find your own interests, including hidden/withdrawn ones. Request `teamable`, `intent`, `status` and `intakeQuestions` alongside IDs; reuse the matching interest.

## 2. Discover compatible interests

Search with `teamable_ids` and the complementary `teaming_interest_intents` (`prime` or `sub`). Request `govtribe_id`, `govtribe_url`, `name`, `govtribe_ai_summary`, `can_request_match` and `match_state_for_viewer`; reuse existing matches.

Discovery hides other companies' vendor identities and intake answers. For a named partner, use their known interest ID/link. Accepted matches expose identity through `viewer_context`, rather than the nested counterparty `vendor` field.

## 3. Create or update your interest

Resolve the represented company with [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool). An existing vendor record is required; claiming its profile is not.

Reuse known answers and ask for missing intake:

- **Prime:** `capture_stage`, `sub_workshare_expectations`.
- **Sub:** `role_and_workshare`, `relevant_experience`; add capacity, location or preferences when useful.

Use [Create_Teaming_Interest](https://govtribe.com/docs/govtribe-for-agents/tools/create-teaming-interest-mcp-tool) for a new interest or [Update_Teaming_Interest](https://govtribe.com/docs/govtribe-for-agents/tools/update-teaming-interest-mcp-tool) for name, intake or tags. Intake/tag updates replace the entire set; include all answers/tags to retain. Intent, vendor and target cannot be changed through Update.

## 4. Request matches and use conversations

Use [Request_Teaming_Match](https://govtribe.com/docs/govtribe-for-agents/tools/request-teaming-match-mcp-tool) with your existing open interest, or [Create_Interest_And_Request_Match](https://govtribe.com/docs/govtribe-for-agents/tools/create-interest-and-request-match-mcp-tool) when creating your interest and approaching a selected partner together. Both sides need the same target and opposite intents.

Find inbound requests with [Search_Teaming_Matches](https://govtribe.com/docs/govtribe-for-agents/tools/search-teaming-matches-mcp-tool), `my_pending_inbound: true`. Request `govtribe_id`, `govtribe_url`, `status`, `viewer_context`, `prime_teaming_interest` and `sub_teaming_interest`. Listing requests does not authorize a response: explain these effects and call [Respond_To_Teaming_Match](https://govtribe.com/docs/govtribe-for-agents/tools/respond-to-teaming-match-mcp-tool) to accept or decline only when the user asks for or approves that response. Acceptance reveals company identity and declines the sub interest's other pending matches. Decline prevents that requester from requesting the same target interest again.

Read with [Get_Teaming_Messages](https://govtribe.com/docs/govtribe-for-agents/tools/get-teaming-messages-mcp-tool); search topics across an interest's conversations with [Search_Teaming_Messages](https://govtribe.com/docs/govtribe-for-agents/tools/search-teaming-messages-mcp-tool). Clarifying messages can precede acceptance. [Send_Teaming_Message](https://govtribe.com/docs/govtribe-for-agents/tools/send-teaming-message-mcp-tool) sends the user's approved text and notifies participants. Use `teaming_match_id` for a match conversation or the prime's `teaming_interest_id` for the team hub.

## 5. Manage interests and teams

| Action | Tool and effect |
| --- | --- |
| Pause discovery | [Hide_Teaming_Interest](https://govtribe.com/docs/govtribe-for-agents/tools/hide-teaming-interest-mcp-tool) hides an open interest; existing matches remain. |
| Resume discovery | [Reopen_Teaming_Interest](https://govtribe.com/docs/govtribe-for-agents/tools/reopen-teaming-interest-mcp-tool) reopens hidden/withdrawn interests; cancelled matches stay cancelled. |
| Withdraw an interest | [Withdraw_Teaming_Interest](https://govtribe.com/docs/govtribe-for-agents/tools/withdraw-teaming-interest-mcp-tool) cancels its pending matches. |
| Cancel or leave a match | [Withdraw_Teaming_Match](https://govtribe.com/docs/govtribe-for-agents/tools/withdraw-teaming-match-mcp-tool): requester cancels a pending request; either participant can leave an accepted match. |
| Finalize a team | [Lock_In_Teaming_Team](https://govtribe.com/docs/govtribe-for-agents/tools/lock-in-teaming-team-mcp-tool): prime with accepted partners opens the hub and withdraws all pending matches on that prime interest. |
| End a team | [Disband_Teaming_Team](https://govtribe.com/docs/govtribe-for-agents/tools/disband-teaming-team-mcp-tool) ends accepted and pending matches, withdraws the prime and closes the hub; subs return to open. |
| Record feedback | [Submit_Teaming_Match_Feedback](https://govtribe.com/docs/govtribe-for-agents/tools/submit-teaming-match-feedback-mcp-tool) records private account feedback, replacing that account's prior feedback. |
