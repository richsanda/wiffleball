package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by rich on 9/24/16.
 */
@Entity
public class GameLogSummaryEntry {

    @Id
   	@GeneratedValue
   	private Long id;

    private GamePlayEvent gamePlayEvent;

    @ManyToOne
    private Player player1;
    @ManyToOne
    private Player player2;

    @ManyToOne
    private Player pitcher;
    @ManyToOne
    private Player batter;

    private int awayScore;
    private int homeScore;
    private boolean isHomeHalf;
    private boolean isScoreChange;

    public GameLogSummaryEntry(GameLogEntry gameLogEntry) {

        this.gamePlayEvent = gameLogEntry.getGamePlayEvent();
        this.player1 = gameLogEntry.getPlayer1();
        this.player2 = gameLogEntry.getPlayer2();

        IGameStatus gameStatus = gameLogEntry.getGameStatus();
        IGameStatus nextGameStatus = gameLogEntry.getNextGameStatus();

        this.pitcher = gameStatus.getPitcher();
        this.batter = gameStatus.getBatter();
        this.awayScore = nextGameStatus.getAwayScore();
        this.homeScore = nextGameStatus.getHomeScore();
        this.isHomeHalf = gameStatus.isHomeHalf();
        this.isScoreChange =
                gameStatus.getAwayScore() != nextGameStatus.getAwayScore() ||
                gameStatus.getHomeScore() != nextGameStatus.getHomeScore();
    }

    
    public GamePlayEvent getGamePlayEvent() {
        return gamePlayEvent;
    }

    
    public Player getPlayer1() {
        return player1;
    }

    
    public Player getPlayer2() {
        return player2;
    }

    
    public Player getPitcher() {
        return pitcher;
    }

    
    public Player getBatter() {
        return batter;
    }

    
    public int getAwayScore() {
        return awayScore;
    }

    
    public int getHomeScore() {
        return homeScore;
    }

    
    public boolean isHomeHalf() {
        return isHomeHalf;
    }

    
    public boolean isScoreChange() {
        return isScoreChange;
    }
}
