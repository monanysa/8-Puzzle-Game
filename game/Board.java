package game; // The Board class is in the game package

import edu.princeton.cs.algs4.StdDraw; // used for drawing the board 

import java.awt.Color; // used for coloring the board
import java.awt.Point; // used for the positions of the tiles and the empty cell
import java.util.ArrayList;
import java.util.List;

// A class that is used for modeling the board in the 8 puzzle.
public class Board {
   // Data fields: the class variables (actually constants here)
   // --------------------------------------------------------------------------
   // the background color used for the empty cell on the board
   private static final Color backgroundColor = new Color(145, 234, 255);
   // the color used for drawing the boundaries around the board
   private static final Color boxColor = new Color(31, 160, 239);
   // the line thickness value for the boundaries around the board
   // (it is twice the value used for the tiles as only half of it is visible)
   private static final double lineThickness = 0.02;

   // Data fields: the instance variables
   // --------------------------------------------------------------------------
   // a matrix to store the tiles on the board in their current configuration
   private Tile[][] tiles = new Tile[3][3];
   // the row and the column indexes of the empty cell
   private int emptyCellRow, emptyCellCol;

   // The default constructor creates a random board
   // --------------------------------------------------------------------------

   private Tile[][] copyfromDefaulTiles(Tile[][] defaulTile) {
      Tile[][] copy = new Tile[defaulTile.length][];
      for (int i = 0; i < defaulTile.length; i++) {
         copy[i] = defaulTile[i].clone();
      }
      return copy;
   }

   private void swapTiles(Tile[][] tiles, int r1, int c1, int r2, int c2) {
      Tile temp = tiles[r1][c1];
      tiles[r1][c1] = tiles[r2][c2];
      tiles[r2][c2] = temp;
   }

   public Board(Tile[][] tiles) {
      this.tiles = copyfromDefaulTiles(tiles);
   }

   public Board() {
      // create an array that contains each number from 0 to 8
      int[] numbers = new int[9];
      for (int i = 0; i < 9; i++)
         numbers[i] = i;
      // randomly shuffle the numbers in the array by using the randomShuffling
      // method defined below
      randomShuffling(numbers);

      // create the tiles and the empty cell on the board by using the randomly
      // shuffled numbers from 0 to 8 and store them in the tile matrix
      int arrayIndex = 0; // the index of the current number in the numbers array
      // for each tile in the tile matrix
      for (int row = 0; row < 3; row++)
         for (int col = 0; col < 3; col++) {
            // create a tile if the current value in the numbers array is not 0
            if (numbers[arrayIndex] != 0)
               // create a tile with the current number and assign this tile to
               // the current cell of the tile matrix
               tiles[row][col] = new Tile(numbers[arrayIndex]);
            // otherwise, this is an empty cell
            else {
               // assign the row and the column indexes of the empty cell
               emptyCellRow = row;
               emptyCellCol = col;
            }
            // increase the array index by 1
            arrayIndex++;
         }
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            if (tiles[row][col] != null) {
               builder.append(tiles[row][col].getNumber());
            } else {
               builder.append(0); // print 0 for the empty tile
            }
            builder.append(' ');
         }
         builder.append('\n'); // new line after each row
      }
      return builder.toString();

   }

   public Board(Board other) {
      // This constructor will create a new Board object with the same state as
      // 'other'
      this.tiles = new Tile[3][3];
      this.emptyCellRow = other.emptyCellRow;
      this.emptyCellCol = other.emptyCellCol;

      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            if (other.tiles[row][col] != null) {
               this.tiles[row][col] = new Tile(other.tiles[row][col].getNumber());
            }
         }
      }
   }

   // The method(s) of the Board class
   // --------------------------------------------------------------------------
   // An inner method that randomly reorders the elements in a given int array.
   private void randomShuffling(int[] array) {
      int inversions;
      do {
         // Perform the shuffling
         for (int i = 0; i < array.length; i++) {
            int randIndex = (int) (Math.random() * array.length);
            int temp = array[i];
            array[i] = array[randIndex];
            array[randIndex] = temp;
         }
         inversions = countInversions(array);
      } while (inversions % 2 != 0);
   }

   private int countInversions(int[] array) {
      int inversions = 0;
      for (int i = 0; i < array.length - 1; i++) {
         for (int j = i + 1; j < array.length; j++) {
            if (array[i] > array[j] && array[i] != 0 && array[j] != 0) {
               inversions++;
            }
         }
      }
      return inversions;
   }

   // A method for moving the empty cell right
   public boolean moveRight() {
      // the empty cell cannot go right if it is already at the rightmost column
      if (emptyCellCol == 2)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile on its right
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol + 1];
      tiles[emptyCellRow][emptyCellCol + 1] = null;
      // update the column index of the empty cell
      emptyCellCol++;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for moving the empty cell left
   public boolean moveLeft() {
      // the empty cell cannot go left if it is already at the leftmost column
      if (emptyCellCol == 0)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile on its left
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol - 1];
      tiles[emptyCellRow][emptyCellCol - 1] = null;
      // update the column index of the empty cell
      emptyCellCol--;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for moving the empty cell up
   public boolean moveUp() {
      // the empty cell cannot go up if it is already at the topmost row
      if (emptyCellRow == 0)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile above it
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow - 1][emptyCellCol];
      tiles[emptyCellRow - 1][emptyCellCol] = null;
      // update the row index of the empty cell
      emptyCellRow--;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for moving the empty cell down
   public boolean moveDown() {
      // the empty cell cannot go down if it is already at the bottommost row
      if (emptyCellRow == 2)
         return false; // return false as the empty cell cannot be moved
      // replace the empty cell with the tile below it
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow + 1][emptyCellCol];
      tiles[emptyCellRow + 1][emptyCellCol] = null;
      // update the row index of the empty cell
      emptyCellRow++;
      // return true as the empty cell is moved successfully
      return true;
   }

   // A method for drawing the board by using the StdDraw library
   public void draw() {
      // clear the drawing canvas using the background color
      StdDraw.clear(backgroundColor);
      // for each tile in the tile matrix
      for (int row = 0; row < 3; row++)
         for (int col = 0; col < 3; col++) {
            // skip the empty cell
            if (tiles[row][col] == null)
               continue;
            Point tilePosition = getTilePosition(row, col);
            // draw the tile centered on its position
            tiles[row][col].draw(tilePosition.x, tilePosition.y);

         }
      // draw the box around the board
      StdDraw.setPenColor(boxColor);
      StdDraw.setPenRadius(lineThickness);
      StdDraw.square(2, 2, 1.5);
      StdDraw.setPenRadius(); // reset pen radius to its default value
   }

   // An inner method that returns the position of the tile on the board
   // with the given row and column indexes
   private Point getTilePosition(int rowIndex, int columnIndex) {
      // convert the indexes to the positions in StdDraw
      int posX = columnIndex + 1, posY = 3 - rowIndex;
      return new Point(posX, posY);
   }

   public void shuffle() {
      throw new UnsupportedOperationException("Unimplemented method 'shuffle'");
   }

   public Iterable<Board> neighbors() {
      List<Board> neighbors = new ArrayList<>();

      int blankRow = -1;
      int blankCol = -1;

      // Find the position of the blank tile.
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (tiles[i][j] == null) {
               blankRow = i;
               blankCol = j;
               break;
            }
         }
      }

      // Generate all valid neighbors.
      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
      for (int[] direction : directions) {
         int newRow = blankRow + direction[0];
         int newCol = blankCol + direction[1];

         if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
            Tile[][] newTiles = copyfromDefaulTiles(tiles);
            swapTiles(newTiles, blankRow, blankCol, newRow, newCol);
            neighbors.add(new Board(newTiles));
         }
      }
      return neighbors;
   }

   public boolean isGoalState() {
      if (tiles[1][1] != null) {
         return false;
      }

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (i == 1 && j == 1)
               continue;

            int expectedValue = i * 3 + j + 1;
            if (i > 1 || (i == 1 && j > 1)) {
               expectedValue -= 1;
            }

            if (tiles[i][j] == null || (int) tiles[i][j].getNumber() != expectedValue) {
               return false;
            }
         }
      }
      return true;
   }

   public boolean isSolvable() {
      int inversions = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = i + 1; j < 9; j++) {
            int currentRow = i / 3;
            int currentCol = i % 3;
            int nextRow = j / 3;
            int nextCol = j % 3;

            if (tiles[currentRow][currentCol] != null && tiles[nextRow][nextCol] != null &&
                  (int) tiles[currentRow][currentCol].getNumber() > (int) tiles[nextRow][nextCol].getNumber()) {
               inversions++;
            }
         }
      }
      return inversions % 2 == 0;
   }

   public int manhattan() {
      int totalDistance = 0;
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            if (tiles[row][col] != null) {
               int value = (int) tiles[row][col].getNumber();
               int targetRow = (value - 1) / 3; // assuming 1-9 are the tile numbers
               int targetCol = (value - 1) % 3;
               totalDistance += Math.abs(row - targetRow) + Math.abs(col - targetCol);
            }
         }
      }
      return totalDistance;
   }
}
