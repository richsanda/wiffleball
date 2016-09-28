package w.whatevera.wiffleball.game;

import java.util.List;

/**
 * Created by rich on 9/27/16.
 */
public interface TeamStats {

    List<Player> getPlayers();

    List<PlayerBattingStats> getPlayerBattingStats();

    BattingStats getBattingStats();

    BattingStats getBattingStats(Player player);

    List<PlayerPitchingStats> getPlayerPitchingStats();

    PitchingStats getPitchingStats();

    PitchingStats getPitchingStats(Player player);

    TeamStats add(TeamStats teamStats);
}
