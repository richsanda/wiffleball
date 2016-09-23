package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.UUID;

/**
 * Created by rich on 9/23/16.
 */
public class GameImpl implements Game {

    private String id = UUID.randomUUID().toString();
    private Deque<GameLogEntry> gameLog = new ArrayDeque<GameLogEntry>();
    private GameStatus currentGameStatus;

    public GameImpl(GameSettings gameSettings, List<Player> awayTeam, List<Player> homeTeam) {
        currentGameStatus = new GameStatusImpl(new GamePlayImpl(gameSettings, awayTeam, homeTeam));
    }

    public String getId() {
        return id;
    }

    @Override
    public GameStatus getGameStatus() {
        return currentGameStatus;
    }

    @Override
    public GameStatus apply(GamePlayEvent event) {
        return apply(event, null);
    }

    @Override
    public GameStatus apply(GamePlayEvent event, Player player) {
        return apply(event, player, null);
    }

    @Override
    public GameStatus apply(GamePlayEvent event, Player player1, Player player2) {

        if (GamePlayEvent.UNDO.equals(event)) {

            undo();

        } else {

            GameLogEntry entry = new GameLogEntry(currentGameStatus, event, player1, player2);
            gameLog.add(entry);
            currentGameStatus = GameUtils.applyPlayToGame(this, event, player1, player2);
        }

        return currentGameStatus;
    }

    @Override
    public void undo() {
        if (!gameLog.isEmpty()) {
            currentGameStatus = gameLog.removeLast().gameStatus;
        }
    }

    private class GameLogEntry {

        private GameStatus gameStatus;
        private GamePlayEvent event;
        private Player player1;
        private Player player2;

        private GameLogEntry(GameStatus gameStatus, GamePlayEvent event, Player player1, Player player2) {
            this.gameStatus = gameStatus;
            this.event = event;
            this.player1 = player1;
            this.player2 = player2;
        }
    }
}
