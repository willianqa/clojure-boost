(ns semana3.schema-testes
  (:use clojure.pprint)
  (:require [semana3.schemas :as schemas]))

"compra valida"
(pprint (schemas/nova-compra "2022-02-02" 100M "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com data no formato incorreto"
;(pprint (schemas/nova-compra "20-03-1990" 100.00 "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com valor incorreto"
;(pprint (schemas/nova-compra "1990-03-20" -10.00 "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com estabelecimento incorreto"
;(pprint (schemas/nova-compra "1990-03-20" 100.00 "w" "Alimentação" 1234123412341234))
"compra com categoria inválida"
;(pprint (schemas/nova-compra "1990-03-20" 100.00 "Churrasco do Brabo" "PBKids" 1234123412341234))
"Compra com Cartão inválido"
;(pprint (schemas/nova-compra "1990-03-20" 100.00 "Churrasco do Brabo" "Alimentação" 10000000000000001))

