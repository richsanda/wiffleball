$(pageBehavior)

function pageBehavior () {

    actionsBehavior($(this));

    var url = "/game";

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
        $('#actions').append(showGameActions(data.id));
        $('#status').append(buildGameStatus(data.gameStatus));
    });
}

function showGameActions(gameId) {

    var gameActionsDiv = $("<div class='game-actions' id='" + gameId + "'></div>");

    gameActionsDiv.append(
        "<div class='actions'>" +
            "<div class='action button hit-action single'>" + "1B" + "</div>" +
            "<div class='action button hit-action double'>" + "2B" + "</div>" +
            "<div class='action button hit-action triple'>" + "3B" + "</div>" +
            "<div class='action button hit-action homerun'>" + "HR" + "</div>" +
            "<div class='action button walk'>" + "BB" + "</div>" +
            "<div class='action button error error1'>" + "E1" + "</div>" +
            "<div class='action button error error2'>" + "E2" + "</div>" +
            "<div class='action button error error3'>" + "E3" + "</div>" +
        "</div>" +
        "<div class='actions'>" +
            "<div class='action button out-action groundout'>" + "GO" + "</div>" +
            "<div class='action button out-action lineout'>" + "LO" + "</div>" +
            "<div class='action button out-action flyout'>" + "FO" + "</div>" +
            "<div class='action button doubleplay'>" + "DP" + "</div>" +
            "<div class='action button strikeout strikeout-swinging'>" + "K" + "</div>" +
            "<div class='action button strikeout strikeout-looking'>" + "kl" + "</div>" +
            "<div class='action button strikeout strikeout-both'>" + "X" + "</div>" +
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

    situationDiv.append(inningsDiv);
    situationDiv.append(outsDiv);

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

    var pitcher = $("<div class='player pitcher'>" + game.awayPitcher.name + "</div>");

    if (game.homeHalf) { pitcher.addClass("current"); }

    teamDiv.append(pitcher);
    teamDiv.append(lineup);

    jQuery.each(game.awayTeam, function() {
        var player = $("<div class='player batter'>" + this.name + "</div>");
        if (!game.homeHalf && game.batter.name == this.name) {
            player.addClass("current");
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

    var pitcher = $("<div class='player pitcher'>" + game.homePitcher.name + "</div>");

    if (!game.homeHalf) { pitcher.addClass("current"); }

    teamDiv.append(pitcher);
    teamDiv.append(lineup);

    jQuery.each(game.homeTeam, function() {
        var player = $("<div class='player batter'>" + this.name + "</div>");
        if (game.homeHalf && game.batter.name == this.name) {
            player.addClass("current");
        }

        lineup.append(player);
    });

    return teamDiv;
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
        ERROR_1,
        ERROR_2,
        ERROR_3,
        STRIKEOUT_SWINGING,
        STRIKEOUT_LOOKING,
        STRIKEOUT_BOTH,
        FLY_OUT,
        POP_OUT,
        GROUND_OUT,
        LINE_OUT,
        DOUBLE_PLAY,
     */

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
        'error1': function ($$) {
            error1(gameId);
        },
        'error2': function ($$) {
            error2(gameId);
        },
        'error3': function ($$) {
            error3(gameId);
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
        }
    };

    regionClick(e, actions);
}

function actionsBehavior(root) {

    $(root).unbind('click');
    $(root).click(actionsClick);

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

function error1(gameId) {
    updateGame(gameId, "ERROR_1");
}

function error2(gameId) {
    updateGame(gameId, "ERROR_2");
}

function error3(gameId) {
    updateGame(gameId, "ERROR_3");
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

/*
    WALK,
    SINGLE,
    DOUBLE,
    TRIPLE,
    HOME_RUN,
    ERROR_1,
    ERROR_2,
    ERROR_3,
    STRIKEOUT_SWINGING,
    STRIKEOUT_LOOKING,
    STRIKEOUT_BOTH,
    FLY_OUT,
    POP_OUT,
    GROUND_OUT,
    LINE_OUT,
    DOUBLE_PLAY,
 */

function updateGame(gameId, play) {
    
    var url = "/game/" + gameId + "/play/" + play;

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
        $('#status').html(buildGameStatus(data.gameStatus));
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