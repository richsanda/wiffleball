package w.whatevera.wiffleball.game;

import java.util.List;

/**
 * Created by rich on 9/4/16.
 */
public interface GameStatus {

    // game info

    GameSettings getGameSettings();

    List<Player> getAwayTeam();

    List<Player> getHomeTeam();

    Player getBatter();

    Player getPitcher();

    Player getAwayBatter();

    Player getHomeBatter();

    int getAwayBatterIndex();

    int getHomeBatterIndex();

    Player getAwayPitcher();

    Player getHomePitcher();

    int getAwayScore();

    int getHomeScore();

    int getOuts();

    int getInning();

    int getNumberOfInnings();

    BaseRunner getOnFirst();

    BaseRunner getOnSecond();

    BaseRunner getOnThird();

    List<BaseRunner> getPlatedRuns();

    boolean isHomeHalf();

    boolean isOver();
}
