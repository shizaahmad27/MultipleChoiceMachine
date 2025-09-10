# Multiple Choice Machine

A multiple-choice quiz application with **two implementations** showcasing different technologies:

## 🖥️ Desktop Version (JavaFX)
- **Location**: `src/main/java/` 
- **Tech Stack**: Java 21, JavaFX, Maven, Jackson JSON
- **Features**: Modern GUI with darker grey background, progress bar, keyboard shortcuts (A-D, Enter), immediate feedback, summary with pie chart and statistics
- **Best for**: Offline use, desktop applications, Java portfolio pieces

## 🌐 Web Version (Next.js)
- **Location**: `web/`
- **Tech Stack**: Next.js 15, TypeScript, Tailwind CSS
- **Features**: Responsive web UI, dataset selector, progress tracking, statistics summary, deployable to Vercel
- **Best for**: Online sharing, web portfolios, easy deployment

## Shared Features
- Shuffled questions from JSON datasets
- Progress tracking and statistics
- Review of incorrect answers
- Multiple question sets (alleOS, kompendium, bærekraft, etc.)

## Quick Start

### Desktop Version (JavaFX)
**Requirements**: Java 21+, Maven 3.8+

```bash
# Check Java version
java -version

# Run desktop app
mvn javafx:run
```
*Note (macOS): You might see a log warning about app reactivation; the window still opens.*

### Web Version (Next.js)
**Requirements**: Node.js 18+

```bash
# Navigate to web folder
cd web

# Install dependencies (first time only)
npm install

# Run development server
npm run dev
```
*Open http://localhost:3000 in your browser*

## Web Version (Next.js for Vercel)
A deployable web version is in `web/` (Next.js + TypeScript). It loads the same JSON datasets from `public/`.

- Dev:
```bash
cd web
npm run dev
```

- Build:
```bash
cd web
npm run build
npm start
```

- Deploy to Vercel:
  1) Install the Vercel CLI: `npm i -g vercel`
  2) From `web/`: `vercel` (or connect the repo in the Vercel dashboard)
  3) Framework preset: Next.js; Build command: `npm run build`; Output: `.next`
  4) Datasets in `web/public/*.json` will be served as `/<file>.json`

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
