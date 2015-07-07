(ns phonedata.stats.stats)

(defn -get-volume-col
  [full-data-rows]
  (map :volume full-data-rows))

(def get-usage-data
  (memoize -get-volume-col))

(defn mean
  [data-rows]
  (float (/ (reduce + data-rows) (count data-rows))))

(defn median
  [data-rows]
  (nth (sort (get-usage-data data-rows)) (int (/ (count data-rows) 2))))

(defn variance
  [data-rows]
  (let [avg (mean (get-usage-data data-rows))
        data (get-usage-data data-rows)]
    (mean (map (fn [value] (Math/pow (- value avg) 2)) data))))

(defn standard-deviation
  [data-rows]
  (Math/sqrt (variance data-rows)))

(defn highest-usage
  [data-rows]
  (first (reverse (sort-by :volume data-rows))))

(defn lowest-usage
  [data-rows]
  (first (sort-by :volume data-rows)))
