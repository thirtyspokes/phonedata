(ns phonedata.core
  (:require [phonedata.parsing.parser :as parser]
            [phonedata.stats.stats :as stats]))

(defn -main
  [& args]
  (let [csv-data (parser/csv->map (first args))
        highest (stats/highest-usage csv-data)
        lowest (stats/lowest-usage csv-data)]
    (println "Phone Usage Data")
    (println "----------------")
    (println "The median usage was" (stats/median csv-data))
    (println "The mean usage was" (stats/mean (map :volume csv-data)))
    (println "The standard deviation was" (stats/standard-deviation csv-data))
    (println "The highest usage was" (:volume highest) "mb on" (:date highest))
    (println "The lowest usage was" (:volume lowest) "mb on" (:date lowest))))
