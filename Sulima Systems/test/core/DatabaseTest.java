package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import booking.Movie;
import booking.Room;
import booking.Seat;
import booking.Session;

/**
 * These tests require the importation 
 * of the database with its data
 *
 */

class DatabaseTest {
	
	private String query = null;
	private Database db = new Database();

	@Test
	void testExistsInDatabase() {
		query = "SELECT * FROM rooms";
		assertEquals(db.existsInDatabase(query) , true );
	}


	@Test
	void testUpdate() {
		query = "INSERT INTO rooms (room_id) VALUES (10)";
		assertEquals(db.update(query) , true );
		query = "DELETE FROM rooms where room_id = 10";
		assertEquals(db.update(query) , true );
	}

	@Test
	void testGetCountWithCondition() {
		assertEquals(db.getCountWithCondition("rooms", "room_id", "1") , 1 );
	}

	@Test
	void testFetchMovies() {
		Movie[] movies = new Movie[1];
		assertEquals(db.fetchMovies(movies, 1) , true);
	}

	@Test
	void testFetchRooms() {
		Room[] rooms = new Room[1];
		assertEquals(db.fetchRooms(rooms, 1) , true);
	}

	@Test
	void testFetchSessionsSessionArrayInt() {
		Session[] sessions = new Session[1];
		assertEquals(db.fetchSessions(sessions, 1), true);
	}

	@Test
	void testFetchSeats() {
		Seat[] seats = new Seat[1];
		assertEquals(db.fetchSeats(1, seats, 1) , true);
	}

	@Test
	void testFetchReservedSeatsId() {
		int[] array = new int[1];
		assertEquals(db.fetchReservedSeatsId(array, 1, 1) , true);
	}

	@Test
	void testGetInt() {
		query = "SELECT COUNT(*) FROM movies";
		assertEquals(db.getInt( query , "COUNT(*)") , 2);
	}

}
