package buchung;


public class Test {


	public static void main(String[] args) {

		Room rooms[] = loadRooms();
//		Room room1 = new Room(1);
//		Room room2 = new Room(2);
//		room1.saveRoom();
//		room2.saveRoom();
//		rooms = loadRooms();
//		rooms[0].addSeatsToRoom(2, "regular");
//		rooms[1].addSeatsToRoom(2, "loveseat");
		System.out.println(rooms[1].roomSeats[1].getSeatNumber());

		
		//room1.addSeatsToRoom(20, "regular");
		//Room room1 = new Room(1);
//		addSeats(rooms[0], 25, "regular");
//		addSeats(rooms[0], 5, "loveseat");
//		addSeats(rooms[0], 2, "loge");


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
		
		db = null;
		return rooms;
	}
	
}


	//public class TestSeat {
	//
	//	public static void main(String[] args) {
	//		
	//		int totalSeats = 10;
	//		Seat theatre[] = new Seat[totalSeats];
	//
	//		for (int i=0; i<10; i++) {
	//			theatre[i] = new Seat();
	//			theatre[i].setResetSeat(i+1);
	//			System.out.println("Seat created. Nr:" + theatre[i].getSeatNumber() + ". reserved: " + theatre[i].getReserved() + ". name: " + theatre[i].getFirstName() + " " + theatre[i].getLastName());
	//		}
	//		
	//		int seatToModify= 5;
	//		theatre[seatToModify+1].setReservedSeat("mamak", "babak");
	//		
	//		for (int i=0; i<10; i++) {
	//			System.out.println("Seat created. Nr:" + theatre[i].getSeatNumber() + ". reserved: " + theatre[i].getReserved() + ". name: " + theatre[i].getFirstName() + " " + theatre[i].getLastName());
	//		}
	//		
	//	}
	//}
