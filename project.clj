(defproject clojure-boost "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :creds :gpg}}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clojure.java-time "0.3.3"]
                 [prismatic/schema "1.3.5"]
                 [org.clojure/test.check "0.10.0"]
                 [com.datomic/datomic-pro "1.0.6397"]]
  :repl-options {:init-ns semana1.core})
