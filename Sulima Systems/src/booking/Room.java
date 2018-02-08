package booking;

public class Room{

	private int room_id; // unique
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
	public Room(int room_id){

		this(room_id,0,0,0);

	}// Room(room_id) constructor

	public Room(int room_id, int numberOfRegularSeats, int numberOfLoveSeats, int numberOfLogeSeats){

		/*because the room has to be saved in order to add seats to it,
		 * only a valid room_id will be accepted, 
		 * this means room_id > 0 and Unique.
		 */
		if (room_id > 0 && numberOfRegularSeats >=0 && numberOfLoveSeats >=0 && numberOfLogeSeats >=0) {
			if (isIdUnique(room_id))  /* if the room_id is unique*/
			{ 
				/*create and save the room before adding seats*/
				this.room_id = room_id;  
				this.numberOfRegularSeats = 0;
				this.numberOfLoveSeats = 0;
				this.numberOfLogeSeats = 0;
				this.numberOfSeats = 0;
				roomSeats = null;

				saveRoom();  /* the room must be saved to Database because
				 * the room_id is a Foreign key in the table Seats
				 **/

				/*now can add seats*/

				if (numberOfRegularSeats > 0)
				{
					addSeatsToRoom(numberOfRegularSeats, SeatType.REGULAR);
				}

				if (numberOfLoveSeats > 0)
				{
					addSeatsToRoom(numberOfLoveSeats, SeatType.LOVESEAT);

				}

				if (numberOfLogeSeats > 0)
				{
					addSeatsToRoom(numberOfLogeSeats, SeatType.LOGE);
				}
			}// if room_id is unique
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
		return this.room_id;
	}

	public void updateRoomId(int room_id) {
		this.room_id = room_id;
	}

	public void setRoomId(int room_id) {

		/* This check is done by a query to database because a Room has
		 * no way of checking the other rooms, if one already has this room_id.
		 * The other way could be a check performed from the Main function.
		 */

		if (room_id > 0) {
			if(isIdUnique(room_id))   /* only if the new room_id is unique*/
			{ 
				if(this.roomSeats != null) {
					/*Step 1: set the foreign key "room_id" 
					 * to Null for all seats belonging to this room
					 * */
					for (int i=0; i<this.numberOfSeats; i++) {
						this.roomSeats[i].setRoomIdToNull();
					}

					/*Step 2: update the room_id in the database 
					 * then change the class variable room_id
					 * */

					this.updateRoom(room_id);
					this.room_id = room_id;

					/*Step 3: assign all the previously unassigned seats
					 * to the new room_id
					 */

					for (int i=0; i<this.numberOfSeats; i++) {
						this.roomSeats[i].updateRoomIdOfSeat(room_id);
					}

				}// if(this.roomSeats != null)

				else {
					this.updateRoom(room_id);
					this.room_id = room_id;
				}

			}// if(isIdUnique(room_id))
			else System.out.println("Error: Room already exists in database");
		}// if room_id >0
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

	public void setRoom(int room_id, int numberOfRegularSeats, int numberOfLoveSeats, int numberOfLogeSeats, Seat[] seats){
		this.room_id = room_id;
		this.numberOfRegularSeats = 0;
		this.numberOfLoveSeats = 0;
		this.numberOfLogeSeats = 0;
		this.numberOfSeats = 0;
		this.roomSeats = seats;
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

		if (this.room_id>0) {
			Database db = new Database();
			String query = "INSERT INTO ROOMS (room_id)"
					+ " VALUES (" + room_id +")";
			db.updateDatabase(query);

			query=null; db = null; /*clearing memory*/
		}
		else {
			System.out.println("saveRoom() at Room: Cannot save a room with this room_id");
		}
	}// ...saveRoom()

	private void updateRoom(int room_id) {

		Database db = new Database();
		String query = "UPDATE ROOMS SET room_id = " + room_id +" WHERE room_id = " + this.room_id;

		db.updateDatabase(query);
		query=null; /*clearing memory*/
		//		}

		//		else System.out.println("Error: room already in database");
		db = null; /*chkQuery = null;*/ /*clearing memory*/
	}// updateRoom()

	private boolean isIdUnique(int room_id) {
		String query = "SELECT * FROM ROOMS WHERE room_id = " + room_id;
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
		String query = "SELECT COUNT(*) FROM SEATS WHERE room_id = " + room_id;
		String key = "COUNT(*)";
		int rowsCount = db.getIntFromDatabase(query, key);

		if (rowsCount > 0) {

			seats= new Seat[rowsCount];
			db.fetchSeatsFromDatabase( room_id ,seats, rowsCount);
			System.out.println("Seats loaded");
			roomSeats = seats;
			setNumberOfSeats();
		}

		else System.out.println("No seats in this room");

		db = null; query = null; key=null; seats=null; /*clearing memory*/

	}// loadSeats()

	public void addSeatsToRoom(int seatsToAdd, SeatType seatType) {

		Seat[] seats= new Seat[seatsToAdd];
		for (int i= 0; i < seatsToAdd; i++)
		{
			// create a new instance of Seat in the Array seats
			seats[i] = new Seat(this.numberOfSeats + 1+i, seatType);

			// set the RoomId variable IN the Seat instance to that of the Room it belongs to.
			seats[i].setRoomIdOfSeat(this.room_id);
			seats[i].saveSeat();
		}// for loop
		if (seatType == seatType.REGULAR)
		{
			this.numberOfRegularSeats+= seatsToAdd;
			this.numberOfSeats += seatsToAdd;
			System.out.println(this.getNumberOfSeats());
		}

		else if (seatType == seatType.LOVESEAT)
		{
			this.numberOfLoveSeats+= seatsToAdd;
			this.numberOfSeats += seatsToAdd;
			System.out.println(this.getNumberOfSeats());
		}

		else if (seatType == seatType.LOGE)
		{

			this.numberOfLogeSeats= seatsToAdd;
			this.numberOfSeats += seatsToAdd;
			System.out.println(this.getNumberOfSeats());
		}
		else
			System.out.println(this.getNumberOfSeats());

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
		String query = "DELETE FROM rooms WHERE room_id = " + this.room_id;
		db.updateDatabase(query);
		db = null;

	}// removeRoom()

	public String toString() {
		String string ="";
		string = "Room: " + room_id + ", Seats: " + this.numberOfSeats + ", Regular seats: " + this.numberOfRegularSeats +
				", Loveseats: " + this.numberOfLoveSeats + ", Loges:" + this.numberOfLogeSeats;
		return string;
	}
}// Class Room
