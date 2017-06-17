package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private List<Player> playrs;

    public Team() {}

    public Team(List<Player> players) {
        this.playrs = players;
    }

    public List<Player> getPlayers() {
        return playrs;
    }

    public void setPlayers(List<Player> players) {
        this.playrs = players;
    }
}
