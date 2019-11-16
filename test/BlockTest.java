import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {

    private Block testBlock;

    @Before
    public void setUp() throws Exception {
        testBlock = new Block("012345678");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void updateBlockAtBeginning() {
        String expected = "X12345678";
        testBlock.updateBlock(1, "X");
        assertEquals(expected, testBlock.getState());
    }

    @Test
    public void updateBlockAtEnd() {
        String expected = "01234567X";
        testBlock.updateBlock(9, "X");
        assertEquals(expected, testBlock.getState());
    }

    @Test
    public void updateBlock() {
        String expected = "0123X5678";
        testBlock.updateBlock(5, "X");
        assertEquals(expected, testBlock.getState());

    }

    @Test
    public void rotateRight() {
        String expected = "630741852";
        testBlock.rotateRight();
        assertEquals(expected, testBlock.getState());
    }

    @Test
    public void rotateLeft() {
        String expected = "258147036";
        testBlock.rotateLeft();
        assertEquals(expected, testBlock.getState());
    }

    @Test
    public void isSpaceOccupied1() {
        testBlock.setState("..X......");
        assertFalse(testBlock.isSpaceOccupied(1));
    }

    @Test
    public void isSpaceOccupied2() {
        testBlock.setState("..X......");
        assertTrue(testBlock.isSpaceOccupied(3));
    }
}