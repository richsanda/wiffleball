package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.GamePlayEvent;
import w.whatevera.wiffleball.game.GameStatus;
import w.whatevera.wiffleball.game.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class GameLogEntry implements w.whatevera.wiffleball.game.GameLogEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public GameStatus getGameStatus() {
        return null;
    }

    @Override
    public GameStatus getNextGameStatus() {
        return null;
    }

    @Override
    public GamePlayEvent getGamePlayEvent() {
        return null;
    }

    @Override
    public Player getPlayer1() {
        return null;
    }

    @Override
    public Player getPlayer2() {
        return null;
    }
}
