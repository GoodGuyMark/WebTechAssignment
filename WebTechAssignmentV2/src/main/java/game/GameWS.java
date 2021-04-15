package game;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/games")
@Stateless
@LocalBean
public class GameWS {

	@EJB 
	private GameDAO gameDao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllGames() {
		List<Game> games = gameDao.findAllGames();
		return Response.status(200).entity(games).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getGameById(@PathParam("id") int id) {
		Game game = gameDao.findGameById(id);
		return Response.status(200).entity(game).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createNewGame(Game game) {
		gameDao.saveNewGame(game);
		return Response.status(201).entity(game).build();
	}
	
	@PUT 
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateGame(Game game) {
		gameDao.updateGameDetails(game);
		return Response.status(200).entity(game).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteGame(@PathParam("id") int id) {
		gameDao.delete(id);
		return Response.status(204).build();
	}
	
	@GET
	@Path("/countGames")
	@Produces({ MediaType.APPLICATION_JSON })
	public int countGames() {
		List<Game> games = gameDao.findAllGames();
		int gameCount = games.size();
		return gameCount;
	}
	
}
