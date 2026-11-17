<!-- GovTribe Skills generated documentation reference. Do not edit; regenerate from the canonical public GovTribe Docs page. -->

# Use saved searches

- Canonical GovTribe Docs page: [https://govtribe.com/docs/govtribe-for-agents/guides/use-saved-searches](https://govtribe.com/docs/govtribe-for-agents/guides/use-saved-searches)

Use saved-search MCP tools when an agent needs to preserve a useful GovTribe search, find a previously saved search, reopen its current results, update its criteria, or change its notification cadence. Saved searches store a search definition so the user or workspace can return to the same search later.

## What saved searches store

A saved search can include the source search type, query, filters, sorts, search mode, notification frequency, owner or creator context, view-results URL, and a `search_results_id`. The exact fields available to the agent come from [Search saved searches MCP tool](https://govtribe.com/docs/govtribe-for-agents/tools/search-saved-searches-mcp-tool).

Use [Source identifiers and record matching](https://govtribe.com/docs/data-model/guides/source-identifiers-and-record-matching) when you need to distinguish a saved-search `govtribe_id`, `govtribe_url`, `search_results_id`, and `view_results_url`.

## Create a saved search

Create a saved search only after a `Search_*` tool returns a `search_results_id` for the useful search state. Name the saved search for the user-facing market, buyer, topic, or workflow it will support.

Tool: `Create_Saved_Search`

```json
{
  "search_results_id": "<SEARCH_RESULTS_ID>",
  "name": "Counter UAS opportunities due in the next 12 months",
  "frequency": "Daily"
}
```

Choose `Never` when the user wants to preserve the search without saved-search email. Choose `Instant`, `Daily`, or `Weekly` when the user wants notifications at that cadence.

This field does not control saved-search automations. Each automation trigger requires its own lowercase `instant`, `daily`, or `weekly` polling frequency.

## Find existing saved searches

Use `Search_Saved_Searches` when the agent needs to find a saved search by name, owner, creator, scope, or saved-search ID.

Tool: `Search_Saved_Searches`

```json
{
  "query": "\"Counter UAS\"",
  "fields_to_return": [
    "govtribe_id",
    "name",
    "frequency",
    "govtribe_url",
    "q",
    "filters",
    "sorts",
    "view_results_url",
    "search_results_id"
  ],
  "per_page": 10
}
```

Review the returned `q`, `filters`, `sorts`, and `searches_govtribe_type` before deciding whether a saved search is the one the user meant.

## Reopen current results

Use the saved search's `govtribe_url` or `view_results_url` when the user wants the GovTribe Web page. Use the saved search's stored `q`, `filters`, `sorts`, and `searches_govtribe_type` when the agent needs to continue analysis through MCP tools.

If the task needs row-level records, rerun the matching `Search_*` tool with the saved search's query and filters. Request only the fields needed for the current step.

## Update a saved search

Use `Update_Saved_Search` when the user asks to rename a saved search, change notification frequency, or replace saved criteria with a refined `Search_*` result. To replace criteria, first run or refine the appropriate `Search_*` tool until it returns the desired `search_results_id`, then pass that ID with the saved-search ID.

Tool: `Update_Saved_Search`

Rename or change notification cadence:

```json
{
  "saved_search_id": "<SAVED_SEARCH_ID>",
  "name": "Counter UAS opportunities - active review",
  "frequency": "Weekly"
}
```

Set `frequency` to `Never` to stop saved-search email. Changing this value does not change any automation trigger that uses the saved search.

Replace criteria from a recent search:

```json
{
  "saved_search_id": "<SAVED_SEARCH_ID>",
  "search_results_id": "<SEARCH_RESULTS_ID>"
}
```

When criteria are replaced, the saved search keeps its saved-search ID and adopts the recent search's query, filters, sorts, source search type, and search mode.

Do not update a saved search just because a new search returned better results. Create or update only when the user asks to preserve refined criteria, rename a saved search, or change saved-search notifications.

## Saved search versus shared search

A saved search is a workspace record with a name and optional email cadence. A shared search or `view_results_url` opens a search state in GovTribe Web but does not by itself create a saved-search record.

Use a saved search when the user wants to revisit, monitor, or manage the search later. Use a URL when the user only needs to open or share the current result set.

## Related articles

- [Search saved searches MCP tool](https://govtribe.com/docs/govtribe-for-agents/tools/search-saved-searches-mcp-tool): Find saved-search records and inspect saved query definitions.
- [Create saved search MCP tool](https://govtribe.com/docs/govtribe-for-agents/tools/create-saved-search-mcp-tool): Create a saved search from a `search_results_id`.
- [Update saved search MCP tool](https://govtribe.com/docs/govtribe-for-agents/tools/update-saved-search-mcp-tool): Rename a saved search, change notification frequency, or replace criteria from a `search_results_id`.
- [Choose a search mode and write queries](https://govtribe.com/docs/govtribe-for-agents/guides/choose-a-search-mode-and-write-queries): Shape a search before preserving it.
- [Troubleshoot search results](https://govtribe.com/docs/govtribe-for-agents/guides/troubleshoot-search-results): Adjust a search before saving it when results are too broad or too narrow.

---

For current tool schemas, parameters, response fields, or freshness-sensitive behavior, call the live `Documentation` MCP tool instead of inferring details from this bundled reference.
