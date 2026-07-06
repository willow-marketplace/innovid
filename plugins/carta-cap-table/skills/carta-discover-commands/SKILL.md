---
name: carta-discover-commands
description: META-DISCOVERY ONLY — answers the question "what cap-table tools or commands exist?" when the user is lost about what's available. NEVER use this skill for any request that names a cap-table topic (stakeholders, grants, vesting, SAFEs, notes, valuations, ownership, waterfall, financing, exposure, etc.) — those are always direct data requests, even if the user phrases them vaguely. The matching specialist skill wins every time over this one.
---
<!-- Part of the official Carta AI Agent Plugin -->

# Discover Commands

Use `search_tools` to find available commands when no specific skill covers the user's request.

## Step 1 — Search for Relevant Commands

```
search_tools({"query": "<keyword from user's request>"})
```

Use a keyword that captures the user's intent (e.g. "valuation", "grant", "safe", "stakeholder").

## Step 2 — Pick the Best Match

Review the returned tools. Each has:
- `name`: the tool name to pass to `call_tool` (e.g. `cap_table__get__stakeholders`)
- `description`: what it returns
- `inputSchema`: the required and optional parameters

## Step 3 — Execute

```
call_tool({"name": "<tool_name>", "arguments": { ...params }})
```

You still need `corporation_id` for most commands — get it from `list_accounts` if you don't have it.