package proj.main.services;

import proj.dbconnctor.*;
import java.sql.*;

import proj.main.user.Check;
public class UserFunction
{
	
	private ConnectionProvider dbConnector;
	public UserFunction()
	{
		dbConnector=ConnectionProvider.getInstance();
	
	}
	public void viewTrips(String sql) throws Exception
	{
		
		try
		{	
			ResultSet allTripsResultSet = dbConnector.selectQuery(sql);
			while (allTripsResultSet.next()) 
			{
				System.out.println("\nPackageNo.: " + allTripsResultSet.getInt("package_id"));
				System.out.println("Package Name: " + allTripsResultSet.getString("package_name"));
				System.out.println("Source: " + allTripsResultSet.getString("source"));
				System.out.println("Destination: " + allTripsResultSet.getString("destination"));
				System.out.println("Dep Date:" + allTripsResultSet.getString("departure_date"));
				System.out.println("HOTEL: " + allTripsResultSet.getString("hotel_name"));
				System.out.println("NO.OF Nights: " + allTripsResultSet.getInt("no_of_nights"));
				System.out.println("Mode of Transportation: " + allTripsResultSet.getString("mode_of_transport"));
				System.out.println("Description: " + allTripsResultSet.getString("trip_desc"));
				System.out.println("Cost(€): " + allTripsResultSet.getInt("package_cost"));
				System.out.println("Discount: " + allTripsResultSet.getInt("discount"));
				
			
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	public ResultSet searchTrips(String inDestination,String inDate,String inPrice)
	{		
	
			
			try {
										
					String sql="SELECT * FROM trips WHERE destination='"+inDestination+"' OR departure_date='"+inDate+"' OR package_cost<='"+inPrice+"';";

					ResultSet searchResultSet= dbConnector.selectQuery(sql);
	
			while (searchResultSet.next()) 
			{
				System.out.println("\nPackageNo.: " + searchResultSet.getInt("package_id"));
				System.out.println("Package Name: " + searchResultSet.getString("package_name"));
				System.out.println("Source: " + searchResultSet.getString("source"));
				System.out.println("Destination: " + searchResultSet.getString("destination"));
				System.out.println("Dep Date:" + searchResultSet.getString("departure_date"));
				System.out.println("HOTEL: " + searchResultSet.getString("hotel_name"));
				System.out.println("NO.OF Nights: " + searchResultSet.getInt("no_of_nights"));
				System.out.println("Mode of Transportation: " + searchResultSet.getString("mode_of_transport"));
				System.out.println("Description: " + searchResultSet.getString("trip_desc"));
				System.out.println("Cost: " + searchResultSet.getInt("package_cost"));
				System.out.println("Discount: " + searchResultSet.getInt("discount"));
				
			
			}
			
			
			}catch (SQLException e) {
			System.out.println("ERROR:"+e.getMessage());
		}
		return null;
	}


	public void bookTicket(int inPackageID, int userDbId)
	{
		int checkFlag=0;
		Check chk=new Check();
		checkFlag=chk.checkPackage(inPackageID);
		
		if(checkFlag==1)
		{
		
		try{	
			
			int resultCount=dbConnector.insert("INSERT INTO ticket(user_id,package_id,ticket_date)"
					+ " VALUES('"+userDbId+"','"+inPackageID+"',CURRENT_DATE());");
			
			if (resultCount > 0)
			{	//System.out.println("Confirmation EMAIL SEND ");
				System.out.println("TICKET BOOKED Successfully");
			}
			else
				System.out.println("Error Inserting Record Please Try Again");
			
		}catch(Exception e)
		{
			System.out.println("ERROR:"+e.getMessage());
		}
		}else
			System.out.println("Package Not Found");
		
	}
	
	
	public void viewUserTicket(int userDbId)
	{
		try { 
			
				String sql="SELECT * FROM ticket WHERE user_id='"+userDbId+"';";
				ResultSet ticketResultSet= dbConnector.selectQuery(sql);
				
			while (ticketResultSet.next()) 
			{
				
				System.out.println("\nPackageID.: " + ticketResultSet.getInt("package_id"));
				System.out.println("UserID: " + ticketResultSet.getInt("user_id"));
				System.out.println("TICKET date: " + ticketResultSet.getDate("ticket_date"));
				System.out.println("Ticket no.: " + ticketResultSet.getInt("ticket_no"));
				
			
			}
			
			
		} catch (SQLException e) 
		{
			System.out.println("ERROR:"+e.getMessage());
		}
	}
	public ResultSet searchKeyword(String keyword1)
	{		
	
			
			try {
										
			String sql="SELECT * FROM trips ";

				ResultSet searchResultSet= dbConnector.selectQuery(sql);
			while (searchResultSet.next()) 
			{
				String s1=searchResultSet.getString(9);
				boolean flag=s1.contains(keyword1);
				if(flag==true)
				{
				
				System.out.println("\nPackageNo.: " + searchResultSet.getInt("package_id"));
				System.out.println("Package Name: " + searchResultSet.getString("package_name"));
				System.out.println("Source: " + searchResultSet.getString("source"));
				System.out.println("Destination: " + searchResultSet.getString("destination"));
				System.out.println("Dep Date:" + searchResultSet.getString("departure_date"));
				System.out.println("HOTEL: " + searchResultSet.getString("hotel_name"));
				System.out.println("NO.OF Nights: " + searchResultSet.getInt("no_of_nights"));
				System.out.println("Mode of Transportation: " + searchResultSet.getString("mode_of_transport"));
				System.out.println("Description: " + searchResultSet.getString("trip_desc"));
				System.out.println("Cost: " + searchResultSet.getInt("package_cost"));
				System.out.println("Discount: " + searchResultSet.getInt("discount"));
				
				
				}	
			
			}
			
			
		} catch (SQLException e) 
		{
			System.out.println("ERROR:"+e.getMessage());
		}
		return null;
	}
	public void viewUserNotification(int userDbId)
	{
		try { 
			
			String sql="SELECT * FROM notifications WHERE user_id='"+userDbId+"';";
			ResultSet notiResultSet= dbConnector.selectQuery(sql);
			while (notiResultSet.next()) 
			{
				System.out.println("\nFeedbackID.: " + notiResultSet.getInt("feedback_id"));
				System.out.println("UserID: " + notiResultSet.getInt("user_id"));
				System.out.println("Requested User EMAIL.: " + notiResultSet.getString("req_userEmail"));
			}
			
			
		} catch (SQLException e) 
		{
			System.out.println("ERROR:"+e.getMessage());
		}
	}
}	