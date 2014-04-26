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
    (let [resp (client/get "http://www.wix.com/" {:socket-timeout 1000 :conn-timeout 1000})
          seen-by (get (:headers resp) "x-seen-by")]
      (not (nil? (re-matches #".*public.*" seen-by))))
    (catch Object _
      false)))

(defn index []
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "wix.is"]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:style "h1{left:0;line-height:200px;margin-top:-100px;position:absolute;text-align:center;top:50%;width:100%;font-size:10em;font-family:arial,sans-serif}"]
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
  (ring/run-jetty app {:port  port
                       :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))