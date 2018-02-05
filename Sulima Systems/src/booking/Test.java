package booking;

public class Test {


	public static void main(String[] args) {

		Room rooms[] = loadRooms();
		Room room1 = new Room(1, 15,3,2);
		rooms = loadRooms();
//		rooms[0].addSeatsToRoom(2, "regular");
//		rooms[1].addSeatsToRoom(2, "loveseat");
//		System.out.println(rooms[2].roomSeats[5].getSeatNumber());
//		System.out.println(rooms[0].getNumberOfSeats());
//		rooms[0].addSeatsToRoom(2, SeatType.LOGE);
		rooms[0].removeSeatsFromRoom(16, 18);
		System.out.println(rooms[0].getNumberOfSeats());
		rooms = loadRooms();
		System.out.println(rooms[0].getNumberOfSeats());
	}


	public static Room[] loadRooms() {
		
		Room[] rooms = null;
		Database db = new Database();
		String query = "SELECT COUNT(*) FROM ROOMS";
		String key = "COUNT(*)";
		int rowsCount = db.getIntFromDatabase(query, key);

		if (rowsCount > 0) {

			rooms= new Room[rowsCount];
			db.fetchRoomsFromDatabase(rooms, rowsCount);
			System.out.println("Rooms loaded");
			
			for (int i=0; i<rowsCount; i++) {
				rooms[i].loadSeats();
			}
		}

		else System.out.println("No room in database");
		
		db = null; query = null; key=null;
		return rooms;
	}
}

