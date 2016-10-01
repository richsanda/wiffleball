package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/23/16.
 */
public interface PitchedInning {

    GameStatus getGameStatus();

    Player getPitcher();

    void apply(GamePlayEvent event, Player player1, Player player2);

    boolean isClosed();

    int getRuns();
}
