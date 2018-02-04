package buchung;

public class Room {

	private int roomId;
	private int numberOfSeats;
	private int numberOfRegularSeats;
	private int numberOfLoveSeats;
	private int numberOfLogeSeats;
	Seat[] roomSeats;


	public Room(){

		numberOfRegularSeats = 0;
		numberOfLoveSeats = 0;
		numberOfLogeSeats = 0;
		numberOfSeats = 0;
		roomSeats = null;
	}

	public Room(int pRoomId){

		String query = "SELECT * FROM ROOMS WHERE room_id = " + pRoomId;
		Database db = new Database();
		if(!db.isAlreadyInDatabase(query)) {
			roomId = pRoomId;
			numberOfRegularSeats = 0;
			numberOfLoveSeats = 0;
			numberOfLogeSeats = 0;
			numberOfSeats = 0;
			roomSeats = null;
		}
		else System.out.println("Error: Room exists already");

	}

	public Room(int pNumberOfRegularSeats, int pNumberOfLoveSeats, int pNumberOfLogeSeats){

		numberOfRegularSeats = pNumberOfRegularSeats;
		numberOfLoveSeats = pNumberOfLoveSeats;
		numberOfLogeSeats = pNumberOfLogeSeats;
		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;
	}


	public int getNumberOfSeats() {
		return this.numberOfSeats;
	}

	public int getNumberOfRegularSeats() {
		return this.numberOfRegularSeats;
	}

	public int getNumberOfLoveSeats() {
		return this.numberOfLoveSeats;
	}

	public int getNumberOfLogeSeats() {
		return this.numberOfLogeSeats;
	}

	public int getRoomId() {
		return this.roomId;
	}

	public void setRoomId(int pRoomId) {
		roomId = pRoomId;
	}

	public void setNumberOfSeats(int pNumberOfRegularSeats, int pNumberOfLoveSeats, int pNumberOfLogeSeats) {

		numberOfRegularSeats = pNumberOfRegularSeats;
		numberOfLoveSeats = pNumberOfLoveSeats;
		numberOfLogeSeats = pNumberOfLogeSeats;
		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;

	}

	public void setNumberOfRegularSeats(int pNumberOfRegularSeats) {
		numberOfRegularSeats = pNumberOfRegularSeats;
		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;
	}

	public void setNumberOfLoveSeats(int pNumberOfLoveSeats) {
		numberOfLoveSeats = pNumberOfLoveSeats;
		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;
	}

	public void setNumberOfLogeSeats(int pNumberOfLogeSeats) {
		numberOfLogeSeats = pNumberOfLogeSeats;
		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;
	}

	public void incrementNumberOfRegularSeats() {
		numberOfRegularSeats++ ;
		numberOfSeats++;
	}

	public void incrementNumberOfLoveSeats() {
		numberOfLoveSeats++;
		numberOfSeats++;
	}

	public void incrementNumberOfLogeSeats() {
		numberOfLogeSeats++;
		numberOfSeats++;
	}

	public void saveRoom() {

		Database db = new Database();
		String chkQuery = "SELECT * FROM ROOMS WHERE room_id=" + roomId;

		if (db.isAlreadyInDatabase(chkQuery) == false) {

			String saveQuery = "INSERT INTO ROOMS (room_id, numberOfRegularSeats, numberOfLoveSeats, numberOfLogeSeats)"
					+ " VALUES (" + roomId + "," + numberOfRegularSeats + "," + numberOfLoveSeats + "," + numberOfLogeSeats + ")";

			db.insertIntoDatabase(saveQuery);
		}

		else System.out.println("Error: room already in database");
	}

	public void loadSeats() {
		
		Seat[] seats = null;
		Database db = new Database();
		String query = "SELECT COUNT(*) FROM SEATS WHERE room_id = " + roomId;
		String key = "COUNT(*)";
		int rowsCount = db.getIntFromDatabase(query, key);

		if (rowsCount > 0) {

			seats= new Seat[rowsCount];
			db.fetchSeatsFromDatabase( roomId ,seats, rowsCount);
			System.out.println("Seats loaded");
		}

		else System.out.println("No seats in this room");
		
		db = null;
		roomSeats = seats;
	}
	
	public void addSeatsToRoom(int numberOfSeats, String stringSeatType) {

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

		int initialSeatNumber = getSeatCount();
		Seat[] seats= new Seat[numberOfSeats];
		for (int i= 0; i < numberOfSeats; i++)
		{
			seats[i] = new Seat(initialSeatNumber + 1 + i, seatType);
			seats[i].setRoomIdOfSeat(this.roomId);
			seats[i].saveSeat();
		}
		
		this.roomSeats=seats;
	}

	public int getSeatCount(){
		int result = 0;
		String key ="seat_number";
		String query = "SELECT * FROM SEATS WHERE room_id =" + this.roomId + " ORDER by seat_number DESC limit 1  ";
		Database db = new Database();
		result = db.getIntFromDatabase(query, key);
		db = null;
		return result;
	}
}
