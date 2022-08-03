(ns clojure-boost.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [semana1.core :as semana1]))

(def vetor-de-compras
  [{:data            "2022-08-01"
     :valor           100.00
     :estabelecimento "Outback"
     :categoria       "Alimentação"
     :cartao          1234123412341234
     }
    {:data            "2022-08-02"
     :valor           200.00
     :estabelecimento "Dentista"
     :categoria       "Saúde"
     :cartao          1234123412341234
     }
     {:data            "2022-08-03"
      :valor           300.00
      :estabelecimento "Leroy Merlin"
      :categoria       "Casa"
      :cartao          1234123412341234}])

(pprint (semana1/total-gasto 1234123412341234 vetor-de-compras))
;(pprint (seman1/compras))

(deftest total-gasto?-test
  (testing "total gasto é igual a R$ 600.0"
    (is (= (semana1/total-gasto 1234123412341234 vetor-de-compras) 600.0)))

  (testing "total gasto não é igual a R$ 600.0"
    (is (not(= (semana1/total-gasto 1234123412341234 vetor-de-compras) 500.0)))))


(deftest compras-agrupadas?-test
  (testing "valida as compras agrupadas"
    (is (= (semana1/compras-agrupadas vetor-de-compras) {"Alimentação" 100.00, "Saúde" 200.00, "Casa" 300.00})))
  )





