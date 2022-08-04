(ns clojure-boost.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [semana1.core :as semana1]
            [semana3.schema :as semana3]
            [semana3.logic :as logic]
            [schema.core :as s]))

(s/set-fn-validation! true)

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

(s/validate logic/DataValida "2022-05-09")
(s/validate logic/ValorValido 100M)
(s/validate logic/EstabelecimentoValido "Amazon")
(s/validate logic/CategoriaValida "Casa")
(s/validate logic/CartaoValido 1111222233334444)

(s/validate semana3/CompraSchema {:data            "2022-05-09"
                                  :valor           100M
                                  :estabelecimento "Amazon"
                                  :categoria       "Casa"
                                  :cartao          1111222233334444})

(deftest total-gasto?-test
  (testing "valida se total gasto é igual a R$ 600.0"
    (is (= (semana1/total-gasto 1234123412341234 vetor-de-compras) 600.0)))

  (testing "valida que o total gasto não é igual a R$ 600.0"
    (is (not (= (semana1/total-gasto 1234123412341234 vetor-de-compras) 500.0)))))


(deftest compras-agrupadas?-test
  (testing "valida se as compras agrupadas corretamente"
    (is (= (semana1/compras-agrupadas vetor-de-compras) {"Alimentação" 100.00, "Saúde" 200.00, "Casa" 300.00})))

  (testing "valida que as compras agrupadas estão com erro no match do valor"
    (is (not (= (semana1/compras-agrupadas vetor-de-compras) {"Alimentação" 100.00, "Saúde" 200.00, "Casa" 500.00})))))

(deftest schema-de-compras?-test
  (testing "valida o shcema de compras"
    (is (= (semana3/nova-compra "2022-05-09" 100M "Amazon" "Casa" 1111222233334444)
           {:data            "2022-05-09",
            :valor           100M,
            :estabelecimento "Amazon",
            :categoria       "Casa",
            :cartao          1111222233334444})))

  (testing "Compra com data inválida"
    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "03-08-2022" 100M "Amazon" "Casa" 1111222233334444))))

  (testing "Compra com valor negativo"
    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08-03" -100M "Amazon" "Casa" 1111222233334444))))

  (testing "Estabelecimento com String vazia"
    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08-03" 100M " " "Casa" 1111222233334444))))

  (testing "Categoria Inválida"
    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08-03" 100M "Amazon" "PBKids" 1111222233334444))))

  (testing "Cartao com número Inválido"
    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08-03" 100M "Amazon" "Casa" 111122223333444499)))))

;--------------------------------------------------------------------------------

(deftest outros-testes?
  (testing "testando limites da data"
    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2 0 2 2-08-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-0 8-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08-0 3" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "mmmm-08-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-MM-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08-dd" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra " " 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "-08-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022-08" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra "2022" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (semana3/nova-compra 20220804 100M "Amazon" "Casa" 1111222233334444)))
    ))



