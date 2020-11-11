package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="rental")
public class Rental {


  @Id
  @Column(name="rental_id")
  private Integer rentalId;

  @Column(name="rental_date")
  private java.sql.Timestamp rentalDate;

  @Column(name="inventory_id")
  private Integer inventoryId;

  @Column(name="customer_id")
  private Integer customerId;

  @Column(name="return_date")
  private java.sql.Timestamp returnDate;

  @Column(name="staff_id")
  private Integer staffId;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getRentalId() {
    return this.rentalId;
  }

  public void setRentalId(Integer rentalId) {
    this.rentalId = rentalId;
  }

  public java.sql.Timestamp getRentalDate() {
    return this.rentalDate;
  }

  public void setRentalDate(java.sql.Timestamp rentalDate) {
    this.rentalDate = rentalDate;
  }

  public Integer getInventoryId() {
    return this.inventoryId;
  }

  public void setInventoryId(Integer inventoryId) {
    this.inventoryId = inventoryId;
  }

  public Integer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public java.sql.Timestamp getReturnDate() {
    return this.returnDate;
  }

  public void setReturnDate(java.sql.Timestamp returnDate) {
    this.returnDate = returnDate;
  }

  public Integer getStaffId() {
    return this.staffId;
  }

  public void setStaffId(Integer staffId) {
    this.staffId = staffId;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
