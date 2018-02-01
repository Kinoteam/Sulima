package buchung;

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

	public boolean getReserved() {
		return reserved;
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
			return("Seat type undefined");
		}
	}


	public void addSeatToRoom(Room room) {

		switch (this.seatType) {
		case LOVESEAT:
			room.incrementNumberOfLoveSeats();
			roomNumber = room.getRoomId();
			break;

		case REGULAR:
			room.incrementNumberOfRegularSeats();
			roomNumber = room.getRoomId();
			break;

		case LOGE:
			room.incrementNumberOfLogeSeats();
			roomNumber = room.getRoomId();
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



