package w.whatevera.wiffleball.domain;

import javax.persistence.*;

/**
 * Created by rich on 9/27/16.
 */
@Entity
public class PlayerPitchingStats {

    @Id
   	@GeneratedValue
   	private Long id;

    @ManyToOne
    private final Player player;
    @OneToOne
    private final PitchingStats pitchingStats;

    public PlayerPitchingStats(Player player, PitchingStats pitchingStats) {
        this.player = player;
        this.pitchingStats = pitchingStats;
    }

    public Player getPlayer() {
        return player;
    }

    public PitchingStats getPitchingStats() {
        return pitchingStats;
    }

    private PlayerPitchingStats add(PitchingStats pitchingStats) {
        return new PlayerPitchingStats(player, this.pitchingStats.add(pitchingStats));
    }

    public PlayerPitchingStats add(PlayerPitchingStats playerPitchingStats) {
        // TODO: check that playerPitchingStats.getPlayer().equals(player)
        return add(playerPitchingStats.getPitchingStats());
    }
}
