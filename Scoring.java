import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Scoring {

    static LinkedList<ScoreEntry> leaderboard = new LinkedList<>();
    static final String FILE_NAME = "Leaderboards.txt";


    static void addScore(int score) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name for the leaderboard: ");
        String name = scanner.nextLine();

        ScoreEntry entry = new ScoreEntry(name, score);

         // Insert into linked list in order
        ListIterator<ScoreEntry> it = leaderboard.listIterator();
        while (it.hasNext()) {
            if (score < it.next().score) {
                it.previous();
                it.add(entry);
                writeToFile(entry); // Save to file
                return;
            }
        }
        leaderboard.add(entry);
        writeToFile(entry);     // Save to file

    }

    static void writeToFile(ScoreEntry entry) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(entry.name + " - " + entry.score + " moves\n");
        } catch (IOException e) {
            System.out.println("Error writing to leaderboard file.");
            e.printStackTrace();
        }
    }



     static void printLeaderboard() {
    System.out.println("\nLeaderboard:");

    List<ScoreEntry> entries = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Each line is formatted as "Name - Score moves"
            String[] parts = line.split(" - ");
            if (parts.length == 2) {
                String name = parts[0].trim();
                String scorePart = parts[1].replace("moves", "").trim();
                try {
                    int score = Integer.parseInt(scorePart);
                    entries.add(new ScoreEntry(name, score));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }

        // Sort by score (ascending)
        entries.sort(Comparator.comparingInt(entry -> entry.score));

        // Print sorted entries
        for (ScoreEntry entry : entries) {
            System.out.println(entry.name + " - " + entry.score + " moves");
        }

    } catch (FileNotFoundException e) {
        System.out.println("Leaderboard file not found. No scores recorded yet.");
    } catch (IOException e) {
        System.out.println("Error reading leaderboard file.");
        e.printStackTrace();
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
