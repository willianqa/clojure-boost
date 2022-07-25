(ns clojure-boost.csv)

(defn nova-compra [[data, valor, estabelecimento, categoria, cartao]]
  {
   :data data,
   :valor valor,
   :estabelecimento estabelecimento,
   :categoria categoria,
   :cartao cartao
   })
;([[data, valor, estabelecimento, categoria, cartao]]
; (nova-compra data, valor, estabelecimento, categoria, cartao))



  (->> (slurp "/Users/willian.nunes/Documents/clojure/clojure-boost/Massa de dados.csv")
       clojure.string/split-lines
       (map #(clojure.string/split % #","))
       rest
        (map #(nova-compra %))
       println)
