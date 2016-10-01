package w.whatevera.wiffleball.game;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public interface PitchingStats extends BaseStats<PitchingStats> {

    int getEarnedRuns();

    BigDecimal getEarnedRunAverage();

    int getOneThirdInningsPitched();

    int getInningsPitched();

    int getInningsPitchedRemainder();

    int getWins();

    int getLosses();

    int getAppearances();

    PitchingStats addEarnedRuns(int runs);

    PitchingStats addOneThirdInning();

    PitchingStats addWin();

    PitchingStats addLoss();

    PitchingStats addAppearance();
}
