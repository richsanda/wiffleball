package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

import java.util.List;

/**
 * Created by rich on 9/4/16.
 */
public class GameStatusImpl implements GameStatus {

    final private GameSettings gameSettings;

    final private List<Player> awayTeam;
    final private List<Player> homeTeam;

    final private Player awayPitcher;
    final private Player homePitcher;

    final private Player onFirst;
    final private Player onSecond;
    final private Player onThird;
    final private List<Player> platedRuns;

    final private int awayBatterIndex;
    final private int homeBatterIndex;

    final private int awayScore;
    final private int homeScore;

    final private boolean isHomeHalf;

    final private int outs;
    final private int inning;

    public GameStatusImpl(GameStatus status) {

        gameSettings = status.getGameSettings();

        awayTeam = status.getAwayTeam();
        homeTeam = status.getHomeTeam();

        awayPitcher = status.getAwayPitcher();
        homePitcher = status.getHomePitcher();

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

    public GameSettings getGameSettings() {
        return gameSettings;
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
}
