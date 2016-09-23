package w.whatevera.wiffleball.game.impl;

import com.google.common.collect.Lists;
import w.whatevera.wiffleball.game.*;

import java.util.List;

/**
 * Created by rich on 9/1/16.
 */
public class GamePlayImpl implements GamePlay, GameStatus {

    private GameSettings gameSettings;

    private List<Player> awayTeam;
    private List<Player> homeTeam;

    private Player awayPitcher;
    private Player homePitcher;

    private Player batter;
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

    public GamePlayImpl(GameSettings gameSettings, List<Player> awayTeam, List<Player> homeTeam) {

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

    public GamePlayImpl(GameStatus status) {

        gameSettings = status.getGameSettings();

        awayTeam = status.getAwayTeam();
        homeTeam = status.getHomeTeam();

        awayPitcher = status.getAwayPitcher();
        homePitcher = status.getHomePitcher();

        batter = status.getBatter();
        onFirst = status.getOnFirst();
        onSecond = status.getOnSecond();
        onThird = status.getOnThird();
        platedRuns = status.getPlatedRuns();

        awayBatterIndex = status.getAwayBatterIndex();
        homeBatterIndex = status.getHomeBatterIndex();

        awayScore = status.getAwayScore();
        homeScore = status.getHomeScore();

        isHomeHalf = status.isHomeHalf();

        outs = status.getOuts();
        inning = status.getInning();
    }

    @Override
    public GameSettings getGameSettings() {
        return gameSettings;
    }

    @Override
    public List<Player> getAwayTeam() {
        return awayTeam;
    }

    @Override
    public List<Player> getHomeTeam() {
        return homeTeam;
    }

    @Override
    public Player getBatter() {
        return isHomeHalf ? getHomeBatter() : getAwayBatter();
    }

    @Override
    public Player getAwayBatter() {
        return awayTeam.get(awayBatterIndex);
    }

    @Override
    public Player getHomeBatter() {
        return homeTeam.get(homeBatterIndex);
    }

    @Override
    public int getAwayBatterIndex() {
        return awayBatterIndex;
    }

    @Override
    public int getHomeBatterIndex() {
        return homeBatterIndex;
    }

    @Override
    public Player getPitcher() {
        return isHomeHalf ? awayPitcher : homePitcher;
    }

    @Override
    public Player getAwayPitcher() {
        return awayPitcher;
    }

    @Override
    public Player getHomePitcher() {
        return homePitcher;
    }

    @Override
    public Player getOnFirst() {
        return onFirst;
    }

    @Override
    public Player getOnSecond() {
        return onSecond;
    }

    @Override
    public Player getOnThird() {
        return onThird;
    }

    @Override
    public List<Player> getPlatedRuns() {
        return platedRuns;
    }

    @Override
    public int getAwayScore() {
        return awayScore;
    }

    @Override
    public int getHomeScore() {
        return homeScore;
    }

    @Override
    public int getOuts() {
        return outs;
    }

    @Override
    public int getInning() {
        return inning;
    }

    @Override
    public boolean isHomeHalf() {
        return isHomeHalf;
    }

    @Override
    public boolean isOver() {

        int numberOfInnings = gameSettings.getNumberOfInnings();
        boolean homeTeamWins = inning >= numberOfInnings && isHomeHalf && homeScore > awayScore;
        boolean awayTeamWins = inning > numberOfInnings && !isHomeHalf && awayScore > homeScore;

        return homeTeamWins || awayTeamWins;
    }

    @Override
    public GameStatus status() {
        return this;
    }

    @Override
    public void setPitcher(Player pitcher) throws GameOverException {

        if (null == pitcher) return;

        checkIsGameOver();

        if (isHomeHalf) {
            homePitcher = pitcher;
        } else {
            awayPitcher = pitcher;
        }
    }

    @Override
    public void walk() throws GameOverException {

        checkIsGameOver();
        oneForceBase();
        nextBatter();
    }

    @Override
    public void hitSingle() throws GameOverException {

        checkIsGameOver();
        oneEqualBase();
        nextBatter();
    }

    @Override
    public void hitDouble() throws GameOverException {

        checkIsGameOver();
        twoEqualBases();
        nextBatter();
    }

    @Override
    public void hitTriple() throws GameOverException {

        checkIsGameOver();
        threeEqualBases();
        nextBatter();
    }

    @Override
    public void hitHomeRun() throws GameOverException {

        checkIsGameOver();
        fourEqualBases();
        nextBatter();
    }

    @Override
    public void error1Base(Player fielder) throws GameOverException {

        checkIsGameOver();
        oneEqualBase();
        nextBatter();
    }

    @Override
    public void error2Base(Player fielder) throws GameOverException {

        checkIsGameOver();
        twoEqualBases();
        nextBatter();
    }

    @Override
    public void error3Base(Player fielder) throws GameOverException {

        checkIsGameOver();
        threeEqualBases();
        nextBatter();
    }

    @Override
    public void strikeoutSwinging() throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    @Override
    public void strikeoutLooking() throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    @Override
    public void strikeoutSwingingAndLooking() throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    @Override
    public void flyOut(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    @Override
    public void groundOut(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    @Override
    public void lineOut(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    @Override
    public void doublePlay(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        if (outs < 2) {
            leadForceOut();
        }
        out();
    }

    @Override
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
        batter = getBatter();
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

        if (null != batter) {
            onFirst = batter;
            batter = null;
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
        batter = getBatter();
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
