package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;
import w.whatevera.wiffleball.game.PitchingStats;
import w.whatevera.wiffleball.game.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class PlayerPitchingStats implements w.whatevera.wiffleball.game.PlayerPitchingStats {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public PitchingStats getPitchingStats() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PlayerPitchingStats add(w.whatevera.wiffleball.game.PlayerPitchingStats pitchingStats) {
        return null;
    }
}
