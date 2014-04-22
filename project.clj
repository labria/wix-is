(defproject wixis "0.1.0-SNAPSHOT"
  :description "wix.is application"
  :url "http://wix.is/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-ancient "0.5.5"]]
  :ring {:handler wixis.handler/app}
  :uberjar-name "wixis-standalone.jar"
  :profiles {:uberjar {:main wixis.handler, :aot :all}
  :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                       [ring-mock "0.1.5"]]}})
