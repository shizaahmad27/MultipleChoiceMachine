# Multiple Choice Machine (GUI)

A lightweight, modern JavaFX GUI for practicing multiple-choice questions loaded from JSON. Clean styling with a darker grey background, progress bar, keyboard shortcuts, and a summary screen with statistics.

## Features
- Modern JavaFX UI
- Shuffled questions from bundled JSON files (default: `alleOS_labeled.json`)
- Progress indicator and bar
- Keyboard shortcuts: A, B, C, D to select; Enter to submit
- Immediate feedback with correct/incorrect highlighting
- Summary with pie chart, score, time, and review of incorrect answers
- Restart and Close actions

## Requirements
- Java 21+
- Maven 3.8+

Check your Java version:
```bash
java -version
```

## Run the App
From the project root:
```bash
mvn javafx:run
```
Note (macOS): You might see a log warning about app reactivation; the window still opens. If it doesn’t pop front, click it in the Dock.

## Build
Compile without running tests:
```bash
mvn -DskipTests package
```

## Data Files
Question sets live in `src/main/resources/` as JSON (e.g., `alleOS_labeled.json`, `kompendium.json`). The app currently loads `alleOS_labeled.json` by default. To switch datasets, change the resource name in `org/shizaadiv/QuizApp.java` inside `loadQuestions()`.

JSON format example (array of objects):
```json
[
  {
    "questionText": "What is 2 + 2?",
    "answers": [
      { "answerText": "A) 3", "isCorrect": false },
      { "answerText": "B) 4", "isCorrect": true },
      { "answerText": "C) 5", "isCorrect": false },
      { "answerText": "D) 22", "isCorrect": false }
    ]
  }
]
```

## Keyboard & UX
- Select options: `A`, `B`, `C`, `D`
- Submit: `Enter`
- Visual feedback: green (correct), red (incorrect)

## Packaging Options (optional)
- Executable JAR:
```bash
mvn -DskipTests package
```
Jar will be in `target/`. Because JavaFX modules are needed at runtime, prefer `javafx:run` during development or use jlink/jpackage for distribution.

- Native bundle with `jpackage` (requires JDK with jpackage):
```bash
jpackage --name "Multiple Choice Machine" \
  --input target \
  --main-jar MultipleChoiceMachine-1.0-SNAPSHOT.jar \
  --type app-image
```
Adjust paths and JavaFX modules for your platform.

## Troubleshooting
- If the window doesn’t appear on macOS, use Mission Control or click the app in the Dock.
- Ensure Java 21 is selected. With multiple JDKs, set `JAVA_HOME` accordingly.

## License
Personal/educational use. Adapt as needed for your portfolio.
