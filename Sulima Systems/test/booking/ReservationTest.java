package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReservationTest {

	/*
	 * 
	 * 	REQUIRES MANUAL INPUT
	 * 
	 * 	login: root
	 * 	pwd: root
	 * 
	 */
	
	@Test
	void testShowMovies() {
		Reservation reservation = new Reservation();
		assertEquals(reservation.showMovies() , true);
	}
}