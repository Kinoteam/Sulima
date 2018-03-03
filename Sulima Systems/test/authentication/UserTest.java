package authentication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import core.Global;

class UserTest {

	User user = new User();
	@Test
	void testLogin() {
		user.login("root", "root");
		assertEquals(Global.logged_in, true);
	}

	@Test
	void testLogout() {
		user.logout();
		assertEquals(Global.logged_in, false);
	}

}
