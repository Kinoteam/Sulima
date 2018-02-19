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
	}
	
	public int getMovie_id() {
		return this.movie_id;
	}
	
	public String getMovieName() {
		return this.movieName;
	}
	
	public void setMovie(int movie_id, String movieName) {
		this.movie_id = movie_id;
		this.movieName = movieName;
	}
	
	public void saveMovie() {
		Database db = new Database();
		db.insertIntoDatabase("INSERT INTO movies (movie_name)"
				+ " VALUES ('" + this.movieName+"')");
	}
	
	public String toString() {
		String string = "Id: "+this.movie_id+ " , "+ this.movieName;
		return string;
	}
}
