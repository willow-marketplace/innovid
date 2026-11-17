# Proposal Compliance & Control: Sheet and Field Map

This map describes the verified `proposal-control-workbook` master. Match actual headers when populating a revision; custom workbooks may arrange the same controls differently. Preserve calculated Control Flag cells, Dashboard formulas, validations and the Lists sheet.

## Workbook setup and summaries

The eleven sheets are Start Here, Opportunity Setup, Dashboard, Requirement Matrix, Evaluation Crosswalk, Submission Checklist, Pricing & Deliverables, Questions-Risks, Amendment Log, Sources and Lists.

- **Start Here:** usage instructions for the proposal team.
- **Opportunity Setup:** company, buyer, project, solicitation, source cutoff, due date, source-local time, named time zone, submission channel, controlling deadline source, contract type, tour dates, performance end, UTC offset, response volumes and internal milestones. The ISO deadline cell is calculated from the date, time and offset.
- **Dashboard:** calculated counts for active requirements, unresolved/failed compliance, unfinished work, missing final files, control errors, unfinished submissions, open questions and amendment actions; additional summaries show mandatory/evaluated requirements, missing owners/evidence/locations and work progress.

## Working table headers

Populate below the indicated header row. These are the current master headers in order; bind by name rather than column letter.

### Requirement Matrix — header row 7

`Req ID`, `Verbatim Requirement`, `Source Citation`, `Proposal Action / Interpretation`, `Proposal Volume`, `Proposal Outline Ref`, `Evidence / Artifact Needed`, `Compliance Posture`, `Draft Status`, `Evidence Reviewed`, `Final Proposal Location`, `Final Page / File`, `Control Flag`, `Source Area`, `Requirement Category`, `Requirement Level`, `Owner`, `Support Owner(s)`, `Evaluator Focus / Discriminator`, `Amendment / Q&A Impact`, `Notes / Assumptions`, `Source URL`, `Source ID`, `Classification`.

### Evaluation Crosswalk — header row 7

`Eval ID`, `Factor / Subfactor`, `Evaluator Focus`, `Source Citation`, `Related Req IDs`, `Proposal Volume`, `Proposal Section`, `Evidence / Discriminators`, `Review Status`, `Relative Importance`, `Lead Owner`, `Source URL`.

### Submission Checklist — header row 6

`Check ID`, `Control Item`, `Due / Verify By`, `Status`, `Evidence / Location`, `Source Citation`, `Control Flag`, `Category`, `Why it matters`, `Owner`, `Notes`.

### Pricing & Deliverables — header row 6

`Item ID`, `Description`, `Related Requirement IDs`, `Price / Proposal Location`, `Status`, `Notes`, `Source Citation`, `Item Type`, `Owner`, `Source URL`, `Evidence Reviewed`, `Control Flag`.

### Questions-Risks — header row 6

`Item ID`, `Issue / Decision`, `Resolution Path`, `Impacted Req IDs`, `Due Date`, `Status`, `Resolution / Notes`, `Type`, `Priority`, `Owner`, `Need Customer Q&A?`, `Control Flag`.

### Amendment Log — header row 6

`Amendment ID`, `Date`, `Summary of Change / Clarification`, `Impacted Req IDs`, `Action Needed`, `Status`, `Impacted Sections`, `Change Type`, `Owner`, `Source URL`.

### Sources — header row 5

`Source Type`, `Short Name`, `What it supports`, `Key Takeaway / Notes`, `Source URL`, `Source ID`, `Classification`, `SHA-256`, `Source date`, `Retrieved date`.

## List values

Use the master's existing dropdowns: compliance is Pass, Fail or Unresolved; work progress is Not started, In progress, In review or Complete; requirement level is Mandatory or Optional. Use the requested response volumes and preserve dependent validations when adapting the lists.
