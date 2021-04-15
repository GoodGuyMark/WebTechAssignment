package game.arquillian;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import game.Game;
import game.GameDAO;
import game.GameWS;
import rest.RestApplication;

@RunWith(Arquillian.class)
public class GamesIntegrationTest {

	@Deployment
	public static Archive<?> createTestArchive(){
		return ShrinkWrap.create(JavaArchive.class, "Test.jar")
				.addClasses(GameUtilsDAO.class, Game.class, GameDAO.class, GameWS.class, RestApplication.class)
				.addAsResource("META-INF/persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private GameDAO gameDao;
	
	@EJB 
	private GameWS gameWS;
	
	@EJB
	private GameUtilsDAO gameUtilsDao;
	
	@Before
	public void setup() {
		gameUtilsDao.deleteGameTable();
		Game game = new Game();
		game.setName("Rocket League");
		game.setReleaseDate("07/07/15");
		game.setDeveloper("Psyonix");
		game.setPublisher("Psyonix");
		game.setEngine("Unreal Engine 3");
		game.setGenre("Sports");
		game.setPlatform("Windows, PS4, Xbox One, macOS, Linux, Nintendo Switch");
		game.setBoxArt("rl.jpg");
		gameDao.saveNewGame(game);
		Game game2 = new Game();
		game2.setName("test");
		game2.setReleaseDate("test");
		game2.setDeveloper("test");
		game2.setPublisher("test");
		game2.setEngine("test");
		game2.setGenre("test");
		game2.setPlatform("test");
		game2.setBoxArt("test");
		gameDao.saveNewGame(game2);
	}
	
	@Test
	public void testFindAllGames() {
		List<Game> games = gameDao.findAllGames();
		assertEquals("Data fetch = data persisted", games.size(), 2);
	}

	@Test
	public void testFindGameById() {
		Game game = gameDao.findGameById(1);
		assertEquals(game.getId(), 1);
		assertEquals(game.getName(), "Rocket League");
		assertEquals(game.getReleaseDate(), "07/07/15");
		assertEquals(game.getDeveloper(), "Psyonix");
		assertEquals(game.getPublisher(), "Psyonix");
		assertEquals(game.getEngine(), "Unreal Engine 3");
		assertEquals(game.getGenre(), "Sports");
		assertEquals(game.getPlatform(), "Windows, PS4, Xbox One, macOS, Linux, Nintendo Switch");
		assertEquals(game.getBoxArt(), "rl.jpg");
	}
	
	@Test
	public void testAddGame() {
		Game game = new Game();
		game.setName("Dragon Quest XI");
		game.setReleaseDate("29/07/18");
		game.setDeveloper("Square Enix");
		game.setPublisher("Square Enix");
		game.setEngine("Unreal Engine 4");
		game.setGenre("Role-Playing");
		game.setPlatform("Nintendo 3DS, PS4, Windows, Nintendo Switch");
		game.setBoxArt("dq11.jpg");
		gameDao.saveNewGame(game);
		List<Game> games = gameDao.findAllGames();
		assertEquals(games.size(), 3);
		assertEquals(game.getId(), 3);
		assertEquals(game.getName(), "Dragon Quest XI");
		assertEquals(game.getReleaseDate(), "29/07/18");
		assertEquals(game.getDeveloper(), "Square Enix");
		assertEquals(game.getPublisher(), "Square Enix");
		assertEquals(game.getEngine(), "Unreal Engine 4");
		assertEquals(game.getGenre(), "Role-Playing");
		assertEquals(game.getPlatform(), "Nintendo 3DS, PS4, Windows, Nintendo Switch");
		assertEquals(game.getBoxArt(), "dq11.jpg");
	}
	
	@Test
	public void testDeleteGame() {
		List<Game> games = gameDao.findAllGames();
		assertEquals(games.size(), 2);
		gameDao.delete(2);
		games = gameDao.findAllGames();
		assertEquals(games.size(), 1);
		assertEquals(null, gameDao.findGameById(2));
	}
	
	@Test
	public void testUpdateGame() {
		Game game = gameDao.findGameById(1);
		game.setDeveloper("update");
		game.setReleaseDate("2019");
		game.setPlatform("xbox");
		gameDao.updateGameDetails(game);
		gameDao.findGameById(1);
		assertEquals(game.getId(), 1);
		assertEquals(game.getName(), "Rocket League");
		assertEquals(game.getReleaseDate(), "2019");
		assertEquals(game.getDeveloper(), "update");
		assertEquals(game.getPublisher(), "Psyonix");
		assertEquals(game.getEngine(), "Unreal Engine 3");
		assertEquals(game.getGenre(), "Sports");
		assertEquals(game.getPlatform(), "xbox");
		assertEquals(game.getBoxArt(), "rl.jpg");
	}
	
	@Test
	public void testGetAllGamesWS() {
	Response response = gameWS.getAllGames();
	List<Game> games = (List<Game>) response.getEntity();
	assertEquals(HttpStatus.SC_OK, response.getStatus());
	assertEquals("Data fetch = data persisted", games.size(), 2);
	Game game = games.get(0);
	assertEquals("Rocket League", game.getName());
	game = games.get(1);
	assertEquals("test", game.getName());
	} 
	
	@Test
	public void testGameById() {
		Response response = gameWS.getGameById(1);
		Game game = (Game) response.getEntity();
		assertEquals(game.getId(), 1);
		assertEquals(game.getName(), "Rocket League");
		assertEquals(game.getReleaseDate(), "07/07/15");
		assertEquals(game.getDeveloper(), "Psyonix");
		assertEquals(game.getPublisher(), "Psyonix");
		assertEquals(game.getEngine(), "Unreal Engine 3");
		assertEquals(game.getGenre(), "Sports");
		assertEquals(game.getPlatform(), "Windows, PS4, Xbox One, macOS, Linux, Nintendo Switch");
		assertEquals(game.getBoxArt(), "rl.jpg");
	}
	
	@Test
	public void testAddGameWS() {
		Game game = new Game();
		game.setName("Dragon Quest XI");
		game.setReleaseDate("29/07/18");
		game.setDeveloper("Square Enix");
		game.setPublisher("Square Enix");
		game.setEngine("Unreal Engine 4");
		game.setGenre("Role-Playing");
		game.setPlatform("Nintendo 3DS, PS4, Windows, Nintendo Switch");
		game.setBoxArt("dq11.jpg");
		Response response = gameWS.createNewGame(game);
		assertEquals(HttpStatus.SC_CREATED, response.getStatus());
		game = (Game) response.getEntity();
		assertEquals(game.getId(), 3);
		assertEquals(game.getName(), "Dragon Quest XI");
		assertEquals(game.getReleaseDate(), "29/07/18");
		assertEquals(game.getDeveloper(), "Square Enix");
		assertEquals(game.getPublisher(), "Square Enix");
		assertEquals(game.getEngine(), "Unreal Engine 4");
		assertEquals(game.getGenre(), "Role-Playing");
		assertEquals(game.getPlatform(), "Nintendo 3DS, PS4, Windows, Nintendo Switch");
		assertEquals(game.getBoxArt(), "dq11.jpg");
	}
	
	@Test
	public void testRemoveGame() {
		Response response = gameWS.getAllGames();
		List<Game> gameList = (List<Game>) response.getEntity();
		assertEquals(gameList.size(), 2);
		gameWS.deleteGame(2);
		response = gameWS.getAllGames();
		gameList = (List<Game>) response.getEntity();
		assertEquals(gameList.size(), 1);
	
		
	}
	
	@Test
	public void testUpdateGameWS() {
		Response response = gameWS.getGameById(1);
		Game game = (Game) response.getEntity();
		game.setDeveloper("update");
		game.setReleaseDate("2019");
		game.setPlatform("xbox");
		response = gameWS.updateGame(game);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		game = (Game) response.getEntity();
		assertEquals(game.getId(), 1);
		assertEquals(game.getName(), "Rocket League");
		assertEquals(game.getReleaseDate(), "2019");
		assertEquals(game.getDeveloper(), "update");
		assertEquals(game.getPublisher(), "Psyonix");
		assertEquals(game.getEngine(), "Unreal Engine 3");
		assertEquals(game.getGenre(), "Sports");
		assertEquals(game.getPlatform(), "xbox");
		assertEquals(game.getBoxArt(), "rl.jpg");
	}
	
	@Test
	public void testGameCount() {
		int count = gameWS.countGames();
		assertEquals(2, count);
	}
	
}
