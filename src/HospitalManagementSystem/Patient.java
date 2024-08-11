package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class Patient {
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection , Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void addPatient() {
		System.out.println("Enter patient name");
		String name=scanner.next();
		System.out.println("Enter patient age");
		int age=scanner.nextInt();
		System.out.println("Enter the gender of the patient");
		String gender= scanner.next();
		
		try {
			String query="INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.setInt(2,age);
			preparedStatement.setString(3, gender);
			
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows > 0) {
				System.out.println("Patient Added successfully");
			}else {
				System.out.println("Failed to add Patient.");
			}	
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void viewPatient() {
		String query="Select * FROM patients";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Patient List");
			System.out.println("+------------+------------------------+----------+-----------+");
			System.out.println("| Patient Id | Name                   | Age      | Gender    |");
			System.out.println("+------------+------------------------+----------+-----------+");
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String gender=resultSet.getString("gender");
			System.out.printf("| %-11s| %-23s| %-9s| %-10s|\n",id,name,age,gender);
			System.out.println("+------------+------------------------+----------+-----------+");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String query="Select * From patients Where id=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1,id);
			ResultSet resultset = preparedStatement.executeQuery();
			if(resultset.next()) {
				return true;
			}else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
