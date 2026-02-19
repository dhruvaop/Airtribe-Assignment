package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public Optional<Student> findById(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public boolean delete(Student student) {
        return students.remove(student);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
}
