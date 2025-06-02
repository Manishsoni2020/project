package schoolAdministrationUsingMysql;

import java.sql.*;
import java.util.Scanner;

abstract class User {
	String id;
	String name;
	String role;

	User(String id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}

	abstract void showDetails();
}

class Admin extends User {
	Admin(String id, String name) {
		super(id, name, "Admin");
	}

	void showDetails() {
		System.out.println("+----------+----------------+");
		System.out.printf("| %-8s | %-14s | \n", "Admin ID", "Name");
		System.out.println("+----------+----------------+");
		System.out.printf("| %-8s | %-14s |\n", id, name);
		System.out.println("+----------+----------------+");
	}
}

class Teacher extends User {
	String subject;

	Teacher(String id, String name, String subject) {
		super(id, name, "Teacher");
		this.subject = subject;
	}

	void showDetails() {

		System.out.printf("| %-4s | %-11s | %-11s |\n", id, name, subject);

	}
}

class Student extends User {
	String grade;

	Student(String id, String name, String grade) {
		super(id, name, "Student");
		this.grade = grade;
	}

	void showDetails() {
		System.out.printf("| %-4s | %-11s | %-8s |\n",id,name,grade);
	}
}

public class ProjectUsingMySql {
	static Scanner sc = new Scanner(System.in);
	static Connection con;

	public static void main(String[] args) {
		try {
			// Database connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_system", "root", "2002");
			while (true) {
				System.out.println("----- School Administration System -----");
				System.out.print("Enter your role (Admin/Teacher/Student): ");
				String role = sc.nextLine();

				if (role.equalsIgnoreCase("admin")) {
					adminPanel();
				} else if (role.equalsIgnoreCase("teacher")) {
					teacherPanel();
				} else if (role.equalsIgnoreCase("student")) {
					studentPanel();
				} else {
					System.out.println("Invalid role.");
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Admin Panel
	public static void adminPanel() throws Exception {
		System.out.print("Enter Admin ID: ");
		String id = sc.nextLine();
		PreparedStatement pst = con.prepareStatement("SELECT * FROM admin WHERE id=?");
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			Admin admin = new Admin(rs.getString("id"), rs.getString("name"));
			System.out.println("\nWelcome " + admin.name);

			while (true) {
				System.out.println("\n--- Admin Menu ---");
				System.out.println("1. View All Teachers");
				System.out.println("2. View All Students");
				System.out.println("3. View Own Record");
				System.out.println("4. Add Teacher");
				System.out.println("5. Add Student");
				System.out.println("6. Delete Teacher");
				System.out.println("7. Delete Student");
				System.out.println("8. Exit");

				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					viewAllTeachers();
					break;
				case 2:
					viewAllStudents();
					break;
				case 3:
					admin.showDetails();
					break;
				case 4:
					addTeacher();
					break;
				case 5:
					addStudent();
					break;
				case 6:
					deleteTeacher();
					break;
				case 7:
					deleteStudent();
					break;
				case 8:
					return;
				default:
					System.out.println("Invalid choice.");
				}
			}
		} else {
			System.out.println("Admin not found.");
		}
	}

	// Teacher Panel
	public static void teacherPanel() throws Exception {
		System.out.print("Enter Teacher ID: ");
		String id = sc.nextLine();
		PreparedStatement pst = con.prepareStatement("SELECT * FROM teacher WHERE id=?");
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			Teacher teacher = new Teacher(rs.getString("id"), rs.getString("name"), rs.getString("subject"));
			System.out.println("\nWelcome " + teacher.name);

			while (true) {
				System.out.println("\n--- Teacher Menu ---");
				System.out.println("1. View Own Record");
				System.out.println("2. View Student Records");
				System.out.println("3. Exit");

				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					teacher.showDetails();
					break;
				case 2:
					viewAllStudents();
					break;
				case 3:
					return;
				default:
					System.out.println("Invalid choice.");
				}
			}
		} else {
			System.out.println("Teacher not found.");
		}
	}

	// Student Panel
	public static void studentPanel() throws Exception {
		System.out.print("Enter Student ID: ");
		String id = sc.nextLine();
		PreparedStatement pst = con.prepareStatement("SELECT * FROM student WHERE id=?");
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			Student student = new Student(rs.getString("id"), rs.getString("name"), rs.getString("grade"));
			System.out.println("\nWelcome " + student.name);

			while (true) {
				System.out.println("\n--- Student Menu ---");
				System.out.println("1. View Own Record");
				System.out.println("2. Exit");

				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					student.showDetails();
					break;
				case 2:
					return;
				default:
					System.out.println("Invalid choice.");
				}
			}
		} else {
			System.out.println("Student not found.");
		}
	}

	// View All Teachers
	public static void viewAllTeachers() throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM teacher");
		System.out.println("\n=== All Teacher ===");
		System.out.printf("+------+-------------+-------------+\n");
		System.out.printf("|  ID  |    Name     |   Subject   |\n");
		System.out.printf("+------+-------------+-------------+\n");

		while (rs.next()) {
			Teacher t = new Teacher(rs.getString("id"), rs.getString("name"), rs.getString("subject"));
			t.showDetails();
		}
		System.out.printf("+------+-------------+-------------+\n");
	}

	// View All Students
	public static void viewAllStudents() throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM student");
		System.out.printf("+------+-------------+----------+\n");
		System.out.printf("|  ID  |    Name     |   Grade  |\n");
		System.out.printf("+------+-------------+----------+\n");
		while (rs.next()) {
			Student s = new Student(rs.getString("id"), rs.getString("name"), rs.getString("grade"));
			s.showDetails();
		}
		System.out.printf("+------+-------------+----------+\n");
	}

	// Add Teacher
	public static void addTeacher() throws Exception {
		System.out.print("Enter Teacher ID: ");
		String id = sc.next();
		sc.nextLine();
		System.out.print("Enter Name: ");
		String name = sc.nextLine();
		System.out.print("Enter Subject: ");
		String subject = sc.nextLine();

		PreparedStatement pst = con.prepareStatement("INSERT INTO teacher VALUES (?, ?, ?)");
		pst.setString(1, id);
		pst.setString(2, name);
		pst.setString(3, subject);
		pst.executeUpdate();
		System.out.println("Teacher added.");
	}

	// Add Student
	public static void addStudent() throws Exception {
		System.out.print("Enter Student ID: ");
		String id = sc.next();
		sc.nextLine();
		System.out.print("Enter Name: ");
		String name = sc.next();
		sc.nextLine();
		System.out.print("Enter Grade: ");
		String grade = sc.next();
		sc.nextLine();

		PreparedStatement pst = con.prepareStatement("INSERT INTO student VALUES (?, ?, ?)");
		pst.setString(1, id);
		pst.setString(2, name);
		pst.setString(3, grade);
		pst.executeUpdate();
		System.out.println("Student added.");
	}

	// Delete Teacher
	public static void deleteTeacher() throws Exception {
		System.out.print("Enter Teacher ID to delete: ");
		String id = sc.next();

		PreparedStatement pst = con.prepareStatement("DELETE FROM teacher WHERE id=?");
		pst.setString(1, id);
		int rows = pst.executeUpdate();

		if (rows > 0)
			System.out.println("Teacher removed.");
		else
			System.out.println("Teacher not found.");
	}

	// Delete Student
	public static void deleteStudent() throws Exception {
		System.out.print("Enter Student ID to delete: ");
		String id = sc.next();

		PreparedStatement pst = con.prepareStatement("DELETE FROM student WHERE id=?");
		pst.setString(1, id);
		int rows = pst.executeUpdate();

		if (rows > 0)
			System.out.println("Student removed.");
		else
			System.out.println("Student not found.");
	}
}
