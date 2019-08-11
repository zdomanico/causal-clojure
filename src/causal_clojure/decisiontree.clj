(ns causal-clojure.decisiontree
	(:gen-class))

(defn sqr [x]
	(* x x)
)

(defn mean [data]
	(/ (reduce + data) (count data))
)

(defn variance [data]
	(mean (map #(sqr (- % (mean data))) data))
)


(defn variance_reduction [predicate data] 
		(- 
			(variance data)
			(+
				(variance (filter predicate data))
				(variance (remove predicate data))
			)
		)
)

(defn best_split [xdata ydata nsplits]
 (max-key 
 #(variance_reduction (fn [m](> m %)) ydata)
 	(range 
			(min xdata) 
			(max xdata) 
			(/ 
				(- (max xdata) (min xdata)) 
				nsplits
			)
		)
	)

)
