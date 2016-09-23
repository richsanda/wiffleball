package w.whatevera.wiffleball.game;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public interface BattingStats {

    int getGames();

    int getPlateAppearances();

    int getAtBats();

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
}
