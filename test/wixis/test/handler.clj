(ns wixis.test.handler
  (:use clojure.test
        ring.mock.request  
        wixis.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404))))
  (testing "simple check"
    (is (= (simple-check) "UP")))
)
