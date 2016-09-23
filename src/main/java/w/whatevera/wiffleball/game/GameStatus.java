package w.whatevera.wiffleball.game;

import java.util.List;

/**
 * Created by rich on 9/4/16.
 */
public interface GameStatus {

    // game info

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

    Player getOnFirst();

    Player getOnSecond();

    Player getOnThird();

    List<Player> getPlatedRuns();

    boolean isHomeHalf();

    boolean isOver();

}
