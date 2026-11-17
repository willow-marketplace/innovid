---
name: youtube
description: Analyze YouTube channel, playlist, and video performance through Windsor.ai. Use when the user asks about their YouTube channel's organic insights — views, watch time, average view duration, subscribers gained and lost, likes, comments, shares, or traffic sources, at the channel, playlist, or video level (including comparing Shorts vs. regular videos). Do not use for YouTube Ads, other platforms, or publishing or editing videos.
---

# YouTube via Windsor.ai

Windsor.ai pulls YouTube channel, playlist, and video analytics from the
YouTube Analytics API. The connector id is `youtube`. Pass `youtube` as the connector on every tool call; the Windsor.ai server
serves all connectors, so it is never implied.

This connector is **read-only** YouTube analytics. It has **no ad or cost
data** (YouTube Ads run through Google Ads, so route ad-spend questions
there), and it does **not** publish or edit videos or change channel
settings — there are no write actions. If asked to upload, edit, or manage
videos, say that's out of scope here.

Golden rules:

- **Never guess field, account, or option names** — get them from
  `get_fields`, `get_connectors`, and `get_options`. Call those first.
- Read-only connector; there are no live write actions to run.
- Report what the user asked for, concisely.

## 1. Ground yourself first

Call `get_connectors` to see which YouTube channels are connected (each has
an id and usually a channel name). If none is connected, help the user
connect: call `get_connector_authorization_url` (or
`get_connector_connect_info` for the auth type and steps) and give them the
setup link — don't describe manual dashboard navigation. If several channels
are connected and the request is ambiguous, ask which one.

## 2. Reading YouTube data

1. Call `get_fields` for `youtube` to get valid field ids — channel,
   playlist, and video metrics (views, watch time, average view duration,
   subscribers gained and lost, likes, comments, shares) and dimensions
   (e.g. video title, content type, traffic source). Use the exact ids
   returned, not these examples.
2. If the analysis needs segmentation controls, call `get_options` to see
   the connector's options and valid values.
3. Call `get_data` with `fields`, the target `account`(s) (channel), and a
   date range (`date_from`/`date_to` or a `date_preset` like `last_7d`,
   `last_30d`, `last_Xd`/`last_Xw`/`last_Xm`, `this_month`, `this_year`,
   `last_year`; append `T` to include today, e.g. `last_30dT`). For ranges
   without a preset (e.g. "this quarter"), compute explicit `date_from`/
   `date_to`. Add `filters` as needed, e.g. to isolate Shorts vs. regular
   videos.

Common requests → recipe:

- **Channel overview / trend:** fields `date` + the metrics, over a preset
  range.
- **Top / worst videos:** fields `video` (or video title) + the metric
  (e.g. watch time), then rank; return only what was asked.
- **Shorts vs. regular videos:** fields for content type/format + the
  metric (e.g. average view percentage), grouped by format.
- **Traffic sources:** fields for traffic source + views, over the
  requested range.

If a metric the user wants isn't in `get_fields`, say so rather than
substituting a different one. There is no cost/spend data here, and
YouTube Ads questions belong in the Google Ads connector.

## 3. Reference real data, don't fabricate

On an error (unknown field/connector/account), call the relevant discovery
tool and retry with correct ids, or report the specific error. Never invent
view counts, subscriber numbers, watch-time figures, or video ids.