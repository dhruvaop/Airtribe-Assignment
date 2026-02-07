# Implementation Guide

Quick reference for implementing each component.

## Order of Implementation

### Phase 1: Utilities (Start Here!)
1. **IdGenerator.java** - Simple static class with counters
2. **InputValidator.java** - Helper methods for validation
3. **AppConstants.java** - Store app-wide constants
4. **MenuOptions.java** - Menu number constants

### Phase 2: Basic Structure
5. **EnrollmentStatus.java** - Enum with ACTIVE, COMPLETED, CANCELLED
6. **EntityNotFoundException.java** - Custom exception
7. **InvalidInputException.java** - Custom exception

### Phase 3: Entities (Core classes)
8. **Person.java** - Base class with common fields
9. **Student.java** - Extends Person, adds batch and active
10. **Course.java** - Course details
11. **Enrollment.java** - Links students to courses

### Phase 4: Data Layer
12. **StudentRepository.java** - ArrayList operations for students
13. **CourseRepository.java** - ArrayList operations for courses
14. **EnrollmentRepository.java** - ArrayList operations for enrollments

### Phase 5: Business Logic
15. **StudentService.java** - Business logic for students
16. **CourseService.java** - Business logic for courses
17. **EnrollmentService.java** - Business logic for enrollments

### Phase 6: User Interface
18. **Main.java** - Build the complete menu system

## Tips

- Test each class as you build it
- Add a simple test in Main.java to verify it works
- Don't move to next phase until current phase works
- Commit to git after each working feature

## Common Issues

**Can't find symbol errors:**
- Check package declarations match folder structure
- Make sure you're importing classes correctly

**NullPointerException:**
- Initialize your ArrayLists in constructors
- Check if objects exist before using them

**Scanner buffer issues:**
- Always add `scanner.nextLine()` after `scanner.nextInt()`

## Testing Strategy

After implementing each component:
1. Create a test in Main.java
2. Run and verify output
3. Fix any issues
4. Comment out test code and move to next component

Example test for IdGenerator:
```java
System.out.println("Testing IdGenerator:");
System.out.println("Student ID: " + IdGenerator.getNextStudentId());
System.out.println("Course ID: " + IdGenerator.getNextCourseId());
```

## Keep It Simple

Don't overthink it! Start with basic functionality:
- Add/view features first
- Then add search
- Then add update/deactivate
- Polish last

Good luck! You got this! 🚀
