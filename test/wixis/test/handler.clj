(ns wixis.test.handler
  (:use clojure.test
        ring.mock.request
        wixis.handler
        clj-http.fake))

(deftest test-routes
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404))))
  (testing "simple check"
    (is (= (simple-check) "UP"))))

(deftest request-checks
  (with-fake-routes {
                      "http://www.wix.com/" (fn [request] {:status 200 :headers {"x-seen-by" "some public server"} :body "Hey, do I look like wix.com?"})
                      }
                    (testing "positive check with one x-seen-by"
                      (is (= (check) true))))
  (with-fake-routes {
                      "http://www.wix.com/" (fn [request] {:status 200 :headers {"x-seen-by" ["sputnik" "some public server"]} :body "Hey, do I look like wix.com?"})
                      }
                    (testing "positive check with one x-seen-by"
                      (is (= (check) true))))
  (with-fake-routes {
                      "http://www.wix.com/" (fn [request] {:status 404 :headers {"x-seen-by" "some public server"} :body "Hey, do I look like wix.com?"})
                      }
                    (testing "404 from public"
                      (is (= (check) false))))

  (with-fake-routes {
                      "http://www.wix.com/" (fn [request] {:status 200 :headers {"x-seen-by" "not us"} :body "Hey, do I look like wix.com?"})
                      }
                    (testing "200 not from public"
                      (is (= (check) false))))

  (with-fake-routes {
                      "http://www.wix.com/" (fn [request] {:status 200 :headers {} :body "Hey, do I look like wix.com?"})
                      }
                    (testing "200 not from public, without x-seen-by"
                      (is (= (check) false)))))