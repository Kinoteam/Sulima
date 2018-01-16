package Buchung;

public class Room {
	
	private int roomId;
	private int nOfSeats;
	private Seat theater[];
	
	
	public Room(){
		
		roomId = 0;
		nOfSeats = 0;
		theater = null;
	}
	
	public Room(int pRoomId, int pNOfSeats){
		
		roomId = pRoomId;
		nOfSeats = pNOfSeats;
		for (int i=0; i< nOfSeats ; i++ ){
			theater[i] = new Seat(i+1);
		}
		
	}

}
