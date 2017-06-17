package w.whatevera.wiffleball.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import w.whatevera.wiffleball.domain.BaseRunner;
import w.whatevera.wiffleball.domain.GameSettings;
import w.whatevera.wiffleball.domain.Player;
import w.whatevera.wiffleball.domain.Team;

import java.util.List;

/**
 * Created by rich on 9/4/16.
 */
public interface IGameStatus {

    // game info

    GameSettings getGameSettings();

    Team getAwayTeam();

    Team getHomeTeam();

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
