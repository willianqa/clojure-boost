(ns semana3.schema
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def CompraSchema
  "Schema de uma compra"
  {:data s/Str
   :valor s/Num
   :estabelecimento s/Str
   :categoria s/Str
   :cartao s/Num
   })

;(pprint (s/explain CompraSchema))
(pprint (s/validate CompraSchema {:data "2022-08-02", :valor 100, :estabelecimento "pbkids", :categoria "Brinquedos", :cartao 12341234}))
;nao funciona pois falta um valor
;(pprint (s/validate CompraSchema {:valor 100, :estabelecimento "pbkids", :categoria "Brinquedos", :cartao 12341234}))

;nao da match na CompraSchema tem um campo a mais
;(s/defn nova-compra :- CompraSchema
;  [data :- s/Str
;   valor :- s/Num
;   estabelecimento :- s/Str
;   categoria :- s/Str
;   cartao :- s/Num]
;  {:data data, :valor valor, :estabelecimento estabelecimento, :categoria categoria, :cartao cartao :amais []})

(s/defn nova-compra :- CompraSchema
  [data :- s/Str
   valor :- s/Num
   estabelecimento :- s/Str
   categoria :- s/Str
   cartao :- s/Num]
  {:data data, :valor valor, :estabelecimento estabelecimento, :categoria categoria, :cartao cartao})

(pprint (nova-compra "10/10/2010" 100.00 "pbkids" "brinquedos" 12341234))