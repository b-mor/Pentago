/**
 * Move.java by Brandon Morris
 */
public class Move {

    int myPlayBlock;
    int myPosition;
    int myRotatingBlock;
    String myDirection;

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

    public int getMyPlayBlock() {
        return myPlayBlock;
    }

    public int getMyPosition() {
        return myPosition;
    }

    public int getMyRotatingBlock() {
        return myRotatingBlock;
    }

    public String getMyDirection() {
        return myDirection;
    }

    public String toString() {
        return myPlayBlock + "/" + myPosition + " " + myRotatingBlock + myDirection;
    }

}
