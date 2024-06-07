(ns automaton-optimization.time-based.impl.storage-strategy.deltas-test
  (:require
   #?(:clj [clojure.test :refer [deftest is]]
      :cljs [cljs.test :refer [deftest is] :include-macros true])
   [automaton-optimization.time-based.impl.storage-strategy
    :as opt-tb-storage-strategy]
   [automaton-optimization.time-based.impl.storage-strategy.deltas :as sut]))

(deftest assoc-date-test
  (is (= {4 :v
          9 :v
          77 :v}
         (-> (sut/make)
             (opt-tb-storage-strategy/assoc-date 9 :v)
             (opt-tb-storage-strategy/assoc-date 4 :v)
             (opt-tb-storage-strategy/assoc-date 77 :v)
             :deltas))))

(deftest get-after-test
  (is (= :v
         (-> (sut/make)
             (opt-tb-storage-strategy/assoc-date 12 :v)
             (opt-tb-storage-strategy/get-after 10)))))

(deftest get-before-test
  (is (= :v
         (-> (sut/make)
             (opt-tb-storage-strategy/assoc-date 2 :v)
             (opt-tb-storage-strategy/get-before 10)))))

(deftest get-exact-test
  (is (= :v
         (-> (sut/make)
             (opt-tb-storage-strategy/assoc-date 10 :v)
             (opt-tb-storage-strategy/get-exact 10)))))

(deftest get-measures-test
  (is (= [:v :v nil]
         (-> (sut/make)
             (opt-tb-storage-strategy/assoc-date 10 :v)
             (opt-tb-storage-strategy/assoc-date 11 :v)
             (opt-tb-storage-strategy/get-measures (range 10 13))))))

(deftest range-dates-test
  (is (= [10 18]
         (-> (sut/make)
             (opt-tb-storage-strategy/assoc-date 10 :v)
             (opt-tb-storage-strategy/assoc-date 11 :v)
             (opt-tb-storage-strategy/assoc-date 18 :v)
             opt-tb-storage-strategy/range-dates))))
