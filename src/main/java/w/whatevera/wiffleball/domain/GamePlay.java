package w.whatevera.wiffleball.domain;

import com.google.common.collect.Lists;
import w.whatevera.wiffleball.game.GameOverException;
import w.whatevera.wiffleball.game.GameUtils;
import w.whatevera.wiffleball.game.IGameStatus;

import java.util.List;

/**
 * Created by rich on 9/1/16.
 */
public class GamePlay implements IGameStatus {

    private GameUtils gameUtils;

    private GameSettings gameSettings;

    private Team awayTeam;
    private Team homeTeam;

    private Player awayPitcher;
    private Player homePitcher;

    private Player batter;

    private BaseRunner onFirst;
    private BaseRunner onSecond;
    private BaseRunner onThird;
    private List<BaseRunner> platedRuns = Lists.newArrayList();

    private int awayBatterIndex = 0;
    private int homeBatterIndex = 0;

    private int awayScore = 0;
    private int homeScore = 0;

    private int numberOfInnings = 0;

    private boolean isHomeHalf = true;

    private int outs = 0;
    private int inning = 0;

    public GamePlay(GameSettings gameSettings, Team awayTeam, Team homeTeam) {

        assert null != awayTeam;
        assert null != homeTeam;

        this.gameSettings = gameSettings;
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;

        this.homePitcher = homeTeam.getPlayers().get(homeTeam.getPlayers().size() - 1);
        this.awayPitcher = awayTeam.getPlayers().get(awayTeam.getPlayers().size() - 1);

        this.numberOfInnings = gameSettings.getNumberOfInnings();

        nextHalfInning();
    }

    public GamePlay(IGameStatus status, GameUtils gameUtils) {

        this.gameUtils = gameUtils;

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

        numberOfInnings = status.getNumberOfInnings();
    }

    @Override
    public GameSettings getGameSettings() {
        return gameSettings;
    }

    @Override
    public Team getAwayTeam() {
        return awayTeam;
    }

    @Override
    public Team getHomeTeam() {
        return homeTeam;
    }

    @Override
    public Player getBatter() {
        return isHomeHalf ? getHomeBatter() : getAwayBatter();
    }

    @Override
    public Player getAwayBatter() {
        return awayTeam.getPlayers().get(awayBatterIndex);
    }

    @Override
    public Player getHomeBatter() {
        return homeTeam.getPlayers().get(homeBatterIndex);
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
    public BaseRunner getOnFirst() {
        return onFirst;
    }

    @Override
    public BaseRunner getOnSecond() {
        return onSecond;
    }

    @Override
    public BaseRunner getOnThird() {
        return onThird;
    }

    @Override
    public List<BaseRunner> getPlatedRuns() {
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
    public int getNumberOfInnings() {
        return numberOfInnings;
    }

    @Override
    public boolean isHomeHalf() {
        return isHomeHalf;
    }

    @Override
    public boolean isOver() {

        boolean homeTeamWins = inning >= numberOfInnings && isHomeHalf && homeScore > awayScore;
        boolean awayTeamWins = inning > numberOfInnings && !isHomeHalf && awayScore > homeScore;

        return homeTeamWins || awayTeamWins;
    }

    public IGameStatus status() {
        return this;
    }

    public void setPitcher(Player pitcher) throws GameOverException {

        // allow null to aid in calculating era
        //if (null == pitcher) return;

        checkIsGameOver();

        if (!isHomeHalf) {
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

    public void errorReach(Player fielder) throws GameOverException {

        checkIsGameOver();
        oneEqualBase();
        nextBatter();
    }

    public void errorAdvance(Player fielder) throws GameOverException {

        checkIsGameOver();
        oneEqualBaseKeepBatter();
    }

    public void strikeoutSwinging() throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    public void strikeoutLooking() throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    public void strikeoutSwingingAndLooking() throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    public void flyOut(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    public void groundOut(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    public void lineOut(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        out();
    }

    public void doublePlay(Player fielder) throws GameOverException {

        checkIsGameOver();
        nextBatter();
        if (outs < 2) {
            leadForceOut();
        }
        out();
    }

    public void skipBatter() throws GameOverException {

        checkIsGameOver();
        nextBatter();
    }

    public void finalizeGame() {

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
        batter = getBatter();
    }

    private void nextHomeBatter() {
        homeBatterIndex = nextBatterIndex(homeBatterIndex, homeTeam.getPlayers().size());
    }

    private void nextAwayBatter() {
        awayBatterIndex = nextBatterIndex(awayBatterIndex, awayTeam.getPlayers().size());
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
            platedRuns.add(onThird);
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
            onFirst = newBaseRunner(batter, getPitcher());
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

    private void oneEqualBaseKeepBatter() {

        pushToHome();
        pushToThird();
        pushToSecond();
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

    public void out() {

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

        if (isHomeHalf) {
            inning++;
        }
        isHomeHalf = !isHomeHalf;
        batter = getBatter();
        onFirst = null;
        onSecond = null;
        onThird = null;
        platedRuns = Lists.newArrayList();
        outs = 0;

         // account for extra innings
        if (!isHomeHalf && !isOver() && inning > numberOfInnings) {
            numberOfInnings++;
        }
    }

    private int nextBatterIndex(int currentBatterIndex, int numberOfPlayersPerTeam) {

        int nextBatterIndex = currentBatterIndex + 1;
        return nextBatterIndex == numberOfPlayersPerTeam ? 0 : nextBatterIndex;
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

    // for use in calculating earned run average
    public void clearPitchersOfResponsibility() {
        if (null != onFirst) {
            onFirst = newBaseRunner(onFirst.getRunner());
        }
        if (null != onSecond) {
            onSecond = newBaseRunner(onSecond.getRunner());
        }
        if (null != onThird) {
            onThird = newBaseRunner(onThird.getRunner());
        }
        platedRuns.clear();
    }

    public List<BaseRunner> allBaseRunners() {
        List<BaseRunner> result = Lists.newArrayList();
        if (null != onFirst) result.add(onFirst);
        if (null != onSecond) result.add(onSecond);
        if (null != onThird) result.add(onThird);
        if (null != platedRuns) result.addAll(platedRuns);
        return result;
    }

    public BaseRunner newBaseRunner(Player runner) {
        BaseRunner baseRunner = new BaseRunner(runner, null);
        if (null != gameUtils) gameUtils.baseRunnerRepository.save(baseRunner);
        return baseRunner;
    }

    public BaseRunner newBaseRunner(Player runner, Player pitcher) {
        BaseRunner baseRunner = new BaseRunner(runner, pitcher);
        if (null != gameUtils) gameUtils.baseRunnerRepository.save(baseRunner);
        return baseRunner;
    }
}
