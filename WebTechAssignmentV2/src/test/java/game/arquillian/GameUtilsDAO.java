package game.arquillian;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class GameUtilsDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void deleteGameTable() {
		em.createQuery("DELETE FROM Game").executeUpdate();
		em.createNativeQuery("ALTER TABLE Game AUTO_INCREMENT=1").executeUpdate();
	}

}
