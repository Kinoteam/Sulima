package booking;

public class Room {

	private int roomId; // unique
	private int numberOfSeats;
	private int numberOfRegularSeats;
	private int numberOfLoveSeats;
	private int numberOfLogeSeats;
	private Seat[] roomSeats; // Array seats belonging to this room


	public Room(){

		this.numberOfRegularSeats = 0;
		this.numberOfLoveSeats = 0;
		this.numberOfLogeSeats = 0;
		this.numberOfSeats = 0;
		this.roomSeats = null;
	} // Room() constructor

	public Room(int roomId){

		/*when creating a new instance with a roomId,
		 a database check has to be made to avoid duplicates*/

		String query = "SELECT * FROM ROOMS WHERE room_id = " + roomId;
		Database db = new Database();

		if(!db.isAlreadyInDatabase(query))   /* if the roomId is unique*/
		{ 
			this.roomId = roomId;
			this.numberOfRegularSeats = 0;
			this.numberOfLoveSeats = 0;
			this.numberOfLogeSeats = 0;
			this.numberOfSeats = 0;
			this.roomSeats = null;
			saveRoom();
		}

		else System.out.println("Error: Room exists already");

		db=null; //dereference the pointer

	}// Room(roomId) constructor

	public Room(int roomId, int numberOfRegularSeats, int numberOfLoveSeats, int numberOfLogeSeats){

		/*when creating a new instance with a roomId,
		 a database check has to be made to avoid duplicates*/

		String query = "SELECT * FROM ROOMS WHERE room_id = " + roomId;
		Database db = new Database();

		if(!db.isAlreadyInDatabase(query))   /* if the roomId is unique*/
		{ 
			/*create and save the room before adding seats*/
			this.roomId = roomId;      
			this.numberOfRegularSeats = 0;
			this.numberOfLoveSeats = 0;
			this.numberOfLogeSeats = 0;
			this.numberOfSeats = 0;
			roomSeats = null;

			saveRoom(); /* the room must be saved to Database because 
						the roomId is a Foreign key in the table Seats*/

			/*now can add seats*/

			if (numberOfRegularSeats > 0)
			{
				addSeatsToRoom(numberOfRegularSeats, SeatType.REGULAR);
				this.numberOfRegularSeats = numberOfRegularSeats;
				numberOfSeats += numberOfRegularSeats;
			}

			if (numberOfLoveSeats > 0)
			{
				addSeatsToRoom(numberOfLoveSeats, SeatType.LOVESEAT);
				this.numberOfLoveSeats= numberOfLoveSeats;
				numberOfSeats += numberOfLoveSeats;
			}

			if (numberOfLogeSeats > 0)
			{
				addSeatsToRoom(numberOfLogeSeats, SeatType.LOGE);
				this.numberOfLogeSeats= numberOfLogeSeats;
				numberOfSeats += numberOfLogeSeats;
			}

		}// if roomId is unique

		else System.out.println("Error: Room exists already");

		db=null; /*clearing memory*/
	}// Room(int Id, int, int, int) Constructor

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

	private void setNumberOfSeats() {

		for (int i = 0; i< this.roomSeats.length; i++) {

			if (this.roomSeats[i].getSeatType() == SeatType.REGULAR)
			{
				this.numberOfRegularSeats++;
			}

			else if (this.roomSeats[i].getSeatType() == SeatType.LOVESEAT)
			{
				this.numberOfLoveSeats++;
			}
			else if (this.roomSeats[i].getSeatType() == SeatType.LOGE)
			{
				numberOfLogeSeats++ ;
			}
			else System.out.println("Error: something weird happened at Class Room, Method setNumberOfSeats()");

		}

		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;

	}

	public void updateNumberOfSeats(int seatsAdded, SeatType seatType) {

		switch (seatType) {
		case LOVESEAT:
			this.numberOfLoveSeats += seatsAdded;
			this.numberOfSeats += seatsAdded;

		case REGULAR:
			this.numberOfRegularSeats += seatsAdded;
			this.numberOfSeats += seatsAdded;

		case LOGE:
			this.numberOfLogeSeats += seatsAdded;
			this.numberOfSeats += seatsAdded;

		default:
			System.out.println("Something wrong at updateNumberOfSeats at Class Room");
		}

	}

	public void saveRoom() {

		Database db = new Database();
		String chkQuery = "SELECT * FROM ROOMS WHERE room_id=" + roomId;

		if (db.isAlreadyInDatabase(chkQuery) == false) /*if the roomId is not already in the Database*/
		{ 

			String saveQuery = "INSERT INTO ROOMS (room_id)"
					+ " VALUES (" + roomId + ")";

			db.insertIntoDatabase(saveQuery);
			saveQuery=null; /*clearing memory*/
		}

		else System.out.println("Error: room already in database");
		db = null; chkQuery = null; /*clearing memory*/
	}// saveRoom()

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
			roomSeats = seats;
			setNumberOfSeats();
		}

		else System.out.println("No seats in this room");

		db = null; query = null; key=null; seats=null; /*clearing memory*/

	}// loadSeats()

	public void addSeatsToRoom(int numberOfSeats, SeatType seatType) {

		Seat[] seats= new Seat[numberOfSeats];
		for (int i= 0; i < numberOfSeats; i++)
		{
			// create a new instance of Seat in the Array seats
			seats[i] = new Seat(this.numberOfSeats + 1 + i, seatType);

			// set the RoomId variable IN the Seat instance to that of the Room it belongs to.
			seats[i].setRoomIdOfSeat(this.roomId);
			seats[i].saveSeat();

		}// for loop

		this.roomSeats = seats;
		seats=null; /*clearing memory*/
	}// Method addSeatsToRoom()

	public int getSeatCount(){
		if (roomSeats != null) 
		{
			return this.roomSeats.length;
		}
		else return 0;
	}// Method getSeatCount
	
	public void removeSeatsFromRoom(int lowerBoundary, int upperBoundary) {
		/*this Function is meant to remove the seats that have their
		 *  seatNumber between the two boundaries, boundaries included.
		 *  having the same number N as boundaries means the seat with the number N
		 *  will be removed
		 */
		
		if (upperBoundary>= lowerBoundary && lowerBoundary>0 && upperBoundary <= this.numberOfSeats )
		{
			for (int i = 0; i<this.numberOfSeats; i++) {
				if(this.roomSeats[i].getSeatNumber()>= lowerBoundary && this.roomSeats[i].getSeatNumber()<= upperBoundary) {
					this.roomSeats[i].deleteSeat();
				}
				else if(this.roomSeats[i].getSeatNumber()> upperBoundary) { 
					this.roomSeats[i].updateSeat((upperBoundary+1-lowerBoundary));
				}
				
				
			}// for loop	
		}// initial if condition
		else System.out.println("Error: Delete arguments out of boundaries");
	}// Method removeSeatsFromRoom()
}// Class Room
