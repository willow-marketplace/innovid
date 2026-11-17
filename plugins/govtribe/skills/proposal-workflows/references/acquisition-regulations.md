---
title: Read Acquisition Regulations
description: Read FAR, DFARS, DFARS PGI and GSAM text by citation or topic, in the edition the buyer applies, and cite it exactly.
---

# Read Acquisition Regulations

Use this when an answer depends on what a FAR, DFARS, DFARS PGI or GSAM section, clause or provision says. Examples: an incorporated clause, a set-aside or subcontracting rule, a threshold, a prescription, or an agency deviation. Read the text with `Get_Regulation_Section` and `Search_Regulations` instead of recalling it. The FAR Overhaul renumbered and rewrote many sections, and dollar thresholds change. When you need a tool's current arguments, retrieve its page with `Documentation`: [Get regulation section](https://govtribe.com/docs/govtribe-for-agents/tools/get-regulation-section-mcp-tool) or [Search regulations](https://govtribe.com/docs/govtribe-for-agents/tools/search-regulations-mcp-tool).

## 1. Start from the procurement

Reuse the active solicitation, contract or order and its requirement register. Take the clauses it lists, with their dates, alternates, fill-ins and deviation notices. They are in Sections I and K, the commercial addendum, or the order's clause list. If the clauses haven't been extracted, use [Solicitation Requirements and Amendments](solicitation-intelligence-and-compliance-extraction.md). The incorporated version governs that procurement. Compare the returned clause date with the incorporated clause date; the tool cannot select a historical clause version. If they differ, say so and treat the current text as context.

Without a procurement, answer for the agency and date the user gives, or state the assumption you used.

## 2. Choose the edition

`Get_Regulation_Section` requires `edition`, which takes one of two values:

- `codified`: the published regulatory text and accompanying PGI or GSAM manual guidance. Guidance is not 48 CFR regulatory text.
- `overhaul`: the Revolutionary FAR Overhaul text, or GSA's matching GSAR Overhaul. Agencies apply both through class deviations.

To choose:

- **The incorporated clause edition:** use the edition and dated version the procurement incorporates for that clause, including applicable modifications. A solicitation-wide RFO or RGO notice, or a deviation for another part, does not establish every clause's edition. Check the clause prescription, relevant clause deviation and source memo scope before selecting `overhaul`.
- **The buyer's edition is unclear:** pass the buying agency and relevant date for a part-mapping heuristic from available indexed deviation records. The implementation maps DFARS Part 252 to FAR Part 52 without following the clause prescription or checking source memo scope. Treat the report as a research lead, not a clause-level governing-edition determination: read the prescription, relevant clause deviation, source memo scope and effective date against the procurement before relying on it. A supported part with no matching memo defaults to `codified`; this does not prove codified text governs. GSAM, appendices and unsupported parts are uncovered, so verify the procurement, applicable agency source memo and effective date; the report does not evaluate them. The report never changes the requested text or retrieves a historical clause version. Read another edition when the evidence calls for it; if the edition remains unsettled, read both and state the unresolved condition.
- **The question is what changed:** read both editions and compare them. Section numbers can differ between the editions.

State the edition you relied on and why.

## 3. Read or find the text

- **Known citation:** use `Get_Regulation_Section` with the citation as written. That can be a section, a paragraph such as `52.219-14(e)(1)`, an alternate, or a part or subpart for its contents. For a FAR citation, request supplement listings, then read the relevant returned citations: DFARS and PGI for DoD, GSAM for GSA. Listings cover the same edition and exactly the requested FAR part, subpart or section; they do not contain the supplement text. A supplement adds to the FAR; it doesn't replace it.
- **Topic or unknown number:** use `Search_Regulations` by keyword or meaning, filtered by regulation, edition, part or kind. Set the edition filter explicitly; otherwise results mix editions. Its relationship filters can:
  - walk a part's outline;
  - find the clauses and provisions prescribed by a section;
  - find what cites a section;
  - find agency sections that supplement a FAR citation.

  Results list citations and titles. To find when a clause must be included, follow its returned prescription to the prescribing section. Read the text you rely on with `Get_Regulation_Section`.
- **GSAM:** the codified GSAM mixes binding GSAR text with GSA's internal manual guidance. When the answer must bind a contractor, filter to regulatory text. An unmarked section is unknown, not manual text.
- **Pending or recent changes:** use `Search_Federal_Register_Documents` with the amended section to find proposed and final rules.

These tools cover the FAR, DFARS, DFARS PGI and GSAM. Read SBA's 13 CFR rules, wage determinations, statutes and the solicitation's own terms from their own sources.

## 4. Answer and cite

Lead with what the rule means for this procurement, then quote or paraphrase the controlling paragraph. Cite the way practitioners write it, for example `FAR 52.219-14(e)(1)`, `FAR 52.219-9 Alternate II` or `DFARS 252.204-7012(b)`. Add the clause date when the solicitation lists one. Quote only retrieved text, and quote it exactly.

If a citation isn't found, is withdrawn, or has a different number in the other edition, say so. Follow what the tool returns: other editions, or the paragraphs and parts that are available. Search by title before concluding that a section doesn't exist. Don't reconstruct text from memory. Flag three cases as limitations:

- the returned revision shows an Overhaul section isn't in force yet;
- a passage is GSA manual text;
- a deviation is unresolved.

If the regulation tools are unavailable, say so. Use the solicitation's quoted text or the official source, and label anything else as unverified.
