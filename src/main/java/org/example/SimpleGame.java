package org.example;

public class SimpleGame extends SOSGame {
    public SimpleGame(int someInt, String someString) {
        super(someInt, someString);
    }
    @Override
    public boolean isGameOver(){
    
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                //horizontal
                if (j + 2 < board.length && board[i][j] == 'S' && board[i][j + 1] == 'O' && board[i][j + 2] == 'S') {
                    return true;
                }
                //vertical
                if (i + 2 < board.length && board[i][j] == 'S' && board[i + 1][j] == 'O' && board[i + 2][j] == 'S') {
                    return true;
                }
                //diagonal
                if (i + 2 < board.length && j + 2 < board.length && board[i][j] == 'S' && board[i + 1][j + 1] == 'O' && board[i + 2][j + 2] == 'S') {
                    return true;
                }
                if (i + 2 < board.length && j - 2 >= 0 && board[i][j] == 'S' && board[i + 1][j - 1] == 'O' && board[i + 2][j - 2] == 'S') {
                    return true;
                }
            }
        }
        return false;
    }
}