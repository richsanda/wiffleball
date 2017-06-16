package w.whatevera.wiffleball.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import w.whatevera.wiffleball.domain.GameSettings;

/**
 * Created by rich on 6/16/17.
 */
@Component
public interface GameSettingsRepository extends PagingAndSortingRepository<GameSettings, Long> {

}
