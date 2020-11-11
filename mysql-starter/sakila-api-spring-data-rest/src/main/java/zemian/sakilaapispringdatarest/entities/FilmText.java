package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="film_text")
public class FilmText {


  @Id
  @Column(name="film_id")
  private Integer filmId;

  @Column(name="title")
  private String title;

  @Column(name="description")
  private String description;


  public Integer getFilmId() {
    return this.filmId;
  }

  public void setFilmId(Integer filmId) {
    this.filmId = filmId;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
