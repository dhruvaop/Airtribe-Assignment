package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) throws InvalidInputException {
        String validCourseName = InputValidator.validateNonBlank(courseName, "Course name");
        String validDescription = InputValidator.validateNonBlank(description, "Description");
        int validDuration = InputValidator.validateDurationInWeeks(durationInWeeks);

        Course course = new Course(
            IdGenerator.getNextCourseId(),
            validCourseName,
            validDescription,
            validDuration,
            true
        );
        courseRepository.save(course);
        return course;
    }

    public Course findCourseById(int courseId) throws EntityNotFoundException {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found for id: " + courseId));
    }

    public Course setCourseActiveStatus(int courseId, boolean active) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        course.setActive(active);
        return course;
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }
}
