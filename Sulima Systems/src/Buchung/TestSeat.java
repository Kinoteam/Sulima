package buchung;


public class TestSeat {

	public static void main(String[] args) {

//		SeatType regular = SeatType.REGULAR;
//		SeatType loge = SeatType.LOGE;
//		SeatType loveseat = SeatType.LOVESEAT;
//		
//		
//		Room room1 = new Room();;
//		System.out.println("total number of seats" + room1.getNumberOfSeats());
//		room1.setRoomId(2);
//		room1.saveRoom();
//		
//		Seat[] seats= new Seat[10];
//		for (int i= 0; i < 10; i++)
//		{
//			seats[i] = new Seat(i+1, regular );
//			seats[i].addSeatToRoom(room1);
//			seats[i].saveSeat();
//		}
//		System.out.println("total number of seats from Java: " + room1.getNumberOfSeats());
		Database db = new Database();
		System.out.println("total number of seats from DB: " + db.getCountFromDatabase("seats"));
		System.out.println("Seats from Room 1: " + db.getCountWithCondition("seats", "room_id", "1"));

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
