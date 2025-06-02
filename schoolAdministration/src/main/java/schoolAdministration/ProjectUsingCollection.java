package schoolAdministration;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class User {
	String id;
	String name;
	String role;

	public User(String id, String name, String role) {
		super();
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
		System.out.println("Admin ID: " + id + ", Name: " + name);
	}
}

class Teacher extends User {
	String subject;

	Teacher(String id, String name, String subject) {
		super(id, name, "Teacher");
		this.subject = subject;
	}

	void showDetails() {
		System.out.println("Teacher ID: " + id + ", Name: " + name + ",Subject: " + subject);
	}
}

class Student extends User {
	String grade;

	Student(String id, String name, String grade) {
		super(id, name, "Student");
		this.grade = grade;
	}

	void showDetails() {
		System.out.println("Student ID: " + id + ", Name: " + name + ", Grade: " + grade);
	}
}

public class ProjectUsingCollection {
	static Scanner sc = new Scanner(System.in);

	static Map<String, Admin> adminMap = new HashMap<>();
	static Map<String, Teacher> teacherMap = new HashMap<>();
	static Map<String, Student> studentMap = new HashMap<>();

	public static void main(String[] args) {
		adminMap.put("A1", new Admin("A1", "Manish"));
		teacherMap.put("T1", new Teacher("T1", "Kajal", "java"));
		studentMap.put("S1", new Student("S1", "Gopal", "10"));

		while (true) {
			System.out.println("----School Administration System----");
			System.out.println("Enter Your Role(Admin/Teacher/Student)");
			String role = sc.nextLine();
			switch (role.toLowerCase()) {
			case "admin":
				adminPanel();
				break;

			case "teacher":
				teacherPanel();
				break;

			case "student":
				studentPanel();
				break;

			default:
				System.out.println("Invalid Role...");
			}
		}
	}

	static void adminPanel() {
		System.out.println("Enter Admin ID: ");
		String id = sc.nextLine();

		Admin admin = adminMap.get(id);
		if (admin != null) {
			System.out.println("Welcome " + admin.name);
			while (true) {
				System.out.println("----Admin Menu----");
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
					for (Teacher teacher : teacherMap.values()) {
						teacher.showDetails();
					}
					break;

				case 2:
					for (Student student : studentMap.values()) {
						student.showDetails();
						break;
					}

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
					System.out.println("Invalid Choice...");
				}
			}

		} else {
			System.out.println("Admin not found");
		}
	}

	static void teacherPanel() {
		System.out.println("Enter Teacher ID: ");
		String id = sc.nextLine();
		Teacher teacher = teacherMap.get(id);
		if (teacher != null) {
			System.out.println("--- Welocme " + teacher.name);
			while (true) {
				System.out.println("----Teacher Menu----");
				System.out.println("1. View Own Record");
				System.out.println("2. View Student Record");
				System.out.println("3. Exit");
				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					teacher.showDetails();
					break;

				case 2:
					for (Student student : studentMap.values()) {
						student.showDetails();
					}
					break;

				case 3:
					return;

				default:
					System.out.println("Invalid Choice");
				}
			}
		} else {
			System.out.println("Teacher not found");
		}
	}

	static void studentPanel() {
		System.out.println("Enter Student ID: ");
		String id = sc.nextLine();
		Student student = studentMap.get(id);
		if (student != null) {
			System.out.println("--- Welocme " + student.name);
			while (true) {
				System.out.println("---- Student Menu ----");
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
					System.out.println("Invalid Choice...");
				}
			}
		} else {
			System.out.println("Student not found");
		}
	}

	static void addTeacher() {
		System.out.println("Enter Tecaher ID ");
		String id = sc.next();
		System.out.println("Enter Name: ");
		String name = sc.next();
		System.out.println("Enter Subject: ");
		String subject = sc.next();
		teacherMap.put(id, new Teacher(id, name, subject));
		System.out.println("Teacher added");
	}

	static void addStudent() {
		System.out.println("Enter Student ID");
		String id = sc.next();
		System.out.println("Enter Name: ");
		String name = sc.next();
		System.out.println("Enter Grade: ");
		String grade = sc.next();
		studentMap.put(id, new Student(id, name, grade));
		System.out.println("Student added.");
	}

	static void deleteTeacher() {
		System.out.println("Enter Teacher ID for Delete: ");
		String id = sc.next();
		if (teacherMap.remove(id) != null) {
			System.out.println("Teacher removed: ");
		} else {
			System.out.println("Teacher not found");
		}
	}

	static void deleteStudent() {
		System.out.println("Enter Student ID for Delete: ");
		String id = sc.next();
		if (studentMap.remove(id) != null) {
			System.out.println("Student removed. ");
		} else {
			System.out.println("Student not found. ");
		}
	}
}