package user;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UserDAO {

	@PersistenceContext
	private EntityManager em;

	public List<User> findAllUsers() {
		Query query = em.createQuery("SELECT u FROM User u");
		return query.getResultList();
	}

	public User getUserByUsername(String username) {
		return em.find(User.class, username);
	}

	public void saveNewUser(User user) {
		em.persist(user);
	}

	public void updateUserDetails(User user) {
		em.merge(user);
	}

	public void delete(String username) {
		em.remove(getUserByUsername(username));
	}

}
