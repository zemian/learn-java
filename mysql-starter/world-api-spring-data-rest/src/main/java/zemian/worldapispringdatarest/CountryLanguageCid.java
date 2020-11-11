package zemian.worldapispringdatarest;

import java.io.Serializable;
import java.util.Objects;

/*
Composite Keys Implementation: JPA supports two ways

1) Using @Embeddable on the KeyClass and then use @EmbeddedId in the Entity
2) Using POJO as KeyClass and then use @IdClass and "repeated" @Id in the Entity

The Embeddable composite key affect the HQL structure since you need to write
out the full object path to get to the key field.

The IdClass avoided this, but you would need to create duplicated key fields in
separated class.

NOTE: Composite key class should implements Serializable, equals() and hashCode().
*/
public class CountryLanguageCid implements Serializable {
  private static final long serialVersionUID = 1L;

  private String countryCode;
  private String language;

  public String getCountryCode() {
    return countryCode;
  }

  public String getLanguage() {
    return language;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "CountryLanguageCid{" +
        "countryCode='" + countryCode + '\'' +
        ", language='" + language + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CountryLanguageCid)) {
      return false;
    }
    CountryLanguageCid cid = (CountryLanguageCid) o;
    return countryCode.equals(cid.countryCode) &&
        language.equals(cid.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countryCode, language);
  }
}
