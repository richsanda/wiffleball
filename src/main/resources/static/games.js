$(pageBehavior)

function pageBehavior () {

    var url = "/w/games";

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
        console.log(data);
        $('#games').append(showGames(data));
    });
}

function showGames(games) {

    $.each(games, function() {

        var status = this.gameStatus;
        var awayTeam = status.awayTeam;
        var homeTeam = status.homeTeam;

        var awayTeamStr, homeTeamStr;

        $.each(homeTeam.players, function() {
            if (null == homeTeamStr) {
                homeTeamStr = this.name;
            } else {
                homeTeamStr += ", " + this.name;
            }
        });

        $.each(awayTeam.players, function() {
            if (null == awayTeamStr) {
                awayTeamStr = this.name;
            } else {
                awayTeamStr += ", " + this.name;
            }
        });

        var score = "(" + status.awayScore + ", " + status.homeScore + ")";

        var gameDiv = $("<div class='game'>" + awayTeamStr + " vs " + homeTeamStr + " " + score + "</div>");
        $("#games").append(gameDiv);
    });

    /*

    {
      "key" : "1",
      "gameStatus" : {
        "awayBatterIndex" : 0,
        "homeBatterIndex" : 0,
        "awayScore" : 5,
        "homeScore" : 2,
        "outs" : 0,
        "inning" : 4,
        "numberOfInnings" : 3,
        "over" : true,
        "homeHalf" : false,
        "platedRuns" : [ ],
        "awayTeam" : {
          "players" : [ {
            "name" : "bill"
          }, {
            "name" : "justin"
          }, {
            "name" : "john"
          } ]
        },
        "homeTeam" : {
          "players" : [ {
            "name" : "jim"
          }, {
            "name" : "shawn"
          }, {
            "name" : "rich"
          } ]
        },
        "pitcher" : {
          "name" : "shawn"
        },
        "batter" : {
          "name" : "bill"
        },
        "awayBatter" : {
          "name" : "bill"
        },
        "homeBatter" : {
          "name" : "jim"
        },
        "awayPitcher" : {
          "name" : "john"
        },
        "homePitcher" : {
          "name" : "shawn"
        },
        "onFirst" : null,
        "onSecond" : null,
        "onThird" : null

     */
}