package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/24/16.
 */
public interface GameLogSummaryEntry {

    GamePlayEvent getGamePlayEvent();

    Player getPlayer1();

    Player getPlayer2();

    Player getPitcher();

    Player getBatter();

    int getAwayScore();

    int getHomeScore();

    boolean isHomeHalf();
}
