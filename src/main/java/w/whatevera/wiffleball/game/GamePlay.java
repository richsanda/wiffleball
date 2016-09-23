package w.whatevera.wiffleball.game;

/**
 * Created by rich on 8/31/16.
 */
public interface GamePlay {

    // game events

    GameStatus status();

    void setPitcher(Player pitcher) throws GameOverException;

    void walk() throws GameOverException;

    void hitSingle() throws GameOverException;

    void hitDouble() throws GameOverException;

    void hitTriple() throws GameOverException;

    void hitHomeRun() throws GameOverException;

    void error1Base(Player fielder) throws GameOverException;

    void error2Base(Player fielder) throws GameOverException;

    void error3Base(Player fielder) throws GameOverException;

    void strikeoutSwinging() throws GameOverException;

    void strikeoutLooking() throws GameOverException;

    void strikeoutSwingingAndLooking() throws GameOverException;

    void flyOut(Player fielder) throws GameOverException;

    void groundOut(Player fielder) throws GameOverException;

    void lineOut(Player fielder) throws GameOverException;

    void doublePlay(Player fielder) throws GameOverException;

    void replacePlayer(Player replacing, Player replacement) throws GameOverException;
}
