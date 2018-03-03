package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import core.Database;

/**
 * These tests are also testing the Seat class, namely the methods:
 * 	Seat(int, int , int , String)
 * 	Seat(int, SeatType)
 * 	setRoom_id(int)
 * 	save()
 * 	unsetRoom_id()
 * 	updateRoom_id
 * 	delete()
 */
class RoomTest {

	Room rooms[]= null;
	Room room = null;

	Room[] LoadRooms(){

		Database db = new Database();
		String query = "SELECT COUNT(*) FROM ROOMS";
		String key = "COUNT(*)";
		int rowsCount = db.getInt(query, key);

		if (rowsCount > 0) {

			rooms= new Room[rowsCount];
			db.fetchRooms(rooms, rowsCount);

			for (int i=0; i<rowsCount; i++) 
			{
				rooms[i].loadSeats();
			}
		}

		db = null; query = null; key=null;
		return rooms;
	}


	@Test							
	void testRoomIntIntIntInt() {
		
		room = new Room(3,5,2,1);
		assertEquals(room.getRoom_id(), 3);
	}
	
/*
 * testDelete() will fail if executed before testAddseats() or testRoomIntIntIntInt()
 * 
 */
	@Test							
	void testDelete() {
		rooms = this.LoadRooms();
		rooms[2].delete();
		rooms[3].delete();
		rooms = this.LoadRooms();
		assertEquals(rooms.length, 2);
	}
	
	@Test							
	void testAddseats() {
		
		room = new Room(5, 0, 0, 0);
		room.addSeats(1, SeatType.REGULAR);
		assertEquals(room.getNumberOfSeats(), 1);
		
	}
	
	@Test							
	void testUpdate() {
		room = new Room(5, 1,0,1);
		rooms = this.LoadRooms();
		rooms[2].setRoom_id(9);
		rooms = this.LoadRooms();
		assertEquals(rooms[2].getRoom_id() , 9);
		rooms[2].delete();
		
	}
}