package game;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class GameDAO {

	@PersistenceContext
	private EntityManager em;
	
	public List<Game> findAllGames() {
		Query query = em.createQuery("SELECT g FROM Game g");
		return query.getResultList();
	}
	
	public Game findGameById(int id) {
		return em.find(Game.class, id);
	}
	
	public void saveNewGame(Game game) {
		em.persist(game);
	}
	
	public void updateGameDetails(Game game) {
		em.merge(game);
	}
	
	public void delete(int id) {
		em.remove(findGameById(id));
	}

}
