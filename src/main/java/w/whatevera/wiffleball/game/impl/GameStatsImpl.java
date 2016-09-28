package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

/**
 * Created by rich on 9/23/16.
 */
public class GameStatsImpl implements GameStats {

    private TeamStats awayTeamStats;
    private TeamStats homeTeamStats;

    public GameStatsImpl(TeamStats awayTeamStats, TeamStats homeTeamStats) {
        this.awayTeamStats = awayTeamStats;
        this.homeTeamStats = homeTeamStats;
    }

    @Override
    public TeamStats getAwayTeamStats() {
        return awayTeamStats;
    }

    @Override
    public TeamStats getHomeTeamStats() {
        return homeTeamStats;
    }

    @Override
    public GameStats add(GameStats gameStats) {

        return new GameStatsImpl(
                awayTeamStats.add(gameStats.getAwayTeamStats()),
                homeTeamStats.add(gameStats.getHomeTeamStats()));
    }
}
