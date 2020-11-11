package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="category")
public class Category {


  @Id
  @Column(name="category_id")
  private Integer categoryId;

  @Column(name="name")
  private String name;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
