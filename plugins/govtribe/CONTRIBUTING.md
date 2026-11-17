# Contributing

Use Claude Code 2.1.292 or later to validate a checkout:

```sh
claude plugin validate . --strict
claude plugin validate .claude-plugin/plugin.json --strict
claude plugin validate skills --strict
```

The plugin folder must contain only text files and PNG, JPEG, GIF or WebP images. Do not add scripts, Office files, archives or files that start with `#!`; the Claude directory holds or refuses them. CI runs the commands above and checks file types.

Skill files are generated from GovTribe's shared skill source. Change them there rather than editing `skills/` here; `docs/skill-source.json` records the hash of every shipped skill file.

The cases in `evals/` use synthetic inputs to check skill selection and responses. To run them:

```sh
claude plugin eval . --trust-plugin --runs 1 --ablation none --no-publish --max-cost-usd 10
```

Evaluations consume Claude usage. They do not enable live MCP calls or verify authentication. Keep evaluation outputs, account data, and credentials out of commits and issue reports.

For connection troubleshooting, see [SETUP.md](SETUP.md). Include reproduction steps and relevant validation results with a pull request.
