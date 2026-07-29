"""Shared test environment guards.

Tests build sandboxes by pointing HOME at a temp directory. That is only half
a sandbox: CLAUDE_CONFIG_DIR relocates Claude Code's whole config tree, so a
developer who exports it (per-project accounts, direnv) had the real variable
follow the code straight out of the fixture and into their actual store.

It stayed invisible while the plugin hardcoded ~/.claude and simply ignored the
variable — the leak and the bug cancelled out. Honouring it (#166) exposed both
at once: five existing tests started reading the developer's real config tree.
Cleared here rather than in each fixture, so the next sandbox is hermetic
without anyone having to remember why.
"""

from __future__ import annotations

import os
import subprocess
from pathlib import Path

import pytest


@pytest.fixture(autouse=True)
def _isolate_claude_config_dir(monkeypatch):
    """Keep the ambient CLAUDE_CONFIG_DIR out of every test.

    A test that wants it set does so explicitly, which then means what it says
    rather than inheriting whatever the developer happens to run.
    """
    monkeypatch.delenv("CLAUDE_CONFIG_DIR", raising=False)


# Git repo-selection variables. Any one of these, if exported in the shell
# that launches pytest, overrides `-C <dir>` and `cwd=<dir>` alike — that is
# documented git precedence, not a bug — so a test that builds its own repo
# under tmp_path and passes it explicitly via -C/cwd is STILL not isolated
# from an ambient GIT_DIR. This bit us for real (2026-07): running the suite
# from inside a git worktree, with a leaked GIT_DIR in the launching shell,
# flipped core.bare on the real repo, moved HEAD via stray commits, deleted
# a tracked file, and registered a pytest tmp dir as a live worktree of it.
# Every git subprocess call in tests/ already used -C or cwd= correctly; the
# leak happened anyway. The fix belongs here, once, for the whole suite —
# matching the same unset the plugin's own git-backup hook applies to itself
# (hooks.d/after_save/50-git-backup.sh: "Prevent outer git env vars from
# overriding git -C behaviour").
_GIT_REPO_SELECTION_ENV_VARS = (
    "GIT_DIR",
    "GIT_WORK_TREE",
    "GIT_INDEX_FILE",
    "GIT_COMMON_DIR",
    "GIT_OBJECT_DIRECTORY",
    "GIT_ALTERNATE_OBJECT_DIRECTORIES",
    "GIT_CEILING_DIRECTORIES",
    "GIT_NAMESPACE",
)


@pytest.fixture(autouse=True)
def _sanitize_ambient_git_env(monkeypatch):
    """Strip leaked GIT_* repo-selection vars before every test.

    A test that wants one of these set does so explicitly (as
    test_leaked_git_dir_does_not_disable_the_guard does, via its own env
    dict passed straight to subprocess.run) — never by inheriting whatever
    happened to be exported in the shell pytest was launched from.
    """
    for _var in _GIT_REPO_SELECTION_ENV_VARS:
        monkeypatch.delenv(_var, raising=False)


def _clean_git_env() -> dict:
    """A copy of the ambient environment with repo-selection vars stripped.

    Used only by the regression guard below, which must sanitize its own
    fingerprinting calls independently of the autouse fixture above (the
    guard's session-scoped snapshot runs outside any per-test monkeypatch).
    """
    return {k: v for k, v in os.environ.items() if k not in _GIT_REPO_SELECTION_ENV_VARS}


def _repo_fingerprint(repo_root: Path) -> str:
    """A cheap, sensitive snapshot of repo state: bareness, HEAD, worktrees,
    working-tree status. Any test-suite escape that mutates the repo pytest
    is running from will change at least one of these."""
    env = _clean_git_env()

    def _git(*args: str) -> str:
        result = subprocess.run(
            ["git", "-C", str(repo_root), *args],
            env=env, capture_output=True, text=True,
        )
        return result.stdout.strip()

    return "\n".join([
        f"bare={_git('config', '--get', 'core.bare')}",
        f"head={_git('rev-parse', 'HEAD')}",
        f"worktrees={_git('worktree', 'list')}",
        f"status={_git('status', '--porcelain')}",
    ])


@pytest.fixture(scope="session", autouse=True)
def _guard_launching_repo_is_not_mutated():
    """Fail the run if the suite mutates the repo pytest was launched from.

    Session-scoped so it brackets the whole run: snapshot before any test,
    compare after the last one. This is the regression guard for the exact
    incident described above — if a future test (or a reverted fix)
    reopens the leak, this fails loudly instead of silently corrupting
    whatever repo the developer happened to be sitting in.
    """
    repo_root = Path(__file__).resolve().parent.parent
    before = _repo_fingerprint(repo_root)
    yield
    after = _repo_fingerprint(repo_root)
    assert before == after, (
        "Test suite mutated the repo it was launched from — this is the "
        "exact GIT_DIR-leak class of bug tracked by "
        "_sanitize_ambient_git_env. DO NOT ignore this.\n"
        f"--- before ---\n{before}\n--- after ---\n{after}"
    )
