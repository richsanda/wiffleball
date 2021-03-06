package w.whatevera.wiffleball.controllers;

import com.google.common.collect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import w.whatevera.wiffleball.domain.*;
import w.whatevera.wiffleball.domain.GameSettings;
import w.whatevera.wiffleball.domain.Player;
import w.whatevera.wiffleball.domain.repository.*;
import w.whatevera.wiffleball.game.*;
import w.whatevera.wiffleball.game.GamePlayEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rich on 9/21/16.
 */
@RestController
public class WiffleballGameController {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final GameSettingsRepository gameSettingsRepository;
    private final GameStatusRepository gameStatusRepository;
    private final GameLogEntryRepository gameLogEntryRepository;
    private final TeamRepository teamRepository;

    private final GameUtils gameUtils;

    @Autowired
    public WiffleballGameController(PlayerRepository playerRepository, GameRepository gameRepository, GameSettingsRepository gameSettingsRepository, GameStatusRepository gameStatusRepository, BaseRunnerRepository baseRunnerRepository, GameLogEntryRepository gameLogEntryRepository, TeamRepository teamRepository, GameUtils gameUtils) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.gameSettingsRepository = gameSettingsRepository;
        this.gameStatusRepository = gameStatusRepository;
        this.gameLogEntryRepository = gameLogEntryRepository;
        this.teamRepository = teamRepository;
        this.gameUtils = gameUtils;
    }

    @RequestMapping(value = "/w/game/{awayTeam}/{homeTeam}", method= RequestMethod.POST, produces = "application/json")
    public Game newGame(@PathVariable("awayTeam")List<String> awayTeamPlayerNames,
                        @PathVariable("homeTeam")List<String> homeTeamPlayerNames) {

        List<Player> awayTeamPlayers = Lists.newArrayList();
        List<Player> homeTeamPlayers = Lists.newArrayList();

        for (String playerName : awayTeamPlayerNames) {
            Player player = findOrCreatePlayer(playerName);
            awayTeamPlayers.add(player);
        }

        for (String playerName : homeTeamPlayerNames) {
            Player player = findOrCreatePlayer(playerName);
            homeTeamPlayers .add(player);
        }

        Player awayPitcher = awayTeamPlayers.get(awayTeamPlayers.size() - 1);
        Player homePitcher = homeTeamPlayers.get(homeTeamPlayers.size() - 1);

        GameSettings gameSettings = new GameSettings(awayTeamPlayers.size(), 3, 3);
        gameSettingsRepository.save(gameSettings);
//
//        GamePlayImpl gamePlay = new GamePlayImpl(gameSettings, awayTeam, homeTeam);
////        gamePlay.setGameSettings(gameSettings);
////        gamePlay.setHomeTeam(homeTeam);
////        gamePlay.setAwayTeam(awayTeam);
////        gamePlay.setHomePitcher(homePitcher);
////        gamePlay.setAwayPitcher(awayPitcher);
////        gamePlay.setNumberOfInnings(gameSettings.numberOfInnings());
//        gamePlayRepository.save(gamePlay);
//
//        GameStatusImpl gameStatus = new GameStatusImpl(game);
//        gameStatus.setGameSettings(gameSettings);
//        gameStatus.setAwayTeam(awayTeam);
//        gameStatus.setHomeTeam(homeTeam);
//        gameStatus.setHomePitch(homePitcher);
//        gameStatus.setAwayPitch(awayPitcher);
//        gameStatus.setNumberOfInnings(gameSettings.numberOfInnings());
//        gameStatusRepository.save(gameStatus);

        Team awayTeam = new Team(awayTeamPlayers);
        Team homeTeam = new Team(homeTeamPlayers);
        teamRepository.save(awayTeam);
        teamRepository.save(homeTeam);

        Game game = new Game(gameSettings, awayTeam, homeTeam);
        saveGame(game);

        return game;
    }

    private void saveGame(Game game) {
        gameStatusRepository.save(game.getGameStatus());
        gameRepository.save(game);
    }

    private Player findOrCreatePlayer(String name) {
        Player player = playerRepository.findByName(name);
        if (null == player) {
            player = createAndSavePlayer(name);
        }
        return player;
    }

    @RequestMapping(value = "/w/game/{game}", method= RequestMethod.GET, produces = "application/json")
    public Game game(@PathVariable("game") String gameId) {

        return gameRepository.findOne(Long.valueOf(gameId));
    }

    @RequestMapping(value = "/w/games", method= RequestMethod.GET, produces = "application/json")
    public List<Game> allGames() {

        List<Game> result = gameRepository.findAll();

        return result;
    }

    @RequestMapping(value = "/w/game/{game}/play/{play}", method= RequestMethod.GET, produces = "application/json")
    public Game gameEvent(@PathVariable("game") String gameId,
                          @PathVariable("play") GamePlayEvent event,
                          @RequestParam(required = false) String player1,
                          @RequestParam(required = false) String player2) {

        Game game = gameRepository.findOne(Long.valueOf(gameId));
        Player player1Player = GameUtils.findPlayer(game, player1);
        apply(game, event, player1Player);
        saveGame(game);

        return game;
    }

    @RequestMapping(value = "/w/game/{game}/stats", method= RequestMethod.GET, produces = "application/json")
    public GameStats gameStats(@PathVariable("game") String gameId) {

        Game game = gameRepository.findOne(new Long(gameId));
        return gameUtils.calculateStats(game);
    }

    @RequestMapping(value = "/w/players/stats", method= RequestMethod.GET, produces = "application/json")
    public TeamStats playerStats() {

        Set<Player> allPlayers = Sets.newHashSet();
        Multimap<Player, PitchingStats> allPitchingStats = HashMultimap.create();
        Multimap<Player, BattingStats> allBattingStats = HashMultimap.create();

        List<Game> games = gameRepository.findAll();
        List<GameStats> gameStats = games.stream().map(gameUtils::calculateStats).collect(Collectors.toList());

        for (GameStats stats : gameStats) {
            TeamStats awayStats = stats.getAwayTeamStats();
            TeamStats homeStats = stats.getHomeTeamStats();
            for (Player player : awayStats.getPlayers()) {

                allPlayers.add(player);

                PitchingStats pitchingStats = awayStats.getPitchingStats(player);
                if (null != pitchingStats) allPitchingStats.put(player, pitchingStats);

                BattingStats battingStats = awayStats.getBattingStats(player);
                if (null != battingStats) allBattingStats.put(player, battingStats);
            }
            for (Player player : homeStats.getPlayers()) {

                allPlayers.add(player);

                PitchingStats pitchingStats = homeStats.getPitchingStats(player);
                if (null != pitchingStats) allPitchingStats.put(player, pitchingStats);

                BattingStats battingStats = homeStats.getBattingStats(player);
                if (null != battingStats) allBattingStats.put(player, battingStats);
            }
        }

        Map<Player, BattingStats> combinedBattingStats = new HashMap<>();
        Map<Player, PitchingStats> combinedPitchingStats = new HashMap<>();

        for (Player player : allBattingStats.keySet()) {
            BattingStats playerBattingStats = null;
            for (BattingStats battingStats : allBattingStats.get(player)) {
                if (null == playerBattingStats) {
                    playerBattingStats = battingStats;
                } else {
                    playerBattingStats = playerBattingStats.add(battingStats);
                }
            }
            combinedBattingStats.put(player, playerBattingStats);
        }
        for (Player player : allPitchingStats.keySet()) {
            PitchingStats playerPitchingStats = null;
            for (PitchingStats pitchingStats : allPitchingStats.get(player)) {
                if (null == playerPitchingStats) {
                    playerPitchingStats = pitchingStats;
                } else {
                    playerPitchingStats = playerPitchingStats.add(pitchingStats);
                }
            }
            combinedPitchingStats.put(player, playerPitchingStats);
        }

        return new TeamStats(Lists.newArrayList(allPlayers), combinedBattingStats, combinedPitchingStats);
    }

    @RequestMapping(value = "/w/player", method= RequestMethod.POST, produces = "application/json")
    public Player createPlayer(@RequestParam("name") String name) {
        return createAndSavePlayer(name);
    }

    private Player createAndSavePlayer(String name) {
        Player player = new Player(name);
        playerRepository.save(player);
        return player;
    }

    @RequestMapping(value = "/w/player/{name}", method= RequestMethod.GET, produces = "application/json")
    public Player readPlayer(@PathVariable("name") String name) {

        return playerRepository.findByName(name);
    }

    public IGameStatus apply(Game game, GamePlayEvent event, Player player) {
        return apply(game, event, player, null);
    }

    public IGameStatus apply(Game game, GamePlayEvent event, Player player1, Player player2) {

        if (GamePlayEvent.UNDO.equals(event)) {

            game.undo();

        } else if (!game.getGameStatus().isOver()) {

            GameStatus gameStatus = game.getGameStatus();
            GameStatus nextGameStatus = gameUtils.applyPlayToGame(gameStatus, event, player1, player2);
            gameStatusRepository.save(nextGameStatus);
            game.setGameStatus(nextGameStatus);
            GameLogEntry entry = new GameLogEntry(gameStatus, nextGameStatus, event, player1, player2);
            gameLogEntryRepository.save(entry);
            game.getGameLog().add(entry);
        }

        return game.getGameStatus();
    }
}
