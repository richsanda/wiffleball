package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class BaseRunner implements w.whatevera.wiffleball.game.BaseRunner {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Player getRunner() {
        return null;
    }

    @Override
    public Player getPitcher() {
        return null;
    }
}
