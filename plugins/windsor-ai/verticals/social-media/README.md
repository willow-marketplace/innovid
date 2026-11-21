# Windsor.ai for Social Media

Publish posts and Reels, answer comments, replies and reviews, and report on organic social across Instagram, Facebook Pages, LinkedIn Pages, Threads, TikTok, YouTube and Google Business Profile from Claude through [Windsor.ai](https://windsor.ai). This plugin connects only your social accounts, so its skills, commands and agent stay on social media work.

For ads, analytics, CRM, e-commerce and the other 350+ Windsor.ai sources, use the main [Windsor.ai plugin](https://github.com/windsor-ai/claude-windsor-ai-plugin). The main plugin also covers these seven platforms, so install one or the other: with both, Claude sees two copies of the same tools.

> This repository is generated from the main plugin repository by `scripts/build_vertical.py` there. Fix shared skills and commands in the main repository and rebuild; edit only the files under `verticals/social-media/` there for anything specific to this plugin.

## What This Plugin Does

- **Publish after you approve:** Reels, image, carousel and story posts on Instagram; text, link and photo posts on Facebook Pages; text, link and image posts on LinkedIn company pages; text and image posts on Threads; local posts with a call-to-action button on Google Business Profile. One message can be adapted and cross-posted to several accounts.
- **Answer comments, replies and reviews:** find Instagram comments and Threads replies the account has not answered, reply to them, hide or delete Instagram comments; find Google reviews without an owner reply and publish replies
- **Manage a Google Business Profile listing:** opening hours, holiday hours, temporary closure, description, website, phone numbers, categories, attributes, services, service area, address and photos
- **Report:** reach, views, engagement, saves, shares, reposts, follower growth and watch time by account, post or video; Reels watch time and skip rate on Instagram, Reels plays on Facebook, Shorts on YouTube; best posts per platform; Google Business Profile views, calls, direction requests and ratings

Claude shows every post, reply and listing change and waits for your yes before it runs. Posts and replies are public as soon as they run. TikTok and YouTube are read-only, Facebook Pages cannot publish Reels or videos, Facebook and TikTok comments can be read but not answered, and LinkedIn and YouTube report comment counts only. The current write actions per platform are listed in the [Windsor.ai documentation](https://windsor.ai/documentation/windsor-mcp/write-actions/).

## Installation

In Claude Code:

```
/plugin marketplace add windsor-ai/claude-windsor-social-media-plugin
/plugin install windsor-social-media@windsor-social-media
```

Or from the command line:

```bash
claude plugin marketplace add https://github.com/windsor-ai/claude-windsor-social-media-plugin
claude plugin install windsor-social-media@windsor-social-media
```

## Setup

After installing, run `/mcp`, select `windsor-social-media` and sign in with your Windsor.ai account. Sign-up happens in the same browser flow. Then ask Claude to "get started" or run `/social-report`.

## Slash Commands

| Command | Description |
|---------|-------------|
| `/social-report` | Reach, engagement, follower change and the three best posts per platform for the last 30 days |
| `/instagram-report` | Instagram posts and Reels from the last 30 days by reach, saves, shares and watch time, against the account average |
| `/publish-post` | Draft one post or Reel, adapt it per platform and publish it after you approve every version |
| `/comment-inbox` | Instagram comments and Threads replies with no answer from the account, with drafted replies, posted or hidden after you approve |
| `/review-replies` | Google reviews without an owner reply, with drafted replies, published after you approve |

## Skills

Claude loads these automatically when a request matches, or you can call them by name.

| Skill | Use it for |
|-------|------------|
| `/social-media-get-started` | Sign in, connect or reconnect accounts, first report |
| `/social-media-insights` | Reports, rankings, comparisons and trends across platforms |
| `/social-media-publish` | New posts and Reels on Instagram, Facebook Pages, LinkedIn Pages, Threads and Google Business Profile |
| `/social-media-engage` | Instagram comments, Threads replies and Google reviews |
| `/instagram`, `/facebook-organic`, `/linkedin-pages`, `/threads`, `/tiktok-organic`, `/youtube`, `/google-business-profile` | Platform-specific fields, actions and limits |

## Agent

- **Social Media Analyst** handles multi-step work: which formats and topics drive reach, why reach dropped, a weekly social review, or the same review across every client or brand. It proposes posts and replies; you confirm them in the main conversation. Read-only: it cannot call write tools.

## Supported Platforms

Instagram (Business and Creator accounts) · Facebook Pages · LinkedIn company pages · Threads · TikTok · YouTube · Google Business Profile

Threads is reachable on the social media endpoint once Windsor.ai's server change for it is deployed; until then, Threads requests on this plugin return "not available on this endpoint".

## Examples

```
> Which of my Instagram Reels from last month kept people watching longest, and how do they compare with carousels?

> Post this announcement to our LinkedIn page, Facebook Page and Threads, with the blog link

> Show me Instagram comments and Threads replies from this week we haven't answered and draft replies

> Reply to every Google review from the last 30 days that has no answer yet

> Set our Google Business Profile hours for 24 to 26 December: closed on the 25th, 10:00 to 14:00 on the other two days

> Compare Shorts and regular videos on our YouTube channel by average view percentage
```

## Troubleshooting

- **Sign-in fails or tools stop responding:** run `/mcp`, select `windsor-social-media`, re-authenticate and sign in again.
- **A platform shows no accounts or asks you to log in again:** reconnect the account in Windsor.ai at [onboard.windsor.ai](https://onboard.windsor.ai), or ask Claude to "connect my Instagram account" for a setup link.
- **Instagram comment actions return a permission error:** the account was connected before comment access was added; reconnect it.
- **A post or change is refused because write actions are off:** turn them on at [onboard.windsor.ai/app/settings/account](https://onboard.windsor.ai/app/settings/account).
- **A field is rejected:** field names differ by platform. Ask Claude to check them with `get_fields`.
- **Anything else:** email [support@windsor.ai](mailto:support@windsor.ai).

## Privacy

The plugin contains no hooks and no local code. Claude sends requests to Windsor.ai's social media MCP server at `mcp.windsor.ai/social_media`, which reads data from, and publishes approved posts and changes to, the accounts you have connected in Windsor.ai. Windsor.ai processes these requests under its [Privacy Statement](https://windsor.ai/privacy-statement/).

Windsor.ai is an independent product and is not affiliated with or endorsed by the platforms it connects to.

## Links

- [Windsor.ai](https://windsor.ai)
- [Windsor.ai for Social Media connector in the Claude directory](https://claude.ai/directory/windsor-ai-for-social-media)
- [Write actions documentation](https://windsor.ai/documentation/windsor-mcp/write-actions/)
- [Terms of Service](https://windsor.ai/terms-of-service/)

## License

MIT
