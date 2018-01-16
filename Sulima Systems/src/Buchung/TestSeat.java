package Buchung;

import java.util.*;

public class TestSeat {

	public static void main(String[] args) {
		
		String letsWatch = "Spider Man";
		CharSequence date1 = "--01-14";
		CharSequence time1 = "18:00:00";
		
		Movie movieVar = new Movie(letsWatch, date1, time1);
		System.out.println("We gonna watch " + movieVar.getMovieName());
		
		
		
	}
}


//public class TestSeat {
//
//	public static void main(String[] args) {
//		
//		int totalSeats = 10;
//		Seat theatre[] = new Seat[totalSeats];
//
//		for (int i=0; i<10; i++) {
//			theatre[i] = new Seat();
//			theatre[i].setResetSeat(i+1);
//			System.out.println("Seat created. Nr:" + theatre[i].getSeatNumber() + ". reserved: " + theatre[i].getReserved() + ". name: " + theatre[i].getFirstName() + " " + theatre[i].getLastName());
//		}
//		
//		int seatToModify= 5;
//		theatre[seatToModify+1].setReservedSeat("mamak", "babak");
//		
//		for (int i=0; i<10; i++) {
//			System.out.println("Seat created. Nr:" + theatre[i].getSeatNumber() + ". reserved: " + theatre[i].getReserved() + ". name: " + theatre[i].getFirstName() + " " + theatre[i].getLastName());
//		}
//		
//	}
//}
