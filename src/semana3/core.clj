(ns semana3.core
  (:require [semana3.schema :as schema]
            [schema.core :as s]
            [semana3.logic :as logic]))


(s/defn nova-compra :- schema/CompraSchema
        [data :- logic/DataValida
         valor :- logic/ValorValido
         estabelecimento :- logic/EstabelecimentoValido
         categoria :- logic/CategoriaValida
         cartao :- logic/CartaoValido]
        {:data data, :valor valor, :estabelecimento estabelecimento, :categoria categoria, :cartao cartao})

(s/defn valida-data :- schema/DataSchema
        [data :- logic/DataValida]
        {:data data})

(s/defn valida-valor :- schema/ValorSchema
        [valor :- logic/ValorValido]
        {:valor valor})

(s/defn valida-estabelecimento :- schema/EstabelecimentoSchema
        [estabelecimento :- logic/EstabelecimentoValido]
        {:estabelecimento estabelecimento})

(s/defn valida-categoria :- schema/CategoriaSchema
        [categoria :- logic/CategoriaValida]
        {:categoria categoria})

(s/defn valida-cartao :- schema/CartaoSchema
        [cartao :- logic/CartaoValido]
        {:cartao cartao})
