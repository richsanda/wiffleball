package w.whatevera.wiffleball.domain;

import w.whatevera.wiffleball.game.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class GameStats {

    @Id
   	@GeneratedValue
   	private Long id;

    @OneToOne
    private TeamStats awayTeamStats;
    @OneToOne
    private TeamStats homeTeamStats;

    public GameStats(TeamStats awayTeamStats, TeamStats homeTeamStats) {
        this.awayTeamStats = awayTeamStats;
        this.homeTeamStats = homeTeamStats;
    }

    public TeamStats getAwayTeamStats() {
        return awayTeamStats;
    }

    public TeamStats getHomeTeamStats() {
        return homeTeamStats;
    }

    public GameStats add(GameStats gameStats) {

        return new GameStats(
                awayTeamStats.add(gameStats.getAwayTeamStats()),
                homeTeamStats.add(gameStats.getHomeTeamStats()));
    }
}
