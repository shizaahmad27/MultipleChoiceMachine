package org.shizaadiv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizApp extends Application {
  private List<Question> questions = new ArrayList<>();
  private int currentIndex = 0;
  private int correctCount = 0;
  private int incorrectCount = 0;

  private Label questionLabel;
  private RadioButton optionA;
  private RadioButton optionB;
  private RadioButton optionC;
  private RadioButton optionD;
  private Label progressLabel;
  private ProgressBar progressBar;
  private Label feedbackLabel;
  private final List<Boolean> results = new ArrayList<>();
  private long quizStartMs;

  @Override
  public void start(Stage primaryStage) {
    loadQuestions();
    if (questions.isEmpty()) {
      Label error = new Label("Failed to load questions.");
      error.setTextFill(Color.web("#b00020"));
      error.setPadding(new Insets(24));
      BorderPane errorRoot = new BorderPane(error);
      errorRoot.setStyle("-fx-background-color: #e0e0e0;");
      primaryStage.setScene(new Scene(errorRoot, 720, 480, Color.web("#e0e0e0")));
      primaryStage.setTitle("Multiple Choice Machine");
      primaryStage.show();
      return;
    }

    Collections.shuffle(questions);
    quizStartMs = System.currentTimeMillis();

    BorderPane root = new BorderPane();
    root.setPadding(new Insets(24));
    root.setStyle("-fx-background-color: #e0e0e0;");

    // Top bar
    Label title = new Label("Multiple Choice Machine");
    title.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
    title.setTextFill(Color.web("#1d1d1f"));
    progressLabel = new Label();
    progressLabel.setTextFill(Color.web("#6e6e73"));
    // Progress bar
    progressBar = new ProgressBar(0);
    progressBar.setPrefWidth(240);
    progressBar.setStyle("-fx-accent: #2f6fed;");
    HBox topBar = new HBox(12, title, progressLabel, progressBar);
    topBar.setAlignment(Pos.CENTER_LEFT);
    root.setTop(topBar);

    // Center content
    questionLabel = new Label();
    questionLabel.setWrapText(true);
    questionLabel.setFont(Font.font("Inter", FontWeight.MEDIUM, 18));
    questionLabel.setTextFill(Color.web("#111111"));

    ToggleGroup group = new ToggleGroup();
    optionA = styledRadioButton(group);
    optionB = styledRadioButton(group);
    optionC = styledRadioButton(group);
    optionD = styledRadioButton(group);

    VBox center = new VBox(16, questionLabel, optionA, optionB, optionC, optionD);
    center.setPadding(new Insets(16, 0, 16, 0));
    ScrollPane scroll = new ScrollPane(center);
    scroll.setFitToWidth(true);
    scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
    root.setCenter(scroll);

    // Bottom bar
    Button submitBtn = primaryButton("Submit");
    Button nextBtn = secondaryButton("Next");
    nextBtn.setDisable(true);

    feedbackLabel = new Label("");
    feedbackLabel.setTextFill(Color.web("#0a7b34"));
    feedbackLabel.setWrapText(true);

    submitBtn.setOnAction(e -> handleSubmit(group, submitBtn, nextBtn));
    nextBtn.setOnAction(e -> {
      currentIndex++;
      if (currentIndex >= questions.size()) {
        showSummary(primaryStage);
      } else {
        group.selectToggle(null);
        feedbackLabel.setText("");
        resetOptionStyles();
        setQuestion(currentIndex);
        submitBtn.setDisable(false);
        nextBtn.setDisable(true);
      }
    });

    HBox actions = new HBox(12, submitBtn, nextBtn);
    actions.setAlignment(Pos.CENTER_LEFT);

    VBox bottom = new VBox(8, feedbackLabel, actions);
    root.setBottom(bottom);

    setQuestion(currentIndex);

    Scene scene = new Scene(root, 900, 620, Color.web("#e0e0e0"));
    // Keyboard shortcuts
    scene.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case A -> optionA.setSelected(true);
        case B -> optionB.setSelected(true);
        case C -> optionC.setSelected(true);
        case D -> optionD.setSelected(true);
        case ENTER -> submitBtn.fire();
        default -> {}
      }
    });
    primaryStage.setTitle("Multiple Choice Machine");
    primaryStage.setScene(scene);
    primaryStage.centerOnScreen();
    primaryStage.show();
  }

  private RadioButton styledRadioButton(ToggleGroup group) {
    RadioButton rb = new RadioButton();
    rb.setToggleGroup(group);
    rb.setWrapText(true);
    rb.setTextFill(Color.web("#1d1d1f"));
    rb.setFont(Font.font("Inter", 15));
    return rb;
  }

  private Button primaryButton(String text) {
    Button b = new Button(text);
    b.setStyle("-fx-background-color: #2f6fed; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 16; -fx-font-size: 14; -fx-font-weight: 600;");
    return b;
  }

  private Button secondaryButton(String text) {
    Button b = new Button(text);
    b.setStyle("-fx-background-color: #e9eefc; -fx-text-fill: #1d3aa9; -fx-background-radius: 8; -fx-padding: 10 16; -fx-font-size: 14; -fx-font-weight: 600;");
    return b;
  }

  private void setQuestion(int index) {
    Question q = questions.get(index);
    questionLabel.setText((index + 1) + ". " + q.getQuestionText());
    List<Question.Answer> answers = q.getAnswers();
    // Defensive in case there are fewer than 4 options
    optionA.setText(answers.size() > 0 ? answers.get(0).getAnswerText() : "A) ");
    optionB.setText(answers.size() > 1 ? answers.get(1).getAnswerText() : "B) ");
    optionC.setText(answers.size() > 2 ? answers.get(2).getAnswerText() : "C) ");
    optionD.setText(answers.size() > 3 ? answers.get(3).getAnswerText() : "D) ");
    progressLabel.setText("  •  " + (index + 1) + "/" + questions.size());
    progressBar.setProgress((double) index / (double) questions.size());
  }

  private void handleSubmit(ToggleGroup group, Button submitBtn, Button nextBtn) {
    RadioButton selected = (RadioButton) group.getSelectedToggle();
    if (selected == null) {
      feedbackLabel.setTextFill(Color.web("#b00020"));
      feedbackLabel.setText("Please select an option.");
      return;
    }

    Question q = questions.get(currentIndex);
    List<Question.Answer> answers = q.getAnswers();

    String selectedText = selected.getText();
    boolean isCorrect = answers.stream().anyMatch(a -> a.isCorrect() && a.getAnswerText().equals(selectedText));

    String correctAnswerText = answers.stream().filter(Question.Answer::isCorrect).findFirst().map(Question.Answer::getAnswerText).orElse("Unknown");

    if (isCorrect) {
      correctCount++;
      feedbackLabel.setTextFill(Color.web("#0a7b34"));
      feedbackLabel.setText("Correct!");
      styleAsCorrect(selected);
    } else {
      incorrectCount++;
      feedbackLabel.setTextFill(Color.web("#b00020"));
      feedbackLabel.setText("Incorrect. Correct answer: " + correctAnswerText);
      styleAsIncorrect(selected);
      // highlight correct one
      if (optionA.getText().equals(correctAnswerText)) styleAsCorrect(optionA);
      if (optionB.getText().equals(correctAnswerText)) styleAsCorrect(optionB);
      if (optionC.getText().equals(correctAnswerText)) styleAsCorrect(optionC);
      if (optionD.getText().equals(correctAnswerText)) styleAsCorrect(optionD);
    }

    results.add(isCorrect);
    submitBtn.setDisable(true);
    nextBtn.setDisable(false);
  }

  private void showSummary(Stage stage) {
    long elapsedMs = System.currentTimeMillis() - quizStartMs;
    double percent = questions.isEmpty() ? 0 : (100.0 * correctCount / questions.size());

    Label title = new Label("Quiz Summary");
    title.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 26));
    title.setTextFill(Color.web("#1d1d1f"));

    PieChart chart = new PieChart();
    chart.getData().add(new PieChart.Data("Correct", correctCount));
    chart.getData().add(new PieChart.Data("Incorrect", incorrectCount));
    chart.setLabelsVisible(true);
    chart.setLegendVisible(true);

    Label score = new Label(String.format("Score: %.1f%% (%d/%d)", percent, correctCount, questions.size()));
    score.setFont(Font.font("Inter", 18));
    Label time = new Label(String.format("Time: %.1f sec", elapsedMs / 1000.0));
    time.setFont(Font.font("Inter", 14));

    // Incorrect review list
    VBox reviewList = new VBox(8);
    reviewList.setPadding(new Insets(8));
    for (int i = 0; i < results.size(); i++) {
      if (!results.get(i)) {
        Question q = questions.get(i);
        Label ql = new Label((i + 1) + ". " + q.getQuestionText());
        ql.setWrapText(true);
        ql.setFont(Font.font("Inter", FontWeight.MEDIUM, 14));
        String correctAnswerText = q.getAnswers().stream().filter(Question.Answer::isCorrect).findFirst().map(Question.Answer::getAnswerText).orElse("Unknown");
        Label al = new Label("Correct: " + correctAnswerText);
        al.setTextFill(Color.web("#0a7b34"));
        reviewList.getChildren().add(new VBox(4, ql, al));
      }
    }
    ScrollPane reviewScroll = new ScrollPane(reviewList);
    reviewScroll.setFitToWidth(true);
    reviewScroll.setPrefViewportHeight(220);

    Button restart = secondaryButton("Restart");
    restart.setOnAction(e -> {
      currentIndex = 0;
      correctCount = 0;
      incorrectCount = 0;
      results.clear();
      quizStartMs = System.currentTimeMillis();
      Collections.shuffle(questions);
      start(stage);
    });

    Button exit = primaryButton("Close");
    exit.setOnAction(e -> stage.close());

    HBox actions = new HBox(12, restart, exit);
    actions.setAlignment(Pos.CENTER);

    VBox box = new VBox(16, title, chart, score, time, new Label("Review incorrect answers"), reviewScroll, actions);
    box.setAlignment(Pos.CENTER);
    box.setPadding(new Insets(24));

    box.setStyle("-fx-background-color: #e0e0e0;");
    Scene scene = new Scene(box, 900, 620, Color.web("#e0e0e0"));
    stage.setScene(scene);
  }

  private void loadQuestions() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream inputStream = QuizApp.class.getClassLoader().getResourceAsStream("alleOS_labeled.json")) {
      if (inputStream == null) {
        questions = new ArrayList<>();
        return;
      }
      questions = mapper.readValue(inputStream, new TypeReference<>() {});
    } catch (IOException e) {
      questions = new ArrayList<>();
    }
  }

  private void styleAsCorrect(RadioButton rb) {
    rb.setStyle("-fx-background-color: #e6f4ea; -fx-background-radius: 6; -fx-padding: 6; -fx-text-fill: #0a7b34;");
  }

  private void styleAsIncorrect(RadioButton rb) {
    rb.setStyle("-fx-background-color: #fdecea; -fx-background-radius: 6; -fx-padding: 6; -fx-text-fill: #b00020;");
  }

  private void resetOptionStyles() {
    String base = "-fx-background-color: transparent; -fx-text-fill: #1d1d1f;";
    optionA.setStyle(base);
    optionB.setStyle(base);
    optionC.setStyle(base);
    optionD.setStyle(base);
  }

  public static void main(String[] args) {
    launch(args);
  }
}


