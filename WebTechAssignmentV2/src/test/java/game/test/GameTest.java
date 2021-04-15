package game.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.Game;

public class GameTest {

	@Test
	public void testGameConstructor() {
		Game game = new Game();
		game.setId(1);
		game.setName("example");
		game.setReleaseDate("example");
		game.setDeveloper("example");
		game.setPublisher("example");
		game.setEngine("example");
		game.setGenre("example");
		game.setPlatform("example");
		assertEquals(1, game.getId());
		assertEquals("example", game.getName());
		assertEquals("example", game.getReleaseDate());
		assertEquals("example", game.getDeveloper());
		assertEquals("example", game.getPublisher());
		assertEquals("example", game.getEngine());
		assertEquals("example", game.getGenre());
		assertEquals("example", game.getPlatform());
	}
}
