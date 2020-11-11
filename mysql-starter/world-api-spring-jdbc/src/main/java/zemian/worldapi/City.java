package zemian.worldapi;

import org.springframework.jdbc.core.RowMapper;

public class City {
  public static final String FIND_ALL = "SELECT * FROM CITY ORDER BY CountryCode, Name LIMIT 25";
  public static RowMapper<City> FIND_ALL_MAPPER = (rs, rowNum) -> {
    City ret = new City();
    ret.setCountryCode(rs.getString("CountryCode"));
    ret.setDistrict(rs.getString("District"));
    ret.setId(rs.getInt("ID"));
    ret.setName(rs.getString("Name"));
    ret.setPopulation(rs.getInt("Population"));
    return ret;
  };

  private int id;
  private String name;
  private String countryCode;
  private String district;
  private int population;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public int getPopulation() {
    return population;
  }

  public void setPopulation(int population) {
    this.population = population;
  }
}
