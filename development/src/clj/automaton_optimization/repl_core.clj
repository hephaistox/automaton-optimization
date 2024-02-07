(ns automaton-optimization.repl-core
  "repl for `automaton-simulation-de` stand alone"
  (:require
   [automaton-core.log :as core-log]
   [automaton-core.repl :as core-repl]
   [clojure.core.async :refer [<! chan go]]
   [mount.core :as mount]
   [mount.tools.graph :as graph])
  (:gen-class))

(defn -main
  "Main entry point for repl"
  [& _args]
  (core-log/info "Start `automaton-simulation-de`")
  (core-log/trace "Component dependencies: " (graph/states-with-deps))
  (mount/start)
  (core-repl/start-repl)
  (let [c (chan 1)] (go (<! c))))
