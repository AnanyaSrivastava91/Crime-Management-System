package Criminal_Information_System;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class crime {
	private Connection connection;
	private Scanner scanner;
	
	public crime(Connection connection, Scanner scanner)
	{
		this.connection=connection;
		this.scanner=scanner;
	}
	public void addCrimes()
	{
		System.out.println("Enter Date(YYYY-MM-DD)");
		String date=scanner.next();
		System.out.println("Enter place");
		String place=scanner.next();
		System.out.println("Enter Description");
		String description=scanner.next();
		System.out.println("Enter victimes");
		String victimes=scanner.next();
		System.out.println("Enter Details");
		String details= scanner.next();
		System.out.println("Enter Suspect names");
		String suspect= scanner.next();
		System.out.println("Solved or unsolved");
		String solved= scanner.next();
		
		try {
			String query="insert into crime (Date, Place, Description, Victimes, Details, Suspect, Solved) values(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareCall(query);
			preparedStatement.setDate(1, Date.valueOf(date));
			preparedStatement.setString(2, place);
			preparedStatement.setString(3, description);
			preparedStatement.setString(4, victimes);
			preparedStatement.setString(5, details);
			preparedStatement.setString(6, suspect);
			preparedStatement.setString(7, solved);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows>0)
			{
				if(solved=="solved")
				{
					System.out.println("The case is already solved...");
				}
				System.out.println("Crime is Added and will be investigates soon...");
			}
			else
			{
				System.out.println("Failed to load the Crimes!!!");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void viewCrimes()
	{
		String query="select * from crime";
		try {
			PreparedStatement preparedStatement=connection.prepareCall(query);
			ResultSet resultSet= preparedStatement.executeQuery();
			System.out.println("Crimes: ");
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
				System.out.println("+------+------------+------------+------------------------+------------------+------------------------+------------------+------------+");
			}
			resultSet.close();
			preparedStatement.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}
