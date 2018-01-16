package Buchung;

public class Movie {
	
	private String movieName;
	private Date Calendar;
	
	public Movie() {
		movieName ="";
	}
	
	public Movie(String pMovieName,CharSequence pDay, CharSequence pTime) {
		movieName = pMovieName;
		Calendar = new Date(pDay, pTime);
	}
	
//	public Movie(String pMovieName, int pCalendarEntries) {
//		movieName = pMovieName;
//		for (int i=0; i< pCalendarEntries ; i++ ){
//			Calendar[i] = new Date();
//		}
//		
//	}
	
	public void setMovieName(String pMovieName) {
		this.movieName = pMovieName;
	}
	
	public String getMovieName() {
		return this.movieName;
	}
	
}
