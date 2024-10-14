package org.example;

import java.util.Arrays;

public class SOSGame {
  private final int size;
  private final String mode;
  private final char[][] board;
  private String currentPlayer;

  //create a new game initialize game mode and size
  //create an empty board
  public SOSGame(int size,String mode) {
    this.size = size;
    this.mode = mode;
    this.board = new char[size][size];
    for (char[] row : board) {
      Arrays.fill(row,' ');
    }
    this.currentPlayer = "Red";
  }

  //check if space is empty and makes a move
  public boolean makeMove(int row, int col, char letter) {
    if (board[row][col] != ' ') {
      return false;
    }
    board[row][col] = letter;
    return true;
  }

  //switch to the other player
  public void switchPlayer() {
    currentPlayer = (currentPlayer.equals("Red")) ? "Blue" : "Red";
  }

  public String getcurrentPlayer() {
    return currentPlayer;
  }
  public char[][] getBoard() {
    return board;
  }

  public String getMode() {
    return mode;
  }

  public int getSize() {
    return size;
  }

}