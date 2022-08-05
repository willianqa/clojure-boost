(ns semana3.modelo
  (:use clojure.pprint)
  (:require [semana3.schema_validacoes :as validacoes]
            [schema.core :as s]))

(s/set-fn-validation! true)

(def CompraSchema
  "Schema de uma compra"
  {:data            validacoes/DataValida
   :valor           validacoes/ValorValido
   :estabelecimento validacoes/EstabelecimentoValido
   :categoria       validacoes/CategoriaValida
   :cartao          validacoes/CartaoValido})

(def DataSchema
  "Schema de uma data"
  {:data validacoes/DataValida})

(def ValorSchema
  "Schema de um valor"
  {:valor validacoes/ValorValido})

(def EstabelecimentoSchema
  "Schema de um estabelecimento"
  {:estabelecimento validacoes/EstabelecimentoValido})

(def CategoriaSchema
  "Schema de uma categoria"
  {:categoria validacoes/CategoriaValida})

(def CartaoSchema
  "Schema de um cartao"
  {:cartao validacoes/CartaoValido})

