package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="inventory")
public class Inventory {


  @Id
  @Column(name="inventory_id")
  private Integer inventoryId;

  @Column(name="film_id")
  private Integer filmId;

  @Column(name="store_id")
  private Integer storeId;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getInventoryId() {
    return this.inventoryId;
  }

  public void setInventoryId(Integer inventoryId) {
    this.inventoryId = inventoryId;
  }

  public Integer getFilmId() {
    return this.filmId;
  }

  public void setFilmId(Integer filmId) {
    this.filmId = filmId;
  }

  public Integer getStoreId() {
    return this.storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
