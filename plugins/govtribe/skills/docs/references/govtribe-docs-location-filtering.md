<!-- GovTribe Skills generated documentation reference. Do not edit; regenerate from the canonical public GovTribe Docs page. -->

# Location filtering

- Canonical GovTribe Docs page: [https://govtribe.com/docs/govtribe-for-agents/guides/location-filtering](https://govtribe.com/docs/govtribe-for-agents/guides/location-filtering)

Use location filters when a GovTribe MCP search request needs to narrow results by place of performance, vendor location, state, county, city, ZIP code, jurisdiction, or another geography.

## Choose the location meaning

First decide what the location represents. Different search tools expose different location concepts.

| Location meaning | Use this when | Common fields |
| --- | --- | --- |
| Place of performance | The work happens in a location, regardless of where the buyer or vendor is based. | `place_of_performance_ids` |
| Vendor location | The organization is based, registered, or enriched at a location. | `vendor_location_ids` |
| State scope | The record belongs to a state-level public-sector market. | `state_ids` |
| Jurisdiction scope | The record belongs to a county, city, school district, or other jurisdiction. | `jurisdiction_ids` |

Do not use a location filter as a substitute for a buyer, vendor, or agency filter. For example, a vendor in Virginia and work performed in Virginia are different constraints.

## Field value types

Always check the selected MCP tool schema before sending a location filter. Location-like fields can accept different value types.

| Field pattern | Accepted values |
| --- | --- |
| `place_of_performance_ids` | GovTribe location IDs or uniquely resolvable location text for countries, cities, ZIP or postal codes, states, territories, counties, and supported facility aliases. Compound locality or facility plus state values such as `Huntsville AL` or `Fort Benning GA` are accepted. |
| `vendor_location_ids` | GovTribe location IDs or uniquely resolvable location text for countries, cities, ZIP or postal codes, states, territories, counties, and supported facility aliases. Compound values such as `Huntsville, AL` are accepted. |
| `state_ids` | GovTribe state IDs, case-insensitive USPS state codes such as `VA`, or full state and territory names. |
| `jurisdiction_ids` | GovTribe jurisdiction IDs or FIPS codes. |

These are runtime value rules. The MCP tool field names and JSON Schemas do not change.

## How location values resolve

GovTribe resolves every value independently and in a fixed order:

1. Use an existing GovTribe location ID as-is.
2. Match exact searchable location text already indexed for an existing GovTribe location after normalizing harmless case, punctuation, whitespace, and terminal state-name or USPS-code differences. Formatted display labels and nested address components are not separate match sources.
3. Match a bounded alias for a known renamed place or installation, such as `Fort Benning GA`, when that alias is explicitly associated with an existing GovTribe location.
4. Use fixed U.S. context to evaluate location candidates without requester-location bias. A candidate is accepted only when exactly one candidate maps to an existing GovTribe location.

  If no existing record matches, or more than one record remains equally valid, the tool returns a validation error for the original field and array position. Retry with a candidate GovTribe ID or a more specific value. GovTribe does not drop the value, broaden the search, or substitute a city, county, or state for an unresolved facility.

When an earlier result already includes a nested location object, prefer its `govtribe_id` over repeating free text. Use [Source identifiers and record matching](https://govtribe.com/docs/data-model/guides/source-identifiers-and-record-matching) when you need to decide whether a returned value is a GovTribe record ID, source identifier, code, or display label.

## Operators

Use `<filter_key>_operator` when the tool exposes it.

| Operator | Behavior |
| --- | --- |
| `in` | Include records matching the supplied values. |
| `not_in` | Exclude records matching the supplied values. |

If the operator is omitted, search tools generally use the default include behavior listed on the tool page.

## Common location tasks

### Search by place of performance

Use `place_of_performance_ids` when the user cares where contract work, grant work, forecasted work, or opportunity performance will happen.

Tool: `Search_Federal_Contract_Opportunities`

```json
{
  "place_of_performance_ids": ["Virginia"],
  "place_of_performance_ids_operator": "in",
  "fields_to_return": [
    "govtribe_id",
    "name",
    "place_of_performance",
    "federal_agency",
    "due_date"
  ]
}
```

### Search by vendor location

Use `vendor_location_ids` on vendor searches when the user wants organizations in a region, not work performed there.

Tool: `Search_Vendors`

```json
{
  "vendor_location_ids": ["Huntsville, AL"],
  "vendor_location_ids_operator": "in",
  "fields_to_return": ["govtribe_id", "name", "uei", "location"]
}
```

### Search state and local markets

Use `state_ids` for broad state scope. A GovTribe state ID, USPS code, or full state or territory name resolves to the same canonical state when the match is unique. Add `jurisdiction_ids` only when the user names a specific local buyer or local-government geography and you have a usable GovTribe ID or FIPS code.

Tool: `Search_State_And_Local_Contract_Opportunities`

```json
{
  "state_ids": ["VA"],
  "state_ids_operator": "in",
  "jurisdiction_ids": ["51059"],
  "jurisdiction_ids_operator": "in",
  "fields_to_return": ["govtribe_id", "name", "state", "jurisdictions", "due_date"]
}
```

### Exclude a location

Use `not_in` when the user asks to remove a location from an otherwise useful cohort.

Tool: `Search_Federal_Contract_Awards`

```json
{
  "award_date_range": {
    "from": "now-12M/d",
    "to": "now/d"
  },
  "place_of_performance_ids": ["California", "TX"],
  "place_of_performance_ids_operator": "not_in",
  "fields_to_return": ["govtribe_id", "name", "place_of_performance", "dollars_obligated"]
}
```

## Examples

Place of performance by state text:

Tool: `Search_Federal_Contract_Awards`

```json
{
  "place_of_performance_ids": ["Virginia"],
  "place_of_performance_ids_operator": "in"
}
```

Exclude one place of performance:

Tool: `Search_Federal_Contract_Awards`

```json
{
  "place_of_performance_ids": ["California", "TX"],
  "place_of_performance_ids_operator": "not_in"
}
```

Vendor location by ZIP code:

Tool: `Search_Vendors`

```json
{
  "vendor_location_ids": ["20001"]
}
```

Place of performance by compound facility and state:

Tool: `Search_Federal_Contract_Opportunities`

```json
{
  "place_of_performance_ids": ["Fort Benning GA"]
}
```

State filter by USPS code:

Tool: `Search_State_And_Local_Contract_Opportunities`

```json
{
  "state_ids": ["VA"],
  "state_ids_operator": "in"
}
```

Jurisdiction filter by FIPS code:

Tool: `Search_State_And_Local_Contract_Opportunities`

```json
{
  "jurisdiction_ids": ["51059"],
  "jurisdiction_ids_operator": "in"
}
```

## Related articles

- [MCP tools](https://govtribe.com/docs/govtribe-for-agents/tools): Review each tool's filter names, operators, and accepted value notes.
- [Search federal contract awards](https://govtribe.com/docs/govtribe-for-agents/tools/search-federal-contract-awards-mcp-tool): Review a tool with place-of-performance filtering.
- [Search vendors](https://govtribe.com/docs/govtribe-for-agents/tools/search-vendors-mcp-tool): Review a tool with vendor-location filtering.
- [Search state and local contract opportunities](https://govtribe.com/docs/govtribe-for-agents/tools/search-state-and-local-contract-opportunities-mcp-tool): Review state and jurisdiction filters.
- [Source identifiers and record matching](https://govtribe.com/docs/data-model/guides/source-identifiers-and-record-matching): Decide when a value is a GovTribe ID, source identifier, or code.

---

For current tool schemas, parameters, response fields, or freshness-sensitive behavior, call the live `Documentation` MCP tool instead of inferring details from this bundled reference.
