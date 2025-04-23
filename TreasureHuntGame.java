import java.util.*;

public class TreasureHuntGame {
    public static void main(String[] args) {
        System.out.println("Pick a level: ");
        System.out.println("1. Easy (8x8, 3 treasures, 5 traps)");
        System.out.println("2. Medium (10x10, 5 treasures, 10 traps)");
        System.out.println("3. Hard (15x15, 7 treasures, 20 traps)");
        System.out.print("Your choice (1-3): ");    
        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();

        switch (choice) {
            case 1: // Easy
                GridSetup.SIZE = 8; GridSetup.totalTreasures = 5; GridSetup.totalTraps = 5;break;
            case 2: // Medium
                GridSetup.SIZE = 10; GridSetup.totalTreasures = 7;GridSetup.totalTraps = 10;break;
            case 3: // Hard
                GridSetup.SIZE = 15;GridSetup.totalTreasures = 10; GridSetup.totalTraps = 15;break;
            default:
                System.out.println("Invalid choice. Defaulting to Easy.");
                GridSetup.SIZE = 10;
                GridSetup.totalTreasures = 5;
                GridSetup.totalTraps = 5;
            }
        
        GridSetup.grid = new char[GridSetup.SIZE][GridSetup.SIZE];
        playermovement.revealed = new boolean[GridSetup.SIZE][GridSetup.SIZE];


        GridSetup.initializeGrid();
        GridSetup.placeRandomItems(GridSetup.TREASURE, GridSetup.totalTreasures);
        GridSetup.placeRandomItems(GridSetup.TRAP, GridSetup.totalTraps);
        GridSetup.grid[playermovement.playerRow][playermovement.playerCol] = playermovement.PLAYER;
        playermovement.moveStack.push(new int[]{playermovement.playerRow, playermovement.playerCol});

        while (!playermovement.gameOver) {
            GridSetup.printGrid();
            System.out.println("Moves: " + playermovement.moves + " | Treasures found: " + playermovement.treasuresFound + "/" + GridSetup.totalTreasures);
            System.out.println("Key:");
            System.out.println("Up - U/u");
            System.out.println("Down - D/d");
            System.out.println("Left - L/l");
            System.out.println("Right - R/r");
            System.out.println("Undo - Z/z");
            System.out.println("Enter move (U/D/L/R) or undo (Z): ");
            String input = scanner.next().toUpperCase();

            if (input.equals("Z")) {
                playermovement.undoMove();
            } else {
                playermovement.makeMove(input.charAt(0));
            }

            if (playermovement.treasuresFound == GridSetup.totalTreasures) {
                System.out.println("Congratulations! You found all the treasures in " + playermovement.moves + " moves!");
                Scoring.addScore(playermovement.moves);
                playermovement.gameOver = true;
            }
        }
        
        Scoring.printLeaderboard(); // At the end of the game
        scanner.close();
        
    }
    
}