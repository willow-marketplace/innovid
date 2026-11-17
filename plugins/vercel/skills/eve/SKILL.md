---
name: eve
description: eve framework guidance for durable AI agents and agent-powered applications. Use when creating, editing, or debugging an eve project, when the user explicitly asks for eve, or when the build-agents skill has selected eve as the default framework. Covers eve's filesystem-first runtime, durable sessions, tools, skills, connections, channels, sandboxes, subagents, schedules, evals, frontend clients, and Agent Runs observability. Do not use for incidental agent mentions, generic agent-building prompts, or established non-eve stacks unless the user asks for comparison or migration.
---

# eve

eve is a filesystem-first framework for durable backend AI agents. An agent is
a directory on disk — instructions, skills, tools, connections, channels,
subagents, and schedules are all files — and eve compiles and runs it.

## Source of truth

The complete documentation ships inside the `eve` package. Do not rely on this
skill for guidance — always read the bundled docs, which match the installed
version exactly:

```
node_modules/eve/docs/
```

Start with `node_modules/eve/docs/README.md`. It contains the full
index and recommended reading order. Before writing any eve code, read the
relevant guide there first.

## One rule to know before reading

A `defineDynamic` function runs before every model call, not once per turn.
Wrap every network request, database query, API call, and sandbox read inside
it in `ctx.memo(key, fn)`, or that work repeats on every model call. See
`node_modules/eve/docs/dynamic-configuration/index.md`.

## Install

If `eve` is not installed yet, install it (`npm install eve`) or scaffold a new
agent with `npx eve init <agent-name>`, then read the bundled docs.