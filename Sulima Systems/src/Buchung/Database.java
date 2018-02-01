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
			System.out.println("Connected");
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

		try { connection.close(); } catch (SQLException ignore) {}
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

		//		try { connection.close(); } catch (SQLException ignore) {}

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

		return result;
	}
}

