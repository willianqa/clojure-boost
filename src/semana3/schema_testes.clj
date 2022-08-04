(ns semana3.schema-testes
  (:use clojure.pprint)
  (:require [semana3.schema :as semana3]))

"compra valida"
(pprint (semana3/nova-compra "1990-03-02" 100M "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com data no formato incorreto"
;(pprint (semana3/nova-compra "20-03-1990" 100.00 "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com valor incorreto"
;(pprint (semana3/nova-compra "1990-03-20" -10.00 "Churrasco do Brabo" "Alimentação" 1234123412341234))
"compra com estabelecimento incorreto"
;(pprint (semana3/nova-compra "1990-03-20" 100.00 "w" "Alimentação" 1234123412341234))
"compra com categoria inválida"
;(pprint (semana3/nova-compra "1990-03-20" 100.00 "Churrasco do Brabo" "PBKids" 1234123412341234))
"Compra com Cartão inválido"
;(pprint (semana3/nova-compra "1990-03-20" 100.00 "Churrasco do Brabo" "Alimentação" 10000000000000001))

