package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;
import w.whatevera.wiffleball.game.BattingStats;
import w.whatevera.wiffleball.game.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class PlayerBattingStats implements w.whatevera.wiffleball.game.PlayerBattingStats {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public BattingStats getBattingStats() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PlayerBattingStats add(w.whatevera.wiffleball.game.PlayerBattingStats playerBattingStats) {
        return null;
    }
}
