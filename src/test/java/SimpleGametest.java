import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.example.SimpleGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleGametest {

    private SimpleGame game;

    @BeforeEach
    void setUp() {
        game = new SimpleGame(3, "Simple");
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
        assertTrue(game.isGameOver());
    }
    @Test
    void testisGameOver2() {
        assertFalse(game.isGameOver());
        assertTrue(game.makeMove(0, 0, 'S'));
        assertTrue(game.makeMove(1, 1, 'O'));
        assertTrue(game.makeMove(2, 2, 'S'));
        assertTrue(game.isGameOver());
    }

    @Test
    void testGetWinner() {
        assertTrue(game.makeMove(0, 0, 'S'));
        assertTrue(game.makeMove(0, 1, 'O'));
        assertTrue(game.makeMove(0, 2, 'S'));
        assertEquals("Red wins!", game.getWinner());
    }

    @Test
    void testBlueWins() {
        assertTrue(game.makeMove(0, 0, 'S'));game.switchPlayer();
        assertTrue(game.makeMove(1, 1, 'O'));game.switchPlayer();
        assertTrue(game.makeMove(0, 2, 'S'));game.switchPlayer();
        assertTrue(game.makeMove(0, 1, 'O'));
        assertEquals("Blue wins!", game.getWinner());
    }

    @Test
    void testRedWins() {
        assertTrue(game.makeMove(0, 0, 'S'));
        assertTrue(game.makeMove(1, 1, 'O'));
        assertTrue(game.makeMove(2, 2, 'S'));
        assertTrue(game.makeMove(2, 1, 'S'));
        assertEquals("Red wins!", game.getWinner());
    }

    @Test
    void GameDraw() {
        assertFalse(game.isFull());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertTrue(game.makeMove(i, j, 'S'));
            }
        }
        //if game is full before game over the game is a draw
        assertTrue(game.isFull());
        assertFalse(game.isGameOver());
    }
}