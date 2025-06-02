package schoolAdministrationUsingMongoDB;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
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
        System.out.println("Teacher ID: " + id + ", Name: " + name + ", Subject: " + subject);
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

public class ProjectUsingMongoDB {
    static Scanner sc = new Scanner(System.in);
    static MongoClient mongoClient;
    static MongoDatabase database;

    public static void main(String[] args) {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("schoolDB");

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Admin Panel
    public static void adminPanel() {
        MongoCollection<Document> adminCollection = database.getCollection("admins");

        System.out.print("Enter Admin ID: ");
        String id = sc.nextLine();

        Document adminDoc = adminCollection.find(Filters.eq("id", id)).first();

        if (adminDoc != null) {
            Admin admin = new Admin(adminDoc.getString("id"), adminDoc.getString("name"));
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
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> viewAllTeachers();
                    case 2 -> viewAllStudents();
                    case 3 -> admin.showDetails();
                    case 4 -> addTeacher();
                    case 5 -> addStudent();
                    case 6 -> deleteTeacher();
                    case 7 -> deleteStudent();
                    case 8 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Admin not found.");
        }
    }

    // Teacher Panel
    public static void teacherPanel() {
        MongoCollection<Document> teacherCollection = database.getCollection("teachers");

        System.out.print("Enter Teacher ID: ");
        String id = sc.nextLine();

        Document doc = teacherCollection.find(Filters.eq("id", id)).first();

        if (doc != null) {
            Teacher teacher = new Teacher(doc.getString("id"), doc.getString("name"), doc.getString("subject"));
            System.out.println("\nWelcome " + teacher.name);

            while (true) {
                System.out.println("\n--- Teacher Menu ---");
                System.out.println("1. View Own Record");
                System.out.println("2. View Student Records");
                System.out.println("3. Exit");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> teacher.showDetails();
                    case 2 -> viewAllStudents();
                    case 3 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Teacher not found.");
        }
    }

    // Student Panel
    public static void studentPanel() {
        MongoCollection<Document> studentCollection = database.getCollection("students");

        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        Document doc = studentCollection.find(Filters.eq("id", id)).first();

        if (doc != null) {
            Student student = new Student(doc.getString("id"), doc.getString("name"), doc.getString("grade"));
            System.out.println("\nWelcome " + student.name);

            while (true) {
                System.out.println("\n--- Student Menu ---");
                System.out.println("1. View Own Record");
                System.out.println("2. Exit");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> student.showDetails();
                    case 2 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    // View All Teachers
    public static void viewAllTeachers() {
        MongoCollection<Document> teacherCollection = database.getCollection("teachers");

        for (Document doc : teacherCollection.find()) {
            Teacher t = new Teacher(doc.getString("id"), doc.getString("name"), doc.getString("subject"));
            t.showDetails();
        }
    }

    // View All Students
    public static void viewAllStudents() {
        MongoCollection<Document> studentCollection = database.getCollection("students");

        for (Document doc : studentCollection.find()) {
            Student s = new Student(doc.getString("id"), doc.getString("name"), doc.getString("grade"));
            s.showDetails();
        }
    }

    // Add Teacher
    public static void addTeacher() {
        MongoCollection<Document> teacherCollection = database.getCollection("teachers");

        System.out.print("Enter Teacher ID: ");
        String id = sc.next();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();

        Document doc = new Document("id", id)
                .append("name", name)
                .append("subject", subject);

        teacherCollection.insertOne(doc);
        System.out.println("Teacher added.");
    }

    // Add Student
    public static void addStudent() {
        MongoCollection<Document> studentCollection = database.getCollection("students");

        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        Document doc = new Document("id", id)
                .append("name", name)
                .append("grade", grade);

        studentCollection.insertOne(doc);
        System.out.println("Student added.");
    }

    // Delete Teacher
    public static void deleteTeacher() {
        MongoCollection<Document> teacherCollection = database.getCollection("teachers");

        System.out.print("Enter Teacher ID to delete: ");
        String id = sc.nextLine();

        if (teacherCollection.deleteOne(Filters.eq("id", id)).getDeletedCount() > 0) {
            System.out.println("Teacher removed.");
        } else {
            System.out.println("Teacher not found.");
        }
    }

    // Delete Student
    public static void deleteStudent() {
        MongoCollection<Document> studentCollection = database.getCollection("students");

        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine();

        if (studentCollection.deleteOne(Filters.eq("id", id)).getDeletedCount() > 0) {
            System.out.println("Student removed.");
        } else {
            System.out.println("Student not found.");
        }
    }
}
