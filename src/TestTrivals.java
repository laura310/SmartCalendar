import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestTrivals {
	public static void main(String[] args) {
//		String userID = "TESTUSER0";
//		String event = "swimming";
//		int allDayEvent = 1;
//		String startTime = "1990-03-10 05:30:00";
//		String endTime = "2020-03-13 05:40:00";
//		String repeat = "Yearly";
//		String repeatEndTime = "2020-05-01 12:00:00";
//		int travelTime = 50;
//		String location = "567 S 6th St. San Jose, CA 95112";
//		int alert = 1;
//		String description = "swimming practice";
//		String note = "Appointment with Thomas";
//
//		String query = "INSERT INTO " + userID + "(Event, allDayEvent, startTime, endTime, Repeat, repeatEndTime, travelTime, Location, Alert, Description) "
//				+ " VALUES ('" + event + "', " + allDayEvent + ", STR_TO_DATE('" + startTime + "', '%Y-%m-%d %H:%i:%s'),"
//				+ " STR_TO_DATE('" + endTime + "', '%Y-%m-%d %H:%i:%s'), '" + repeat + "', STR_TO_DATE('" + repeatEndTime + "', '%Y-%m-%d %H:%i:%s'), "
//				+ ", " + travelTime + ", '" + location + "', " + alert + ", '" + description + "');";
//		System.out.println(query);
//		String query = "SELECT Event, startTime FROM " + userID + " WHERE startTime BETWEEN '" + startTime + "' and '" + endTime + "';"; 
//		System.out.println(query);
		
		
		String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0000").format(Calendar.getInstance().getTime());
		System.out.println(curTime);
		
	}
}
