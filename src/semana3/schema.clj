(ns semana3.schema
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)
;-------------------------------------------------------------------------------
;validações:
(defn data-no-formato? [data]
  (re-matches #"\d{4}-\d{2}-\d{2}" data))

(defn pelo-menos-2-caracteres? [estabelecimento]
  (> (count estabelecimento) 2))

(defn categoria-valida? [categoria]
  (contains? #{"Alimentação", "Automóvel", "Casa", "Educação", "Lazer", "Saúde"} categoria))

(defn esta-no-range? [cartao]
  (and (> cartao 0) (< cartao 10000000000000000)))

(def DataValida (s/constrained s/Str data-no-formato?))
(def ValorValido (s/constrained BigDecimal pos?))
(def EstabelecimentoValido (s/constrained s/Str pelo-menos-2-caracteres?))
(def CategoriaValida (s/constrained s/Str categoria-valida?))
(def CartaoValido (s/constrained s/Int esta-no-range?))
;-------------------------------------------------------------------------------

(def CompraSchema
  "Schema de uma compra"
  {:data            DataValida
   :valor           ValorValido
   :estabelecimento EstabelecimentoValido
   :categoria       CategoriaValida
   :cartao          CartaoValido
   })

;(pprint (s/validate CompraSchema {:data "2022-08-01", :valor 100.00, :estabelecimento "Leroy Merlim", :categoria "Casa", :cartao 1000000000000000}))

(s/defn nova-compra :- CompraSchema
  [data :- DataValida
   valor :- ValorValido
   estabelecimento :- EstabelecimentoValido
   categoria :- CategoriaValida
   cartao :- CartaoValido]
  {:data data, :valor valor, :estabelecimento estabelecimento, :categoria categoria, :cartao cartao})

"compra valida"
(pprint (nova-compra "1990-03-02" 100M "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com data no formato incorreto"
;(pprint (nova-compra "20-03-1990" 100.00 "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com valor incorreto"
;(pprint (nova-compra "1990-03-20" -10.00 "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com estabelecimento incorreto"
;(pprint (nova-compra "1990-03-20" 100.00 "w" "Alimentação" 1234123412341234))
"compra com categoria inválida"
;(pprint (nova-compra "1990-03-20" 100.00 "Churrasco do Brabo" "PBKids" 1234123412341234))
"Compra com Cartão inválido"
;(pprint (nova-compra "1990-03-20" 100.00 "Churrasco do Brabo" "Alimentação" 10000000000000001))

