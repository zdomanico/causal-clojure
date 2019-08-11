(ns causal-clojure.graph)

(def empty-graph {})

(defn create-graph [lst]
  "Creates a graph from a list of lists. Each of the sub-component lists
   should be formatted as follows: [SOURCE keyword  DESTINATION NODES ...]
  "
  (reduce 
    #(add-node %1 (first %2) (set (rest %2))) 
    empty-graph 
    lst
  )
)

(defn add-node [graph source dest]
  "args: graph - current graph data structure.
         source - node that edges originate from
         dest - set of nodes that edges lead to
  "
  (if (contains? graph source)
    (add-edges graph source dest)
    (let [g (assoc graph source dest)]
      (reduce add-childless-nodes g dest)
    )
  )
)

(defn add-childless-nodes [graph node]
  " Internal Function. Ensures that if a node exists it is a 
    key in the top level map that is the graph.
    i.e. {:A #{:B :C}} -> {:A #{:B :C}, :B #{}, :C #{}}
  "
  (if-not (contains? graph node)
    (add-node graph node #{})
    graph
  )
)

(defn add-edges [graph source dest]
  "args: graph - current graph data structure.
         source - node that edges originate from
         dest - set of nodes that edges lead to
   Same as add-node except this will add edges to existing
   source nodes. Desitnation nodes need not exist prior to this
   function.
  "
  (let [g (update graph source #(clojure.set/union % dest))]
    (reduce add-childless-nodes g dest)
  )
)
