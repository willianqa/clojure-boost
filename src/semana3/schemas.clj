(ns semana3.schemas
  (:require [semana3.modelo :as modelo]
            [schema.core :as s]
            [semana3.schema_validacoes :as validacoes]))


(s/defn nova-compra :- modelo/CompraSchema
        [data :- validacoes/DataValida
         valor :- validacoes/ValorValido
         estabelecimento :- validacoes/EstabelecimentoValido
         categoria :- validacoes/CategoriaValida
         cartao :- validacoes/CartaoValido]
        {:data data, :valor valor, :estabelecimento estabelecimento, :categoria categoria, :cartao cartao})

(s/defn valida-data :- modelo/DataSchema
        [data :- validacoes/DataValida]
        {:data data})

(s/defn valida-valor :- modelo/ValorSchema
        [valor :- validacoes/ValorValido]
        {:valor valor})

(s/defn valida-estabelecimento :- modelo/EstabelecimentoSchema
        [estabelecimento :- validacoes/EstabelecimentoValido]
        {:estabelecimento estabelecimento})

(s/defn valida-categoria :- modelo/CategoriaSchema
        [categoria :- validacoes/CategoriaValida]
        {:categoria categoria})

(s/defn valida-cartao :- modelo/CartaoSchema
        [cartao :- validacoes/CartaoValido]
        {:cartao cartao})
