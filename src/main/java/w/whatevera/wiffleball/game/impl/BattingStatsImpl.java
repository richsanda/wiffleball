package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.BaseStats;
import w.whatevera.wiffleball.game.BattingStats;

/**
 * Created by rich on 9/23/16.
 */
public class BattingStatsImpl extends BaseStatsImpl<BattingStats> implements BattingStats {

    @Override
    public BattingStats add(BattingStats stats) {
        super.add((BaseStats)stats);
        return this;
    }
}
