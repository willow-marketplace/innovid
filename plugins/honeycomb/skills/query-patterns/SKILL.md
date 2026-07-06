---
name: query-patterns
description: >
---
# Honeycomb Query Patterns

Opinionated guidance for writing effective Honeycomb queries. The MCP tools already
document their parameters and schemas — this skill focuses on *when* and *why* to
use each pattern, not *how* to call the tools.

## Key Principles

1. **Never use AVG for latency** — AVG hides tail latency. Use P99 (or P95/P90) to see what slow users experience. Reserve AVG for non-latency metrics like payload size.
2. **Use HEATMAP for distributions** — Single-number aggregates hide bimodal patterns. HEATMAP reveals whether you have one population or two.
3. **Combine calculations in one query** — `COUNT, P99(duration_ms), HEATMAP(duration_ms)` in a single query reduces API calls and gives a complete picture.
4. **Start broad, narrow with WHERE** — Begin with a COUNT/GROUP BY to understand shape, then add filters to focus.
5. **Check for prior work** — Call `find_queries` before writing new queries. Someone may have already answered the question.

## Choosing the Right Operation

| Question | Use |
|----------|-----|
| How much traffic? | `COUNT` grouped by route or service |
| How many unique users/IPs? | `COUNT_DISTINCT(field)` |
| How fast for most users? | `P50(duration_ms)` |
| How fast for the worst-off users? | `P99(duration_ms)` |
| Is there a bimodal pattern? | `HEATMAP(duration_ms)` |
| What's the worst case? | `MAX(duration_ms)` |
| How many concurrent operations? | `CONCURRENCY` |
| Is it getting worse over time? | `RATE_AVG(duration_ms)` |

## Relational Field Strategy

Use relational prefixes to ask cross-span questions within a trace:

- **"Show me slow endpoints caused by a specific downstream"**: Filter with `any.service.name` to find traces where that service participates, group by `root.http.route` to see which user-facing endpoints are affected.
- **"What's different about errored traces?"**: Filter with `any.error = true`, group by `root.name` to see which entry points have errors somewhere in their trace tree.
- **Exclude noise**: `none.service.name = "health-check"` removes traces containing health checks.

## Calculated Fields

Calculated fields are per-event expressions evaluated at query time. They transform,
classify, and combine existing fields without re-instrumenting code.

**Three scopes** — choose the narrowest that fits the need:
- **Query-scoped** (not saved): exploratory, one-off analysis
- **Dataset-level** (saved): reusable within one service's dataset
- **Environment-level** (saved): reusable across all datasets (e.g., `error_pct`)

**Common patterns:**
- **Error rate**: `MUL(IF($error, 1, 0), 100)` → use `AVG(error_pct)` to get percentage
- **Status classification**: `IF(GTE($http.status_code, 500), "5xx", GTE($http.status_code, 400), "4xx", "ok")`
- **Latency bucketing**: `BUCKET($duration_ms, 500, 0, 3000)`
- **Prefix routing**: `IF(STARTS_WITH($url, "/admin"), "admin", STARTS_WITH($url, "/api"), "api", "other")`
- **Exact-match classification**: use `SWITCH` instead of `IF(EQUALS(...))` chains — same expression, more efficient

**Key guardrails:**
- **Don't create presentational (alias-only) fields** — a field that just renames another field adds no analytical value and clutters the schema. Only save a calculated field when it does real computation (classification, extraction, math).
- **Avoid regex on large/complex fields** — running `REG_MATCH`, `REG_VALUE`, or `REG_COUNT` on `exception.stacktrace`, `db.statement`, or full log lines can be very slow. Check whether a more targeted OTel field exists first (`exception.type`, `exception.message`, `db.operation`). If you must regex a long field, guard it with a `CONTAINS` check first.
- **`EQUALS` has strict type matching** — `EQUALS($http.status_code, 200)` silently returns false if the field is stored as a string. Use `find_columns` to verify the field type before comparing.
- **`FORMAT_TIME` is expensive** — avoid in high-volume queries.
- **Save query-scoped, not dataset-level, for one-off work** — saved fields show up in everyone's schema.

For full syntax, operator reference, and extended anti-pattern examples, consult
`${CLAUDE_PLUGIN_ROOT}/skills/query-patterns/references/calculated-fields.md`.

## Before Every Query

- **Filter on `is_root`** when measuring user-facing latency — without it, internal spans inflate the numbers
- **Use human-readable time ranges** (`"24h"`, `"-6h"`) — epoch timestamps are error-prone and hard to review
- **Validate columns with `find_columns` before querying** — confirms field names exist and prevents empty results

## Interpreting Results

After running a query, the MCP tool returns formatted markdown plus metadata.
The most important metadata field is `query_result_json` — a signed URL to the raw
JSON result. For precise analysis, download it and parse with jq or python rather
than relying solely on the ASCII rendering.

Key interpretation rules:
- **P99/P50 > 10x** — bimodal distribution likely; run HEATMAP to confirm
- **TOTAL row** in breakdown results = aggregate across all groups
- **OTHER row** = groups beyond the query limit (increase limit if OTHER is large)
- **ASCII heatmap** `▁▂▃▄▅▆▇█` = density from low to high; two bands = two populations
- **query_run_pk** in metadata — feed directly to `run_bubbleup` for outlier analysis

## Additional Resources

### Reference Files
- **`${CLAUDE_PLUGIN_ROOT}/skills/query-patterns/references/visualize-operations.md`** — Complete VISUALIZE operation reference with examples
- **`${CLAUDE_PLUGIN_ROOT}/skills/query-patterns/references/relational-fields.md`** — Detailed relational field guide with cross-service patterns
- **`${CLAUDE_PLUGIN_ROOT}/skills/query-patterns/references/query-examples.md`** — Extensive query cookbook organized by use case
- **`${CLAUDE_PLUGIN_ROOT}/skills/query-patterns/references/result-interpretation.md`** — Guide to interpreting query results, raw JSON access, and statistical heuristics
- **`${CLAUDE_PLUGIN_ROOT}/skills/query-patterns/references/calculated-fields.md`** — Calculated field syntax, full operator reference, common patterns, and anti-patterns (presentational fields, expensive string ops, type mismatches)

### Cross-References
- For the structured investigation workflow that uses these query patterns: **production-investigation** skill
- For SLO interpretation and burn alert design: **slos-and-triggers** skill