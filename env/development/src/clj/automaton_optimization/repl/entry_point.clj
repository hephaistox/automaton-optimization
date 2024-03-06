(ns automaton-optimization.repl.entry-point
  "repl for `automaton-optimization` stand alone"
  (:require
   [automaton-core.log :as core-log]
   [automaton-core.repl :as core-repl]
   [cider.nrepl :as cider-nrepl]
   [refactor-nrepl.middleware]
   [mount.core :as mount]
   [mount.tools.graph :as graph])
  (:gen-class))

(defn- stub [& _args] nil)

(defn -main
  "Main entry point for repl"
  [& args]
  (core-log/info "Start `automaton-simulation-de`")
  (core-log/trace "Component dependencies: " (graph/states-with-deps))
  (core-repl/start-repl args
                        (conj cider-nrepl/cider-middleware
                              'refactor-nrepl.middleware/wrap-refactor)
                        stub)
  (mount/start))
