---
name: social-media-create
description: Create new items on Instagram, Facebook Pages, TikTok, YouTube, LinkedIn Pages, and Google Business Profile through Windsor.ai - publish image, video, carousel, and story posts and comment on posts on Instagram; publish text and photo posts on Facebook Pages; publish text and image posts on LinkedIn Pages; create posts, reply to reviews, and update photos, hours, and open status on Google Business Profile. Use when the user wants to create, publish, or post something new. Collect inputs, show the full draft, create only after confirmation.
---

# Create on social media

Create new items on connected social media accounts through Windsor.ai:
publish image, video, carousel, and story posts and comment on posts on Instagram; publish text and photo posts on Facebook Pages; publish text and image posts on LinkedIn Pages; create posts, reply to reviews, and update photos, hours, and open status on Google Business Profile. The main connector ids for this area are instagram, facebook_organic, tiktok_organic, youtube, linkedin_organic, google_my_business. Other sources in the same category work the same way; find their ids with get_connectors.

## Non-negotiable contract
- Interpret create, make, build, publish, post, and launch as a creation request for this skill.
- Gather every required input first, show the complete draft, and create only after an explicit yes. Never create with placeholder values.
- The available create actions and their required fields are whatever list_actions returns for the connector. Build params from that schema, not from memory.
- Create one item per confirmation unless the user approved an explicit list.

## Workflow
1. Call get_connectors to pick the target account; ask if several match.
2. Call list_actions for the connector and read the schema of the create action.
3. Collect the inputs the schema requires, then present the full draft (every field, the account, and what will be live afterwards).
4. After an explicit yes, call execute_action once and report the created item and its state.

## Rules
- Show the exact caption, media, and target account before publishing, and publish only after an explicit yes.
- Media must be a URL the platform can fetch; do not generate images for a post unless the user asks and provides or approves them.
- An Instagram account connected before comment access was added returns a permission error on comments until it is reconnected; explain and offer /windsor-connect.
- Do not generate images, videos, or copy the user did not ask for. If the user wants creative help, draft it as text for approval first.

## Stay in scope
- Changing or removing an existing item (hide, unhide, or delete an Instagram comment; update a Google Business Profile listing or post) follows the same steps: read the item first, show the exact change, and execute only after an explicit yes. Prefer hiding over deleting a comment unless the user asks to delete.
- Reporting -> /social-media-insights.
- Connecting an account -> /windsor-connect.

## Answer with evidence
Report exactly what was created, where, and its status, using the execute_action result.