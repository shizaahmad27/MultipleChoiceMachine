package org.shizaadiv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    List<Question> questions = loadQuestionsFromFile();

    if (questions == null) {
      System.out.println("Failed to load questions.");
      return;
    }

    // Shuffle questions randomly
    Collections.shuffle(questions);
    int counter = 0;
    int correctCount = 0;
    int incorrectCount = 0;

    Scanner scanner = new Scanner(System.in);
    for (Question question : questions) {
      counter++;
      System.out.println(counter + ". " + question.getQuestionText());
      List<Question.Answer> answers = question.getAnswers();
      for (Question.Answer value : answers) {
        System.out.println(value.getAnswerText());
      }

      System.out.print("Enter your answer (A, B, C, D): ");
      String userAnswer = scanner.nextLine().toUpperCase();

      boolean correct = answers.stream()
              .anyMatch(answer -> answer.getAnswerText().startsWith(userAnswer + ")") && answer.isCorrect());
      String correctAnswer = answers.stream()
              .filter(Question.Answer::isCorrect)
              .findFirst()
              .map(Question.Answer::getAnswerText)
              .orElse("Unknown");
      if (correct) {
        System.out.println("Correct!\n");
        correctCount++;
      } else {
        System.out.println("Incorrect.\n");
        System.out.println("The correct answer is: " + correctAnswer + "\n");
        incorrectCount++;
      }
    }
    scanner.close();

    // Display summary
    System.out.println("Quiz Summary:");
    System.out.println("Correct answers: " + correctCount);
    System.out.println("Incorrect answers: " + incorrectCount);
  }

  private static List<Question> loadQuestionsFromFile() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      // Load the file from resources using class loader
      InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("alleOS_labeled.json");

      if (inputStream == null) {
        System.out.println("Resource not found.");
        return null;
      }

      return mapper.readValue(inputStream, new TypeReference<>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
