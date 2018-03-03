package booking;

import core.Database;

public class Seat{
	private int seat_id;
	private int seatNumber;
	private int room_id;
	private SeatType seatType;
	Database db = new Database();
	
	public Seat() {
		seatNumber = 0;
		room_id = 0;
		seatType=null;
	}

	public Seat(int seatNumber, SeatType seatType) {
		this.seatNumber = seatNumber;
		this.room_id = 0;
		this.seatType= seatType;
	}
	
	public Seat(int seat_id, int seatNumber, int room_id, String stringSeatType) {
		this.seat_id = seat_id;
		this.seatNumber = seatNumber;
		this.room_id = room_id;
		this.seatType = seatTypeFromString(stringSeatType);
	}
	
	int getSeat_id() {
		return(this.seat_id);
	}

	int getSeatNumber() {
		return seatNumber;
	}
	
	SeatType getSeatType() {
		return(this.seatType);
	}
	
	private SeatType seatTypeFromString(String stringSeatType) {
		switch (stringSeatType) 
		{
		case "LOVESEAT":
			return SeatType.LOVESEAT;

		case "LOGE":
			return SeatType.LOGE;

		default:
			return SeatType.REGULAR;
		}
	}

	void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	void unsetRoom_id() {
		String query = "UPDATE seats SET room_id = NULL WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.room_id;
		db.update(query);
		query = null;
	}
	
	void updateRoom_id(int room_id){
		String query = "UPDATE seats SET room_id = " + room_id+ " WHERE room_id is NULL";
		db.update(query);
		query = null;
	}

	void save() {
		String query = "INSERT INTO seats (seat_number, room_id , seat_category) "
					 + "VALUES (" + seatNumber + "," + room_id + ", '"+ seatTypeToString()+ "')";
		db.update(query);
		query = null;
	}

	void update(int shift) {
		String query = "UPDATE seats SET seat_number = " + (this.seatNumber-shift) +
					   " WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.room_id;
		db.update(query);
		query = null;
	}
	
	void delete() {
		String query = "DELETE FROM seats WHERE seat_number = " +this.seatNumber + " AND room_id= " +this.room_id;
		db.update(query);
		query = null;
	}

	String seatTypeToString() {
		switch (this.seatType) {
		case LOVESEAT:
			return "LOVESEAT";

		case REGULAR:
			return "REGULAR";

		case LOGE:
			return "LOGE";

		default:
			System.err.println("Error at seatTypeToString at Class Room");
			return null;
		}
	}

	public String toString() {

		return "Seat N: " + this.seatNumber + ". Id: " + this.seat_id + ". Type: " + this.seatTypeToString();
	}
}