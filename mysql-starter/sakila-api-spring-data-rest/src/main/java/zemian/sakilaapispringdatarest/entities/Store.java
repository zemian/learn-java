package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="store")
public class Store {


  @Id
  @Column(name="store_id")
  private Integer storeId;

  @Column(name="manager_staff_id")
  private Integer managerStaffId;

  @Column(name="address_id")
  private Integer addressId;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getStoreId() {
    return this.storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public Integer getManagerStaffId() {
    return this.managerStaffId;
  }

  public void setManagerStaffId(Integer managerStaffId) {
    this.managerStaffId = managerStaffId;
  }

  public Integer getAddressId() {
    return this.addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
