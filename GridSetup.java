import java.util.Arrays;
import java.util.Random;


public class GridSetup {

    static int SIZE = 10;
    static final char EMPTY = '.';
    static final char TREASURE = 'T';
    static final char TRAP = 'X';
    static char[][] grid;
    static int totalTreasures = 5;
    static int totalTraps = 10;

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
                    if (i == playermovement.playerRow && j == playermovement.playerCol) {
                        System.out.print("P ");
                    } else if (playermovement.revealed[i][j]) {
                        System.out.print(grid[i][j] + " ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
        } 
    
}
