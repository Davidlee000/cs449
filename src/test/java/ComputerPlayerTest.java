
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;
import org.example.ComputerPlayer;
import org.example.SOSGame;

class ComputerPlayerTest {
  private SOSGame game;
  private ComputerPlayer computerPlayer;

  @BeforeEach
  void setUp() {
    game = new SOSGame(3, "Simple");
    computerPlayer = new ComputerPlayer(game);
  }

  @Test
  void testHorizontalSOSCompletionWithS() {
    game.makeMove(0, 1, 'O');
    game.makeMove(0, 2, 'S');

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should find a winning move");
    assertEquals(0, move.row(), "Should choose row 0");
    assertEquals(0, move.col(), "Should choose column 0");
    assertEquals('S', move.letter(), "Should place 'S' to complete SOS");
  }

  @Test
  void testVerticalSOSCompletionWithS() {
    game.makeMove(1, 0, 'O');
    game.makeMove(2, 0, 'S');

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should find a winning move");
    assertEquals(0, move.row(), "Should choose row 0");
    assertEquals(0, move.col(), "Should choose column 0");
    assertEquals('S', move.letter(), "Should place 'S' to complete SOS");
  }

  @Test
  void testDiagonalSOSCompletionWithS() {
    game.makeMove(1, 1, 'O');
    game.makeMove(2, 2, 'S');

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should find a winning move");
    assertEquals(0, move.row(), "Should choose row 0");
    assertEquals(0, move.col(), "Should choose column 0");
    assertEquals('S', move.letter(), "Should place 'S' to complete SOS");
  }

  @Test
  void testSOSCompletionWithO() {
    game.makeMove(0, 0, 'S');
    game.makeMove(0, 2, 'S');

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should find a winning move");
    assertEquals(0, move.row(), "Should choose row 0");
    assertEquals(1, move.col(), "Should choose column 1");
    assertEquals('O', move.letter(), "Should place 'O' to complete SOS");
  }

  @Test
  void testNoWinningMove() {
    game.makeMove(0, 0, 'S');
    game.makeMove(0, 2, 'O');

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should return a random move");
    assertTrue(isValidMove(move), "Random move should be valid");
  }

  @RepeatedTest(10)
  void testRandomMoveGeneration() {
    // Test that random moves are generated when no winning move is available
    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should generate a random move");
    assertTrue(isValidMove(move), "Random move should be valid");
    assertTrue(move.letter() == 'S' || move.letter() == 'O',
        "Move should use either 'S' or 'O'");
  }

  @Test
  void testFullBoard() {
    // Fill the board entirely except for one space
    for (int i = 0; i < game.getSize(); i++) {
      for (int j = 0; j < game.getSize(); j++) {
        if (i != 2 || j != 2) {  // Leave last space empty
          game.makeMove(i, j, 'S');
        }
      }
    }

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should find the last available move");
    assertEquals(2, move.row(), "Should choose last row");
    assertEquals(2, move.col(), "Should choose last column");
    assertTrue(move.letter() == 'S' || move.letter() == 'O',
        "Should place either 'S' or 'O'");
  }

  @Test
  void testPreferWinningOverRandom() {
    game.makeMove(0, 0, 'S');
    game.makeMove(0, 2, 'S');

    ComputerPlayer.Move move = computerPlayer.getBestMove();
    assertNotNull(move, "Should find a move");
    assertEquals(0, move.row(), "Should choose winning row");
    assertEquals(1, move.col(), "Should choose winning column");
    assertEquals('O', move.letter(), "Should choose 'O' to complete SOS");
  }

  private boolean isValidMove(ComputerPlayer.Move move) {
    return move.row() >= 0 && move.row() < game.getSize() &&
        move.col() >= 0 && move.col() < game.getSize() &&
        (move.letter() == 'S' || move.letter() == 'O');
  }
}