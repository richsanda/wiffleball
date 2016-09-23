package w.whatevera.wiffleball.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.*;
import w.whatevera.wiffleball.game.*;
import w.whatevera.wiffleball.game.impl.GameSettingsImpl;
import w.whatevera.wiffleball.game.impl.GameStatusImpl;
import w.whatevera.wiffleball.game.impl.PlayerImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by rich on 9/21/16.
 */
@RestController
public class WiffleballGameController {

    private static Map<String, GameStatus> games = Maps.newHashMap();

    @RequestMapping(value = "/game", method= RequestMethod.GET, produces = "application/json")
    public GameStatus game() {

        GameStatus game = newGame();
        games.put(game.getId(), game);
        return game;
    }

    @RequestMapping(value = "/game/{game}/play/{play}", method= RequestMethod.GET, produces = "application/json")
    public GameStatus gameEvent(@PathVariable("game") String gameId,
                                @PathVariable("play") GamePlayEvent play,
                                @RequestParam(required = false) Player pitcher,
                                @RequestParam(required = false) Player fielder,
                                @RequestParam(required = false) Player batter) {

        GameStatus game = games.get(gameId);
        return GameUtils.applyPlayToGame((GamePlay)game, play, pitcher, fielder, batter);
    }

    private static GameStatus newGame() {

        Player bill = new PlayerImpl("bill");
        Player justin = new PlayerImpl("justin");
        Player john = new PlayerImpl("john");
        Player jim = new PlayerImpl("jim");
        Player shawn = new PlayerImpl("shawn");
        Player rich = new PlayerImpl("rich");

        List<Player> awayTeam = Lists.newArrayList(bill, justin, john);
        List<Player> homeTeam = Lists.newArrayList(jim, shawn, rich);

        GameSettings gameSettings = new GameSettingsImpl(3, 3, 3);
        GameStatusImpl gameStatus = new GameStatusImpl(gameSettings, awayTeam, homeTeam);

        return gameStatus;
    }
}
