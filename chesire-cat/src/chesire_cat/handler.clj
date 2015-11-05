(ns chesire-cat.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cheshire.core :refer :all :as json]
            [ring.middleware.json :as ring-json]
            [ring.util.response :as rr]))

(defroutes app-routes
  (GET "/" [] "Hello World!!!")
  (GET "/cheshire-cat" [] "Smile!!!")
  (GET "/json" []
       {
        :status 200
        :headers {"Content-Type" "application/json; charset=utf-8"}
        :body (json/generate-string {:first "Brad" :last "Lucas"})
        }
       )
  (GET "/json2" []
       (rr/response {:first "Brad" :last "Lucas"}))
  (route/not-found "Not Found"))

;; see project.clj :ring :handler
(def app
  ;;  (wrap-defaults app-routes site-defaults)
  (-> app-routes
      (ring-json/wrap-json-response)
      (wrap-defaults site-defaults)))

