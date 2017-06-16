package w.whatevera.wiffleball.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import w.whatevera.wiffleball.domain.BaseRunner;

/**
 * Created by rich on 6/16/17.
 */
public interface BaseRunnerRepository extends PagingAndSortingRepository<BaseRunner, Long> {
}
