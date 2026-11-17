---
title: Conduct Bid / No-Bid Review
description: Decide whether and how to pursue a government contract or grant opportunity using buyer requirements, company fit, competition and economics.
---

# Conduct Bid / No-Bid Review

## 1. Identify the opportunity and our company

If given a saved pursuit, resolve it with [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool). Retrieve the supplied or linked opportunity using [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool), or [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool). Resolve the bidder with [Search_Vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool).

Reuse known company context. If needed, follow the company-profile step in [Relevant Opportunities](relevant-opportunities.md#1-build-the-company-profile-from-its-work): awards and subawards, held vehicles/IDVs, certifications, prior pursuit outcomes and a reasonable selection of relevant, already-visible company files. Ask for missing context that could change the decision.

## 2. Read the current requirements

Reuse an existing solicitation requirement map when available. For detailed extraction or amendment reconciliation, use `references/solicitation-intelligence-and-compliance-extraction.md` from `govtribe-proposal-workflows`' installed root; a qualification review need not create a full proposal matrix.

Use [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool) to retrieve the solicitation or funding notice, amendments and Q&A. Read the scope, submission instructions, evaluation criteria and material terms. When snippets are insufficient, read the full file using native download/text tools or [source-content retrieval](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval).

Establish current status, response and question deadlines, mandatory events, performance period, eligibility and access requirements. When attachments disagree, compare file IDs, posting dates and amendment text to identify the controlling version. Distinguish a sources-sought response or forecast from a bid-ready solicitation.

## 3. Assess whether we can compete and deliver

Compare the buyer's requirements and evaluation factors with our technical approach, relevant experience, staffing, key personnel, transition plan and delivery capacity. Use the research steps in [Past Performance Match](past-performance-match.md) for relevant examples and gaps.

Assess capacity in the units of the work: inventory, production and delivery for supply; trade coverage, schedule and bonding for construction; platform access, rights and transition for digital work; staffing and credentials for services.

Establish which qualifications and access conditions the controlling sources impose before checking vehicle access, clearances, certifications, conflicts, cybersecurity, bonding and subcontracting limits. Treat a missing vehicle seat as a barrier only when the controlling source requires that access. Evaluate the actual bidding entity and proposed arrangement; a partner's credentials or vehicle seat do not automatically qualify the prime. Keep verified barriers, supported remedies and unknown company status or timing separate. A buyer requirement does not establish that the company lacks the qualification; conclude that a gap cannot close in time only from the actual required date and supported resolution timing.

Some gates turn on a regulation: a set-aside, limitations on subcontracting, the nonmanufacturer rule, a cybersecurity clause or an OCI provision. For these, read the clause the solicitation incorporates, with its date and alternate. Use `Get_Regulation_Section` in the edition the buyer applies, and check the agency's deviation when that edition is unclear. Don't decide a gate from a recalled percentage or paragraph. Follow `govtribe-proposal-workflows` reference `references/acquisition-regulations.md` from its installed root for the edition, supplement and citation steps.

Verify required authorizations against the proposed product or system, current status and required date. [FedRAMP certification](https://www.fedramp.gov/2026/agencies/use/) supplies reusable security evidence; the agency still authorizes its own system and use.

## 4. Assess whether the pursuit makes business sense

Use [Find Incumbent](find-incumbent-for-federal-contract-opportunity.md) and [Likely Bidders](likely-bidders.md) where relevant. Research comparable awards from the same buyer/office and similar scope over the last 24–36 months with [Search_Federal_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool), [Search_Federal_Grant_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-awards-mcp-tool), or [Search_State_And_Local_Contract_Awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-awards-mcp-tool). Identify a credible differentiator against the buyer's priorities.

Consider proposal effort and available time, delivery costs, margin, payment terms, cash needs, existing commitments and strategic value using available company information. Distinguish contract ceilings from likely workshare. Use `govtribe-pricing-data` for pricing research that could change the decision. For grants, assess program fit, allowable costs, cost sharing and funding obligations instead of contract profit.

## 5. Recommend a practical path

Compare the plausible paths: prime alone, partner, subcontract, monitor/shape, or decline. For partner options, explain the role and gap closed. Use the established labels `BID`, `BID_WITH_PARTNER`, `SUB_ONLY`, `MONITOR_AND_SHAPE`, or `NO_BID` when a decision is supported. If essential information is missing, identify what must be resolved before deciding.

Prepare an appropriate GovCon decision report with the recommendation, decisive reasons, source links, unresolved conditions and next actions. Let the requested use determine the format.
