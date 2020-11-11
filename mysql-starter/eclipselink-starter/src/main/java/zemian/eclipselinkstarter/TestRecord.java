package zemian.eclipselinkstarter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class TestRecord {
    @Id
    private Long id;
    private String cat;
    private Double price;

    public String toString() {
        return "TestRecord{" + id + ", " + cat + ", " + price + "}";
    }
}
