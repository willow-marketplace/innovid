---
name: revenue-report
description: Revenue report from Shopify, Amazon Seller Central, WooCommerce, Stripe, PayPal, QuickBooks, Xero and other commerce or finance sources
---

# /revenue-report

Show revenue, orders and trends from the connected commerce and finance sources.

## Instructions

1. Call `get_connectors` and pick the connected revenue sources: e-commerce (`shopify`, `amazon_sp`, `woocommerce`, `bigcommerce`), payments (`stripe`, `paypal_transaction`, `braintree`, `chargebee`) and accounting (`quickbooks`, `xero`). Ask which to use if both a store and an accounting system are connected, since they count the same sales twice.
2. Read field ids with `get_fields` and call `get_data` by week for the last 90 days (or the user's range): orders or charges or invoices, gross revenue, refunds, and net revenue where available.
3. Report the weekly trend, the period total against the previous period, top products or customers, and refunds as a share of revenue. Keep currencies separate.
4. If ad sources are also connected, offer a blended view: revenue next to ad spend by week, labelled as not attributed.