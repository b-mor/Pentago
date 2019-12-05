import java.util.ArrayList;

/**
 * Board.java by Brandon Morris
 *
 * Board objects are the game board of a Pentago game. They contain four 3x3 quadrants called
 * Blocks that together comprise a 6x6 game board.
 */
public class Board {

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
                myBlock3.updateBlock(thePosNum, thePlayer);
                break;
            case 4:
                myBlock4.updateBlock(thePosNum, thePlayer);
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
        } else if (!theDir.equalsIgnoreCase("L") && !theDir.equalsIgnoreCase("R")) {
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
     * Checks the current state of the game board and returns the utility.
     * Used by the AI to make decisions on what move to make, the board is checked for
     * streaks of pieces for the current player, where the higher the streak, the better the,
     * utility. The maximum utility that can be had is 6 for a board containing a full line on
     * one of the main diagonals, though a utility value of 5 is a board where any winning
     * move has been played.
     *
     * @param thePlayer The player we are currently evaluating the board for.
     * @return An integer value representing the longest streak for the current player on the board.
     */
    public int evaluateUtility(String thePlayer) {
        int max = 0;
        // Check horizontal lines.
        max = checkHorizontalLines(thePlayer, 0, 3, max);
        max = checkHorizontalLines(thePlayer, 3, 6, max);
        max = checkHorizontalLines(thePlayer, 6, 9, max);

        // Check vertical lines.
        max = checkVerticalLines(thePlayer, 0, 7, max);
        max = checkVerticalLines(thePlayer, 1, 8, max);
        max = checkVerticalLines(thePlayer, 2, 9, max);

        // Check diagonal lines.
        max = checkDiagonalLines(thePlayer, max);

        return max;
    }

    /**
     * Checks an individual position on the game board to see if it matches a current player's piece.
     * Used when evaluating the utility of the game board, a current and max streak are passed into
     * the method and are updated appropriately inside.
     *
     * @param thePlayer The current player who we are evaluating the board for.
     * @param theState The current state of a block on the game board.
     * @param thePosition The position on the block on the game board.
     * @param theStreak The current streak going into the method.
     * @param theMax The max streak going into the method.
     * @return An integer array containing the new current streak and max streak.
     */
    private int[] checkPosition(String thePlayer, char[] theState, int thePosition, int theStreak, int theMax) {
        int currStreak = theStreak;
        int maxStreak = theMax;
        if (theState[thePosition] == thePlayer.charAt(0)) {
            currStreak++;
            if (currStreak > maxStreak) {
                maxStreak = currStreak;
            }
        } else {
            currStreak = 0;
        }

        return new int[] {currStreak, maxStreak};
    }

    /**
     * Checks horizontal lines on the game board to evaluate the utility of the game for the
     * player who has the current turn. Given a start and ending index, checks two lines on the board,
     * one line in the top half and one line on the bottom half. Appropriate values for theStart and theEnd
     * would be (0,3) to check lines 1 and 4, (3, 6) to check lines 2 and 5, and (6, 9) to check
     * lines 3 and 6.
     *
     * @param thePlayer The current player we are evaluating the board for.
     * @param theStart The starting index of the blocks to check.
     * @param theEnd The ending index of the blocks to check.
     * @param theMax The current max streak going into the method.
     * @return An updated max streak value after checking for new streaks.
     */
    private int checkHorizontalLines(String thePlayer, int theStart, int theEnd, int theMax) {
        int currStreak = 0;
        int currMax = theMax;
        int[] result;
        char[] b1 = myBlock1.getState().toCharArray();
        char[] b2 = myBlock2.getState().toCharArray();
        char[] b3 = myBlock3.getState().toCharArray();
        char[] b4 = myBlock4.getState().toCharArray();

        // Checking horizontal line on top half of board.
        for (int i = theStart; i < theEnd; i++) {
            result = checkPosition(thePlayer, b1, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }
        for (int i = theStart; i < theEnd; i++) {
            result = checkPosition(thePlayer, b2, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }
        currStreak = 0;  // Checking new line on bottom half of board, reset streak.
        for (int i = theStart; i < theEnd; i++) {
            result = checkPosition(thePlayer, b3, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }
        for (int i = theStart; i < theEnd; i++) {
            result = checkPosition(thePlayer, b4, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }

        return currMax;
    }

    /**
     * Checks vertical lines on the game board to evaluate the utility of the game for the
     * player who has the current turn. Given a start and ending index, checks two lines on the board,
     * one line in the left half and one line on the right half. Appropriate values for theStart and theEnd
     * would be (0,7) to check lines v1 and v4, (1, 8) to check lines v2 and v5, and (2, 9) to check
     * lines v3 and v6.
     *
     * @param thePlayer The current player we are evaluating the board for.
     * @param theStart The starting index of the blocks to check.
     * @param theEnd The ending index of the blocks to check.
     * @param theMax The current max streak going into the method.
     * @return An updated max streak value after checking for new streaks.
     */
    private int checkVerticalLines(String thePlayer, int theStart, int theEnd, int theMax) {
        int currStreak = 0;
        int currMax = theMax;
        int[] result;
        char[] b1 = myBlock1.getState().toCharArray();
        char[] b2 = myBlock2.getState().toCharArray();
        char[] b3 = myBlock3.getState().toCharArray();
        char[] b4 = myBlock4.getState().toCharArray();

        // Checking vertical line on top half of board.
        for (int i = theStart; i < theEnd; i += 3) {
            result = checkPosition(thePlayer, b1, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }
        for (int i = theStart; i < theEnd; i += 3) {
            result = checkPosition(thePlayer, b3, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }
        currStreak = 0;  // Checking new line on right half of board, reset streak.
        for (int i = theStart; i < theEnd; i += 3) {
            result = checkPosition(thePlayer, b2, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }
        for (int i = theStart; i < theEnd; i += 3) {
            result = checkPosition(thePlayer, b4, i, currStreak, currMax);
            currStreak = result[0];
            currMax = result[1];
        }

        return currMax;
    }

    /**
     * Checks diagonal lines on the game board to evaluate the utility of the game for the
     * player who has the current turn.
     *
     * @param thePlayer The current player we are evaluating the board for.
     * @param theMax The current max streak going into the method.
     * @return An updated max streak value after checking for new streaks.
     */
    private int checkDiagonalLines(String thePlayer, int theMax) {
        // Anonymous inner class to help pass (state, index) tuples to the checkPosition method in loops.
        class Tuple {
            private char[] myState;
            private int myPos;

            private Tuple(char[] theState, int thePos) {
                myState = theState;
                myPos = thePos;
            }
        }

        int currStreak;
        int currMax = theMax;
        int[] result;
        ArrayList<Tuple[]> diagList = new ArrayList<>();
        char[] b1 = myBlock1.getState().toCharArray();
        char[] b2 = myBlock2.getState().toCharArray();
        char[] b3 = myBlock3.getState().toCharArray();
        char[] b4 = myBlock4.getState().toCharArray();

        // Add the eight diagonal lines you can win on to the list.
        // Utilizes the anonymous Tuple class to pair the state and index pairs to pass into helper methods later.
        // Unlike the horizontal and vertical lines, the diagonals have no pretty way to increment through
        // and add the lines as they appear on the board. It looks ugly, I know.
        diagList.add(new Tuple[] {new Tuple(b1, 3), new Tuple(b1, 7), new Tuple(b3, 2), new Tuple(b4, 3), new Tuple(b4, 7)});
        diagList.add(new Tuple[] {new Tuple(b1, 0), new Tuple(b1, 4), new Tuple(b1, 8), new Tuple(b4, 0), new Tuple(b4, 4)});
        diagList.add(new Tuple[] {new Tuple(b1, 4), new Tuple(b1, 8), new Tuple(b4, 0), new Tuple(b4, 4), new Tuple(b4, 8)});
        diagList.add(new Tuple[] {new Tuple(b1, 1), new Tuple(b1, 5), new Tuple(b2, 6), new Tuple(b4, 1), new Tuple(b4, 5)});
        diagList.add(new Tuple[] {new Tuple(b2, 1), new Tuple(b2, 3), new Tuple(b1, 8), new Tuple(b3, 1), new Tuple(b3, 3)});
        diagList.add(new Tuple[] {new Tuple(b2, 2), new Tuple(b2, 4), new Tuple(b2, 6), new Tuple(b3, 2), new Tuple(b3, 4)});
        diagList.add(new Tuple[] {new Tuple(b2, 4), new Tuple(b2, 6), new Tuple(b3, 2), new Tuple(b3, 4), new Tuple(b3, 6)});
        diagList.add(new Tuple[] {new Tuple(b2, 5), new Tuple(b2, 7), new Tuple(b4, 0), new Tuple(b3, 5), new Tuple(b3, 7)});

        for (Tuple[] line : diagList) {
            currStreak = 0;
            for (Tuple position : line) {
                result = checkPosition(thePlayer, position.myState, position.myPos, currStreak, currMax);
                currStreak = result[0];
                currMax = result[1];
            }
        }

        return currMax;
    }

    /**
     * Checks to see if the current state of the board has made someone a winner.
     * Should be called between making a new move and before letting the next player have their turn.
     *
     * @param thePlayer The player we are checking the win state for.
     * @return True if the player has won the game, false if not.
     */
    public boolean isWinner(String thePlayer) {
        return (evaluateUtility(thePlayer) >= 5);
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
        final String HORIZONTAL_PRINT_LINE = "+-------+-------+";
        char[] b1 = myBlock1.getState().toCharArray();
        char[] b2 = myBlock2.getState().toCharArray();
        char[] b3 = myBlock3.getState().toCharArray();
        char[] b4 = myBlock4.getState().toCharArray();

        System.out.println(HORIZONTAL_PRINT_LINE);
        System.out.println("| " + b1[0] + s + b1[1] + s + b1[2] + " | " + b2[0] + s + b2[1] + s + b2[2] + " |");
        System.out.println("| " + b1[3] + s + b1[4] + s + b1[5] + " | " + b2[3] + s + b2[4] + s + b2[5] + " |");
        System.out.println("| " + b1[6] + s + b1[7] + s + b1[8] + " | " + b2[6] + s + b2[7] + s + b2[8] + " |");
        System.out.println(HORIZONTAL_PRINT_LINE);
        System.out.println("| " + b3[0] + s + b3[1] + s + b3[2] + " | " + b4[0] + s + b4[1] + s + b4[2] + " |");
        System.out.println("| " + b3[3] + s + b3[4] + s + b3[5] + " | " + b4[3] + s + b4[4] + s + b4[5] + " |");
        System.out.println("| " + b3[6] + s + b3[7] + s + b3[8] + " | " + b4[6] + s + b4[7] + s + b4[8] + " |");
        System.out.println(HORIZONTAL_PRINT_LINE);

    }

}
