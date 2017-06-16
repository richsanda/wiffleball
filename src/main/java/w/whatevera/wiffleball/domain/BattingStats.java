package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class BattingStats implements w.whatevera.wiffleball.game.BattingStats {

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
    public w.whatevera.wiffleball.game.BattingStats addGame() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addPlateAppearance() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addAtBat() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addWalk() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addHit() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addDouble() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addTriple() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addHomeRun() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addStrikeout() {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addRuns(int runs) {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats addRunsBattedIn(int runsBattedIn) {
        return null;
    }

    @Override
    public w.whatevera.wiffleball.game.BattingStats add(w.whatevera.wiffleball.game.BattingStats addition) {
        return null;
    }
}
