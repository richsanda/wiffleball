package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

/**
 * Created by rich on 9/23/16.
 */
public class PitchedInningImpl implements PitchedInning {

    private GameStatus initialGameStatus;
    private GameStatus currentGameStatus;
    private boolean isPitcherDone = false;
    private boolean isClosed = false;
    private int earnedRuns = 0;

    public PitchedInningImpl(GameStatus gameStatus) {

        GamePlayImpl gamePlay = new GamePlayImpl(gameStatus);
        gamePlay.clearPitchersOfResponsibility();

        initialGameStatus = new GameStatusImpl(gamePlay);
        currentGameStatus = new GameStatusImpl(gamePlay);
    }

    @Override
    public GameStatus getGameStatus() {
        return currentGameStatus;
    }

    @Override
    public Player getPitcher() {
        return initialGameStatus.getPitcher();
    }

    @Override
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

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public int getRuns() {
        return earnedRuns;
    }
}