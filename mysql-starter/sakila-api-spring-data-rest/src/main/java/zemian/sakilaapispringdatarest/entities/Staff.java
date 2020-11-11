package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="staff")
public class Staff {


  @Id
  @Column(name="staff_id")
  private Integer staffId;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  @Column(name="address_id")
  private Integer addressId;

  @Column(name="picture")
  private String picture;

  @Column(name="email")
  private String email;

  @Column(name="store_id")
  private Integer storeId;

  @Column(name="active")
  private Integer active;

  @Column(name="username")
  private String username;

  @Column(name="password")
  private String password;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getStaffId() {
    return this.staffId;
  }

  public void setStaffId(Integer staffId) {
    this.staffId = staffId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getAddressId() {
    return this.addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }

  public String getPicture() {
    return this.picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getStoreId() {
    return this.storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public Integer getActive() {
    return this.active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
