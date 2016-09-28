package w.whatevera.wiffleball.game;

import com.google.common.collect.Maps;
import w.whatevera.wiffleball.game.impl.GamePlayImpl;
import w.whatevera.wiffleball.game.impl.GameStatsImpl;
import w.whatevera.wiffleball.game.impl.GameStatusImpl;
import w.whatevera.wiffleball.game.impl.TeamStatsImpl;

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

    public static GameStats calculateStats(Game game) {

        Iterator<GameLogEntry> gameLog = game.getGameLog().iterator();

        TeamStats awayTeamStats = new TeamStatsImpl(game.getGameStatus().getAwayTeam());
        TeamStats homeTeamStats = new TeamStatsImpl(game.getGameStatus().getHomeTeam());
        GameStats result = new GameStatsImpl(awayTeamStats, homeTeamStats);

        while (gameLog.hasNext()) {
            result = result.add(calculateStats(gameLog.next()));
        }

        return result;
    }

    public static GameStats calculateStats(GameLogEntry gameLogEntry) {

        TeamStats awayTeamStats = new TeamStatsImpl(gameLogEntry.getGameStatus().getAwayTeam());
        TeamStats homeTeamStats = new TeamStatsImpl(gameLogEntry.getGameStatus().getHomeTeam());
        GameStats result = new GameStatsImpl(awayTeamStats, homeTeamStats);

        GameStatus gameStatus = gameLogEntry.getGameStatus();
        GamePlayEvent event = gameLogEntry.getGamePlayEvent();
        Player player1 = gameLogEntry.getPlayer1();
        Player player2 = gameLogEntry.getPlayer2();
        
        boolean isHomeHalf = gameStatus.isHomeHalf();
        
        Player batter = gameStatus.getBatter();
        Player pitcher = gameStatus.getPitcher();

        GameStatus nextGameStatus = applyPlayToGame(gameStatus, event, player1, player2);
        
        int runs = isHomeHalf ? 
                nextGameStatus.getHomeScore() - gameStatus.getHomeScore() :
                nextGameStatus.getAwayScore() - gameStatus.getAwayScore();

        TeamStats pitchingTeamStats = isHomeHalf ? awayTeamStats : homeTeamStats;
        TeamStats battingTeamStats = isHomeHalf ? homeTeamStats : awayTeamStats;
        
        PitchingStats pitchingStats = pitchingTeamStats.getPitchingStats(pitcher);
        BattingStats battingStats = battingTeamStats.getBattingStats(batter);
        
        switch (event) {
            case START_GAME:
                break;
            case SET_PITCHER:
                break;
            case WALK:
                battingStats.addWalk().addRunsBattedIn(runs);
                pitchingStats.addWalk();
                break;
            case SINGLE:
                battingStats.addHit().addRunsBattedIn(runs);
                pitchingStats.addHit();
                break;
            case DOUBLE:
                battingStats.addDouble().addRunsBattedIn(runs);
                pitchingStats.addDouble();
                break;
            case TRIPLE:
                battingStats.addTriple().addRunsBattedIn(runs);
                pitchingStats.addTriple();
                break;
            case HOME_RUN:
                battingStats.addHomeRun().addRunsBattedIn(runs);
                pitchingStats.addHomeRun();
                break;
            case ERROR_REACH:
                battingStats.addAtBat();
                pitchingStats.addAtBat();
                break;
            case ERROR_ADVANCE:
                break;
            case STRIKEOUT_SWINGING:
                battingStats.addStrikeout();
                pitchingStats.addStrikeout().addOneThirdInning();
                break;
            case STRIKEOUT_LOOKING:
                battingStats.addStrikeout();
                pitchingStats.addStrikeout().addOneThirdInning();
                break;
            case STRIKEOUT_BOTH:
                battingStats.addStrikeout();
                pitchingStats.addStrikeout().addOneThirdInning();
                break;
            case FLY_OUT:
                battingStats.addAtBat();
                pitchingStats.addAtBat().addOneThirdInning();
                break;
            case POP_OUT:
                battingStats.addAtBat();
                pitchingStats.addAtBat().addOneThirdInning();
                break;
            case GROUND_OUT:
                battingStats.addAtBat();
                pitchingStats.addAtBat().addOneThirdInning();
                break;
            case LINE_OUT:
                battingStats.addAtBat();
                pitchingStats.addAtBat().addOneThirdInning();
                break;
            case DOUBLE_PLAY:
                battingStats.addAtBat();
                pitchingStats.addAtBat().addOneThirdInning().addOneThirdInning();
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
