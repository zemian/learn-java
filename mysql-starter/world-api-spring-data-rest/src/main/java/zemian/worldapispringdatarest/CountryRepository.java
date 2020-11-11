package zemian.worldapispringdatarest;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface CountryRepository extends PagingAndSortingRepository<Country, String> {
  Page findByNameLike(@Param("name") String name, Pageable pageable);
  Page findByContinent(@Param("continent") String continent, Pageable pageable);

  @Query(value = "SELECT a.* FROM country a"
      + " LEFT JOIN countrylanguage b ON a.code = b.countryCode"
      + " WHERE b.language = ?"
      , nativeQuery = true)
  List<Country> findByLanguage(@Param("language") String language);
}
