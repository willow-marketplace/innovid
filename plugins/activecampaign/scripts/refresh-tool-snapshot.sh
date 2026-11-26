#!/usr/bin/env bash
# Rebuild tools/server-tools.txt from a local ac-mcp-server checkout.
# Usage: scripts/refresh-tool-snapshot.sh <path-to-ac-mcp-server>
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
server=${1:?usage: $0 <path-to-ac-mcp-server>}
contracts="$server/tests/mcp/contracts"
[[ -d "$contracts/dedicated" && -d "$contracts/projected" ]] || { echo "no contract snapshots under $contracts" >&2; exit 1; }
rev=$(git -C "$server" rev-parse --short HEAD 2>/dev/null || echo unknown)
day=$(git -C "$server" log -1 --format=%cs 2>/dev/null || date +%F)
{
  echo "# Tool names defined by ac-mcp-server, one per line."
  echo "# Source: ac-mcp-server @ $rev ($day), file names under"
  echo "#   tests/mcp/contracts/dedicated/ and tests/mcp/contracts/projected/."
  echo "# This is what the server can register. A given client may list fewer"
  echo "# (exposure policy, cached tool lists), so skills still say \"if listed\"."
  echo "# Refresh: scripts/refresh-tool-snapshot.sh <path-to-ac-mcp-server-checkout>"
  ls "$contracts/dedicated" "$contracts/projected" | grep '\.json$' | sed 's/\.json$//' | sort -u
} > "$root/tools/server-tools.txt"
echo "wrote tools/server-tools.txt ($(grep -vc '^#' "$root/tools/server-tools.txt") tools)"
