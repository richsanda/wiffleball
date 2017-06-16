package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 6/16/17.
 */
@Entity
public class Player implements w.whatevera.wiffleball.game.Player {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
