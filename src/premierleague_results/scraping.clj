(ns premierleague-results.scraping
  (:require [net.cgrand.enlive-html :as html])
  (:gen-class))

(defn- fetch-file [name]
  (html/html-resource (clojure.java.io/resource name)))

(defn- content-tables [file-name]
  (html/select (fetch-file file-name) [[:table.contentTable]]))

(defn- extract-matches [node]
  "Extract match information from .fixturechangerow"
  (let [time      (first (html/select [node] [[:td.time]]))
        home-team (first (html/select [node] [[:td.rHome] [:a]]))
        score     (first (html/select [node] [[:td.score] [:a]]))
        away-team (first (html/select [node] [[:td.rAway] [:a]]))
        location  (first (html/select [node] [[:td.location] [:a]]))
        result    (map html/text [time home-team score away-team location])]
    (zipmap [:time :home-team :score :away-team :location] result)))

(defn- extract [node]
  (let [match-date (map html/text (html/select [node] [[:tr html/first-of-type] [:th]]))
        matches    (drop 1 (html/select [node] [:tr]))]
    (map (fn [x] (assoc x :date (clojure.string/trim(first match-date)))) (map extract-matches matches))))

(defn matches [file-name]
  (flatten (map extract (content-tables file-name))))

