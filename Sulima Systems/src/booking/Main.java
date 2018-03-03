package booking;

import authentication.User;
import core.*;

public class Main {

	public static void main(String[] args) {

//		User user = new User();
//		user.login();
//		Reservation res = new Reservation();
//		while (true)
//		{
//		res.showMovies();
//		}
//		Session session1 = new Session(2 , 2);
//		session1.save();
//		
	}

	public static Room[] loadRooms() {

		Room[] rooms = null;
		Database db = new Database();
		String query = "SELECT COUNT(*) FROM rooms";
		String key = "COUNT(*)";
		int rowsCount = db.getInt(query, key);

		if (rowsCount > 0) {

			rooms= new Room[rowsCount];
			db.fetchRooms(rooms, rowsCount);
			System.out.println("Rooms loaded");

			for (int i=0; i<rowsCount; i++) {
				rooms[i].loadSeats();
			}
		}

		else System.out.println("No rooms in database");

		db = null; query = null; key=null;
		return rooms;
	}
	
	public static Session[] loadSessions() {

		Session[] sessions = null;
		Database db = new Database();
		String query = "SELECT COUNT(*) FROM sessions";
		String key = "COUNT(*)";
		int rowsCount = db.getInt(query, key);

		if (rowsCount > 0) {

			sessions= new Session[rowsCount];
			db.fetchSessions(sessions, rowsCount);
			

			for (int i=0; i<rowsCount; i++) {
				System.out.println(sessions[i].toString());
			}
		}
		else System.out.println("No sessions in database");

		db = null; query = null; key=null;
		return sessions;
	}
}