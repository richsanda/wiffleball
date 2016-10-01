package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.BaseStats;
import w.whatevera.wiffleball.game.PitchingStats;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by rich on 9/23/16.
 */
public class PitchingStatsImpl extends BaseStatsImpl<PitchingStats> implements PitchingStats {

    private static final int OUTS_PER_INNING = 3;
    private static final int INNINGS_PER_GAME = 3;

    private int oneThirdInningsPitched = 0;
    private int appearances = 0;
    private int wins = 0;
    private int losses = 0;
    private int earnedRuns = 0;

    @Override
    public int getEarnedRuns() {
        return earnedRuns;
    }

    @Override
    public BigDecimal getEarnedRunAverage() {
        if (0 == oneThirdInningsPitched) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        return new BigDecimal(earnedRuns * OUTS_PER_INNING * INNINGS_PER_GAME).setScale(2, RoundingMode.HALF_UP).divide(new BigDecimal(oneThirdInningsPitched), RoundingMode.HALF_UP);
    }

    @Override
    public int getInningsPitched() {

        return oneThirdInningsPitched / OUTS_PER_INNING;
    }

    @Override
    public int getOneThirdInningsPitched() {
        return oneThirdInningsPitched;
    }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public int getLosses() {
        return losses;
    }

    @Override
    public int getAppearances() {
        return appearances;
    }

    @Override
    public PitchingStats addEarnedRuns(int earnedRuns) {
        this.earnedRuns += earnedRuns;
        return this;
    }

    @Override
    public PitchingStats addOneThirdInning() {
        oneThirdInningsPitched++;
        return this;
    }

    @Override
    public PitchingStats addWin() {
        wins++;
        return this;
    }

    @Override
    public PitchingStats addLoss() {
        losses++;
        return this;
    }

    @Override
    public PitchingStats addAppearance() {
        appearances++;
        return null;
    }

    @Override
    public PitchingStats add(PitchingStats stats) {

        super.add((BaseStats)stats);

        oneThirdInningsPitched += stats.getOneThirdInningsPitched();
        appearances += stats.getAppearances();
        wins += stats.getWins();
        losses += stats.getLosses();
        earnedRuns += stats.getEarnedRuns();

        return this;
    }
}
