package zemian.eclipselinkstarter;

import java.util.*;
import javax.persistence.*;

public class TestRecordMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("zemiandb");
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "SELECT e FROM TestRecord e";
            TypedQuery<TestRecord> query = em.createQuery(sql, TestRecord.class);
            List<TestRecord> records = query.getResultList();
            for (TestRecord rec : records) {
                System.out.println(rec);
            }            
        } finally {
            em.close();
            emf.close();
        }
    }
}