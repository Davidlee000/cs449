package org.example;

public class GeneralGame extends SOSGame {

  int red = 0;
  int blue = 0;

  public GeneralGame(int someInt, String someString) {
    super(someInt, someString);
  }

  @Override
  public boolean makeMove(int row, int col, char letter) {

    if (board[row][col] != ' ') {
      return false;
    }

    // Make the move
    board[row][col] = letter;
    boolean scoreEarned = false;

    // Check for SOS formations
    if (letter == 'S') {
      // Check all possible 'S-O-S' patterns starting with this S
      scoreEarned = checkSOSPatterns(row, col);
    } else if (letter == 'O') {
      // Check if this O completes any SOS patterns
      scoreEarned = checkOPatterns(row, col);
    }

    // Update score if SOS was formed
    if (scoreEarned) {
      if (getcurrentPlayer().equals("Red")) {
        red++;
        switchPlayer();
      } else {
        blue++;
        switchPlayer();
      }
    }

    return true;
  }

  private boolean checkSOSPatterns(int row, int col) {
    boolean scored = false;
    int[][] directions = {
        {1, 0},   // down
        {-1, 0},  // up
        {0, 1},   // right
        {0, -1},  // left
        {1, 1},   // diagonal down-right
        {-1, -1}, // diagonal up-left
        {1, -1},  // diagonal down-left
        {-1, 1}   // diagonal up-right
    };

    for (int[] dir : directions) {
      int r1 = row + dir[0];
      int r2 = row + 2 * dir[0];
      int c1 = col + dir[1];
      int c2 = col + 2 * dir[1];

      if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
        if (board[r1][c1] == 'O' && board[r2][c2] == 'S') {
          scored = true;
        }
      }
    }
    return scored;
  }

  private boolean checkOPatterns(int row, int col) {
    boolean scored = false;
    int[][] directions = {
        {1, 0},   // vertical
        {0, 1},   // horizontal
        {1, 1},   // diagonal
        {1, -1}   // other diagonal
    };

    for (int[] dir : directions) {
      int r1 = row - dir[0];
      int r2 = row + dir[0];
      int c1 = col - dir[1];
      int c2 = col + dir[1];

      if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
        if (board[r1][c1] == 'S' && board[r2][c2] == 'S') {
          scored = true;
        }
      }
    }
    return scored;
  }

  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
  }

  @Override
  public String getWinner() {
    if (red > blue) {
      return "Red wins";
    } else if (blue > red) {
      return "Blue wins";
    } else {
      return "it's a Draw";
    }
  }

  @Override
  public int getred() {
    return red;
  }

  @Override
  public int getblue() {
    return blue;
  }
}