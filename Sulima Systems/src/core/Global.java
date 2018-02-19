package core;

import java.text.SimpleDateFormat;

public class Global {

	public static final SimpleDateFormat USERDF = new SimpleDateFormat("E dd-MM HH:mm");
	public static final SimpleDateFormat DATABASEDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
	public static boolean logged_in = false;
	public static int user_id = 0;
}
