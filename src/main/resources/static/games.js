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
        var gameDiv = $("<div class='game'>" + this + "</div>");
        $("#games").append(gameDiv);
    });
}