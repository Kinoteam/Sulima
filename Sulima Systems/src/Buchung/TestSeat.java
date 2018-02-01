package buchung;

public class TestSeat {

	public static void main(String[] args) {
		
		Room room1 = new Room();
		room1.setNumberOfSeats(100, 20,10);
		room1.getNumberOfSeats();
		room1.setRoomId(1);
		room1.saveRoom();
		
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
