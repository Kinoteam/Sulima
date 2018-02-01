package buchung;

public class Room {

	private int roomId;
	private int numberOfSeats;
	private int numberOfRegularSeats;
	private int numberOfLoveSeats;
	private int numberOfLogeSeats;


	public Room(){

		numberOfRegularSeats = 0;
		numberOfLoveSeats = 0;
		numberOfLogeSeats = 0;
		numberOfSeats = 0;
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
}
