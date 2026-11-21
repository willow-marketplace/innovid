#!/usr/bin/env python3
"""Build a vertical plugin bundle from this repository.

A vertical (for example ``social-media``) is a plugin that points at one of
Windsor.ai's use-case MCP endpoints and ships a subset of the skills, commands
and agents maintained here, plus a few files of its own. The shared files are
copied, never edited by hand, so a fix in this repository reaches every
vertical on the next build.

Usage:

    python3 scripts/build_vertical.py social-media --output ../claude-windsor-social-media-plugin

The output directory is the root of the vertical plugin (usually a clone of
its repository). Generated paths inside it are replaced; anything else, such
as ``.git``, is left alone. Run it again after changing a shared file and
commit the diff in the vertical repository.
"""

from __future__ import annotations

import argparse
import json
import re
import shutil
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
MAIN_MANIFEST = ROOT / ".claude-plugin" / "plugin.json"
MAIN_MCP = ROOT / ".mcp.json"

# Paths the build owns inside the output directory.
GENERATED_PATHS = (
    ".claude-plugin",
    ".mcp.json",
    "skills",
    "commands",
    "agents",
    "assets",
    "LICENSE",
    "README.md",
    "LISTING.md",
)

# Skill and command references look like /name; only names that exist in this
# repository count, so URLs such as /app/settings never trigger the check.
REFERENCE = re.compile(r"(?<![\w/`.])/([a-z0-9][a-z0-9-]*)")


def load_json(path: Path) -> dict:
    """Read a JSON file."""
    return json.loads(path.read_text(encoding="utf-8"))


def tool_prefix(plugin_name: str, server_name: str) -> str:
    """Return the tool-name prefix Claude Code gives a plugin's MCP server."""
    return f"mcp__plugin_{plugin_name}_{server_name}__"


def copy_dir(source: Path, destination: Path) -> None:
    """Copy a directory tree, replacing whatever is at the destination."""
    if destination.exists():
        shutil.rmtree(destination)
    shutil.copytree(source, destination)


def copy_file(source: Path, destination: Path) -> None:
    """Copy one file, creating parent directories."""
    destination.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(source, destination)


def rewrite(path: Path, replacements: dict[str, str]) -> None:
    """Apply the vertical's text replacements to one Markdown file."""
    text = path.read_text(encoding="utf-8")
    for old, new in replacements.items():
        text = text.replace(old, new)
    path.write_text(text, encoding="utf-8")


def write_manifest(config: dict, main: dict, output: Path) -> None:
    """Write the vertical's plugin.json, inheriting author and policy URLs."""
    manifest = {
        "name": config["name"],
        "displayName": config["displayName"],
        "version": config["version"],
        "description": config["description"],
        "author": main["author"],
        "repository": config["repository"],
        "homepage": main["homepage"],
        "documentationUrl": f"{config['repository']}#readme",
        "supportUrl": main["supportUrl"],
        "privacyPolicyUrl": main["privacyPolicyUrl"],
        "termsOfServiceUrl": main["termsOfServiceUrl"],
        "icon": main["icon"],
        "keywords": config["keywords"],
        "license": main["license"],
    }
    (output / ".claude-plugin").mkdir(parents=True, exist_ok=True)
    (output / ".claude-plugin" / "plugin.json").write_text(
        json.dumps(manifest, indent=2, ensure_ascii=False) + "\n", encoding="utf-8"
    )
    marketplace = {
        "name": config["name"],
        "owner": {"name": main["author"]["name"]},
        "description": config["marketplaceDescription"],
        "plugins": [
            {
                "name": config["name"],
                "displayName": config["displayName"],
                "description": config["description"],
                "source": "./",
            }
        ],
    }
    (output / ".claude-plugin" / "marketplace.json").write_text(
        json.dumps(marketplace, indent=2, ensure_ascii=False) + "\n", encoding="utf-8"
    )


def write_mcp(config: dict, output: Path) -> None:
    """Write the vertical's .mcp.json pointing at its use-case endpoint."""
    server = config["mcp"]
    (output / ".mcp.json").write_text(
        json.dumps(
            {
                server["server"]: {
                    "type": "http",
                    "url": server["url"],
                    "description": server["description"],
                }
            },
            indent=2,
            ensure_ascii=False,
        )
        + "\n",
        encoding="utf-8",
    )


def repo_names() -> set[str]:
    """Return every skill and command name defined in this repository."""
    skills = {path.name for path in (ROOT / "skills").iterdir() if path.is_dir()}
    commands = {path.stem for path in (ROOT / "commands").glob("*.md")}
    return skills | commands


def bundle_names(output: Path) -> set[str]:
    """Return every skill and command name shipped in the built bundle."""
    skills = {path.name for path in (output / "skills").iterdir() if path.is_dir()}
    commands = {path.stem for path in (output / "commands").glob("*.md")}
    return skills | commands


def check_bundle(output: Path, main_server: str, main_prefix: str) -> list[str]:
    """Return the problems that make the bundle unsafe to publish."""
    problems: list[str] = []
    known = repo_names()
    shipped = bundle_names(output)
    for path in sorted(output.rglob("*.md")):
        text = path.read_text(encoding="utf-8")
        relative = path.relative_to(output)
        if f"`{main_server}`" in text:
            problems.append(f"{relative}: still names the main server `{main_server}`")
        if main_prefix in text:
            problems.append(f"{relative}: still uses the main tool prefix {main_prefix}")
        for name in sorted(set(REFERENCE.findall(text))):
            if name in known and name not in shipped:
                problems.append(f"{relative}: refers to /{name}, which is not in the bundle")
    return problems


def build(vertical: str, output: Path) -> list[str]:
    """Build one vertical into the output directory and return any problems."""
    source = ROOT / "verticals" / vertical
    config = load_json(source / "vertical.json")
    main = load_json(MAIN_MANIFEST)
    main_server = next(iter(load_json(MAIN_MCP)))
    main_prefix = tool_prefix(main["name"], main_server)
    prefix = tool_prefix(config["name"], config["mcp"]["server"])
    replacements = {main_prefix: prefix, **config.get("replacements", {})}

    output.mkdir(parents=True, exist_ok=True)
    for name in GENERATED_PATHS:
        target = output / name
        if target.is_dir():
            shutil.rmtree(target)
        elif target.exists():
            target.unlink()

    for skill in config["skills"]:
        copy_dir(ROOT / "skills" / skill, output / "skills" / skill)
    for command in config["commands"]:
        copy_file(ROOT / "commands" / f"{command}.md", output / "commands" / f"{command}.md")
    for agent in config["agents"]:
        copy_file(ROOT / "agents" / f"{agent}.md", output / "agents" / f"{agent}.md")
    for path in output.rglob("*.md"):
        rewrite(path, replacements)

    # The vertical's own files win over shared ones with the same path.
    for path in source.rglob("*"):
        if path.is_file() and path.name != "vertical.json":
            copy_file(path, output / path.relative_to(source))

    copy_file(ROOT / "LICENSE", output / "LICENSE")
    copy_file(ROOT / main["icon"], output / main["icon"])
    write_manifest(config, main, output)
    write_mcp(config, output)
    return check_bundle(output, main_server, main_prefix)


def main() -> int:
    """Parse arguments, build the vertical and report problems."""
    parser = argparse.ArgumentParser(description=__doc__.split("\n\n")[0])
    parser.add_argument("vertical", help="name of a directory under verticals/")
    parser.add_argument("--output", required=True, type=Path, help="plugin root to write")
    args = parser.parse_args()
    problems = build(args.vertical, args.output.resolve())
    for problem in problems:
        print(f"error: {problem}", file=sys.stderr)
    if problems:
        return 1
    print(f"built {args.vertical} into {args.output}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
