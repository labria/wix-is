(ns wixis.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :as h]))

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
