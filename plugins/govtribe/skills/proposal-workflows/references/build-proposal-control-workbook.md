---
title: Build or Update a Compliance Matrix
description: Link solicitation requirements to proposal actions, owners, evidence and submission controls in a new or existing workbook.
---

# Build or Update a Compliance Matrix

## 1. Reuse the requirements

Start with the current source inventory, requirement register and amendments. Use [Solicitation Intelligence and Compliance Extraction](solicitation-intelligence-and-compliance-extraction.md) for missing or changed material. Keep existing source and requirement IDs.

## 2. Select the working file

For a new workbook, use `govtribe-file-templates` to resolve **Proposal Compliance & Control** (`proposal-control-workbook`) when its template library (`govtribe-file-templates` reference `references/template-library.md`) is installed. Read `govtribe-file-templates` reference `references/proposal-control-workbook.md` and its `references/template-adaptation-and-revisions.md` from the installed File Templates root, then populate a copy of the verified blank master using the host's native spreadsheet capabilities. Without the library, build the workbook natively from that guide's sheet and field map.

For an update, continue the selected working file, preserving its layout, formulas, IDs and accepted edits. If the user requests a simple matrix or supplies a buyer layout, use that structure; the controls below do not require eleven sheets.

## 3. Populate connected controls

Map actual column headers to the relevant controls:

- **Requirements:** ID, exact wording and source locator, separate response action, owner/contributors, needed evidence, status and proposal location.
- **Evaluation:** factors/subfactors, stated relative importance and evaluator focus, linked to requirement IDs and response sections.
- **Submission:** deadline with source-local time/zone, channel, volumes, file/page limits, forms, signatures and amendment acknowledgments.
- **Pricing and deliverables:** schedules, CLINs, quantities, units, periods and attachments linked to requirement IDs; use `govtribe-pricing-data` for pricing research.
- **Questions and changes:** missing sources, evidence or assignments, decisions and amendment impacts, with affected IDs and next actions.

Keep source requirements distinct from internal controls. Leave unknown owners, evidence and company commitments visible for the proposal team to resolve. For a requested outline, carry the same IDs into `govtribe-file-templates` reference `references/annotated-proposal-outline.md` from its installed root.

## 4. Update and reconcile

Apply controlling amendments to every affected requirement, evaluation link, deadline, submission/pricing control and response location. Retain superseded instructions as labeled history.

Reconcile the working rows with summary counts, open questions, missing owners/evidence and amendment impacts. Confirm referenced response files use the same requirement IDs, deadlines and amendment version. File Templates and the host handle workbook calculation and file checks. Deliver the workbook with a brief explanation of material gaps and next actions.
