(ns semana4.model.model)

(defn nova-compra [data valor estabelecimento categoria cartao]
  {:compra/data data
   :compra/valor valor
   :compra/estabelecimento estabelecimento
   :compra/categoria categoria
   :compra/cartao cartao})