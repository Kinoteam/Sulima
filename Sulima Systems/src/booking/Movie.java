package booking;

import core.Database;

public class Movie {
	
	private int movie_id;
	private String movieName;
	
	public Movie() {
		movieName ="";
	}
	
	public Movie(String movieName) {
		this.movieName = movieName;
		this.save();
	}
	
	public Movie(int movie_id, String movieName) {
		this.movie_id = movie_id;
		this.movieName = movieName;
	}
	
	public int getMovie_id() {
		return this.movie_id;
	}
	
	public String getMovieName() {
		return this.movieName;
	}
	
	
	private boolean save() {
		Database db = new Database();
		return db.update("INSERT INTO movies (movie_name)"
				+ " VALUES ('" + this.movieName+"')");
	}
	
	public boolean delete() {
		Database db = new Database();
		return db.update("DELETE FROM movies WHERE movie_name = '" + this.movieName+"'");
	}
	
	public String toString() {
		String string = "Id: "+this.movie_id+ " , "+ this.movieName;
		return string;
	}
}
