package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class PitchingStats extends BaseStats<PitchingStats> {

    @Id
   	@GeneratedValue
   	private Long id;

    private static final int OUTS_PER_INNING = 3;
    private static final int INNINGS_PER_GAME = 3;

    private static final BigDecimal MAX_ERA = new BigDecimal(99.99).setScale(2, RoundingMode.HALF_UP);

    private int oneThirdInningsPitched = 0;
    private int appearances = 0;
    private int wins = 0;
    private int losses = 0;
    private int earnedRuns = 0;

    public int getEarnedRuns() {
        return earnedRuns;
    }

    public BigDecimal getEarnedRunAverage() {
        if (earnedRuns == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        } else if (0 == oneThirdInningsPitched) {
            return MAX_ERA;
        }
        return new BigDecimal(earnedRuns * OUTS_PER_INNING * INNINGS_PER_GAME)
                .setScale(2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(oneThirdInningsPitched), RoundingMode.HALF_UP)
                .min(MAX_ERA);
    }

    public int getInningsPitched() {

        return oneThirdInningsPitched / OUTS_PER_INNING;
    }

    public int getInningsPitchedRemainder() {

        return oneThirdInningsPitched % OUTS_PER_INNING;
    }

    public int getOneThirdInningsPitched() {
        return oneThirdInningsPitched;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getAppearances() {
        return appearances;
    }

    public PitchingStats addEarnedRuns(int earnedRuns) {
        this.earnedRuns += earnedRuns;
        return this;
    }

    public PitchingStats addOneThirdInning() {
        oneThirdInningsPitched++;
        return this;
    }

    public PitchingStats addWin() {
        wins++;
        return this;
    }

    public PitchingStats addLoss() {
        losses++;
        return this;
    }

    public PitchingStats addAppearance() {
        appearances++;
        return null;
    }

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
