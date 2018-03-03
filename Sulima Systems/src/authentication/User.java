package authentication;

import java.util.*;
import core.*;

public class User {

	private String username;
	private String pwd;
	private Database db = new Database();

	public User() {

		this.username = "";
		this.pwd = "";
	}

	public void login() {

		if (Global.logged_in) 
		{
			this.logout();
		}

		Scanner scan = new Scanner(System.in);
		String query = "";
		String key = "";

		while (!Global.logged_in) {

			System.out.print("Enter your username: ");
			this.username = scan.next();
			System.out.print("Enter your password: ");
			this.pwd = scan.next();

			query = "Select * FROM users WHERE `username` = '" + this.username + "' AND `pwd` = '"+ this.pwd +"'";
			if(db.existsInDatabase(query)) 
			{
				Global.logged_in = true;
				key = "user_id";
				Global.user_id = db.getInt(query, key);
			}

			else System.out.println("false username or password");
		}
	}

	public void login(String username, String pwd) {

		if (Global.logged_in) 
		{
			this.logout();
		}

		this.username = username;
		this.pwd = pwd;

		String query = "";
		String key = "";

		query = "Select * FROM users WHERE `username` = '" + this.username + "' AND `pwd` = '"+ this.pwd +"'";
		if(db.existsInDatabase(query)) 
		{
			Global.logged_in = true;
			key = "user_id";
			Global.user_id = db.getInt(query, key);
		}

		else System.out.println("false username or password");
	}

	public void logout() {
		Global.logged_in = false;
		Global.user_id = 0;
	}
}
