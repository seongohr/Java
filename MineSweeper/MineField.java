// Name: Seongoh Ryoo

import java.util.Random;

/**
 MineField
 class with locations of mines for a game.
 This class is mutable, because we sometimes need to change it once it's created.
 mutators: populateMineField, resetEmpty
 includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {

    // <put instance variables here>
    private int mineNums = 0;
    private boolean[][] mineFieldArr;


    /**
     Create a minefield with same dimensions as the given array, and populate it with the mines in the array
     such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
     this minefield will corresponds to the number of 'true' values in mineData.
     * @param mineData  the data for the mines; must have at least one row and one col.
     */
    public MineField(boolean[][] mineData) {
        int numRow = mineData.length;
        int numCol = mineData[0].length;

        //iff have at least one row and one col
        if (numRow > 0 && numCol > 0) {
            mineFieldArr = new boolean[numRow][numCol];

            for (int i = 0; i < numRow; i++) {
                for (int j = 0; j < numCol; j++) {
                    mineFieldArr[i][j] = mineData[i][j];
                    if (hasMine(i, j)) {
                        mineNums++;
                    }
                }
            }
        }
    }


    /**
     Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once
     populateMineField is called on this object).  Until populateMineField is called on such a MineField,
     numMines() will not correspond to the number of mines currently in the MineField.
     @param numRows  number of rows this minefield will have, must be positive
     @param numCols  number of columns this minefield will have, must be positive
     @param numMines   number of mines this minefield will have,  once we populate it.
     PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations).
     */
    public MineField(int numRows, int numCols, int numMines) {
        mineFieldArr = new boolean[numRows][numCols];
        mineNums = numMines;
    }


    /**
     Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
     ensuring that no mine is placed at (row, col).
     @param row the row of the location to avoid placing a mine
     @param col the column of the location to avoid placing a mine
     PRE: inRange(row, col)
     */
    public void populateMineField(int row, int col) {
        assert inRange(row,col);

        Random rand = new Random();
        int nums = numMines(); // number of mines to be populated

        resetEmpty(); // removes current mines

        // execute whenever the number of mines to be populated is left
        while (nums > 0)
        {
            // random (row, col) within the mineFieldArr
            int randRow = rand.nextInt(numRows());
            int randCol = rand.nextInt(numCols());

            //put the mine at (randRow, randCol) iff no mine there and not at (row, col)
            if (! hasMine(randRow, randCol)){
                if ( !(randRow == row && randCol == col)){
                    mineFieldArr[randRow][randCol] = true;
                    nums--;
                }

            }
        }
    }


    /**
     Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
     Thus, after this call, the actual number of mines in the minefield does not match numMines().
     Note: This is the state the minefield is in at the beginning of a game.
     */
    public void resetEmpty() {
        for (int i = 0; i < numRows(); i++){
            for (int j = 0; j < numCols(); j++){
                mineFieldArr[i][j] = false;
            }
        }
    }


    /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
     */
    public int numAdjacentMines(int row, int col) {
        assert inRange(row,col);

        int adjacentMine = 0;
        int[] rowRange = ranges(row, numRows()); //mine search range for row, rowRange[0] : start, rowRange[1] : end
        int[] colRange = ranges(col, numCols());//mine search range for col, colRange[0] : start, colRange[1] : end

        if ( ! inRange(row, col) ){
            return 0;
        }
        // count adjacent numeber of mine
        for ( int i = rowRange[0]; i <= rowRange[1]; i++ ){
            for ( int j = colRange[0]; j <= colRange[1]; j++ ){
                if ( !(i == row && j == col)){
                    if ( hasMine(i, j) ){
                        adjacentMine++;
                    }
                }
            }
        }
        return adjacentMine;
    }


    /**
     Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
     start from 0.
     @param row  row of the location to consider
     @param col  column of the location to consider
     @return whether (row, col) is a valid field location
     */
    public boolean inRange(int row, int col) {
        if (row >= 0 && row < numRows() && col >= 0 && col < numCols())
        {
            return true;
        }
        return false;
    }


    /**
     Returns the number of rows in the field.
     @return number of rows in the field
     */
    public int numRows() {
        return mineFieldArr.length;
    }


    /**
     Returns the number of rows in the field.
     @return number of rows in the field
     */
    public int numCols() {
        return mineFieldArr[0].length;
    }


    /**
     Returns whether there is a mine in this square
     @param row  row of the location to check
     @param col  column of the location to check
     @return whether there is a mine in this square
     PRE: inRange(row, col)
     */
    public boolean hasMine(int row, int col) {
        assert inRange(row,col);

        return mineFieldArr[row][col];
    }


    /**
     Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
     some of the time this value does not match the actual number of mines currently on the field.  See doc for that
     constructor, resetEmpty, and populateMineField for more details.
     * @return the number of mines in this minefield
     */
    public int numMines() {
        return mineNums;
    }


    // <put private methods here>

    /**
     * Returns the range of adjacent row or column in an array.
     * range[0] : start, range[1] : end
     * @param p current row or column
     * @param max last row or last column
     * @return the range of adjacent rows or column at (p)
     */
    private int[] ranges(int p, int max) {
        int [] range = {p -1, p + 1};
        if(range[0] < 0) range[0] = 0;
        if(range[1] > max -1) range[1] = max- 1;
        return range;
    }

}
