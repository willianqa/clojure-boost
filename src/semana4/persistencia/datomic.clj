(ns semana4.persistencia.datomic
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [semana4.persistencia.datomic :as db]
            [semana4.outro.model :as model]))

(def db-uri "datomic:dev://localhost:4334/clojure-boost")

(defn cria-conexao! []
  (d/create-database db-uri)
  (d/connect db-uri))

(cria-conexao!)

(defn apaga-banco! []
  (d/delete-database db-uri))

;(apaga-banco!)


(def schema [{ :db/ident       :compra/data
               :db/valueType   :db.type/string
               :db/cardinality :db.cardinality/one
               :db/doc         "Data da compra"}
              {:db/ident       :compra/valor
               :db/valueType   :db.type/bigdec
               :db/cardinality :db.cardinality/one
               :db/doc         "Valor da compra"}
              {:db/ident       :compra/estabelecimento
               :db/valueType   :db.type/string
               :db/cardinality :db.cardinality/one
               :db/doc         "Estabelecimento da compra"}
              {:db/ident       :compra/categoria
               :db/valueType   :db.type/string
               :db/cardinality :db.cardinality/one
               :db/doc         "Categoria da compra"}
              {:db/ident       :compra/cartao
               :db/valueType   :db.type/long
               :db/cardinality :db.cardinality/one
               :db/doc         "Cartão da compra"}])

(def conn (db/cria-conexao!))

(d/transact conn db/schema)

(defn insere-compra! []
  (let [burguer-king (model/nova-compra "2022-01-01" 20.00M "Burguer King" "Alimentação" 123412341234)
        habbibs (model/nova-compra "2022-02-02" 90.00M "Habbib's" "Alimentação" 123412341234)
        mcdonalds (model/nova-compra "2022-03-03" 30.00M "McDonald's" "Alimentação" 1111122222333)
        pizzaria (model/nova-compra "2022-04-04" 50.00M "PizzaHut" "Alimentação" 1111122222333)]
    (d/transact conn [burguer-king habbibs mcdonalds pizzaria])))

(insere-compra!)


(def db (d/db conn))

"Outro modelo de listar as compras"
;(defn lista-compras! [db]
;  (d/q '[:find (pull ?entidade [*])
;         :where [?entidade :compra/data]]db))

"Outro modelo de listar as compras"
;(defn lista-compras! [db]
;  (d/q '[:find (pull ?entidade [:compra/data
;                                :compra/valor
;                                :compra/estabelecimento
;                                :compra/categoria
;                                :compra/cartao])
;         :where [?entidade :compra/data]]db))

(defn lista-compras! [db]
  (d/q '[:find ?data ?valor ?estabelecimento ?categoria ?cartao
         :keys data, valor, estabelecimento, categoria, cartao
         :where [?compra :compra/data ?data]
         [?compra :compra/valor ?valor]
         [?compra :compra/estabelecimento ?estabelecimento]
         [?compra :compra/categoria ?categoria]
         [?compra :compra/cartao ?cartao]] db))

(pprint (lista-compras! db))

(defn lista-compras-por-cartao! [db cartao]
  (d/q '[:find ?cartao-filtrado ?data ?valor ?estabelecimento ?categoria
         :keys cartao, data, valor, estabelecimento, categoria
         :in $ ?cartao-filtrado
         :where [?entidade :compra/data ?data]
         [?entidade :compra/valor ?valor]
         [?entidade :compra/estabelecimento ?estabelecimento]
         [?entidade :compra/categoria ?categoria]
         [?entidade :compra/cartao ?cartao-filtrado]] db cartao))

(pprint (vec (lista-compras-por-cartao! (d/db conn) 123412341234)))


