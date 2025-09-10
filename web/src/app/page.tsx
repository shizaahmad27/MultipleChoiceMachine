"use client";

import { useEffect, useMemo, useState } from "react";

type Answer = { answerText: string; isCorrect: boolean };
type Question = { questionText: string; answers: Answer[] };

function shuffle<T>(input: T[]): T[] {
  const array = input.slice();
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
}

export default function Home() {
  const [questions, setQuestions] = useState<Question[] | null>(null);
  const [idx, setIdx] = useState(0);
  const [selected, setSelected] = useState<string | null>(null);
  const [correctCount, setCorrectCount] = useState(0);
  const [incorrectCount, setIncorrectCount] = useState(0);
  const [results, setResults] = useState<boolean[]>([]);
  const [startedAt, setStartedAt] = useState<number>(() => Date.now());
  const [submitted, setSubmitted] = useState(false);
  const [feedback, setFeedback] = useState<string>("");
  const datasets = useMemo(
    () => [
      { file: "/alleOS_labeled.json", label: "alleOS_labeled" },
      { file: "/alleOS.json", label: "alleOS" },
      { file: "/kompendium.json", label: "kompendium" },
      { file: "/bærekraft.json", label: "bærekraft" },
      { file: "/oldies_generell.json", label: "oldies_generell" },
      { file: "/questions_1&2.json", label: "questions_1&2" },
      { file: "/questions_3.json", label: "questions_3" },
      { file: "/questions_4&5.json", label: "questions_4&5" },
    ],
    []
  );
  const [dataset, setDataset] = useState<string>(datasets[0]?.file ?? "/alleOS_labeled.json");

  useEffect(() => {
    async function load() {
      const res = await fetch(dataset, { cache: "no-store" });
      const data: Question[] = await res.json();
      setQuestions(shuffle(data));
      setStartedAt(Date.now());
      setIdx(0);
      setSelected(null);
      setCorrectCount(0);
      setIncorrectCount(0);
      setResults([]);
      setSubmitted(false);
      setFeedback("");
    }
    load();
  }, [dataset]);

  const total = questions?.length ?? 0;
  const question = questions?.[idx] ?? null;
  const progress = useMemo(() => (total ? idx / total : 0), [idx, total]);

  function submit() {
    if (!question || !selected || submitted) return;
    const isCorrect = question.answers.some(
      (a) => a.isCorrect && a.answerText === selected
    );
    setResults((r) => [...r, isCorrect]);
    if (isCorrect) {
      setCorrectCount((c) => c + 1);
      setFeedback("Correct!");
    } else {
      setIncorrectCount((c) => c + 1);
      const correctAnswer = question.answers.find((a) => a.isCorrect)?.answerText;
      setFeedback(`Incorrect. Correct answer: ${correctAnswer}`);
    }
    setSubmitted(true);
  }

  function next() {
    setSelected(null);
    setSubmitted(false);
    setFeedback("");
    setIdx((i) => i + 1);
  }

  function restart() {
    if (!questions) return;
    setIdx(0);
    setSelected(null);
    setCorrectCount(0);
    setIncorrectCount(0);
    setResults([]);
    setSubmitted(false);
    setFeedback("");
    setQuestions(shuffle(questions));
    setStartedAt(Date.now());
  }

  if (questions === null) {
    return (
      <div className="min-h-screen bg-neutral-100 text-neutral-900 flex items-center justify-center p-6">
        Loading questions...
      </div>
    );
  }

  const finished = idx >= total;

  if (finished) {
    const elapsedSec = (Date.now() - startedAt) / 1000;
    const percent = total ? (100 * correctCount) / total : 0;

    return (
      <div className="min-h-screen bg-neutral-100 text-neutral-900 p-6 flex items-center justify-center">
        <div className="w-full max-w-3xl bg-white rounded-xl shadow-sm p-6">
          <h1 className="text-2xl font-semibold mb-4">Quiz Summary</h1>
          <p className="text-lg mb-1">Score: {percent.toFixed(1)}% ({correctCount}/{total})</p>
          <p className="text-sm text-neutral-600 mb-6">Time: {elapsedSec.toFixed(1)} sec</p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="bg-neutral-50 rounded-lg p-4 border">
              <p className="text-sm text-neutral-600 mb-2">Overview</p>
              <div className="flex items-center gap-4">
                <div className="w-24 h-24 rounded-full bg-gradient-to-br from-green-400 to-green-600 text-white flex items-center justify-center text-xl font-bold">
                  {Math.round(percent)}%
                </div>
                <div className="text-sm">
                  <div>Correct: <span className="text-green-700 font-medium">{correctCount}</span></div>
                  <div>Incorrect: <span className="text-red-700 font-medium">{incorrectCount}</span></div>
                  <div>Total: {total}</div>
                </div>
              </div>
            </div>
            <div className="bg-neutral-50 rounded-lg p-4 border max-h-64 overflow-auto">
              <p className="text-sm text-neutral-600 mb-2">Review incorrect answers</p>
              <div className="space-y-3">
                {results.map((ok, i) => {
                  if (ok) return null;
                  const q = questions[i];
                  const correct = q.answers.find((a) => a.isCorrect)?.answerText;
                  return (
                    <div key={i} className="text-sm">
                      <div className="font-medium">{i + 1}. {q.questionText}</div>
                      <div className="text-green-700">Correct: {correct}</div>
                    </div>
                  );
                })}
                {results.every(Boolean) && (
                  <div className="text-sm text-neutral-600">No incorrect answers 🎉</div>
                )}
              </div>
            </div>
          </div>

          <div className="mt-6 flex gap-3">
            <button onClick={restart} className="px-4 py-2 rounded-lg bg-neutral-200 hover:bg-neutral-300 text-neutral-900 font-medium">Restart</button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-neutral-100 text-neutral-900 p-6 flex items-center justify-center">
      <div className="w-full max-w-3xl bg-white rounded-xl shadow-sm p-6">
        <div className="flex items-center gap-3 mb-6">
          <h1 className="text-xl font-semibold">Multiple Choice Machine</h1>
          <div className="text-neutral-500">• {idx + 1}/{total}</div>
          <div className="flex-1" />
          <select
            className="border rounded-lg px-3 py-2 text-sm bg-white"
            value={dataset}
            onChange={(e) => setDataset(e.target.value)}
            title="Choose dataset"
          >
            {datasets.map((d) => (
              <option key={d.file} value={d.file}>{d.label}</option>
            ))}
          </select>
          <div className="w-48 h-2 bg-neutral-200 rounded-full overflow-hidden">
            <div className="h-full bg-blue-600" style={{ width: `${progress * 100}%` }} />
          </div>
        </div>

        <div className="mb-4 text-lg font-medium">{idx + 1}. {question?.questionText}</div>

        <div className="space-y-2">
          {question?.answers.map((a) => (
            <label key={a.answerText} className="flex items-start gap-3 p-3 rounded-lg border hover:bg-neutral-50 cursor-pointer">
              <input
                type="radio"
                name="answer"
                className="mt-1"
                checked={selected === a.answerText}
                onChange={() => setSelected(a.answerText)}
              />
              <span>{a.answerText}</span>
            </label>
          ))}
        </div>

        {feedback && (
          <div className={`mt-4 p-3 rounded-lg ${feedback.startsWith("Correct") ? "bg-green-50 text-green-800" : "bg-red-50 text-red-800"}`}>
            {feedback}
          </div>
        )}
        
        <div className="mt-6 flex gap-3">
          <button 
            onClick={submit} 
            disabled={!selected || submitted} 
            className="px-4 py-2 rounded-lg bg-blue-600 disabled:bg-blue-300 text-white font-medium"
          >
            Submit
          </button>
          <button 
            onClick={next} 
            disabled={!submitted}
            className="px-4 py-2 rounded-lg bg-neutral-200 disabled:bg-neutral-100 text-neutral-900 font-medium"
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}
