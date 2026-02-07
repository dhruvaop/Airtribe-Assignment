# Setup Instructions

## My Environment

**JDK Version:** 17.0.8
**IDE:** IntelliJ IDEA Community Edition
**OS:** Windows 11 (but should work on Mac/Linux too)

## Installing Java

I downloaded JDK 17 from https://adoptium.net/ - it's free and works great.

After installation, I checked it worked by running:
```bash
java -version
javac -version
```

Both commands should show version 17 or higher.

### If the commands don't work

You might need to set up environment variables. I had to do this on Windows:
1. Search for "Environment Variables" in Windows search
2. Edit the Path variable
3. Add the path to Java bin folder (mine was `C:\Program Files\Java\jdk-17\bin`)
4. Restart command prompt

## Setting Up the Project

### In IntelliJ IDEA (what I used)

1. Click "New Project"
2. Name it "LearnTrack"
3. Choose Java 17
4. Make sure "Add sample code" is unchecked
5. Click Create

Then I created the package structure by right-clicking on `src`:
- New → Package → `com.airtribe.learntrack`
- Then created subpackages: entity, repository, service, exception, util, constants, enums

### In VS Code

If you prefer VS Code:
1. Install "Extension Pack for Java"
2. Create a folder called "LearnTrack"
3. Open it in VS Code
4. Create the folder structure manually in `src/`

## First Test - Hello World

Created Main.java to test if everything works:

```java
package com.airtribe.learntrack;

public class Main {
    public static void main(String[] args) {
        System.out.println("LearnTrack is running!");
    }
}
```

Ran it and saw the output - success!

## Building the Project

### In IDE
Just hit the Run button (green triangle) - the IDE handles compilation automatically.

### Command Line
```bash
# From project root
javac -d bin src/com/airtribe/learntrack/**/*.java
java -cp bin com.airtribe.learntrack.Main
```

## Folder Structure After Setup

```
LearnTrack/
├── src/
│   └── com/airtribe/learntrack/
│       ├── Main.java
│       ├── entity/
│       ├── repository/
│       ├── service/
│       ├── exception/
│       ├── util/
│       ├── constants/
│       └── enums/
├── docs/
│   ├── Setup_Instructions.md (this file)
│   ├── JVM_Basics.md
│   └── Design_Notes.md
└── README.md
```

## Common Issues I Ran Into

**"Package does not exist" error**
- Made sure the file was in the right folder
- Package declaration had to match folder structure exactly

**Scanner not reading properly**
- Had to add `scanner.nextLine()` after `scanner.nextInt()` to clear the buffer
- Took me a while to figure this out!

**IDE not recognizing Java**
- Had to configure the Project SDK in IntelliJ
- File → Project Structure → Project → SDK dropdown

## Team Setup

If working in a team:
1. Each person installs Java on their machine
2. Clone/download the project
3. Open in your IDE of choice
4. IDE should detect it's a Java project automatically

## Verification Checklist

Before starting development, make sure:
- [ ] Java installed and verified
- [ ] IDE set up and working
- [ ] Can create new Java files
- [ ] Can run Main.java successfully
- [ ] No compilation errors

## Next Steps

Once setup is done, start building in this order:
1. Utility classes (IdGenerator, InputValidator)
2. Entity classes (Person, Student, Course, Enrollment)
3. Exception classes
4. Repository layer
5. Service layer
6. Finally, the menu system in Main.java

---

*Last updated: [Date when you're setting up]*
*If you run into issues I haven't covered, feel free to add notes here!*
