package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.BaseStats;
import w.whatevera.wiffleball.game.PitchingStats;

import java.math.BigDecimal;

/**
 * Created by rich on 9/23/16.
 */
public class PitchingStatsImpl extends BaseStatsImpl<PitchingStats> implements PitchingStats {

    @Override
    public int getEarnedRuns() {
        return 0;
    }

    @Override
    public BigDecimal getEarnedRunAverage() {
        return null;
    }

    @Override
    public int getInningsPitched() {
        return 0;
    }

    @Override
    public int getWins() {
        return 0;
    }

    @Override
    public int getLosses() {
        return 0;
    }

    @Override
    public PitchingStats addEarnedRuns(int runs) {
        return this;
    }

    @Override
    public PitchingStats addOneThirdInning() {
        return this;
    }

    @Override
    public PitchingStats addWin() {
        return this;
    }

    @Override
    public PitchingStats addLoss() {
        return this;
    }

    @Override
    public PitchingStats add(PitchingStats stats) {
        super.add((BaseStats)stats);
        return this;
    }
}
