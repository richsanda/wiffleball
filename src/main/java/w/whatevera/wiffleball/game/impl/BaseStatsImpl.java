package w.whatevera.wiffleball.game.impl;

import com.sun.org.glassfish.external.statistics.Stats;
import w.whatevera.wiffleball.game.BaseStats;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public abstract class BaseStatsImpl<StatsType> implements BaseStats<StatsType> {

    private int games = 0;
    private int plateAppearances = 0;
    private int atBats = 0;
    private int walks = 0;
    private int hits = 0;
    private int doubles = 0;
    private int triples = 0;
    private int homeRuns = 0;
    private int strikeouts = 0;
    private int runs = 0;
    private int runsBattedIn = 0;

    protected StatsType add(BaseStats stats) {

        games += stats.getGames();
        plateAppearances += stats.getPlateAppearances();
        atBats += stats.getAtBats();
        walks += stats.getWalks();
        hits += stats.getHits();
        doubles += stats.getDoubles();
        triples += stats.getTriples();
        homeRuns += stats.getHomeRuns();
        strikeouts += stats.getStrikeouts();
        runs += stats.getRuns();
        runsBattedIn += stats.getRunsBattedIn();

        return (StatsType)this;
    }

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
    public int getWalks() {
        return walks;
    }

    @Override
    public int getHits() {
        return hits;
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

    @Override
    public StatsType addGame() {
        games++;
        return (StatsType)this;
    }

    @Override
    public StatsType addPlateAppearance() {
        plateAppearances++;
        return (StatsType)this;
    }

    @Override
    public StatsType addAtBat() {
        atBats++;
        addPlateAppearance();
        return (StatsType)this;
    }

    @Override
    public StatsType addWalk() {
        walks++;
        addPlateAppearance();
        return (StatsType)this;
    }

    @Override
    public StatsType addHit() {
        hits++;
        addAtBat();
        return (StatsType)this;
    }

    @Override
    public StatsType addDouble() {
        doubles++;
        addHit();
        return (StatsType)this;
    }

    @Override
    public StatsType addTriple() {
        triples++;
        addHit();
        return (StatsType)this;
    }

    @Override
    public StatsType addHomeRun() {
        homeRuns++;
        addHit();
        return (StatsType)this;
    }

    @Override
    public StatsType addStrikeout() {
        strikeouts++;
        addAtBat();
        return (StatsType)this;
    }

    @Override
    public StatsType addRuns(int runs) {
        this.runs += runs;
        return (StatsType)this;
    }

    @Override
    public StatsType addRunsBattedIn(int runsBattedIn) {
        this.runsBattedIn += runsBattedIn;
        return (StatsType)this;
    }
}
