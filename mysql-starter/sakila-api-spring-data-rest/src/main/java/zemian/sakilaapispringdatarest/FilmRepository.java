package zemian.sakilaapispringdatarest;

import org.springframework.data.repository.PagingAndSortingRepository;
import zemian.sakilaapispringdatarest.entities.Film;

public interface FilmRepository extends PagingAndSortingRepository<Film, Long> {
}
