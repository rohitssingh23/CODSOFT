import java.util.Scanner;

public class Student {
    private String name;
    private int[] marks;
    private int totalMarks;
    private double averagePercentage;
    private Grade grade;

    public Student(String name, int[] marks) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
        this.marks = marks;
        this.totalMarks = calculateTotalMarks();
        this.averagePercentage = calculateAveragePercentage();
        this.grade = calculateGrade();
    }

    private int calculateTotalMarks() {
        return java.util.Arrays.stream(marks).sum();
    }

    private double calculateAveragePercentage() {
        return (double) totalMarks / marks.length;
    }

    private Grade calculateGrade() {
        if (averagePercentage >= 90) {
            return Grade.A;
        } else if (averagePercentage >= 80) {
            return Grade.B;
        } else if (averagePercentage >= 70) {
            return Grade.C;
        } else if (averagePercentage >= 60) {
            return Grade.D;
        } else {
            return Grade.F;
        }
    }

    public void displayResults() {
        System.out.println("----- Student Report -----");
        System.out.println("Name: " + name);
        System.out.println("Total Marks: " + totalMarks);
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty. Exiting program.");
            return;
        }

        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();

        int[] marks = new int[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            int mark;
            do {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                mark = scanner.nextInt();
                if (mark < 0 || mark > 100) {
                    System.out.println("Invalid marks. Marks should be between 0 and 100.");
                }
            } while (mark < 0 || mark > 100);
            marks[i] = mark;
        }

        Student student = new Student(name, marks);
        student.displayResults();

        scanner.close();
    }

    public enum Grade {
        A, B, C, D, F
    }
    
    public String getName() {
        return name;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public Grade getGrade() {
        return grade;
    }
}
