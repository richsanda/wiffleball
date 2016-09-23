package w.whatevera.wiffleball.game;

import java.util.Map;

/**
 * Created by rich on 9/23/16.
 */
public interface GameStats {

    Map<Player, BattingStats> getBattingStats();

    Map<Player, PitchingStats> getPitchingStats();
}
