$(pageBehavior);

function pageBehavior () {

    // actionsBehavior($(this));

    var url = "/w/games";

    $.ajax({
        url: url,
        type: "get",
        dataType: "json"
    }).success(function (data) {

        $.each(data, function(i, game) {
            var div = $("<div class='game-display'><div class='game-title'>Game " + (i + 1) + "</div></div>");
            div.append(buildConsole(game, true));
            $("body").append(div);
        });
    });

    url = "/w/players/stats";

    $.ajax({
        url: url,
        type: "get",
        dataType: "json"
    }).success(function (data) {

        var statsDiv = $("<div class='stats'></div>");

        statsDiv.append(buildBattingStatsHeader());

        $.each(data.playerBattingStats, function(k, v) {

            statsDiv.append(buildPlayerBattingStats(this));
        });

        statsDiv.append("<div><hr/></div>");

        statsDiv.append(buildPitchingStatsHeader());

        $.each(data.playerPitchingStats, function(k, v) {

            statsDiv.append(buildPlayerPitchingStats(this));
        });

        $("body").append(statsDiv);
    });
}