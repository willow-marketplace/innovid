<!-- GovTribe Skills generated documentation reference. Do not edit; regenerate from the canonical public GovTribe Docs page. -->

# Troubleshoot search results

- Canonical GovTribe Docs page: [https://govtribe.com/docs/govtribe-for-agents/guides/troubleshoot-search-results](https://govtribe.com/docs/govtribe-for-agents/guides/troubleshoot-search-results)

Use this guide when a GovTribe MCP `Search_*` tool returns too many records, no records, unrelated records, or records that appear to be missing expected values. Start by checking the tool, `search_mode`, `query`, filters, sort, and returned fields before changing the search strategy.

## Before changing the request

- Confirm the selected `Search_*` tool matches the record type the user expects.
- Check whether the tool supports `search_mode`, and whether the request used `keyword`, `semantic`, or the tool default.
- Review active structured filters, especially date, agency, vendor, category, location, value, set-aside, workflow, and capture-state filters.
- Confirm identifiers, acronyms, organization names, and category codes before treating a search as failed.
- Remember that `sort` changes result order, not which records match.
- Check `fields_to_return`; a field omitted from the response may still exist on the record.
- For aggregation-only calls, remember that `per_page: 0` intentionally suppresses row payloads.

## Too many results

Narrow the result set when the current request returns more records than the agent can review.

- Add structured filters for date, agency, vendor, category, location, value, status, or workspace state when the user requirement is a field requirement.
- Use narrower keyword terms, quoted phrases, exact identifiers, category codes, or organization names.
- Switch to `keyword` when exact words, identifiers, names, or phrases must appear.
- Keep `semantic` for concept research, but pair it with strong filters when the concept could match many markets or record types.
- Use `aggregations` with `per_page: 0` when the user needs a cohort summary before row review.

Tool: `Search_Federal_Contract_Opportunities`

```json
{
  "search_mode": "keyword",
  "query": "\"zero trust architecture\"",
  "due_date_range": {
    "from": "now/d",
    "to": "now+90d/d"
  },
  "fields_to_return": ["govtribe_id", "name", "due_date", "federal_agency"],
  "per_page": 10
}
```

## No results

Broaden the request when the selected tool returns no records.

- Remove one filter at a time, especially narrow date ranges, category filters, location filters, and workspace-state filters.
- Remove quotes when the exact phrase may not appear in source text.
- Use broader terms, fewer required terms, or common synonyms.
- Switch to `semantic` when the record may use different wording than the user query.
- Try a related `Search_*` tool when the record may belong to another record type.
- If an identifier does not match the expected record type, search the upstream or related record first.

Tool: `Search_Federal_Contract_Opportunities`

```json
{
  "search_mode": "semantic",
  "query": "counter unmanned aircraft systems detection and response work",
  "fields_to_return": ["govtribe_id", "name", "govtribe_url", "due_date"]
}
```

## Unrelated results

Check whether the tool, mode, and filters match the user's intended question.

- Add filters that force the buyer, seller, category, place, date, value, or status the user cares about.
- Switch from `semantic` to `keyword` when exact terms matter more than conceptual similarity.
- Search by an exact identifier, organization, category code, title, or quoted phrase when available.
- Review the returned `govtribe_type`, title, summary, description, and key fields before discarding a result as unrelated.
- Use a more specific `Search_*` tool if integrated search found the right topic but the wrong record type.

## Expected values are missing

Missing response values can mean different things. Do not assume the search failed just because a field is absent.

| Symptom | Check |
| --- | --- |
| A field is not in the response. | Confirm `fields_to_return` included it and that the selected tool lists it as an available field. |
| A field is present but empty or null. | Treat it as missing source or enrichment data for that record. |
| A related object is missing. | Confirm the selected record type actually exposes that relationship. |
| A value appears under a different field name. | Check the tool page and related data-model attribute pages before guessing. |

Use [Source identifiers and record matching](https://govtribe.com/docs/data-model/guides/source-identifiers-and-record-matching) when the issue is an identifier mismatch. Use [Federal contract record structure](https://govtribe.com/docs/data-model/guides/federal-contract-record-structure) when the user may be mixing forecasts, opportunities, IDVs, vehicles, awards, transactions, or subawards.

## Filters narrowed more than expected

Filters apply alongside the `query`. A useful query can still return few or no records when filters are too narrow.

For why some filters can include related records, hierarchy matches, or role-specific relationships, see [Filter by related records and hierarchies](https://govtribe.com/docs/govtribe-for-agents/guides/filter-by-related-records-and-hierarchies).

- Remove one filter at a time so the agent can identify which condition is hiding records.
- Broaden date ranges, locations, values, statuses, or category filters before changing the query text.
- Check whether the request is filtering for a specific agency role, vendor role, status, category system, or related record type.
- Prefer resolved GovTribe IDs for entity filters when a prior tool call returned them.

## When to switch modes

Use `keyword` when the user gives exact words, identifiers, names, codes, or phrases. Use `semantic` when the user describes a concept, mission, problem, capability, or related work and exact wording is not the main requirement.

For more detail, see [Choose a search mode and write queries](https://govtribe.com/docs/govtribe-for-agents/guides/choose-a-search-mode-and-write-queries).

## Try a related tool

If the search still feels wrong, the record may belong to a different search tool.

| Start with | When the record may be |
| --- | --- |
| `Search_Federal_Contract_Opportunities` | A solicitation, special notice, sources-sought notice, or other pre-award federal notice. |
| `Search_Federal_Forecasts` | Planned federal demand before a solicitation is posted. |
| `Search_Federal_Contract_Awards` | Awarded contract work, orders, purchase orders, or BPA calls. |
| `Search_Federal_Contract_IDVs` or `Search_Federal_Contract_Vehicles` | Parent instruments, IDIQs, schedules, GWACs, pools, or buying lanes. |
| `Search_Vendors`, `Search_Federal_Agencies`, or category tools | Participants, agencies, and classification records used as filters. |
| `Search_Government_Files` or `Search_User_Files` | Files and attachments rather than structured record fields. |

## Related articles

- [Choose a search mode and write queries](https://govtribe.com/docs/govtribe-for-agents/guides/choose-a-search-mode-and-write-queries): Choose keyword or semantic search and shape the `query` value.
- [Filter by related records and hierarchies](https://govtribe.com/docs/govtribe-for-agents/guides/filter-by-related-records-and-hierarchies): Understand why filters can match through parent, child, role, and connected-record relationships.
- [Manage search context](https://govtribe.com/docs/govtribe-for-agents/guides/manage-search-context): Use aggregations and focused returned fields before requesting richer records.
- [Date filtering](https://govtribe.com/docs/govtribe-for-agents/guides/date-filtering): Build date-bounded cohorts with fixed dates, open-ended ranges, and date math.
- [Source identifiers and record matching](https://govtribe.com/docs/data-model/guides/source-identifiers-and-record-matching): Decide which identifier should be used for matching.

---

For current tool schemas, parameters, response fields, or freshness-sensitive behavior, call the live `Documentation` MCP tool instead of inferring details from this bundled reference.
