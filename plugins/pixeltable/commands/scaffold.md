---
name: scaffold
description: Scaffold a new Pixeltable project from an official template or structural pattern.
---

Scaffold a new Pixeltable project using the official `pixeltable-new` generator.

Arguments: `$ARGUMENTS`

Steps:

1. Run `uvx pixeltable-new --list` FIRST to see the patterns and templates available on the installed version. Never invent or guess a name — only use one that appears in that output. There are two kinds:
   - **Structural patterns** (`serving` (default), `backend`, `batch`) — bare API/pipeline scaffolds. Always available.
   - **Application templates** — a full app (schema + API/UI) for a use case, each layered on a pattern. Current set: `knowledge-base`, `chat-agent`, `audio-transcription`, `video-search`, `media-indexing`, `image-dataset`, `full-stack-showcase`.

2. Choose a target by use case. Each template maps to a pattern; if the template is unavailable on this version, the pattern is your fallback:
   - "RAG app" / docs+images+video+audio upload + unified search + Q&A → `knowledge-base` → fallback `--backend`.
   - chatbot / tool-calling agent / persistent memory / MCP → `chat-agent` → fallback `--backend`.
   - audio / podcast / transcription + summarization → `audio-transcription` → fallback `--backend`.
   - video frames / detection / transcription / temporal search → `video-search` → fallback default `serving`. Run with `pxt serve videointel`.
   - enterprise media / S3 ingest / process all modalities / export → `media-indexing` → fallback `--batch`.
   - ML dataset / auto-annotate / curate / version / PyTorch export → `image-dataset` → fallback `--batch`.
   - complete reference app (Gemini + DETR + Whisper, React UI) → `full-stack-showcase` → fallback `--backend`.
   - headless API, no specific template fit → `--backend` directly.
   - one-shot ingest-compute-export → `--batch` directly.
   - unsure → default `serving`.

3. Pick a fresh project directory name (the generator refuses to write into an existing directory). Then generate:

```bash
uvx pixeltable-new --template knowledge-base my-rag-app   # template
uvx pixeltable-new my-app --backend                       # structural pattern, no --template
```

4. If the `--template` command reports an unknown name or "No files found" / "restructured":
   - Re-check `--list` and use a listed canonical name (or a legacy alias shown there, e.g. `video-intel` → `video-search` in pixeltable-new 0.4.2+).
   - Upgrade: `uvx --from pixeltable-new==0.4.2 pixeltable-new --template video-search my-app`
   - Last resort: run the mapped structural pattern (`serving`, `backend`, or `batch`) and adapt from [workflows.md](../skills/pixeltable-skill/references/workflows.md) (e.g. Video Analysis Pipeline).
   - If the directory already exists (including empty dirs left by a failed scaffold), choose a new name or remove the empty directory — do not delete a populated project without asking.
   - Do NOT retry guessed template names.

5. State clearly which template or pattern you actually used (and, if you fell back, why). Then follow the **Next steps** the generator prints:
   - **pxt-serve templates** (no `app.py`): `uv sync` → `uv run python schema.py` → `uv run pxt serve <service-name>` — `video-search` → `videointel`, `media-indexing` → `pipeline`, `image-dataset` → `datalab`.
   - **`app.py` templates**: `uv sync` → `uv run python app.py`. A `pxt serve` route set exists as an API-only alternative (`knowledge-base` → `kb`, `chat-agent` → `agent`, `audio-transcription` → `audiointel`, `full-stack-showcase` → `sitewatch`) — do NOT run `app.py` and `pxt serve` at the same time; they bind the same port.
   - **`full-stack-showcase`**: build the React UI first — `cd frontend && npm install && npm run build && cd ..` — then `uv run python app.py`, or the UI 404s.
   - **`serving`** (default pattern): `uv sync` → `uv run python schema.py` → `uv run pxt serve pipeline`
   - **`backend`**: `uv sync` → `uv run python setup_pixeltable.py` → `uv run uvicorn main:app --reload`
   - Do NOT hand-write boilerplate the scaffold already provides.