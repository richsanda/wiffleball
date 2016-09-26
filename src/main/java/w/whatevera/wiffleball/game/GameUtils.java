package w.whatevera.wiffleball.game;

import com.google.common.collect.Maps;
import w.whatevera.wiffleball.game.impl.GamePlayImpl;
import w.whatevera.wiffleball.game.impl.GameStatsImpl;
import w.whatevera.wiffleball.game.impl.GameStatusImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by rich on 9/21/16.
 */
public class GameUtils {

    public static GameStatus applyPlayToGame(GameStatus gameStatus, GamePlayEvent event, Player player1, Player player2) {
        return applyPlayToGame(gameStatus, event, determinePlayerMap(event, player1, player2));
    }

    public static GameStatus applyPlayToGame(GameStatus gameStatus, GamePlayEvent event, Map<PlayerType, Player> players) {

        GamePlay gamePlay = new GamePlayImpl(gameStatus);

        Player pitcher = null;
        Player fielder = null;
        Player sub = null;

        if (null != players) {
            pitcher = players.get(PlayerType.PITCHER);
            fielder = players.get(PlayerType.FIELDER);
            sub = players.get(PlayerType.SUB);
        }

        try {
            switch (event) {
                case START_GAME:
                    break;
                case SET_PITCHER:
                    gamePlay.setPitcher(pitcher);
                    break;
                case WALK:
                    gamePlay.walk();
                    break;
                case SINGLE:
                    gamePlay.hitSingle();
                    break;
                case DOUBLE:
                    gamePlay.hitDouble();
                    break;
                case TRIPLE:
                    gamePlay.hitTriple();
                    break;
                case HOME_RUN:
                    gamePlay.hitHomeRun();
                    break;
                case ERROR_REACH:
                    gamePlay.errorReach(fielder);
                    break;
                case ERROR_ADVANCE:
                    gamePlay.errorAdvance(fielder);
                    break;
                case STRIKEOUT_SWINGING:
                    gamePlay.strikeoutSwinging();
                    break;
                case STRIKEOUT_LOOKING:
                    gamePlay.strikeoutLooking();
                    break;
                case STRIKEOUT_BOTH:
                    gamePlay.strikeoutSwingingAndLooking();
                    break;
                case FLY_OUT:
                    gamePlay.flyOut(fielder);
                    break;
                case POP_OUT:
                    gamePlay.flyOut(fielder);
                    break;
                case GROUND_OUT:
                    gamePlay.groundOut(fielder);
                    break;
                case LINE_OUT:
                    gamePlay.lineOut(fielder);
                    break;
                case DOUBLE_PLAY:
                    gamePlay.doublePlay(fielder);
                    break;
                case OUT:
                    gamePlay.out();
                case REPLACE_PLAYER:
                    gamePlay.replacePlayer(fielder, sub);
                    break;
                //case SKIP:
                //    gamePlay.skipBatter();
                default:
                    break;
            }
        } catch (GameOverException e) {
            // pass
        }

        return new GameStatusImpl((GameStatus)gamePlay);
    }

    private static Map<PlayerType, Player> determinePlayerMap(GamePlayEvent event, Player player1, Player player2) {

        Map<PlayerType, Player> result = Maps.newHashMap();

        switch (event) {
            case START_GAME:
                break;
            case SET_PITCHER:
                result.put(PlayerType.PITCHER, player1);
                break;
            case WALK:
                break;
            case SINGLE:
                break;
            case DOUBLE:
                break;
            case TRIPLE:
                break;
            case HOME_RUN:
                break;
            case ERROR_REACH:
                result.put(PlayerType.FIELDER, player1);
                break;
            case ERROR_ADVANCE:
                result.put(PlayerType.FIELDER, player1);
                break;
            case STRIKEOUT_SWINGING:
                break;
            case STRIKEOUT_LOOKING:
                break;
            case STRIKEOUT_BOTH:
                break;
            case FLY_OUT:
                result.put(PlayerType.FIELDER, player1);
                break;
            case POP_OUT:
                result.put(PlayerType.FIELDER, player1);
                break;
            case GROUND_OUT:
                result.put(PlayerType.FIELDER, player1);
                break;
            case LINE_OUT:
                result.put(PlayerType.FIELDER, player1);
                break;
            case DOUBLE_PLAY:
                result.put(PlayerType.FIELDER, player1);
                break;
            case REPLACE_PLAYER:
                result.put(PlayerType.FIELDER, player1);
                result.put(PlayerType.SUB, player2);
                break;
            //case SKIP:
            //    game.skipBatter();
            default:
                break;
        }

        return result;
    }

    public static GameStats calculateStats(Iterator<GameLogEntry> gameLog) {

        GameStats result = new GameStatsImpl();

        while (gameLog.hasNext()) {
            result = result.add(calculateStats(gameLog.next()));
        }

        return result;
    }

    public static GameStats calculateStats(GameLogEntry gameLogEntry) {

        GameStats result = new GameStatsImpl();

        GameStatus gameStatus = gameLogEntry.getGameStatus();
        GamePlayEvent event = gameLogEntry.getGamePlayEvent();
        Player player1 = gameLogEntry.getPlayer1();
        Player player2 = gameLogEntry.getPlayer2();

        Player batter = gameStatus.getBatter();
        Player pitcher = gameStatus.getPitcher();

        GameStatus nextGameStatus = applyPlayToGame(gameStatus, event, player1, player2);

        int runs = gameStatus.isHomeHalf() ?
                nextGameStatus.getHomeScore() - gameStatus.getHomeScore() :
                nextGameStatus.getAwayScore() - gameStatus.getAwayScore();

        switch (event) {
            case START_GAME:
                break;
            case SET_PITCHER:
                break;
            case WALK:
                result.getBattingStats(batter).addWalk().addRunsBattedIn(runs);
                result.getPitchingStats(pitcher).addWalk();
                break;
            case SINGLE:
                result.getBattingStats(batter).addHit().addRunsBattedIn(runs);
                result.getPitchingStats(pitcher).addHit();
                break;
            case DOUBLE:
                result.getBattingStats(batter).addDouble().addRunsBattedIn(runs);
                result.getPitchingStats(pitcher).addDouble();
                break;
            case TRIPLE:
                result.getBattingStats(batter).addTriple().addRunsBattedIn(runs);
                result.getPitchingStats(pitcher).addTriple();
                break;
            case HOME_RUN:
                result.getBattingStats(batter).addHomeRun().addRunsBattedIn(runs);
                result.getPitchingStats(pitcher).addHomeRun();
                break;
            case ERROR_REACH:
                result.getBattingStats(batter).addAtBat();
                result.getPitchingStats(pitcher).addAtBat();
                break;
            case ERROR_ADVANCE:
                break;
            case STRIKEOUT_SWINGING:
                result.getBattingStats(batter).addStrikeout();
                result.getPitchingStats(pitcher).addStrikeout().addOneThirdInning();
                break;
            case STRIKEOUT_LOOKING:
                result.getBattingStats(batter).addStrikeout();
                result.getPitchingStats(pitcher).addStrikeout().addOneThirdInning();
                break;
            case STRIKEOUT_BOTH:
                result.getBattingStats(batter).addStrikeout();
                result.getPitchingStats(pitcher).addStrikeout().addOneThirdInning();
                break;
            case FLY_OUT:
                result.getBattingStats(batter).addAtBat();
                result.getPitchingStats(pitcher).addAtBat().addOneThirdInning();
                break;
            case POP_OUT:
                result.getBattingStats(batter).addAtBat();
                result.getPitchingStats(pitcher).addAtBat().addOneThirdInning();
                break;
            case GROUND_OUT:
                result.getBattingStats(batter).addAtBat();
                result.getPitchingStats(pitcher).addAtBat().addOneThirdInning();
                break;
            case LINE_OUT:
                result.getBattingStats(batter).addAtBat();
                result.getPitchingStats(pitcher).addAtBat().addOneThirdInning();
                break;
            case DOUBLE_PLAY:
                result.getBattingStats(batter).addAtBat();
                result.getPitchingStats(pitcher).addAtBat().addOneThirdInning().addOneThirdInning();
                break;
            case REPLACE_PLAYER:
                break;
        }

        return result;
    }

    public static Player findPlayer(Game game, String playerName) {
        for (Player player : game.getGameStatus().getAwayTeam()) {
            if (player.getName().equals(playerName)) return player;
        }
        for (Player player : game.getGameStatus().getHomeTeam()) {
            if (player.getName().equals(playerName)) return player;
        }
        return null;
    }
}
