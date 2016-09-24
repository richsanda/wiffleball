package w.whatevera.wiffleball.game;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public interface PitchingStats extends BaseStats<PitchingStats> {

    int getEarnedRuns();

    BigDecimal getEarnedRunAverage();

    int getInningsPitched();

    int getWins();

    int getLosses();

    PitchingStats addEarnedRuns(int runs);

    PitchingStats addOneThirdInning();

    PitchingStats addWin();

    PitchingStats addLoss();
}
