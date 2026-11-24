# Saved views of a workflow’s runs

A saved view is a named way of looking at one workflow’s runs: a filter, which columns are shown, which are pinned, their order and widths, and columns extracted from a value. It does not change the workflow or its runs. The built-in view is named All results and is always order 1. An older copy may still be named All records.

`auth_forbidden` has two causes, and the error message says which. If it says saved views are off for this workspace, say that saved views aren’t available here. Any other `auth_forbidden` means this key can’t access the workflow or its views. Say so and don’t retry until access is fixed. Either way, don’t retry in a loop.

Run `clay workflows views <subcommand> --help` for the full JSON shape and error codes. These commands read and write one workflow:

- `clay workflows views list <workflowId> [--limit <n>] [--offset <n>]`
- `clay workflows views get <workflowId> <viewId>`
- `clay workflows views create <workflowId> --input <json|file|->`
- `clay workflows views update <workflowId> <viewId> --input <json|file|->`
- `clay workflows views delete <workflowId> <viewId>`

## Before you change a view

List the views first. `order` is the position in that list. A view with `order` null is hidden from the list. A new view that omits `order` goes at the end of the list. Send `order` only to place it somewhere else, for example one past the highest `order` you just listed (All results is 1, so the next view is at least 2).

`list` returns pages of at most 100. When the response includes `nextOffset`, pass it as `--offset` to get the rest.

Read the view with `get` before an update that changes `derivedColumns`, `columnOrder`, `columnVisibility`, or `columnWidths`. Each of those replaces the whole value. To add an extracted column or a pin, send the existing list plus the new entry.

## What you can set

Send camelCase JSON. On create, `name` is required. On update, send at least one field.

- `filterQuery` is a runs select query, the same language as `clay workflows runs query reference`. Example: `select from runs where status = "failed"`. `null` clears the filter. A query that is not `select from runs ...` is rejected.
- `columnVisibility` is either `{ "default": "show", "hiddenColumnKeys": ["inputs"] }` or `{ "default": "hide", "shownColumnKeys": ["inputs"] }`.
- `columnOrder.pinned` is the pinned column keys, left to right. `columnOrder.root.root` is the full left-to-right order of the main table. Put pinned keys in that order too. `null` clears pins and custom order.
- `derivedColumns` extracts a field. Each item is `{ "id", "label", "sourceColumnKey", "sourceColumnType", "path" }`. `sourceColumnType` is `input` or `output`. `path` is a JSONPath such as `$.person.email`. Ids must be unique; use `tdc_<uuid>` for a new one.
- `columnWidths` maps a column key to a width in pixels.

Column keys: `inputs` is the Inputs column, a node column’s key is the node id, and an extracted column’s key is its `derivedColumns` id.

## What you cannot do

All results can change its extracted columns and column layout. Its name, order, and filter cannot, and it cannot be deleted. No other view can be named All results or All records at order 1. Deleting any other view removes the view and its settings only. Runs stay. Deleting can ask for approval; present that request and wait, and don’t run the delete again after it is approved. Creating a view is not idempotent: if the result is uncertain, list the views before creating it again.
