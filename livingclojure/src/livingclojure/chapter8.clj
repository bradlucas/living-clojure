(ns livingclojure.chapter8)

(defmacro when
  [test & body]
  (list 'if test (cons 'do body)))

(defn hi-queen [phrase]
  (str phrase ", so please your Majesty"))

(defn alice-hi-queen []
  (hi-queen "My name is Alice"))

(defn march-hare-hi-queen []
  (hi-queen "I'm the March Hare"))

(defn white-rabbit-hi-queen []
  (hi-queen "I'm the White Rabbit"))

(defn mad-hatter-hi-queen []
  (hi-queen "I'm the Mad Hatter"))


;; replace with macro
;; two pieces of informationl the symbol of the function and the indroduction
(defmacro def-hi-queen [name phrase]
  (list 'defn 
        (symbol name)
        []
        (list 'hi-queen phrase)))


(macroexpand-1 '(def-hi-queen alice-hi-queen "My name is Alice"))


;; templating
(defmacro def-hi-queen [name phrase]
  `(defn ~(symbol name) []
    (hi-queen ~phrase)))
