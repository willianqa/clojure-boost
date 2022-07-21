(ns clojure-boost.core
  (:require [clojure.string :as str]))

(defn nova-compra [] {
                      :data "2022-01-01"
                      :valor 129.90
                      :estabelecimento "Outback"
                      :categoria "Alimentação"
                      :cartao 1234123412341234
                      })
(println "LISTA UMA NOVA COMPRA: " (nova-compra))


(defn lista-compras []
  "Retorna todas as compras realizadas"
  [{
    :data "2022-01-01"
    :valor 129.90
    :estabelecimento "Outback"
    :categoria "Alimentação"
    :cartao 1234123412341234
    }
   {
    :data "2022-01-02"
    :valor 260.00
    :estabelecimento "Dentista"
    :categoria "Saúde"
    :cartao 1234123412341234
    }
   {
    :data "2022-02-01"
    :valor 20.00
    :estabelecimento "Cinema"
    :categoria "Lazer"
    :cartao 1234123412341234
    }
   {
    :data "2022-01-10"
    :valor 150.00
    :estabelecimento "Show"
    :categoria "Lazer"
    :cartao 4321432143214321
    }
   {
    :data "2022-02-10"
    :valor 289.99
    :estabelecimento "Posto de gasolina"
    :categoria "Automóvel"
    :cartao 4321432143214321
    }
   {
    :data "2022-02-20"
    :valor 79.90
    :estabelecimento "iFood"
    :categoria "Alimentação"
    :cartao 4321432143214321
    }
   {
    :data "2022-03-01"
    :valor 85.00
    :estabelecimento "Alura"
    :categoria "Educação"
    :cartao 4321432143214321
    }
   {
    :data "2022-01-30"
    :valor 85.00
    :estabelecimento "Alura"
    :categoria "Educação"
    :cartao 1598159815981598
    }
   {
    :data "2022-01-31"
    :valor 350.00
    :estabelecimento "Tok&Stok"
    :categoria "Casa"
    :cartao 1598159815981598
    }
   {
    :data "2022-02-01"
    :valor 400.00
    :estabelecimento "Leroy Merlin"
    :categoria "Casa"
    :cartao 1598159815981598
    }
   {
    :data "2022-03-01"
    :valor 50.00
    :estabelecimento "Madero"
    :categoria "Alimentação"
    :cartao 6655665566556655
    }
   {
    :data "2022-03-01"
    :valor 70.00
    :estabelecimento "Teatro"
    :categoria "Lazer"
    :cartao 6655665566556655
    }
   {
    :data "2022-03-04"
    :valor 250.00
    :estabelecimento "Hospital"
    :categoria "Saúde"
    :cartao 6655665566556655
    }
   {
    :data "2022-04-10"
    :valor 130.0
    :estabelecimento "Drogaria"
    :categoria "Saúde"
    :cartao 6655665566556655
    }
   {
    :data "2022-03-10"
    :valor 100.0
    :estabelecimento "Show de pagode"
    :categoria "Lazer"
    :cartao 3939393939393939
    }
   {
    :data "2022-03-11"
    :valor 25.9
    :estabelecimento "Dogão"
    :categoria "Alimentação"
    :cartao 3939393939393939
    }
   {
    :data "2022-03-12"
    :valor 215.87
    :estabelecimento "Praia"
    :categoria "Lazer"
    :cartao 3939393939393939
    }
   {
    :data "2022-04-01"
    :valor 976.88
    :estabelecimento "Oficina"
    :categoria "Automóvel"
    :cartao 3939393939393939
    }
   {
    :data "2022-04-01"
    :valor 85.0
    :estabelecimento "Alura"
    :categoria "Educação"
    :cartao 3939393939393939
    }
   ])

(println "LISTA TODAS AS COMPRAS: " (lista-compras))

(defn filtrando-cartao
  [numerocartao cartoes]
  (->> cartoes
       (filter #(= (get % :cartao) numerocartao))
       (map :valor)
       ))

(defn total-gasto
  [cartao lista-compras]
  (let [cartao-filtrado (filtrando-cartao cartao lista-compras)]
    (->> cartao-filtrado
         (reduce +)
         )))
(println "TOTAL GASTO EM UM CARTÃO: R$" (total-gasto 1234123412341234 (lista-compras)))

(defn mes-da-data
  [data]
  (-> data
      (str/split #"-")
      (second)
      (Integer/parseInt)))

(defn lista-compras-mes
  [lista-compras mes]
  (->> lista-compras
       (filter #(= mes (mes-da-data (:data %))))))

(println "LISTA COMPRAS DE UM MES: " (lista-compras-mes (lista-compras)1))

;**************************************************************
(defn filtrando-valores
  [lista-compras]
  (->> lista-compras
       (map :valor)
       (reduce +) ))

(println "TOTAL GASTO NO MES:")
(defn total-gasto-no-mes
  [[cartao valores]]
  {:cartao      cartao
   :gasto-total (filtrando-valores valores)
   })
(->> (lista-compras-mes (lista-compras) 1)
     (group-by :cartao)
     (map total-gasto-no-mes)
     println)
;**************************************************************
(println "TOTAL GASTO POR CATEGORIA:")

;(defn agrupados-por-categoria
;  [[categoria valores]]
;  {:categoria      categoria
;   :valor (filtrando-valores valores)
;   })
;
;(defn agrupados-na-chave
;  [lista-compras]
;  (->> lista-compras
;       (group-by :categoria)
;        (map agrupados-por-categoria)))
;
;(println ">>>>>>" (agrupados-na-chave (lista-compras)))

(defn soma-gasto
  [lista-compras]
  (->> lista-compras
       (map :valor)
       (reduce +)))

(defn compras-agrupadas
  [lista-compras]
  (group-by :categoria lista-compras))

;(println (into {} (map (fn [[categoria compras-da-categoria]] [categoria (soma-gasto compras-da-categoria)]) (compras-agrupadas (lista-compras)))))
(->> (compras-agrupadas (lista-compras))
     (into {} (map (fn [[categoria compras-da-categoria]] [categoria (soma-gasto compras-da-categoria)])))
     println)

;*****************************************************************
;task opcional - Filtrar compras num intervalo de valores

(defn filtrar-compras-num-intervalo-de-valores
  [x y]
  (->> (lista-compras)
       (filter #(and (< x (:valor %)) (> y (:valor %))))))

(println "-->>>" (filtrar-compras-num-intervalo-de-valores 80 100))







