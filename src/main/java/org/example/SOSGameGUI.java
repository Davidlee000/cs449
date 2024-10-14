package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SOSGameGUI extends Application {
  private Button[][] buttons;
  private SOSGame game;
  private VBox root;
  private Label current;

  @Override
  public void start(Stage primaryStage) {
    root = new VBox(10);
    Scene myScene = new Scene(root);

    // choose game mode
    ToggleGroup gamemode = new ToggleGroup();
    RadioButton simple = new RadioButton("Simple");
    RadioButton general = new RadioButton("General");
    simple.setToggleGroup(gamemode);
    general.setToggleGroup(gamemode);
    simple.setSelected(true);

    // Set user data for each radio button
    simple.setUserData("Simple");
    general.setUserData("General");

    //Choose Board Size
    ComboBox<Integer> boardSize = new ComboBox<>();
    boardSize.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
    boardSize.setValue(3);

    //Start Button
    Button play = new Button("Start Game");
    play.setOnAction(e -> {
      game = new SOSGame(boardSize.getValue(), gamemode.getSelectedToggle().getUserData().toString());
      createBoard(boardSize.getValue());
    });

    //Add Components
    root.getChildren().addAll(simple, general, boardSize, play);
    primaryStage.setScene(myScene);
    primaryStage.setTitle("SOS GAME");
    primaryStage.setWidth(600);
    primaryStage.setHeight(600);
    primaryStage.show();
  }

  //create board
  public void createBoard(int size) {
    GridPane boardGrid = new GridPane();
    boardGrid.setHgap(5);
    boardGrid.setVgap(5);

    //add radio buttons for s or o
    ToggleGroup letter = new ToggleGroup();
    RadioButton s = new RadioButton("S");
    RadioButton o = new RadioButton("O");
    s.setToggleGroup(letter);
    o.setToggleGroup(letter);
    s.setSelected(true);
    s.setUserData("S");
    o.setUserData("O");

    current = new Label();
    current.setText("Current Player: " + game.getcurrentPlayer());

    buttons = new Button[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Button button = new Button(" ");
        button.setPrefSize(40, 40);
        int finalI = i;
        int finalJ = j;
        button.setOnAction(e -> handleMove(finalI, finalJ, letter.getSelectedToggle().getUserData().toString().charAt(0)));
        boardGrid.add(button, j, i);
        buttons[i][j] = button;
      }
    }

    root.getChildren().addAll(s, o, current, boardGrid);

  }


  public void handleMove(int row, int col, char letter) {
    if (game.makeMove(row, col, letter)) {
      buttons[row][col].setText(String.valueOf(letter));
      game.switchPlayer();
      current.setText("Current Player: " + game.getcurrentPlayer());

    }

  }

  public static void main(String[] args) {
    launch(args);
  }
}

