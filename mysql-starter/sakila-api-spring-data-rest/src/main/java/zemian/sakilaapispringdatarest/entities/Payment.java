package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="payment")
public class Payment {


  @Id
  @Column(name="payment_id")
  private Integer paymentId;

  @Column(name="customer_id")
  private Integer customerId;

  @Column(name="staff_id")
  private Integer staffId;

  @Column(name="rental_id")
  private Integer rentalId;

  @Column(name="amount")
  private Double amount;

  @Column(name="payment_date")
  private java.sql.Timestamp paymentDate;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getPaymentId() {
    return this.paymentId;
  }

  public void setPaymentId(Integer paymentId) {
    this.paymentId = paymentId;
  }

  public Integer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getStaffId() {
    return this.staffId;
  }

  public void setStaffId(Integer staffId) {
    this.staffId = staffId;
  }

  public Integer getRentalId() {
    return this.rentalId;
  }

  public void setRentalId(Integer rentalId) {
    this.rentalId = rentalId;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public java.sql.Timestamp getPaymentDate() {
    return this.paymentDate;
  }

  public void setPaymentDate(java.sql.Timestamp paymentDate) {
    this.paymentDate = paymentDate;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
