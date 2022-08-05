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

(def DataSchema
  "Schema de uma data"
  {:data logic/DataValida})

(def ValorSchema
  "Schema de um valor"
  {:valor logic/ValorValido})

(def EstabelecimentoSchema
  "Schema de um estabelecimento"
  {:estabelecimento logic/EstabelecimentoValido})

(def CategoriaSchema
  "Schema de uma categoria"
  {:categoria logic/CategoriaValida})

(def CartaoSchema
  "Schema de um cartao"
  {:cartao logic/CartaoValido})

