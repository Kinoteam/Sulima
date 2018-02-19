package authentification;

import java.util.*;
import core.*;

public class User {

	private String username;
	private String pwd;
//	private int privilege;

	public User() {

		this.username = "";
		this.pwd = "";
//		privilege = 0;
	}//... Constructor

	public void Login() {

		if (Global.logged_in) {
			this.Logout();
		}//... if someone already logged in

		Scanner scan = new Scanner(System.in);
		Database db = new Database();
		String query = "";
		String key = "";

		while (!Global.logged_in) {

			System.out.print("Enter your username: ");
			this.username = scan.next();
			System.out.print("Enter your password: ");
			this.pwd = scan.next();

			query = "Select * FROM USERS WHERE `username` = '" + this.username + "' AND `pwd` = '"+ this.pwd +"'";
			if(db.isAlreadyInDatabase(query)) {
				Global.logged_in = true;
				key = "user_id";
				Global.user_id = db.getIntFromDatabase(query, key);
			}//... if user and pwd match

			else System.out.println("false username or password");
		} //... while not logged in
	} //... Login() function

	public void Logout() {
		Global.logged_in = false;
		Global.user_id = 0;
	}//... Logout() function
}//... Class
