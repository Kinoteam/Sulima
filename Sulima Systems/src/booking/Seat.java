package booking;

class Seat{
	private int seat_id;
	private int seatNumber;
	private int room_id;
	private SeatType seatType;


	Seat() {

		seatNumber = 0;
		room_id = 0;
		seatType=null;
	}


	Seat(SeatType pSeatType) {

		seatNumber = 0;
		room_id = 0;
		seatType=pSeatType;
	}

	Seat(int pSeatNumber, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		room_id = 0;
		seatType= pSeatType;
	}

	Seat(int pSeatNumber, Room room, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		room_id = room.getRoomId();
		seatType= pSeatType;
	}

	Seat(int seatNumber, int room_id, SeatType seatType ){
		this.seatNumber= seatNumber;
		this.room_id= room_id;
		this.seatType = seatType;

	}

	int getSeatNumber() {
		return seatNumber;
	}

	void setSeat(int seat_id, int seatNumber, int room_id, String stringSeatType) {
		this.seat_id = seat_id;
		this.seatNumber = seatNumber;
		this.room_id = room_id;
		this.seatType = seatTypeFromString(stringSeatType);
	}

	void setSeatNumber(int pSeatNumber) {
		seatNumber= pSeatNumber;
	}


	int getSeat_id() {
		return(this.seat_id);
	}
	
	SeatType getSeatType() {
		return(this.seatType);
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

	void setRoomIdOfSeat(int roomId) {
		room_id = roomId;
	}

	void setRoomIdToNull() {

		Database db = new Database();
		String query = "UPDATE SEATS SET room_id = NULL WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.room_id;

		db.updateDatabase(query);
		db = null;

	}

	void updateRoomIdOfSeat(int roomId){

		Database db = new Database();
		String query = "UPDATE SEATS SET room_id = " + roomId+ " WHERE room_id is NULL";

		db.updateDatabase(query);
		db = null;
	}


	void saveSeat() {

		Database db = new Database();
		String query = "INSERT INTO SEATS (seat_number, room_id , seat_category)"
				+ " VALUES (" + seatNumber + "," + room_id + ", '"+ seatTypeToString()+ "')";

		db.updateDatabase(query);
	}

	void deleteSeat() {
		Database db = new Database();
		String query = "DELETE FROM seats WHERE seat_number = " + this.seatNumber + " AND room_id= " + this.room_id;

		db.updateDatabase(query);
		db = null;
	}

	void updateSeat(int shift) {

		Database db = new Database();
		String query = "UPDATE SEATS SET seat_number = " + (this.seatNumber-shift) +
				" WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.room_id;

		db.updateDatabase(query);
		db = null;
	}

	public String toString() {

		String string =  "Seat N: " + this.seatNumber + ". Id: " + this.seat_id + ". Type: " + this.seatTypeToString();
		return string;
	}
}



