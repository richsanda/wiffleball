$(pageBehavior)

function pageBehavior () {

    actionsBehavior($(this));

    var url = "/w/games";

    $.ajax({
        url: url,
        dataType: "json"
    }).success(function (data) {
        console.log(data);
        $('#games').append(showGames(data));
    });
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

    var actions = {
        'start-game' : function ($$) {
            startGame($$);
        }
    };

    regionClick(e, actions);
}

function actionsBehavior(root) {

    $(root).unbind('click');
    $(root).click(actionsClick);

    return root
}

function showGames(games) {

    var gamesDiv = $("<div class='games'></div>");

    $.each(games, function() {
        var gameDiv = $("<div class='game'>" + this + "</div>");
        gamesDiv.append(gameDiv);
    });

    return gamesDiv;
}

function startGame() {

    var newGameDiv = $('#new-game');

    var url = "game.html?awayteam=" +
        fixPlayerNames(newGameDiv.find('input[name=away]').val()) +
        "&hometeam=" +
        fixPlayerNames(newGameDiv.find('input[name=home]').val());

    window.location.href = url;
}

function fixPlayerNames(names) {

    var namesArray = names.split(/[\s\,]+/g) || [];
    var result = "";

    for (var i = 0; i < namesArray.length; i++) {
        if (i != 0) result += ",";
        result += namesArray[i];
    }

    return result;
}