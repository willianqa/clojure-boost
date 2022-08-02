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
                   :categoria       "Alimeintação"
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
    (+ 1 (apply max (map :id compras)))
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
       (remove #(= id (:id %)))
       vec))
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
(def estabelecimento (:estabelecimento nova-compra))
(def categoria {"Alimentação", "Automóvel", "Casa", "Educação", "Lazer", "Saúde"})

(defn valida-compra [nova-compra]
  (let [data-valida (re-matcher #"\d{4}-\d{2}-\d{2}" (:data nova-compra))
        valor-valido (> (:valor nova-compra)0)
        estabelecimento-valido (> (count estabelecimento) 2)
        categoria-valida (contains? categoria (:categoria nova-compra))
        ]
    (if (and data-valida valor-valido estabelecimento-valido categoria-valida)
      (println "Tudo ok com a sua compra")
      (throw (ex-info "Compra com dados incorretor"
                      {:valor           valor-valido
                       :estabelecimento estabelecimento-valido
                       :categoria       categoria-valida
                       :data            data-valida
                       })))))

    (pprint (valida-compra nova-compra))













