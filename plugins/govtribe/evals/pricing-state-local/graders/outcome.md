---
type: llm
---

PASS if the response preserves the selected city procurement and known state/local record types. Its plan should use Search_State_And_Local_Contract_Opportunities for the opportunity, Search_State_And_Local_Contract_Awards for the predecessor award, and Search_State_And_Local_Contract_IDVs for the parent instrument, or their connected namespaced equivalents. It should retrieve actual pricing files and controlling amendments before extracting pricing requirements, distinguish a parent ceiling from the likely procurement price, and keep absent prices and obligations unverified. Search_GovTribe may be offered for unresolved identities, and Documentation may be used for relationships or fields. Federal-only record lookup or federal transaction tools must not replace the known state/local records. The response must not claim it performed live lookups or created an artifact. FAIL if any condition is unmet.
