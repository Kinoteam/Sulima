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

	private void connect()
	{
		try 
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sulima" , "root", "zbi");
		}

		catch (Exception dbError)
		{
			System.err.println("Error: Can't conect to Database");
		}
	}

	/*
	 * checks if the ResultSet is not empty,
	 * returns true in that case 
	 *
	 */
	public boolean existsInDatabase(String query) {

		//Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}

		//Begin: check if the ResultSet is not empty
		boolean result = true;

		try 
		{
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			result = resultSet.isBeforeFirst();
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		//After: Close connection
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return result;
	}

	/*
	 * inserts new data, updates existing data,
	 * or deletes data from the Database
	 *
	 */
	public boolean update(String query) {

		//Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}

		//Begin: Insert/Update/Delete Data
		boolean done = false;
		try 
		{
			statement = connection.createStatement();
			statement.executeUpdate(query);
			done = true;

		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		//After: Close connection
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		
		return done;
	}
	
	/*
	 * returns the Count of elements from any table,
	 * the column and the value for the "where" clause
	 * must be provided as parameters
	 * 
	 */
	public int getCountWithCondition(String table, String column, String value) {

		//Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}
		
		//Begin: Get the Count
		int result = 0;
		String query = "SELECT COUNT(*) FROM " + table + " where " + column + " = " + value;

		try 
		{
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) 
			{
				result = (resultSet.getInt("COUNT(*)"));
			}
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		//After: Close connection
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return result;
	}

	/*
	 * Loads all the movies from the Database in an array of movies
	 * 
	 */
	public boolean fetchMovies(Movie[] movies, int rowsCount) {

		// Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}

		// Begin: load all movies into the movies array
		String query = "SELECT * FROM MOVIES";
		boolean done=false;

		try 
		{
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) 
			{
				for (int i=0; i<rowsCount; i++)
				{
					movies[i] = new Movie(resultSet.getInt("movie_id"),resultSet.getString("movie_name") );
					resultSet.next();
				}
			}
			done=true;
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		// After: Close Connection to Database
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return done;
	}

	/*
	 * Loads all the Rooms from the Database in an array of rooms
	 * 
	 */
	public boolean fetchRooms(Room[] rooms, int rowsCount) {

		// Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}

		// Begin: load all rooms into the rooms array
		String query = "SELECT * FROM ROOMS";
		boolean done = false;
		try 
		{
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					rooms[i] = new Room(resultSet.getInt("room_id"));
					resultSet.next();
				}
			}
			done = true;
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		// After: Close Connection to Database
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return done;
	}

	/*
	 * Loads all the Sessions from the Database or the ones
	 * relative to a specific movie in an array of sessions
	 * 
	 */
	
	public boolean fetchSessions(Session[] sessions, int rowsCount) {
		return this.fetchSessions(sessions, rowsCount, 0);
	}
	public boolean fetchSessions(Session[] sessions, int rowsCount, int movie_id) {

		// Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}

		// Begin: load sessions into the sessions array 
		String query = null;
		if (movie_id !=0) 
		{
			 query = "SELECT * FROM SESSIONS where movie_id =" + movie_id;
		}
		else query = "SELECT * FROM SESSIONS";
		
		boolean done = false;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					sessions[i] = new Session(resultSet.getInt("session_id"),resultSet.getTimestamp("session_time").toLocalDateTime(),
							resultSet.getInt("room_id"), resultSet.getInt("movie_id"));
					resultSet.next();
				}
			}
			done = true;
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}
		
		// After: Close Connection to Database

		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return done;
	}
	
	/*
	 * Loads all the Seats from a specific Room into an array of Seats
	 * 
	 */
	public boolean fetchSeats(int room_id, Seat[] seats, int rowsCount) {

		// Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}

		// Begin: load seats into the seats array 
		String query = "SELECT * FROM SEATS WHERE room_id = " + room_id;
		boolean done = false;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					seats[i] = new Seat(resultSet.getInt("seat_id"), resultSet.getInt("seat_number") , resultSet.getInt("room_id") ,
							resultSet.getString("seat_category"));
					resultSet.next();
				}
			}
			done = true;
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		// After: Close Connection to Database
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		
		return done;
	}
	
	/*
	 * Puts the id's of Seats that are already reserved in an array of integers
	 * 
	 */
	public boolean fetchReservedSeatsId(int[] array, int rowsToSubstract, int session_id) {
		if (connection == null) 
		{
			connect();
		}
		String query = "SELECT * FROM RESERVATIONS where session_id = " + session_id;
		boolean done = false;
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
			done = true;
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return done;
	}
	
	/*
	 * Returns an int from the database,
	 * can return the count if the key is "Count(*)"
	 * (on the condition that select count(*) is used on the query)
	 * 
	 */
	public int getInt(String query, String key) {

		// Before: Connect to Database
		if (connection == null) 
		{
			connect();
		}
		
		// Begin: get an int
		
		int result = 0;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) 
			{
				result = resultSet.getInt(key);
			}
		}

		catch (Exception dbError)
		{
			dbError.printStackTrace();
		}

		// After: Close Connection to Database
		finally
		{ 
			try 
			{
				connection.close();
				connection = null;
			} 
			catch (SQLException ignore) {}
		}
		return result;
	}
}