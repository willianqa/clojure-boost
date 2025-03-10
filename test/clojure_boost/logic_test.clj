(ns clojure-boost.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [semana1.modelo-com-csv.csv :as semana1]
            [semana3.modelo :as modelo]
            [semana3.schemas :as schemas]
            [semana3.schema_validacoes :as validacao]
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

(s/validate validacao/DataValida "2022-05-09")
(s/validate validacao/ValorValido 100M)
(s/validate validacao/EstabelecimentoValido "Amazon")
(s/validate validacao/CategoriaValida "Casa")
(s/validate validacao/CartaoValido 1111222233334444)

(s/validate modelo/CompraSchema {:data             "2022-05-09"
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
    (is (= (schemas/nova-compra "2022-05-09" 100M "Amazon" "Casa" 1111222233334444)
           {:data            "2022-05-09",
            :valor           100M,
            :estabelecimento "Amazon",
            :categoria       "Casa",
            :cartao          1111222233334444})))

  (testing "Compra com data inválida"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "03-08-2022" 100M "Amazon" "Casa" 1111222233334444))))

  (testing "Compra com valor negativo"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-03" -100M "Amazon" "Casa" 1111222233334444))))

  (testing "Estabelecimento com String vazia"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-03" 100M " " "Casa" 1111222233334444))))

  (testing "Categoria Inválida"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-03" 100M "Amazon" "PBKids" 1111222233334444))))

  (testing "Cartao com número Inválido"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-03" 100M "Amazon" "Casa" 111122223333444499)))))

;--------------------------------------------------------------------------------

(deftest outros-testes-data?
  (testing "testando limites da data"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2 0 2 2-08-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-0 8-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-0 3" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "mmmm-08-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-MM-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-dd" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra " " 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "-08-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-03" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022" 100M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra 20220804 100M "Amazon" "Casa" 1111222233334444)))))

(deftest otros-testes-valor?
  (testing "Testando limites do valor"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 100 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 100.0 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 100.00 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 100.50 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" "100M" "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ArityException
                 (schemas/nova-compra "2022-08-10" "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 0M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 0 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" -10M "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" -10 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" -0.10 "Amazon" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" -10.0 "Amazon" "Casa" 1111222233334444)))))

(deftest outros-testes-estabelecimento?
  (testing "Validando limites do estabelecimento"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "A" "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M 2 "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M nil "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M " " "Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ArityException
                 (schemas/nova-compra "2022-08-10" 10M "Casa" 1111222233334444)))))

(deftest outros-testes-categoria?
  (testing "Validando limites da categoria"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "CASA" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "CasA" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" " Casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa " 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "casa" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "PBKids" 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" nil 1111222233334444)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" " " 1111222233334444)))

    (is (thrown? clojure.lang.ArityException
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" 1111222233334444)))))

(deftest outros-testes-cartao?
  (testing "Validando limites do cartão"
    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" 0)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" 10000000000000000)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" -1)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" "1000000000000000")))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" 1000000000000000M)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" 0.1000000000000000)))

    (is (thrown? clojure.lang.ExceptionInfo
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa" nil)))

    (is (thrown? clojure.lang.ArityException
                 (schemas/nova-compra "2022-08-10" 10M "Alimentação" "Casa")))
    ))

