(ns semana2.core
  (:use clojure.pprint))

;-------------------------------------------------------------------------------------------------------------
(def repositorio-de-compras (atom []))
;-------------------------------------------------------------------------------------------------------------
(defrecord Compra [^Long id, ^String data, ^BigDecimal valor, ^String estabelecimento, ^String categoria, ^long cartao])
;-------------------------------------------------------------------------------------------------------------
(def nova-compra
  (map->Compra  {
                   :data            "2022-07-25"
                   :valor           350.00
                   :estabelecimento "PBKids"
                   :categoria       "Brinquedos"
                   :cartao          1234123412341234}))
(println " Nova compra sem id:")
(pprint nova-compra)
;-------------------------------------------------------------------------------------------------------------
(def compras [{
                 :id              1
                 :data            "2022-07-25"
                 :valor           100.00
                 :estabelecimento "Outback"
                 :categoria       "Alimentação"
                 :cartao          1234123412341234}
                {
                 :id              2
                 :data            "2022-07-25"
                 :valor           200.00
                 :estabelecimento "Mc Donalds"
                 :categoria       "Alimentação"
                 :cartao          1234123412341234}
               {
                 :id              3
                 :data            "2022-07-25"
                 :valor           300.00
                 :estabelecimento "Madeiro"
                 :categoria       "Alimentação"
                 :cartao          1234123412341234}])
(println "Lista as compras cadastradas:")
(pprint compras)
;-------------------------------------------------------------------------------------------------------------
;(defn insere-compra [compras nova-compra]
;                     (let [id-filtrado (:id (last (sort-by :id compras)))
;                           nova-compra-com-id (if id-filtrado
;                                       (merge nova-compra {:id (inc id-filtrado)})
;                                       (merge nova-compra {:id 1}))]
;                       (conj compras nova-compra-com-id)))

(defn gera-id [compras]
  (if-not (empty? compras)
    (+ 1 (apply max (map :id compras))) ; pega o id máximo e soma 1
    1))

(defn insere-compra [compras nova-compra]
  (let [id-gerado (gera-id compras)
        compra-com-id (assoc nova-compra :id id-gerado)]
    (conj compras compra-com-id)))

(println "Insere compra com id criado na lista de compras:")
(pprint (insere-compra compras nova-compra))
;-------------------------------------------------------------------------------------------------------------
(defn insere-compra! [nova-compra repositorio-de-compras]
  (swap! repositorio-de-compras insere-compra nova-compra))
(println "insere compra no atomo:")
(insere-compra! nova-compra repositorio-de-compras)
(pprint @repositorio-de-compras)
;-------------------------------------------------------------------------------------------------------------
(defn lista-compras! [repositorio-de-compras]
  (pprint (deref repositorio-de-compras))
  )
(println "Listar compras do átomo")
(lista-compras! repositorio-de-compras)
;-------------------------------------------------------------------------------------------------------------
(defn exclui-compra
  [compras id]
  (->> compras
       (remove #(= id (:id %)))))
(println "Exclui-compra por id")
(pprint (exclui-compra compras 1))
;-------------------------------------------------------------------------------------------------------------
(defn exclui-compra! [repositorio-de-compras id]
  (swap! repositorio-de-compras exclui-compra id))
(println "atomo antes da exclusao:")
(pprint @repositorio-de-compras)
(exclui-compra! repositorio-de-compras 1)
(println "atomo depois da exclusao:")
(pprint @repositorio-de-compras)
;-------------------------------------------------------------------------------------------------------------
;(defn valida-data? [nova-compra data]
;  (-> nova-compra
;      (get data)
;      (= (format "yyyy-mm-dd"))))
;
;(defn valida-compra [nova-compra]
;  (if (valida-data nova-compra data))
;  (if valor  = BigDecimal)
;  (if Estabelecimento  = String com pelo menos 2 caracteres)
;  (if categoria  = Alimentação ou Automóvel ou Casa ou Educação ou Lazer ou Saúde.)
;  )














