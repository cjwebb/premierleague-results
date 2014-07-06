(defproject premierleague-results "0.1.0-SNAPSHOT"
  :description "Scraping English Premier League website to retrieve match results"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.5"]
                 [org.clojure/algo.generic "0.1.2"]
                 [com.ashafa/clutch "0.4.0-RC1"]]
  :main ^:skip-aot premierleague-results.couch
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
