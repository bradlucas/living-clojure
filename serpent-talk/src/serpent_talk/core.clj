(ns serpent-talk.core
  (:require [camel-snake-kebab.core :refer :all :as csk]))

(defn serpent-talk [s]
  (str "Serpent! You said: " (csk/->snake_case s)))

(defn -main [& args]
  (println (serpent-talk (first args))))

; v -> [a,b,c,d] representing two points (a, b) and (c, d)
(defn slope [v]
  (let [[a b c d] v
        x (- c a)
        y (- d b)
        ]
    (if (zero? x)
      "undefined"
      (str (/ y x)))))
