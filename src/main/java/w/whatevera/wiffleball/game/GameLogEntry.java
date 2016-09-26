package w.whatevera.wiffleball.game;

import java.util.List;

/**
 * Created by rich on 9/23/16.
 */
public interface GameLogEntry {

    GameStatus getGameStatus();

    GameStatus getNextGameStatus();

    GamePlayEvent getGamePlayEvent();

    Player getPlayer1();

    Player getPlayer2();
}
