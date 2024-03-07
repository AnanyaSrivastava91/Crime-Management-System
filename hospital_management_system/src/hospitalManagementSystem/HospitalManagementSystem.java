package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url="jdbc:mysql://localhost:3306/hospital";/*url is private so that  no one can access the connectivity*/
	                                   /*static so that we don't require to make a object in the main class*/
	                                   /*final so that the  value given in input to this var should be changed through out the program execusion*/
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
		}
		Scanner scanner= new Scanner(System.in);
		try
		{
			Connection connection=DriverManager.getConnection(url, username, password);
			Patient patient =new Patient(connection,scanner);
			Doctor doctor = new Doctor(connection);
			while(true)
			{
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your Choice");
				int choice  = scanner.nextInt();
				
				switch(choice)
				{
				case 1:
					//add patients
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					//view patients
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					//view doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					//book appointment
					bookAppointment(patient, doctor, connection, scanner);
					System.out.println();
					break;
				case 5:
					System.out.println("Thank you for using our Software!!!");
					return;
					default:
						System.out.println("Enter valid choice");
						break;
				}
				

			}
			 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
	{
		System.out.println("Enter patient ID: ");
		int patientId=scanner.nextInt();
		System.out.println("Enter doctor ID: ");
		int doctorId =scanner.nextInt();
		System.out.println("Enter Appointment date(YYYY-MM-DD):");
		String appointmentDate= scanner.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
		{
			if(checkDoctorAvailability(doctorId , appointmentDate,connection))
			{
				String appointmentQuery="INSERT INTO appointments(patient_id, doctor_id, apointment_date) VALUES(?,?,?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected = preparedStatement.executeUpdate();
					if(rowsAffected>0)
					{
						System.out.println("Congrats! Appointment Booked...");
					}
					else {
						System.out.println("Failed to book a appointment");
					}
				}catch(SQLException e)
				{
					e.printStackTrace();;
				}
			}else
			{
				System.out.println("Doctor not availble in this date");
			}
		}else
		{
			System.out.println("Either doctor or patient doesn't exists!!!");
		}
		
	}
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection)
	{
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				if(count==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
