package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class SessionTest {
	Session session = null;

	@Test
	void testSave() {
		String dateString = "2018-04-01 19:00:00";
		session = new Session( 99, 2 , 1, dateString);
		assertEquals(session.save(), true);
	}

	/*
	 * TestDelete() will fail if executed first
	 */
	@Test
	void testDelete() {
		String dateString = "2018-04-01 19:00:00";
		session = new Session( 99, 2 , 1, dateString);
		assertEquals(session.delete(), true);
	}

}
