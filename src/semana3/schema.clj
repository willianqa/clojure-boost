(ns semana3.schema
  (:use clojure.pprint)
  (:require [semana3.logic :as logic]
            [schema.core :as s]))

(s/set-fn-validation! true)

(def CompraSchema
  "Schema de uma compra"
  {:data            logic/DataValida
   :valor           logic/ValorValido
   :estabelecimento logic/EstabelecimentoValido
   :categoria       logic/CategoriaValida
   :cartao          logic/CartaoValido
   })

(s/defn nova-compra :- CompraSchema
  [data :- logic/DataValida
   valor :- logic/ValorValido
   estabelecimento :- logic/EstabelecimentoValido
   categoria :- logic/CategoriaValida
   cartao :- logic/CartaoValido]
  {:data data, :valor valor, :estabelecimento estabelecimento, :categoria categoria, :cartao cartao})