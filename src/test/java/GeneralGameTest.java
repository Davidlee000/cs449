import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.example.GeneralGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneralGameTest {

  private GeneralGame game;

  @BeforeEach
  void setUp() {
    game = new GeneralGame(3, "General");
  }

  @Test
  void testMakeMove() {
    assertTrue(game.makeMove(0, 0, 'S'));
    assertFalse(game.makeMove(0, 0, 'S'));
  }

  @Test
  void testIsGameOver() {
    assertFalse(game.isGameOver());
    assertTrue(game.makeMove(0, 0, 'S'));
    assertTrue(game.makeMove(0, 1, 'O'));
    assertTrue(game.makeMove(0, 2, 'S'));
    assertFalse(game.isGameOver());
  }

  @Test
  void testisGameOver2() {
    assertFalse(game.isGameOver());
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertTrue(game.makeMove(i, j, 'S'));
      }
    }
    assertTrue(game.isGameOver());
  }
  @Test
  void testMakeMoveScoreEarned() {
    assertEquals(0, game.getred());
    assertTrue(game.makeMove(0, 0, 'S'));
    assertTrue(game.makeMove(0, 1, 'O'));
    assertTrue(game.makeMove(0, 2, 'S'));
    assertEquals(1, game.getred());
  }

  @Test
  void testGetWinner() {
    //the gui calls changePlayer() to change the player
    assertTrue(game.makeMove(0, 0, 'S'));
    assertTrue(game.makeMove(0, 1, 'O'));
    assertTrue(game.makeMove(0, 2, 'S'));
    //even though the game is not over get winner returns whoever has the most points
    assertEquals("Red wins", game.getWinner());
  }
}