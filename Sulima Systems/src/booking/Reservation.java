package booking;

import java.util.Date;
import java.util.Scanner;
import core.*;

public class Reservation {

	private int reservation_id;
	private int movie_id;
	private int session_id;
	private int room_id;
	private int seat_id;
	private int seatNumber;
	private String movieName;
	private Date date;
	Movie[] movies;
	Session[] sessions;
	Seat[] seats;
	Scanner scan;

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

	public int getSeat_id() {
		return this.seat_id;
	}
	
	public void showMovies() {

		Database db = new Database();
		String query = "SELECT COUNT(*) FROM MOVIES";
		String key = "COUNT(*)";
		int rowsCount = db.getIntFromDatabase(query, key);

		if (rowsCount > 0) {

			this.movies= new Movie[rowsCount];
			db.fetchMoviesFromDatabase(movies, rowsCount);
			System.out.println("Movies loaded");

			for (int i=0; i<rowsCount; i++) {
				System.out.println(movies[i].toString());
			}

			this.selectMovie();
		}

		else System.out.println("No Movies in database");

		db = null; query = null; key=null;
	}

	private void selectMovie() {

		boolean valid = false;
		this.scan = new Scanner(System.in);
		int selected;
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
		showSessions();
	}

	private void showSessions() {
		Database db = new Database();
		String query = "SELECT COUNT(*) FROM SESSIONS where movie_id = "+movie_id;
		int rowsCount = db.getIntFromDatabase(query, "COUNT(*)");
		if (rowsCount > 0) {

			this.sessions= new Session[rowsCount];
			db.fetchSessionsFromDatabase(sessions, movie_id, rowsCount);
			System.out.println("Sessions loaded");

			for (int i=0; i<rowsCount; i++) {
				System.out.println(sessions[i].toString());
			}

			this.selectSession();
		}

		else System.out.println("This Movie is not on schedule");
	}

	private void selectSession() {

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
		showFreeSeats();
	}

	private void showFreeSeats() {
		int array[] = null;
		Database db = new Database();

		String key = "COUNT(*)";
		String query1 = "SELECT COUNT(*) FROM RESERVATIONS where session_id = " + this.session_id;
		int rowsToSubstract = db.getIntFromDatabase(query1, key);
		if (rowsToSubstract>0) {

			array= new int[rowsToSubstract];
			db.fetchReservedSeats(array, rowsToSubstract, this.session_id);
		}

		String query2 = "SELECT COUNT(*) FROM SEATS where `room_id` = " + this.room_id;
		int rowsCount = db.getIntFromDatabase(query2, "COUNT(*)");
		if (rowsCount-rowsToSubstract> 0) {

			seats= new Seat[rowsCount];
			db.fetchSeatsFromDatabase( room_id, seats, rowsCount);

			boolean isReserved;

			for (int i=0; i<rowsCount; i++) {

				if (rowsToSubstract >0) {
					isReserved = false;
					for (int j = 0; j< rowsToSubstract; j++) {
						if (seats[i].getSeat_id() == array[j]) {
							isReserved = true;
						}
					}

					if (!isReserved) {
						System.out.println(seats[i].toString());
					}

				}
				else System.out.println(seats[i].toString());
			}

			this.selectSeat();
		}

		else System.out.println("No free seats available");
	}

	private void selectSeat() {
		boolean valid = false;
		int selected;

		do {

			System.out.print("Please enter the number of the selected Seat: ");
			selected =  scan.nextInt();



			for (int i = 0; i<this.seats.length; i++) {
				if (selected == this.seats[i].getSeatNumber()) {
					this.seatNumber = selected;
					selected = this.seats[i].getSeat_id();
					valid = true;
					break;
				}
			}
		}while (!valid);
		
		this.seat_id = selected;
		Database db = new Database();
		String query = "INSERT INTO RESERVATIONS (seat_id, session_id , user_id)"
				+ " VALUES (" + this.seat_id + "," + this.session_id + ", " + Global.user_id +" )";
		if (Global.user_id!=0) {
		db.insertIntoDatabase(query);
		System.out.println(this.toString());
		}
		else System.err.println("Error: Please log in");
		
	}

	public String toString() {
		Database db = new Database();
		String query = "SELECT * FROM RESERVATIONS where `session_id` = " +session_id+ " AND seat_id = "+ seat_id;
		String key = "reservation_id";
		this.reservation_id = db.getIntFromDatabase(query, key);
		String string = "Ticket N: " + reservation_id + ". " + Global.USERDF.format(this.date) + ". Movie: " +this.movieName+ ". Room: " + this.room_id + ". Seat n: " + this.seatNumber;
		return string;
	}
}


