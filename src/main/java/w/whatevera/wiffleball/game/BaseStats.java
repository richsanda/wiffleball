package w.whatevera.wiffleball.game;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public interface BaseStats<StatsType> {

    int getGames();

    int getPlateAppearances();

    int getAtBats();

    int getWalks();

    int getHits();

    int getSingles();

    int getDoubles();

    int getTriples();

    int getHomeRuns();

    int getExtraBaseHits();

    int getTotalBases();

    int getStrikeouts();

    BigDecimal getBattingAverage();

    BigDecimal getSluggingPercentage();

    int getRuns();

    int getRunsBattedIn();

    StatsType addGame();

    StatsType addPlateAppearance();

    StatsType addAtBat();

    StatsType addWalk();

    StatsType addHit();

    StatsType addDouble();

    StatsType addTriple();

    StatsType addHomeRun();

    StatsType addStrikeout();

    StatsType addRuns(int runs);

    StatsType addRunsBattedIn(int runsBattedIn);

    StatsType add(StatsType addition);
}
