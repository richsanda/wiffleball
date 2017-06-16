package w.whatevera.wiffleball.domain;

import javax.persistence.*;

/**
 * Created by rich on 9/27/16.
 */
@Entity
public class PlayerBattingStats {

    @Id
   	@GeneratedValue
   	private Long id;

    @ManyToOne
    private final Player player;
    @ManyToOne
    private final BattingStats battingStats;

    public PlayerBattingStats(Player player, BattingStats battingStats) {
        this.player = player;
        this.battingStats = battingStats;
    }

    public Player getPlayer() {
        return player;
    }

    public BattingStats getBattingStats() {
        return battingStats;
    }

    private PlayerBattingStats add(BattingStats battingStats) {
        return new PlayerBattingStats(player, this.battingStats.add(battingStats));
    }

    public PlayerBattingStats add(PlayerBattingStats playerBattingStats) {
        // TODO: check that playerBattingStats.getPlayer().equals(player)
        return add(playerBattingStats.getBattingStats());
    }
}
