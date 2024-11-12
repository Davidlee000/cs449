package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class SOSGameGUI extends Application {
    private Button[][] buttons;
    private SOSGame game;
    private VBox root;
    private Label statusLabel;
    private GridPane boardGrid;
    private RadioButton sButton, oButton;
    private boolean redIsComputer, blueIsComputer;
    private ComputerPlayer computerPlayer;

    @Override
    public void start(Stage primaryStage) {
        root = new VBox(10);
        setupGameControls();
        
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SOS Game");
        primaryStage.show();
    }

    private void setupGameControls() {
        // Game mode selection
        ToggleGroup gameModeGroup = new ToggleGroup();
        RadioButton simpleMode = new RadioButton("Simple");
        RadioButton generalMode = new RadioButton("General");
        simpleMode.setToggleGroup(gameModeGroup);
        generalMode.setToggleGroup(gameModeGroup);
        simpleMode.setSelected(true);

        // Player type selection
        ToggleGroup redPlayerGroup = new ToggleGroup();
        RadioButton redHuman = new RadioButton("Red Human");
        RadioButton redComputer = new RadioButton("Red Computer");
        redHuman.setToggleGroup(redPlayerGroup);
        redComputer.setToggleGroup(redPlayerGroup);
        redHuman.setSelected(true);

        ToggleGroup bluePlayerGroup = new ToggleGroup();
        RadioButton blueHuman = new RadioButton("Blue Human");
        RadioButton blueComputer = new RadioButton("Blue Computer");
        blueHuman.setToggleGroup(bluePlayerGroup);
        blueComputer.setToggleGroup(bluePlayerGroup);
        blueHuman.setSelected(true);

        // Board size selection
        ComboBox<Integer> sizeSelector = new ComboBox<>();
        sizeSelector.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
        sizeSelector.setValue(3);

        // New Game button
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> startNewGame(
            gameModeGroup.getSelectedToggle() == generalMode,
            redComputer.isSelected(),
            blueComputer.isSelected(),
            sizeSelector.getValue()
        ));

        statusLabel = new Label("Select game options and click New Game");
        
        root.getChildren().addAll(
            simpleMode, generalMode,
            redHuman, redComputer,
            blueHuman, blueComputer,
            sizeSelector,
            newGameButton,
            statusLabel
        );
    }

    private void startNewGame(boolean isGeneral, boolean redComputer, boolean blueComputer, int size) {
        // Clear previous game if exists
        if (boardGrid != null) {
            root.getChildren().removeAll(boardGrid, sButton, oButton);
        }

        // Initialize game state
        redIsComputer = redComputer;
        blueIsComputer = blueComputer;
        game = isGeneral ? new GeneralGame(size, "General") : new SimpleGame(size, "Simple");
        computerPlayer = new ComputerPlayer(game);

        // Create move controls
        ToggleGroup letterGroup = new ToggleGroup();
        sButton = new RadioButton("S");
        oButton = new RadioButton("O");
        sButton.setToggleGroup(letterGroup);
        oButton.setToggleGroup(letterGroup);
        sButton.setSelected(true);

        createBoard(size);
        root.getChildren().addAll(sButton, oButton, boardGrid);
        updateStatus();

        // Make computer move if Computer player is starting
        if (redIsComputer) {
            makeComputerMove();
        }
    }

    private void createBoard(int size) {
        boardGrid = new GridPane();
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        buttons = new Button[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button button = new Button(" ");
                button.setPrefSize(50, 50);
                int row = i, col = j;
                button.setOnAction(e -> handleMove(row, col));
                boardGrid.add(button, j, i);
                buttons[i][j] = button;
            }
        }
    }
//Handle Human move when button is clicked
    private void handleMove(int row, int col) {

        char letter = sButton.isSelected() ? 'S' : 'O';
        if (game.makeMove(row, col, letter)) {
            buttons[row][col].setText(String.valueOf(letter));
            updateStatus();

            if (!game.isGameOver()) {
                game.switchPlayer();
                if ((game.getcurrentPlayer().equals("Red") && redIsComputer) ||
                    (game.getcurrentPlayer().equals("Blue") && blueIsComputer)) {
                    makeComputerMove();
                }
            }
        }
    }

    private void makeComputerMove() {
        
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            ComputerPlayer.Move move = computerPlayer.getBestMove();
            game.makeMove(move.row(), move.col(), move.letter());
            buttons[move.row()][move.col()].setText(String.valueOf(move.letter()));
            
            updateStatus();
            
            if (!game.isGameOver()) {
                game.switchPlayer();
                if ((game.getcurrentPlayer().equals("Red") && redIsComputer) ||
                    (game.getcurrentPlayer().equals("Blue") && blueIsComputer)) {
                    makeComputerMove();
                }
            }
        });
        pause.play(); 
    }

    private void updateStatus() {
        if (game.isGameOver()) {
            statusLabel.setText("Game Over! " + game.getWinner());
        } else if (game.isFull()) {
            statusLabel.setText("Game Over! It's a Draw!");
        } else {
            String scores = game instanceof GeneralGame ? 
                String.format(" (Red: %d, Blue: %d)", game.getred(), game.getblue()) : "";
            statusLabel.setText("Current Player: " + game.getcurrentPlayer() + scores);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}