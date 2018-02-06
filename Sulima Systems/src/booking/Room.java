package booking;

public class Room {

	private int roomId; // unique
	private int numberOfSeats;
	private int numberOfRegularSeats;
	private int numberOfLoveSeats;
	private int numberOfLogeSeats;
	private Seat[] roomSeats; // Array seats belonging to this room


	public Room() {

		this.numberOfRegularSeats = 0;
		this.numberOfLoveSeats = 0;
		this.numberOfLogeSeats = 0;
		this.numberOfSeats = 0;
		this.roomSeats = null;
	}
	public Room(int roomId){

		this(roomId,0,0,0);

	}// Room(roomId) constructor

	public Room(int roomId, int numberOfRegularSeats, int numberOfLoveSeats, int numberOfLogeSeats){

		/*because the room has to be saved in order to add seats to it,
		 * only a valid roomId will be accepted, 
		 * this means roomId > 0 and Unique.
		 */
		if (roomId > 0 && numberOfRegularSeats >=0 && numberOfLoveSeats >=0 && numberOfLogeSeats >=0) {
			if (isIdUnique(roomId))  /* if the roomId is unique*/
			{ 
				/*create and save the room before adding seats*/
				this.roomId = roomId;      
				this.numberOfRegularSeats = 0;
				this.numberOfLoveSeats = 0;
				this.numberOfLogeSeats = 0;
				this.numberOfSeats = 0;
				roomSeats = null;

				saveRoom();  /* the room must be saved to Database because
				 * the roomId is a Foreign key in the table Seats
				 **/

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
		}// if args>0

		else System.out.println("Error: One or more parameters not allowed");

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

	public void updateRoomId(int roomId) {
		this.roomId = roomId;
	}
	public void setRoomId(int roomId) {

		/* This check is done by a query to database because a Room has
		 * no way of checking the other rooms, if one already has this roomId.
		 * The other way could be a check performed from the Main function.
		 */

		if (roomId > 0) {
			if(isIdUnique(roomId))   /* only if the new roomId is unique*/
			{ 
				if(this.roomSeats != null) {
					/*Step 1: set the foreign key "room_id" 
					 * to Null for all seats belonging to this room
					 * */
					for (int i=0; i<this.numberOfSeats; i++) {
						this.roomSeats[i].setRoomIdToNull();
					}

					/*Step 2: update the room_id in the database 
					 * then change the class variable roomId
					 * */

					this.updateRoom(roomId);
					this.roomId = roomId;

					/*Step 3: assign all the previously unassigned seats
					 * to the new room_id
					 */

					for (int i=0; i<this.numberOfSeats; i++) {
						this.roomSeats[i].updateRoomIdOfSeat(roomId);
					}

				}// if(this.roomSeats != null)

				else {
					this.updateRoom(roomId);
					this.roomId = roomId;
				}

			}// if(isIdUnique(roomId))
			else System.out.println("Error: Room already exists in database");
		}// if roomId >0
		else System.out.println("Error: RoomId not allowed");

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
			System.out.println("updateNumberOfSeats at Class Room: Something went");
		}

	}

	public void saveRoom() {

		if (this.roomId>0) {
			Database db = new Database();
			String query = "INSERT INTO ROOMS (room_id)"
					+ " VALUES (" + roomId + ")";
			db.updateDatabase(query);

			query=null; db = null; /*clearing memory*/
		}
		else {
			System.out.println("saveRoom() at Room: Cannot save a room with this room_id");
		}
	}// saveRoom()

	private void updateRoom(int roomId) {

		Database db = new Database();
		String query = "UPDATE ROOMS SET room_id = " + roomId +" WHERE room_id = " + this.roomId;

		db.updateDatabase(query);
		query=null; /*clearing memory*/
		//		}

		//		else System.out.println("Error: room already in database");
		db = null; /*chkQuery = null;*/ /*clearing memory*/
	}// updateRoom()

	private boolean isIdUnique(int roomId) {
		String query = "SELECT * FROM ROOMS WHERE room_id = " + roomId;
		Database db = new Database();
		boolean isUnique = false;
		if(!db.isAlreadyInDatabase(query)){
			return !isUnique;
		}
		else return isUnique;
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

	public void removeRoom() {

		if (this.numberOfSeats!=0) {
			removeSeatsFromRoom(1 , this.numberOfSeats);
		}
		this.roomSeats = null;
		Database db = new Database();
		String query = "DELETE FROM rooms WHERE room_id = " + this.roomId;
		db.updateDatabase(query);
		db = null;

	}// removeRoom()
}// Class Room
