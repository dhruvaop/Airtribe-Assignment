package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(
        EnrollmentRepository enrollmentRepository,
        StudentService studentService,
        CourseService courseService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId) throws EntityNotFoundException, InvalidInputException {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("Student " + studentId + " is inactive. Activate before enrollment.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Course " + courseId + " is inactive. Activate before enrollment.");
        }
        if (enrollmentRepository.hasActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException("Student is already actively enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment(
            IdGenerator.getNextEnrollmentId(),
            studentId,
            courseId,
            LocalDate.now(),
            EnrollmentStatus.ACTIVE
        );
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    public Enrollment updateEnrollmentStatus(int enrollmentId, EnrollmentStatus status)
        throws EntityNotFoundException, InvalidInputException {
        if (status == null) {
            throw new InvalidInputException("Enrollment status cannot be null.");
        }
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        enrollment.setStatus(status);
        return enrollment;
    }

    public Enrollment findEnrollmentById(int enrollmentId) throws EntityNotFoundException {
        return enrollmentRepository.findById(enrollmentId)
            .orElseThrow(() -> new EntityNotFoundException("Enrollment not found for id: " + enrollmentId));
    }

    public List<Enrollment> listEnrollmentsByStudentId(int studentId) throws EntityNotFoundException {
        studentService.findStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> listAllEnrollments() {
        return enrollmentRepository.findAll();
    }
}
