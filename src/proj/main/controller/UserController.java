package proj.main.controller;

import java.util.Scanner;

import pojo.UserPOJO;
import proj.main.blog.Blog;
import proj.main.services.*; 
public class UserController {

	private Scanner in;
	boolean mainFlag = true;
	private UserPOJO userPOJO;
	

	public UserController(Scanner in, UserPOJO userPOJO) {
		this.userPOJO = userPOJO;
		this.in = in;
	}

	public void startUserOperations() 
	{
		System.out.println("---------------------------------------------------------"
				+ "\nWELCOME-"+userPOJO.getUser_fname());
		
		do {
			int choice = 0;

			System.out.println("----------------------------------------------------Choose Operations---------------------------------"
			+"\n1.VIEW TRIPS\n2.SEARCH\n3.Edit Details\n4.Book TICKET\n5.VIEW BOOKED TRIPS\n6.Blog\n7VIEW NOTIFICATION\n8VIEW DETAILS\n9Do you want to build your own package?\n10.View customised packages...\n11.Logout");
			try {
					choice = Integer.parseInt(in.nextLine());

				}catch (NumberFormatException e)
				{
					System.out.println("INVALID INPUT:" + e.getMessage());
				}
			switch (choice)
			{

				case 1:
						System.out.println("------------------------------------VIEW TRIPS--------------------------------"+
											"\nSORT BY\n1.Price\n2.Discount\n3.GO back");
						int press=0;
						try
						{
							press = Integer.parseInt(in.nextLine());
						}catch (NumberFormatException e) 
							{
								System.out.println("INVALID INPUT:" + e.getMessage());
							}
				
						switch (press) 
						{
							case 1:
									String sql1 = "SELECT * FROM trips ORDER BY package_cost";
									try {
											UserFunction userService1 = new UserFunction();
											userService1.viewTrips(sql1);
										}catch (Exception e) 
										{
											System.out.println("INVALID INPUT:" + e.getMessage());
										}
									break;
							case 2:
									String sql2 = "SELECT * FROM trips ORDER BY discount DESC";
									try {
										UserFunction userService1 = new UserFunction();
											userService1.viewTrips(sql2);
										} catch (Exception e) 
										{
											System.out.println("INVALID INPUT:" + e.getMessage());
										}
									break;


							default:
									System.out.println("WRONG KEY PRESSED");
									break;
						}

						break;

			case 2:
								System.out.println("SEARCH TRIPS\nSORT BY\n1.Destination\n2.Keyword");
								int press2 = Integer.parseInt(in.nextLine());
								switch (press2) {

														case 1:
																	System.out.println("Enter Destination no to be searched:");
																	String inDestination = in.nextLine();
																	System.out.println("Enter Date to be searched:");
																	String inDate = in.nextLine();
																	System.out.println("Enter budget to be searched:");
																	String inPrice = in.nextLine();
																	UserFunction userService2 = new UserFunction();
																	userService2.searchTrips(inDestination, inDate, inPrice);
																	break;

														case 2:
																	System.out.println("Enter Searching Keyword no to be searched:");
																	String keyword1 = in.nextLine();

																	UserFunction userService3 = new UserFunction();
																	userService3.searchKeyword(keyword1);
																	break;
														case 3: 	break;
														
														default:
																	System.out.println("WRONG KEY PRESSED");
																	break;
														}
								break;
			case 3: UserInfo userServices3=new UserInfo();
					System.out.println("What do you want to update?");
					System.out.println("1.Password\n2.First name\n3.Last name\n4.Birth date\n5.Email id\n6.Contact Number\n7.Country\n8.Address\n9.City\n10.Zipcode");
					int update_choice=Integer.parseInt(in.nextLine());
					userServices3.update_users(update_choice,userPOJO);
					break;
				
			case 4:
					System.out.println("WHICH TRIP YOU WANT TO BOOK????\nEnter package ID");
					int inPackageID = Integer.parseInt(in.nextLine());
					UserFunction userServices4 = new UserFunction();
					userServices4.bookTicket(inPackageID, userPOJO.getUser_id());
					break;
			case 5:
					UserFunction userServices5 = new UserFunction();
					userServices5.viewUserTicket(userPOJO.getUser_id());
					break;
			case 6:	
					System.out.println("--------------------------------------------------------------"
							+ "\nBlog\n----------------------------------------------------------------"
							+ " Enter \n1.Would you like to give your blog of some trip?\n2.View your and other's Blogs");
					int press3 = Integer.parseInt(in.nextLine());
					switch (press3) 
					{
					case 1:	System.out.println("Which trip you want to give blog for?\nEnter package ID");
							int inPackageID2 = Integer.parseInt(in.nextLine());
							Blog feed1=new Blog(in);
							feed1.giveBlog(inPackageID2, userPOJO.getUser_id());
							break;
					case 2:	Blog feed2=new Blog(in);
							feed2.viewBlog(userPOJO.getUser_id(),userPOJO.getUser_emailid());
							break;	
					}	
					break;
			
			case 7:	UserFunction userServices7 = new UserFunction();
					userServices7.viewUserNotification(userPOJO.getUser_id());
					break;
					
			case 8:String sql6="SELECT user_login_id,user_fname,user_lname,user_bdate,user_gender,user_emailid,user_contact,user_country,user_address,user_city,user_zipcode FROM user_details where user_id='"+userPOJO.getUser_id()+"'";	
					try{
						UserInfo userService6= new UserInfo();
						userService6.viewUserDetails(sql6);
						}catch(Exception e)
						{
							System.out.println("ERROR"+e.getMessage());
						}
					break;		
			
			case 9:
					try{
					
						CustomPackage userService7= new CustomPackage();
						userService7.buildPackage(userPOJO);
						
						}catch(Exception e)
						{
							System.out.println("ERROR"+e.getMessage());
						}					
					break;
			case 10:
					String sql8="Select * from cust_package, user_details where cust_package.user_id=user_details.user_id and cust_package.user_id="+userPOJO.getUser_id();
					try{
						CustomPackage userService8= new CustomPackage();
						userService8.viewBuildPackage(userPOJO,sql8);
						}catch(Exception e)
							{System.out.println("ERROR"+e.getMessage());}	
					break;
					
			
			
			case 11:
					mainFlag = false;
					break;
			default:
					System.out.println("WRONG KEY PRESSED");
					break;
			}

		} while (mainFlag);
	}

}
