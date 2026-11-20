---
name: explore
description: Detailed instructions and examples for developing explore dashboard resources in Rill
---

# Instructions for developing an explore dashboard in Rill

## Introduction

Explore dashboards are resources that configure an interactive, drill-down dashboard for a metrics view. They are Rill's default dashboard type, designed for explorative slice-and-dice analysis of a single metrics view.

Explore dashboards are lightweight resources that sit downstream of a metrics view in the project DAG. Their reconcile logic is fast (validation only), so they can be created and modified freely without performance concerns.

### When to use explores vs canvases

- **Explore dashboards:** Best for explorative analysis, drill-down investigations, and letting users freely slice data by any dimension.
- **Canvas dashboards:** Best for fixed reports, executive summaries, or combining multiple metrics views into a single view.

## Development approach

**Check for an existing inline explore first.** Most metrics views already emit an explore dashboard: any metrics view with `version: 1` and an `explore:` block, and any legacy metrics view without a `version:` property. If the target metrics view already emits an explore, do NOT create a stand-alone `type: explore` file for it; that produces a second, duplicate dashboard. Instead, edit the `explore:` block in the metrics view file. If you are a sub-agent restricted to a different path, report this back to the parent agent instead of writing a duplicate file.

Only create a stand-alone explore file when the metrics view needs more than one explore dashboard, or when the user explicitly asks for a separate file.

Explore dashboards require minimal configuration. In most cases, you only need to:

1. Reference the metrics view
2. Select which dimensions and measures to expose (usually all, indicated by `'*'`)
3. Optionally configure defaults and time ranges

**Best practice:** Keep explore configurations simple. Only add advanced features (security policies, custom themes, restricted dimensions) when there is a clear requirement. The metrics view already defines the business logic; the explore just controls presentation and access.

## Inline explores in metrics views

The default way to create an explore is inline in the metrics view file: set `version: 1` and add an `explore:` block, which emits an explore resource with the same name as the metrics view (or `name:` if set):

```yaml
# metrics/sales.yaml
version: 1
type: metrics_view
display_name: Sales Analytics

model: sales_model
timeseries: order_date

dimensions:
  - column: region
  - column: product_category

measures:
  - name: total_revenue
    expression: SUM(revenue)

# Inline explore configuration
explore:
  display_name: Sales Dashboard
  dimensions: '*'  # Optional: dimensions to expose ('*', a list, or {exclude: [...]}); defaults to all
  measures: '*'    # Optional: measures to expose ('*', a list, or {exclude: [...]}); defaults to all
  time_ranges:
    - P7D
    - P30D
    - P90D
  defaults:
    time_range: P30D
```

For legacy reasons, metrics views without `version:` auto-emit an explore even without an `explore:` block; metrics views with `version: 1` only emit one when the block is present.

Use inline explores to keep the metrics view and its dashboard configuration together. Use separate explore files only when you need multiple explores for the same metrics view (for example, a second dashboard that exposes a restricted subset of dimensions), and give each one a distinct name so it does not collide with the inline explore.

## Stand-alone explore example with annotations

The examples below are for stand-alone explore files. Only use them in the cases described above; otherwise put the same properties in the metrics view's `explore:` block.

Note that most explore dashboards work great without any of the optional properties shown here.

```yaml
# dashboards/sales_explore.yaml

# Required: resource type
type: explore

# Required: the metrics view this dashboard renders
metrics_view: sales_metrics

# Optional: display name shown in the navigation and header
display_name: "Sales Performance"

# Optional: informational banner at the top of the dashboard
banner: "Data refreshes daily at 6 AM UTC"

# Optional: which dimensions to expose (use '*' for all)
dimensions: '*'

# Optional: which measures to expose (use '*' for all)
measures: '*'

# Optional: customize the time range dropdown
time_ranges:
  - P7D
  - P30D
  - P90D
  - P12M
  - rill-MTD
  - rill-YTD

# Optional: default dashboard state on first load
defaults:
  time_range: P30D
  comparison_mode: time
  # Default filter: one Metrics SQL filter expression (not a map by metrics view as in a canvas)
  filters: "region = 'EMEA'"

# Optional: filters that always stay in the filter bar (pinned),
# and filters that must have a value before the dashboard renders (required)
filters:
  pinned:
    - product_category
  required:
    - region

# Optional: pin specific time zones to the top of the selector
time_zones:
  - America/Los_Angeles
  - America/New_York

# Optional: custom theme
theme: my_theme

# Optional: restrict access to specific users.
# Note: usually you should do this in the metrics view, not the explore resource.
security:
  access: "{{ .user.admin }} OR '{{ .user.email }}' LIKE '%@example.com'"
```

## Default, pinned and required filters

`filters.pinned` and `filters.required` list dimensions or measures by name. Every name must be shown in the explore, otherwise the explore fails to reconcile. They control different things:

- **`pinned`**: keeps the filter in the filter bar, even without a value, so users can change it without opening the filter menu. Users cannot remove it.
- **`required`**: the dashboard is not rendered until the filter has a value. Required filters are implicitly pinned; do not also list them under `pinned`.

`defaults.filters` is a single Metrics SQL filter expression that is applied when the dashboard is first opened, for example `"region = 'EMEA' AND product_category IN ('Shoes', 'Bags')"`. It can only reference dimensions and measures shown in the explore. Filter on a measure with a subquery, for example `"region IN (SELECT region FROM metrics_view HAVING total_revenue > 1000)"`.

Use `required` for filters that are unsafe or meaningless to omit (for example a tenant or customer scope), and pair it with a value in `defaults.filters` when the dashboard should open without prompting. These settings shape the dashboard only; use security policies on the metrics view to restrict the data a user can access.

All three also work in a metrics view's inline `explore:` block.

## Minimal stand-alone example

When a stand-alone file is warranted, a minimal explore is sufficient:

```yaml
type: explore
metrics_view: sales_metrics
display_name: "Sales Dashboard"
dimensions: '*'
measures: '*'
```

## Reference documentation

Here is a full JSON schema for the explore syntax:

```
allOf:
    - properties:
        ai_prompts:
            $ref: '#/definitions/ai_prompts_properties'
        allow_custom_time_range:
            description: Defaults to true, when set to false it will hide the ability to set a custom time range for the user.
            type: boolean
        banner:
            description: Refers to the custom banner displayed at the header of an explore dashboard
            type: string
        defaults:
            additionalProperties: false
            description: |
                Defines the defaults YAML struct.
                  ```yaml
                  defaults: #define all the defaults within here
                    dimensions:
                      - dim_1
                      - dim_2
                    measures:
                      - measure_1
                      - measure_2
                    time_range: P1M
                    comparison_mode: dimension #time, none
                    comparison_dimension: filename
                    filters: "country = 'US' AND device IN ('mobile', 'tablet')"
                  ```
            properties:
                comparison_dimension:
                    description: For dimension mode, specify the comparison dimension by name.
                    type: string
                comparison_mode:
                    description: 'Controls how to compare current data with historical or categorical baselines. Options: `none` (no comparison), `time` (compares with past based on default_time_range), `dimension` (compares based on comparison_dimension values)'
                    enum:
                        - none
                        - time
                        - dimension
                    type: string
                dimensions:
                    $ref: '#/definitions/field_selector_properties'
                    description: Provides the default dimensions to load on viewing the dashboard
                filters:
                    description: Default filter applied when a user initially loads the dashboard, written as a Metrics SQL `WHERE` expression, for example `country = 'US' AND device IN ('mobile', 'tablet')`. It can only reference dimensions and measures shown in the explore. An explore has a single metrics view, so this is one expression and not a map keyed by metrics view as on a canvas dashboard.
                    type: string
                measures:
                    $ref: '#/definitions/field_selector_properties'
                    description: Provides the default measures to load on viewing the dashboard
                time_range:
                    description: Refers to the default time range shown when a user initially loads the dashboard. The value must be either an [ISO 8601 duration](https://en.wikipedia.org/wiki/ISO_8601#Durations) (for example, PT12H for 12 hours, P1M for 1 month, or P26W for 26 weeks) or one of the [Rill ISO 8601 extensions](https://docs.rilldata.com/reference/rill-iso-extensions#extensions).
                    type: string
            type: object
        description:
            description: Refers to the description of the explore dashboard
            type: string
        dimensions:
            $ref: '#/definitions/field_selector_properties'
            description: List of dimension names. Use '*' to select all dimensions (default)
            examples:
                - dimensions:
                    - country
                - dimensions:
                    exclude:
                        - country
                - dimensions:
                    expr: ^public_.*$
        display_name:
            description: Refers to the display name for the explore dashboard
            type: string
        embeds:
            additionalProperties: false
            description: Configuration options for embedded dashboard views
            properties:
                hide_pivot:
                    description: When true, hides the pivot table view in embedded mode
                    type: boolean
            type: object
        filters:
            additionalProperties: false
            description: |
                Configures the filters of the explore dashboard.
                  ```yaml
                  filters:
                    pinned:
                      - device
                    required:
                      - advertiser
                  ```
            properties:
                pinned:
                    description: Dimension or measure names to pin in the filter bar. Pinned filters stay visible even when they have no value, and viewers cannot remove them. Each name must be a measure or a dimension shown in the explore, other than a time dimension.
                    items:
                        type: string
                    type: array
                required:
                    description: Dimension or measure names that must have a value before the explore renders. Required filters are implicitly pinned. Combine with `defaults.filters` so the explore opens with a value already set. Each name must be a measure or a dimension shown in the explore, other than a time dimension.
                    items:
                        type: string
                    type: array
            type: object
        lock_time_zone:
            description: When true, the dashboard will be locked to the first time provided in the time_zones list. When no time_zones are provided, the dashboard will be locked to UTC
            type: boolean
        measures:
            $ref: '#/definitions/field_selector_properties'
            description: List of measure names. Use '*' to select all measures (default)
            examples:
                - measures:
                    - sum_of_total
                - measures:
                    exclude:
                        - sum_of_total
                - measures:
                    expr: ^public_.*$
        metrics_view:
            description: Refers to the metrics view resource
            type: string
        security:
            $ref: '#/definitions/dashboard_security_policy_properties'
            description: Security rules to apply for access to the explore dashboard
        theme:
            description: Name of the theme to use. Only one of theme and embedded_theme can be set.
            oneOf:
                - description: Name of an existing theme to apply to the dashboard
                  type: string
                - $ref: '#/definitions/theme_properties'
                  description: Inline theme configuration.
        time_ranges:
            description: |
                Overrides the list of default time range selections available in the dropdown. It can be a string or an object with a 'range' and optional 'comparison_offsets'.
                  ```yaml
                  time_ranges:
                    - PT15M // Simplified syntax to specify only the range
                    - PT1H
                    - PT6H
                    - P7D
                    - range: P5D // Advanced syntax to specify comparison_offsets as well
                    - P4W
                    - rill-TD // Today
                    - rill-WTD // Week-To-date
                  ```
            items:
                $ref: '#/definitions/explore_time_range_properties'
            type: array
        time_zones:
            description: Refers to the time zones that should be pinned to the top of the time zone selector. It should be a list of [IANA time zone identifiers](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)
            items:
                type: string
            type: array
        type:
            const: explore
            description: Refers to the resource type and must be `explore`
            type: string
      required:
        - type
        - metrics_view
      title: Properties
      type: object
    - $ref: '#/definitions/common_properties'
description: Explore dashboards provide an interactive way to explore data with predefined measures and dimensions.
id: explore-dashboards
title: Explore Dashboard YAML
type: object
```