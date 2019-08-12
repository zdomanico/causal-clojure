(ns causal-clojure.core-test
  (:require [clojure.test :refer :all]
            [causal-clojure.core :refer :all]
            [causal-clojure.graph :as graph]
            [causal-clojure.decisiontree :as dt]
  )
)

(deftest create-graph
  (testing "Create Empty Graph"
    (is 
      (= 
        {}
        (graph/create-graph [])
      )
    )
  )
  (testing "Create Connected Graph"
    (is 
      (= 
        {:A #{:B :C}, :B #{:A :C}, :C #{:A :B}}
        (graph/create-graph [[:A :B :C] [:B :A :C] [:C :A :B]])
      )
    )
  )
  (testing "Create Graph with one source and 3 leafs 1/2"
    (is 
      (= 
        {:A #{:B :C :D}, :B #{}, :C #{} :D #{}}
        (graph/create-graph [[:A :B :C :D]])
      )
    )
  )
  (testing "Create Graph with one source and 3 leafs 2/2"
    (is 
      (= 
        {:A #{:B :C :D}, :B #{}, :C #{} :D #{}}
        (graph/create-graph [[:A :B :C :D] [:B]])
      )
    )
  )
  (testing "Create Graph with three singleton nodes"
    (is 
      (= 
        {:A #{}, :B #{}, :C #{}}
        (graph/create-graph [[:A] [:B] [:C]])
      )
    )
  )
)
