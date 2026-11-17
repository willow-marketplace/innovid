---
title: GAO Bid Protest Evidence
description: Find GAO protests and decisions relevant to a federal procurement, agency, company or evaluation issue.
---

# GAO Bid Protest Evidence

## 1. Identify the research question

Reuse the resolved procurement and company context. For a supplied pursuit, use [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool) to retrieve its linked opportunity. Resolve missing federal contract notices with [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), using `govtribe_ids` or `solicitation_numbers`. For agency or company history, resolve names with [Search_Federal_Agencies](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-agencies-mcp-tool) or [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool).

For history, use the requested period, otherwise the past five years: `decision_date_range` for decisions, `filed_date_range` for docket activity including open cases. Apply no date cutoff to exact-case or procurement-identifier lookups.

## 2. Find relevant protests

Use [Search_GAO_Bid_Protests](https://govtribe.com/docs/govtribe-for-agents/tools/search-gao-bid-protests-mcp-tool) with exact `file_numbers`, source `procurement_identifiers`, linked `federal_meta_opportunity_ids`, `federal_agency_ids` or exact `protesters` names, as appropriate. If structured linkage is missing, search identifiers and names with `query`; use keyword or semantic search for issues such as unequal discussions or unstated evaluation criteria. Distinguish cases about this procurement from analogous decisions.

Start with `fields_to_return` covering `govtribe_id`, `file_number`, `protester`, `status`, `outcome`, relevant dates and `source_links`. Follow relevant results and remaining pages before concluding no matching protest was found; missing records or links do not establish a clean protest history.

## 3. Read the decision and related proceedings

Retrieve relevant records by `govtribe_ids`, adding `decision_text`, `pdf_url` and `procurement_identifiers`. Read a joint decision once. Open the official decision when retrieved text is insufficient; use [GAO's bid-protest resources](https://www.gao.gov/legal/bid-protests) to fill source gaps. Look up cited docket suffixes, supplemental protests and reconsideration directly when they affect the answer.

Explain the challenged action, parties' arguments, GAO's findings and any recommended remedy. Separate allegations from findings, dismissal from denial, and recommended corrective action from implemented changes or an actual contract award. Preserve mixed outcomes and distinguish `decision_date` from public `available_date`; open dockets have no decided outcome.

## 4. Compare history when requested

Use aggregations such as `top_outcomes_by_doc_count`, `top_protesters_by_doc_count` and `top_procurement_identifiers_by_doc_count`, with `per_page: 0` for counts alone. State the filters and period; group related proceedings before making procurement-level comparisons. Docket counts are not a company's contract win rate. Read representative decisions to explain recurring issues rather than infer them from outcome labels.

## 5. Connect findings to the pursuit

Check subsequent notices, controlling amendments and Q&A with `Search_Federal_Contract_Opportunities` and [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool); verify resulting awards with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool). For an IDV, retrieve subsequent orders with `federal_contract_idv_ids`. Reuse documents already read and identify what changed after the decision.

Recommend practical responses: clarify an ambiguous requirement, substantiate past performance, revise pricing assumptions or update the capture schedule. Reuse [Black Hat Review](conduct-black-hat-review.md), [Capture Price-to-Win Review](conduct-price-to-win-review.md) or [Bid / No-Bid Review](conduct-bid-no-bid-review.md) when the findings affect those decisions.

Prepare an appropriate GovCon report with case numbers, dates, supporting sources, implications and unresolved questions. Let the requested use determine the format.
