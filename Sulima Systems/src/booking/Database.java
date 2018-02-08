package booking;

import java.sql.*;

class Database {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;


	private void connectToDatabase ()
	{
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sulima" , "root", "zbi");
		}

		catch (Exception dbError){
			dbError.printStackTrace();
		}

	}

	public boolean isAlreadyInDatabase(String query) {

		if (connection == null) {
			connectToDatabase();
		}
		boolean result = true;

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

		return result;
	}

	public void insertIntoDatabase(String query) {

		if (connection == null) {
			connectToDatabase();
		}

		try {
			statement = connection.createStatement();
			System.out.println(query);
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

	public void updateDatabase(String query) {
		if (connection == null) {
			connectToDatabase();
		}

		try {
			System.out.println(query);
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

	public int getCountWithCondition(String database, String column, String value) {

		if (connection == null) {
			connectToDatabase();
		}
		int result = 0;
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
		return result;
	}
	
	public void fetchMoviesFromDatabase(Movie[] movies, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

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

	public void fetchRoomsFromDatabase(Room[] rooms, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		String query = "SELECT * FROM ROOMS";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					rooms[i] = new Room();
					rooms[i].updateRoomId(resultSet.getInt("room_id"));
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

	public void fetchAllSessionsFromDatabase(Session[] sessions, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		String query = "SELECT * FROM SESSIONS";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					sessions[i] = new Session();
					sessions[i].set(resultSet.getInt("session_id"),resultSet.getTimestamp("session_time"),
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

	public void fetchSessionsFromDatabase(Session[] sessions, int movie_id, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		String query = "SELECT * FROM SESSIONS where movie_id =" + movie_id;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					sessions[i] = new Session();
					sessions[i].set(resultSet.getInt("session_id"),resultSet.getTimestamp("session_time"),
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

	public void fetchSeatsFromDatabase(int room_id, Seat[] seats, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		String query = "SELECT * FROM SEATS WHERE room_id = " + room_id;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					seats[i] = new Seat();
					seats[i].setSeat(resultSet.getInt("seat_number") , resultSet.getInt("room_id") ,
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

	public void fetchFreeSeatsFromDatabase(Seat[] seats, int session_id, int rowsCount) {

		if (connection == null) {
			connectToDatabase();
		}

		String query = "SELECT * FROM SEATS s left OUTER join RESERVATIONS r on s.seat_id= r.seat_id "
				+ "where r.seat_id is null or (session_id != " + session_id + " or session_id is null) ORDER BY seat_number";

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
			{
				for (int i=0; i<rowsCount; i++)
				{
					seats[i] = new Seat();
					seats[i].setSeat(resultSet.getInt("seat_number") , resultSet.getInt("room_id") ,
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


	public int getIntFromDatabase(String query, String key) {

		int result = 0;
		if (connection == null) {
			connectToDatabase();
		}

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

		return result;
	}
}
