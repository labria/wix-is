(ns wixis.handler
  (:use compojure.core)
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]
            [hiccup.page :as h]
            [clj-http.client :as client])
  (:gen-class))

(defn simple-check []
  "UP"
)

(defn check []
  (try+
    (let [resp (client/get "http://www.wix.com/" {:socket-timeout 1000 :conn-timeout 1000})]
    (not (nil? (re-matches #".*public.*" (get (:headers resp) "x-seen-by")))))
    (catch Object _
      false)))

(defn index []
  (h/html5
    [:head
     [:title "wix.is"]
     (h/include-css "/main.css")
    ]
    [:body
      [:h1 (if (check) "UP" "DOWN")]]))

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