package booking;

import java.time.LocalDateTime;
import java.util.Scanner;

import authentication.User;
import core.*;

public class Reservation {

	private int reservation_id;
	private int movie_id;
	private int session_id;
	private int room_id;
	private int seat_id;
	private int seatNumber;
	private String movieName;
	private LocalDateTime date;
	Movie[] movies;
	Session[] sessions;
	Seat[] seats;
	Database db = new Database();
	Scanner scan = new Scanner(System.in);

	public Reservation() {
		this.reservation_id = 0;
		this.movie_id = 0;
		this.session_id = 0;
		this.room_id = 0;
		this.seat_id = 0;
		this.seatNumber = 0;
		this.movies = null;
		this.sessions = null;
		this.seats = null;
	}

	/*
	 * only needed for the unit test
	 */
	public int getSeat_id() {
		return this.seat_id;
	}

	/*
	 * Shows all the available movies in the Database
	 * 
	 */
	public boolean showMovies() {

		String query = "SELECT COUNT(*) FROM MOVIES";
		String key = "COUNT(*)";
		int rowsCount = db.getInt(query, key);

		if (rowsCount > 0) {

			this.movies= new Movie[rowsCount];
			db.fetchMovies(movies, rowsCount);
			System.out.println("Movies loaded");

			for (int i=0; i<rowsCount; i++) {
				System.out.println(movies[i].toString());
			}

			// If there are movies, proceed to select a movie	
			return this.selectMovie();
		}

		else System.out.println("No Movies in database");
		query = null; key=null;
		return false;
	}

	private boolean selectMovie() {

		boolean valid = false;
		int selected;
		// Verify if the entered Id is valid, i.e. there is a movies with that Id
		do {

			System.out.print("Please enter the Id of the selected movie: ");
			selected =  scan.nextInt();

			for (int i = 0; i<this.movies.length; i++) {
				if (selected == this.movies[i].getMovie_id()) {
					this.movieName = this.movies[i].getMovieName();
					valid = true;
					break;
				}
			}
		}while (!valid);

		this.movie_id = selected;

		// Once the movie is selected, show the available sessions
		return showSessions();
	}

	/*
	 * Shows all the sessions containing the previously selected movie
	 */
	private boolean showSessions() {

		String query = "SELECT COUNT(*) FROM SESSIONS where movie_id = "+ movie_id;
		int rowsCount = db.getInt(query, "COUNT(*)");
		if (rowsCount > 0) {

			this.sessions= new Session[rowsCount];
			db.fetchSessions(sessions, rowsCount , movie_id);
			System.out.println("Sessions loaded");

			for (int i=0; i<rowsCount; i++) {
				System.out.println(sessions[i].toString());
			}

			// Proceed to select a session
			return this.selectSession();
		}

		else System.out.println("This Movie is not on schedule");
		return false;
	}

	private boolean selectSession() {

		boolean valid = false;
		int selected;

		do {

			System.out.print("Please enter the Id of the selected Session: ");
			selected =  scan.nextInt();



			for (int i = 0; i<this.sessions.length; i++) {
				if (selected == this.sessions[i].getSession_id()) {
					this.room_id = this.sessions[i].getRoom_id();
					this.date = this.sessions[i].getDate();
					valid = true;
					break;
				}
			}
		}while (!valid);

		this.session_id = selected;

		// After the session is selected, show the available free seats in that session
		return showFreeSeats();
	}

	/*
	 * Shows the free seats in the previously selected session
	 * 
	 */
	private boolean showFreeSeats() {

		// Step 1: get the count and the Id's of the already booked seats in that session
		int reservedSeatIds[] = null;
		String key = "COUNT(*)";
		String query1 = "SELECT COUNT(*) FROM RESERVATIONS where session_id = " + this.session_id;
		int rowsToSubstract = db.getInt(query1, key);
		if (rowsToSubstract>0) {

			reservedSeatIds= new int[rowsToSubstract];
			db.fetchReservedSeatsId(reservedSeatIds, rowsToSubstract, this.session_id);
		}

		// Step 2: get the count of the free seats in that session
		String query2 = "SELECT COUNT(*) FROM SEATS where `room_id` = " + this.room_id;
		int rowsCount = db.getInt(query2, "COUNT(*)");

		// Step 3: verify if there are free Seats, and if so, display them
		if (rowsCount-rowsToSubstract> 0) {

			seats= new Seat[rowsCount];
			db.fetchSeats( room_id, seats, rowsCount);

			boolean isReserved;

			for (int i=0; i<rowsCount; i++) {

				if (rowsToSubstract >0) 
				{
					isReserved = false;
					for (int j = 0; j< rowsToSubstract; j++) 
					{
						if (seats[i].getSeat_id() == reservedSeatIds[j]) 
						{
							isReserved = true;
						}
					}

					if (!isReserved) 
					{
						System.out.println(seats[i].toString());
					}

				}
				else System.out.println(seats[i].toString());
			}

			// Proceed to select a seat
			return this.selectSeat(reservedSeatIds, rowsToSubstract);
		}

		else System.out.println("No free seats available");
		return false;
	}

	private boolean selectSeat(int[] reservedSeatIds, int rowsToSubstract) {
		boolean valid = true;
		int selected;

		// Verify if the entered seat number is valid, i.e. there is a seat with that number in this session
		// and then if that seat is not already booked
		do {
			System.out.print("Please enter the number of the selected Seat: ");
			selected =  scan.nextInt();

			for (int i = 0; i<this.seats.length; i++) 
			{
				if (selected == this.seats[i].getSeatNumber()) 
				{
					valid = true;
					for (int j = 0; j< rowsToSubstract; j++) 
					{
						if (seats[i].getSeat_id() == reservedSeatIds[j]) 
						{
							valid = false;
							break;
						}
					}

					if (valid) 
					{
						this.seatNumber = selected;
						selected = this.seats[i].getSeat_id();
						break;
					}
				}
			}
		}while (!valid);

		this.seat_id = selected;
		// After the seat is selected, make a reservation
		return save();

	}
	/*
	 * Checks if a user is logged, if so proceeds to book the selected seat
	 * 
	 */
	private boolean save() {

		if (Global.user_id!=0) 
		{
			String query = "INSERT INTO RESERVATIONS (seat_id, session_id , user_id) "
						 + "VALUES (" + this.seat_id + "," + this.session_id + ", " + Global.user_id +" )";
			db.update(query);
			System.out.println(this.toString());
			return true;
		}

		else {

			User user=new User();
			user.login();
			String query = "INSERT INTO RESERVATIONS (seat_id, session_id , user_id) "
						 + "VALUES (" + this.seat_id + "," + this.session_id + ", " + Global.user_id +" )";
			db.update(query);
			System.out.println(this.toString());
			return true;
		}
	}

	public String toString() {
		String query = "SELECT * FROM RESERVATIONS where `session_id` = " +session_id+ " AND seat_id = "+ seat_id;
		String key = "reservation_id";
		this.reservation_id = db.getInt(query, key);
		String string = "Ticket N: " + reservation_id + ". " +
				this.date.format(Global.USER_FORMAT) +
				". Movie: " +this.movieName+ ". Room: " + this.room_id +
				". Seat n: " + this.seatNumber;
		return string;
	}
}