package w.whatevera;

import com.google.common.collect.Lists;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import w.whatevera.wiffleball.game.GameOverException;
import w.whatevera.wiffleball.game.GameSettings;
import w.whatevera.wiffleball.game.Player;
import w.whatevera.wiffleball.game.impl.GameSettingsImpl;
import w.whatevera.wiffleball.game.impl.GameStatusImpl;
import w.whatevera.wiffleball.game.impl.PlayerImpl;

import java.util.List;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class WiffleballApplicationTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WiffleballApplicationTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( WiffleballApplicationTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

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

        try {

            while (!gameStatus.isOver()) {

                System.out.println(gameStatus.getPitcher().getName() + " pitching to " + gameStatus.getBatter().getName() + "...");
                System.out.println("score: " + gameStatus.getAwayScore() + " - " + gameStatus.getHomeScore() + "; outs: " + gameStatus.getOuts() + "; inning: " + (gameStatus.isHomeHalf() ? "bot " : "top ") + gameStatus.getInning());

                boolean homer = new Random().nextBoolean();
                if (homer) {
                    System.out.println("HOME RUN !");
                    gameStatus.hitHomeRun();
                } else {
                    System.out.println("STRIKEOUT !");
                    gameStatus.strikeoutLooking();
                }

                System.out.println("**********");
            }

        } catch (GameOverException e) {
            System.out.println("GAME IS OVER !!");
        }

        System.out.println("final score: " + gameStatus.getAwayScore() + " - " + gameStatus.getHomeScore() + " (away - home)");
    }
}
