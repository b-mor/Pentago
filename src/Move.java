/**
 * Move.java by Brandon Morris
 */
public class Move {

    private int myPlayBlock;
    private int myPosition;
    private int myRotatingBlock;
    private String myDirection;

    /**
     * Move object constructor.
     *
     * @param thePlayBlock The number of the block to be played on.
     * @param thePosition The position on the block to be played on.
     * @param theRotatingBlock The number of the block to be rotated.
     * @param theDirection The direction in which the rotating block gets rotated.
     */
    public Move(int thePlayBlock, int thePosition, int theRotatingBlock, String theDirection) {
        myPlayBlock = thePlayBlock;
        myPosition = thePosition;
        myRotatingBlock = theRotatingBlock;
        myDirection = theDirection;
    }

    /**
     * Alternate constructor that creates a new Move object as a copy of an existing one.
     *
     * @param theMove The move to be copied into the new object.
     */
    public Move(Move theMove) {
        myPlayBlock = theMove.getPlayBlock();
        myPosition = theMove.getPosition();
        myRotatingBlock = theMove.getRotatingBlock();
        myDirection = "" + theMove.getDirection();
    }

    public int getPlayBlock() {
        return myPlayBlock;
    }

    public int getPosition() {
        return myPosition;
    }

    public int getRotatingBlock() {
        return myRotatingBlock;
    }

    public String getDirection() {
        return myDirection;
    }

    public String toString() {
        return myPlayBlock + "/" + myPosition + " " + myRotatingBlock + myDirection;
    }

}
