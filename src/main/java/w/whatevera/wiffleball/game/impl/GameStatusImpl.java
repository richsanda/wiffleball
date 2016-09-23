package w.whatevera.wiffleball.game.impl;

import com.google.common.collect.Lists;
import w.whatevera.wiffleball.game.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by rich on 9/4/16.
 */
public class GameStatusImpl implements GameStatus, GamePlay {

    private GameSettings gameSettings;

    private List<Player> awayTeam;
    private List<Player> homeTeam;

    private Player awayPitcher;
    private Player homePitcher;

    private Player atBat;
    private Player onFirst;
    private Player onSecond;
    private Player onThird;
    private List<Player> platedRuns = Lists.newArrayList();

    private int awayBatterIndex = 0;
    private int homeBatterIndex = 0;

    private int awayScore = 0;
    private int homeScore = 0;

    private boolean isHomeHalf = true;

    private int outs = 0;
    private int inning = 0;

    public GameStatusImpl(GameSettings gameSettings, List<Player> awayTeam, List<Player> homeTeam) {

        int numberOfPlayersPerTeam = gameSettings.getNumberOfPlayersPerTeam();

        assert null != awayTeam;
        assert null != homeTeam;
        assert awayTeam.size() == numberOfPlayersPerTeam;
        assert homeTeam.size() == numberOfPlayersPerTeam;

        this.gameSettings = gameSettings;
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;

        this.homePitcher = homeTeam.get(numberOfPlayersPerTeam - 1);
        this.awayPitcher = awayTeam.get(numberOfPlayersPerTeam - 1);

        nextHalfInning();
    }

    public List<Player> getAwayTeam() {
        return awayTeam;
    }

    public List<Player> getHomeTeam() {
        return homeTeam;
    }

    public Player getBatter() {
        return isHomeHalf ? getHomeBatter() : getAwayBatter();
    }

    public Player getAwayBatter() {
        return awayTeam.get(awayBatterIndex);
    }

    public Player getHomeBatter() {
        return homeTeam.get(homeBatterIndex);
    }

    public int getAwayBatterIndex() {
        return awayBatterIndex;
    }

    public int getHomeBatterIndex() {
        return homeBatterIndex;
    }

    public Player getPitcher() {
        return isHomeHalf ? awayPitcher : homePitcher;
    }

    public Player getAwayPitcher() {
        return awayPitcher;
    }

    public Player getHomePitcher() {
        return homePitcher;
    }

    public Player getOnFirst() {
        return onFirst;
    }

    public Player getOnSecond() {
        return onSecond;
    }

    public Player getOnThird() {
        return onThird;
    }

    public List<Player> getPlatedRuns() {
        return platedRuns;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getOuts() {
        return outs;
    }

    public int getInning() {
        return inning;
    }

    public boolean isHomeHalf() {
        return isHomeHalf;
    }

    public boolean isOver() {

        int numberOfInnings = gameSettings.getNumberOfInnings();
        boolean homeTeamWins = inning >= numberOfInnings && isHomeHalf && homeScore > awayScore;
        boolean awayTeamWins = inning > numberOfInnings && !isHomeHalf && awayScore > homeScore;

        return homeTeamWins || awayTeamWins;
    }

    public GameStatus status() {
        return this;
    }

    public void setPitcher(Player pitcher) throws GameOverException {

        if (null == pitcher) return;

        checkIsGameOver();

        if (isHomeHalf) {
            homePitcher = pitcher;
        } else {
            awayPitcher = pitcher;
        }
    }

    public void walk() throws GameOverException {

        checkIsGameOver();

        oneForceBase();
        nextBatter();
    }

    public void hitSingle() throws GameOverException {

        checkIsGameOver();

        oneEqualBase();
        nextBatter();
    }

    public void hitDouble() throws GameOverException {

        checkIsGameOver();

        twoEqualBases();
        nextBatter();
    }

    public void hitTriple() throws GameOverException {

        checkIsGameOver();

        threeEqualBases();
        nextBatter();
    }

    public void hitHomeRun() throws GameOverException {

        checkIsGameOver();

        fourEqualBases();
        nextBatter();
    }

    public void error1Base(Player fielder) throws GameOverException {

        checkIsGameOver();

        oneEqualBase();
        nextBatter();
    }

    public void error2Base(Player fielder) throws GameOverException {

        checkIsGameOver();

        twoEqualBases();
        nextBatter();
    }

    public void error3Base(Player fielder) throws GameOverException {

        checkIsGameOver();

        threeEqualBases();
        nextBatter();
    }

    public void strikeoutSwinging() throws GameOverException {

        checkIsGameOver();

        out();
        nextBatter();
    }

    public void strikeoutLooking() throws GameOverException {

        checkIsGameOver();

        out();
        nextBatter();
    }

    public void strikeoutSwingingAndLooking() throws GameOverException {

        checkIsGameOver();

        out();
        nextBatter();
    }

    public void flyOut(Player fielder) throws GameOverException {

        checkIsGameOver();

        out();
        nextBatter();
    }

    public void groundOut(Player fielder) throws GameOverException {

        checkIsGameOver();

        out();
        nextBatter();
    }

    public void lineOut(Player fielder) throws GameOverException {

        checkIsGameOver();

        out();
        nextBatter();
    }

    public void doublePlay(Player fielder) throws GameOverException {

        checkIsGameOver();

        out();
        leadForceOut();
        nextBatter();
    }

    public void replacePlayer(Player replacing, Player replacement) throws GameOverException {

        checkIsGameOver();
    }

    public void leadForceOut() {
        if (isBasesLoaded()) {
            onThird = null;
        } else if (isFirstAndSecondOccupied()) {
            onSecond = null;
        } else if (isFirstOccupied()) {
            onFirst = null;
        } else {
            // um, no force ? just return
            return;
        }
        out();
    }

    private void nextBatter() {
        if (isHomeHalf) {
            nextHomeBatter();
        } else {
            nextAwayBatter();
        }
        atBat = getBatter();
    }

    private void nextHomeBatter() {
        homeBatterIndex = nextBatterIndex(homeBatterIndex);
    }

    private void nextAwayBatter() {
        awayBatterIndex = nextBatterIndex(awayBatterIndex);
    }

    private void run() {
        if (isHomeHalf) {
            homeScore++;
        } else {
            awayScore++;
        }
    }

    private void pushToHome() {
        if (null != onThird) {
            // platedRuns.add(onThird);
            onThird = null;
            run();
        }
    }

    private void pushToThird() {

        if (null != onThird) {
            pushToHome();
        }

        if (null != onSecond) {
            onThird = onSecond;
            onSecond = null;
        }
    }

    private void pushToSecond() {

        if (null != onSecond) {
            pushToThird();
        }

        if (null != onFirst) {
            onSecond = onFirst;
            onFirst = null;
        }
    }

    private void pushToFirst() {

        if (null != onFirst) {
            pushToSecond();
        }

        if (null != atBat) {
            onFirst = atBat;
            atBat = null;
        }
    }

    private void oneForceBase() {
        pushToFirst();
    }

    private void oneEqualBase() {
        pushToHome();
        pushToThird();
        pushToSecond();
        pushToFirst();
    }

    private void twoEqualBases() {
        oneEqualBase();
        oneEqualBase();
    }

    private void threeEqualBases() {
        twoEqualBases();
        oneEqualBase();
    }

    private void fourEqualBases() {
        threeEqualBases();
        oneEqualBase();
    }

    private void out() {

        outs++;

        if (isFinalOut()) {
            nextHalfInning();
        }
    }

    private void checkIsGameOver() throws GameOverException {
        if (isOver()) throw new GameOverException();
    }

    private boolean isFinalOut() {
        return outs == gameSettings.getNumberOfOutsPerInning();
    }

    private void nextHalfInning() {
        if (isHomeHalf) inning++;
        isHomeHalf = !isHomeHalf;
        atBat = getBatter();
        onFirst = null;
        onSecond = null;
        onThird = null;
        platedRuns = Lists.newArrayList();
        outs = 0;
    }

    private int nextBatterIndex(int currentBatterIndex) {
        int nextBatterIndex = currentBatterIndex + 1;
        return nextBatterIndex == gameSettings.getNumberOfPlayersPerTeam() ? 0 : nextBatterIndex;
    }

    private boolean isFirstOccupied() {
        return null != onFirst;
    }

    private boolean isSecondOccupied() {
        return null != onSecond;
    }

    private boolean isThirdOccupied() {
        return null != onThird;
    }

    private boolean isFirstAndSecondOccupied() {
        return isFirstOccupied() && isSecondOccupied();
    }

    private boolean isBasesLoaded() {
        return isFirstAndSecondOccupied() && isThirdOccupied();
    }
}
