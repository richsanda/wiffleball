package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.GameSettings;

/**
 * Created by rich on 9/4/16.
 */
public class GameSettingsImpl implements GameSettings {

    private final int numberOfPlayersPerTeam;
    private final int numberOfOutsPerInning;
    private final int numberOfInnings;

    public GameSettingsImpl(int numberOfPlayersPerTeam, int numberOfOutsPerInning, int numberOfInnings) {
        this.numberOfPlayersPerTeam = numberOfPlayersPerTeam;
        this.numberOfOutsPerInning = numberOfOutsPerInning;
        this.numberOfInnings = numberOfInnings;
    }

    public int getNumberOfPlayersPerTeam() {
        return numberOfPlayersPerTeam;
    }

    public int getNumberOfOutsPerInning() {
        return numberOfOutsPerInning;
    }

    public int getNumberOfInnings() {
        return numberOfInnings;
    }
}
