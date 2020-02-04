import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board testBoard;

    @Before
    public void setUp() throws Exception {
        testBoard = new Board();
    }

    @Test
    public void rotateBlockLeft() {
        testBoard.makeMove(1,1,3,"R", "W");
        testBoard.makeMove(1,2,3,"R", "W");
        testBoard.makeMove(1,3,3,"R", "W");
        testBoard.rotateBlockLeft(1);
        assertEquals("W..W..W..", testBoard.getBlock(1).getState());
    }

    @Test
    public void rotateBlockRight() {
        testBoard.makeMove(1,1,3,"R", "W");
        testBoard.makeMove(1,2,3,"R", "W");
        testBoard.makeMove(1,3,3,"R", "W");
        testBoard.rotateBlockRight(1);
        assertEquals("..W..W..W", testBoard.getBlock(1).getState());
    }

    @Test
    public void printBoard() {
    }

    @Test
    public void getBlock() {
        testBoard.makeMove(1,1,3,"R", "W");
        testBoard.makeMove(1,2,3,"R", "W");
        testBoard.makeMove(1,3,3,"R", "W");
        Block testBlock = testBoard.getBlock(1);
        assertNotNull(testBlock);
    }

    @Test
    public void makeMove() {
        testBoard.makeMove(1,1,3,"R", "B");
        testBoard.makeMove(1,9,3,"R","W");
        Block testBlock = testBoard.getBlock(1);
        assertEquals("B.......W", testBlock.getState());
    }

    @Test
    public void isValidMove() {
    }

    @Test
    public void getValidMovesEmptyBoard() {
        assertEquals(288, testBoard.getValidMoves().size());
    }

    @Test
    public void getValidMovesPlayedBoard() {
        testBoard.makeMove(1,1,1,"R","B");
        assertEquals(280, testBoard.getValidMoves().size());
    }

    @Test
    public void evaluateUtility0() {
        assertEquals(0, testBoard.evaluateUtility("W"));
    }

    @Test
    public void evaluateUtility3() {
        testBoard.makeMove(1,1,3,"R", "W");
        testBoard.makeMove(1,2,3,"R", "W");
        testBoard.makeMove(1,3,3,"R", "W");
        assertEquals(3, testBoard.evaluateUtility("W"));
    }

    public void evaluateUtilityMax() {
        testBoard.makeMove(1,1,3,"R", "W");
        testBoard.makeMove(1,2,3,"R", "W");
        testBoard.makeMove(1,3,3,"R", "W");
        testBoard.makeMove(2,1,3,"R", "W");
        testBoard.makeMove(2,2,3,"R", "W");
        testBoard.makeMove(2,3,3,"R", "W");
        assertEquals(6, testBoard.evaluateUtility("W"));
    }

    @Test
    public void isWinner() {
        testBoard.makeMove(1,1,3,"R", "W");
        testBoard.makeMove(1,2,3,"R", "W");
        testBoard.makeMove(1,3,3,"R", "W");
        testBoard.makeMove(2,1,3,"R", "W");
        testBoard.makeMove(2,2,3,"R", "W");
        assertTrue(testBoard.isWinner("W"));
    }
}