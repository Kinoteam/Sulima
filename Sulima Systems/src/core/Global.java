package core;

import java.time.format.DateTimeFormatter;

/**
 * Global Static Variables
 * 
 * DB_FORMAT is a LocalDateTime Format compliant with the database' Timestamp Format,
 * it's used to write the Dates to the database.
 * 
 * USER_FORMAT is a LocalDateTime Format that is user friendly.
 * 
 * PARSING_FORMAT is a LocalDateTime format used to parse user entries.
 * 
 * user_id contains the Id of the user if he is logged in.
 *
 */

public class Global {

	public static final DateTimeFormatter DB_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
	public static final DateTimeFormatter USER_FORMAT = DateTimeFormatter.ofPattern("E dd-MM HH:mm");
	public static final DateTimeFormatter PARSING_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static boolean logged_in = false;
	public static int user_id = 0;
}