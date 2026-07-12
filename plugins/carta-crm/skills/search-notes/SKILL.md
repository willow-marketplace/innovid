---
name: search-notes
description: >
---
## Overview

Search for notes in the Carta CRM by keyword using `search_notes`.
Return results in a readable summary.

## Step 1 — Collect the search term

If the user provided a keyword or topic, use it directly.
If no search term was given, ask for one.

## Step 2 — Execute the search

```
mcp__carta_crm__search_notes({
  query: "<search term>",
  limit: 20
})
```

Increase `limit` if the user asks to see more results. Use `offset` to paginate
when `remainingCount > 0`.

## Step 3 — Present results

For each note returned, display:
- Title
- Text content (truncated to ~200 chars if long)
- Creation date and owner if available

If no notes are found:
> "No notes found matching your search. Try a different keyword."

Note the total count and offer to paginate if there are more results.