package proj.main.services;

import proj.dbconnctor.*;
import proj.main.user.Check;
import proj.main.user.SearchValue;
import proj.mainApp.UserRegistration;

import java.sql.*;
import java.util.Scanner;

import pojo.CustomisedPackagePOJO;
import pojo.FlightPOJO;
import pojo.StayPOJO;
import pojo.UserPOJO;

public class CustomPackage
{
	private Scanner in = new Scanner(System.in);
	private ConnectionProvider dbConnector;
	public CustomPackage()
	{
		dbConnector=ConnectionProvider.getInstance();
	
	}
	public void buildPackage(UserPOJO userPOJO)
	{
		int cFlightId=0;
		double cFlightCostTotal=0;
		double totalPackageCost=0;
		StayPOJO stayPOJO = new StayPOJO();
		FlightPOJO flightPOJO=new FlightPOJO();
		try
		{

			String sqlStay="SELECT c_hotel_price FROM c_stay ;";
			ResultSet getStayPojo = dbConnector.selectQuery(sqlStay);
			if(getStayPojo.next()) 
			{
				stayPOJO.setC_hotel_price(getStayPojo.getDouble("c_hotel_price"));
			}
			

			String sqlFlight="SELECT c_flight_cost FROM c_flights;";
			ResultSet getFlightPojo = dbConnector.selectQuery(sqlFlight);
			if(getFlightPojo.next()) 
			{
				flightPOJO.setC_flight_cost(getFlightPojo.getDouble("c_flight_cost"));
			}

			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter the departure city:");
			String cDeptCity=sc.next();
			System.out.println("Enter the city you wish to travel:");
			String cDestCity=sc.next();
			Check chk=new Check();
			int checkFlag=chk.checkCity(cDeptCity, cDestCity);
			
			//System.out.println("Enter your date of departure(yyyy-mm-dd):");
			//String cDeptDate=sc.next();
			
			System.out.println("Departure Date(YYYY-MM-DD): ");
			String cDeptDate=null;
			boolean check = false;
			UserRegistration userReg=new UserRegistration();
			
			while(check == false)
			{
				cDeptDate = in.next();
				
				check = userReg.dateCheck(cDeptDate);		
			}
			String searchFlights="select * from c_flights where c_dept_city='"+cDeptCity+"' and c_dest_city='"+cDestCity+"'";
			
			
			ResultSet resultSet=dbConnector.selectQuery(searchFlights); 
			System.out.println("We have following flights to offer");
			while (resultSet.next())
			{
				System.out.println(resultSet.getString("c_flight_id")+" "+resultSet.getString("c_dept_date")+" "+resultSet.getString("c_dept_city")+"  o-------o  "+resultSet.getString("c_dest_city")+"  "+resultSet.getString("c_flight_cost")+"€"+"o----Date:"+resultSet.getString("c_dept_date"));
			}
			if(checkFlag==0)
			{
				System.out.println("/////////////////////////////////////"
						+ "NO flights for these input values"
						+ "//////////////////////////////////////////////");
				
			}else if(checkFlag==1)
			{	
			System.out.println("Enter the flight id of the flights that you want to select. Or Try again ");
			cFlightId=sc.nextInt();
			
			
			int checkFlightID=chk.checkFlightId(cFlightId);
			if(checkFlightID==0)
				System.out.println("You have selected no flight OR give a correct Flight ID");
			
						
					
					
			SearchValue s2=new SearchValue();
			cFlightCostTotal=s2.valueOfFlight(cFlightId);
					
			
			
			}
			System.out.println("Do you want to book a hotel(Y/N)?");
			String cHotelNAme=sc.next();
			
			
			if(cHotelNAme.equals("y")|| cHotelNAme.equals("Y"))
			{
				System.out.println("Select the hotel name that you want to book?");
				String searchHotels="select * from c_stay WHERE c_hotel_place='"+cDestCity+"'";
				ResultSet resultSetHotels=dbConnector.selectQuery(searchHotels); 
				System.out.println("We have following hotels to offer at this "+cDestCity);
				while (resultSetHotels.next())
				{
					System.out.println(resultSetHotels.getString("c_hotel_id")+"  "+resultSetHotels.getString("c_hotel_name")+" --> "+resultSetHotels.getString("c_hotel_price")+"€");
				}
				
				System.out.println("Enter the id of hotel which you want to select:");
				int cHotelId=sc.nextInt();
				
				
				SearchValue s1=new SearchValue();
				int forCost=s1.valueOfHotel(cHotelId);
				
				
				System.out.println("Enter the number of nights you want to stay:");
				int cNoOfNights=sc.nextInt();
				double totalCost=forCost*cNoOfNights;	
				System.out.println("FlightCOST:"+cFlightCostTotal);
				totalPackageCost=totalCost+cFlightCostTotal;
				System.out.println("Your total package cost is:"+totalPackageCost);
				System.out.println("Do you want to book your package consisting of flight and hotel(Y/N)?");
				String cBookPackage=sc.next();
				if(cBookPackage.equals("y")|| cBookPackage.equals("Y"))
				{
					
					bookPackage(cFlightId,cDeptDate,cHotelId,totalCost,cNoOfNights,userPOJO,totalPackageCost);
				}
			}
			else
			{
				totalPackageCost=cFlightCostTotal;
				System.out.println("Your total package cost is:"+cFlightCostTotal);
				System.out.println("Do you want to book your package(Y/N)?");
				String cBookPackage=sc.next();
				if(cBookPackage.equals("y")|| cBookPackage.equals("Y"))
				{
					bookPackage(cFlightId,cDeptDate,0,0,0,userPOJO,totalPackageCost);
				}
			}
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void bookPackage(int cfID, String cDeptDate, int cHotelId,double totalCost,int cNoOfNights,UserPOJO userPOJO,double totalPackageCost)
	{
		try{	
				StayPOJO stayPOJO = new StayPOJO();
				FlightPOJO flightPOJO=new FlightPOJO();
				CustomisedPackagePOJO cPPOJO=new CustomisedPackagePOJO();
				int u_id=userPOJO.getUser_id();
				String sqlFlights="SELECT c_flight_cost FROM c_flights;";
				ResultSet getFlightPojo = dbConnector.selectQuery(sqlFlights);
				if(getFlightPojo.next()) 
				{
					flightPOJO.setC_flight_cost(getFlightPojo.getDouble("c_flight_cost"));
				}
				String sqlStay="SELECT c_hotel_price FROM c_stay;";
				ResultSet getStayPojo = dbConnector.selectQuery(sqlStay);
				if(getStayPojo.next()) 
				{
					stayPOJO.setC_hotel_price(getStayPojo.getDouble("c_hotel_price"));
				}


			if(cHotelId!=0)
			{

				int resultCount=dbConnector.insert("INSERT INTO cust_package(c_id,user_id,c_flight_id,cp_flight_cost,c_hotel_id,dept_date,c_no_of_nights,c_hotel_cost,c_hotel_total_cost,booking_date,c_total_package_cost)"
						+ " VALUES('"+cPPOJO.getcId()+"','"+u_id+"','"+cfID+"','"+flightPOJO.getC_flight_cost()+"','"+cHotelId+"','"+cDeptDate+"','"+cNoOfNights+"','"+stayPOJO.getC_hotel_price()+"','"+totalCost+"',CURRENT_DATE(),'"+totalPackageCost+"');");
				
				if (resultCount > 0)
				{
					System.out.println("Your package has been booked successfully!!");
				}
				
				else
					System.out.println("Error Inserting Record. Please Try Again!");
			}
			else
			{

				int resultCount=dbConnector.insert("INSERT INTO cust_package(c_id,user_id,c_flight_id,cp_flight_cost,c_hotel_id,dept_date,c_no_of_nights,c_hotel_cost,c_hotel_total_cost,booking_date,c_total_package_cost)"
						                                             + " VALUES('"+cPPOJO.getcId()+"','"+u_id+"','"+cfID+"','"+flightPOJO.getC_flight_cost()+"','0','"+cDeptDate+"','0','0','0',CURRENT_DATE(),'"+totalPackageCost+"');");
				
				if (resultCount > 0)
				{
					System.out.println("Your flight has been booked successfully!!");
				}
				
				else
					System.out.println("Error Inserting Record. Please Try Again!");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public void viewBuildPackage(UserPOJO userPOJO, String sql)
	{
		try
		{	
			ResultSet allPackageDetails = dbConnector.selectQuery(sql);
			if(allPackageDetails==null)
				System.out.println("Empty set");
			while (allPackageDetails.next()) 
			{
				System.out.println("-------------------------------------------");
				System.out.println("User Id.: " + allPackageDetails.getString("user_id"));
				System.out.println("Flight Id: " + allPackageDetails.getString("c_flight_id"));
				System.out.println("Cost of the flight: " + allPackageDetails.getInt("cp_flight_cost"));
				System.out.println("Hotel id: " + allPackageDetails.getString("c_hotel_id"));
				System.out.println("Departure date:" + allPackageDetails.getString("dept_date"));
				System.out.println("No of nights: " + allPackageDetails.getString("c_no_of_nights"));
				System.out.println("Hotel cost: " + allPackageDetails.getInt("c_hotel_cost"));
				System.out.println("Total hotel cost: " + allPackageDetails.getString("c_hotel_total_cost"));
				System.out.println("Booking date: " + allPackageDetails.getString("booking_date"));
				System.out.println("Total package cost: " + allPackageDetails.getString("c_total_package_cost"));
				System.out.println("-------------------------------------------\n");

			
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}	