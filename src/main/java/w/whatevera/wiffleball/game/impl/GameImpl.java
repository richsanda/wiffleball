package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by rich on 9/23/16.
 */
public class GameImpl implements Game {

    private String id = UUID.randomUUID().toString();
    private GameStatusImpl gameStatus;

    public GameImpl(GameSettings gameSettings, List<Player> awayTeam, List<Player> homeTeam) {
         gameStatus = new GameStatusImpl(gameSettings, awayTeam, homeTeam);
    }

    public String getId() {
        return id;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
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

        GameUtils.applyPlayToGame(gameStatus, event, player1, player2);
        // add to a stack of event / player1 / player2 / (finalized) gameStatus
        return gameStatus;
    }

    @Override
    public void undo() {

    }
}
