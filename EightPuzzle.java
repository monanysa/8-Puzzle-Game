
// StdDraw is used for animating the 8 puzzle with user interactions
import edu.princeton.cs.algs4.StdDraw;
import game.Board; // the Board class which is in the game package is used here
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent; // for the constants of the keys on the keyboard

// A program that partially implements the 8 puzzle.
public class EightPuzzle {
   // The main method is the entry point where the program starts execution.
   public static void main(String[] args) {

      StdDraw.setCanvasSize(500, 500);

      StdDraw.setScale(-0.5, 5.5);

      StdDraw.enableDoubleBuffering();

      double newPuzzleX = 0.5;
      double newPuzzleY = 0.0001;
      double newPuzzleWidth = 1.5;
      double newPuzzleHeight = 0.5;
      double startSolutionX = 4.5;
      double startSolutionY = 0.0001;
      double startSolutionWidth = 1.5;
      double startSolutionHeight = 0.5;

      Board board = new Board();
      boolean isSolverFinished = false;

      boolean isSolving = false;
      while (true) {

         if (!isSolving) {
            board.draw();
            StdDraw.setPenColor(new Color(15, 76, 129));
            StdDraw.filledRectangle(newPuzzleX + 0.05, newPuzzleY - 0.05, newPuzzleWidth / 2, newPuzzleHeight / 2);
            StdDraw.setPenColor(new Color(31, 160, 239));
            StdDraw.filledRectangle(newPuzzleX, newPuzzleY, newPuzzleWidth / 2, newPuzzleHeight / 2);
            StdDraw.setPenColor(15, 76, 129);
            // Set the font to the desired font
            Font font = new Font("Arial", Font.BOLD, 20); // Change "Arial" to the desired font name
            StdDraw.setFont(font);
            StdDraw.text(newPuzzleX, newPuzzleY, "Randomize");
         }

         StdDraw.setPenColor(15, 76, 129);
         StdDraw.filledRectangle(startSolutionX + 0.05, startSolutionY - 0.05, startSolutionWidth / 2,
               startSolutionHeight / 2);
         StdDraw.setPenColor(31, 160, 239);
         StdDraw.filledRectangle(startSolutionX, startSolutionY, startSolutionWidth / 2, startSolutionHeight / 2);
         StdDraw.setPenColor(15, 76, 129);
         StdDraw.text(startSolutionX, startSolutionY, "Start");

         StdDraw.show();
         StdDraw.pause(100);

         if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
            board.moveRight();
         if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
            board.moveLeft();
         if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
            board.moveUp();
         if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
            board.moveDown();
         if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();

            // Restart button click detection
            if (mouseX >= newPuzzleX - newPuzzleWidth / 2 &&
                  mouseX <= newPuzzleX + newPuzzleWidth / 2 &&
                  mouseY >= newPuzzleY - newPuzzleHeight / 2 &&
                  mouseY <= newPuzzleY + newPuzzleHeight / 2) {
               board = new Board(); // Reset the board to a new random configuration
               isSolving = false;
               isSolverFinished = false;
            }

            // Solve button click detection
            if (!isSolverFinished && mouseX >= startSolutionX - startSolutionWidth / 2 &&
                  mouseX <= startSolutionX + startSolutionWidth / 2 &&
                  mouseY >= startSolutionY - startSolutionHeight / 2 &&
                  mouseY <= startSolutionY + startSolutionHeight / 2) {

               // Inside your main method
               SolutionAlgortihm solverr = new SolutionAlgortihm(board);
               if (!isSolverFinished && mouseX >= startSolutionX - startSolutionWidth / 2 &&
                     mouseX <= startSolutionX + startSolutionWidth / 2 &&
                     mouseY >= startSolutionY - startSolutionHeight / 2 &&
                     mouseY <= startSolutionY + startSolutionHeight / 2) {
                  if (!solverr.isSolvable()) {
                     StdDraw.clear();
                     StdDraw.text(2.5, 2.5, "Unsolvable, please restart.");
                     StdDraw.show();
                     StdDraw.pause(2000);
                     isSolverFinished = true;
                     isSolving = false;
                     continue;
                  }
                  // Initialize the move counter
                  int moveCounter = 0;

                  // Iterate through each solution board
                  for (Board solutionBoard : solverr.returnSolution()) {
                     board = solutionBoard;

                     // Clear the drawing canvas
                     StdDraw.clear();

                     // Draw the current state of the board
                     board.draw();

                     // Display the move counter on the board
                     // Make sure the position and color settings are appropriate for your board's
                     // dimensions
                     StdDraw.setPenColor(StdDraw.BLACK); // Set the pen color to black (or any visible color against
                                                         // your board)
                     StdDraw.text(2.5, 4.5, moveCounter + " moves"); // Adjust the position as necessary
                     // Show the updated drawing
                     StdDraw.show();

                     // Pause for 250 milliseconds to visually distinguish between moves
                     StdDraw.pause(250);

                     // Increment the move counter
                     moveCounter++;
                  }

                  // Move the 'isSolverFinished' flag setting outside the loop
                  isSolverFinished = true;

               

               }

            }

            
         }
      }
   }
}