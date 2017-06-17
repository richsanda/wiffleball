package w.whatevera.wiffleball.domain;

import com.google.common.collect.Lists;
import w.whatevera.wiffleball.game.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rich on 9/4/16.
 */
@Entity
public class GameStatus implements IGameStatus {

    @Id
   	@GeneratedValue
   	private Long id;

    @ManyToOne
    private GameSettings gameSettings;

    @ManyToOne
    private Team away;
    @ManyToOne
    private Team home;

    @ManyToOne
    private Player awayPitch;
    @ManyToOne
    private Player homePitch;

    @OneToOne
    private BaseRunner first;
    @OneToOne
    private BaseRunner second;
    @OneToOne
    private BaseRunner third;
    @ManyToMany
    private List<BaseRunner> plated;

    private int awayBatterIndex;
    private int homeBatterIndex;

    private int awayScore;
    private int homeScore;

    private boolean isHomeHalf;

    private int outs;
    private int inning;

    private int numberOfInnings;
    
    public GameStatus() {}

    public GameStatus(IGameStatus status) {

        gameSettings = status.getGameSettings();

        away = status.getAwayTeam();
        home = status.getHomeTeam();

        awayPitch = status.getAwayPitcher();
        homePitch = status.getHomePitcher();

        first = status.getOnFirst();
        second = status.getOnSecond();
        third = status.getOnThird();
        plated = Lists.newArrayList(status.getPlatedRuns());

        awayBatterIndex = status.getAwayBatterIndex();
        homeBatterIndex = status.getHomeBatterIndex();

        awayScore = status.getAwayScore();
        homeScore = status.getHomeScore();

        isHomeHalf = status.isHomeHalf();

        outs = status.getOuts();
        inning = status.getInning();

        numberOfInnings = status.getNumberOfInnings();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public Team getAwayTeam() {
        return away;
    }

    public Team getHomeTeam() {
        return home;
    }

    public Player getBatter() {
        return isHomeHalf ? getHomeBatter() : getAwayBatter();
    }

    public Player getAwayBatter() {
        return away.getPlayers().get(awayBatterIndex);
    }

    public Player getHomeBatter() {
        return home.getPlayers().get(homeBatterIndex);
    }

    public int getAwayBatterIndex() {
        return awayBatterIndex;
    }

    public int getHomeBatterIndex() {
        return homeBatterIndex;
    }

    public Player getPitcher() {
        return isHomeHalf ? awayPitch : homePitch;
    }

    public Player getAwayPitcher() {
        return awayPitch;
    }

    public Player getHomePitcher() {
        return homePitch;
    }

    public BaseRunner getOnFirst() {
        return first;
    }

    public BaseRunner getOnSecond() {
        return second;
    }

    public BaseRunner getOnThird() {
        return third;
    }

    public List<BaseRunner> getPlatedRuns() {
        return plated;
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

    public int getNumberOfInnings() {
        return numberOfInnings;
    }

    public boolean isHomeHalf() {
        return isHomeHalf;
    }

    public boolean isOver() {

        boolean homeTeamWins = inning >= numberOfInnings && isHomeHalf && homeScore > awayScore;
        boolean awayTeamWins = inning > numberOfInnings && !isHomeHalf && awayScore > homeScore;

        return homeTeamWins || awayTeamWins;
    }
}
