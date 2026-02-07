# LearnTrack - Student & Course Management System

Hey! This is LearnTrack, a console-based student and course management system I built to practice Java fundamentals.

## What does it do?

Basically, you can:
- Add and manage students
- Create courses
- Enroll students in courses
- Track everything through a simple menu

It's all in the terminal - no fancy UI yet, but it gets the job done!

## Tech Stack

- Java (JDK 17)
- Just core Java - no frameworks or libraries
- ArrayList for data storage (keeping it simple for now)

## Project Structure

```
src/
└── com/airtribe/learntrack/
    ├── Main.java              # Entry point with menu
    ├── entity/                # Student, Course, Enrollment classes
    ├── repository/            # Data storage layer
    ├── service/               # Business logic
    ├── exception/             # Custom exceptions
    ├── util/                  # Helper classes
    ├── constants/             # App constants
    └── enums/                 # Status enums
```

## Getting Started

### Prerequisites
- Java JDK 11 or higher installed
- Any Java IDE (I used IntelliJ IDEA)

### How to Run

**Using an IDE:**
1. Open the project in your IDE
2. Run `Main.java`
3. Follow the menu options

**Using Command Line:**
```bash
# Compile
javac -d bin src/com/airtribe/learntrack/**/*.java

# Run
java -cp bin com.airtribe.learntrack.Main
```

## Features Implemented

### Student Management
- Add new students (auto-generates IDs)
- View all students
- Search by ID
- Deactivate students instead of deleting them

### Course Management
- Create courses with duration
- List all courses
- Activate/deactivate courses

### Enrollment Management
- Enroll students in courses
- View enrollments by student
- Mark enrollments as completed or cancelled
- Prevents duplicate enrollments

## What I Learned

This project helped me practice:
- **OOP concepts** - inheritance (Student extends Person), encapsulation, polymorphism
- **Collections** - used ArrayList instead of arrays for flexibility
- **Exception handling** - created custom exceptions for better error messages
- **Design patterns** - repository pattern for data access, service layer for business logic
- **Static members** - for ID generation and constants

Some challenges I faced:
- Understanding when to use static vs instance variables
- Managing the Scanner buffer (had to add `nextLine()` after `nextInt()` - learned that the hard way!)
- Keeping business logic separate from UI logic

## Known Issues

- Data doesn't persist (everything's in-memory)
- No input validation for email format yet
- Could use better error messages in some places

## Future Improvements

If I continue working on this:
- Add file-based storage (CSV or JSON)
- Better input validation
- Search by name, not just ID
- Export reports
- Maybe add a GUI later?

## Documentation

Check out the `docs/` folder for:
- Setup instructions
- JVM basics explanation
- Design decisions and notes

## Running Tests

Right now testing is manual:
1. Add a few students
2. Create some courses
3. Try enrolling students
4. Test error cases (invalid IDs, duplicate enrollments, etc.)

## Notes

This was my first real Java project beyond basic exercises. The code isn't perfect but I tried to keep it clean and follow best practices I've learned so far.

Feel free to check out the code and let me know if you spot any issues or have suggestions!

## License

This is a learning project - free to use however you want.

---

*Built as part of my Java learning journey*
