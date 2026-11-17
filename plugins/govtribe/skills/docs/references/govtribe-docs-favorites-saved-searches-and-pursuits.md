<!-- GovTribe Skills generated documentation reference. Do not edit; regenerate from the canonical public GovTribe Docs page. -->

# Favorites, saved searches, and pursuits

- Canonical GovTribe Docs page: [https://govtribe.com/docs/govtribe-for-agents/guides/favorites-saved-searches-and-pursuits](https://govtribe.com/docs/govtribe-for-agents/guides/favorites-saved-searches-and-pursuits)

GovTribe offers three ways to keep something for later, and they are not interchangeable. Favorites keep a single record. Saved searches keep a query. Pursuits keep active capture work. Picking the wrong one clutters a customer's workspace: a pursuit created for a vendor an agent merely found interesting adds noise to a pipeline, and a favorite used where a saved search belongs loses the filters that made the result set useful.

## Choose the right one

| Use | When | Tool |
| --- | --- | --- |
| Favorite | One specific record is worth revisiting, and nothing else needs to happen to it yet. | [Set favorite](https://govtribe.com/docs/govtribe-for-agents/tools/set-favorite-mcp-tool) |
| Saved search | The query, filters, and sort should be reusable, or the customer wants notifications when results change. | [Create saved search](https://govtribe.com/docs/govtribe-for-agents/tools/create-saved-search-mcp-tool) |
| Pursuit | The record has become real capture work that belongs in a pipeline stage with owners and tasks. | [Create pursuit](https://govtribe.com/docs/govtribe-for-agents/tools/create-pursuit-mcp-tool) |

## Favorite a record

Favoriting is the lightest option. It saves one record to the signed-in user's account, changes nothing about the record itself, and notifies nobody. Reach for it during research, when a vendor, agency, category, vehicle, program, or file is worth another look but does not yet justify a pipeline entry.

Favorites are per user rather than per workspace, and they are scoped to a record type. A customer's favorite vendors and favorite NAICS categories are separate lists, reachable from the vendor and NAICS search surfaces respectively, not one combined list.

Setting a favorite is idempotent, so an agent never needs to read the current state first. Favoriting an already-favorited record returns the existing favorite and creates nothing new.

## Search within favorites

Every search tool whose record type supports favorites accepts a `favorited` argument. Pass `true` to restrict results to records the signed-in user has favorited.

```json
{
  "tool": "Search_Vendors",
  "arguments": {
    "query": "logistics",
    "favorited": true,
    "fields_to_return": [
      "govtribe_id",
      "name"
    ]
  }
}
```

Two rules matter when using it:

- Only `true` is accepted. There is no supported way to search for records the customer has *not* favorited; omit the argument to search everything.
- The argument requires a signed-in user. An unauthenticated call returns a clear error rather than silently returning unfiltered results.

If the customer has no favorites of that record type, a `favorited: true` search correctly returns nothing. Treat an empty result as an empty shortlist, not as a failed search.

## Save a search instead

When what matters is the *query* rather than any single record, create a saved search. A saved search preserves the search text, filters, and sort so the customer can reopen the same result set later, and it can notify them when new records match. See [Use saved searches](https://govtribe.com/docs/govtribe-for-agents/guides/use-saved-searches) for the full workflow.

Favoriting each result of a search is not a substitute. Favorites capture the records that existed at that moment; a saved search keeps finding new ones.

## Create a pursuit instead

When a record has become active work the customer intends to bid, track, or staff, create a pursuit. Pursuits live in a pipeline stage, carry owners, tasks, comments, and discussions, and are visible to the workspace rather than to one user.

Do not create pursuits speculatively. If an agent is unsure whether a record has crossed from research into capture work, favorite it and say so, then let the customer decide.

## Related articles

- [Set favorite](https://govtribe.com/docs/govtribe-for-agents/tools/set-favorite-mcp-tool): Add or remove a record from the signed-in user's favorites.
- [Use saved searches](https://govtribe.com/docs/govtribe-for-agents/guides/use-saved-searches): Create, find, reopen, and update GovTribe saved searches through MCP tools.
- [Create pursuit](https://govtribe.com/docs/govtribe-for-agents/tools/create-pursuit-mcp-tool): Move a record into capture work in a pipeline stage.
- [Use favorites](https://govtribe.com/docs/govtribe-user-guide/guides/use-favorites): See how favorites behave for customers in GovTribe Web.

---

For current tool schemas, parameters, response fields, or freshness-sensitive behavior, call the live `Documentation` MCP tool instead of inferring details from this bundled reference.
