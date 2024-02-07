(ns automaton-optimization.date.bucket-test
  (:require
   #?@(:clj [[clojure.test :refer [deftest is testing]]
             [automaton-optimization.date-test :as optimization-date-test]
             [automaton-optimization.date.bucket :as sut]])))

;; Not working in lfe-test when it's in :cljc mode
;; Also worth checking why tests in browsers don't catch it
#?(:clj
     (deftest protocol-test
       (testing "Are invalid data detected as such in"
         (is (mapv sut/make-bucket-date [:a :b])))
       (testing
         "Is the sut/BucketDate protocol meets the Date protocol expectations?"
         (is (empty? (optimization-date-test/validate-date-protocol-constraints
                      (partial sut/make-random-date 10 200)
                      (partial sut/make-random-delay-bucket 10 14)))))))
