# Cloudinary Plugin

A coding agent plugin that brings Cloudinary's media management capabilities directly into your coding workflow. It connects your AI coding agent to Cloudinary's MCP servers and provides a documentation skill so you can get accurate, up-to-date answers about Cloudinary APIs without leaving your editor.

## What's Included

### MCP Servers (`mcp.json`)

Five Cloudinary MCP servers are pre-configured:

| Server | Description |
|---|---|
| `cloudinary-asset-mgmt` | Upload, manage, and transform media assets |
| `cloudinary-env-config` | Configure your Cloudinary environment settings |
| `cloudinary-smd` | Work with structured metadata on assets |
| `cloudinary-analysis` | Analyze images and videos with AI |
| `cloudinary-mediaflows` | Build and run automated media workflows |

> **Auth:** The first four servers use OAuth2 — you'll be prompted to log in with your Cloudinary account on first use via Cursor or Claude. The `cloudinary-mediaflows` server requires your Cloudinary credentials (`cloud_name`, `api_key`, `api_secret`) to be set in the headers in `mcp.json`.

### Skills

Three skills are included to give the agent deep Cloudinary knowledge:

| Skill | Directory | Description |
|---|---|---|
| **cloudinary-docs** | `skills/cloudinary-docs/` | Answers Cloudinary questions by fetching live documentation from `cloudinary.com/documentation/llms.txt`, ensuring accurate and up-to-date responses with real code examples. |
| **cloudinary-transformations** | `skills/cloudinary-transformations/` | Creates and debugs Cloudinary transformation URLs from natural language. Covers resize/crop, generative AI effects, video transformations, overlays, named transformations, and cost optimization — with a built-in validation checklist and debugging guide. |
| **claimable-cloud** | `skills/claimable-cloud/` | Provisions a working Cloudinary cloud with a single command and no signup (a Claimable Cloud) when no credentials are available — claimable by email within 24 hours to keep as a permanent free account. |

## Getting Started

1. Install this plugin in Cursor/Claude Code.
2. On first use, approve the OAuth login prompt for the MCP servers. For `cloudinary-mediaflows`, add your credentials to its headers in `mcp.json`. No Cloudinary account yet? Just ask the agent to set one up — the **claimable-cloud** skill provisions a free working cloud with no signup (claim it by email within 24 hours to keep it).
3. Ask your agent anything about Cloudinary — uploads, transformations, metadata, analysis, and more. The agent will automatically use the right skill and MCP server for the job.
