package org.shizaadiv;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Question {
  private String questionText;
  private List<Answer> answers;


  @JsonCreator
  public Question(@JsonProperty("questionText") String questionText, @JsonProperty("answers") List<Answer> answers) {
    this.questionText = questionText;
    this.answers = answers;
  }

  public String getQuestionText() {
    return questionText;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public static class Answer {
    private String answerText;
    private boolean isCorrect;

    @JsonCreator
    public Answer(@JsonProperty("answerText") String answerText, @JsonProperty("isCorrect") boolean isCorrect) {
      this.answerText = answerText;
      this.isCorrect = isCorrect;
    }

    public String getAnswerText() {
      return answerText;
    }

    public boolean isCorrect() {
      return isCorrect;
    }
  }
}