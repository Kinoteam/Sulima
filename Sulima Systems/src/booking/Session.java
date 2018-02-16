package booking;

//import java.sql.Timestamp;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.*;
import java.util.Date;
import java.util.Scanner;

public class Session implements Cloneable{

	private int session_id;
	private Date time;
	private int room_id;
	private int movie_id;

	public Session() {
		time = new Date();
		room_id = 0;
		movie_id = 0;
		//		SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd HH:mm");
		//		Date currentTime = simpleDate.parse("2012-04-19 20:51:06");
		//		roomId = 0;
		//		movieId = 0;
		//		System.out.println(toString(currentTime));
	}

	public Session clone() {
		try {
			return (Session)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void set(int session_id, Date date, int room_id, int movie_id) {
		this.session_id = session_id;
		this.time = date;
		//		try {
		//			d = Global.sdf.parse("2012-04-19 20:51:06");
		//		} catch (ParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		this.room_id = room_id;
		this.movie_id= movie_id;
	}

	public void setDateManually() {

		Scanner scan = new Scanner(System.in);
		boolean done = false;

		while (!done) {
			System.out.println("Enter a date in the following format:\nyyyy-MM-dd HH:mm");

			String dateString = scan.nextLine();
			dateString = dateString+":00";
			try {
				this.time = Global.databasedf.parse(dateString);
				done = true;
				scan.close();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Wrong you idiot");
			}
		}
	}

	public int getSessionId() {
		return this.session_id;
	}
	
	public int getRoomId() {
		return this.room_id;
	}

	public void saveToDatabase() {
		Database db = new Database();
		db.insertIntoDatabase("INSERT INTO SESSIONS (session_time)"
				+ " VALUES ('"+ Global.databasedf.format(this.time)+ "')");
	}


	public String toString() {
		String string;
		string = "Session: "+ this.session_id + ", Movie: " +this.movie_id+ ", Room: "+this.room_id+", time: "+Global.userdf.format(this.time);
		return string;
	}

}



