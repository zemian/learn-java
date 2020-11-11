package zemian.worldapispringdatarest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CountryLanguageCid.class)
public class CountryLanguage {

  private Character isOfficial;
  private Double percentage;

  @Id
  @Column(nullable = false)
  private String countryCode;

  @Id
  @Column(nullable = false)
  private String language;

  public Character getOfficial() {
    return isOfficial;
  }

  public Character getIsOfficial() {
    return isOfficial;
  }

  public void setIsOfficial(Character isOfficial) {
    this.isOfficial = isOfficial;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setOfficial(Character official) {
    isOfficial = official;
  }

  public Double getPercentage() {
    return percentage;
  }

  public void setPercentage(Double percentage) {
    this.percentage = percentage;
  }
}
