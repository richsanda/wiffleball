package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;
import w.whatevera.wiffleball.game.GameLogEntry;
import w.whatevera.wiffleball.game.GamePlayEvent;
import w.whatevera.wiffleball.game.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Deque;
import java.util.List;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class Game implements w.whatevera.wiffleball.game.Game {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public GameStatus getGameStatus() {
        return null;
    }

    @Override
    public Deque<GameLogEntry> getGameLog() {
        return null;
    }

    @Override
    public List<GameLogSummaryEntry> getGameSummary() {
        return null;
    }

    @Override
    public GameStatus apply(GamePlayEvent event) {
        return null;
    }

    @Override
    public GameStatus apply(GamePlayEvent event, Player fielder) {
        return null;
    }

    @Override
    public GameStatus apply(GamePlayEvent event, Player player1, Player player2) {
        return null;
    }

    @Override
    public void undo() {

    }
}
