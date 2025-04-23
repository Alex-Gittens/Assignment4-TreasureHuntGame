import java.util.Stack;

public class playermovement {

    static final char PLAYER = 'P';

    static int playerRow = 0, playerCol = 0;
    static boolean[][] revealed;
    static Stack<int[]> moveStack = new Stack<>();
    static boolean gameOver = false;
    static int moves = 0;
    static int treasuresFound = 0;
    

    static void makeMove(char direction) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (direction) {
            case 'U': newRow--; break;
            case 'D': newRow++; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
            default: System.out.println("Invalid input!"); return;
        }

        if (newRow < 0 || newRow >= GridSetup.SIZE || newCol < 0 || newCol >= GridSetup.SIZE) {
            System.out.println("Out of bounds! Try a different move.");
            return;
        }

        moveStack.push(new int[]{playerRow, playerCol});
        GridSetup.grid[playerRow][playerCol] = GridSetup.EMPTY;

        playerRow = newRow;
        playerCol = newCol;
        moves++;

        revealed[playerRow][playerCol] = true;

        if (GridSetup.grid[playerRow][playerCol] == GridSetup.TREASURE) {
            System.out.println("You found a treasure!");
            treasuresFound++;
        } else if (GridSetup.grid[playerRow][playerCol] == GridSetup.TRAP) {
            System.out.println("You hit a trap! Game over.");
            gameOver = true;
            Scoring.addScore(moves);
            return;
        }

        GridSetup.grid[playerRow][playerCol] = PLAYER;
    }

    static void undoMove() {
        if (moveStack.size() > 1) {
            GridSetup.grid[playerRow][playerCol] = GridSetup.EMPTY;
            int[] lastMove = moveStack.pop();
            playerRow = lastMove[0];
            playerCol = lastMove[1];
            GridSetup.grid[playerRow][playerCol] = PLAYER;
            moves++;
            System.out.println("↩ Move undone.");
        } else {
            System.out.println("No moves to undo!");
        }
    }





}
