package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/23/16.
 */
public interface Game {

    String getId();

    GameStatus getGameStatus();

    GameStatus apply(GamePlayEvent event);

    GameStatus apply(GamePlayEvent event, Player fielder);

    GameStatus apply(GamePlayEvent event, Player player1, Player player2);

    void undo();
}
