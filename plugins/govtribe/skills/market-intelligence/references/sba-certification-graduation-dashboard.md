---
title: SBA Certification Expiration Analysis
description: Find incumbent work to pursue as contractors approach SBA certification expiration, then identify follow-on opportunities and eligibility.
---

# SBA Certification Expiration Analysis

Help 8(a) participants and other small firms find work to pursue as incumbents leave SBA programs. Use the requested certification window; otherwise use **today through 12 months**: `{"from":"now/d","to":"now+12M/d"}`. Apply this window to certification dates, not contract end dates.

## 1. Clarify the pursuit scope

Use the user's capabilities, customers, geography and certifications to focus the search. Ask clarifying questions only where needed, including which program they mean by "small business." Small Disadvantaged Business is a specific designation, not a synonym for every small firm.

The supported award/IDV filters and corresponding vendor fields are:

| Program | Award/IDV filter | Vendor date field |
| --- | --- | --- |
| 8(a) | `vendor_sba_8a_expiration_date_range` | `sba_cert_8a_expiration_date` |
| 8(a) joint venture | `vendor_sba_8a_joint_venture_expiration_date_range` | `sba_cert_8a_joint_venture_expiration_date` |
| HUBZone | `vendor_sba_hubzone_expiration_date_range` | `sba_cert_hubzone_expiration_date` |
| Small Disadvantaged Business | `vendor_sba_small_disadvantaged_business_expiration_date_range` | `sba_cert_small_disadvantaged_business_expiration_date` |

## 2. Find relevant incumbent work

Use [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool) and [Search_Federal_Contract_IDVs](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-idvs-mcp-tool) with the selected certification filter and relevant vendor, agency, NAICS or requirement filters.

For ongoing work, set `ultimate_completion_date_range` (awards) or `last_date_to_order_range` (IDVs) to `{"from":"now/d","to":null}`. Leave the lifecycle filter off when investigating records with missing end dates. Retrieve identifiers, buyer, `awardee`, `govtribe_url` and `dollars_obligated`, plus award `descriptions`, `completion_date`, `ultimate_completion_date`, `set_aside_type`, or IDV `description`, `last_date_to_order`, `set_aside`. Use `fields_to_return` to expand promising records and paginate the relevant results.

## 3. Compare certification and contract dates

Call [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool) with the direct `awardee.govtribe_id` values in `govtribe_ids`; request the selected certification field alongside identity and URLs. Use that entity's date, not its ultimate parent's.

Calculate `gap_days = contract end date - certification date`, sorting locally. Positive gaps identify work extending beyond the reported certification date. Compare current and ultimate award completion separately to distinguish current performance from potential options/extensions. Check descriptions and notices for terminations or replacements before treating future end dates as ongoing work. Keep missing-date cases unassessed.

## 4. Establish the opening for another contractor

Check whether the contract's set-aside depends on the expiring certification. Verify promising incumbents' current status through [SBA sources](https://www.sba.gov/certifications/) and source notices: renewal, graduation and other exits have different implications. A date gap alone does not establish contract loss or a new competition.

For 8(a), follow-on requirements generally remain in the program unless SBA releases them or an exception applies (codified FAR 19.815). That can create a pursuit for another eligible 8(a) firm; other small firms need to check the actual acquisition strategy and access requirements. Before a pursuit recommendation relies on the 8(a) rule, read it with `Get_Regulation_Section` in the edition the agency applies.

## 5. Find and prioritize follow-on pursuits

For the strongest matches, follow [Find Federal Recompete Opportunities](find-federal-recompete-opportunities.md) to locate successor forecasts and notices, verify the relationship, and check timing, eligibility and vehicle access. Distinguish an actionable opportunity, an early prospect to monitor, an already-awarded successor, and no follow-on found.

Rank by capability fit, eligible access, competition timing and actual contract activity—not the longest date gap alone. Keep IDV ceilings separate from obligated spending and distinguish vehicle replacement from task-order recompetes. Identify the useful next step, such as responding to sources sought, researching the buying office or monitoring a forecast.

## Report

Prepare a GovCon business-development report explaining whose work is worth pursuing, why, when and through which opportunity or acquisition path. Include supporting links and material gaps; choose the format that best communicates the findings.
