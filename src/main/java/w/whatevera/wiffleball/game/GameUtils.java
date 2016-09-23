package w.whatevera.wiffleball.game;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by rich on 9/21/16.
 */
public class GameUtils {

    public static GameStatus applyPlayToGame(GamePlay game, GamePlayEvent event) {
        return applyPlayToGame(game, event, null);
    }

    public static GameStatus applyPlayToGame(GamePlay game, GamePlayEvent event, Player player1, Player player2) {
        return applyPlayToGame(game, event, determinePlayerMap(event, player1, player2));
    }

    public static GameStatus applyPlayToGame(GamePlay game, GamePlayEvent event, Map<PlayerType, Player> players) {

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
                    game.setPitcher(pitcher);
                    break;
                case WALK:
                    game.walk();
                    break;
                case SINGLE:
                    game.hitSingle();
                    break;
                case DOUBLE:
                    game.hitDouble();
                    break;
                case TRIPLE:
                    game.hitTriple();
                    break;
                case HOME_RUN:
                    game.hitHomeRun();
                    break;
                case ERROR_1:
                    game.error1Base(fielder);
                    break;
                case ERROR_2:
                    game.error2Base(fielder);
                    break;
                case ERROR_3:
                    game.error3Base(fielder);
                    break;
                case STRIKEOUT_SWINGING:
                    game.strikeoutSwinging();
                    break;
                case STRIKEOUT_LOOKING:
                    game.strikeoutLooking();
                    break;
                case STRIKEOUT_BOTH:
                    game.strikeoutSwingingAndLooking();
                    break;
                case FLY_OUT:
                    game.flyOut(fielder);
                    break;
                case POP_OUT:
                    game.flyOut(fielder);
                    break;
                case GROUND_OUT:
                    game.groundOut(fielder);
                    break;
                case LINE_OUT:
                    game.lineOut(fielder);
                    break;
                case DOUBLE_PLAY:
                    game.doublePlay(fielder);
                    break;
                case REPLACE_PLAYER:
                    game.replacePlayer(fielder, sub);
                    break;
                //case SKIP:
                //    game.skipBatter();
                default:
                    break;
            }
        } catch (GameOverException e) {
            // pass
        }

        return (GameStatus)game;
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
