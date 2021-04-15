package user;

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

@Path("/users")
@Stateless
@LocalBean
public class UserWS {
	
	User user;
	String dbPassword;
	
	@EJB 
	private UserDAO userDao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllUsers() {
		List<User> users = userDao.findAllUsers();
		return Response.status(200).entity(users).build();
	}
	
	@GET
	@Path("/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findUserByUsername(@PathParam("username") String username) {
		User user = userDao.getUserByUsername(username);
		return Response.status(200).entity(user).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createNewUser(User user) {
		userDao.saveNewUser(user);
		return Response.status(201).entity(user).build();
	}
	
	@PUT 
	@Path("/{username}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateUser(User user) {
		userDao.updateUserDetails(user);
		return Response.status(200).entity(user).build();
	}
	
	@DELETE
	@Path("/{username}")
	public Response deleteUser(@PathParam("username") String username) {
		userDao.delete(username);
		return Response.status(204).build();
	}
	
	@GET
	@Path("/countUsers")
	@Produces({ MediaType.APPLICATION_JSON })
	public int countUsers() {
		List<User> users = userDao.findAllUsers();
		int userCount = users.size();
		return userCount;
	}
	
	@GET
	@Path("/{username}/{password}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findUserByUsernameAndPassword(@PathParam("username") String username, @PathParam("password") String password) {
		User user = userDao.getUserByUsername(username);
		String dbPassword = user.getPassword();
		boolean check = (password.equals(dbPassword));
		if (check) {
			return Response.status(200).entity(user).build();
		} else {
			return Response.status(400).entity(user).build();
		}
	}

}
