package w.whatevera.wiffleball.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by rich on 9/27/16.
 */
@Entity
public class TeamStats {

    @Id
   	@GeneratedValue
   	private Long id;

    @ManyToMany
    private List<Player> players = Lists.newArrayList();

    @ElementCollection
    //@CollectionTable(name="<name_of_join_table>")
    //@MapKeyColumn(name="<name_of_map_key_in_table>")
    private Map<Player, BattingStats> battingStats = Maps.newHashMap();
    @ElementCollection
    private Map<Player, PitchingStats> pitchingStats = Maps.newHashMap();

    public TeamStats(Team team) {
        this.players = Lists.newArrayList(team.getPlayers());
    }

    public TeamStats(List<Player> players, Map<Player, BattingStats> battingStats, Map<Player, PitchingStats> pitchingStats) {

        this.players = players;
        this.battingStats = battingStats;
        this.pitchingStats = pitchingStats;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<PlayerBattingStats> getPlayerBattingStats() {

        List<PlayerBattingStats> result = Lists.newArrayList();

        for (Player player : players) {
            result.add(new PlayerBattingStats(player, battingStats.get(player)));
        }

        return result;
    }

    public List<PlayerPitchingStats> getPlayerPitchingStats() {

        List<PlayerPitchingStats> result = Lists.newArrayList();

        for (Player player : players) {
            result.add(new PlayerPitchingStats(player, pitchingStats.get(player)));
        }

        return result;
    }

    public BattingStats getBattingStats(Player player) {

        if (battingStats.containsKey(player)) {
            return battingStats.get(player);
        }

        BattingStats result = new BattingStats();
        battingStats.put(player, result);
        return result;
    }

    public BattingStats getBattingStats() {

        BattingStats battingStats = new BattingStats();

        for (Player player : players) {
            battingStats = battingStats.add(this.battingStats.get(player));
        }

        return battingStats;
    }

    public PitchingStats getPitchingStats(Player player) {

        if (pitchingStats.containsKey(player)) {
            return pitchingStats.get(player);
        }

        PitchingStats result = new PitchingStats();
        pitchingStats.put(player, result);
        return result;
    }

    public TeamStats add(TeamStats teamStats) {

        Map<Player, BattingStats> battingStats = Maps.newHashMap();
        Map<Player, PitchingStats> pitchingStats = Maps.newHashMap();

        for (Player player : players) {
            battingStats.put(player, getBattingStats(player).add(teamStats.getBattingStats(player)));
            pitchingStats.put(player, getPitchingStats(player).add(teamStats.getPitchingStats(player)));
        }

        return new TeamStats(players, battingStats, pitchingStats);
    }

    public PitchingStats getPitchingStats() {

        PitchingStats pitchingStats = new PitchingStats();

        for (Player player : players) {
            pitchingStats = pitchingStats.add(this.pitchingStats.get(player));
        }

        return pitchingStats;
    }
}
