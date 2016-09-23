package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/21/16.
 */
public class GameUtils {

    public static GameStatus applyPlayToGame(GamePlay game, GamePlayEvent play, Player pitcher, Player fielder, Player batter) {

        try {
            switch (play) {
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
                //case REPLACE_PLAYER:
                //    game.replacePlayer(replacing, replacement);
                //    break;
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
}
