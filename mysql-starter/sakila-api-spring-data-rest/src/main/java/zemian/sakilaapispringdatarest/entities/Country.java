package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="country")
public class Country {


  @Id
  @Column(name="country_id")
  private Integer countryId;

  @Column(name="country")
  private String country;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getCountryId() {
    return this.countryId;
  }

  public void setCountryId(Integer countryId) {
    this.countryId = countryId;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
