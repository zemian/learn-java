package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="address")
public class Address {


  @Id
  @Column(name="address_id")
  private Integer addressId;

  @Column(name="address")
  private String address;

  @Column(name="address2")
  private String address2;

  @Column(name="district")
  private String district;

  @Column(name="city_id")
  private Integer cityId;

  @Column(name="postal_code")
  private String postalCode;

  @Column(name="phone")
  private String phone;

  @Column(name="location")
  private String location;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getAddressId() {
    return this.addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress2() {
    return this.address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getDistrict() {
    return this.district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public Integer getCityId() {
    return this.cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
