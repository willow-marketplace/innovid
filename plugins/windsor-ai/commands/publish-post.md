---
name: publish-post
description: Draft one post and publish it to Instagram, Facebook Pages, LinkedIn Pages, Threads or Google Business Profile, or cross-post it to several, after you approve every version
---

# /publish-post

Turn the user's message, link, image or video into a post for one or more connected accounts, and publish it after an explicit yes.

## Instructions

1. Call `get_connectors` and list the connected accounts that can publish: `instagram`, `facebook_organic`, `linkedin_organic`, `threads` and `google_my_business`. Ask which accounts to post to if the user did not say. TikTok and YouTube cannot publish here, and Facebook has no video or Reel action.
2. Collect what the post needs: the text, a link if any, and media as public URLs. For Facebook and LinkedIn a local image file also works if `upload_files` is available: the user picks the file in its upload widget or runs the curl command it returns. Instagram always needs media; a feed video becomes a Reel, and a story can be one image or one video. Do not create images or videos.
3. Call `list_actions` for each target connector and pick the action that fits (for example `create_video_post` for a Reel, `create_image_post`, `create_carousel_post` or `create_story` on Instagram; `create_post` or `create_photo_post` on Facebook; `create_post` or `create_image_post` on LinkedIn; `create_text_post` or `create_image_post` on Threads; `create_local_post` on Google Business Profile).
4. Write one version per platform within its limits (Threads 500 characters, Google Business Profile 1,500, Instagram 2,200, LinkedIn 3,000 with a `link_title` for links; no caption on an Instagram story). Show every version with its account, action and media, and say that each post goes live immediately.
5. After an explicit yes, call `execute_action` once per approved post and report each result with the id or URL it returned. If one platform fails, report its error, do not retry it, and continue with the remaining approved posts.