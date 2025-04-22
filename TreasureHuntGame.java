import java.util.*;

public class TreasureHuntGame {
    static int SIZE = 10;
    static final char EMPTY = '.';
    static final char TREASURE = 'T';
    static final char TRAP = 'X';
    static final char PLAYER = 'P';

    static char[][] grid;
    static boolean[][] revealed;
    static Stack<int[]> moveStack = new Stack<>();
    static LinkedList<ScoreEntry> leaderboard = new LinkedList<>();

    static int playerRow = 0, playerCol = 0;
    static int treasuresFound = 0;
    static int totalTreasures = 5;
    static int totalTraps = 10;
    static int moves = 0;
    static boolean gameOver = false;

    public static void main(String[] args) {
        System.out.println("Pick a level: ");
        System.out.print("1. Easy (8x8, 3 treasures, 5 traps)");
        System.out.println("2. Medium (10x10, 5 treasures, 10 traps)");
        System.out.println("3. Hard (15x15, 7 treasures, 20 traps)");
        System.out.print("Your choice (1-3): ");


        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();
        switch (choice) {
            case 1: // Easy
                SIZE = 8;
                totalTreasures = 5;
                totalTraps = 5;
                break;
            case 2: // Medium
                SIZE = 10;
                totalTreasures = 7;
                totalTraps = 10;
                break;
            case 3: // Hard
                SIZE = 15;
                totalTreasures = 10;
                totalTraps = 15;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Easy.");
                SIZE = 10;
                totalTreasures = 5;
                totalTraps = 5;
        }
        
            grid = new char[SIZE][SIZE];
            revealed = new boolean[SIZE][SIZE];


        initializeGrid();
        placeRandomItems(TREASURE, totalTreasures);
        placeRandomItems(TRAP, totalTraps);
        grid[playerRow][playerCol] = PLAYER;
        moveStack.push(new int[]{playerRow, playerCol});

        while (!gameOver) {
            printGrid();
            System.out.println("Moves: " + moves + " | Treasures found: " + treasuresFound + "/" + totalTreasures);
            System.out.println("Key:");
            System.out.println("Up - U/u");
            System.out.println("Down - D/d");
            System.out.println("Left - L/l");
            System.out.println("Right - R/r");
            System.out.println("Undo - Z/z");
            System.out.println("Enter move (U/D/L/R) or undo (Z): ");
            String input = scanner.next().toUpperCase();

            if (input.equals("Z")) {
                undoMove();
            } else {
                makeMove(input.charAt(0));
            }

            if (treasuresFound == totalTreasures) {
                System.out.println("🎉 Congratulations! You found all the treasures in " + moves + " moves!");
                addScore(moves);
                gameOver = true;
            }
        }

        printLeaderboard();
        scanner.close();
    }

    static void initializeGrid() {

        for (int i = 0; i < SIZE; i++)
            Arrays.fill(grid[i], EMPTY);
    }

    static void placeRandomItems(char item, int count) {
        Random rand = new Random();
        int placed = 0;

        while (placed < count) {
            int r = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);
            if (grid[r][c] == EMPTY && !(r == 0 && c == 0)) {
                grid[r][c] = item;
                placed++;
            }
        }
    }

    static void printGrid() {
        System.out.println("\nGrid:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print("P ");
                } else if (revealed[i][j]) {
                    System.out.print(grid[i][j] + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

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

        if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) {
            System.out.println("Out of bounds! Try a different move.");
            return;
        }

        moveStack.push(new int[]{playerRow, playerCol});
        grid[playerRow][playerCol] = EMPTY;

        playerRow = newRow;
        playerCol = newCol;
        moves++;

        revealed[playerRow][playerCol] = true;

        if (grid[playerRow][playerCol] == TREASURE) {
            System.out.println("💰 You found a treasure!");
            treasuresFound++;
        } else if (grid[playerRow][playerCol] == TRAP) {
            System.out.println("💥 You hit a trap! Game over.");
            gameOver = true;
            addScore(moves);
            return;
        }

        grid[playerRow][playerCol] = PLAYER;
    }

    static void undoMove() {
        if (moveStack.size() > 1) {
            grid[playerRow][playerCol] = EMPTY;
            int[] lastMove = moveStack.pop();
            playerRow = lastMove[0];
            playerCol = lastMove[1];
            grid[playerRow][playerCol] = PLAYER;
            moves++;
            System.out.println("↩️ Move undone.");
        } else {
            System.out.println("No moves to undo!");
        }
    }

    static void addScore(int score) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name for the leaderboard: ");
        String name = scanner.nextLine();
        ScoreEntry entry = new ScoreEntry(name, score);

        ListIterator<ScoreEntry> it = leaderboard.listIterator();
        while (it.hasNext()) {
            if (score < it.next().score) {
                it.previous();
                it.add(entry);
                return;
            }
        }
        leaderboard.add(entry);
    }

    static void printLeaderboard() {
        System.out.println("\n🏆 Leaderboard:");
        for (ScoreEntry entry : leaderboard) {
            System.out.println(entry.name + " - " + entry.score + " moves");
        }
    }

    static class ScoreEntry {
        String name;
        int score;

        ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}