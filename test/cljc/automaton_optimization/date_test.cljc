(ns automaton-optimization.date-test
  "Prepare test namespaces for all date's implementation"
  (:require
   [automaton-optimization.date :as sut]
   [automaton-core.protocols.binary-relation :as core-protocols-binary-relation]
   [automaton-core.protocols.test-registry :as core-protocols-test-registry]))

(defn validate-date-protocol-constraints
  "Validate the `sut/DateProtocol`'s implementation

  Params:
  * `random-date-builder`
  * `random-delay-builder`"
  [random-date-builder random-delay-builder]
  (let
    [date-stub (random-date-builder)
     date-test-registry
     (merge
      (core-protocols-binary-relation/equivalence
       #'sut/equal-date
       (repeatedly 10 random-date-builder)
       30)
      (core-protocols-binary-relation/total-order
       #'sut/compare-date
       #'sut/equal-date
       (repeatedly 10 random-date-builder)
       30)
      {::add-delay-creates-new-date
       {:msg
        "A random date, with a positive delay creates a new date with different time"
        :result (-> date-stub
                    (sut/add-delay (random-delay-builder)))
        :expect-fn #(and (some? %) (not (sut/equal-date % date-stub)))}
       ::substract-delay-creates-new-date
       {:msg
        "A random date, with a positive delay creates a new date with different time"
        :result (-> date-stub
                    (sut/substract-delay (random-delay-builder)))
        :expect-fn #(and (some? %) (not (sut/equal-date % date-stub)))}})]
    (core-protocols-test-registry/build-report date-test-registry)))
