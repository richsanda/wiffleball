package w.whatevera.wiffleball.game.impl;

import com.google.common.collect.Maps;
import w.whatevera.wiffleball.game.*;

import java.util.Map;

/**
 * Created by rich on 9/23/16.
 */
public class GameStatsImpl implements GameStats {

    private Map<Player, BattingStats> battingStats = Maps.newHashMap();
    private Map<Player, PitchingStats> pitchingStats = Maps.newHashMap();

    public GameStatsImpl(Map<Player, BattingStats> battingStats, Map<Player, PitchingStats> pitchingStats) {
        this.battingStats = battingStats;
        this.pitchingStats = pitchingStats;
    }

    @Override
    public Map<Player, BattingStats> getBattingStats() {
        return battingStats;
    }

    @Override
    public Map<Player, PitchingStats> getPitchingStats() {
        return pitchingStats;
    }
}
