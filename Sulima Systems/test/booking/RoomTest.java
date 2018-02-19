package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import core.Database;

class RoomTest {

	Room rooms[]= null;
	Room room1 = null;
	Room room2 = null;
	Room room3 = null;


	Room[] LoadRooms(){

		Database db = new Database();
		String query = "SELECT COUNT(*) FROM ROOMS";
		String key = "COUNT(*)";
		int rowsCount = db.getIntFromDatabase(query, key);

		if (rowsCount > 0) {

			rooms= new Room[rowsCount];
			db.fetchRoomsFromDatabase(rooms, rowsCount);

			for (int i=0; i<rowsCount; i++) {
				rooms[i].loadSeats();
			}
		}

		db = null; query = null; key=null;
		return rooms;
	}

	@Test 								/*test 1*/
	void testRoom(){
		rooms=this.LoadRooms();
		assertNotNull(rooms);
	}


	@Test								/*test 2*/
	void testRoomInt() {

		room1 = new Room(1);
		assertEquals(room1.getRoom_id(), 0);

	}

	@Test								/*test 3*/
	void testRoomIntIntIntInt() {
		
		room2 = new Room(10, 1, 0, -5);
		assertEquals(room2.getRoom_id(), 0);
		
		room3 = new Room(3,5,2,1);
		assertEquals(room3.getRoom_id(), 3);
	}
	
	

	@Test								/*test 4*/
	void testDeleteRoom() {
		rooms = this.LoadRooms();
		rooms[2].deleteRoom();
		rooms = this.LoadRooms();
		assertEquals(rooms.length, 2);
	}
}
