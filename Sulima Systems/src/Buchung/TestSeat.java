package buchung;


public class TestSeat {


	public static void main(String[] args) {

		Room rooms[] = loadRooms();
		//Room room1 = new Room(1);
		addSeats(rooms[0], 25, "regular");
		addSeats(rooms[0], 5, "loveseat");
		addSeats(rooms[0], 2, "loge");

		
		
		//		Seat[] seats= new Seat[10];
		//		for (int i= 0; i < 10; i++)
		//		{
		//			seats[i] = new Seat(i+1, regular );
		//			seats[i].addSeatToRoom(room1);
		//			seats[i].saveSeat();
		//		}
		//		System.out.println("total number of seats from Java: " + room1.getNumberOfSeats());
//		Database db = new Database();
//		System.out.println("total number of seats in DB: " + db.getCountFromDatabase("seats"));
//		System.out.println("Seats from Room 1: " + db.getCountWithCondition("seats", "room_id", "1"));

	}


	public static Room[] loadRooms() {
		
		Room[] rooms = null;
		Database db = new Database();
		int rowsCount = db.getCountFromDatabase("ROOMS");

		if (rowsCount > 0) {

			rooms= new Room[rowsCount];
			db.fetchRoomsFromDatabase(rooms, rowsCount);
			System.out.println("Rooms loaded");
		}

		else System.out.println("No room in database");
		
		return rooms;
	}

	public static void addSeats(Room room, int numberOfSeats, String stringSeatType) {
		
		SeatType seatType;
		
		switch (stringSeatType) {
		
			case "regular":
				seatType = SeatType.REGULAR;
				break;
				
			case "loge":
				seatType = SeatType.LOGE;
				break;
				
			case "loveseat":
				seatType = SeatType.LOVESEAT;
				break;
				
			default:
				seatType = SeatType.REGULAR;
				break;
		}

		int initialSeatNumber = getSeatCount(room);
		Seat[] seats= new Seat[numberOfSeats];
		for (int i= 0; i < numberOfSeats; i++)
		{
			seats[i] = new Seat(i+1+initialSeatNumber, seatType );
			seats[i].addSeatToRoom(room);
			seats[i].saveSeat();
		}

	}
	
	public static int getSeatCount(Room room){
		int result = 0;
		String key ="seat_number";
		String query = "SELECT * FROM SEATS WHERE room_id =" + room.getRoomId() + " ORDER by seat_number DESC limit 1  ";
		Database db = new Database();
		result = db.getIntFromDatabase(query, key);
		return result;
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
