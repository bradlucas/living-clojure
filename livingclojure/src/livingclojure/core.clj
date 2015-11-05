(ns livingclojure.core)

(defn rev [s out]
  (if (empty? s)
    out
    (rev (rest s) (cons (first s) out))))

(defn rev [s]
  (loop [s s
         out []]
  (if (empty? s)
    out
    (recur (rest s) (cons (first s) out)))))

(defn countdown [n]
  (if (= n 0)
    n
    (countdown (- n 1))))

(defn countdown [n]
  (if (= n 0)
    n
    (recur (- n 1))))

(defn remove-nils [s]
  (reduce (fn [r x] (if (nil? x) r (conj r x))) [] s))

(defn forexample []
  (for [a [1 2 3 4]
        b [5 6 7 8]
        :let [c 0
              d 9]]
    (println (str "a: " a " " "b: " b " " "c: " c " " "d: " d))))

(def who-atom (atom :caterpillar))

(defn change [state]
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

(def counter (atom 0))

(defn inc-print [val]
  (println val)
  (inc val))

(def alice-height (ref 3))
(def right-hand-bites (ref 10))

;; A function that will increment Alice's height by 24 inches for every bit she
;; takes from the right hand
(defn eat-from-right-hand []
  (dosync (when (pos? @right-hand-bites)
            (alter right-hand-bites dec)
            (alter alice-height #(+ % 24)))))

;; test by running in 3 different threads calling the function two times
(let [n 2]
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand))))

;; y will always be the value of x + 2
;; when one ref is defined in relation to another use ref-set

(def x (ref 1))
(def y (ref 1))

(defn new-values []
  (dosync
   (alter x inc)
   (ref-set y (+ @x 2))))

;; test this
;; call new-values two times on two threads
(let [n 2]
  (future (dotimes [_ n] (new-values)))
  (future (dotimes [_ n] (new-values))))


(def who-agent (agent :caterpillar))

(send who-agent change)

