import java.util.ArrayList;
import java.util.Scanner;

/**
 * CodeAlpha Internship - Task 1: Student Grade Tracker
 * A console-based program to manage student grades,
 * calculate statistics, and display a summary report.
 */
public class StudentGradeTracker {

    // Inner class to represent a Student
    static class Student {
        String name;
        ArrayList<Double> grades;

        // Constructor
        Student(String name) {
            this.name = name;
            this.grades = new ArrayList<>();
        }

        // Add a grade for this student
        void addGrade(double grade) {
            grades.add(grade);
        }

        // Calculate average grade
        double getAverage() {
            if (grades.isEmpty()) return 0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        // Get highest grade
        double getHighest() {
            if (grades.isEmpty()) return 0;
            double max = grades.get(0);
            for (double g : grades) if (g > max) max = g;
            return max;
        }

        // Get lowest grade
        double getLowest() {
            if (grades.isEmpty()) return 0;
            double min = grades.get(0);
            for (double g : grades) if (g < min) min = g;
            return min;
        }

        // Convert average to letter grade
        String getLetterGrade() {
            double avg = getAverage();
            if (avg >= 90) return "A";
            else if (avg >= 80) return "B";
            else if (avg >= 70) return "C";
            else if (avg >= 60) return "D";
            else return "F";
        }
    }

    // List to store all students
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     STUDENT GRADE TRACKER v1.0       ║");
        System.out.println("║          CodeAlpha Internship         ║");
        System.out.println("╚══════════════════════════════════════╝");

        int choice;
        do {
            printMenu();
            choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: addGradeToStudent(); break;
                case 3: viewStudentReport(); break;
                case 4: viewAllStudents(); break;
                case 5: viewClassSummary(); break;
                case 6: System.out.println("\nThank you for using Student Grade Tracker. Goodbye!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    // Display main menu
    static void printMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("  1. Add New Student");
        System.out.println("  2. Add Grade to Student");
        System.out.println("  3. View Student Report");
        System.out.println("  4. View All Students");
        System.out.println("  5. View Class Summary");
        System.out.println("  6. Exit");
        System.out.println("================================");
    }

    // Add a new student
    static void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        // Check if student already exists
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.println("Student '" + name + "' already exists!");
                return;
            }
        }

        students.add(new Student(name));
        System.out.println("✔ Student '" + name + "' added successfully!");
    }

    // Add a grade to a specific student
    static void addGradeToStudent() {
        if (students.isEmpty()) {
            System.out.println("No students found. Please add a student first.");
            return;
        }

        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        Student student = findStudent(name);

        if (student == null) {
            System.out.println("Student '" + name + "' not found.");
            return;
        }

        double grade = getDoubleInput("Enter grade (0 - 100): ");
        if (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100.");
            return;
        }

        student.addGrade(grade);
        System.out.println("✔ Grade " + grade + " added for " + student.name);
    }

    // View detailed report for one student
    static void viewStudentReport() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        Student student = findStudent(name);

        if (student == null) {
            System.out.println("Student '" + name + "' not found.");
            return;
        }

        System.out.println("\n-------- Student Report --------");
        System.out.println("Name    : " + student.name);
        System.out.println("Grades  : " + student.grades);

        if (student.grades.isEmpty()) {
            System.out.println("No grades recorded yet.");
        } else {
            System.out.printf("Average : %.2f%n", student.getAverage());
            System.out.printf("Highest : %.2f%n", student.getHighest());
            System.out.printf("Lowest  : %.2f%n", student.getLowest());
            System.out.println("Grade   : " + student.getLetterGrade());
        }
        System.out.println("--------------------------------");
    }

    // View list of all students
    static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n-------- All Students --------");
        System.out.printf("%-5s %-20s %-10s %-8s%n", "No.", "Name", "Average", "Grade");
        System.out.println("----------------------------------------------");

        int i = 1;
        for (Student s : students) {
            if (s.grades.isEmpty()) {
                System.out.printf("%-5d %-20s %-10s %-8s%n", i++, s.name, "N/A", "N/A");
            } else {
                System.out.printf("%-5d %-20s %-10.2f %-8s%n", i++, s.name, s.getAverage(), s.getLetterGrade());
            }
        }
        System.out.println("----------------------------------------------");
    }

    // View overall class summary
    static void viewClassSummary() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        double classTotal = 0;
        double classHighest = Double.MIN_VALUE;
        double classLowest = Double.MAX_VALUE;
        String topStudent = "", lowestStudent = "";
        int count = 0;

        for (Student s : students) {
            if (!s.grades.isEmpty()) {
                double avg = s.getAverage();
                classTotal += avg;
                count++;

                if (avg > classHighest) {
                    classHighest = avg;
                    topStudent = s.name;
                }
                if (avg < classLowest) {
                    classLowest = avg;
                    lowestStudent = s.name;
                }
            }
        }

        System.out.println("\n-------- Class Summary --------");
        System.out.println("Total Students  : " + students.size());
        System.out.println("Graded Students : " + count);

        if (count > 0) {
            System.out.printf("Class Average   : %.2f%n", classTotal / count);
            System.out.printf("Highest Average : %.2f (%s)%n", classHighest, topStudent);
            System.out.printf("Lowest Average  : %.2f (%s)%n", classLowest, lowestStudent);
        } else {
            System.out.println("No grades have been recorded yet.");
        }
        System.out.println("-------------------------------");
    }

    // Helper: find a student by name (case-insensitive)
    static Student findStudent(String name) {
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    // Helper: safely get integer input
    static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Helper: safely get double input
    static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
