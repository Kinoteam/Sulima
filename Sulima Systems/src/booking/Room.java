package booking;

import core.Database;

public class Room{

	private int room_id;
	private int numberOfSeats;
	private int numberOfRegularSeats;
	private int numberOfLoveSeats;
	private int numberOfLogeSeats;
	private Seat[] roomSeats;
	Database db = new Database();

	public Room() {

		this.numberOfRegularSeats = 0;
		this.numberOfLoveSeats = 0;
		this.numberOfLogeSeats = 0;
		this.numberOfSeats = 0;
		this.roomSeats = null;
	}

	public Room(int room_id){
		this.room_id = room_id;
	}

	/*
	 * This constructor is meant as a quick way to set up a room and its seats, 
	 * so it has to check if the room_id provided is not
	 * already existing in the database
	 */
	public Room(int room_id, int numberOfRegularSeats, int numberOfLoveSeats, int numberOfLogeSeats){

		// Step 1: create the room and save it to database
		if (room_id > 0 && numberOfRegularSeats >=0 && numberOfLoveSeats >=0 && numberOfLogeSeats >=0) 
		{
			if (isIdUnique(room_id))
			{ 
				this.room_id = room_id;  
				this.numberOfRegularSeats = 0;
				this.numberOfLoveSeats = 0;
				this.numberOfLogeSeats = 0;
				this.numberOfSeats = 0;
				roomSeats = null;
				save();  

				// Step 2: add seats to the room
				if (numberOfRegularSeats > 0)
				{
					addSeats(numberOfRegularSeats, SeatType.REGULAR);
				}

				if (numberOfLoveSeats > 0)
				{
					addSeats(numberOfLoveSeats, SeatType.LOVESEAT);
				}

				if (numberOfLogeSeats > 0)
				{
					addSeats(numberOfLogeSeats, SeatType.LOGE);
				}
			}

			else 
			{
				System.err.println("Error: Room already exists");
				this.room_id = 0;
			}
		}

		else {
			System.err.println("Error: One or more parameters not allowed");
			this.room_id = 0;
		}
	}

	public int getRoom_id() {
		return this.room_id;
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

	/* 
	 * This setter is meant as a way to manually set a room_id, 
	 * as such, a check must be done to ensure that the new room_id
	 * is not already existing in the database, 
	 * and that the reference to the room is also updated in the seats
	 * belonging to that room, if there are any
	 */
	public void setRoom_id(int room_id) {


		if(room_id > 0 && isIdUnique(room_id))
		{ 
			// Step 1: Set the room_id reference to null in the seats table
			if(this.roomSeats != null)
			{
				for (int i=0; i<this.numberOfSeats; i++) 
				{
					this.roomSeats[i].unsetRoom_id();
				}

				// Step 2: save the new room_id to the database 

				this.changeRoom_id(room_id);
				this.room_id = room_id;

				// Step 3: update the room_id reference unset in Step 1

				for (int i=0; i<this.numberOfSeats; i++) 
				{
					this.roomSeats[i].updateRoom_id(room_id);
				}
			}
			else 
			{
				this.changeRoom_id(room_id);
				this.room_id = room_id;
			}
		}
		else System.err.println("Error: Room already exists or new Id is invalid");
	}

	private void setNumberOfSeats() {

		for (int i = 0; i< this.roomSeats.length; i++) 
		{

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
			else System.err.println("Error: something weird happened at Class Room, Method setNumberOfSeats()");
		}
		numberOfSeats = numberOfRegularSeats + numberOfLoveSeats + numberOfLogeSeats;
	}

	public void updateNumberOfSeats(int seatsAdded, SeatType seatType) {

		switch (seatType) 
		{
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
			System.err.println("Error Class Room, Method updateNumberOfSeats()");
		}
	}

	private void save() {

		String query = "INSERT INTO rooms (room_id) VALUES (" + room_id +")";
		db.update(query);
		query=null;
	}

	private void changeRoom_id(int room_id) {
		String query = "UPDATE rooms SET room_id = " + room_id +" WHERE room_id = " + this.room_id;
		db.update(query);
		query=null;
	}

	private boolean isIdUnique(int room_id) {
		String query = "SELECT * FROM rooms WHERE room_id = " + room_id;
		return (!db.existsInDatabase(query));
	}


	public void loadSeats() {
		
		// Before: check if there are seats in that room
		Seat[] seats = null;
		String query = "SELECT COUNT(*) FROM SEATS WHERE room_id = " + room_id;
		String key = "COUNT(*)";
		int rowsCount = db.getInt(query, key);

		// Begin: Load Seats
		if (rowsCount > 0)
		{
			seats= new Seat[rowsCount];
			db.fetchSeats( room_id ,seats, rowsCount);
			roomSeats = seats;
			setNumberOfSeats();
		}

		else System.out.println("No seats in this room");
		query = null; key=null; seats=null;
	}

	public void addSeats(int seatsToAdd, SeatType seatType) {

		// Step 1: create seats and set their reference to this room_id
		Seat[] seats= new Seat[seatsToAdd];
		for (int i= 0; i < seatsToAdd; i++)
		{
			seats[i] = new Seat(this.numberOfSeats + 1+i, seatType);
			seats[i].setRoom_id(this.room_id);
			seats[i].save();
		}
		
		// Step 2: increment the seat count
		if (seatType == SeatType.REGULAR)
		{
			this.numberOfRegularSeats+= seatsToAdd;
			this.numberOfSeats += seatsToAdd;
		}

		else if (seatType == SeatType.LOVESEAT)
		{
			this.numberOfLoveSeats+= seatsToAdd;
			this.numberOfSeats += seatsToAdd;
		}

		else if (seatType == SeatType.LOGE)
		{
			this.numberOfLogeSeats= seatsToAdd;
			this.numberOfSeats += seatsToAdd;
		}
		else
		{
			System.err.println("Error at Class Room, Method addSeatsToRoom()");
		}

		this.roomSeats = seats;
		seats=null; 
	}

	/*
	 *  This Function is meant to remove the seats that have their
	 *  seatNumber between the two boundaries, boundaries included.
	 *  If the boundaries are the same, only the seat with that
	 *  number will be removed.
	 */
	public void deleteSeats(int lowerBoundary, int upperBoundary) {

		if (upperBoundary>= lowerBoundary && lowerBoundary>0 && upperBoundary <= this.numberOfSeats )
		{
			for (int i = 0; i<this.numberOfSeats; i++) 
			{
				// Step 1: Remove the seats between the 2 boundaries
				if(this.roomSeats[i].getSeatNumber()>= lowerBoundary && this.roomSeats[i].getSeatNumber()<= upperBoundary) 
				{
					this.roomSeats[i].delete();
				}
				// Step 2: update the seat number of the remaining seats
				else if(this.roomSeats[i].getSeatNumber()> upperBoundary) 
				{ 
					this.roomSeats[i].update((upperBoundary+1-lowerBoundary));
				}
			}	
		}
		else System.out.println("Error at Class Room, Method deleteSeatsFromRoom(int int)");
	}

	public void delete() {

		// Step 1: delete the Seats if they exist
		if (this.numberOfSeats!=0) {
			deleteSeats(1 , this.numberOfSeats);
		}
		this.roomSeats = null;

		// Step 2: delete the Room
		String query = "DELETE FROM rooms WHERE room_id = " + this.room_id;
		db.update(query);
	}

	public String toString() {
		String string ="";
		string = "Room: " + room_id + ", Seats: " + this.numberOfSeats + ", Regular seats: " + this.numberOfRegularSeats +
				", Loveseats: " + this.numberOfLoveSeats + ", Loges:" + this.numberOfLogeSeats;
		return string;
	}
}