package HospitalManagementSystem;
import java.sql.*;
import java.util.*;


public class HospitalManagementSystem {
	private static final String url ="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="Pranav@1503";
	
	public static void main (String args[]) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		Scanner scanner=new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection,scanner);
			Doctor doctor=new Doctor(connection);
			
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println(" 1.Add Patient \n 2.View Patient \n 3.Check Patient \n 4.View Doctor \n 5.Check Doctor \n 6.Get Appointment \n 7.Exit");
				System.out.println("Enter your choice ");
				int choice = scanner.nextInt();
				
				switch(choice) {
				case 1:
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					patient.viewPatient();
					System.out.println();
					break;
				case 3:
					System.out.println("Enter the id of patient");
					int pid=scanner.nextInt();
					if(patient.getPatientById(pid)) {
						System.out.print("Patient is available for that id");
					}else {
						System.out.print("Patient is not available for that id");
					}
					System.out.println();
					break;
				case 4:
					doctor.viewDoctor();
					System.out.println();
					break;
				case 5:
					System.out.println("Enter the id of doctor");
					int did=scanner.nextInt();
					if(doctor.getDoctorById(did)) {
						System.out.print("Doctor is available for that id");
					}else {
						System.out.print("Doctor is not available for that id");
					}
					System.out.println();
					break;
				case 6:
					bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
				case 7:
					System.out.println();
					return;
				default:
					System.out.print("Enter valid choice !!");
					System.out.println();
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void bookAppointment(Patient patient , Doctor doctor ,Connection connection,Scanner scanner) {
		System.out.print("Enter Patient Id");
		int patientId = scanner.nextInt();
		System.out.print("Enter Doctor Id");
		int doctorId = scanner.nextInt();
		System.out.print("Enter appointment date (YYYY-MM-DD)");
		String appointmentDate=scanner.next();
		
		if(patient.getPatientById(patientId)){
			if(doctor.getDoctorById(doctorId)) {
				if(checkDoctorAvailability(doctorId,appointmentDate,connection)) {
					String appointmentQuery="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
					try {
						PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
						preparedStatement.setInt(1,patientId);
						preparedStatement.setInt(2,doctorId);
						preparedStatement.setString(3, appointmentDate);
						
						int affectedRow=preparedStatement.executeUpdate();
						if(affectedRow > 0) {
							System.out.print("Appointment Booked ");
						}else {
							System.out.print("Failed to book appontment");
						}
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
				}else {
					System.out.print("Any Doctor is not available for that date");
				}
			}else {
				System.out.print("Any Doctor is not available for that id");
			}
		}else {
			System.out.print("Patient is not available for that id");
		}
		
	}
	
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection){
		String query="SELECT count(*) From appointments where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet =preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
}