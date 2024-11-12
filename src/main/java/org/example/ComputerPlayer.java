package org.example;

public class ComputerPlayer {

  private final SOSGame game;

  public ComputerPlayer(SOSGame game) {
    this.game = game;
  }

  public Move getBestMove() {
    // First try to complete an SOS
    Move winningMove = findWinningMove();
    if (winningMove != null) {
      return winningMove;
    }
    return makeRandomMove();
  }

  private Move findWinningMove() {
    char[][] board = game.getBoard();
    int size = game.getSize();

    // Look for potential SOS completions
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
          if (board[row][col] != ' ') {
              continue;
          }

        // Try 'S' first
        if (wouldCompleteSOSAsS(row, col)) {
          return new Move(row, col, 'S');
        }

        // Try 'O'
        if (wouldCompleteSOSAsO(row, col)) {
          return new Move(row, col, 'O');
        }
      }
    }
    return null;
  }

  private boolean wouldCompleteSOSAsS(int row, int col) {
    int[][] directions = {
        {1, 0}, {-1, 0},   // vertical
        {0, 1}, {0, -1},   // horizontal
        {1, 1}, {-1, -1},  // diagonal
        {1, -1}, {-1, 1}   // other diagonal
    };

    char[][] board = game.getBoard();
    int size = game.getSize();

    for (int[] dir : directions) {
      int r1 = row + dir[0];
      int r2 = row + 2 * dir[0];
      int c1 = col + dir[1];
      int c2 = col + 2 * dir[1];

      if (isValidPosition(r1, c1, size) && isValidPosition(r2, c2, size)) {
        if (board[r1][c1] == 'O' && board[r2][c2] == 'S') {
          return true;
        }
      }
    }
    return false;
  }

  private boolean wouldCompleteSOSAsO(int row, int col) {
    int[][] directions = {
        {1, 0}, {0, 1}, {1, 1}, {1, -1}  // Check all directions
    };

    char[][] board = game.getBoard();
    int size = game.getSize();

    for (int[] dir : directions) {
      int r1 = row - dir[0];
      int r2 = row + dir[0];
      int c1 = col - dir[1];
      int c2 = col + dir[1];

      if (isValidPosition(r1, c1, size) && isValidPosition(r2, c2, size)) {
        if (board[r1][c1] == 'S' && board[r2][c2] == 'S') {
          return true;
        }
      }
    }
    return false;
  }

  private Move makeRandomMove() {
    int size = game.getSize();
    char[][] board = game.getBoard();

    while (true) {
      int row = (int) (Math.random() * size);
      int col = (int) (Math.random() * size);
      if (board[row][col] == ' ') {
        return new Move(row, col, Math.random() < 0.5 ? 'S' : 'O');
      }
    }
  }

  private boolean isValidPosition(int row, int col, int size) {
    return row >= 0 && row < size && col >= 0 && col < size;
  }

  public record Move(int row, int col, char letter) {

  }
} 