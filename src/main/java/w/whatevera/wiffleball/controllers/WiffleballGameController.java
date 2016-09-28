package w.whatevera.wiffleball.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.*;
import w.whatevera.wiffleball.game.*;
import w.whatevera.wiffleball.game.impl.GameImpl;
import w.whatevera.wiffleball.game.impl.GameSettingsImpl;
import w.whatevera.wiffleball.game.impl.PlayerImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by rich on 9/21/16.
 */
@RestController
public class WiffleballGameController {

    private static Map<String, Game> games = Maps.newHashMap();

    @RequestMapping(value = "/w/game/demo", method= RequestMethod.GET, produces = "application/json")
    public Game gameDemo() {

        Game game = newDemoGame();
        games.put(game.getId(), game);
        return game;
    }

    @RequestMapping(value = "/w/game/new/{awayTeam}/{homeTeam}", method= RequestMethod.GET, produces = "application/json")
    public Game newGame(@PathVariable("awayTeam")List<String> awayTeamPlayerNames,
                        @PathVariable("homeTeam")List<String> homeTeamPlayerNames) {

        List<Player> awayTeam = Lists.newArrayList();
        List<Player> homeTeam = Lists.newArrayList();

        for (String playerName : awayTeamPlayerNames) {
            Player player = new PlayerImpl(playerName);
            awayTeam.add(player);
        }

        for (String playerName : homeTeamPlayerNames) {
            Player player = new PlayerImpl(playerName);
            homeTeam.add(player);
        }

        GameSettings gameSettings = new GameSettingsImpl(awayTeam.size(), 3, 3);
        Game game = new GameImpl(gameSettings, awayTeam, homeTeam);
        games.put(game.getId(), game);
        return game;
    }

    @RequestMapping(value = "/w/game/{game}", method= RequestMethod.GET, produces = "application/json")
    public Game game(@PathVariable("game") String gameId) {

        Game game = games.get(gameId);
        return game;
    }

    @RequestMapping(value = "/w/games", method= RequestMethod.GET, produces = "application/json")
    public List<String> allGames() {

        List<String> result = Lists.newArrayList();

        for (Map.Entry<String, Game> entry : games.entrySet()) {
            result.add(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }

        return result;
    }

    @RequestMapping(value = "/w/game/{game}/play/{play}", method= RequestMethod.GET, produces = "application/json")
    public Game gameEvent(@PathVariable("game") String gameId,
                                @PathVariable("play") GamePlayEvent event,
                                @RequestParam(required = false) String player1,
                                @RequestParam(required = false) String player2) {

        Game game = games.get(gameId);
        Player player1Player = GameUtils.findPlayer(game, player1);
        game.apply(event, player1Player);
        return game;
    }

    @RequestMapping(value = "/w/game/{game}/stats", method= RequestMethod.GET, produces = "application/json")
    public GameStats gameStats(@PathVariable("game") String gameId) {

        Game game = games.get(gameId);
        return GameUtils.calculateStats(game);
    }

    private static Game newDemoGame() {

        Player bill = new PlayerImpl("bill");
        Player justin = new PlayerImpl("justin");
        Player john = new PlayerImpl("john");
        Player jim = new PlayerImpl("jim");
        Player shawn = new PlayerImpl("shawn");
        Player rich = new PlayerImpl("rich");

        List<Player> awayTeam = Lists.newArrayList(bill, justin, john);
        List<Player> homeTeam = Lists.newArrayList(jim, shawn, rich);

        GameSettings gameSettings = new GameSettingsImpl(3, 3, 3);
        Game game = new GameImpl(gameSettings, awayTeam, homeTeam);

        return game;
    }
}
