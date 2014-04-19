(ns premierleague-results.core
  (:use [premierleague-results.scraping :as scraping]))

(def all-files
  "List all files in resources directory. There is probably a better way of doing this!"
  '("results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1992-1993&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1993-1994&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1994-1995&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1995-1996&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1996-1997&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1997-1998&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1998-1999&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=1999-2000&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2000-2001&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2001-2002&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2002-2003&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2003-2004&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2004-2005&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2005-2006&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2006-2007&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2007-2008&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2008-2009&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2009-2010&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2010-2011&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2011-2012&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2012-2013&view=.dateSeason"
 "results.html?paramClubId=ALL&paramComp_8=true&paramSeason=2013-2014&view=.dateSeason"
 ))

(defn all-matches []
  "Returns results for all football matches"
  ; reverse puts them in order, at the moment
  (flatten (map scraping/matches (reverse all-files))))

(defn has-value [key value]
  "Returns a predicate that tests whether a map contains a specific value"
  (fn [m]
    (= value (m key))))

(defn contains-team [value]
  "Returns a predicate that tests whether a map contains a value in :home-team or :away-team"
  (fn [m]
    (or (= value (m :home-team))
        (= value (m :away-team)))))

; Example functions that can be written:
(defn home-team [team-name]
  "Retrieves home matches for specified team"
  (->> (all-matches)
       (filter (has-value :home-team team-name))))

(defn away-team [team-name]
  "Retrieves away matches for specified team"
  (->> (all-matches)
       (filter (has-value :away-team team-name))))

(defn team [team-name]
  "Retrieves all matches for a specified team"
  (->> (all-matches)
       (filter (contains-team team-name))))

(defn head-to-head [team1 team2]
  (->> (all-matches)
       (filter (contains-team team1))
       (filter (contains-team team2))))

(defn -main
  [& args]

  (doseq [match (all-matches)]
    (println match)))
