package booking;

public class Test {


	public static void main(String[] args) {


		Reservation res = new Reservation();
		while (true){
		res.showMovies();
		}
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

		else System.out.println("No rooms in database");

		db = null; query = null; key=null;
		return rooms;
	}

}//class

