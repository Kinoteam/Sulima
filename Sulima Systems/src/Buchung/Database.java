package buchung;

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

	public void queryToDatabase(String query) {

		if (connection == null) {
			connectToDatabase();
		}

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString("date") + ", " + resultSet.getString("hour") + "h, in room" + resultSet.getString("room") + "."  );
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
			statement.executeUpdate(query);
			System.out.println("Successfully inserted to Database");
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

	public int getCountFromDatabase(String database) {

		if (connection == null) {
			connectToDatabase();
		}
		int result = 0;
		String query = "SELECT COUNT(*) FROM " + database;

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
					rooms[i].setRoomId(resultSet.getInt("room_id"));
					rooms[i].setNumberOfSeats(resultSet.getInt("numberOfRegularSeats"), resultSet.getInt("numberOfLoveSeats"), resultSet.getInt("numberOfLogeSeats"));
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
			while (resultSet.next()) {
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

