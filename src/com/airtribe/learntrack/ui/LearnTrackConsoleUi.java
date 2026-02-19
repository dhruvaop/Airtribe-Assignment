package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

public final class LearnTrackConsoleUi {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    private LearnTrackConsoleUi() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        this.studentService = new StudentService(studentRepository);
        this.courseService = new CourseService(courseRepository);
        this.enrollmentService = new EnrollmentService(enrollmentRepository, studentService, courseService);
    }

    public static void start() {
        new LearnTrackConsoleUi().run();
    }

    private void run() {
        boolean running = true;
        System.out.println("Welcome to LearnTrack - Student & Course Management");
        while (running) {
            printMainMenu();
            try {
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 -> manageStudents();
                    case 2 -> manageCourses();
                    case 3 -> manageEnrollments();
                    case 0 -> {
                        running = false;
                        System.out.println("Exiting LearnTrack. Goodbye.");
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InvalidInputException exception) {
                System.out.println("Input error: " + exception.getMessage());
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("=== Main Menu ===");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("0. Exit");
    }

    private void manageStudents() {
        boolean back = false;
        while (!back) {
            printStudentMenu();
            try {
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> listStudents();
                    case 3 -> searchStudentById();
                    case 4 -> updateStudent();
                    case 5 -> deactivateStudent();
                    case 6 -> removeStudent();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }
    }

    private void printStudentMenu() {
        System.out.println();
        System.out.println("--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Deactivate Student");
        System.out.println("6. Remove Student");
        System.out.println("0. Back to Main Menu");
    }

    private void addStudent() throws InvalidInputException {
        String firstName = readRequiredText("First name: ", "First name");
        String lastName = readRequiredText("Last name: ", "Last name");
        String batch = readRequiredText("Batch: ", "Batch");
        String email = readOptionalText("Email (leave blank to auto-generate): ");

        Student student;
        if (email.isBlank()) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            student = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Student added successfully: " + student);
    }

    private void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void searchStudentById() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student id: ");
        Student student = studentService.findStudentById(studentId);
        System.out.println(student);
    }

    private void updateStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student id to update: ");
        String firstName = readRequiredText("Updated first name: ", "First name");
        String lastName = readRequiredText("Updated last name: ", "Last name");
        String email = readRequiredText("Updated email: ", "Email");
        String batch = readRequiredText("Updated batch: ", "Batch");
        boolean active = readBoolean("Is student active? (y/n): ");

        Student updatedStudent = studentService.updateStudent(studentId, firstName, lastName, email, batch, active);
        System.out.println("Student updated: " + updatedStudent);
    }

    private void deactivateStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student id to deactivate: ");
        studentService.deactivateStudent(studentId);
        System.out.println("Student deactivated successfully.");
    }

    private void removeStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student id to remove: ");
        studentService.removeStudent(studentId);
        System.out.println("Student removed successfully.");
    }

    private void manageCourses() {
        boolean back = false;
        while (!back) {
            printCourseMenu();
            try {
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 -> addCourse();
                    case 2 -> listCourses();
                    case 3 -> searchCourseById();
                    case 4 -> updateCourseStatus();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }
    }

    private void printCourseMenu() {
        System.out.println();
        System.out.println("--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by ID");
        System.out.println("4. Activate/Deactivate Course");
        System.out.println("0. Back to Main Menu");
    }

    private void addCourse() throws InvalidInputException {
        String courseName = readRequiredText("Course name: ", "Course name");
        String description = readRequiredText("Description: ", "Description");
        int durationInWeeks = readInt("Duration in weeks: ");

        Course course = courseService.addCourse(courseName, description, durationInWeeks);
        System.out.println("Course added successfully: " + course);
    }

    private void listCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void searchCourseById() throws InvalidInputException, EntityNotFoundException {
        int courseId = readInt("Enter course id: ");
        Course course = courseService.findCourseById(courseId);
        System.out.println(course);
    }

    private void updateCourseStatus() throws InvalidInputException, EntityNotFoundException {
        int courseId = readInt("Enter course id: ");
        boolean shouldBeActive = readBoolean("Set course active? (y/n): ");
        Course course = courseService.setCourseActiveStatus(courseId, shouldBeActive);
        System.out.println("Course status updated: " + course);
    }

    private void manageEnrollments() {
        boolean back = false;
        while (!back) {
            printEnrollmentMenu();
            try {
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 -> enrollStudentInCourse();
                    case 2 -> viewEnrollmentsForStudent();
                    case 3 -> updateEnrollmentStatus();
                    case 4 -> viewAllEnrollments();
                    case 0 -> back = true;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InvalidInputException | EntityNotFoundException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }
    }

    private void printEnrollmentMenu() {
        System.out.println();
        System.out.println("--- Enrollment Management ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. View Enrollments for Student");
        System.out.println("3. Update Enrollment Status");
        System.out.println("4. View All Enrollments");
        System.out.println("0. Back to Main Menu");
    }

    private void enrollStudentInCourse() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student id: ");
        int courseId = readInt("Enter course id: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created: " + enrollment);
    }

    private void viewEnrollmentsForStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Enter student id: ");
        List<Enrollment> enrollments = enrollmentService.listEnrollmentsByStudentId(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for student " + studentId + ".");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private void updateEnrollmentStatus() throws InvalidInputException, EntityNotFoundException {
        int enrollmentId = readInt("Enter enrollment id: ");
        EnrollmentStatus status = readEnrollmentStatus();
        Enrollment enrollment = enrollmentService.updateEnrollmentStatus(enrollmentId, status);
        System.out.println("Enrollment updated: " + enrollment);
    }

    private void viewAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.listAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private EnrollmentStatus readEnrollmentStatus() throws InvalidInputException {
        System.out.println("Select status:");
        System.out.println("1. ACTIVE");
        System.out.println("2. COMPLETED");
        System.out.println("3. CANCELLED");
        int option = readInt("Enter status option: ");
        return switch (option) {
            case 1 -> EnrollmentStatus.ACTIVE;
            case 2 -> EnrollmentStatus.COMPLETED;
            case 3 -> EnrollmentStatus.CANCELLED;
            default -> throw new InvalidInputException("Invalid enrollment status option.");
        };
    }

    private int readInt(String prompt) throws InvalidInputException {
        String rawValue = readOptionalText(prompt);
        if (rawValue.isBlank()) {
            throw new InvalidInputException("Value cannot be empty.");
        }
        try {
            return Integer.parseInt(rawValue.trim());
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Please enter a valid number.");
        }
    }

    private boolean readBoolean(String prompt) throws InvalidInputException {
        String rawValue = readOptionalText(prompt).trim().toLowerCase();
        if ("y".equals(rawValue) || "yes".equals(rawValue)) {
            return true;
        }
        if ("n".equals(rawValue) || "no".equals(rawValue)) {
            return false;
        }
        throw new InvalidInputException("Please enter y/yes or n/no.");
    }

    private String readRequiredText(String prompt, String fieldName) throws InvalidInputException {
        String value = readOptionalText(prompt);
        if (value.isBlank()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    private String readOptionalText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
