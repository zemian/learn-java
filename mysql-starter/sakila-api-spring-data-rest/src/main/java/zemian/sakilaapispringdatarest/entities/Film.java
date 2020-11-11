package zemian.sakilaapispringdatarest.entities;


import javax.persistence.*;

@Entity
@Table(name="film")
public class Film {


  @Id
  @Column(name="film_id")
  private Integer filmId;

  @Column(name="title")
  private String title;

  @Column(name="description")
  private String description;

  @Column(name="release_year")
  private String releaseYear;

  @Column(name="language_id")
  private Integer languageId;

  @Column(name="original_language_id")
  private Integer originalLanguageId;

  @Column(name="rental_duration")
  private Integer rentalDuration;

  @Column(name="rental_rate")
  private Double rentalRate;

  @Column(name="length")
  private Integer length;

  @Column(name="replacement_cost")
  private Double replacementCost;

  @Column(name="rating")
  private String rating;

  @Column(name="special_features")
  private String specialFeatures;

  @Column(name="last_update")
  private java.sql.Timestamp lastUpdate;


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

  public String getReleaseYear() {
    return this.releaseYear;
  }

  public void setReleaseYear(String releaseYear) {
    this.releaseYear = releaseYear;
  }

  public Integer getLanguageId() {
    return this.languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  public Integer getOriginalLanguageId() {
    return this.originalLanguageId;
  }

  public void setOriginalLanguageId(Integer originalLanguageId) {
    this.originalLanguageId = originalLanguageId;
  }

  public Integer getRentalDuration() {
    return this.rentalDuration;
  }

  public void setRentalDuration(Integer rentalDuration) {
    this.rentalDuration = rentalDuration;
  }

  public Double getRentalRate() {
    return this.rentalRate;
  }

  public void setRentalRate(Double rentalRate) {
    this.rentalRate = rentalRate;
  }

  public Integer getLength() {
    return this.length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Double getReplacementCost() {
    return this.replacementCost;
  }

  public void setReplacementCost(Double replacementCost) {
    this.replacementCost = replacementCost;
  }

  public String getRating() {
    return this.rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getSpecialFeatures() {
    return this.specialFeatures;
  }

  public void setSpecialFeatures(String specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public java.sql.Timestamp getLastUpdate() {
    return this.lastUpdate;
  }

  public void setLastUpdate(java.sql.Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
