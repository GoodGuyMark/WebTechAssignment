package user.arquillian;

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

import rest.RestApplication;
import user.User;
import user.UserDAO;
import user.UserWS;

@RunWith(Arquillian.class)
public class UserIntegrationTest {

	@Deployment
	public static Archive<?> createTestArchive(){
		return ShrinkWrap.create(JavaArchive.class, "Test.jar")
				.addClasses(UserUtilsDAO.class, User.class, UserDAO.class, UserWS.class, RestApplication.class)
				.addAsResource("META-INF/persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private UserDAO userDao;
	
	@EJB 
	private UserWS userWS;
	
	@EJB
	private UserUtilsDAO userUtilsDao;
	
	@Before
	public void setup() {
		userUtilsDao.deleteUserTable();
		User user = new User();
		user.setName("user4");
		user.setUsername("user4");
		user.setPassword("password4");
		user.setImage("generic.png");
		userDao.saveNewUser(user);
		User user2 = new User();
		user2.setName("test");
		user2.setUsername("test");
		user2.setPassword("test");
		user2.setImage("test");
		userDao.saveNewUser(user2);
	}
	
	@Test
	public void testFindAllUsers() {
		List<User> users = userDao.findAllUsers();
		assertEquals("Data fetch = data persisted", users.size(), 2);
	}

	@Test
	public void testFindUserByUsername() {
		User user = userDao.getUserByUsername("user4");
		assertEquals(user.getName(), "user4");
		assertEquals(user.getUsername(), "user4");
		assertEquals(user.getPassword(), "password4");
		assertEquals(user.getImage(), "generic.png");
	}
	
	@Test
	public void testAddUser() {
		User user = new User();
		user.setName("test2");
		user.setUsername("test2");
		user.setPassword("test2");
		user.setImage("test2");
		userDao.saveNewUser(user);
		List<User> users = userDao.findAllUsers();
		assertEquals(users.size(), 3);
		assertEquals(user.getName(), "test2");
		assertEquals(user.getUsername(), "test2");
		assertEquals(user.getPassword(), "test2");
		assertEquals(user.getImage(), "test2");
	}
	
	@Test
	public void testDeleteUser() {
		List<User> users = userDao.findAllUsers();
		assertEquals(users.size(), 2);
		userDao.delete("test");
		users = userDao.findAllUsers();
		assertEquals(users.size(), 1);
		assertEquals(null, userDao.getUserByUsername("test"));
	}
	
	@Test
	public void testUpdateUser() {
		User user = userDao.getUserByUsername("test");
		user.setName("update");
		user.setImage("update");
		userDao.updateUserDetails(user);
		userDao.getUserByUsername("test");
		assertEquals(user.getName(), "update");
		assertEquals(user.getUsername(), "test");
		assertEquals(user.getPassword(), "test");
		assertEquals(user.getImage(), "update");
	}
	
	@Test
	public void testGetAllUsersWS() {
	Response response = userWS.getAllUsers();
	List<User> users = (List<User>) response.getEntity();
	assertEquals(HttpStatus.SC_OK, response.getStatus());
	assertEquals("Data fetch = data persisted", users.size(), 2);
	User user = users.get(0);
	assertEquals("user4", user.getName());
	user = users.get(1);
	assertEquals("test", user.getName());
	} 
	
	@Test
	public void testUserById() {
		Response response = userWS.findUserByUsername("user4");
		User user = (User) response.getEntity();
		assertEquals(user.getName(), "user4");
		assertEquals(user.getUsername(), "user4");
		assertEquals(user.getPassword(), "password4");
		assertEquals(user.getImage(), "generic.png");
	}
	
	@Test
	public void testAddUserWS() {
		User user = new User();
		user.setName("test2");
		user.setUsername("test2");
		user.setPassword("test2");
		user.setImage("test2");
		Response response = userWS.createNewUser(user);
		assertEquals(HttpStatus.SC_CREATED, response.getStatus());
		user = (User) response.getEntity();
		assertEquals(user.getName(), "test2");
		assertEquals(user.getUsername(), "test2");
		assertEquals(user.getPassword(), "test2");
		assertEquals(user.getImage(), "test2");
	}
	
	@Test
	public void testRemoveUser() {
		Response response = userWS.getAllUsers();
		List<User> userList = (List<User>) response.getEntity();
		assertEquals(userList.size(), 2);
		userWS.deleteUser("test");
		response = userWS.getAllUsers();
		userList = (List<User>) response.getEntity();
		assertEquals(userList.size(), 1);
	
		
	}
	
	@Test
	public void testUpdateUserWS() {
		Response response = userWS.findUserByUsername("test");
		User user = (User) response.getEntity();
		user.setName("update");
		user.setImage("update");
		response = userWS.updateUser(user);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		user = (User) response.getEntity();
		assertEquals(user.getName(), "update");
		assertEquals(user.getUsername(), "test");
		assertEquals(user.getPassword(), "test");
		assertEquals(user.getImage(), "update");
	}
	
	@Test
	public void testUserCount() {
		int count = userWS.countUsers();
		assertEquals(2, count);
	}
	
	@Test
	public void loginFail() {
		Response response = userWS.findUserByUsernameAndPassword("user4", "inavlid");
		User user = (User) response.getEntity();
		assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());
		assertEquals("user4", user.getName());
	}
	
	@Test
	public void loginSuccess() {
		Response response = userWS.findUserByUsernameAndPassword("test", "test");
		User user = (User) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals("test", user.getName());
	}
	
}
