(ns semana1.modelo-com-csv.csv
  (:use clojure.pprint)
  (:require [java-time :as jt]
            [clojure.string :as str]))

(defn java-time [data]
  (jt/format "yyyy-MM-dd" (jt/local-date data)))

(defn nova-compra
  ([data valor estabelecimento categoria cartao]
   {:data            (java-time data)
    :valor           (bigdec valor)
    :estabelecimento estabelecimento
    :categoria       categoria
    :cartao           cartao})

  ([[data valor estabelecimento categoria cartao]]
   (nova-compra data valor estabelecimento categoria cartao)))

(defn lista-de-compras-csv []
  (->> (slurp "dados/compras.csv")
       str/split-lines
       (map #(str/split % #";"))
       rest
       (map #(nova-compra %))
       vec))


  ;(->> (lista-de-compras-csv)
  ;     (map :cartao)
  ;     vec)
(pprint nova-compra)

(println "Lista todas as compras")
(pprint (lista-de-compras-csv))

(defn somando-valores
  [lista-de-compras-csv]
  (->> lista-de-compras-csv
       (map :valor)
       (reduce +)))

(println "Valor total das compras")
(pprint  (somando-valores (lista-de-compras-csv)))

(defn filtrando-cartao
  [numerocartao cartoes]
  (->> cartoes
       (filter #(= (get % :cartao) numerocartao))
       (map :valor)))

(defn total-gasto
  [cartao lista-de-compras-csv]
  (let [cartao-filtrado (filtrando-cartao cartao lista-de-compras-csv)]
    (->> cartao-filtrado
         (reduce +))))

(println "Total Gasto em um determinado cartão")
(pprint (total-gasto "1234 1234 1234 1234" (lista-de-compras-csv)))

(defn mes-da-data
  [data]
  (-> data
      (str/split #"-")
      (second)
      (Integer/parseInt)))

(defn lista-compras-mes
  [lista-de-compras-csv mes]
  (->> (lista-de-compras-csv)
       (filter #(= mes (mes-da-data (:data %))))))

(println "Lista de compras de um determinado mês: ")
(pprint (lista-compras-mes lista-de-compras-csv 4))

(println "Total gasto no mês:")
(defn total-gasto-no-mes
  [[cartao valores]]
  {:cartao      cartao
   :gasto-total (somando-valores valores)
   })
(->> (lista-compras-mes lista-de-compras-csv 4)
     (group-by :cartao)
     (map total-gasto-no-mes)
     (map :gasto-total)
     (reduce +)
     println)


(defn organiza-categoria [[categoria compras-da-categoria]]
  [categoria (somando-valores compras-da-categoria)])

(defn compras-agrupadas
  [compras]
  (->> (group-by :categoria compras)
       (into {} (map organiza-categoria))))

(println "Total gasto por categoria:")
(pprint (compras-agrupadas (lista-de-compras-csv)))


(defn filtrar-compras-num-intervalo-de-valores
  [x y]
  (->> (lista-de-compras-csv)
       (filter #(and (< x (:valor %))
                     (> y (:valor %))))))
(println "Lista compras em um range de valores:")
(pprint(filtrar-compras-num-intervalo-de-valores 80 100))













