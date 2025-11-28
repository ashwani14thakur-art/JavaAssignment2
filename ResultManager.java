import java.util.Scanner;

// Custom Exception
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    int rollNumber;
    String studentName;
    int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < 3; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i+1) + ": " + marks[i]);
            }
        }
    }

    public double calculateAverage() {
        return (marks[0] + marks[1] + marks[2]) / 3.0;
    }

    // Displaying details of the students
    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);

        System.out.print("Marks: ");
        for (int m : marks) {
            System.out.print(m + " ");
        }
        System.out.println();

        double avg = calculateAverage();
        System.out.println("Average: " + avg);

        if (avg >= 40)
            System.out.println("Result: Pass");
        else
            System.out.println("Result: Fail");
    }
}

public class ResultManager {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Student[] students = new Student[10];
        int count = 0;

        try {
            int choice;

            do {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("Enter Roll Number: ");
                            int roll = sc.nextInt();
                            sc.nextLine(); // clear buffer

                            System.out.print("Enter Student Name: ");
                            String name = sc.nextLine();

                            int[] marks = new int[3];
                            for (int i = 0; i < 3; i++) {
                                System.out.print("Enter marks for subject " + (i+1) + ": ");
                                marks[i] = sc.nextInt();
                            }

                            Student s = new Student(roll, name, marks);
                            s.validateMarks(); // may throw custom exception

                            students[count] = s;
                            count++;
                            System.out.println("Student added successfully.");

                        } catch (InvalidMarksException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Input error! Please enter valid details.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Roll Number to search: ");
                        int searchRoll = sc.nextInt();

                        boolean found = false;
                        for (int i = 0; i < count; i++) {
                            if (students[i].rollNumber == searchRoll) {
                                students[i].displayResult();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Student not found.");
                        }
                        break;

                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } while (choice != 3);

        } finally {
            sc.close();
            System.out.println("Scanner closed.");
        }
    }
}
