package w.whatevera.wiffleball.game.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import w.whatevera.wiffleball.game.*;

import java.util.List;
import java.util.Map;

/**
 * Created by rich on 9/27/16.
 */
public class TeamStatsImpl implements TeamStats {

    private List<Player> players = Lists.newArrayList();

    private Map<Player, BattingStats> battingStats = Maps.newHashMap();
    private Map<Player, PitchingStats> pitchingStats = Maps.newHashMap();

    public TeamStatsImpl(List<Player> players) {
        this.players = players;
    }

    public TeamStatsImpl(List<Player> players, Map<Player, BattingStats> battingStats, Map<Player, PitchingStats> pitchingStats) {

        this.players = players;
        this.battingStats = battingStats;
        this.pitchingStats = pitchingStats;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public List<PlayerBattingStats> getPlayerBattingStats() {

        List<PlayerBattingStats> result = Lists.newArrayList();

        for (Player player : players) {
            result.add(new PlayerBattingStatsImpl(player, battingStats.get(player)));
        }

        return result;
    }

    @Override
    public List<PlayerPitchingStats> getPlayerPitchingStats() {

        List<PlayerPitchingStats> result = Lists.newArrayList();

        for (Player player : players) {
            result.add(new PlayerPitchingStatsImpl(player, pitchingStats.get(player)));
        }

        return result;
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
    public BattingStats getBattingStats() {

        BattingStats battingStats = new BattingStatsImpl();

        for (Player player : players) {
            battingStats = battingStats.add(this.battingStats.get(player));
        }

        return battingStats;
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
    public TeamStats add(TeamStats teamStats) {

        Map<Player, BattingStats> battingStats = Maps.newHashMap();
        Map<Player, PitchingStats> pitchingStats = Maps.newHashMap();

        for (Player player : players) {
            battingStats.put(player, getBattingStats(player).add(teamStats.getBattingStats(player)));
            pitchingStats.put(player, getPitchingStats(player).add(teamStats.getPitchingStats(player)));
        }

        return new TeamStatsImpl(players, battingStats, pitchingStats);
    }

    @Override
    public PitchingStats getPitchingStats() {

        PitchingStats pitchingStats = new PitchingStatsImpl();

        for (Player player : players) {
            pitchingStats = pitchingStats.add(this.pitchingStats.get(player));
        }

        return pitchingStats;
    }
}
