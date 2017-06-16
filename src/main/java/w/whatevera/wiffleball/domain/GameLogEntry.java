package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;

import javax.persistence.*;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class GameLogEntry {

    @Id
   	@GeneratedValue
   	private Long id;

    @OneToOne
    private GameStatus gameStatus;
    @OneToOne
    private GameStatus nextGameStatus;
    private GamePlayEvent event;
    @ManyToOne
    private Player player1;
    @ManyToOne
    private Player player2;

    public GameLogEntry() {}

    public GameLogEntry(GameStatus gameStatus, GameStatus nextGameStatus, GamePlayEvent event, Player player1, Player player2) {
        this.gameStatus = gameStatus;
        this.nextGameStatus = nextGameStatus;
        this.event = event;
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public IGameStatus getNextGameStatus() {
        return nextGameStatus;
    }

    public GamePlayEvent getGamePlayEvent() {
        return event;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
