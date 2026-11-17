# Solicitation Intelligence and Compliance Extraction

## 1. Resolve the question and source package

Reuse the active opportunity, supplied files and existing requirement register. Establish whether the request is a focused question or a full compliance shred.

Resolve missing context with [Search_Pursuits](https://govtribe.com/docs/govtribe-for-agents/tools/search-pursuits-mcp-tool), or the established opportunity type: [Search_Federal_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-opportunities-mcp-tool), [Search_State_And_Local_Contract_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool), or [Search_Federal_Grant_Opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-grant-opportunities-mcp-tool). Use [Search_GovTribe](https://govtribe.com/docs/govtribe-for-agents/tools/search-govtribe-mcp-tool) when the type is unclear.

Find package files with [Search_Government_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-government-files-mcp-tool), using the resolved record's documented parent filter. Use supplied files directly; use [Search_User_Files](https://govtribe.com/docs/govtribe-for-agents/tools/search-user-files-mcp-tool) for needed company evidence or named prior work.

## 2. Establish controlling sources

Record source identity, filename/title, version or amendment, locators and coverage: full file, selected passages, missing or inaccessible. Preserve existing source IDs.

Apply the solicitation's stated precedence. Otherwise reconcile the current solicitation and incorporated attachments with formal amendments and incorporated Q&A. Verify whether a conformed copy includes later changes; a newer upload or informal answer does not automatically supersede an instruction. Keep unresolved official-source conflicts visible. Government instructions control compliance; metadata, incumbent research and company context supply supporting evidence.

## 3. Extract and shred the files

- **Focused question:** retrieve the relevant passages, surrounding context and applicable amendments using native file access or the [vector-store retrieval guide](https://govtribe.com/docs/govtribe-for-agents/guides/vector-store-content-retrieval). Answer that question without creating a full requirement register.
- **Full compliance shred:** extract text and tables from every package file. Review each document section by section in manageable batches, preserving page, section and table locations. Track sections reviewed and unreadable or missing content. Use vector search to locate and cross-check requirements; retrieved chunks alone do not establish complete coverage.

Use the host's document, PDF and spreadsheet tools or a small extraction script. GTAI follows the [hosted-shell guide](https://govtribe.com/docs/govtribe-for-agents/guides/use-search-results-and-user-files-in-the-hosted-shell); external MCP clients use supplied or downloaded originals with native file/code capabilities. Apply OCR to scans and inspect forms or tables visually when extraction loses content. If originals or extraction are unavailable, provide the supported partial review and identify the uncovered files or sections.

Identify **what to submit**, **how it is evaluated**, and **what must be performed**, wherever those requirements appear. Connect related instructions: a resume request in an attachment, its evaluation criterion in Section M and personnel qualifications in the PWS. Evaluation criteria may need coverage without an explicit command; background with no response implication need not become a row.

Preserve existing requirement IDs and add IDs only for new items. Split independently missable commands, artifacts and discriminators. Retain exact wording separately from interpretation, with source ID and page, section, paragraph, table, form field or Q&A number. Link required action, response location, evidence and responsible role when useful. Label inferred needs and internal controls separately from source requirements. Retain requirements supported by explicit user text or verified excerpts with that provenance and coverage; missing originals limit coverage and form completion without establishing unsupplied acknowledgment instructions or form fields.

Cover deadlines with local time/time zone, mandatory events, submission channel, volume/file separation, naming and size limits, pages and formatting, source-required forms/signatures and acknowledgments, eligibility/access, pricing schedules/CLINs/options, deliverables, evaluation factors and stated relative importance. Separate past-performance count, recency, permitted performer/role, relevance and questionnaire rules. For oral presentations or demonstrations, capture content, time, attendees, format and logistics. Capture the actual acknowledgment/signature instruction, method and deadline; unresolved applicability belongs in a separate check, not a confirmed requirement.

Record each clause and provision incorporated by reference exactly as listed: number, title, date, alternate, fill-ins and any deviation notice. They usually sit in Sections I and K or a commercial addendum. A clause title doesn't state what the clause requires. When a requirement, fill-in or applicability question depends on the clause text, read it through [Read Acquisition Regulations](acquisition-regulations.md) in the edition the solicitation uses. The listed clause date controls; flag any difference from the current text.

## 4. Check procurement-specific sources

Use the actual scope to inspect applicable instructions; industry practice alone creates no requirement.

| Work | Source checks |
| --- | --- |
| Products/supply | Part/NSN and drawing revision; quantity/UOM; equivalency and salient characteristics; first article/testing; delivery/FOB; packaging, traceability, warranty and origin terms. |
| Digital/IT/cyber | System/data boundary, interfaces and volumes; actual authorization scope; incorporated security framework/version; accessibility; license, data/IP and portability rights. |
| Services | Workload/QASP; key-person qualifications, resumes and commitments; transition; wage determination/CBA; staffing, workshare and OCI instructions. |
| Construction/A-E/facilities | Selection model and phase; drawings/specifications/addenda precedence; site events, phasing/outages; bonds, licenses, wage instruments; SF 330 if required; commissioning and closeout. |
| Mission-specific work | Required clinical credentials, data protections, research ethics/IP, cold chain, environmental handling/permits, transport authority or emergency-response conditions. Verify applicability and jurisdiction from the sources. |

## 5. Apply amendments and identify gaps

Trace added, removed, replaced and clarified requirements to the controlling amendment. Update affected instructions, dates, forms, pricing inputs and evaluation mappings across existing controls; preserve superseded text only as labeled history. Retain IDs for continuing requirements and record impacts. Identify missing amendments or attachments before claiming complete coverage.

Prioritize preventable submission failures, missing proof and ambiguous instructions. Distinguish confirmed barriers from unknowns; frame material conflicts as specific Q&A or contracts-review items. Carry affected IDs and source limitations into the answer.

## 6. Use the extraction

Choose a useful readout, checklist or change summary for the request. Carry the same register into a requested [compliance matrix](build-proposal-control-workbook.md) or the relevant [response template](../SKILL.md#response-templates). For an annotated outline, use `govtribe-file-templates` reference `references/annotated-proposal-outline.md` from its installed root. Capture can reuse findings for pursuit decisions; Pricing handles unresolved delivery economics. Neither handoff requires repeating completed source research.
