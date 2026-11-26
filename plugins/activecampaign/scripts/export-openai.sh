#!/usr/bin/env bash
# Build the ChatGPT (OpenAI) plugin package from this repo's skills.
#
# The skills here are the source of truth. This script reshapes them for
# OpenAI's skill format and keeps the existing ChatGPT listing's identity
# (app id, publisher, descriptions) by reusing the previous package's manifest.
#
# Usage:
#   scripts/export-openai.sh <previous-package.zip|dir> <new-version> [release-notes-file]
# Output:
#   dist/openai/                              the unpacked package
#   dist/activecampaign-chatgpt-<version>.zip the zip to submit
set -euo pipefail

root=$(cd "$(dirname "$0")/.." && pwd)
prev=${1:?usage: $0 <previous-package.zip|dir> <new-version> [release-notes-file]}
version=${2:?usage: $0 <previous-package.zip|dir> <new-version> [release-notes-file]}
notes=${3:-}
out="$root/dist/openai"
mcp_url=$(sed -nE 's/.*"url": *"([^"]+)".*/\1/p' "$root/mcp.json" | head -1)
[[ -n "$mcp_url" ]] || { echo "no url in mcp.json" >&2; exit 1; }

# Previous package: unzip into a scratch dir if needed.
work=$(mktemp -d); trap 'rm -rf "$work"' EXIT
if [[ -f "$prev" ]]; then unzip -q "$prev" -d "$work/prev"; prev="$work/prev"; fi
[[ -f "$prev/.codex-plugin/plugin.json" ]] || { echo "no .codex-plugin/plugin.json in previous package" >&2; exit 1; }

rm -rf "$out"; mkdir -p "$out/.codex-plugin" "$out/assets" "$out/skills"

# Manifest: same identity, new version, optional new release notes.
python3 - "$prev/.codex-plugin/plugin.json" "$out/.codex-plugin/plugin.json" "$version" "$notes" <<'PY'
import json, sys
src, dst, version, notes = sys.argv[1:5]
m = json.load(open(src))
m["version"] = version
if notes:
    m.setdefault("extensions", {}).setdefault("com.openai", {}).setdefault("publication", {})["release_notes"] = open(notes).read().strip()
else:
    print("warning: release_notes kept from the previous package; pass a notes file to replace them", file=sys.stderr)
json.dump(m, open(dst, "w"), indent=2)
open(dst, "a").write("\n")
PY
cat > "$out/.mcp.json" <<JSON
{
  "mcpServers": {
    "activecampaign": {
      "url": "$mcp_url"
    }
  }
}
JSON
cp "$root/assets/activecampaign-icon.png" "$out/assets/"

allowed='name description license allowed-tools metadata'
status=0
for dir in "$root"/skills/*/; do
  name=$(basename "$dir")
  dest="$out/skills/$name"; mkdir -p "$dest/agents"
  src="$dir/SKILL.md"
  compat=$(awk 'NR==1&&/^---$/{f=1;next} f&&/^---$/{exit} f&&/^compatibility:/{sub(/^compatibility: */,"");print}' "$src")

  # Frontmatter minus `compatibility` (OpenAI's validator rejects it); the
  # requirement moves into the body, right under the title.
  awk -v compat="$compat" '
    NR==1 && /^---$/ { infm=1; print; next }
    infm && /^---$/  { infm=0; print; next }
    infm && /^compatibility:/ { next }
    !infm && !done && /^# / { print; print ""; if (compat != "") print "> " compat; done=1; next }
    { print }
  ' "$src" > "$dest/SKILL.md"

  # Validate the frontmatter the way OpenAI's quick_validate.py does.
  keys=$(awk 'NR==1&&/^---$/{f=1;next} f&&/^---$/{exit} f&&/^[a-z-]+:/{sub(/:.*/,"");print}' "$dest/SKILL.md")
  for k in $keys; do
    [[ " $allowed " == *" $k "* ]] || { echo "$name: frontmatter key '$k' not allowed" >&2; status=1; }
  done
  fm_name=$(sed -nE 's/^name: *//p' "$dest/SKILL.md" | head -1)
  desc=$(sed -nE 's/^description: *//p' "$dest/SKILL.md" | head -1)
  [[ "$fm_name" == "$name" ]] || { echo "$name: name '$fm_name' doesn't match folder" >&2; status=1; }
  (( ${#fm_name} <= 64 )) || { echo "$name: name over 64 characters" >&2; status=1; }
  (( ${#desc} <= 1024 )) || { echo "$name: description over 1024 characters" >&2; status=1; }
  [[ "$desc" != *"<"* && "$desc" != *">"* ]] || { echo "$name: description contains angle brackets" >&2; status=1; }

  meta="$root/openai/$name.yaml"
  [[ -f "$meta" ]] || { echo "$name: missing openai/$name.yaml" >&2; status=1; continue; }
  { cat "$meta"; cat <<YAML

dependencies:
  tools:
    - type: "mcp"
      value: "activecampaign"
      description: "ActiveCampaign marketing automation and CRM tools"
      transport: "streamable_http"
      url: "$mcp_url"
YAML
  } > "$dest/agents/openai.yaml"
done
(( status == 0 )) || { echo "export failed validation" >&2; exit 1; }

zip_path="$root/dist/activecampaign-chatgpt-$version.zip"
rm -f "$zip_path"
(cd "$out" && zip -qr "$zip_path" .codex-plugin .mcp.json assets skills)
echo "built $zip_path"
