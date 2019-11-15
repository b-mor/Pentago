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
        newState = newState.concat(String.valueOf(myState.charAt(6)));
        newState = newState.concat(String.valueOf(myState.charAt(3)));
        newState = newState.concat(String.valueOf(myState.charAt(0)));
        newState = newState.concat(String.valueOf(myState.charAt(7)));
        newState = newState.concat(String.valueOf(myState.charAt(4)));
        newState = newState.concat(String.valueOf(myState.charAt(1)));
        newState = newState.concat(String.valueOf(myState.charAt(8)));
        newState = newState.concat(String.valueOf(myState.charAt(5)));
        newState = newState.concat(String.valueOf(myState.charAt(2)));
        myState = newState;
    }

    /**
     * Takes the current state and rotates it 90 degrees to the left.
     */
    public void rotateLeft() {
        String newState = "";
        newState = newState.concat(String.valueOf(myState.charAt(2)));
        newState = newState.concat(String.valueOf(myState.charAt(5)));
        newState = newState.concat(String.valueOf(myState.charAt(8)));
        newState = newState.concat(String.valueOf(myState.charAt(1)));
        newState = newState.concat(String.valueOf(myState.charAt(4)));
        newState = newState.concat(String.valueOf(myState.charAt(7)));
        newState = newState.concat(String.valueOf(myState.charAt(0)));
        newState = newState.concat(String.valueOf(myState.charAt(3)));
        newState = newState.concat(String.valueOf(myState.charAt(6)));
        myState = newState;
    }

}
