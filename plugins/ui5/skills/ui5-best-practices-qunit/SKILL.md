---
name: ui5-best-practices-qunit
description: |
---
# QUnit Test Best Practices for UI5

## When to load each reference

| Trigger | Load |
|---|---|
| Writing a new QUnit test file or module from scratch | [`references/writing-new-tests.md`](references/writing-new-tests.md) |
| Modernizing, refactoring, or reviewing existing test code | [`references/modernizing-tests.md`](references/modernizing-tests.md) |
| Migrating from QUnit 1 (globals: `test`, `asyncTest`, `ok`, `stop`, `start`) to QUnit 2 | [`references/modernizing-tests.md`](references/modernizing-tests.md) |
| Any test touches `nextUIUpdate`, `Core.applyChanges`, `assert.async`, fake timers, or event-based async | [`references/async-patterns.md`](references/async-patterns.md) |

Load the reference before producing any output. Do not work from memory.

---

## Core rules (always apply)

| Rule | Detail |
|---|---|
| No `var` | Use `const` or `let`. One declaration per line  -  no comma chains. |
| No `.bind(this)` | Use arrow functions for callbacks that do not need their own `this`. |
| `assert.expect(N)` in every `async` test | Guards against silent passes when async callbacks never fire. Not required for sync tests. |
| `sinon.createSandbox()` | `sinon.sandbox.create()` emits a runtime deprecation warning in Sinon 5+  -  prefer `sinon.createSandbox()`. Alternatively use the QUnit-sinon bridge (`this.stub()`, `this.spy()`, `this.mock()`; `this.clock` only when `sinon.config.useFakeTimers` is truthy). Do not mix both approaches in the same module. |
| Descriptive test names | Sentence describing behavior. Never start with "it should". Unique within each module. |
| `beforeEach` / `afterEach` in every module | Create all controls in `beforeEach`, destroy them in `afterEach`. No shared mutable state between tests. |
| `try/finally` in helper-created controls | Helpers that create a control must destroy it in `finally` so it is cleaned up even when assertions throw. |
| No non-ASCII characters | No non-ASCII characters in comments, strings, or JSDoc. Use plain ASCII hyphens, not em dashes. UTF-8 is required, but non-ASCII in comments has historically caused encoding issues. |
| ESLint  -  0 errors | Warnings for pre-existing patterns (`max-nested-callbacks`, `no-use-before-define`, `valid-jsdoc`) are acceptable. |

---

## Quick-reference checklist

Use when authoring or reviewing a QUnit test file:

- [ ] No `var`  -  use `const` or `let`; one declaration per line (no comma chains)
- [ ] No `.bind(this)`  -  use arrow functions for callbacks that do not need their own `this`
- [ ] No `assert.async()` in simple cases  -  use `async function` + `await new Promise(...)`
- [ ] Every `async` test has `assert.expect(N)`
- [ ] No `sinon.sandbox.create()` in new code  -  use `sinon.createSandbox()` or the bridge (`this.stub()`, `this.spy()`, `this.mock()`); `this.clock` only when fake timers are enabled
- [ ] No `"it should..."` test titles  -  use descriptive sentences
- [ ] Every `QUnit.module` has `beforeEach` / `afterEach` that create and destroy all controls
- [ ] With fake timers: prefer `await nextUIUpdate(this.clock)` over `Core.applyChanges()`; only keep `Core.applyChanges()` when `nextUIUpdate(clock)` cannot handle the case
- [ ] Helper functions that create controls destroy them in `try/finally`
- [ ] No non-ASCII characters in comments or strings (UTF-8 required, but non-ASCII causes encoding issues)