# 🗺️ Treasure Hunt Game (Java)

A console-based adventure game written in Java, using core data structures like arrays, stacks, linked lists, and binary search trees. Navigate a 10x10 grid, collect treasures, avoid traps, and aim for a high score!

---

## 🎮 Game Instructions

### ✅ Instructions
- Grid Setup: Implement a grid (10x10) using a 2D array. Randomly place 5 treasures, represented as 'T', and 10 traps, represented as 'X', on the grid. Ensure no two treasures or traps overlap. The player starts at the top-left corner of the grid (position [0][0]).

- Player Movement: Allow the player to move up, down, left, or right using commands ('U', 'D', 'L', 'R'). Keep track of the player's position with a stack to allow undoing the last move. Prevent the player from moving out of bounds.
- Game Rules: If the player lands on a treasure ('T'), they collect it, and the position becomes empty. If the player lands on a trap ('X'), the game ends, and the player loses.The player wins by collecting all 5 treasures without hitting any traps. Provide the option to undo the last move using a stack.
- Scoring System: Calculate the score based on the number of moves made to collect all treasures. Track and display a leaderboard of high scores using a linked list or binary search tree for efficient insertion and retrieval.
- User Interface: Create a simple text-based interface to display the grid after each move. Provide feedback to the player about their current position, collected treasures, and remaining moves.


---
