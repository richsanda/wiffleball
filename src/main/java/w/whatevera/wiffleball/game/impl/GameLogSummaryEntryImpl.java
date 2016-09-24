package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

/**
 * Created by rich on 9/24/16.
 */
public class GameLogSummaryEntryImpl implements GameLogSummaryEntry {

    private GamePlayEvent gamePlayEvent;
    private Player player1;
    private Player player2;

    private Player pitcher;
    private Player batter;

    private int awayScore;
    private int homeScore;
    private boolean isHomeHalf;

    public GameLogSummaryEntryImpl(GameLogEntry gameLogEntry) {

        this.gamePlayEvent = gameLogEntry.getGamePlayEvent();
        this.player1 = gameLogEntry.getPlayer1();
        this.player2 = gameLogEntry.getPlayer2();

        GameStatus gameStatus = gameLogEntry.getGameStatus();

        this.pitcher = gameStatus.getPitcher();
        this.batter = gameStatus.getBatter();
        this.awayScore = gameStatus.getAwayScore();
        this.homeScore = gameStatus.getHomeScore();
        this.isHomeHalf = gameStatus.isHomeHalf();
    }

    @Override
    public GamePlayEvent getGamePlayEvent() {
        return gamePlayEvent;
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }

    @Override
    public Player getPitcher() {
        return pitcher;
    }

    @Override
    public Player getBatter() {
        return batter;
    }

    @Override
    public int getAwayScore() {
        return awayScore;
    }

    @Override
    public int getHomeScore() {
        return homeScore;
    }

    @Override
    public boolean isHomeHalf() {
        return isHomeHalf;
    }
}
