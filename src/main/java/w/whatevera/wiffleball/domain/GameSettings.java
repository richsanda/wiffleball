package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 9/4/16.
 */
@Entity
public class GameSettings {

    @Id
   	@GeneratedValue
   	private Long id;

    private int numberOfFieldersPerTeam;
    private int numberOfOutsPerInning;
    private int numberOfInnings;

    public GameSettings() {}

    public GameSettings(int numberOfFieldersPerTeam, int numberOfOutsPerInning, int numberOfInnings) {
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
