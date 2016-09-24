package w.whatevera.wiffleball.game.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import w.whatevera.wiffleball.game.*;

import java.util.*;

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

    @JsonIgnore
    @Override
    public Deque<GameLogEntry> getGameLog() {
        return gameLog;
    }

    @Override
    public List<GameLogSummaryEntry> getGameSummary() {

        List<GameLogSummaryEntry> result = Lists.newArrayList();
        Iterator<GameLogEntry> gameLogIterator = gameLog.descendingIterator();
        while (gameLogIterator.hasNext()) {
            result.add(new GameLogSummaryEntryImpl(gameLogIterator.next()));
        }
        return result;
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

            GameLogEntry entry = new GameLogEntryImpl(currentGameStatus, event, player1, player2);
            gameLog.add(entry);
            currentGameStatus = GameUtils.applyPlayToGame(currentGameStatus, event, player1, player2);
        }

        return currentGameStatus;
    }

    @Override
    public void undo() {
        if (!gameLog.isEmpty()) {
            currentGameStatus = gameLog.removeLast().getGameStatus();
        }
    }
}
