(defproject wixis "0.1.0-SNAPSHOT"
  :description "wix.is application"
  :url "http://wix.is/"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler wixis.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
