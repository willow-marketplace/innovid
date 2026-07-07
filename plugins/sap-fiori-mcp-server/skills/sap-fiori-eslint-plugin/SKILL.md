---
name: sap-fiori-eslint-plugin
description: >
---
# SAP Fiori ESLint Plugin

Work with `@sap-ux/eslint-plugin-fiori-tools` on SAP Fiori projects: set up ESLint from scratch, migrate from a legacy configuration, or run and fix lint issues.

## Determine which task to perform

Identify the user's intent from their request:

| User says / situation | Task | Reference |
|---|---|---|
| "Set up ESLint", "Add ESLint", no `eslint.config.mjs` exists | **Set up** | [references/setup.md](references/setup.md) |
| "Migrate ESLint", `.eslintrc` / eslint@8 present, upgrade ESLint | **Migrate** | [references/migrate.md](references/migrate.md) |
| "Run ESLint", "Check my code", "Fix lint errors", `eslint.config.mjs` exists | **Lint** | [references/lint.md](references/lint.md) |

If the intent is unclear, check the project state:

```bash
# Check for existing ESLint config (any format)
ls eslint.config.mjs eslint.config.js .eslintrc .eslintrc.js .eslintrc.cjs .eslintrc.json .eslintrc.yml .eslintrc.yaml 2>/dev/null
```

- **No config found** → follow [references/setup.md](references/setup.md)
- **Legacy config found** (`.eslintrc*`) → follow [references/migrate.md](references/migrate.md)
- **Flat config found** (`eslint.config.mjs`) → follow [references/lint.md](references/lint.md)


If the intent is still unclear, ask the user to clarify whether they want to set up ESLint, migrate an existing config, or run linting.