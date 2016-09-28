package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.BattingStats;
import w.whatevera.wiffleball.game.Player;
import w.whatevera.wiffleball.game.PlayerBattingStats;

/**
 * Created by rich on 9/27/16.
 */
public class PlayerBattingStatsImpl implements PlayerBattingStats {

    private final Player player;
    private final BattingStats battingStats;

    public PlayerBattingStatsImpl(Player player, BattingStats battingStats) {
        this.player = player;
        this.battingStats = battingStats;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public BattingStats getBattingStats() {
        return battingStats;
    }

    private PlayerBattingStats add(BattingStats battingStats) {
        return new PlayerBattingStatsImpl(player, this.battingStats.add(battingStats));
    }

    @Override
    public PlayerBattingStats add(PlayerBattingStats playerBattingStats) {
        // TODO: check that playerBattingStats.getPlayer().equals(player)
        return add(playerBattingStats.getBattingStats());
    }
}
