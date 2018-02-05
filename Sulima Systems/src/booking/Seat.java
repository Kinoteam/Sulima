package booking;

 class Seat {
	private int seatNumber;
	private int roomNumber;
	private SeatType seatType;
	private boolean reserved;


	 Seat() {
		seatNumber = 0;
		roomNumber = 0;
		reserved = false;
		seatType=null;
	}


	 Seat(SeatType pSeatType) {
		seatNumber = 0;
		roomNumber = 0;
		reserved = false;
		seatType=pSeatType;
	}

	 Seat(int pSeatNumber, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		roomNumber = 0;
		reserved = false;
		seatType= pSeatType;
	}

	 Seat(int pSeatNumber, Room room, boolean pReserved, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		roomNumber = room.getRoomId();
		reserved = pReserved;
		seatType= pSeatType;
	}

	 void resetSeat() {
		reserved = false;
	}

	 void reserveSeat(String pFirstName, String pLastName) {
		reserved = true;
	}

	 int getSeatNumber() {
		return seatNumber;
	}
	
	 void setSeat(int seatNumber, int roomNumber, String stringSeatType, boolean reserved) {
		this.seatNumber = seatNumber;
		this.roomNumber = roomNumber;
		this.seatType = seatTypeFromString(stringSeatType);
		this.reserved = reserved;
	}
	
	 void setSeatNumber(int pSeatNumber) {
		seatNumber= pSeatNumber;
	}

	 boolean getReserved() {
		return reserved;
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

		switch (this.seatType) {
		case LOVESEAT:
			roomNumber = roomId;
			break;

		case REGULAR:
			roomNumber = roomId;
			break;

		case LOGE:
			roomNumber = roomId;
			break;

		default:
			System.out.println("Please set the seat type before assigning the seat to a room");
			break;
		}

	}


	void saveSeat() {

		Database db = new Database();
		String query = "INSERT INTO SEATS (seat_number, room_id , seat_category, is_reserved)"
				+ " VALUES (" + seatNumber + "," + roomNumber + ", '"+ seatTypeToString()+ "', '" +reserved  + "')";

		db.insertIntoDatabase(query);
	}
	
	void deleteSeat() {
		Database db = new Database();
		String query = "DELETE FROM seats WHERE seat_number = " + this.seatNumber + " AND room_id= " + this.roomNumber;

		db.deleteRowFromDatabase(query);
		db = null;
	}
	
	void updateSeat(int shift) {
		
		Database db = new Database();
		String query = "UPDATE SEATS SET seat_number = " + (this.seatNumber-shift) +
				" WHERE seat_number = " + this.seatNumber + " AND room_id = " + this.roomNumber;

		db.insertIntoDatabase(query);
		db = null;
	}
}



