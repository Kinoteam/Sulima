package booking;

public class Seat {
	private int seatNumber;
	private int seatId;
	private int roomNumber;
	private SeatType seatType;
	private boolean reserved;


	public Seat() {
		seatNumber = 0;
		roomNumber = 0;
		reserved = false;
		seatType=null;
	}


	public Seat(SeatType pSeatType) {
		seatNumber = 0;
		roomNumber = 0;
		reserved = false;
		seatType=pSeatType;
	}

	public Seat(int pSeatNumber, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		roomNumber = 0;
		reserved = false;
		seatType= pSeatType;
	}

	public Seat(int pSeatNumber, Room room, boolean pReserved, SeatType pSeatType) {
		seatNumber = pSeatNumber;
		roomNumber = room.getRoomId();
		reserved = pReserved;
		seatType= pSeatType;
	}

	public void resetSeat() {
		reserved = false;
	}

	public void reserveSeat(String pFirstName, String pLastName) {
		reserved = true;
	}

	public int getSeatNumber() {
		return seatNumber;
	}
	
	public void setSeat(int seatId, int seatNumber, int roomNumber, String stringSeatType, boolean reserved) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.roomNumber = roomNumber;
		this.seatType = seatTypeFromString(stringSeatType);
		this.reserved = reserved;
	}
	
	public void setSeatNumber(int pSeatNumber) {
		seatNumber= pSeatNumber;
	}

	public boolean getReserved() {
		return reserved;
	}

	public SeatType getSeatType() {
		return(this.seatType);
	}

	public String seatTypeToString() {
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


	public void setRoomIdOfSeat(int roomId) {

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


	public void saveSeat() {

		Database db = new Database();
		String query = "INSERT INTO SEATS (seat_number, room_id , seat_category, is_reserved)"
				+ " VALUES (" + seatNumber + "," + roomNumber + ", '"+ seatTypeToString()+ "', '" +reserved  + "')";

		db.insertIntoDatabase(query);

	}
}



