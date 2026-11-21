---
name: social-media-publish
description: "Publish new organic posts through Windsor.ai: Reels, image, carousel and story posts on Instagram; text, link and photo posts on Facebook Pages; text, link and image posts on LinkedIn company pages; text and image posts on Threads; and local posts with an optional call-to-action button on Google Business Profile. Use when the user wants to post, publish, share or announce something on a social account, or to cross-post one message to several platforms. Draft first, show the exact post, publish only after an explicit yes. Not for TikTok, YouTube or Facebook Reels, which cannot be published here."
---

# Publish social posts

Publish new posts on connected social accounts through Windsor.ai. Publishing actions exist on Instagram (`instagram`), Facebook Pages (`facebook_organic`), LinkedIn Pages (`linkedin_organic`), Threads (`threads`) and Google Business Profile (`google_my_business`). TikTok (`tiktok_organic`) and YouTube (`youtube`) have no write actions, and Facebook Pages has no video or Reel action; say so if asked to post there. The exact set is whatever list_actions returns for the connector; never assume an action exists.

## Non-negotiable contract
- A post is public as soon as it runs, and none of these actions schedules a post for later. Show the exact text, media URL(s), target account and platform, and publish only after an explicit yes to that draft.
- Never publish placeholder text, a draft the user has not seen in full, or media the user did not provide or approve.
- One post per confirmation. For a cross-post, show each platform's version and get one explicit approval for the exact list.
- Do not generate images or videos. If the user wants help with copy, draft it as text for approval first.

## Workflow
1. Call get_connectors to pick the account; ask if several match.
2. Call list_actions for the connector and read the schema of the action that fits the post. Build params from that schema, not from memory.
3. Check the draft against the platform limits below, then show it exactly as it will appear.
4. After an explicit yes, call execute_action once and report the result, including any post id or URL it returns.

## Platform limits (check list_actions for the current schema)
- **Instagram Reels:** `create_video_post` publishes every single video as a Reel. MP4 or MOV, at most 1 GB, 3 seconds to 15 minutes, 9:16 recommended. Cover from `cover_url` (a public JPEG) or `thumb_offset` (milliseconds), not both. `share_to_feed` controls whether the Reel also shows in the main feed (Instagram shows it there by default).
- **Instagram other formats:** `create_image_post` (one JPEG, at most 8 MB, aspect ratio between 4:5 and 1.91:1), `create_carousel_post` (2 to 10 JPEG images in that range, shown in the given order; video items are not supported) and `create_story` (exactly one image (JPEG, at most 8 MB) or video (MP4 or MOV, at most 100 MB, 3 to 60 seconds); no caption; disappears after 24 hours). Captions up to 2,200 characters. All media must be publicly reachable URLs.
- **Facebook Pages:** `create_post` (a message, a link, or both) and `create_photo_post` (an image URL, or a `photo_id` from `upload_photo`, plus an optional caption).
- **LinkedIn Pages:** `create_post` (commentary up to 3,000 characters, a link, or both; a link needs `link_title`, because LinkedIn does not fetch the page title) and `create_image_post` (an https image URL, or an `image_urn` from `upload_image`, plus optional commentary).
- **Threads:** `create_text_post` (text up to 500 characters) and `create_image_post` (a public JPEG or PNG URL plus optional text up to 500 characters).
- **Google Business Profile:** `create_local_post` (summary up to 1,500 characters, optional photo URL (JPG or PNG, at least 250x250 px and 10 KB), optional button: BOOK, ORDER, SHOP, LEARN_MORE or SIGN_UP with a `cta_url`, or CALL, which uses the listing's phone number; `language_code` defaults to en). An existing local post can be changed with `update_local_post`; see the google-business-profile skill.
- **Local image files:** Facebook `upload_photo` and LinkedIn `upload_image` take a file instead of a URL. They run through `upload_files`, which opens an upload widget where the user picks the file; if the widget does not appear, the result includes a curl command the user can run (the link is valid for ten minutes and one upload). The action result is the id to publish with. If `upload_files` is not available on this server, ask the user for a public image URL instead.

## Cross-posting
Adapt one message per platform instead of sending identical text: respect each length limit (Threads 500, Google Business Profile 1,500, Instagram 2,200, LinkedIn 3,000), give LinkedIn a `link_title` when there is a link, and drop the caption for an Instagram story. Show every version before publishing any of them.

## Rules that prevent wrong posts
- If execute_action reports that write actions are not enabled for the account, send the user to https://onboard.windsor.ai/app/settings/account, then retry after they confirm.
- If it reports a missing permission or an expired connection, hand off to /windsor-connect to reconnect the account.
- If the platform rejects the media (format, size, aspect ratio, length), report the reason it gave and ask for a different file; do not retry with the same URL.

## Hand-offs
- Comments, replies and reviews -> /social-media-engage
- Google Business Profile listing edits (hours, address, categories, photos) -> /google-business-profile
- How earlier posts performed -> /social-media-insights

## Answer with evidence
State what was published, where, and the id or URL from the execute_action result. Never claim a post went live without that result.