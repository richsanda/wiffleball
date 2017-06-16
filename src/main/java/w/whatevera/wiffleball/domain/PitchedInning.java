package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class PitchedInning {

    @Id
   	@GeneratedValue
   	private Long id;

    @ManyToOne
    private GameStatus initialGameStatus;
    @ManyToOne
    private GameStatus currentGameStatus;
    private boolean isPitcherDone = false;
    private boolean isClosed = false;
    private int earnedRuns = 0;

    public PitchedInning(IGameStatus gameStatus) {

        GamePlay gamePlay = new GamePlay(gameStatus);
        gamePlay.clearPitchersOfResponsibility();

        initialGameStatus = new GameStatus(gamePlay);
        currentGameStatus = new GameStatus(gamePlay);
    }

    public IGameStatus getGameStatus() {
        return currentGameStatus;
    }

    public Player getPitcher() {
        return initialGameStatus.getPitcher();
    }

    public void apply(GamePlayEvent event, Player player1, Player player2) {

        if (isClosed) return;

        GamePlayEvent errorFree = event;

        switch (event) {
            case ERROR_REACH:
                errorFree = GamePlayEvent.OUT;
                break;
            case ERROR_ADVANCE:
                errorFree = null;
                break;
            case SET_PITCHER:
                if (isPitcherDone || getPitcher() != player1) {
                    player1 = null; // pass null from now on once the pitcher is done
                    isPitcherDone = true;
                }
        }

        if (null != errorFree) currentGameStatus = GameUtils.applyPlayToGame(currentGameStatus, errorFree, player1, player2);

        for (BaseRunner baseRunner : currentGameStatus.getPlatedRuns()) {
            Player pitcher = baseRunner.getPitcher();
            if (null != pitcher && pitcher.equals(getPitcher())) {
                earnedRuns++;
            }
        }

        // is this the best place to do this ?
        currentGameStatus.getPlatedRuns().clear();

        if (initialGameStatus.isHomeHalf() != currentGameStatus.isHomeHalf()) isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public int getRuns() {
        return earnedRuns;
    }
}
