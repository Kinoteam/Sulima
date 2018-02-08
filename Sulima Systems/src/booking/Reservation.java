package booking;

import java.util.Scanner;

public class Reservation {

	private int reservation_id;
	private int movie_id;
	private int session_id;
	private int seat_id;
	private int seatNumber;
	private int user_id;
	Seat[] seats;
	Movie[] movies;
	Session[] sessions;

	public Reservation() {
		this.reservation_id = 0;
		this.movie_id = 0;
		this.session_id = 0;
		this.seat_id = 0;
		this.seatNumber = 0;
		this.user_id = 0;
		this.movies = null;
		this.sessions = null;
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

	public void selectMovie() {

		boolean valid = false;
		Scanner scan = new Scanner(System.in);
		int selected;
		do {

			System.out.print("Please enter the Id of the selected movie: ");
			selected =  scan.nextInt();



			for (int i = 0; i<this.movies.length; i++) {
				if (selected == this.movies[i].getMovieId()) {
					valid = true;
					break;
				}
			}
		}while (!valid);
		
		this.movie_id = selected;
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

	public void selectSession() {
		
		boolean valid = false;
		Scanner scan = new Scanner(System.in);
		int selected;
		do {

			System.out.print("Please enter the Id of the selected Session: ");
			selected =  scan.nextInt();



			for (int i = 0; i<this.sessions.length; i++) {
				if (selected == this.sessions[i].getSessionId()) {
					valid = true;
					break;
				}
			}
		}while (!valid);

		this.session_id = selected;
		Database db = new Database();
		String query = "SELECT COUNT(*) FROM SEATS s left OUTER join RESERVATIONS r on s.seat_id= r.seat_id "
				+ "where r.seat_id is null or (session_id != " + session_id + " or session_id is null)";
		int rowsCount = db.getIntFromDatabase(query, "COUNT(*)");
		if (rowsCount > 0) {

			seats= new Seat[rowsCount];
			db.fetchFreeSeatsFromDatabase(seats, session_id, rowsCount);
			System.out.println("Free Seats loaded");

			for (int i=0; i<rowsCount; i++) {
				System.out.println(seats[i].toString());
			}
		}
	}	
}


