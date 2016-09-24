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

    @RequestMapping(value = "/game", method= RequestMethod.GET, produces = "application/json")
    public Game game() {

        Game game = newGame();
        games.put(game.getId(), game);
        return game;
    }

    @RequestMapping(value = "/game/{game}/stats", method= RequestMethod.GET, produces = "application/json")
    public GameStats gameStats(@PathVariable("game") String gameId) {

        Game game = games.get(gameId);
        return GameUtils.calculateStats(game.getGameLog().iterator());
    }

    @RequestMapping(value = "/game/{game}/play/{play}", method= RequestMethod.GET, produces = "application/json")
    public Game gameEvent(@PathVariable("game") String gameId,
                                @PathVariable("play") GamePlayEvent event,
                                @RequestParam(required = false) Player pitcher,
                                @RequestParam(required = false) Player fielder,
                                @RequestParam(required = false) Player batter) {

        Game game = games.get(gameId);
        game.apply(event);
        return game;
    }

    private static Game newGame() {

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
