package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {    /*doctors are supposed to be included by the DBA from the back-end so no need to enter query*/
	
	private Connection connection;/*these both are private so that no other class can use these data members  */
	
	
	public Doctor(Connection connection)
    {
	this.connection = connection;/*we will get the connection from the main class */
	
    }
	
	public void viewDoctors()
	{
		String query="select * from doctors";
		try           /*to execute this particular query*/
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet= preparedStatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+-----------+-------------------------+--------------------+");
			System.out.println("|Doctor ID  | Name                    | Specialization     |");
			System.out.println("+-----------+-------------------------+--------------------+");
			while(resultSet.next())/*sets a pointer over the the table and print the data line by line*/
			{
				int id=resultSet.getInt("id");
				String name = resultSet.getString("name");
				String specialization = resultSet.getString("specialization");
				System.out.printf("|%-12s|%-25s|%-19s|",id , name, specialization);
				System.out.println("+-----------+-------------------------+--------------------+");
			}
			}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public boolean getDoctorById(int id)
	{
		String query= "select * from doctors where id = ?";
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

