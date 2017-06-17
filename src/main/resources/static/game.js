function buildConsole(game, hideActions) {

    var console = $(
        "<div class=\"console\"> \
             <div class=\"left\"> \
                 <div class=\"status-pane\"></div> \
                 <div class=\"summary-pane\"></div> \
              </div> \
              <div class=\"actions-pane\"></div> \
         </div>"
    );

    if (!hideActions) {
        console.find('div.actions-pane').append(showGameActions(game.key));
        console.find('div.actions-pane').find('div.get-summary').removeClass('get-summary').addClass('get-stats');
    }
    console.find('div.status-pane').append(buildGameStatus(game.gameStatus));
    console.find('div.summary-pane').append(buildGameSummary(game.gameSummary));

    return console;
}

function showGameActions(gameId) {

    var gameActionsDiv = $("<div class='game-actions' id='" + gameId + "'></div>");

    gameActionsDiv.append(
        "<div class='actions'>" +
        "<div class='action button hit-action single' title='single'>" + "1B" + "</div>" +
        "<div class='action button hit-action double' title='double'>" + "2B" + "</div>" +
        "<div class='action button hit-action triple' title='triple'>" + "3B" + "</div>" +
        "<div class='action button hit-action homerun' title='home run'>" + "HR" + "</div>" +
        "<div class='action button walk' title='walk'>" + "BB" + "</div>" +
        "<div class='action button error error-reach' title='reach on error'>" + "E" + "</div>" +
        "<div class='action button error error-advance' title='advance on error'>" + "E+" + "</div>" +
        "<div class='action button skip-batter' title='skip batter'>" + "s" + "</div>" +
        "<div class='action button get-stats' title='stats / summary'>" + "s/s" + "</div>" +
        "</div>" +
        "<div class='actions'>" +
        "<div class='action button out-action groundout' title='ground out'>" + "GO" + "</div>" +
        "<div class='action button out-action lineout' title='line out'>" + "LO" + "</div>" +
        "<div class='action button out-action flyout' title='fly out'>" + "FO" + "</div>" +
        "<div class='action button doubleplay' title='double play'>" + "DP" + "</div>" +
        "<div class='action button strikeout strikeout-swinging' title='strikeout swinging'>" + "K" + "</div>" +
        "<div class='action button strikeout strikeout-looking' title='strikeout looking'>" + "z" + "</div>" +
        "<div class='action button strikeout strikeout-both' title='strikeout swinging and looking'>" + "X" + "</div>" +
        "<div class='action button finalize-game' title='confirm final'>" + "f" + "</div>" +
        "<div class='action button undo' title='undo'>" + "u" + "</div>" +
        "</div>"
    );

    return gameActionsDiv;
}

function buildGameStatus(game) {

    var gameStatusDiv = $("<div class='game-status'></div>");

    var inningsDiv = $("<div class='inning'>" + (game.homeHalf ? "bot " : "top ") + game.inning + "</div>");
    var outsDiv = $("<div class='outs'>" + game.outs + " out" + "</div>");

    var situationDiv = $("<div class='situation'></div>");

    var teamsDiv = $("<div class='teams'></div>");
    var basesDiv = $("<div class='bases'></div>");

    if (game.over) {
        situationDiv.text("final");
    } else {
        situationDiv.append(inningsDiv);
        situationDiv.append(outsDiv);
    }

    gameStatusDiv.append(situationDiv);
    gameStatusDiv.append(teamsDiv);
    gameStatusDiv.append(basesDiv);

    var awayTeamDiv = buildAwayTeam(game);
    var homeTeamDiv = buildHomeTeam(game);

    teamsDiv.append(awayTeamDiv);
    teamsDiv.append(homeTeamDiv);

    var firstBaseDiv = $("<div class='base first-base'>&#160;</div>");
    var secondBaseDiv = $("<div class='base second-base'>&#160;</div>");
    var thirdBaseDiv = $("<div class='base third-base'>&#160;</div>");
    var homeDiv = $("<div class='base home-base'>&#160;</div>");

    if (null != game.onFirst) {
        firstBaseDiv.addClass('occupied');
    }

    if (null != game.onSecond) {
        secondBaseDiv.addClass('occupied');
    }

    if (null != game.onThird) {
        thirdBaseDiv.addClass('occupied');
    }

    basesDiv.append(firstBaseDiv);
    basesDiv.append(secondBaseDiv);
    basesDiv.append(thirdBaseDiv);
    basesDiv.append(homeDiv);

    return gameStatusDiv;
}

function buildAwayTeam(game) {

    var teamDiv = $("<div class='team away-team'></div>");

    if (!game.homeHalf) teamDiv.addClass("batting");

    var lineup = $("<div class='lineup'></div>");

    teamDiv.append("<div class='score'>" + game.awayScore + "</div>");

    var pitcher = $("<div class='player pitcher' id='" + game.awayPitcher.name + "'>" + game.awayPitcher.name + "</div>");

    if (game.homeHalf) { pitcher.addClass("current"); }

    teamDiv.append(pitcher);
    teamDiv.append(lineup);

    $.each(game.awayTeam.players, function() {
        var player = $("<div class='player batter' id='" + this.name + "'>" + this.name + "</div>");
        if (!game.homeHalf) {
            if (game.batter.name == this.name) {
                player.addClass("current");
            }
        } else if (pitcher.attr('id') != this.name) {
            player.addClass("button set-pitcher");
        }

        lineup.append(player);
    });

    return teamDiv;
}

function buildHomeTeam(game) {

    var teamDiv = $("<div class='team home-team'></div>");

    if (game.homeHalf) teamDiv.addClass("batting");

    var lineup = $("<div class='lineup'></div>");

    teamDiv.append("<div class='score'>" + game.homeScore + "</div>");

    var pitcher = $("<div class='player pitcher' id='" + game.homePitcher.name + "'>" + game.homePitcher.name + "</div>");

    if (!game.homeHalf) {
        pitcher.addClass("current");
    }

    teamDiv.append(pitcher);
    teamDiv.append(lineup);

    $.each(game.homeTeam.players, function() {
        var player = $("<div class='player batter' id='" + this.name + "'>" + this.name + "</div>");
        if (game.homeHalf) {
            if (game.batter.name == this.name) {
                player.addClass("current");
            }
        } else if (pitcher.attr('id') != this.name) {
            player.addClass("button set-pitcher");
        }

        lineup.append(player);
    });

    return teamDiv;
}

function buildGameStats(stats) {

    var statsDiv = $("<div class='stats'></div>");

    statsDiv.append(buildBattingStatsHeader());

    $.each(stats.awayTeamStats.playerBattingStats, function(k, v) {

        statsDiv.append(buildPlayerBattingStats(this));
    });

    statsDiv.append("<div><hr/></div>");

    $.each(stats.homeTeamStats.playerBattingStats, function(k, v) {

        statsDiv.append(buildPlayerBattingStats(this));
    });

    statsDiv.append(buildPitchingStatsHeader());

    $.each(stats.awayTeamStats.playerPitchingStats, function(k, v) {

        statsDiv.append(buildPlayerPitchingStats(this));
    });

    statsDiv.append("<div><hr/></div>");

    $.each(stats.homeTeamStats.playerPitchingStats, function(k, v) {

        statsDiv.append(buildPlayerPitchingStats(this));
    });

    return statsDiv;
}

function buildBattingStatsHeader() {
    return $(
        "<div class='batting-stats'> \
            <div class='batting-stat'>&#160;</div> \
            <div class='batting-stat-header'>hits</div> \
            <div class='batting-stat-header'>avg</div> \
            <div class='batting-stat-header'>2b</div> \
            <div class='batting-stat-header'>3b</div> \
            <div class='batting-stat-header'>hr</div> \
            <div class='batting-stat-header'>rbi</div> \
            <div class='batting-stat-header'>k</div> \
            <div class='batting-stat-header'>ops</div> \
        </div>"
    );
}

function buildPitchingStatsHeader() {
    return $(
        "<div class='pitching-stats'> \
            <div class='pitching-stat'>&#160;</div> \
            <div class='pitching-stat-header'>ip</div> \
            <div class='pitching-stat-header'>era</div> \
            <div class='pitching-stat-header'>er</div> \
            <div class='pitching-stat-header'>r</div> \
            <div class='pitching-stat-header'>k</div> \
            <div class='pitching-stat-header'>hits</div> \
            <div class='pitching-stat-header'>hr</div> \
            <div class='pitching-stat-header'>baa</div> \
        </div>"
    );
}

/*

        "<div class='pitching-stats'>" +
        "<div class='pitching-stat'>&#160;</div>" +
        "<div class='pitching-stat-header'>ip</div>" +
        "<div class='pitching-stat-header'>era</div>" +
        "<div class='pitching-stat-header'>er</div>" +
        "<div class='pitching-stat-header'>r</div>" +
        "<div class='pitching-stat-header'>k</div>" +
        "<div class='pitching-stat-header'>hits</div>" +
        "<div class='pitching-stat-header'>hr</div>" +
        "<div class='pitching-stat-header'>baa</div>" +
        "</div>"
 */

function buildPlayerBattingStats(stats) {

    var player = stats.player;
    var battingStats = stats.battingStats;

    var entry = $("<div class='batting-stats'></div>");

    entry.append("<div class='batting-stat stats-player'>" + player.name + "</div>");
    entry.append("<div class='batting-stat'>" + battingStats.hits + "-" + battingStats.atBats +"</div>");
    entry.append("<div class='batting-stat'>" + threePlaces(battingStats.battingAverage) + "</div>");
    entry.append("<div class='batting-stat'>" + battingStats.doubles + "</div>");
    entry.append("<div class='batting-stat'>" + battingStats.triples + "</div>");
    entry.append("<div class='batting-stat'>" + battingStats.homeRuns + "</div>");
    entry.append("<div class='batting-stat'>" + battingStats.runsBattedIn + "</div>");
    entry.append("<div class='batting-stat'>" + battingStats.strikeouts + "</div>");
    entry.append("<div class='batting-stat'>" + threePlaces(battingStats.onBasePlusSlugging) + "</div>");

    return entry;
}

function buildPlayerPitchingStats(stats) {

    var player = stats.player;
    var pitchingStats = stats.pitchingStats;

    var entry = $("<div class='pitching-stats'></div>");

    entry.append("<div class='pitching-stat stats-player'>" + player.name + "</div>");
    entry.append("<div class='pitching-stat'>" + formatInningsPitched(pitchingStats.inningsPitched, pitchingStats.inningsPitchedRemainder) + "</div>");
    entry.append("<div class='pitching-stat'>" + twoPlaces(pitchingStats.earnedRunAverage) + "</div>");
    entry.append("<div class='pitching-stat'>" + pitchingStats.earnedRuns + "</div>");
    entry.append("<div class='pitching-stat'>" + pitchingStats.runs + "</div>");
    entry.append("<div class='pitching-stat'>" + pitchingStats.strikeouts + "</div>");
    entry.append("<div class='pitching-stat'>" + pitchingStats.hits + "-" + pitchingStats.atBats +"</div>");
    entry.append("<div class='pitching-stat'>" + pitchingStats.homeRuns +"</div>");
    entry.append("<div class='pitching-stat'>" + threePlaces(pitchingStats.battingAverage) + "</div>");

    return entry;
}

function threePlaces(number) {
    return (String((Number(number)).toFixed(3))).replace(/^0+/, '');
}

function twoPlaces(number) {
    return (Number(number)).toFixed(2);
}

function formatInningsPitched(innings, remainder) {
    return "" + innings + (remainder == 0 ? "" : "." + remainder);
}

function buildGameSummary(summary) {

    var summaryDiv = $("<div class='summary'></div>");

    var awayScore = summary.awayScore;
    var homeScore = summary.homeScore;

    $.each(summary, function() {

        var entry = $(
            "<div class='summary-entry'>" +
            this.pitcher.name + " pitching to " + this.batter.name + ": " +
            "<span class='summary-entry-play'>" +
            mapEventText(this.gamePlayEvent) +
            "</span>" +
            (this.scoreChange ? " <span class='score-change'>(" + this.awayScore + "-" + this.homeScore + ")</span>" : "") +
            "</div>");

        entry.addClass(this.homeHalf ? "home-summary-entry" : "away-summary-entry");

        summaryDiv.append(entry);
    });

    return summaryDiv;
}

function regionClick (e, behaviorMap) {

    var $$ = $(e.target);
    while ($$.length > 0) {
        for (var key in behaviorMap) {
            if ($$.hasClass(key)) {
                behaviorMap[key]($$)
                return
            }
        }
        $$ = $$.parent();
    }
}

function actionsClick(e) {

    /*
     WALK,
     SINGLE,
     DOUBLE,
     TRIPLE,
     HOME_RUN,
     ERROR_REACH,
     ERROR_ADVANCE,
     STRIKEOUT_SWINGING,
     STRIKEOUT_LOOKING,
     STRIKEOUT_BOTH,
     FLY_OUT,
     POP_OUT,
     GROUND_OUT,
     LINE_OUT,
     DOUBLE_PLAY,
     UNDO
     */

    var $$ = $(e.target);

    var gameId = $("div.game-actions").attr('id');

    var actions = {
        'walk': function ($$) {
            walk(gameId);
        },
        'single': function ($$) {
            single(gameId);
        },
        'double': function ($$) {
            double(gameId);
        },
        'triple': function ($$) {
            triple(gameId);
        },
        'homerun': function ($$) {
            homerun(gameId);
        },
        'error-reach': function ($$) {
            errorReach(gameId);
        },
        'error-advance': function ($$) {
            errorAdvance(gameId);
        },
        'strikeout-swinging': function ($$) {
            strikeoutSwinging(gameId);
        },
        'strikeout-looking': function ($$) {
            strikeoutLooking(gameId);
        },
        'strikeout-both': function ($$) {
            strikeoutBoth(gameId);
        },
        'groundout': function ($$) {
            groundOut(gameId);
        },
        'lineout': function ($$) {
            lineOut(gameId);
        },
        'flyout': function ($$) {
            flyOut(gameId);
        },
        'doubleplay': function ($$) {
            doublePlay(gameId);
        },
        'skip-batter': function ($$) {
            skipBatter(gameId);
        },
        'undo': function ($$) {
            undo(gameId);
        },
        'pitcher' : function ($$) {
            changePitcher($$);
        },
        'set-pitcher' : function ($$) {
            setPitcher(gameId, $$);
        },
        'get-summary' : function ($$) {
            getSummary(gameId, $$);
        },
        'get-stats' : function ($$) {
            getStats(gameId, $$);
        }
    };

    regionClick(e, actions);
}

function actionsBehavior(root) {

    $(root).on('click touchstart', actionsClick);

    return root
}

function walk(gameId) {
    updateGame(gameId, "WALK");
}

function single(gameId) {
    updateGame(gameId, "SINGLE");
}

function double(gameId) {
    updateGame(gameId, "DOUBLE");
}

function triple(gameId) {
    updateGame(gameId, "TRIPLE");
}

function homerun(gameId) {
    updateGame(gameId, "HOME_RUN");
}

function errorReach(gameId) {
    updateGame(gameId, "ERROR_REACH");
}

function errorAdvance(gameId) {
    updateGame(gameId, "ERROR_ADVANCE");
}

function strikeoutSwinging(gameId) {
    updateGame(gameId, "STRIKEOUT_SWINGING");
}

function strikeoutLooking(gameId) {
    updateGame(gameId, "STRIKEOUT_LOOKING");
}

function strikeoutBoth(gameId) {
    updateGame(gameId, "STRIKEOUT_BOTH");
}

function flyOut(gameId) {
    updateGame(gameId, "FLY_OUT");
}

function groundOut(gameId) {
    updateGame(gameId, "GROUND_OUT");
}

function lineOut(gameId) {
    updateGame(gameId, "LINE_OUT");
}

function doublePlay(gameId) {
    updateGame(gameId, "DOUBLE_PLAY");
}

function undo(gameId) {
    updateGame(gameId, "UNDO");
}

function skipBatter(gameId) {
    updateGame(gameId, "SKIP");
}

function changePitcher(pitcher) {
    $(pitcher).closest("div.team").find("div.player").addClass("set-pitcher button");
}

function setPitcher(gameId, newPitcher) {

    var teamDiv = $(newPitcher).closest("div.team");
    teamDiv.find('div.set-pitcher').removeClass("set-pitcher");
    teamDiv.find('div.batter').removeClass("button");

    updateGame(gameId, "SET_PITCHER", newPitcher.attr("id"));
}

/*
 WALK,
 SINGLE,
 DOUBLE,
 TRIPLE,
 HOME_RUN,
 ERROR_REACH,
 ERROR_ADVANCE,
 STRIKEOUT_SWINGING,
 STRIKEOUT_LOOKING,
 STRIKEOUT_BOTH,
 FLY_OUT,
 POP_OUT,
 GROUND_OUT,
 LINE_OUT,
 DOUBLE_PLAY,
 SKIP,
 UNDO
 */

function updateGame(gameId, play, player1) {

    var url = "/w/game/" + gameId + "/play/" + play;

    if (player1 != null) url += "?player1=" + player1

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
       $('body').html(buildConsole(data));
    });
}

function getStats(gameId, $$) {

    $$.removeClass('get-stats').addClass('get-summary');

    var url = "/w/game/" + gameId + "/stats";

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
        $$.closest('div.console').find('div.summary-pane').html(buildGameStats(data));
    });
}

function getSummary(gameId, $$) {

    $$.removeClass('get-summary').addClass('get-stats');

    var url = "/w/game/" + gameId;

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
        $$.closest('div.console').find('div.summary-pane').html(buildGameSummary(data.gameSummary));
    });
}

function showSlowly (selector, bodyFunction, args) {
    var closeSpeed = 50;
    var openSpeed = 100;
    var $$ = $(selector);
    $$.animate({'opacity': 0.0}, closeSpeed, function () {
        bodyFunction.apply(null, args);
        $$.animate({'opacity': 1.0}, openSpeed);
    })
}

function mapEventText(text) {

    var m = {};

    m.WALK = "walk";
    m.SINGLE = "single";
    m.DOUBLE = "double";
    m.TRIPLE = "triple";
    m.HOME_RUN = "home run";
    m.ERROR_REACH = "reached on error";
    m.ERROR_ADVANCE = "advanced on error";
    m.STRIKEOUT_SWINGING = "K";
    m.STRIKEOUT_LOOKING = "K (backwards)";
    m.STRIKEOUT_BOTH = "X !";
    m.FLY_OUT = "fly out";
    m.POP_OUT = "pop out";
    m.GROUND_OUT = "ground out";
    m.LINE_OUT = "line out";
    m.DOUBLE_PLAY = "double play !";
    m.SET_PITCHER = "pitcher relieved...";
    m.SKIP = "skipped";

    return text in m ? m[text] : text;
}

function getUrlParameter(sParam) {

    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};