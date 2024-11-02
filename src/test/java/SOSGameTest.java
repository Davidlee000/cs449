
import org.example.SOSGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class SOSGameTest {

  private SOSGame sosGame;

  @BeforeEach
  void setUp() {
    // Initialize a game with a 3x3 board and mode (e.g., simple mode)
    sosGame = new SOSGame(3, "Simple");
  }

  @Test
  void testGetMode() {
    // Test the mode is initialized to simple mode
    assertEquals("Simple", sosGame.getMode(), "Expected simple mode to be initialized");
  }

  @Test
  void testGetSize() {
    // Test the size is initialized to 3
    assertEquals(3, sosGame.getSize(), "Expected size to be initialized to 3");
  }

  @Test
  void testGetBoard() {
    // Test the board is initialized to all spaces
    assertEquals(sosGame.getBoard()[0][0], ' ', "Expected board to be initialized to all spaces");
  }

  @Test
  void testMakeMoveValid() {
    // Test a valid move
    assertTrue(sosGame.makeMove(0, 0, 'S'), "Expected to be able to place 'S' at (0, 0)");
    assertEquals('S', sosGame.getBoard()[0][0], "Expected 'S' to be placed at (0, 0)");
  }

  @Test
  void testMakeMoveInvalid() {
    // Test an invalid move (space already occupied)
    sosGame.makeMove(0, 0, 'S');
    assertFalse(sosGame.makeMove(0, 0, 'O'), "Expected move to fail because space is already occupied");
  }

  @Test
  void testSwitchPlayer() {
    // Test switching players
    assertEquals("Red", sosGame.getcurrentPlayer(), "Expected Red to be the starting player");
    sosGame.switchPlayer();
    assertEquals("Blue", sosGame.getcurrentPlayer(), "Expected player to switch to Blue");
    sosGame.switchPlayer();
    assertEquals("Red", sosGame.getcurrentPlayer(), "Expected player to switch back to Red");
  }

  @Test
  void testGetCurrentPlayer() {
    // Test initial player is Red
    assertEquals("Red", sosGame.getcurrentPlayer(), "Expected Red to be the initial player");
  }
}