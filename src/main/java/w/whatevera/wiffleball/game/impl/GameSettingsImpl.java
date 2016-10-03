package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.GameSettings;

/**
 * Created by rich on 9/4/16.
 */
public class GameSettingsImpl implements GameSettings {

    private final int numberOfFieldersPerTeam;
    private final int numberOfOutsPerInning;
    private final int numberOfInnings;

    public GameSettingsImpl(int numberOfFieldersPerTeam, int numberOfOutsPerInning, int numberOfInnings) {
        this.numberOfFieldersPerTeam = numberOfFieldersPerTeam;
        this.numberOfOutsPerInning = numberOfOutsPerInning;
        this.numberOfInnings = numberOfInnings;
    }

    public int getNumberOfFieldersPerTeam() {
        return numberOfFieldersPerTeam;
    }

    public int getNumberOfOutsPerInning() {
        return numberOfOutsPerInning;
    }

    public int getNumberOfInnings() {
        return numberOfInnings;
    }
}
