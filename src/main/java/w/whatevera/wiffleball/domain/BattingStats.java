package w.whatevera.wiffleball.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rich on 9/23/16.
 */
@Entity
public class BattingStats extends BaseStats<BattingStats> {

    @Id
   	@GeneratedValue
   	private Long id;

    public BattingStats add(BattingStats stats) {
        super.add((BaseStats)stats);
        return this;
    }
}
