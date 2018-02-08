package booking;

public class Movie {
	
	private int movie_id;
	private String movieName;
	private int movieDuration;
	
	public Movie() {
		movieName ="";
		movieDuration = 0;
	}
	
	public Movie(int movie_id, String movieName) {
		this.movie_id = movie_id;
		this.movieName = movieName;
		this.movieDuration = 120;
	}
	
	public int getMovieId() {
		return this.movie_id;
	}
	
	public String getMovieName() {
		return this.movieName;
	}
	
	public int getMovieDuration() {
		return this.movieDuration;
	}
	
	public void setMovie(int movie_id, String movieName) {
		this.movie_id = movie_id;
		this.movieName = movieName;
	}
	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public void setMovieDuration(int movieDuration) {
		this.movieDuration = movieDuration;
	}
	
	public void saveMovie() {
		Database db = new Database();
		db.insertIntoDatabase("INSERT INTO movies (movie_id, movie_name)"
				+ " VALUES (" + this.movie_id+ " , '"+ this.movieName+"')");
	}
	
	public String toString() {
		String string = "Id: "+this.movie_id+ " , "+ this.movieName;
		return string;
	}
	
	
	
}
