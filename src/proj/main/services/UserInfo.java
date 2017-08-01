package proj.main.services;

import proj.dbconnctor.*;
import java.sql.*;
import java.util.Scanner;
import pojo.UserPOJO;
public class UserInfo
{
	
	private ConnectionProvider dbConnector;
	public UserInfo()
	{
		dbConnector=ConnectionProvider.getInstance();
	
	}
	public void update_users(int ch, UserPOJO userPOJO)
	{
		try
		{

			switch(ch)
			{
				case 1: 
						System.out.println("Enter new password:");
						Scanner sc1=new Scanner(System.in);
						String pass=sc1.nextLine();
						String up_pass="update user_details set user_password='"+pass+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount1=dbConnector.update(up_pass); 
						if(resultCount1==1)
									System.out.println("Updated Successfully");
						else
									System.out.println("Error updating password");

						break;
				case 2:
						System.out.println("Enter the first name to be updated:");
						Scanner sc2=new Scanner(System.in);
						String fname=sc2.nextLine();
						String up_fname="update user_details set user_fname='"+fname+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount2=dbConnector.update(up_fname); 
						if(resultCount2==1)
								System.out.println("Updated Successfully");
						else
								System.out.println("Error updating first name");

						break;
				case 3:
						System.out.println("Enter the last name to be updated:");
						Scanner sc3=new Scanner(System.in);
						String lname=sc3.nextLine();
						String up_lname="update user_details set user_lname='"+lname+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount3=dbConnector.update(up_lname); 
						if(resultCount3==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating last name");

						break;
				case 4:
						System.out.println("Enter the birth date to be updated:");
						Scanner sc4=new Scanner(System.in);
						String bdate=sc4.nextLine();
						String up_bdate="update user_details set user_bdate='"+bdate+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount4=dbConnector.update(up_bdate); 
						if(resultCount4==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating birth date");
						break;
				case 5:
						System.out.println("Enter the email to be updated:");
						Scanner sc5=new Scanner(System.in);
						String email=sc5.nextLine();
						String up_email="update user_details set user_emailid='"+email+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount5=dbConnector.update(up_email); 
						if(resultCount5==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating password");

						break;
				case 6:
						System.out.println("Enter the contact number to be updated:");
						Scanner sc6=new Scanner(System.in);
						String contact=sc6.nextLine();
						String up_contact="update user_details set user_contact='"+contact+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount6=dbConnector.update(up_contact); 
						if(resultCount6==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating contact number");

						break;
				case 7:
						System.out.println("Enter the country to be updated:");
						Scanner sc7=new Scanner(System.in);
						String country=sc7.nextLine();
						String up_country="update user_details set user_country='"+country+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount7=dbConnector.update(up_country); 
						if(resultCount7==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating country");

						break;
				case 8:
						System.out.println("Enter the address to be updated:");
						Scanner sc8=new Scanner(System.in);
						String address=sc8.nextLine();
						String up_address="update user_details set user_address='"+address+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount8=dbConnector.update(up_address); 
						if(resultCount8==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating address");

						break;
				case 9:
						System.out.println("Enter the city to be updated:");
						Scanner sc9=new Scanner(System.in);
						String city=sc9.nextLine();
						String up_city="update user_details set user_city='"+city+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount9=dbConnector.update(up_city); 
						if(resultCount9==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating city");

						break;
				case 10:
						System.out.println("Enter the zipcode to be updated:");
						Scanner sc10=new Scanner(System.in);
						String zipcode=sc10.nextLine();
						String up_zipcode="update user_details set user_zipcode='"+zipcode+"' where user_id='"+userPOJO.getUser_id()+"'";
						int resultCount10=dbConnector.update(up_zipcode); 
						if(resultCount10==1)
							System.out.println("Updated Successfully");
						else
							System.out.println("Error updating zipcode");

						break;
			}
			
		}catch(Exception e)
		{
			System.out.println("Error:"+e.getMessage());
		}
	}
	public void viewUserDetails(String sql) throws Exception
	{
		
		try
		{	
			ResultSet allUserDetails = dbConnector.selectQuery(sql);
			if(allUserDetails==null)
				System.out.println("Empty set");
			while (allUserDetails.next()) 
			{
				System.out.println("\nLogin Id.: " + allUserDetails.getString("user_login_id"));
				System.out.println("First name: " + allUserDetails.getString("user_fname"));
				System.out.println("Last name: " + allUserDetails.getString("user_lname"));
				System.out.println("Birth Date: " + allUserDetails.getString("user_bdate"));
				System.out.println("Gender:" + allUserDetails.getString("user_gender"));
				System.out.println("Email Id: " + allUserDetails.getString("user_emailid"));
				System.out.println("Contact Number: " + allUserDetails.getString("user_contact"));
				System.out.println("Address: " + allUserDetails.getString("user_address"));
				System.out.println("City: " + allUserDetails.getString("user_city"));
				System.out.println("Country: " + allUserDetails.getString("user_country"));
				System.out.println("Zipcode: " + allUserDetails.getString("user_zipcode")+"\n");
				
			
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}	
}	