package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(String firstName, String lastName, String email, String batch) throws InvalidInputException {
        String validFirstName = InputValidator.validateNonBlank(firstName, "First name");
        String validLastName = InputValidator.validateNonBlank(lastName, "Last name");
        String validEmail = InputValidator.validateEmail(email);
        String validBatch = InputValidator.validateNonBlank(batch, "Batch");

        Student student = new Student(
            IdGenerator.getNextStudentId(),
            validFirstName,
            validLastName,
            validEmail,
            validBatch,
            true
        );
        studentRepository.save(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) throws InvalidInputException {
        String generatedEmail = InputValidator.generateDefaultEmail(firstName, lastName);
        return addStudent(firstName, lastName, generatedEmail, batch);
    }

    public Student findStudentById(int studentId) throws EntityNotFoundException {
        return studentRepository.findById(studentId)
            .orElseThrow(() -> new EntityNotFoundException("Student not found for id: " + studentId));
    }

    public Student updateStudent(
        int studentId,
        String firstName,
        String lastName,
        String email,
        String batch,
        boolean active
    ) throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(studentId);
        student.setFirstName(InputValidator.validateNonBlank(firstName, "First name"));
        student.setLastName(InputValidator.validateNonBlank(lastName, "Last name"));
        student.setEmail(InputValidator.validateEmail(email));
        student.setBatch(InputValidator.validateNonBlank(batch, "Batch"));
        student.setActive(active);
        return student;
    }

    public void deactivateStudent(int studentId) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        student.setActive(false);
    }

    public void removeStudent(int studentId) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        studentRepository.delete(student);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }
}
