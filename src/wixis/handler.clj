(ns wixis.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]
            [hiccup.page :as h])
  (:gen-class))

(defn simple-check []
  "UP"
)

(defn index []
  (h/html5
    [:head
     [:title "wix.is"]
     (h/include-css "/main.css")
    ]
    [:body
      [:h1 (simple-check)]]))

(defroutes app-routes
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



(defn start [port]
  (ring/run-jetty app {:port port
                       :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))