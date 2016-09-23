package w.whatevera.wiffleball.game;

import com.google.common.collect.Maps;
import w.whatevera.wiffleball.game.impl.GamePlayImpl;
import w.whatevera.wiffleball.game.impl.GameStatusImpl;

import java.util.Map;

/**
 * Created by rich on 9/21/16.
 */
public class GameUtils {

    public static GameStatus applyPlayToGame(Game game, GamePlayEvent event, Player player1, Player player2) {
        return applyPlayToGame(game, event, determinePlayerMap(event, player1, player2));
    }

    public static GameStatus applyPlayToGame(Game game, GamePlayEvent event, Map<PlayerType, Player> players) {

        GamePlay gamePlay = new GamePlayImpl(game.getGameStatus());

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
                case ERROR_1:
                    gamePlay.error1Base(fielder);
                    break;
                case ERROR_2:
                    gamePlay.error2Base(fielder);
                    break;
                case ERROR_3:
                    gamePlay.error3Base(fielder);
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
                case REPLACE_PLAYER:
                    gamePlay.replacePlayer(fielder, sub);
                    break;
                case UNDO:
                    game.undo();
                //case SKIP:
                //    gamePlay.skipBatter();
                default:
                    break;
            }
        } catch (GameOverException e) {
            // pass
        }

        // TODO: maybe move this special logic to GameImpl ?
        return GamePlayEvent.UNDO.equals(event) ? game.getGameStatus() : new GameStatusImpl((GameStatus)gamePlay);
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
            case ERROR_1:
                result.put(PlayerType.FIELDER, player1);
                break;
            case ERROR_2:
                result.put(PlayerType.FIELDER, player1);
                break;
            case ERROR_3:
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
}
