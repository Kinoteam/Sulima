package booking;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import core.Database;
import core.Global;

public class Session {

	private int session_id;
	private LocalDateTime date;
	private int room_id;
	private int movie_id;
	private Database db = new Database();

	public Session(int room_id, int movie_id) {

		try 
		{
			this.setDate();
		} 
		catch (ParseException e) {}				// Exception already handled in method setDate()
		this.room_id = room_id;
		this.movie_id= movie_id;
	}
	
	public Session(int session_id, int room_id, int movie_id, String dateString) {

		try 
		{
			this.date =  LocalDateTime.parse(dateString, Global.PARSING_FORMAT );
		} 
		catch (Exception e) {}
		this.session_id = session_id;
		this.room_id = room_id;
		this.movie_id= movie_id;
	}

	public Session(int session_id, LocalDateTime date, int room_id, int movie_id) {
		this.session_id = session_id;
		this.date = date;		
		this.room_id = room_id;
		this.movie_id= movie_id;
	}

	public int getSession_id() {
		return this.session_id;
	}

	public int getRoom_id() {
		return this.room_id;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	private void setDate() throws ParseException {

		Scanner scan = new Scanner(System.in);
		String dateString;
		boolean done = false;
		while (!done) {
			try 
			{
				System.out.println("Enter a date in the following format:\nyyyy-MM-dd HH:mm");
				dateString = scan.nextLine();
				dateString += ":00";
				this.date = LocalDateTime.parse(dateString, Global.PARSING_FORMAT );
				done = true;

			} 
			catch (Exception e)
			{
				System.err.println("Error parsing");
			}
		}
	}

	public boolean save() {
		db.update( "INSERT INTO sessions (movie_id, room_id, session_time) "
				+ "VALUES (" +this.movie_id+ " , "+this.room_id+" , " + "'"+ this.date.format(Global.DB_FORMAT)+ "')");
		System.out.println("Session saved");
		return true;
	}

	public boolean delete() {
		if (!this.isUsed()|| this.isOld()) 
		{
			db.update("DELETE from reservations WHERE session_id = " +this.session_id);
			db.update("DELETE FROM sessions WHERE session_id = " + this.session_id);
			System.out.println("Reservations for this session deleted");
			return true;
		}

		else System.err.println("Error: this session is still in use");
		return false;
	}

	private boolean isOld() {
		return (LocalDate.now()).isAfter(this.date.toLocalDate());
	}
	
	private boolean isUsed() {
		return (db.existsInDatabase("SELECT * from reservations WHERE session_id =" +this.session_id));
	}

	public String toString() {
		return  "Session: "+ this.session_id + ", Movie: " +this.movie_id+
				", Room: "+ this.room_id+", date: "+ this.date.format(Global.USER_FORMAT);
	}
}




