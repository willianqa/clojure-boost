(ns clojure-boost.csv
  :require [clojure.data.])

(defn copy-csv
  (with-open
    [reader (clojure.java.io/reader "Massa de dados.csv")]
                 (doall (clojure.data.csv/read-csv reader))))
