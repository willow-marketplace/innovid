---
type: llm
---

PASS if the response preserves the selected federal grant opportunity and plans to use Search_Federal_Grant_Opportunities, or its connected namespaced equivalent, for the typed record lookup. It should then retrieve the actual funding notice, application instructions, and controlling amendments before extracting requirements, keeping missing facts unverified. Search_GovTribe may be mentioned as a fallback or disambiguation tool, but a federal-contract lookup must not replace the known grant target. The answer must not claim that it fetched data or created a matrix during this dry run. FAIL if any condition is unmet.
