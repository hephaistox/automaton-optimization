(ns automaton-optimization.random
  "Random number generator.
  This protocol hides the complexity of the different random number generator. As many different implementations may exist.

  * See https://github.com/jenetics/prngine for random generator implementation
  * https://github.com/TheClimateCorporation/prng
  * https://github.com/clojure/math.numeric-tower/")

(defprotocol RandomNumberGenerator
  (seed [_] "Returns the seed of the random number generator")
  (next-int [_ max-int]
    "Return the next integer number between [0; max-int [ (i.e. max-int) is excluded")
  (next-float [_ min max]
    "Return a float in [min, max[ (max excluded) its precision is the responsability of the implementation"))
