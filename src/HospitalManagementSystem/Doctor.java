package HospitalManagementSystem;
import java.sql.*;

public class Doctor {
	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection=connection;
	}
	
	public void viewDoctor() {
		String query="Select * FROM doctors";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Doctors List");
			System.out.println("+------------+------------------------+------------------+");
			System.out.println("| Doctor Id  | Name                   | Specialization   |");
			System.out.println("+------------+------------------------+------------------+");
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				String specialization=resultSet.getString("specialization");
			System.out.printf("|%-12s|%-24s|%-18s|\n",id ,name,specialization);
			System.out.println("+------------+------------------------+------------------+");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id) {
		String query="Select * From doctors Where id=?";
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
