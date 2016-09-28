package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/27/16.
 */
public interface PlayerBattingStats {

    Player getPlayer();

    BattingStats getBattingStats();

    PlayerBattingStats add(PlayerBattingStats playerBattingStats);
}
