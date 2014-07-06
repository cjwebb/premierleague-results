(ns premierleague-results.couch
  (:require [com.ashafa.clutch :as clutch]
            [premierleague-results.core :as results]))

(defn -main [& _]
  (let [db (clutch/get-database "results")]
    (doseq [match (results/all-matches)]
        (clutch/put-document db match))))

