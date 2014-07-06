(ns premierleague-results.couch
  (:require [com.ashafa.clutch :as clutch]
            [premierleague-results.core :as results]
            [clj-time.format :as f]
            [clj-time.coerce :as tc]))

(def date-formatter "EEE d MMM YYYY H:m")

(defn to-seconds-from-epoch [match-date-time]
  (/ (tc/to-long (f/parse (f/formatter date-formatter) match-date-time))
      1000))

(defn all-matches-for-couch []
  (->> (results/all-matches)
       (map #(merge %1 (results/match-scores (:score %1))))
       (map #(dissoc %1 :score))
       (map #(assoc %1 :datetime (to-seconds-from-epoch (str (:date %1) " " (:time %1)))))
       (map #(dissoc %1 :date :time))
       (map #(assoc %1 :competition "EPL"))))

(defn -main [& _]
  (let [db (clutch/get-database "results")]
    (doseq [match (all-matches-for-couch)]
        (clutch/put-document db match))))

