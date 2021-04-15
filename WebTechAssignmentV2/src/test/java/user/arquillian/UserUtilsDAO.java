package user.arquillian;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserUtilsDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void deleteUserTable() {
		em.createQuery("DELETE FROM User").executeUpdate();
		em.createNativeQuery("ALTER TABLE User AUTO_INCREMENT=1").executeUpdate();
	}

}
