package w.whatevera.wiffleball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import w.whatevera.wiffleball.game.*;

import javax.persistence.*;
import java.util.*;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class Game {

    @Id
   	@GeneratedValue
   	private Long id;

    @OneToMany
    private List<GameLogEntry> gameLog = new ArrayList<>();
    @OneToOne
    private GameStatus currentGameStatus;

    public Game() {}

    public Game(GameSettings gameSettings, List<Player> awayTeam, List<Player> homeTeam) {
        currentGameStatus = new GameStatus(new GamePlay(gameSettings, awayTeam, homeTeam));
    }

    public String getKey() {
        return id.toString();
    }

    public GameStatus getGameStatus() {
        return currentGameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.currentGameStatus = gameStatus;
    }

    @JsonIgnore
    public List<GameLogEntry> getGameLog() {
        return gameLog;
    }

    public List<GameLogSummaryEntry> getGameSummary() {

        List<GameLogSummaryEntry> result = Lists.newArrayList();
        ListIterator<GameLogEntry> gameLogIterator = gameLog.listIterator(gameLog.size());
        while (gameLogIterator.hasPrevious()) {
            result.add(new GameLogSummaryEntry(gameLogIterator.next()));
        }
        return result;
    }

    public void undo() {
        if (!gameLog.isEmpty()) {
            currentGameStatus = gameLog.remove(gameLog.size() - 1).getGameStatus();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Player player : currentGameStatus.getAwayTeam()) {
            builder.append(player.getName());
        }
        builder.append(" vs ");
        for (Player player : currentGameStatus.getHomeTeam()) {
            builder.append(player.getName());
        }
        builder.append(currentGameStatus.isHomeHalf() ? " bot " : " top ");
        builder.append(currentGameStatus.getInning());
        builder.append(", ");
        builder.append(currentGameStatus.getOuts());
        builder.append(" outs, ");
        builder.append(currentGameStatus.getAwayScore());
        builder.append("-");
        builder.append(currentGameStatus.getHomeScore());

        return builder.toString();
    }
}
