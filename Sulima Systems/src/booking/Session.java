package booking;

import java.time.*;

public class Session {


	private MonthDay day;
	private LocalTime timeStart;
	private int roomId;

	public Session() {
		day = MonthDay.now();
		timeStart = LocalTime.now();
		roomId = 0;
	}
	
	public Session(CharSequence pDay, CharSequence pTime) {
		day = MonthDay.parse(pDay);
		timeStart = LocalTime.parse(pTime);
		roomId = 0;
	}

	public Session(MonthDay pDay, LocalTime pTimeStart, int pRoomId) {
		
		day = pDay;
		timeStart = pTimeStart;
		roomId = pRoomId;
	}

}



