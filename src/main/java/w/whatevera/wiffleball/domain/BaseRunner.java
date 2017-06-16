package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class BaseRunner {

    @Id
   	@GeneratedValue
   	private Long id;

    @ManyToOne
    private Player runner;
    @ManyToOne
    private Player pitcher;

    public BaseRunner(Player runner, Player pitcher) {
        this.runner = runner;
        this.pitcher = pitcher;
    }

    public Player getRunner() {
        return runner;
    }

    public Player getPitcher() {
        return pitcher;
    }
}
