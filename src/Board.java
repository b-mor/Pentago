import java.util.ArrayList;

/**
 * Board.java by Brandon Morris
 *
 * Board objects are the game board of a Pentago game. They contain four 3x3 quadrants called
 * Blocks that together comprise a 6x6 game board.
 */
public class Board {

    /**
     * Aesthetic line used in printing the current Board state to console in printBoard().
     */
    private static final String HORIZONTAL_PRINT_LINE = "+-------+-------+";

    /**
     * Block objects that represent the four corners of the game board.
     */
    private Block myBlock1;
    private Block myBlock2;
    private Block myBlock3;
    private Block myBlock4;

    /**
     * Default constructor.
     */
    public Board() {
        myBlock1 = new Block();
        myBlock2 = new Block();
        myBlock3 = new Block();
        myBlock4 = new Block();
    }

    public Board(String state1, String state2, String state3, String state4) {
        myBlock1 = new Block(state1);
        myBlock2 = new Block(state2);
        myBlock3 = new Block(state3);
        myBlock4 = new Block(state4);
    }

    /**
     * Returns the specified Block from this Board (Block 1, 2, 3, or 4).
     *
     * @param theBlockNum The Block to be returned.
     * @return The Block corresponding the to the block number asked for.
     */
    public Block getBlock(int theBlockNum) {
        Block result = null;
        switch (theBlockNum) {
            case 1:
                result = myBlock1;
                break;
            case 2:
                result = myBlock2;
                break;
            case 3:
                result = myBlock3;
                break;
            case 4:
                result = myBlock4;
                break;
        }

        return result;
    }

    /**
     * Updates the Board's state by adding a player's piece to the specified position
     * on a specific Block on the Board.
     *
     * @param theBlockNum The Block to be played on.
     * @param thePosNum The position on the Block for the piece to be played.
     * @param thePlayer The player's piece.
     */
    public void placePiece(int theBlockNum, int thePosNum, String thePlayer) {
        switch (theBlockNum) {
            case 1:
                myBlock1.updateBlock(thePosNum, thePlayer);
                break;
            case 2:
                myBlock2.updateBlock(thePosNum, thePlayer);
                break;
            case 3:
                myBlock2.updateBlock(thePosNum, thePlayer);
                break;
            case 4:
                myBlock2.updateBlock(thePosNum, thePlayer);
                break;
        }
    }

    /**
     * Performs a full player move on the board. Adds a piece to the current board and rotates the specified
     * block in the direction given.
     *
     * @param theBlockNum The block of the board to be played on.
     * @param thePos The position on the block to be played.
     * @param theRotNum The block of the board to be rotated.
     * @param theDir The direction the rotating block will rotate.
     * @param thePlayer The player's piece.
     * @throws IllegalStateException Thrown if a move is attempted on an unplayable space on the board.
     * @throws IllegalArgumentException Thrown if the direction provided is not "l", "L", "r", or "R".
     */
    public void makeMove(int theBlockNum, int thePos, int theRotNum,
                         String theDir, String thePlayer) throws IllegalStateException, IllegalArgumentException {

        if (!isValidMove(theBlockNum, thePos)) {
            throw new IllegalStateException("Attempting to play move on invalid location.");
        } else if (!theDir.equalsIgnoreCase("L") || !theDir.equalsIgnoreCase("R")) {
            throw new IllegalArgumentException("Invalid direction operator.");
        }

        else {
            placePiece(theBlockNum, thePos, thePlayer);
            if (theDir.equalsIgnoreCase("L")) {
                rotateBlockLeft(theRotNum);
            } else {
                rotateBlockRight(theRotNum);
            }
        }

    }

    /**
     * Checks whether or not a position for a block is available for a player move.
     *
     * @param theBlockNum The block to be checked.
     * @param thePos The position on the block to be checked.
     * @return True if the space is open for play (char at the position is '.'), False if not.
     */
    public boolean isValidMove(int theBlockNum, int thePos) {
        boolean result = true;
        switch (theBlockNum) {
            case 1:
                if (myBlock1.isSpaceOccupied(thePos)) result = false;
                break;
            case 2:
                if (myBlock2.isSpaceOccupied(thePos)) result = false;
                break;
            case 3:
                if (myBlock3.isSpaceOccupied(thePos)) result = false;
                break;
            case 4:
                if (myBlock4.isSpaceOccupied(thePos)) result = false;
                break;
        }

        return result;
    }

    /**
     * Rotates the specified Block of the Board to the left.
     *
     * @param theBlockNum The number of the Block to be rotated.
     */
    public void rotateBlockLeft(int theBlockNum) {
        switch (theBlockNum) {
            case 1:
                myBlock1.rotateLeft();
                break;
            case 2:
                myBlock2.rotateLeft();
                break;
            case 3:
                myBlock3.rotateLeft();
                break;
            case 4:
                myBlock4.rotateLeft();
                break;
        }
    }

    /**
     * Rotates the specified Block of the Board to the right.
     *
     * @param theBlockNum The number of the Block to be rotated.
     */
    public void rotateBlockRight(int theBlockNum) {
        switch (theBlockNum) {
            case 1:
                myBlock1.rotateRight();
                break;
            case 2:
                myBlock2.rotateRight();
                break;
            case 3:
                myBlock3.rotateRight();
                break;
            case 4:
                myBlock4.rotateRight();
                break;
        }
    }

    /**
     * Generated a list of all possible playing options a player could make given the current state of the board.
     * For each space the player could play on, will generate the moves that play on that space and all
     * combinations of rotating blocks and rotation directions.
     *
     * @return A list Move objects that are all valid options to play on.
     */
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> moveList = new ArrayList<>();

        for (int block = 1; block < 5; block++) {
            for (int pos = 1; pos < 10; pos++) {
                if (isValidMove(block, pos)) {
                    generateMoveVariations(moveList, block, pos);
                }
            }
        }

        return moveList;
    }

    /**
     * Helper function for getValidMoves(). For a given valid location on the board, adds the possible
     * variations of that same playable move including rotating all four blocks in each direction.
     * In total, will add eight possible moves to the list of valid moves.
     *
     * @param theMoveList The list of valid moves to be added to.
     * @param theBlockNum The block the move will be made on.
     * @param thePos The position on the block the move will be made on.
     */
    private void generateMoveVariations(ArrayList<Move> theMoveList, int theBlockNum, int thePos) {
        for (int i = 1; i < 5; i++) {
            theMoveList.add(new Move(theBlockNum, thePos, i, "L"));
            theMoveList.add(new Move(theBlockNum, thePos, i, "R"));
        }
    }

    /**
     * Prints a representation of the current Board state to console.
     */
    public void printBoard() {
        String s = " ";
        char[] b1 = myBlock1.getState().toCharArray();
        char[] b2 = myBlock2.getState().toCharArray();
        char[] b3 = myBlock3.getState().toCharArray();
        char[] b4 = myBlock4.getState().toCharArray();

        System.out.println(HORIZONTAL_PRINT_LINE);
        System.out.println("| " + b1[0] + b1[1] + b1[2] + " | " + b2[0] + b2[1] + b2[2] + " |");
        System.out.println("| " + b1[3] + b1[4] + b1[5] + " | " + b2[3] + b2[4] + b2[5] + " |");
        System.out.println("| " + b1[6] + b1[7] + b1[8] + " | " + b2[6] + b2[7] + b2[8] + " |");
        System.out.println(HORIZONTAL_PRINT_LINE);
        System.out.println("| " + b3[0] + b3[1] + b3[2] + " | " + b4[0] + b4[1] + b4[2] + " |");
        System.out.println("| " + b3[3] + b3[4] + b3[5] + " | " + b4[3] + b4[4] + b4[5] + " |");
        System.out.println("| " + b3[6] + b3[7] + b3[8] + " | " + b4[6] + b4[7] + b4[8] + " |");
        System.out.println(HORIZONTAL_PRINT_LINE);

    }

}
