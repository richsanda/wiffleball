$(pageBehavior);

function pageBehavior () {

    actionsBehavior($(this));

    var url = "/w/game/";

    var gameid = getUrlParameter("gameid");
    var awayteam = getUrlParameter("awayteam");
    var hometeam = getUrlParameter("hometeam");

    var method = (gameid) ? "get" : "post";

    if (gameid) {
        url += gameid
    } else if (awayteam && hometeam) {
        url += awayteam + "/" + hometeam;
    }

    $.ajax({
        url: url,
        type: method,
        dataType: "json"
    }).success(function (data) {

        $('body').html(buildConsole(data));

        window.history.pushState("", "", "/game.html?gameid=" + data.key);
    });
}