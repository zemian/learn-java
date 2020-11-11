package zemian.worldapispringdatarest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "country-languages", path = "country-languages")
public interface CountryLanguageRepository extends PagingAndSortingRepository<CountryLanguage, CountryLanguageCid> {
  @RestResource(path = "getByCid", rel = "getByCid")
  CountryLanguage getByCountryCodeAndLanguage(
      @Param("countryCode") String countryCode, @Param("language") String language);

  Page findByCountryCode(@Param("countryCode") String countryCode, Pageable pageable);

  Page findByLanguage(@Param("language") String language, Pageable pageable);

  Page findByLanguageLike(@Param("language") String language, Pageable pageable);
}
