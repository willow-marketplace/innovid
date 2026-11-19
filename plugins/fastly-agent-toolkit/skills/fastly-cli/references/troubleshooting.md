# Fastly CLI Troubleshooting

## 503 "hostname doesn't match against certificate"

When `--override-host` differs from `--address`, you MUST set `--ssl-cert-hostname` and `--ssl-sni-hostname` to the origin's actual hostname (the one its TLS certificate covers), NOT the override-host value. Without these flags, Fastly validates the cert against the override-host and fails. **Always set all four flags together**: `--address`, `--override-host`, `--ssl-cert-hostname`, `--ssl-sni-hostname`. Check the origin's certificate SANs with: `echo | openssl s_client -connect ORIGIN:443 2>/dev/null | openssl x509 -noout -text | grep -A1 "Subject Alternative Name"`

## 403/400 on domain create

Both command families are valid: `fastly service domain create --name HOST --version VERSION` manages a service-version domain, while `fastly domain create --fqdn HOST` uses the versionless Domain Management API.
Use the versioned command for the shared `*.global.ssl.fastly.net` test-domain workflow.
For a 400, inspect the API error detail for invalid or already-registered names; for a 403, check account access and token permissions rather than treating top-level `domain` as unsupported.

## "version is locked"

Use `--autoclone` or clone first with `fastly service version clone`.

## New service setup

Version 1 is unlocked: add domain, backend, and snippets all on `--version 1`, then validate and activate once.
`--autoclone` is unnecessary but does not clone an editable version.
For a live service, clone once and reuse the returned version number; repeated `--version active --autoclone` commands can create separate clones and scatter changes.

## VCL commands

Use `fastly service vcl snippet ...` and `fastly service vcl custom ...` for production service configuration; top-level snippet/custom paths are deprecated aliases.
`fastly vcl check`, `run`, and `serve` instead use a public sandbox and do not update your service.

## `--content` and missing files

On snippet/custom VCL commands, `--content` reads the file if the path exists, otherwise uploads the value as inline VCL. A mistyped path becomes the VCL body.

On `fastly service vcl snippet describe`, `--content` means "print only the raw VCL body". It cannot be combined with `--json` or `--verbose`.

## Test domains

Use a name you choose (e.g. `my-app.global.ssl.fastly.net`), not the service ID. `SERVICE_ID.global.ssl.fastly.net` does NOT work. Adding `foo.global.ssl.fastly.net` automatically makes `foo.freetls.fastly.net` available (HTTP/2).

## `--json` not supported on all commands

`fastly service create` does not support `--json`. Parse the text output (e.g. `SUCCESS: Created service XXXXX`) instead. Always check if `--json` is accepted before relying on JSON output.

## Propagation error sequence

After activating a new service, expect this progression: 500 "Domain Not Found" (10-30s, domain not yet known at edge) -> 503 backend errors (if backend config is wrong) -> 200 (working). If you see 503 right after 500 clears, check the backend configuration. If you see 503 "hostname doesn't match against certificate", fix the SSL hostname settings. A 503 that appears after a working 200 usually means a backend issue, not propagation.

## TLS subscription flags

The CLI flag for certificate authority is `--cert-auth` (not `--certificate-authority`). Always check CAA records with `dig CAA DOMAIN +short` before choosing a CA — mismatched CAA records cause `blocked` authorization state. To get DNS challenge details, you must use `--include tls_authorizations --json` — without `--include`, challenges are null. The `--include` flag only affects JSON output; text output always omits challenges.

## Token safety for REST API calls

Use `$(fastly auth token)` in the authenticated command; no output filtering is needed.
To select a specific stored token, use `$(fastly auth token --token TOKEN_NAME)`.
The command refuses terminal output, but agent-captured stdout may be non-terminal, so never run it standalone or echo its result.

## Debug mode

`fastly --debug-mode <command>` and `FASTLY_DEBUG_MODE=true` expose API tokens in output.
Do not run commands with debug mode enabled in an agent session, even at the user's request.
If debug mode is needed, ask the user to run it in a separate terminal and share only a summary with credentials and other private data removed, not the raw output.
