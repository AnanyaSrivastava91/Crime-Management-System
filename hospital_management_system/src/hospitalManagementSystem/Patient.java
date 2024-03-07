package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;/*these both are private so that no other class can use these data members  */
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner)
    {
	this.connection = connection;/*we will get the connection from the main class */
	this.scanner= scanner;
    }
	public void addPatient()/*The very first function to admit patients*/
	{
		System.out.println("Enter patient name");
		String name = scanner.next();
		System.out.println("Enter patient age");
		int age = scanner.nextInt();
		System.out.println("Enter patient gender");
		String gender= scanner.next();
		
		try /*to connect with DB*/
		{
			String query ="insert into patients (name , age , gender)values(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareCall(query);
			preparedStatement.setString(1, name);/*these preparedStatements took values from the variables and placed them to there respective places*/
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows>0)
			{
				System.out.println("Patients Added Successfullly !");
			} 
			else
			{
				System.out.println("Failed to admit");
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();/*to print every exception data one by one*/
		}
	}
	
	public void viewPatients()
	{
		String query="select * from patients";
		try           /*to execute this particular query*/
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet= preparedStatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-----------+-------------------------+---------+----------+");
			System.out.println("|Patient ID | Name                    | Age     | Gender   |");
			System.out.println("+-----------+-------------------------+---------+----------+");
			while(resultSet.next())/*sets a pointer over the the table and print the data line by line*/
			{
				int id=resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("|%-12s|%-25s|%-9s|%-10s|\n",id, name, age, gender);
				System.out.println("+-----------+-------------------------+---------+----------+");
			}
			}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public boolean getPatientById(int id)
	{
		String query= "select * from patients where id = ?";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return true;
			}else
			{
				return false;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}  

}
