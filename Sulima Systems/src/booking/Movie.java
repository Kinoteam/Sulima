package booking;

public class Movie {
	
	private int movie_id;
	private String movieName;
	private int movieDuration;
	
	public Movie() {
		movieName ="";
		movieDuration = 0;
	}
	
	public Movie(String pMovieName, int pMovieDuration) {
		movieName = pMovieName;
		movieDuration = pMovieDuration;
	}
	
	public String getMovieName() {
		return this.movieName;
	}
	
	public int getMovieDuration() {
		return this.movieDuration;
	}
	
	public void setMovieName(String pMovieName) {
		this.movieName = pMovieName;
	}
	
	public void setMovieDuration(int pMovieDuration) {
		this.movieDuration = pMovieDuration;
	}
	
	
	
}
