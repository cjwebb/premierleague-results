# premierleague-results

Scraping English Premier League website to retrieve match results.

This is very much a work in progress, so please don't complain too much if the code is terrible, or it doesn't work.

Also, most of the idea is taken from [a talk by Mark Needham](
http://datasciencelondon.org/exploring-football-data-ranking-teams-using-clojure-and-friends-by-mark-needham/)

## Usage

Clone project, and follow rough comments in `make_url.clj`. If you change the `project.clj` to use that file as the main class, you can then do `lein run > urls.txt` to generate all the webpages we will get results from.

Instead of a web-crawler, I simply used `wget` to fetch them all. The Premier League website is quite slow, and possibly objects to wgetting so many pages at once.

Once you have a file full of urls to fetch, run

    cat urls.txt | xargs -Iuri wget uri

This will fetch them. Alternatively, you can use the out-of-date versions already provided in the resources directory.

## Examples

With your resources directory full, open up a `lein repl`.

    premierleague-results.core=> (take 1 (all-matches))
    ({:date "Tuesday 15 April 2014", :location "Emirates Stadium", :away-team "West Ham", :score "3 - 1", :home-team "Arsenal", :time "19:45"})

You can find the last 2 home-team games using `home-team`. The `away-team` function also exists.

    premierleague-results.core=> (take 2 (home-team "Arsenal"))
    ({:date "Tuesday 15 April 2014", :location "Emirates Stadium", :away-team "West Ham", :score "3 - 1", :home-team "Arsenal", :time "19:45"}
     {:date "Saturday 29 March 2014", :location "Emirates Stadium", :away-team "Man City", :score "1 - 1", :home-team "Arsenal", :time "17:30"})

The `team-result` function evaluates Win/Loss/Draw for a match:

    premierleague-results.core=> (take 2 (map team-result (all-matches)))
    ({"Arsenal" "W", "West Ham" "L"} {"Swansea" "L", "Chelsea" "W"})


### Bugs

Probably lots. There aren't any tests either. I've mainly just been playing around with the REPL to get something to work.

## License

Copyright © 2014 Colin Webb

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
