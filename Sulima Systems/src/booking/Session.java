package booking;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import core.Database;
import core.Global;

public class Session implements Cloneable{

	private int session_id;
	private Date date;
	private int room_id;
	private int movie_id;

	public Session() {
		date = new Date();
		room_id = 0;
		movie_id = 0;
		//		SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd HH:mm");
		//		Date currentTime = simpleDate.parse("2012-04-19 20:51:06");
		//		roomId = 0;
		//		movieId = 0;
		//		System.out.println(toString(currentTime));
	}

//	public Session clone() {
//		try {
//			return (Session)super.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public int getSession_id() {
		return this.session_id;
	}
	
	public int getRoom_id() {
		return this.room_id;
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setSession(int session_id, Date date, int room_id, int movie_id) {
		this.session_id = session_id;
		this.date = date;
		//		try {
		//			d = Global.sdf.parse("2012-04-19 20:51:06");
		//		} catch (ParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		this.room_id = room_id;
		this.movie_id= movie_id;
	}

	public void setDate() {

		Scanner scan = new Scanner(System.in);
		boolean done = false;

		while (!done) {
			System.out.println("Enter a date in the following format:\nyyyy-MM-dd HH:mm");

			String dateString = scan.nextLine();
			dateString = dateString+":00";
			try {
				this.date = Global.DATABASEDF.parse(dateString);
				done = true;
				scan.close();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Wrong you idiot");
			}
		}
	}

	public void saveToDatabase() {
		Database db = new Database();
		db.insertIntoDatabase("INSERT INTO SESSIONS (session_date)"
				+ " VALUES ('"+ Global.DATABASEDF.format(this.date)+ "')");
	}

	public String toString() {
		String string;
		string = "Session: "+ this.session_id + ", Movie: " +this.movie_id+ ", Room: "+this.room_id+", date: "+Global.USERDF.format(this.date);
		return string;
	}
}



