package Criminal_Information_System;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class CriminalManagementSystem {
	private static final String url="jdbc:mysql://localhost:3306/crimes";
	private static final String username="root";
	private static final String password="ananya";

	public static void main(String[]args)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return;
		}
		
		try(Scanner scanner=new Scanner(System.in))
		{
			Connection connection=DriverManager.getConnection(url, username, password);
			crime crime = new crime (connection,scanner);
			criminal criminal= new criminal(connection,scanner);
			
			while(true)
			{
				System.out.println("Welcome to Criminal Information Management System");
				System.out.println();
				System.out.println("Enter the number according to your requirements");
				System.out.println("-----------------------------------------------");
				System.out.println("1. To report a crime");
				System.out.println("2. To view all the crimes");
				System.out.println("3. To add a data for existing criminal");
				System.out.println("4. To view all the criminals in our database");
				System.out.println("5. To get the details for total cases solved or unsolved");
				System.out.println("6. I will try this other time");
				int c = scanner.nextInt();
				
				switch (c)
				{
				case 1:
					crime.addCrimes();
					System.out.println();
					break;
				case 2:
					crime.viewCrimes();
					System.out.println();
					break;
				case 3:
					criminal.addCriminals();
					System.out.println();
					break;
				case 4:
					criminal.viewCriminals();
					System.out.println();
					break;
				case 5:
					solvedCases(connection);
					System.out.println();
					break;
				case 6:
					System.out.println("OK ! We will be waiting for you!!!");
					System.out.println("Wishing for a safe and secure life 😂");
					return;
					default:
						System.out.println("Sir/ Ma'am please enter a valid choice to proceed...");
						break;
				}
				}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		}
	public static void  solvedCases(Connection connection)
	{
		try(Scanner scanner=new Scanner(System.in))
		{
		System.out.println("1. Enter one for solved cases");
		System.out.println("2. Enter two for unsolved cases");
		int choice= scanner.nextInt();
		
		switch(choice)
		{
		case 1:
			solved(connection, "Solved");
			System.out.println();
			break;
		case 2:
			solved(connection, "Unsolved");
			System.out.println();
			break;
		default:
			System.out.println("Sir/ Ma'am please enter a valid choice to proceed...");
			break;
		}
		
	}
	}
	
	public static void solved(Connection connection, String status) 
	{
		String query ="select * from crime where solved=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setString(1, status);
			try (ResultSet resultSet = preparedStatement.executeQuery()) 
			{
				System.out.println("Total Cases: ");
				System.out.println("+------+------------+------------+------------------------+------------------+------------------------+------------------+------------+");
				System.out.println("| ID   |    Date    |   Place    |      Description       |   Victimes       |         Details        |      Suspect     |   Solved   |");
				System.out.println("+------+------------+------------+------------------------+------------------+------------------------+------------------+------------+");
				while(resultSet.next()) {
					int id=resultSet.getInt("id");
					Date Date= resultSet.getDate("Date");
					String place = resultSet.getString("Place");
					String description = resultSet.getString("Description");
					String victimes = resultSet.getString("Victimes");
					String details = resultSet.getString("Details");
					String suspect = resultSet.getString("Suspect");
					String Solved = resultSet.getString("Solved");
					System.out.printf("|%-6s|%-12s|%-12s|%-24s|%-18s|%-24s|%-18s|%-12s",id, Date, place, description, victimes, details, suspect, Solved );
                   }
				System.out.println("+------+------------+------------+------------------------+------------------+------------------------+------------------+------------+");
			} 
			
	}catch(SQLException e)
		{
		e.printStackTrace();
		}
		
    }
}
