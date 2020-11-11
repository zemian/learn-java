package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="customer")
public class Customer {


  @Id
  @Column(name="customer_id")
  private Integer customerId;

  @Column(name="store_id")
  private Integer storeId;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  @Column(name="email")
  private String email;

  @Column(name="address_id")
  private Integer addressId;

  @Column(name="active")
  private Integer active;

  @Column(name="create_date")
  private java.sql.Timestamp createDate;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getStoreId() {
    return this.storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
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

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAddressId() {
    return this.addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }

  public Integer getActive() {
    return this.active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  public java.sql.Timestamp getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(java.sql.Timestamp createDate) {
    this.createDate = createDate;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
