package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="film_category")
@IdClass(FilmCategory.class)
public class FilmCategory implements java.io.Serializable {


  @Id
  @Column(name="film_id")
  private Integer filmId;

  @Id
  @Column(name="category_id")
  private Integer categoryId;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getFilmId() {
    return this.filmId;
  }

  public void setFilmId(Integer filmId) {
    this.filmId = filmId;
  }

  public Integer getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
