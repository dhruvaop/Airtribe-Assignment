# Design Notes & Decisions

These are my notes on why I built LearnTrack the way I did. Some decisions were from requirements, others I figured out while building.

## Architecture Overview

I went with a layered approach because it makes the code easier to manage:

```
Main.java (UI/Menu)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Storage)
    ↓
Entity Layer (Data Models)
```

Each layer has a specific job, which helped me keep things organized.

## Why ArrayList instead of Arrays?

**Initial thought:** Arrays are simpler, why not just use those?

**What I learned:** Arrays are fixed size. If I declare `Student[] students = new Student[10]`, I can only store 10 students. What if someone wants to add an 11th?

With arrays, I'd have to:
1. Create a new bigger array
2. Copy everything over
3. Add the new student

That's annoying.

**ArrayList solution:**
```java
ArrayList<Student> students = new ArrayList<>();
students.add(newStudent);  // Just works, no size limits!
```

ArrayList grows automatically. Plus it has helpful methods like `remove()`, `contains()`, `size()` that I'd have to write myself with arrays.

**When I used ArrayList:**
- StudentRepository - don't know how many students
- CourseRepository - course catalog can grow
- EnrollmentRepository - enrollments are unpredictable

## Static vs Instance Variables

This confused me at first. Here's what I figured out:

### When I Used Static

**IdGenerator class:**
```java
private static int studentIdCounter = 1000;

public static int getNextStudentId() {
    return studentIdCounter++;
}
```

**Why static?** The counter needs to be shared across the entire application. If it wasn't static, each IdGenerator instance would start from 1000 again. That would create duplicate IDs!

**Constants:**
```java
public static final String APP_NAME = "LearnTrack";
```

These never change and are the same for everyone - perfect for static.

### When I Didn't Use Static

**Student fields:**
```java
private String name;  // NOT static!
private String email; // NOT static!
```

Each student needs their own name and email. If these were static, all students would share the same name! That's obviously wrong.

**Rule of thumb I follow:** If it's shared by everyone, use static. If each object needs its own, don't use static.

## Inheritance - Person → Student

I created a base Person class that Student extends. Here's why:

**Before (without inheritance):**
```java
// Lots of duplicate code
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String batch;
}

public class Trainer {
    private int id;          // Duplicate!
    private String firstName; // Duplicate!
    private String lastName;  // Duplicate!
    private String email;     // Duplicate!
    private String department;
}
```

**After (with inheritance):**
```java
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    // Common fields in one place!
}

public class Student extends Person {
    private String batch;  // Only student-specific stuff
}

public class Trainer extends Person {
    private String department;  // Only trainer-specific stuff
}
```

Much cleaner! No repeated code.

### Method Overriding

Person has a basic `getDisplayName()`:
```java
public String getDisplayName() {
    return firstName + " " + lastName;
}
```

Student overrides it to add batch info:
```java
@Override
public String getDisplayName() {
    return super.getDisplayName() + " (Batch: " + batch + ")";
}
```

This way I can get different display formats for different types of people. Pretty cool!

### Constructor Chaining

Student constructor calls Person's constructor using `super()`:
```java
public Student(int id, String firstName, String lastName, 
               String email, String batch) {
    super(id, firstName, lastName, email);  // Set up Person stuff
    this.batch = batch;  // Then set up Student stuff
}
```

Keeps code DRY (Don't Repeat Yourself).

## Repository Pattern

I separated data access from business logic. It seemed complicated at first but made sense later.

**Without Repository (messy):**
```java
public class StudentService {
    private ArrayList<Student> students = new ArrayList<>();
    
    public void addStudent(...) {
        // Business logic
        // Mixed with data access
        students.add(student);
    }
}
```

**With Repository (cleaner):**
```java
public class StudentRepository {
    private ArrayList<Student> students = new ArrayList<>();
    
    public void save(Student s) {
        students.add(s);  // Only handles storage
    }
}

public class StudentService {
    private StudentRepository repo;
    
    public void addStudent(...) {
        // Business logic only
        validate(...);
        Student s = new Student(...);
        repo.save(s);  // Delegate storage to repository
    }
}
```

**Why this is better:**
- If I want to switch from ArrayList to a database later, I only change the Repository
- Service stays focused on business rules
- Easier to test each layer separately

## Exception Handling Strategy

I created custom exceptions instead of using generic ones:

**Generic (not helpful):**
```java
throw new Exception("Something went wrong");
```

**Custom (helpful):**
```java
throw new EntityNotFoundException("Student", 1001);
// Message: "Student with ID 1001 not found."
```

**Created two main exceptions:**
1. `EntityNotFoundException` - when searching for stuff that doesn't exist
2. `InvalidInputException` - when user provides bad data

This gives much better error messages and I can catch specific problems:
```java
try {
    Student s = service.findStudentById(id);
} catch (EntityNotFoundException e) {
    System.out.println("Student not found! Please check the ID.");
} catch (InvalidInputException e) {
    System.out.println("Invalid input: " + e.getMessage());
}
```

## Constructor Overloading

I added multiple constructors to give flexibility:

```java
// Full details
public Student(int id, String firstName, String lastName, 
               String email, String batch) { ... }

// Without email (some students might not have one yet)
public Student(int id, String firstName, String lastName, String batch) { ... }

// Default constructor
public Student() { ... }
```

This way I can create students with whatever info I have available.

## Encapsulation

I made all fields private and added getters/setters:

```java
private String batch;  // Can't access directly

public String getBatch() { 
    return batch; 
}

public void setBatch(String batch) {
    // Could add validation here if needed
    this.batch = batch;
}
```

**Why bother?** Protection. If batch was public, anyone could set it to anything:
```java
student.batch = "";  // If it was public, this would work
```

With setters, I can add validation:
```java
public void setBatch(String batch) {
    if (batch == null || batch.isEmpty()) {
        throw new IllegalArgumentException("Batch cannot be empty");
    }
    this.batch = batch;
}
```

## Enums for Status

Instead of using strings for enrollment status:
```java
String status = "ACTIVE";  // What if someone types "Active" or "active"?
```

I used an enum:
```java
EnrollmentStatus status = EnrollmentStatus.ACTIVE;  // Can only be valid values!
```

**Benefits:**
- Type-safe - can't accidentally set to an invalid value
- IDE autocomplete shows all possible values
- No typos

## Package Organization

I organized code by function, not by type:

```
com.airtribe.learntrack/
├── entity/        # All data models
├── repository/    # All data access
├── service/       # All business logic
├── util/          # Helper classes
└── exception/     # Custom exceptions
```

This makes it easy to find related code. If I need to work on student stuff, all student-related code is in one place.

## Things I'd Improve

If I had more time or was building this for real:

1. **Data Persistence** - Right now everything disappears when the program closes. Would add file storage or database.

2. **Better Validation** - Currently basic. Would add proper email validation, phone numbers, etc.

3. **Search Features** - Can only search by ID right now. Would add search by name, batch, etc.

4. **Unit Tests** - Would write JUnit tests for the service layer.

5. **Logging** - Instead of System.out.println everywhere, use a proper logging framework.

6. **Builder Pattern** - For creating complex objects with many optional fields.

## What Worked Well

- **Layered architecture** made code organized and easy to navigate
- **Repository pattern** keeps data access separate
- **Custom exceptions** give clear error messages
- **Inheritance** eliminated duplicate code
- **Enums** prevented invalid status values

## What Was Challenging

- **Scanner buffer** - Took me a while to figure out the nextLine() after nextInt() issue
- **Deciding when to use static** - Still not 100% confident but getting better
- **Exception handling** - Deciding which exceptions to catch where
- **Keeping logic out of Main.java** - Was tempted to put everything there but forced myself to use services

## Lessons Learned

1. **Start simple** - I tried to over-engineer at first. Basic solutions work fine.
2. **Separation of concerns** - Keep different responsibilities in different classes.
3. **Name things clearly** - `findStudentById()` is way better than `get()` or `search()`.
4. **Don't repeat code** - If I'm copying and pasting, something's wrong.
5. **Test as you go** - Don't write everything then test. Test each piece.

## Design Principles I Tried to Follow

- **DRY** (Don't Repeat Yourself) - Used inheritance to avoid duplicate code
- **Single Responsibility** - Each class has one job
- **KISS** (Keep It Simple, Stupid) - Avoided over-complication
- **Meaningful Names** - Variable and method names explain what they do

---

*These notes helped me understand my own code better. Writing them down forced me to think through my decisions!*
