(ns automaton-optimization.date
  "Date protocol for optimization.

  Design decision:
  * This concept does not include the transformation of that date in a real time stamped date.
     * Rationale:
        * For simplicity of this namespace, every transformation from integer to timestamp date, taking into account the scenario starting date.
        * Projects that don't need real dates won't be forced to.
     * Implications:
        * No function in the protocol is about turning the date into in a real life date.
     * Limits:
        * None known.

  Constraints:
  * `compare-date` is a total order relation.
  * `equal-date` is an equivalence relation.")

(defprotocol DelayDate
  (delay-date [_] "Returns the delay value")
  (negate [_] "Returns the opposite of the delay"))

(defprotocol DateProtocol
  (compare-date [date1 date2] "Returns true if `date1` is before `date2`")
  (equal-date [date1 date2] "Returns true if `date1` equal `date2`")
  (add-delay [_ delay-date] "Add the `delay-date` to the date")
  (substract-delay [_ delay-date] "Substract the `delay-date` to the date"))


(defn strictly-compare
  "Compare `date1` and `date2` but return false if they' identical"
  [date1 date2]
  (and (not (equal-date date1 date2))
       (compare-date date1 date2)))
