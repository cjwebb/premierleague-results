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

; Functions that retrieve the win/loss/draw aspect of matches
(defn- match-scores [s]
  "For a string, such as '2-1' evaluates home/away win, based on home-score first"
  (let [home-score (Integer/parseInt (str (first s)))
        away-score (Integer/parseInt (str (last s)))]
    {:home-score home-score :away-score away-score}))

(defn team-result [match]
  "For a match, evaluates W/D/L for both home and away teams"
  (let [result (result (match-scores (:score match)))
        home-team (:home-team match)
        away-team (:away-team match)]
    (cond
      (= result :home-win) {home-team "W" away-team "L"}
      (= result :away-win) {home-team "L" away-team "W"}
      :else {home-team "D" away-team "D"})))

(defn result [m]
  "Returns an abstract version of result, home-win, away-win or draw"
  (cond
    (= (:home-score m)(:away-score m)) :draw
    (> (:home-score m)(:away-score m)) :home-win
    :else :away-win))

(defn -main
  [& args]

  (doseq [match (all-matches)]
    (println match)))
