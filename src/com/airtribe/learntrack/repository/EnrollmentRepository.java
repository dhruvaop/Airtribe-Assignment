package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public Optional<Enrollment> findById(int enrollmentId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == enrollmentId) {
                return Optional.of(enrollment);
            }
        }
        return Optional.empty();
    }

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    public boolean hasActiveEnrollment(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId
                && enrollment.getCourseId() == courseId
                && enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }
}
