package Criminal_Information_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class criminal {
	private Connection connection;
	private Scanner scanner;
	
	public criminal(Connection connection, Scanner scanner)
	{
		this.connection=connection;
		this.scanner= scanner;
	}
	public void addCriminals()
	{
		System.out.println("Enter name");
		String name=scanner.next();
		System.out.println("Enter age");
		String age=scanner.next();
		System.out.println("Enter gender");
		String gender=scanner.next();
		System.out.println("Enter address");
		String address=scanner.next();
		System.out.println("Enter Identifying Makes");
		String marks= scanner.next();
		System.out.println("Enter area of crime");
		String crime= scanner.next();
		System.out.println("Enter previous criminal history");
		String history= scanner.next();
		
		try {
			String query="insert into criminals3 (Name, Age, Gender, Address, Body_Marks, Area, Crime) values(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareCall(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, age);
			preparedStatement.setString(3, gender);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, marks);
			preparedStatement.setString(6, crime);
			preparedStatement.setString(7, history);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows>0)
			{
				System.out.println("Criminal is Added and will be in our custody soon...");
			}
			else
			{
				System.out.println("Failed to load the Criminal data!!!");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public void viewCriminals()
	{
		String query="select * from criminals";
		try {
			PreparedStatement preparedStatement=connection.prepareCall(query);
			ResultSet resultSet= preparedStatement.executeQuery();
			System.out.println("Crimes: ");
			System.out.println("+------------------+------------+------------+------------------------+------------------+------------------------+------------------+");
			System.out.println("| Name             |    age     |   Gender   |        Address         |      Marks       |         Crime          |      History     |");
			System.out.println("+------------------+------------+------------+------------------------+------------------+------------------------+------------------+");
			while(resultSet.next()) {
				String name=resultSet.getString("name");
				String age= resultSet.getString("age");
				String gender = resultSet.getString("gender");
				String address = resultSet.getString("address");
				String Body_Marks = resultSet.getString("Body_Marks");
				String area = resultSet.getString("area");
				String crime = resultSet.getString("crime");
				System.out.printf("|%-19s|%-13s|%-13s|%-25s|%-19s|%-25s|%-19s|%n",name, age, gender, address, Body_Marks, area, crime);
				//System.out.println("+------------------+------------+------------+------------------------+------------------+------------------------+------------------+");
			}
			System.out.println("+------------------+------------+------------+------------------------+------------------+------------------------+------------------+");
			resultSet.close();
			preparedStatement.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	

}
