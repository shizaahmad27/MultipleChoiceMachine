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

git ## Quick Start

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

## Deployment

### Web Version to Vercel
1. **Install Vercel CLI**: `npm i -g vercel`
2. **Deploy**: From `web/` folder, run `vercel`
3. **Or connect repo** in Vercel dashboard:
   - Framework preset: Next.js
   - Build command: `npm run build`
   - Output directory: `.next`
4. **Datasets**: JSON files in `web/public/` are served as `/<file>.json`

### Desktop Version Distribution
```bash
# Build JAR
mvn -DskipTests package

# Create native bundle (requires JDK with jpackage)
jpackage --name "Multiple Choice Machine" \
  --input target \
  --main-jar MultipleChoiceMachine-1.0-SNAPSHOT.jar \
  --type app-image
```

## Data Files
Question sets are stored as JSON files:
- **Desktop**: `src/main/resources/*.json`
- **Web**: `web/public/*.json`

**Available datasets**: alleOS_labeled, alleOS, kompendium, bærekraft, oldies_generell, questions_1&2, questions_3, questions_4&5

**JSON format** (array of question objects):
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

## Portfolio Value
This project demonstrates:
- **Full-stack development**: Desktop (JavaFX) + Web (Next.js)
- **Cross-platform deployment**: Native apps + web hosting
- **Modern UI/UX**: Responsive design, progress tracking, statistics
- **Data handling**: JSON parsing, state management
- **Build tools**: Maven, npm, Vercel deployment

## Troubleshooting
- **Desktop**: If window doesn't appear on macOS, use Mission Control or click app in Dock
- **Web**: Ensure Node.js 18+ is installed
- **Java**: Ensure Java 21 is selected; set `JAVA_HOME` if multiple JDKs installed

## License
Personal/educational use. Adapt as needed for your portfolio.
