package w.whatevera.wiffleball.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import w.whatevera.wiffleball.domain.Game;

import java.util.List;

/**
 * Created by rich on 6/16/17.
 */
@Component
public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    public List<Game> findAll();
}
