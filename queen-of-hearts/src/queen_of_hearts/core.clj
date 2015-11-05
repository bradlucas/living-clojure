(ns queen-of-hearts.core
  (:require [clojure.core.async :as async]))

(def flowers ["white carnation"
              "yellow daffodil"
              "yellow rose"
              "red rose"
              "white rose"
              "purple lily"
              "pink carnation"])



(defn paint-it-red [s]
  (str "red "
       (last (clojure.string/split s #"\s"))))

(defn is-a-rose? [s]
  (= "rose"
     (last (clojure.string/split s #"\s"))))

;; Now here is where the transducers come in. We have two transformations
;; that we can apply to a vector of flower strings. In the previous
;; version of Clojure, you would need a data structure to use with
;; map. Not any more.  We can decouple the transformation from the data
;; structure by leaving it off. It returns a transducer

(def fix-for-the-queen-xform
  (comp
   (map paint-it-red)
   (filter is-a-rose?)))

(def flower-chan (async/chan 1 fix-for-the-queen-xform))

(def result-chan (async/reduce 
                  (completing #(str %1 %2 ":"))
                  ""
                  flower-chan))
