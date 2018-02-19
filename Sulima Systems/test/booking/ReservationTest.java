package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReservationTest {

	
	@Test
	void testShowMovies() {
		Reservation reservation = new Reservation();
		reservation.showMovies();
		assertEquals(reservation.getSeat_id(), 590);
	}
}
