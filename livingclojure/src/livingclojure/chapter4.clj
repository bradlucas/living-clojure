(ns livingclojure.chapter4)

;; calling methods on strings
;; dot special form
;; (dot object method)
(. "testing" toUpperCase)

;; or
;; (dot.method object)
(.toUpperCase "testing")


;; create instances with new
(new String "Test")


;; import a Java class and use it
(import 'java.net.InetAddress)
;; of
;; (ns thisismyproject.subject
;;   (:import (hjava.net Inetaddress)))

(InetAddress/getByName "beaconhill.com")


;; of use the class directly
(java.net.InetAddress/getByName "localhost")

;; doto macro allows you string cllass together
(def sb (doto (StringBuffer. "A")
          (.append " B ")
          (.append " C")))

(.toString sb)


;; UUID
(import 'java.util.UUID)
(UUID/randomUUID)


;; polymorphisim
;; In an object-oriented language like Java, there are a large amount
;; of types for every situation. Clojure takes another approach. It has a
;; small amount of types and many different functions for them. However,
;; being pragmatic, Clojure realizes that polymorphism is flexible and
;; useful for some situations.


;; multimethods
;; first you define the multimethod and a function that specifies how it is going to dispatch
;; (defmulti who-are-you class)

(defmulti who-are-you class)

(defmethod who-are-you java.lang.String [input] 
  (str "String method " input))

;; (defmethod who-are-you TYPE [param] body)

(defmethod who-are-you clojure.lang.Keyword [input]
  (str "Keyword method " input))

(defmethod who-are-you java.lang.Long [input]
  (str "Long method " input))

(defmethod who-are-you :default [input]
  (str "Default method for unknown " input))





;; Any function can be given to dispatch on
(defmulti eat-mushroom (fn [height]
                         (if (< height 3)
                           :grow
                           :shrink)))

(defmethod eat-mushroom :grow [_]
  "Eat the right side to grow.")

(defmethod eat-mushroom :shrink [_]
  "Eat the left side to shrink.")



;; Another way to use polymorphism is to use protocols
;; Multi-methods are great for using ploymorphism on one function
;; protocols can wirh with groups of functions


;; define the protocol
(defprotocol BigMushroom
  (eat-mushroom [this]))

;; implement for all our types
(extend-protocol BigMushroom
  java.lang.String
  (eat-mushroom [this]
    (str (.toUpperCase this) " mmm tasty!"))

  clojure.lang.Keyword
  (eat-mushroom [this]
    (case this
      :grow "Eat the right side!"
      :shrink "Eat the left side to shrink"))

  java.lang.Long
  (eat-mushroom [this]
    (if (< this 3)
      "Eat the right side!"
      "Eat the left side to shrink")))




;; structured data
;; defrecord form defines the fields that the class will hold
(defrecord Mushroom [color height])

(def regular-mushroom (Mushroom. "white and blue polka dots" "2 inches"))

;; Can get values using dot-dash

(.-color regular-mushroom) ;; => "white and blue polka dots"
(.-height regular-mushroom) ;; => "2 inches"


;; combine the structured data and type from defrecord with the protocols tom implement interfaces

(defprotocol Edible
  (bite-right-side [this])
  (bite-left-side [this]))

(defrecord WonderlandMushroom [color height]
  Edible
  (bite-right-side [this]
    (str "the " color " bite makes you grow bigger"))o
  (bite-left-side [this]
    (str "the " color " bite makes you grow smaller")))

(defrecord RegularMushroom [color height]
  Edible
  (bite-right-side [this]
    (str "the " color " bite tastes bad"))
  (bite-left-side [this]
    (str "the " color " bite tastes bad too")))


;; construct mushrooms
(def alice-mushroom (WonderlandMushroom. "blue dots" "3 inches"))
(def reg-mushroom (RegularMushroom. "brown" "1 inches"))

(bite-right-side alice-mushroom) ;; => "the blue dots bite makes you grow bigger"
(bite-left-side alice-mushroom) ;; => "the blue dots bite makes you grow smaller"
(bite-right-side reg-mushroom) ;; => "the brown bite tastes bad"
( bite-left-side reg-mushroom) ;; => "the brown bite tastes bad too"


;; Sometimes we don;t really care about the structure or the map lookup
;; features provided by defrecord, we just need an object with a type to
;; save memory.

;; The main difference between using protocols with defrecord and deftype
;; is how you want your data organized. If you want structured data,
;; choose defrecord. Otherwise, use deftype. Why? Because with records,
;; you get type-based dispatch and you can still manipulate your data
;; like maps (which is great for reuse). Sometimes, when this structured
;; data isn't needed, you can use deftype to avoid paying for the
;; overhead for something you don't want.
