(ns semana1.csv
  (:require [clojure.string :as str]))

(defn nova-compra
  ([data valor estabelecimento categoria cartao]
   {:data            data
    :valor           valor
    :estabelecimento estabelecimento
    :categoria       categoria
    :cartao          cartao})

  ([[data valor estabelecimento categoria cartao]]
   (nova-compra data valor estabelecimento categoria cartao)))


(defn processa-compras-csv []
  (->> (slurp "/Users/willian.nunes/Documents/clojure/clojure-boost/dados/compras.csv")
       str/split-lines
       (map #(str/split % #","))
       rest
       (map #(nova-compra %))))

(println (processa-compras-csv))
