(ns semana3.logic
  (:require [schema.core :as s]))


(defn data-no-formato? [data]
  (re-matches #"\d{4}-\d{2}-\d{2}" data))

(defn pelo-menos-2-caracteres? [estabelecimento]
  (> (count estabelecimento) 2))

(defn categoria-valida? [categoria]
  (contains? #{"Alimentação", "Automóvel", "Casa", "Educação", "Lazer", "Saúde"} categoria))

(defn esta-no-range? [cartao]
  (and (> cartao 0) (< cartao 10000000000000000)))

(def DataValida (s/constrained s/Str data-no-formato?))
(def ValorValido (s/constrained BigDecimal pos?))
(def EstabelecimentoValido (s/constrained s/Str pelo-menos-2-caracteres?))
(def CategoriaValida (s/constrained s/Str categoria-valida?))
(def CartaoValido (s/constrained s/Int esta-no-range?))