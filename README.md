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

