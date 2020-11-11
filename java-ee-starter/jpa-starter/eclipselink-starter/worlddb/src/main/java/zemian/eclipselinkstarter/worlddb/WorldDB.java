package zemian.eclipselinkstarter.worlddb;

import javax.persistence.*;
import java.util.List;

public class WorldDB {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("world");
		EntityManager em = emf.createEntityManager();
		try {
			String sql = "SELECT e FROM Country e WHERE e.code LIKE 'U%' ORDER BY e.code ASC";
			TypedQuery<Country> query = em.createQuery(sql, Country.class);
			List<Country> records = query.getResultList();
			for (Country rec : records) {
				System.out.println(rec);
			}
		} finally {
			em.close();
			emf.close();
		}
	}

}
