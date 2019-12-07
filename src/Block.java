/**
 * Block.java by Brandon Morris
 *
 * Block objects represent one quarter of the Pentago game board, there are four blocks each with
 * it's own
 */
public class Block {

    /**
     * Empty Block state, no pieces are played in this Block. Primarily used when
     * constructing new blocks at the beginning of the game.
     */
    private static final String DEFAULT_STATE = ".........";

    /**
     * String representation of the Block's state.
     */
    private String myState;

    /**
     * Default constructor.
     */
    public Block() {
        myState = DEFAULT_STATE;
    }

    /**
     * Creates a Block object with a predefined state.
     * @param theState The state to start the new Block object with.
     */
    public Block(String theState) {
        myState = theState;
    }

    public void setState(String theState) {
        myState = theState;
    }

    public String getState() {
        return myState;
    }

    /**
     * Updates the state of the Block at a given location with a player's game piece.
     *
     * @param thePosition The index of the game board to be played on.
     * @param thePlayer The player whose piece will be played and displayed in the Block's state.
     */
    public void updateBlock(int thePosition, String thePlayer) {
        String newState = "";
        int stringPos = thePosition - 1;
        if (stringPos == 0) {
            newState = newState.concat(thePlayer);
            newState = newState.concat(myState.substring(1));
        }
        else if (stringPos == 8) {
            newState = newState.concat(myState.substring(0, 8));
            newState = newState.concat(thePlayer);
        }
        else {
            newState = newState.concat(myState.substring(0, stringPos));
            newState = newState.concat(thePlayer);
            newState = newState.concat(myState.substring(stringPos + 1));
        }

        myState = newState;
    }

    /**
     * Takes the current state and rotates it 90 degrees to the right.
     */
    public void rotateRight() {
        String newState = "";
        char[] currState = myState.toCharArray();
        newState = newState.concat(String.valueOf(currState[6]));
        newState = newState.concat(String.valueOf(currState[3]));
        newState = newState.concat(String.valueOf(currState[0]));
        newState = newState.concat(String.valueOf(currState[7]));
        newState = newState.concat(String.valueOf(currState[4]));
        newState = newState.concat(String.valueOf(currState[1]));
        newState = newState.concat(String.valueOf(currState[8]));
        newState = newState.concat(String.valueOf(currState[5]));
        newState = newState.concat(String.valueOf(currState[2]));
        myState = newState;
    }

    /**
     * Takes the current state and rotates it 90 degrees to the left.
     */
    public void rotateLeft() {
        String newState = "";
        char[] currState = myState.toCharArray();
        newState = newState.concat(String.valueOf(currState[2]));
        newState = newState.concat(String.valueOf(currState[5]));
        newState = newState.concat(String.valueOf(currState[8]));
        newState = newState.concat(String.valueOf(currState[1]));
        newState = newState.concat(String.valueOf(currState[4]));
        newState = newState.concat(String.valueOf(currState[7]));
        newState = newState.concat(String.valueOf(currState[0]));
        newState = newState.concat(String.valueOf(currState[3]));
        newState = newState.concat(String.valueOf(currState[6]));
        myState = newState;
    }

    /**
     * Checks whether or not a specified spot on the game Block is open for play.
     *
     * @param thePos The index of the block to check.
     * @return True if no player has played in this spot, false if they have.
     */
    boolean isSpaceOccupied(int thePos) {
        boolean result = true;
        int stringPos = thePos - 1;
        if (myState.charAt(stringPos) == '.') {
            result = false;
        }

        return result;
    }

}
