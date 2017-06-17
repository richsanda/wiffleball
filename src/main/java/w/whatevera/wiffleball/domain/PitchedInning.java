package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by rich on 9/23/16.
 */
public class PitchedInning {

    private GameStatus initialGameStatus;
    private GameStatus currentGameStatus;
    private boolean isPitcherDone = false;
    private boolean isClosed = false;
    private int earnedRuns = 0;

    public PitchedInning(IGameStatus gameStatus) {

        GamePlay gamePlay = new GamePlay(gameStatus, null);
        gamePlay.clearPitchersOfResponsibility();

        initialGameStatus = new GameStatus(gamePlay);
        currentGameStatus = new GameStatus(gamePlay);
    }

    public IGameStatus getInitialGameStatus() {
        return initialGameStatus;
    }

    public IGameStatus getGameStatus() {
        return currentGameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        currentGameStatus = gameStatus;
    }

    public Player getPitcher() {
        return initialGameStatus.getPitcher();
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public int getRuns() {
        return earnedRuns;
    }

    public boolean isPitcherDone() {
        return isPitcherDone;
    }

    public void setPitcherDone(boolean pitcherDone) {
        isPitcherDone = pitcherDone;
    }

    public void addEarnedRun() {
        earnedRuns++;
    }
}
