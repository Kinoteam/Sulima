package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MovieTest {
	
	Movie movie = null;

	@Test
	void testMovieString() {
		movie = new Movie("My Movie");
		assertEquals(movie.getMovieName(), "My Movie");
	}
	
	/*
	 * TestDelete() will fail if executed 1st
	 * 
	 */
	@Test
	void testDelete() {
		movie = new Movie("My Movie");
		assertEquals(movie.delete(), true);
	}

}
