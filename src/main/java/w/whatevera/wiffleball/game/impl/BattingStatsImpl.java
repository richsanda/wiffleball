package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.BattingStats;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public class BattingStatsImpl implements BattingStats {

    private int games = 0;
    private int plateAppearances = 0;
    private int atBats = 0;
    private int hits = 0;
    private int doubles = 0;
    private int triples = 0;
    private int homeRuns = 0;
    private int strikeouts = 0;
    private int runs = 0;
    private int runsBattedIn = 0;

    @Override
    public int getGames() {
        return games;
    }

    public int getPlateAppearances() {
        return plateAppearances;
    }

    @Override
    public int getAtBats() {
        return atBats;
    }

    @Override
    public int getSingles() {
        return hits - doubles - triples - homeRuns;
    }

    @Override
    public int getDoubles() {
        return doubles;
    }

    @Override
    public int getTriples() {
        return triples;
    }

    @Override
    public int getHomeRuns() {
        return homeRuns;
    }

    @Override
    public int getExtraBaseHits() {
        return doubles + triples + homeRuns;
    }

    @Override
    public int getTotalBases() {
        return hits + doubles + 2 * triples + 3 * homeRuns;
    }

    @Override
    public int getStrikeouts() {
        return strikeouts;
    }

    @Override
    public BigDecimal getBattingAverage() {
        return new BigDecimal(hits).divide(new BigDecimal(atBats), BigDecimal.ROUND_DOWN);
    }

    @Override
    public BigDecimal getSluggingPercentage() {
        return new BigDecimal(getTotalBases()).divide(new BigDecimal(atBats), BigDecimal.ROUND_DOWN);
    }

    @Override
    public int getRuns() {
        return runs;
    }

    @Override
    public int getRunsBattedIn() {
        return runsBattedIn;
    }
}
