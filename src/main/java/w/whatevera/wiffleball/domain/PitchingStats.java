package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class PitchingStats implements w.whatevera.wiffleball.game.PitchingStats {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public int getGames() {
        return 0;
    }

    @Override
    public int getPlateAppearances() {
        return 0;
    }

    @Override
    public int getAtBats() {
        return 0;
    }

    @Override
    public int getWalks() {
        return 0;
    }

    @Override
    public int getHits() {
        return 0;
    }

    @Override
    public int getSingles() {
        return 0;
    }

    @Override
    public int getDoubles() {
        return 0;
    }

    @Override
    public int getTriples() {
        return 0;
    }

    @Override
    public int getHomeRuns() {
        return 0;
    }

    @Override
    public int getExtraBaseHits() {
        return 0;
    }

    @Override
    public int getTotalBases() {
        return 0;
    }

    @Override
    public int getStrikeouts() {
        return 0;
    }

    @Override
    public BigDecimal getBattingAverage() {
        return null;
    }

    @Override
    public BigDecimal getSluggingPercentage() {
        return null;
    }

    @Override
    public BigDecimal getOnBasePercentage() {
        return null;
    }

    @Override
    public BigDecimal getOnBasePlusSlugging() {
        return null;
    }

    @Override
    public int getRuns() {
        return 0;
    }

    @Override
    public int getRunsBattedIn() {
        return 0;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addGame() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addPlateAppearance() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addAtBat() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addWalk() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addHit() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addDouble() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addTriple() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addHomeRun() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addStrikeout() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addRuns(int runs) {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addRunsBattedIn(int runsBattedIn) {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats add(w.whatevera.wiffleball.game.PitchingStats addition) {
        return null;
    }

    @Override
    public int getEarnedRuns() {
        return 0;
    }

    @Override
    public BigDecimal getEarnedRunAverage() {
        return null;
    }

    @Override
    public int getOneThirdInningsPitched() {
        return 0;
    }

    @Override
    public int getInningsPitched() {
        return 0;
    }

    @Override
    public int getInningsPitchedRemainder() {
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
    public int getAppearances() {
        return 0;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addEarnedRuns(int runs) {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addOneThirdInning() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addWin() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addLoss() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.PitchingStats addAppearance() {
        return null;
    }
}
