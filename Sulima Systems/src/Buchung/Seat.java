package buchung;

public class Seat {
	private int seatNumber;
	private boolean reserved;
	private String firstName;
	private String lastName;
	
	public Seat() {
		seatNumber = 0;
		reserved = false;
		firstName= "";
		lastName= "";
	}
	
	public Seat(int pSeatNumber) {
		seatNumber = pSeatNumber;
		reserved = false;
		firstName= "";
		lastName= "";
	}
	
	public Seat(int pNumber, boolean pReserved, String pFirstName, String pLastName) {
		seatNumber = pNumber;
		reserved = pReserved;
		firstName= pFirstName;
		lastName= pLastName;
	}
	
	public void setResetSeat(int pNumber) {
		seatNumber = pNumber;
		reserved = false;
		firstName= "";
		lastName= "";
	}
	
	public void setReservedSeat(String pFirstName, String pLastName) {
		reserved = true;
		firstName= pFirstName;
		lastName= pLastName;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public boolean getReserved() {
		return reserved;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
}



