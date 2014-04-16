(ns premierleague-results.make_urls
  (:gen-class))

(defn -main
  ; We have lots of URLs like the following:
  ; http://www.premierleague.com/en-gb/matchday/results.html?
  ;  paramClubId=ALL&paramComp_8=true&paramSeason=1993-1994&view=.dateSeason
  ; Only the paramSeason changes.
  ; Work out what they are.
  [& args]

  (def base-uri (str "http://www.premierleague.com/en-gb/matchday/results.html?"
    "paramClubId=ALL&paramComp_8=true&paramSeason=%s&view=.dateSeason"))

  (def seasons
    ; Remember that range end is exclusive
    (map (fn [[y1 y2]] (str y1 "-" y2))
    (zipmap (range 1992 2014)
            (range 1993 2015))))

  (defn make-nice-url [s] (format base-uri s))

  (doall (map println (map make-nice-url seasons))))

  ; pipe that to a file, and then `cat urls.txt | xargs -Iuri wget uri`
