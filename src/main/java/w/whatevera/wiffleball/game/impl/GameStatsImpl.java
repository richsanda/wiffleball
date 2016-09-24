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

    @Override
    public Map<Player, BattingStats> getBattingStats() {
        return battingStats;
    }

    @Override
    public Map<Player, PitchingStats> getPitchingStats() {
        return pitchingStats;
    }

    @Override
    public BattingStats getBattingStats(Player player) {

        if (battingStats.containsKey(player)) {
            return battingStats.get(player);
        }

        BattingStats result = new BattingStatsImpl();
        battingStats.put(player, result);
        return result;
    }

    @Override
    public PitchingStats getPitchingStats(Player player) {

        if (pitchingStats.containsKey(player)) {
            return pitchingStats.get(player);
        }

        PitchingStats result = new PitchingStatsImpl();
        pitchingStats.put(player, result);
        return result;
    }

    @Override
    public GameStats add(GameStats gameStats) {

        for (Player batter : battingStats.keySet()) {
            gameStats.getBattingStats(batter).add(battingStats.get(batter));
        }
        for (Player pitcher : pitchingStats.keySet()) {
            gameStats.getPitchingStats(pitcher).add(pitchingStats.get(pitcher));
        }

        return gameStats;
    }
}
