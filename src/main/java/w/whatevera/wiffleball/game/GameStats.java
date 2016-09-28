package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/23/16.
 */
public interface GameStats {

    TeamStats getAwayTeamStats();

    TeamStats getHomeTeamStats();

    GameStats add(GameStats gameStats);
}
