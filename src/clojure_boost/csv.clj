(ns clojure-boost.csv
  :require '[clojure.java.io :as io]
           '[clojure.data.csv :as csv])

(defn copy-csv
  (with-open
    [reader (io/reader "Massa de dados.csv")]
                 (doall (csv/read-csv reader))))

(println copy-csv)
