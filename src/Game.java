import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {

    /* Board object that represents the current state of the game. */
    private Board myBoard;

    /* The pieces that the player will use for the game (W for white pieces, B for black pieces.) */
    private String myPlayer;

    /* Name of the player. */
    private String myPlayerName;

    /* The pieces that the computer will use for the game (W for white pieces, B for black pieces.) */
    private String myComputer;

    /**
     * Default constructor.
     */
    public Game() {
        myBoard = new Board();
    }

    public static void main(String[] theArgs) {
        Game game = new Game();
        game.startUp();
    }

    /**
     * Shows the user the start up prompt, collects information from them, then kicks off the main game loop.
     */
    public void startUp() {
        myPlayer = "";
        myPlayerName = "";
        int firstPlayer = -1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("~*^*~*^*~*^*~ Welcome to Pentago ~*^*~*^*~*^*~\n\n");

        while (myPlayerName.length() < 1) {  // Input validation for player name.
            System.out.println("Enter your name: ");
            myPlayerName = scanner.nextLine();
        }
        System.out.println("Your name is " + myPlayerName + ". You are the white pieces.");
        myPlayer = "W";
        System.out.println("Your opponent for this game is the computer, they are the black pieces.\n");
        myComputer = "B";

        while (firstPlayer != 1 && firstPlayer != 2) {  // Input validation for who goes first.
            System.out.println("Who goes first? (1 for human first move, 2 for computer first move): ");
            firstPlayer = Integer.parseInt(scanner.next());
            scanner.nextLine();
        }

        gameLoop(firstPlayer);
    }

    /**
     * Main loop for the game. Lets each player go and checks for win state after each turn.
     *
     * @param theFirstPlayer The player who gets to play the first turn.
     */
    private void gameLoop(int theFirstPlayer) {
        boolean gameOver = false;
        myBoard.printBoard();
        // Player goes first.
        if (theFirstPlayer == 1) {
            while (!gameOver) {
                humanTurn();
                myBoard.printBoard();
                gameOver = checkForWinners();
                if (gameOver) {
                    break;
                }
                Move computerMove = computerTurn();
                myBoard.printBoard();
                System.out.println("The computer plays " + computerMove.toString());
                gameOver = checkForWinners();
                if (gameOver) {
                    break;
                }
            }
        }
        // Computer goes first.
        else {
            while (!gameOver) {
                Move computerMove = computerTurn();
                myBoard.printBoard();
                System.out.println("The computer plays " + computerMove.toString());
                gameOver = checkForWinners();
                if (gameOver) {
                    break;
                }
                humanTurn();
                myBoard.printBoard();
                gameOver = checkForWinners();
                if (gameOver) {
                    break;
                }
            }
        }
    }

    /**
     * Checks the current state of the game board for whether or not a player has won the game.
     *
     * @return True if the game is over, false if not.
     */
    private boolean checkForWinners() {
        boolean gameOver = false;
        if (myBoard.isWinner(myPlayer)) {
            System.out.println("You win! Good job!");
            gameOver = true;
        } else if (myBoard.isWinner(myComputer)) {
            System.out.println("The computer wins! Better luck next time.");
            gameOver = true;
        }
        return gameOver;
    }

    /**
     * Lets the human play their turn. Will receive user input, validate that input, and make the move
     * once a valid one has been made.
     */
    private void humanTurn() {
        Scanner scanner = new Scanner(System.in);
        String move = "";

        // Continue prompting user until they give a valid move.
        while (!validateMoveInput(move)) {
            System.out.println("Its your turn. Enter your move: ");
            move = scanner.nextLine();
            if (!validateMoveInput(move)) {
                System.out.println("Invalid input.\n");
                System.out.println("Enter moves in format following format: ");
                System.out.println("Quadrant (1-4)/Index (1-9) Quadrant (1-4) Direction (L or R)");
                System.out.println("Example: 2/3 4R or 1/1 2L");
            }
        }

        int moveBlock = Character.getNumericValue(move.charAt(0));
        int blockIndex = Character.getNumericValue(move.charAt(2));
        int rotateBlock = Character.getNumericValue(move.charAt(4));
        String direction = move.substring(5, 6);
        myBoard.makeMove(moveBlock, blockIndex, rotateBlock, direction, myPlayer);
    }

    /**
     * Creates an alpha beta tree and searches it to decide the next best move to make, then plays that move.
     */
    private Move computerTurn() {
        GameTreeNode decisionNode = new GameTreeNode(myBoard, null, myComputer, "MAX");
        decisionNode.generateTree();
        decisionNode.alphaBetaPruningSearch();
        Move computerMove = decisionNode.getMove();
        myBoard.makeMove(computerMove.getPlayBlock(), computerMove.getPosition(),
                         computerMove.getRotatingBlock(), computerMove.getDirection(), myComputer);
        return computerMove;
    }

    /**
     * Checks a String to see if it matches the pattern and valid possibilities for a move to be played
     * on the board.
     *
     * @param theMove The move input received from the player.
     * @return true if the move is a valid one, false if it isn't.
     */
    private boolean validateMoveInput(String theMove) {
        boolean result = true;

        // If incorrect size of input, return without checking any index to prevent possible exceptions.
        if (theMove.length() != 6) {
            result = false;
        }

        if (result) {
            // Correct input size, need to check individual components of the move.
            int moveBlock = Character.getNumericValue(theMove.charAt(0));
            int blockIndex = Character.getNumericValue(theMove.charAt(2));
            int rotateBlock = Character.getNumericValue(theMove.charAt(4));
            char direction = theMove.charAt(5);

            if (moveBlock < 1 || moveBlock > 4) {
                result = false;
            } else if (blockIndex < 1 || blockIndex > 9) {
                result = false;
            } else if (rotateBlock < 1 || rotateBlock > 4) {
                result = false;
            } else if (direction != 'L' && direction != 'R') {
                result = false;
            } else if (!myBoard.isValidMove(moveBlock, blockIndex)) {
                // Passed all checks, the move is a valid option. Check to see if the space is occupied.
                result = false;
            }
        }

        return result;
    }

    public Board getBoard() {
        return myBoard;
    }

    public void setBoard(Board myBoard) {
        this.myBoard = myBoard;
    }

}
