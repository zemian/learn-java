package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="language")
public class Language {


  @Id
  @Column(name="language_id")
  private Integer languageId;

  @Column(name="name")
  private String name;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


  public Integer getLanguageId() {
    return this.languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
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
