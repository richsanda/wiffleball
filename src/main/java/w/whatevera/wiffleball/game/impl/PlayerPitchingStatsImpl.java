package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.*;

/**
 * Created by rich on 9/27/16.
 */
public class PlayerPitchingStatsImpl implements PlayerPitchingStats {

    private final Player player;
    private final PitchingStats pitchingStats;

    public PlayerPitchingStatsImpl(Player player, PitchingStats pitchingStats) {
        this.player = player;
        this.pitchingStats = pitchingStats;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PitchingStats getPitchingStats() {
        return pitchingStats;
    }

    private PlayerPitchingStats add(PitchingStats pitchingStats) {
        return new PlayerPitchingStatsImpl(player, this.pitchingStats.add(pitchingStats));
    }

    @Override
    public PlayerPitchingStats add(PlayerPitchingStats playerPitchingStats) {
        // TODO: check that playerPitchingStats.getPlayer().equals(player)
        return add(playerPitchingStats.getPitchingStats());
    }
}
