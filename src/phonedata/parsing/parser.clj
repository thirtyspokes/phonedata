(ns phonedata.parsing.parser)

(defn trim-metadata
  [lines]
  (drop 7 (reverse (drop 1 (reverse lines)))))

(defn parse-csv
  [filename]
  (trim-metadata
   (map (fn [line] (clojure.string/split line #",")) 
        (clojure.string/split (slurp filename) #"\n"))))

(defn string->keyword
  "Turns a collection of strings into a collection of keywords."
  [headers]
  (map (fn [column] (keyword (clojure.string/lower-case column))) headers))

(defn mapify-row
  "Creates a map using the provided headers as the keys and the data in row as
   the values."
  [headers row]
  (into {} (map (fn [key value] [key value]) headers row)))

(defn mapify-csv
  "Given a sequence of lines from a CSV file, converts each
   line into a map."
  [parsed-lines]
  (let [headers (string->keyword (first parsed-lines))
        unmapped-lines (rest parsed-lines)]
    (map #(mapify-row headers %) unmapped-lines)))

(defn set-numerics
  [csv-map]
  (map (fn [row] 
         (assoc row :volume (Float/parseFloat (:volume row))))
       csv-map))

(defn csv->map
  "Converts the string contents of a CSV file to a sequence of maps."
  [filename]
  (set-numerics (mapify-csv (parse-csv filename))))
