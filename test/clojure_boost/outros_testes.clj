(ns clojure-boost.outros_testes
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [schema.core :as s]
            [semana3.schemas :as schemas]))

(s/set-fn-validation! true)

;******************************************************************************************
;"Estabelecimento"
(deftest testando-estabelecimento?-test
  (testing "Testando estabelecimentos válidos"
    (let [estabelecimentos-validos ["ll" "-1" "00" "__" "??" "clojure" "python"]]
      (doseq [nome estabelecimentos-validos]
        (println "estabelecimento válido:" nome)
        (is (schemas/valida-estabelecimento nome)))))

  (testing "Testando estabelecimentos inválidos"
    (let [estabelecimentos-invalidos [" " "1" "0" "_" "?" "c" nil]]
      (doseq [nome estabelecimentos-invalidos]
        (println "estabelecimento inválido:" nome)
        (is (thrown? clojure.lang.ExceptionInfo
                     (schemas/valida-estabelecimento nome)))))))
;******************************************************************************************
;"Data"
(deftest testa-data
  (testing "Testando datas válidas"
    (let [datas-validas ["2022-02-02" "2021-01-01" "1990-10-10" "9999-99-99"]]
      (doseq [data datas-validas]
        (println "data válida:" data)
        (is (schemas/valida-data data)))))

  (testing "Testando datas inválidas"
    (let [datas-invalidas ["yyyy-mm-dd" "10-10-2022" nil "" "2022" "01-2022"]]
      (doseq [data datas-invalidas]
        (println "data inválida:" data)
        (is (thrown? clojure.lang.ExceptionInfo
                     (schemas/valida-data datas-invalidas)))))))
;******************************************************************************************
;"Valor"
(deftest testa-valor
  (testing "Testando valores válidos"
    (let [valores-validos [0.00001M 0.5M 1M 2M 10M 20M 30M 100M 1000M 20000M 30000M 999999999M]]
      (doseq [valor valores-validos]
        (println "valor válido:" valor)
        (is (schemas/valida-valor valor)))))

  (testing "Testando valores inválidos"
    (let [valores-invalidos [0.0 0.01 1 2 3 500.00 10000.00 nil "10M" ""]]
      (doseq [valor valores-invalidos]
        (println "valor inválido:" valor)
        (is (thrown? clojure.lang.ExceptionInfo
                     (schemas/valida-valor valor)))))))
;******************************************************************************************
;"Categoria"
(deftest testa-categoria
  (testing "Testando categorias válidas"
    (let [categorias-validas ["Alimentação", "Automóvel", "Casa", "Educação", "Lazer", "Saúde"]]
      (doseq [categoria categorias-validas]
        (println "categoria válida:" categoria)
        (is (schemas/valida-categoria categoria)))))

  (testing "Testando categorias inválidas"
    (let [categorias-invalidas ["" nil "alimentação" "automóvel", "casa", "educação", "lazer", "saúde"]]
      (doseq [categoria categorias-invalidas]
        (println "categoria inválida:" categoria)
        (is (thrown? clojure.lang.ExceptionInfo
                     (schemas/valida-categoria categoria)))))))
;******************************************************************************************
"Cartao"
(deftest testa-cartao
  (testing "Testando cartões válidos"
    (let [cartoes-validos [1 2 3 4 5 10 100 1000 10000 200000 99999999 1000000000000000]]
      (doseq [cartao cartoes-validos]
        (println "cartao válido:" cartao)
        (is (schemas/valida-cartao cartao)))))

  (testing "Testando cartões inválidos"
    (let [cartoes-invalidos ["" nil 0  0.1 10000000000000000 10000000000000001 100M "1" "1000000000000000"]]
      (doseq [cartao cartoes-invalidos]
        (println "cartao inválido:" cartao)
        (is (thrown? clojure.lang.ExceptionInfo
                     (schemas/valida-cartao cartao)))))))