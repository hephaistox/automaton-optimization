(ns automaton-optimization.randomness
  "Randomness for optimization projects."
  (:require
   [automaton-optimization.randomness.distribution               :as opt-proba-distribution]
   [automaton-optimization.randomness.impl.distribution.factory  :as opt-distribution-factory]
   [automaton-optimization.randomness.impl.distribution.registry :as opt-random-registry]
   [automaton-optimization.randomness.impl.prng.built-in         :as opt-prng-built-in]
   [automaton-optimization.randomness.impl.prng.stateful         :as opt-prng-stateful]
   [automaton-optimization.randomness.impl.prng.xoroshiro128     :as opt-prng-xoro]))

(defn build
  "Returns the `distribution` matching name `kw`, built with params `params`.
  If supplied, the `registry` is where `kw` will be searched for, search in built-in registry otherwise.
  If supplied, the `prng` will be leveraged to generate the distribution, will use xoroshiro otherwise."
  ([registry prng kw params] (opt-distribution-factory/build registry kw prng params))
  ([registry kw params] (build registry (opt-prng-xoro/make) kw params))
  ([kw params] (build (opt-random-registry/registry) (opt-prng-xoro/make) kw params)))

(defn xoroshiro128
  "Creates a xoroshiro128 prng instance."
  ([seed] (opt-prng-xoro/make seed))
  ([] (opt-prng-xoro/make)))

(defn built-in
  "Creates an instance of the built-in prng of your platform (java or javascript)."
  []
  (opt-prng-built-in/make))

(defn duplicate
  "Duplicates this prng to a new one, starting at the seed value."
  [this]
  (opt-prng-stateful/duplicate this))

(defn jump "Jump to a completly different place." [this] (opt-prng-stateful/jump this))

(defn rnd-range
  "Returns the range in which rnd is returned."
  [this]
  (opt-prng-stateful/rnd-range this))

(defn seed "Returns the seed of the random number generator." [this] (opt-prng-stateful/seed this))

(defn reset
  "Returns a prng that starts again at the seed value."
  [this]
  (opt-prng-stateful/reset this))

(defn rnd
  "Returns a random number and change the state of the prng so next call will return a new value."
  [this]
  (opt-prng-stateful/rnd this))

(defn as-int
  "Returns an integer generated with `prng` between `[min-int; max-int[`."
  [prng min-int max-int]
  (opt-prng-stateful/as-int prng min-int max-int))

(defn as-int-pair
  "Returns a pair of random integer between `[min-int; max-int[`."
  [prng min-int max-int]
  (let [rnd1 (as-int prng min-int max-int) rnd2 (as-int prng min-int max-int)] [rnd1 rnd2]))

(defn draw-ints
  "Draw `n` random integers with `prng`, between `[min-int; max-int[`."
  [prng n min-int max-int]
  (opt-prng-stateful/draw-ints prng n min-int max-int))

(defn draw-doubles
  "Draw `n` random doubles with `prng`, between `[min-int; max-int[`."
  [prng n min-int max-int]
  (repeatedly n #(opt-prng-stateful/as-double prng min-int max-int)))

(defn draw
  "`(draw)` returns a random value following `distribution`."
  ([distribution] (opt-proba-distribution/draw distribution))
  ([kw params]
   (-> (build kw params)
       opt-proba-distribution/draw)))

(defn distribution-registry "Returns the base registry." [] (opt-random-registry/registry))

(defn median
  "Returns the median of the distribution"
  ([distribution] (opt-proba-distribution/median distribution))
  ([kw params] (opt-proba-distribution/median (build kw params))))

(def cumulative "Returns the cumulative probability before `p`" opt-proba-distribution/cumulative)

(def minimum "Minimum" opt-proba-distribution/minimum)

(def maximum "Maximum" opt-proba-distribution/maximum)

(def quantile opt-proba-distribution/quantile)
