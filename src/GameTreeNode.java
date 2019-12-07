import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameTreeNode {

    /* The current state of the game in this node. */
    private Board myBoard;

    /* The game move that this node is holding. Can be passed up to parent nodes during searches. */
    private Move myMove;

    /* The player piece for whoever's turn it would be during this point in the game. */
    private String myPlayer;

    /* The type of node this is, used in alpha beta searching. Can be "MAX" or "MIN". */
    private String myType;

    /* The utility value of this node's board, calculated by finding longest streaks of player pieces. */
    private int myUtility;

    /* Alpha value for alpha beta pruning. */
    private int myAlpha;

    /* Beta value for alpha beta pruning. */
    private int myBeta;

    /* List of all children states and permutations that could spawn from this node/decision. */
    private ArrayList<GameTreeNode> myChildren;

    /**
     * Creates a new GameTreeNode object. Utility is calculated by evaluating the board provided as an argument.
     * The children list begins empty, and is not filled until populateChildren() is called.
     * Alpha and beta values default to -10 and 10 respectively; these numbers are arbitrary. Traditionally
     * these values should be negative and positive infinity, but because the max utility of any board
     * is 6, these numbers were chosen for simplicity.
     *
     * @param theBoard The game board state this node will hold.
     * @param thePlayer The player whose turn it is at this point in the game.
     */
    public GameTreeNode(Board theBoard, Move theMove, String thePlayer, String theType) {
        myBoard = theBoard;
        myMove = theMove;
        myPlayer = thePlayer;
        myType = theType;
        myUtility = myBoard.evaluateUtility(myPlayer);
        myAlpha = -10;
        myBeta = 10;
        myChildren = new ArrayList<>();
    }

    /**
     * For any given node, get a list of all valid permutations of the board and add them to the list
     * of children.
     */
    private void populateChildren() {
        ArrayList<Move> validMoves = myBoard.getValidMoves();  // Get all valid moves for the current board state.
        Collections.shuffle(validMoves, new Random());
        for (Move move : validMoves) {
            Board board = new Board(myBoard);
            board.makeMove(move.getPlayBlock(), move.getPosition(), move.getRotatingBlock(),
                    move.getDirection(), myPlayer);
            myChildren.add(new GameTreeNode(board, move, oppositePlayer(myPlayer), oppositeType(myType)));
        }
    }

    /**
     * Generate a tree of depth level three.
     */
    public void generateTree() {
        populateChildren();  // Generate all children for the current node. (Depth level 1)
        for (GameTreeNode child : myChildren) {
            child.populateChildren();  // Generate children for each child. (Depth level 2)
        }
    }

    /**
     * Searches the previously generated game tree using alpha beta pruning techniques to
     * find the node with the best move to make. Detects when searching further branches is futile
     * to optimize search time.
     */
    public void alphaBetaPruningSearch() {
        for (GameTreeNode child: myChildren) {
            passAlphaBeta(child);
            for (GameTreeNode grandChild : child.myChildren) {
                grandChild.updateAlphaBeta(grandChild.getUtility());
                boolean isUpdated = child.receiveAlphaBeta(grandChild);
                if (isUpdated) {
                    child.setMove(new Move(grandChild.getMove()));
                }
                if (child.isPruneCondition()) {
                    break;
                }
            }
            boolean isUpdated = receiveAlphaBeta(child);
            if (isUpdated) {
                setMove(new Move(child.getMove()));
            }
            if (isPruneCondition()) {
                break;
            }
        }
    }

    /**
     * Gives the opposite player to the current player.
     *
     * @param theCurrentPlayer The current player.
     * @return "W" is the current player is the black pieces, "B" if they are the white pieces.
     */
    private String oppositePlayer(String theCurrentPlayer) {
        String result = "";
        if (theCurrentPlayer.equalsIgnoreCase("W")) {
            result += "B";
        } else {
            result += "W";
        }
        return result;
    }

    /**
     * Returns the opposite GameTreeNode type as the one passed in.
     *
     * @param theCurrentType The type of the node we want to get the opposite of.
     * @return "MIN" if the compared node is "MAX, "MAX" if the compared node is "MIN".
     */
    private String oppositeType(String theCurrentType) {
        String result = "";
        if (theCurrentType.equalsIgnoreCase("MAX")) {
            result += "MIN";
        } else {
            result += "MAX";
        }
        return result;
    }

    /**
     * Compares the alpha or beta value if the calling node is a MAX or MIN node respectively and updates
     * those values if better than the currently held value.
     *
     * @param theValue The utility or other alpha beta value we are comparing against.
     * @return True if the alpha or beta value of the node has been updated, false if not.
     */
    private boolean updateAlphaBeta(int theValue) {
        boolean isNewValue = false;
        if (myType.equalsIgnoreCase("MAX")) {
            if (myAlpha < theValue) {
                myAlpha = theValue;
                isNewValue = true;
            }
        } else {
            if (myBeta > theValue) {
                myBeta = theValue;
                isNewValue = true;
            }
        }
        return isNewValue;
    }

    /**
     * Passes down the calling node's alpha and beta values to one of it's children in the game tree.
     *
     * @param theChild The child we are passing our values down to.
     */
    private void passAlphaBeta(GameTreeNode theChild) {
        theChild.setAlpha(myAlpha);
        theChild.setBeta(myBeta);
    }

    /**
     * A parent node receives alpha beta values from one of it's children and updates it's alpha
     * or beta value accordingly, depending on what type of node the caller is.
     *
     * @param theChild The child we are checking the values of.
     * @return True if the parent updates their values, false if not.
     */
    private boolean receiveAlphaBeta(GameTreeNode theChild) {
        boolean isNewValue = false;
        isNewValue = updateAlphaBeta(theChild.getAlpha());
        isNewValue = updateAlphaBeta(theChild.getBeta());
        return isNewValue;
    }

    /**
     * The condition that must be met to prune children off of the game tree during search.
     *
     * @return True if alpha is greater than or equal to beta, false otherwise.
     */
    private boolean isPruneCondition() {
        return myAlpha >= myBeta;
    }

    public Board getBoard() { return myBoard; }

    public Move getMove() { return myMove; }

    public void setMove(Move theNewMove) { myMove = theNewMove; }

    public String getPlayer() { return myPlayer; }

    public int getUtility() { return myUtility; }

    public int getAlpha() { return myAlpha; }

    public void setAlpha(int theNewAlpha) { myAlpha = theNewAlpha; }

    public int getBeta() { return myBeta; }

    public void setBeta(int theNewBeta) { myBeta = theNewBeta; }

    public ArrayList<GameTreeNode> getChildren() { return myChildren; }

}
