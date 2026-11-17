# Submission Reference

## What goes in the official marketplace (claude-plugins-official)

When Anthropic approves your submission, they'll add an entry like this to
`external_plugins/` in https://github.com/anthropics/claude-plugins-official

The entry in `.claude-plugin/marketplace.json` would look something like:

```json
{
  "name": "windsor-ai",
  "description": "Manage and analyze Google Ads, Meta Ads, Instagram, TikTok Ads, LinkedIn Ads and Microsoft Ads from Claude Code through Windsor.ai. Report on campaigns, keywords, search terms, quality score, impression share and cost per conversion, track Instagram post reach, saves and follower growth, and combine it all with GA4, Search Console, HubSpot, Salesforce, Shopify, Stripe and 350+ other sources. Pause or enable campaigns, change budgets, set Google Ads bids and bidding strategies, create Google Ads campaigns and responsive search ads, push negative keywords, publish Instagram posts, carousels, reels and stories, and reply to, hide or delete Instagram comments, with your confirmation before every change.",
  "author": {
    "name": "Windsor.ai",
    "email": "support@windsor.ai"
  },
  "source": {
    "source": "github",
    "repo": "windsor-ai/claude-windsor-ai-plugin"
  }
}
```

## Submission Steps

1. Push this plugin to a public GitHub repo (e.g. github.com/windsorai/claude-code-plugin)
2. Fill out the submission form: https://clau.de/plugin-directory-submission
3. Anthropic reviews for quality and security
4. Once approved, it appears in `/plugin > Discover` inside Claude Code

## Pre-submission Checklist

- [ ] plugin.json has all required fields (name, version, description, author with name+email)
- [ ] .mcp.json points to your remote MCP server URL
- [ ] README.md documents installation, setup, and usage
- [ ] Plugin works when tested locally with `claude --plugin-dir ./windsor-ai-plugin`
- [ ] OAuth flow works correctly for new users connecting their Windsor.ai account
- [ ] All slash commands work end-to-end
- [ ] No secrets or API keys are hardcoded anywhere
