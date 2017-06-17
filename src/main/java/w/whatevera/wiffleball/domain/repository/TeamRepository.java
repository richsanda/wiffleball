package w.whatevera.wiffleball.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import w.whatevera.wiffleball.domain.Team;

/**
 * Created by rich on 6/16/17.
 */
public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
}
