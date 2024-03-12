(ns automaton-optimization.date.bucket-test
  (:require
   [automaton-optimization.date-test :as optimization-date-test]
   [automaton-optimization.date.bucket :as sut]
   #?@(:clj [[clojure.test :refer [deftest is testing]]]
       :cljs [[cljs.test :refer [deftest is testing] :include-macros true]])))

(deftest protocol-test
  (testing "Are invalid data detected as such in"
    (is (mapv sut/make-bucket-date [:a :b])))
  (testing
    "Is the sut/BucketDate protocol meets the Date protocol expectations?"
    (is (empty? (optimization-date-test/validate-date-protocol-constraints
                 #(sut/make-random-date 10 200)
                 #(sut/make-random-delay-bucket 10 14))))))

(optimization-date-test/validate-date-protocol-constraints
 #(sut/make-random-date 10 200)
 #(sut/make-random-delay-bucket 10 14))

;; {{:msg "A random date, added with a positive delay creates a new date with different time", :test-name :automaton-optimization.date-test/add-delay-creates-new-date, :expect-fn-res false, :result #automaton-optimization.date.bucket.BucketDate{:bucket-nb 118, :delay-date 129}}
;;  {:msg "A random date, substracted with a positive delay creates a new date with different time", :test-name :automaton-optimization.date-test/substract-delay-creates-new-date, :expect-fn-res false, :result #automaton-optimization.date.bucket.BucketDate{:bucket-nb 118, :delay-date 108}}}
