# Complete Code Reference

This file has all the code you need to implement. Copy and paste as needed, but make sure you understand what each piece does!

## Utility Classes

### IdGenerator.java
```java
package com.airtribe.learntrack.util;

public class IdGenerator {
    private static int studentIdCounter = 1000;
    private static int courseIdCounter = 100;
    private static int enrollmentIdCounter = 10000;
    
    public static int getNextStudentId() {
        return studentIdCounter++;
    }
    
    public static int getNextCourseId() {
        return courseIdCounter++;
    }
    
    public static int getNextEnrollmentId() {
        return enrollmentIdCounter++;
    }
}
```

### InputValidator.java
```java
package com.airtribe.learntrack.util;

public class InputValidator {
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
```

## Constants & Enums

### AppConstants.java
```java
package com.airtribe.learntrack.constants;

public class AppConstants {
    public static final String APP_NAME = "LearnTrack";
    public static final String APP_VERSION = "1.0";
    public static final int MIN_COURSE_DURATION = 1;
    public static final int MAX_COURSE_DURATION = 52;
}
```

### MenuOptions.java
```java
package com.airtribe.learntrack.constants;

public class MenuOptions {
    // Main Menu
    public static final int STUDENT_MANAGEMENT = 1;
    public static final int COURSE_MANAGEMENT = 2;
    public static final int ENROLLMENT_MANAGEMENT = 3;
    public static final int EXIT = 4;
    
    // Sub-menu options
    public static final int ADD = 1;
    public static final int VIEW_ALL = 2;
    public static final int SEARCH = 3;
    public static final int BACK = 5;
}
```

### EnrollmentStatus.java
```java
package com.airtribe.learntrack.enums;

public enum EnrollmentStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED
}
```

## Exception Classes

### EntityNotFoundException.java
```java
package com.airtribe.learntrack.exception;

public class EntityNotFoundException extends Exception {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String entityType, int id) {
        super(entityType + " with ID " + id + " not found.");
    }
}
```

### InvalidInputException.java
```java
package com.airtribe.learntrack.exception;

public class InvalidInputException extends Exception {
    
    public InvalidInputException(String message) {
        super(message);
    }
}
```

## Entity Classes

### Person.java
```java
package com.airtribe.learntrack.entity;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    public Person() {}
    
    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public Person(int id, String firstName, String lastName) {
        this(id, firstName, lastName, "");
    }
    
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

### Student.java
```java
package com.airtribe.learntrack.entity;

public class Student extends Person {
    private String batch;
    private boolean active;
    
    public Student() {
        super();
        this.active = true;
    }
    
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }
    
    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName);
        this.batch = batch;
        this.active = true;
    }
    
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }
    
    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return "Student{id=" + getId() + ", name='" + getDisplayName() + 
               "', email='" + getEmail() + "', active=" + active + "}";
    }
}
```

### Course.java
```java
package com.airtribe.learntrack.entity;

public class Course {
    private int id;
    private String courseName;
    private String description;
    private int durationInWeeks;
    private boolean active;
    
    public Course() {
        this.active = true;
    }
    
    public Course(int id, String courseName, String description, int durationInWeeks) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.active = true;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDurationInWeeks() { return durationInWeeks; }
    public void setDurationInWeeks(int duration) { this.durationInWeeks = duration; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return "Course{id=" + id + ", name='" + courseName + 
               "', duration=" + durationInWeeks + " weeks}";
    }
}
```

### Enrollment.java
```java
package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.enums.EnrollmentStatus;
import java.time.LocalDate;

public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;
    
    public Enrollment() {
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }
    
    public Enrollment(int id, int studentId, int courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate date) { this.enrollmentDate = date; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Enrollment{id=" + id + ", studentId=" + studentId + 
               ", courseId=" + courseId + ", status=" + status + "}";
    }
}
```

## Repository Classes

### StudentRepository.java
```java
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private List<Student> students;
    
    public StudentRepository() {
        this.students = new ArrayList<>();
    }
    
    public void save(Student student) {
        students.add(student);
    }
    
    public Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
    
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
    
    public List<Student> findActiveStudents() {
        List<Student> active = new ArrayList<>();
        for (Student s : students) {
            if (s.isActive()) {
                active.add(s);
            }
        }
        return active;
    }
}
```

### CourseRepository.java
```java
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private List<Course> courses;
    
    public CourseRepository() {
        this.courses = new ArrayList<>();
    }
    
    public void save(Course course) {
        courses.add(course);
    }
    
    public Course findById(int id) {
        for (Course c : courses) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    
    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }
}
```

### EnrollmentRepository.java
```java
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {
    private List<Enrollment> enrollments;
    
    public EnrollmentRepository() {
        this.enrollments = new ArrayList<>();
    }
    
    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
    
    public Enrollment findById(int id) {
        for (Enrollment e : enrollments) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
    
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }
    
    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId) {
                result.add(e);
            }
        }
        return result;
    }
    
    public boolean isStudentEnrolled(int studentId, int courseId) {
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId && 
                e.getCourseId() == courseId && 
                e.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }
}
```

## Service Classes

### StudentService.java (Simplified)
```java
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class StudentService {
    private StudentRepository repository;
    
    public StudentService() {
        this.repository = new StudentRepository();
    }
    
    public Student addStudent(String firstName, String lastName, String email, String batch) 
            throws InvalidInputException {
        if (!InputValidator.isValidName(firstName)) {
            throw new InvalidInputException("First name cannot be empty");
        }
        if (!InputValidator.isValidName(lastName)) {
            throw new InvalidInputException("Last name cannot be empty");
        }
        
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        repository.save(student);
        return student;
    }
    
    public Student findStudentById(int id) throws EntityNotFoundException {
        Student student = repository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student", id);
        }
        return student;
    }
    
    public List<Student> getAllStudents() {
        return repository.findAll();
    }
    
    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
    }
}
```

### CourseService.java (Simplified)
```java
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

public class CourseService {
    private CourseRepository repository;
    
    public CourseService() {
        this.repository = new CourseRepository();
    }
    
    public Course addCourse(String name, String description, int duration) 
            throws InvalidInputException {
        if (!InputValidator.isValidName(name)) {
            throw new InvalidInputException("Course name cannot be empty");
        }
        
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, name, description, duration);
        repository.save(course);
        return course;
    }
    
    public Course findCourseById(int id) throws EntityNotFoundException {
        Course course = repository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course", id);
        }
        return course;
    }
    
    public List<Course> getAllCourses() {
        return repository.findAll();
    }
}
```

### EnrollmentService.java (Simplified)
```java
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.util.List;

public class EnrollmentService {
    private EnrollmentRepository repository;
    private StudentService studentService;
    private CourseService courseService;
    
    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.repository = new EnrollmentRepository();
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    public Enrollment enrollStudent(int studentId, int courseId) 
            throws EntityNotFoundException, InvalidInputException {
        // Validate student and course exist
        studentService.findStudentById(studentId);
        courseService.findCourseById(courseId);
        
        // Check if already enrolled
        if (repository.isStudentEnrolled(studentId, courseId)) {
            throw new InvalidInputException("Student already enrolled in this course");
        }
        
        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        repository.save(enrollment);
        return enrollment;
    }
    
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        return repository.findByStudentId(studentId);
    }
    
    public void completeEnrollment(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = repository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment", enrollmentId);
        }
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
    }
}
```

## Notes

- This is a simplified version to get you started
- You can add more validation and features as needed
- Test each class as you implement it
- Don't forget to handle exceptions properly in Main.java!

For the complete Main.java with menu system, check the full implementation guide or build it step by step starting with a simple menu.
