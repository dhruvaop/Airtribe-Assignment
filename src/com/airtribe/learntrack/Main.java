package com.airtribe.learntrack;

import com.airtribe.learntrack.util.IdGenerator;

/**
 * LearnTrack - Student & Course Management System
 * Main entry point for the application
 * 
 * @author Your Name
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("  Welcome to LearnTrack v1.0");
        System.out.println("  Student & Course Management System");
        System.out.println("=".repeat(50));
        System.out.println();
        
        // Test IdGenerator
        System.out.println("Testing IdGenerator...");
        System.out.println("Student ID 1: " + IdGenerator.getNextStudentId());
        System.out.println("Student ID 2: " + IdGenerator.getNextStudentId());
        System.out.println("Course ID 1: " + IdGenerator.getNextCourseId());
        System.out.println("Enrollment ID 1: " + IdGenerator.getNextEnrollmentId());
        
        System.out.println("\n✓ Setup complete! Ready to build the project.");
        System.out.println("\nNext steps:");
        System.out.println("1. Implement entity classes (Person, Student, Course, Enrollment)");
        System.out.println("2. Create repository layer");
        System.out.println("3. Build service layer");
        System.out.println("4. Add menu-driven UI");
    }
}
