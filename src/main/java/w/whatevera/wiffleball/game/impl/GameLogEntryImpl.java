package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

/**
 * Created by rich on 9/23/16.
 */
class GameLogEntryImpl implements GameLogEntry {

    private GameStatus gameStatus;
    private GameStatus nextGameStatus;
    private GamePlayEvent event;
    private Player player1;
    private Player player2;

    GameLogEntryImpl(GameStatus gameStatus, GameStatus nextGameStatus, GamePlayEvent event, Player player1, Player player2) {
        this.gameStatus = gameStatus;
        this.nextGameStatus = nextGameStatus;
        this.event = event;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public GameStatus getNextGameStatus() {
        return nextGameStatus;
    }

    @Override
    public GamePlayEvent getGamePlayEvent() {
        return event;
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }
}
