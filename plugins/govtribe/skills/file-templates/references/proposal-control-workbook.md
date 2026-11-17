# Proposal Compliance & Control

Use after selecting `proposal-control-workbook`. If `template-library.md` is not beside this guide, no template is installed: build the sheets in the [sheet and field map](proposal-control-workbook-schema.md) natively with live formulas and dropdowns, and ignore instructions to inspect or copy a template.

1. **Read the workbook first.** Inspect Start Here, each working sheet's instructions, headers and dropdowns. The template explains its controls; the [sheet and field map](proposal-control-workbook-schema.md) locates them.
2. **Reuse the requirements.** Start with the current source inventory, requirement register and amendments. For missing or changed requirements, use **Solicitation Intelligence and Compliance Extraction** (`govtribe-proposal-workflows` → `references/solicitation-intelligence-and-compliance-extraction.md`, from that skill's installed root).
3. **Populate connected controls.** Fill Sources and Opportunity Setup, then the requirements, evaluation, submission, pricing/deliverables, questions and amendment tabs. Keep source and requirement IDs stable and propagate changes across linked controls. Record actual owners, evidence and response locations; distinguish compliance from drafting progress using the existing dropdowns.
4. **Check and deliver.** Recalculate and reconcile Dashboard counts with the working rows. Preserve formulas, validations and print panels. When extending print ranges, update their matching `PrintEnd*` defined names so Dashboard's print-coverage check stays accurate. Use [Template adaptation and revision checks](template-adaptation-and-revisions.md) for selected revisions, saved-sheet inspection and delivery.

Where `template-library.md` is installed, it covers the optional structure check and retained-layout row sizing. Otherwise use native spreadsheet checks and row sizing, then inspect the affected print panels.
