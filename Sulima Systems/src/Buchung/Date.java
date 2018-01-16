package Buchung;

import java.time.*;

public class Date {


	private MonthDay day;
	private LocalTime timeStart;
	private int roomId;

	public Date() {
		day = MonthDay.now();
		timeStart = LocalTime.now();
		roomId = 0;
	}
	
	public Date(CharSequence pDay, CharSequence pTime) {
		day = MonthDay.parse(pDay);
		timeStart = LocalTime.parse(pTime);
		roomId = 0;
	}

	public Date(MonthDay pDay, LocalTime pTimeStart, int pRoomId) {
		
		day = pDay;
		timeStart = pTimeStart;
		roomId = pRoomId;
	}

}



