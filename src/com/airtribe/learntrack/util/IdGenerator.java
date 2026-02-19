package com.airtribe.learntrack.util;

public final class IdGenerator {
    private static int studentIdCounter = 1000;
    private static int courseIdCounter = 2000;
    private static int enrollmentIdCounter = 3000;

    private IdGenerator() {
    }

    public static synchronized int getNextStudentId() {
        studentIdCounter++;
        return studentIdCounter;
    }

    public static synchronized int getNextCourseId() {
        courseIdCounter++;
        return courseIdCounter;
    }

    public static synchronized int getNextEnrollmentId() {
        enrollmentIdCounter++;
        return enrollmentIdCounter;
    }
}
