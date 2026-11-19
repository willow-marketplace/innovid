# VCL sandbox

`fastly vcl check`, `run`, and `serve` upload to public Fiddle.
Get public-upload authorization; exclude credentials and private code, origins, headers, and bodies.
Use Falco for local-only tests.
No Fastly token or service ID needed.

Supply subroutine bodies, not full VCL files or `sub vcl_*` wrappers.
Name files by slot (`recv.vcl`, `fetch.vcl`) or use explicit flags (`--recv FILE`, `--fetch FILE`).
Use `--init FILE` for tables, ACLs, and helper subroutines.

```bash
printf '%s\n' 'set req.http.X-Test = "cli";' > recv.vcl

# Compile without executing; nonzero exit on compile failure
fastly vcl check recv.vcl --json

# Start with a cold cache; HTTP 4xx/5xx does not fail run
fastly vcl run recv.vcl --path /example --fresh-cache --json > result.json &&
  jq -e '.response.status == 200 and .response.body_complete == true' result.json

# Local listener, remote VCL execution; redeploy on edits
fastly vcl serve recv.vcl --watch
# In another terminal, after deployment:
curl -sS http://127.0.0.1:7676/example
```

- `--origin URL`: default `https://http-me.fastly.dev`; repeat for up to five backends, `F_origin_0` through `F_origin_4`, first is default.
  Origins must be reachable from Fastly; localhost does not reach your machine.
- `run` reuses one cache across invocations, so a repeat can HIT; `--fresh-cache` switches to a new empty cache.
- `run` retries can repeat requests and their effects; use idempotent requests.
- `check` and `run` share one sandbox recorded next to the CLI config; run them sequentially, since parallel calls overwrite each other's VCL.
- `run`/`serve --timeout`: default `2m`; increase for slow edge sync.
- `check --json`: `{valid, diagnostics}`, one-based diagnostic lines.
- `run --json`: `{request, response, events, origin_fetches}`; compile failure instead returns `{valid: false, diagnostics}` with nonzero exit.
- Check `response.body_complete` before treating `response.body_preview` as the whole body.
- Empty `diagnostics`, `events`, and `origin_fetches` are `null`; iterate with `.diagnostics[]?`, `.events[]?`, and `.origin_fetches[]?`.
- `run`: `--verbose` conflicts with `--json`.
  `serve` accepts neither `run` request flags nor `--json`; send requests with an HTTP client.

Use `fastly vcl run --help` for request, geo, shielding, and protocol flags.
Keep the fastly-fiddle helper for JSON specs, multiple requests, DSL assertions, and existing/shareable fiddle IDs; the CLI accepts neither specs nor IDs.
For production configuration, use `fastly service vcl ...` and `fastly service version validate`.
