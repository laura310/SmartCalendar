import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		
//		Calendar now = Calendar.getInstance();
//		String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0000").format(now.getTime());
//		String curMMDDandTimeStr = curTime.substring(5);  //"MM-dd HH:mm:ss.0000"
//		
//		System.out.println(curMMDDandTimeStr);
//		String curDateStr = curTime.substring(5, 10);  //"MM-dd"
//		System.out.println(curDateStr);
//			
//			try{
//				
//				DateFormat df = new SimpleDateFormat("MM-dd"); 
//			    Date startDate = df.parse("06-27");
//			    
//			    System.out.println("curDateStr:" + curDateStr);
//				String startDateStr = new SimpleDateFormat("MM-dd").format(startDate);
//				System.out.println("startDate:" + startDateStr);
//				Date curDate = df.parse(curDateStr);
//				String tmp = (curDate.before(startDate)) ? "curEarly" : "curLate";
//				System.out.println(tmp);
//			}catch (ParseException e) {
//				e.printStackTrace();
//			}
			
			
	}
	
	
	/**
	 * Given the userID, this method return all events (after current time) information in a JSONObject in format:
	 * {
	 * 		"events" : [
	 * 				{
	 * 					"eventName": 	"Halloween Day",   			//String, 											Not Null
	 *  				"allDayEvent":	1, 							//int, 												Not Null
	 *  				"startTime":	"10/31/2016 0:00:00.0000", 	//String in format "YYYY-MM-DD HH:MI:SS.0000", 		Not Null
	 *  				"endTime":		"07/6/2016 23:59:59.0000", 	//String in format "YYYY-MM-DD HH:MI:SS.0000", 		Not Null
	 *  				"eventRepeat":	"Yearly", 					//String ("None", "Weekly", "Monthly", "Yearly"), 	Not Null
	 *  				"repeatEndTime":"12/31/2050 23:59:59.0000", 	//String in format "YYYY-MM-DD HH:MI:SS.0000"
	 *  				"travelTime":	0,							//int (travel time in minutes),						Not Null
	 *  				"location": 	None, 						//String
	 *  				"alert":		0, 							//int,												Not Null
	 *  				"trafficCheck":	0, 							//int, 												Not Null
	 *  				"description":	NULL						//String
	 *  				"originalLocation": "Washington, DC, USA"	//String							
	 * 				},
	 * 				{
	 * 					...
	 * 				},
	 * 				...
	 * 		]
	 * }
	 * 
	 * @param userID
	 * @param conn
	 * @return
	 */
	// modification for "Yearly"
	public static JSONObject getUserCalendar(String userID, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		
		JSONObject userCalendar = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		
		try {
			Calendar now = Calendar.getInstance();
			String curTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0000").format(now.getTime());
			
			String query = "SELECT * FROM `" + userID + "` WHERE repeatEndTime >= '" + curTimeStr + "';";

			System.out.println(query);  //for debug
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				String curMMDDandTimeStr = curTimeStr.substring(5);  //"MM-dd HH:mm:ss.0000"
				
				try{
					DateFormat df = new SimpleDateFormat("MM-dd");
					Date curDate = df.parse(curMMDDandTimeStr);
				    Date testDate = df.parse("03-10 05:30:00.0000"); 
				    int currentYear = now.get(Calendar.YEAR);
				    int endYear = 2020;  //2020 is for test, should get from database
				    
			    	
				    for(int i = currentYear+1; i <= endYear; i++) {
				    	JSONObject jsonTmp = new JSONObject();
						jsonTmp.put("eventName", rs.getString(2));
						jsonTmp.put("allDayEvent", rs.getInt(3));		
						jsonTmp.put("startTime", "" + i + "-" + rs.getString(4).substring(5));
						jsonTmp.put("endTime", "" + i + "-" + rs.getString(5).substring(5));
						jsonTmp.put("eventRepeat", rs.getString(6));
						jsonTmp.put("repeatEndTime", rs.getString(7));
						jsonTmp.put("travelTime", rs.getInt(8));
						jsonTmp.put("location", rs.getString(9));
						jsonTmp.put("alert", rs.getInt(10));
						jsonTmp.put("trafficCheck", rs.getInt(11));
						jsonTmp.put("description", rs.getString(12));
						jsonTmp.put("originalLocation", rs.getString(13));
						
						jsonArr.put(jsonTmp);
				    }
				    
				    if(curDate.before(testDate)) {
				    	//add date of current year
				    	JSONObject jsonTmp = new JSONObject();
						jsonTmp.put("eventName", rs.getString(2));
						jsonTmp.put("allDayEvent", rs.getInt(3));		
						jsonTmp.put("startTime", "" + currentYear + "-" + rs.getString(4).substring(5));
						jsonTmp.put("endTime", "" + currentYear + "-" + rs.getString(5).substring(5));
						jsonTmp.put("eventRepeat", rs.getString(6));
						jsonTmp.put("repeatEndTime", rs.getString(7));
						jsonTmp.put("travelTime", rs.getInt(8));
						jsonTmp.put("location", rs.getString(9));
						jsonTmp.put("alert", rs.getInt(10));
						jsonTmp.put("trafficCheck", rs.getInt(11));
						jsonTmp.put("description", rs.getString(12));
						jsonTmp.put("originalLocation", rs.getString(13));
						
						jsonArr.put(jsonTmp);
				    } 
				}catch (ParseException e) {
					e.printStackTrace();
				}
			}
			userCalendar.put("events", jsonArr);
			
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
			
		} catch (JSONException e) {
			System.out.println("JSONException: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					ex.printStackTrace();
				}
				
				rs = null;
			}
			
			if(stmt != null) {
				try {
					stmt.close();
				}catch(SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		
		return userCalendar;
	}
}
