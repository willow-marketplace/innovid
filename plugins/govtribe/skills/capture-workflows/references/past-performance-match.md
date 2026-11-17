---
title: Past Performance Match
description: Find a company's most relevant past performance for a government requirement and explain its strengths and gaps.
---

# Past Performance Match

## 1. Identify the company and requirement

Reuse known company and opportunity context. Resolve a supplied pursuit with [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool), the company with [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool), and the linked or supplied target with [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool), or [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool). Read a supplied requirement directly. Ask for missing context that changes the comparison; continue with supplied company references when no vendor record resolves.

## 2. Read the buyer's past-performance requirements

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) for the solicitation or funding notice, amendments and Q&A. Establish scope, evaluation criteria, reference count, recency, permitted performing entities/roles and required forms. Compare file IDs, posting dates and amendment text to identify the controlling version. Read full files with native tools or [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval) when snippets are insufficient.

Use the buyer's performance window; otherwise review the past five years, including older awards with work performed during that period. Check performance dates rather than filtering only by award date.

## 3. Find comparable company work

Search federal awards using `vendor_ids` and the requirement's substantive work:

- [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) for prime contracts and delivered orders.
- [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool) for funded projects.
- [Search_Federal_Contract_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-sub-awards-mcp-tool) and [Search_Federal_Grant_Sub_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-sub-awards-mcp-tool) for subcontract/subrecipient experience; verify which side is the company.

For state/local work, use [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool) with scope, buyer, state or classifications; verify the company in `awardee_name`. `contracting_organization` identifies the buyer; this tool has no `vendor_ids` filter.

Use keyword, semantic or supported [similarity search](https://govtribe.com/docs/govtribe-for-agents/guides/find-similar-records), keeping company identity fixed. Add agency, classification, location or value filters when required for the comparison; relax optional filters when useful work is missed.

Use `fields_to_return` for `govtribe_id`, `name` and available scope, customer, amount, dates and performing-entity fields; add `govtribe_url`/`govtribe_ai_summary` where supported. Subawards expose `description` and `prime_contractor`/`sub_contractor` or `prime_grantee`/`sub_grantee`. Expand promising records and deduplicate by `govtribe_id`. Group records for the same project before counting references. For work under an IDV/vehicle, assess delivered orders and actual workshare.

## 4. Verify the company's contribution and results

Review a reasonable selection of relevant, already-visible company files: project descriptions, past-performance narratives, evaluations and customer references. Read supplied files directly; scope [Search_User_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-user-files-mcp-tool) to relevant `user_file_ids` when indexed. Include supported commercial work when the buyer permits it.

Confirm the performing entity, prime/subrecipient role, actual scope, scale, dates, deliverables and documented outcomes. Identify parent, affiliate or personnel experience separately and apply the buyer's rules for using it. An award records work awarded; successful performance needs supporting results, evaluations or customer references.

## 5. Recommend references and explain gaps

Compare scope, complexity, scale, operating environment, recency and results against the buyer's criteria. Assess useful cross-buyer or partly overlapping work; select the combination that best covers the requirement and required reference count. Explain each recommendation's strengths, limitations and missing support. Distinguish missing information from demonstrated weakness.

For partner gaps, use [Likely Bidders](likely-bidders.md) for complementary-company research and [Team On An Opportunity](team-on-an-opportunity.md) for GovTribe teaming actions.

Prepare an appropriate GovCon past-performance assessment with recommended references, relevance, source links and remaining gaps. Let the requested use determine the format, following any buyer-required forms or reference limits.
