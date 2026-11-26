#!/usr/bin/env bash
# Fail if a skill, agent, or command names a tool the server doesn't define.
# Checks names only; whether each claim about a tool is still true is a review job.
# Usage: scripts/check-tool-refs.sh   (reads tools/server-tools.txt)
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
known=$(mktemp); skip=$(mktemp)
grep -v '^#' "$root/tools/server-tools.txt" | sed '/^$/d' | sort -u > "$known"
grep -v '^#' "$root/tools/not-tools.txt" | sed '/^$/d' | sort -u > "$skip"
verbs='list|get|create|update|add|remove|generate|edit|copy|poll|move|deal|bulk|search|run|prepare|confirm|draft|save|check|is|import|restore|set|unschedule|duplicate|cancel|manage'
refs=$(
  { grep -rhoE 'mcp__plugin_activecampaign_activecampaign__[a-z0-9_]+|mcp__activecampaign__[a-z0-9_]+' \
      "$root/skills" "$root/agents" "$root/commands" | sed -E 's/^mcp__(plugin_activecampaign_)?activecampaign__//'
    grep -rhoE "\`($verbs)_[a-z0-9_]+\`" "$root/skills" "$root/agents" "$root/commands" | tr -d '`'
  } | sort -u | comm -23 - "$skip"
)
missing=$(comm -23 <(printf '%s\n' "$refs" | sed '/^$/d') "$known")
count=$(printf '%s\n' "$refs" | sed '/^$/d' | wc -l | tr -d ' ')
rm -f "$known" "$skip"
if [[ -n "$missing" ]]; then
  echo "Tool names not defined by the server (see tools/server-tools.txt):" >&2
  printf '  %s\n' $missing >&2
  exit 1
fi
echo "ok: $count tool references, all defined by the server"
