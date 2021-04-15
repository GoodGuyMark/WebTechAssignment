package user.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import user.User;

public class UserTest {
	
	@Test
	public void testUserConstructor() {
		User user = new User();
		user.setName("example");
		user.setUsername("example");
		user.setPassword("password");
		user.setImage("generic.jpg");
		assertEquals("example", user.getName());
		assertEquals("example", user.getUsername());
		assertEquals("password", user.getPassword());
		assertEquals("generic.jpg", user.getImage());
	}
}
