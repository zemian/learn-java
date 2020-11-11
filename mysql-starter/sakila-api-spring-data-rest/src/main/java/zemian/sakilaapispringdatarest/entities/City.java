package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="city")
public class City {


  @Id
  @Column(name="city_id")
  private Integer cityId;

  @Column(name="city")
  private String city;

  @Column(name="country_id")
  private Integer countryId;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getCityId() {
    return this.cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getCountryId() {
    return this.countryId;
  }

  public void setCountryId(Integer countryId) {
    this.countryId = countryId;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
