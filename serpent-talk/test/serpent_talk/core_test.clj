(ns serpent-talk.core-test
  (:require [clojure.test :refer :all]
            [serpent-talk.core :refer :all]))


(deftest test-serpent-talk
  (testing "Cries erpent! with a snake_case version of the input"
    (is (= "Serpent! You said: hello_there"
           (serpent-talk "hello there")))))
