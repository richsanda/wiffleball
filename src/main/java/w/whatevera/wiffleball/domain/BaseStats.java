package w.whatevera.wiffleball.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by rich on 9/23/16.
 */
public abstract class BaseStats<StatsType> {

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

    
    public int getGames() {
        return games;
    }

    public int getPlateAppearances() {
        return plateAppearances;
    }

    
    public int getAtBats() {
        return atBats;
    }

    
    public int getWalks() {
        return walks;
    }

    
    public int getHits() {
        return hits;
    }

    
    public int getSingles() {
        return hits - doubles - triples - homeRuns;
    }

    
    public int getDoubles() {
        return doubles;
    }

    
    public int getTriples() {
        return triples;
    }

    
    public int getHomeRuns() {
        return homeRuns;
    }

    
    public int getExtraBaseHits() {
        return doubles + triples + homeRuns;
    }

    
    public int getTotalBases() {
        return hits + doubles + 2 * triples + 3 * homeRuns;
    }

    
    public int getStrikeouts() {
        return strikeouts;
    }

    
    public BigDecimal getBattingAverage() {
        if (atBats == 0) {
            return BigDecimal.ZERO;
        }
        return threePlaces(hits).divide(threePlaces(atBats), RoundingMode.HALF_UP);
    }

    
    public BigDecimal getSluggingPercentage() {
        if (atBats == 0) {
            return BigDecimal.ZERO;
        }
        return threePlaces(getTotalBases()).divide(threePlaces(atBats), RoundingMode.HALF_UP);
    }

    
    public BigDecimal getOnBasePercentage() {
        if (plateAppearances == 0) {
            return BigDecimal.ZERO;
        }
        return threePlaces(hits + walks).divide(threePlaces(plateAppearances), RoundingMode.HALF_UP);
    }

    
    public BigDecimal getOnBasePlusSlugging() {
        return getOnBasePercentage().add(getSluggingPercentage());
    }

    
    public int getRuns() {
        return runs;
    }

    
    public int getRunsBattedIn() {
        return runsBattedIn;
    }

    
    public StatsType addGame() {
        games++;
        return (StatsType)this;
    }

    
    public StatsType addPlateAppearance() {
        plateAppearances++;
        return (StatsType)this;
    }

    
    public StatsType addAtBat() {
        atBats++;
        addPlateAppearance();
        return (StatsType)this;
    }

    
    public StatsType addWalk() {
        walks++;
        addPlateAppearance();
        return (StatsType)this;
    }

    
    public StatsType addHit() {
        hits++;
        addAtBat();
        return (StatsType)this;
    }

    
    public StatsType addDouble() {
        doubles++;
        addHit();
        return (StatsType)this;
    }

    
    public StatsType addTriple() {
        triples++;
        addHit();
        return (StatsType)this;
    }

    
    public StatsType addHomeRun() {
        homeRuns++;
        addHit();
        return (StatsType)this;
    }

    
    public StatsType addStrikeout() {
        strikeouts++;
        addAtBat();
        return (StatsType)this;
    }

    
    public StatsType addRuns(int runs) {
        this.runs += runs;
        return (StatsType)this;
    }

    
    public StatsType addRunsBattedIn(int runsBattedIn) {
        this.runsBattedIn += runsBattedIn;
        return (StatsType)this;
    }

    private BigDecimal threePlaces(int number) {
        return new BigDecimal(number).setScale(3, RoundingMode.HALF_UP);
    }
}
