package w.whatevera.wiffleball.game;

/**
 * Created by rich on 9/27/16.
 */
public interface PlayerPitchingStats {

    Player getPlayer();

    PitchingStats getPitchingStats();

    PlayerPitchingStats add(PlayerPitchingStats pitchingStats);
}
