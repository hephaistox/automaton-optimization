(ns automaton-optimization.date.bucket
  "Stores a date as an integer - representing time buckets, starting at `0`."
  (:require
   [automaton-core.utils.type-arg :as core-type-arg]
   [automaton-optimization.date :as optimization-date]))

(defrecord DelayDateBucket [nb-buckets]
  optimization-date/DelayDate
    (delay-date [_] nb-buckets)
    (negate [_] (DelayDateBucket. (- nb-buckets))))

(defn make-delay-date-bucket [nb-buckets] (->DelayDateBucket nb-buckets))

(defn make-random-delay-bucket
  "Create a random delay between `min-delay` and `max-delay`"
  [min-delay max-delay]
  (->DelayDateBucket (+ min-delay (rand-int (- max-delay min-delay)))))

(defrecord BucketDate [bucket-nb]
  optimization-date/DateProtocol
    (compare-date [date1 date2]
      (core-type-arg/assert-protocols "BucketDate/compare-date"
                                      [[optimization-date/DateProtocol date2]]
                                      (<= (:bucket-nb date1)
                                          (:bucket-nb date2))))
    (equal-date [date1 date2]
      (core-type-arg/assert-protocols "BucketDate/equal-date"
                                      [[optimization-date/DateProtocol date2]]
                                      (= (:bucket-nb date1)
                                         (:bucket-nb date2))))
    (add-delay [_ delay-date]
      (core-type-arg/assert-protocols
       "BucketDate/add"
       [[optimization-date/DelayDate delay-date]]
       (BucketDate. (+ bucket-nb (optimization-date/delay-date delay-date)))))
    (substract-delay [_ delay-date]
      (core-type-arg/assert-protocols
       "BucketDate/substract-delay"
       [[optimization-date/DelayDate delay-date]]
       (BucketDate. (- bucket-nb (optimization-date/delay-date delay-date))))))

(defn make-bucket-date
  "Creates a date based on bucket number. The number starts with 0 and number the .

  Params:
  * `bucket-nb` bucket number - Integer"
  [bucket-nb]
  (core-type-arg/assert-positive-integer
   bucket-nb
   "Date is created with a non positive integer (e.g. `%s`)"
   (->BucketDate bucket-nb)))

(defn make-random-date
  "Creates a date between `min` and `max`"
  [min-date max-date]
  (-> (+ min-date (rand-int (- max-date min-date)))
      make-bucket-date))
