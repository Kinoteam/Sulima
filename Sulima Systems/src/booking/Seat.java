package booking;

import core.Database;

public class Seat{
	private int seat_id;
	private int seatNumber;
	private int room_id;
	private SeatType seatType;
	
	public Seat() {

		seatNumber = 0;
		room_id = 0;
		seatType=null;
	}

	Seat(int pSeatNumber, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		room_id = 0;
		seatType= pSeatType;
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

		SeatType seatType = SeatType.REGULAR;

		switch (stringSeatType) {
		case "LOVESEAT":
			seatType = SeatType.LOVESEAT;
			return seatType;

		case "LOGE":
			seatType = SeatType.LOGE;
			return seatType;

		default:
			return seatType;
		}
	}

	public void setSeat(int seat_id, int seatNumber, int room_id, String stringSeatType) {
		this.seat_id = seat_id;
		this.seatNumber = seatNumber;
		this.room_id = room_id;
		this.seatType = seatTypeFromString(stringSeatType);
	}

	void setRoom_idOfSeat(int room_id) {
		this.room_id = room_id;
	}

	void unsetRoom_idOfSeat() {

		Database db = new Database();
		String query = "UPDATE SEATS SET room_id = NULL WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.room_id;

		db.updateDatabase(query);
		db = null;

	}
	
	void updateRoom_idOfSeat(int room_id){

		Database db = new Database();
		String query = "UPDATE SEATS SET room_id = " + room_id+ " WHERE room_id is NULL";

		db.updateDatabase(query);
		db = null;
	}

	void saveSeat() {

		Database db = new Database();
		String query = "INSERT INTO SEATS (seat_number, room_id , seat_category)"
				+ " VALUES (" + seatNumber + "," + room_id + ", '"+ seatTypeToString()+ "')";

		db.updateDatabase(query);
	}

	void updateSeat(int shift) {

		Database db = new Database();
		String query = "UPDATE SEATS SET seat_number = " + (this.seatNumber-shift) +
				" WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.room_id;

		db.updateDatabase(query);
		db = null;
	}
	
	void deleteSeat() {
		Database db = new Database();
		String query = "DELETE FROM seats WHERE seat_number = " + this.seatNumber + " AND room_id= " + this.room_id;

		db.updateDatabase(query);
		db = null;
	}

	String seatTypeToString() {
		switch (this.seatType) {
		case LOVESEAT:
			return("LOVESEAT");

		case REGULAR:
			return("REGULAR");

		case LOGE:
			return("LOGE");

		default:
			return("Something wrong at seatTypeToString at Class Room");
		}
	}

	public String toString() {

		String string =  "Seat N: " + this.seatNumber + ". Id: " + this.seat_id + ". Type: " + this.seatTypeToString();
		return string;
	}
}