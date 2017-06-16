package w.whatevera.wiffleball.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import w.whatevera.wiffleball.domain.*;
import w.whatevera.wiffleball.domain.repository.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by rich on 9/21/16.
 */
@Component
public class GameUtils {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final GamePlayRepository gamePlayRepository;
    private final GameSettingsRepository gameSettingsRepository;
    private final GameStatusRepository gameStatusRepository;
    private final BaseRunnerRepository baseRunnerRepository;

    @Autowired
    public GameUtils(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayRepository gamePlayRepository, GameSettingsRepository gameSettingsRepository, GameStatusRepository gameStatusRepository, BaseRunnerRepository baseRunnerRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.gamePlayRepository = gamePlayRepository;
        this.gameSettingsRepository = gameSettingsRepository;
        this.gameStatusRepository = gameStatusRepository;
        this.baseRunnerRepository = baseRunnerRepository;
    }

    public static GameStatus applyPlayToGame(IGameStatus gameStatus, GamePlayEvent event, Player player1, Player player2) {
        return applyPlayToGame(gameStatus, event, determinePlayerMap(event, player1, player2));
    }

    public static GameStatus applyPlayToGame(IGameStatus gameStatus, GamePlayEvent event, Map<PlayerType, Player> players) {

        // is this the best way to do this ?
        gameStatus.getPlatedRuns().clear();

        GamePlay gamePlay = new GamePlay(gameStatus);

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
                case SKIP:
                    gamePlay.skipBatter();
                default:
                    break;
            }
        } catch (GameOverException e) {
            // pass
        }

        return new GameStatus(gamePlay);
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

    public GameStats calculateStats(Game game) {

        TeamStats awayTeamStats = new TeamStats(game.getGameStatus().getAwayTeam());
        TeamStats homeTeamStats = new TeamStats(game.getGameStatus().getHomeTeam());
        GameStats result = new GameStats(awayTeamStats, homeTeamStats);

        // iterate over the whole game for batting stats and most pitching stats
        Iterator<GameLogEntry> gameLog = game.getGameLog().iterator();
        while (gameLog.hasNext()) {
            result = result.add(calculateStats(gameLog.next()));
        }

        List<PitchedInning> openPitchedInnings = Lists.newArrayList();
        List<PitchedInning> closedPitchedInnings = Lists.newArrayList();

        Player lastPitcher = null;
        gameLog = game.getGameLog().iterator(); // reset gameLog for earned run calc

        // iterate over the whole game again for earned run calculations
        while (gameLog.hasNext()) {

            GameLogEntry gameLogEntry = gameLog.next();
            GamePlayEvent event = gameLogEntry.getGamePlayEvent();
            Player player1 = gameLogEntry.getPlayer1();
            Player player2 = gameLogEntry.getPlayer2();
            IGameStatus gameStatus = gameLogEntry.getGameStatus();
            Player pitcher = gameStatus.getPitcher();

            if (lastPitcher != pitcher) {
                PitchedInning pitchedInning = new PitchedInning(gameStatus);
                openPitchedInnings.add(pitchedInning);
            }

            Set<PitchedInning> pitchedInningsToClose = Sets.newHashSet();
            for (PitchedInning openPitchedInning : openPitchedInnings) {
                openPitchedInning.apply(event, player1, player2);
                if (openPitchedInning.isClosed()) {
                    pitchedInningsToClose.add(openPitchedInning);
                }
            }

            openPitchedInnings.removeAll(pitchedInningsToClose);
            closedPitchedInnings.addAll(pitchedInningsToClose);

            lastPitcher = pitcher;
        }

        Set<PitchedInning> allPitchedInnings = Sets.newHashSet();
        allPitchedInnings.addAll(openPitchedInnings);
        allPitchedInnings.addAll(closedPitchedInnings);

        Map<Player, Integer> pitchersToEarnedRuns = Maps.newHashMap();

        for (PitchedInning pitchedInning : allPitchedInnings) {
            Player pitcher = pitchedInning.getPitcher();
            int runs = pitchedInning.getRuns();
            Integer previous = pitchersToEarnedRuns.get(pitcher);
            pitchersToEarnedRuns.put(pitcher, null == previous ? runs : previous + runs);
        }

        Set<PlayerPitchingStats> allPlayerPitchingStats = Sets.newHashSet();
        allPlayerPitchingStats.addAll(awayTeamStats.getPlayerPitchingStats());
        allPlayerPitchingStats.addAll(homeTeamStats.getPlayerPitchingStats());

        for (PlayerPitchingStats playerPitchingStats : allPlayerPitchingStats) {
            Player pitcher = playerPitchingStats.getPlayer();
            Integer earnedRuns = pitchersToEarnedRuns.get(pitcher);
            if (null != earnedRuns) playerPitchingStats.getPitchingStats().addEarnedRuns(earnedRuns);
        }

        return result;
    }

    public GameStats calculateStats(GameLogEntry gameLogEntry) {

        TeamStats awayTeamStats = new TeamStats(gameLogEntry.getGameStatus().getAwayTeam());
        TeamStats homeTeamStats = new TeamStats(gameLogEntry.getGameStatus().getHomeTeam());
        GameStats result = new GameStats(awayTeamStats, homeTeamStats);

        IGameStatus gameStatus = gameLogEntry.getGameStatus();
        GamePlayEvent event = gameLogEntry.getGamePlayEvent();
        Player player1 = gameLogEntry.getPlayer1();
        Player player2 = gameLogEntry.getPlayer2();
        
        boolean isHomeHalf = gameStatus.isHomeHalf();
        
        Player batter = gameStatus.getBatter();
        Player pitcher = gameStatus.getPitcher();

        IGameStatus nextGameStatus = applyPlayToGame(gameStatus, event, player1, player2);
        
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

        for (BaseRunner baseRunner : nextGameStatus.getPlatedRuns()) {
            Player platedPitcher = baseRunner.getPitcher();
            Player platedBatter = baseRunner.getRunner();
            pitchingTeamStats.getPitchingStats(platedPitcher).addRuns(1);
            battingTeamStats.getBattingStats(platedBatter).addRuns(1);
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
