package core;

import java.sql.*;

import booking.Movie;
import booking.Room;
import booking.Seat;
import booking.Session;

public class Database {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private boolean connected = false;


	private void connectToDatabase ()
	{
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sulima" , "root", "zbi");
			this.connected = true;
		}

		catch (Exception dbError){
			System.err.println("Error: Can't conect to Database");
		}

	}

	public boolean isAlreadyInDatabase(String query) {

		if (connection == null) {
			connectToDatabase();
		}

		boolean result = true;
		if (connected) {


			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (!resultSet.isBeforeFirst() ) {    
					result= false;
				}

				else result = true;
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
		return result;
	}

	public void insertIntoDatabase(String query) {

		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);

			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public void updateDatabase(String query) {
		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public int getCountWithCondition(String database, String column, String value) {

		if (connection == null) {
			connectToDatabase();
		}
		int result = 0;
		if(connected) {

			String query = "SELECT COUNT(*) FROM " + database + " where " + column + " = " + value;

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next()) {
					result = (resultSet.getInt("COUNT(*)"));
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
		return result;
	}

	public void fetchMoviesFromDatabase(Movie[] movies, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}
		if(connected) {

			String query = "SELECT * FROM MOVIES";

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsCount; i++)
					{
						movies[i] = new Movie();
						movies[i].setMovie(resultSet.getInt("movie_id"),resultSet.getString("movie_name") );
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public void fetchRoomsFromDatabase(Room[] rooms, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			String query = "SELECT * FROM ROOMS";

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsCount; i++)
					{
						rooms[i] = new Room();
						rooms[i].assignRoom_idFromDB(resultSet.getInt("room_id"));
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public void fetchAllSessionsFromDatabase(Session[] sessions, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}
		if(connected) {
			String query = "SELECT * FROM SESSIONS";

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsCount; i++)
					{
						sessions[i] = new Session();
						sessions[i].setSession(resultSet.getInt("session_id"),resultSet.getTimestamp("session_time"),
								resultSet.getInt("room_id"), resultSet.getInt("movie_id"));
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public void fetchSessionsFromDatabase(Session[] sessions, int movie_id, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			String query = "SELECT * FROM SESSIONS where movie_id =" + movie_id;

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsCount; i++)
					{
						sessions[i] = new Session();
						sessions[i].setSession(resultSet.getInt("session_id"),resultSet.getTimestamp("session_time"),
								resultSet.getInt("room_id"), resultSet.getInt("movie_id"));
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public void fetchSeatsFromDatabase(int room_id, Seat[] seats, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			String query = "SELECT * FROM SEATS WHERE room_id = " + room_id;

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsCount; i++)
					{
						seats[i] = new Seat();
						seats[i].setSeat(resultSet.getInt("seat_id"), resultSet.getInt("seat_number") , resultSet.getInt("room_id") ,
								resultSet.getString("seat_category"));
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public void fetchFreeSeatsFromDatabase(Seat[] seats, int session_id, int room_id, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			String query = "SELECT * FROM SEATS s left OUTER join RESERVATIONS r on s.seat_id = r.seat_id "
					+ "where s.room_id = " + room_id + " AND (r.session_id != " + session_id +" or r.session_id is null) ORDER BY seat_number";
			System.out.println(query);
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsCount; i++)
					{
						seats[i] = new Seat();
						seats[i].setSeat(resultSet.getInt("seat_id"), resultSet.getInt("seat_number") , resultSet.getInt("room_id") ,
								resultSet.getString("seat_category"));
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}

	public int getIntFromDatabase(String query, String key) {

		int result = 0;
		if (connection == null) {
			connectToDatabase();
		}

		if(connected) {

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next()) {
					result = resultSet.getInt(key);
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
		return result;
	}

	public void fetchReservedSeats(int[] array, int rowsToSubstract, int session_id) {
		if (connection == null) {
			connectToDatabase();
		}
		if(connected) {
			String query = "SELECT * FROM RESERVATIONS where session_id = " + session_id;

			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				if (resultSet.next())
				{
					for (int i=0; i<rowsToSubstract; i++)
					{
						array[i] = resultSet.getInt("seat_id");
						resultSet.next();
					}
				}
			}

			catch (Exception dbError){
				dbError.printStackTrace();
			}

			finally
			{ 
				try {
					connection.close();
					connection = null;
				} 
				catch (SQLException ignore) {}
			}
		}
	}
}


