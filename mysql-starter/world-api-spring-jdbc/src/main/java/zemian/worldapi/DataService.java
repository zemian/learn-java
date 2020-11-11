package zemian.worldapi;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataService {
  private JdbcTemplate jdbc;

  public DataService(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<City> findCities() {
    return jdbc.query(City.FIND_ALL, City.FIND_ALL_MAPPER);
  }
}
