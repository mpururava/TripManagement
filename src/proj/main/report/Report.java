package proj.main.report;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import proj.dbconnctor.DBPropertyLoader;

public class Report {
	public Connection con;
	public Report()
	{
	Properties prop=null;
	prop= DBPropertyLoader.getProperties();
	try
	{
	Class.forName(prop.getProperty("driverClass"));
	con=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),prop.getProperty("password"));
	}catch(Exception e)
	{
		System.out.println(e);
		
	}
	}
	  public void reportGen()
	  {
		  String sqlQuery=null;
		  try{
			  @SuppressWarnings("resource")
		  
		Scanner in=new Scanner(System.in);
		  System.out.println("Reports generated for \n1.Previous Month Report\n2. Last Quarter Report \n3. Last Six Month Report\n4.Last Year Report ");
		  int choice=Integer.parseInt(in.nextLine());
		  switch(choice)
		  {
		  case 1: 
			  		sqlQuery="SELECT * FROM ticket WHERE ticket_date>='2017-06-01' AND ticket_date<='2017-06-13'";
			  		break;
		  case 2:
			  		sqlQuery="SELECT * FROM ticket WHERE ticket_date>='2017-03-01' AND ticket_date<='2017-06-01'";
		  			break;
		  
		  case 3:	sqlQuery="SELECT * FROM ticket WHERE ticket_date>='2017-01-01' AND ticket_date<='2017-05-01'";
		  			break;
		  
		  case 4:	sqlQuery="SELECT * FROM ticket WHERE ticket_date>='2016-06-01' AND ticket_date<='2017-06-01'";
		  				break;
		  default: System.out.println("Enter valid option");
		  }
		  
		  
		
		  
		  
		JasperReportBuilder report = DynamicReports.report();//a new report
		report
		  .columns(
		      Columns.column("TICKET NO.", "ticket_no", DataTypes.integerType()),
		      Columns.column("USER ID", "user_id", DataTypes.integerType()),
		      Columns.column("Package ID", "package_id", DataTypes.integerType()),
		      Columns.column("Date(mmddyy)", "ticket_date", DataTypes.dateType()))
		  .title(//title of the report
		      Components.text("No. OF BOOKED TRips in a MONTH")
			  .setHorizontalAlignment(HorizontalAlignment.CENTER))
			  .pageFooter(Components.pageXofY())//show page number on the page footer
			  .setDataSource(sqlQuery, con);

		try {
	                //show the report
			report.show();

	                //export the report to a pdf file
			report.toPdf(new FileOutputStream("C:\\Users\\purur\\Documents\\report.pdf"));
		} catch (DRException e) {
			System.out.println("ERROR:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("ERROR:"+e.getMessage());
		}
	 
	 }catch(Exception e){
		 System.out.println("ERROR:"+e.getMessage());
	}
			  	  
	}
	}