package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="actor")
public class Actor {


  @Id
  @Column(name="actor_id")
  private Integer actorId;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getActorId() {
    return this.actorId;
  }

  public void setActorId(Integer actorId) {
    this.actorId = actorId;
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

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
