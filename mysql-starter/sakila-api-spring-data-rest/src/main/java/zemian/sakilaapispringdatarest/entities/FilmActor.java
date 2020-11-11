package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="film_actor")
@IdClass(FilmActor.class)
public class FilmActor implements java.io.Serializable {


  @Id
  @Column(name="actor_id")
  private Integer actorId;

  @Id
  @Column(name="film_id")
  private Integer filmId;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getActorId() {
    return this.actorId;
  }

  public void setActorId(Integer actorId) {
    this.actorId = actorId;
  }

  public Integer getFilmId() {
    return this.filmId;
  }

  public void setFilmId(Integer filmId) {
    this.filmId = filmId;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
