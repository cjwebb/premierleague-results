(defproject premierleague-results "0.1.0-SNAPSHOT"
  :description "Scraping English Premier League website to retrieve match results"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.5"]]
  :main ^:skip-aot premierleague-results.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
