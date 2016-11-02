import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class SmartCalendar {
	
	public static void main(String[] args) {
		SmartCalendar smartCalendar = new SmartCalendar();
		
		Connection conn = smartCalendar.connect();
		
//		String user0info = getUserCalendarInTimePeriod("TESTUSER0", "2016-10-20 00:00:00", "2016-11-01 00:00:00", conn);
//		System.out.println(user0info);

		postUserCalendar("TESTUSER0", "swimming", 1, "1990-03-10 05:30:00.0000", "2020-03-13 05:40:00.0000", "Yearly", 50,
				"Krispy Kreme", 1, conn);
		
		/*******DISCONNECT:  NOT UNTIL GRADED********/
	}


	/**
	 * this method connects to our database for post/get information to/from database
	 * @return		Connection
	 */
	public Connection connect() {
		
		String url = "jdbc:mysql://cmpe281projectdatabase.c2cfw1gzdszr.us-west-2.rds.amazonaws.com:3306/cmpe281projectdatabase";  
		String userName = "SmartCalendar";
		String password = "CMPE281Project";
		String driver = "com.mysql.jdbc.Driver";
		Connection connection = null;
		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Connected to the database.");
			
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		return connection;
	}
		
	/**
	 * This method get information from database of a certain user for a certain time period from startTime to endTime.
	 * @param userID
	 * @param startTime		format "YYYY-MM-DD HH:MI:SS"
	 * @param endTime		format "YYYY-MM-DD HH:MI:SS"
	 * @param conn
	 * @return String formatted in "event1: startTime1 
	 * 								event2: startTime2 
	 * 								event3: startTime3 
	 * 								..."
	 */
	public static String getUserCalendarInTimePeriod(String userID, String startTime, String endTime, Connection conn) {
		
		Statement stmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String query = "SELECT eventName, startTime FROM " + userID + " WHERE startTime BETWEEN '" + startTime + "' and '" + endTime + "';"; 
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			
			ArrayList<String> al = new ArrayList<String>();
			while(rs.next()) {
				ArrayList<String> rowRecord = new ArrayList<String>();
				for(int i = 1; i <= columns; i++) {
					String value = rs.getString(i);
					rowRecord.add(value);
				}
				String rowValue = String.join(": ", rowRecord); // supported in Java 8 or later
				al.add(rowValue);
			}
			
			result = String.join("\n", al);
			
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
			
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
		
		return result;	
	}
	
	
	/**
	 * This method post information of a certain user for a certain time period from startTime to endTime to database.
	 * @param userID			is user's email
	 * @param eventName
	 * @param allDayEvent
	 * @param startTime			String format "YYYY-MM-DD HH:MI:SS.0000"
	 * @param endTime			String format "YYYY-MM-DD HH:MI:SS.0000"
	 * @param eventRepeat
	 * @param repeatEndTime		String format "YYYY-MM-DD HH:MI:SS"
	 * @param travelTime		int (in minutes) is the needed time to travel to event location
	 * @param location
	 * @param alert
	 * @param description
	 * @param conn
	 */
	public static void postUserCalendar(String userID, String eventName, int allDayEvent, String startTime, String endTime, String eventRepeat,
			int travelTime, String location, int alert, Connection conn) {
		
		Statement stmt = null;
		
		try {
			String query = "INSERT INTO cmpe281projectdatabase." + userID + "(eventName, allDayEvent, startTime, endTime, eventRepeat, travelTime, Location, Alert) "
					+ " VALUES ('" + eventName + "', '" + allDayEvent + "', '" + startTime + "',"
					+ " '" + endTime + "', '" + eventRepeat + "', '" + travelTime + "', '" + location + "', '" + alert + "');";
			
			System.out.println(query);
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				}catch(SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
	}

}