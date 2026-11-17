# Release provenance

Claude 1.2.1 replaces the unpublished 1.2.0 candidate.

Web source: `b791b30385595aebbbc39de6285e4a67a65b4d56`. Portable digest:
`f83ce80b5ccada3731351ae65838337ad03ca5bc8c7cabd24415108c04fb20db`.

All 104 skill files match the generated, structurally validated Claude candidate
exactly. `skill-source.json` records their hashes. The root manifest retains the
repository's explicit skills declaration, Apache-2.0 license and directory listing
fields. No skill adaptations were made here.

The Claude build leaves out the template library: the 15 Office template masters,
their previews, the template catalog and the Python helpers. The template guides
detect that the library is absent and describe each document's sections instead.
ChatGPT and GovTribe AI keep the library.

Strict Claude structural validation passes. The skill text was compared with 1.2.0
on the repository's synthetic evaluation cases and on GovTribe's internal analyst
benchmark, with no regression found. No case builds a file, so the simpler
workbooks Claude produces without the masters are not measured. This release follows
the release owner's explicit integrity-only authorization and does not claim every
acceptance case passed.

Workbook formulas, validations and formatting are rebuilt by Claude rather than
copied from a master, so complex workbooks are simpler than GovTribe's templates.
No production tools, hosted skills or defaults are changed by this release.
