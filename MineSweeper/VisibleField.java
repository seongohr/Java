// Name: Seongoh Ryoo

/**
 VisibleField class
 This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
 user can see about the minefield), Client can call getStatus(row, col) for any square.
 It actually has data about the whole current state of the game, including
 the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
 It also has mutators related to moves the player could do (resetGameDisplay(), cycleGuess(), uncover()),
 and changes the game state accordingly.

 It, along with the MineField (accessible in mineField instance variable), forms
 the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
 It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from
 outside this class via the getMineField accessor.
 */
public class VisibleField {
    // ----------------------------------------------------------
    // The following public constants (plus numbers mentioned in comments below) are the possible states of one
    // location (a "square") in the visible field (all are values that can be returned by public method
    // getStatus(row, col)).

    // Covered states (all negative values):
    public static final int COVERED = -1;   // initial value of all squares
    public static final int MINE_GUESS = -2;
    public static final int QUESTION = -3;

    // Uncovered states (all non-negative values):

    // values in the range [0,8] corresponds to number of mines adjacent to this square

    public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
    public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
    public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
    // ----------------------------------------------------------

    // <put instance variables here>
    private MineField mField; // minefield correspond to this visible field
    private int numMinesLeft = -1; 
    private int[][] visible; // visible field arr
    private int numUncover = 0; // number of uncovered squares in visible field
    private boolean gameOver = false;

    /**
     Create a visible field that has the given underlying mineField.
     The initial state will have all the mines covered up, no mines guessed, and the game
     not over.
     @param mineField  the minefield to use for for this VisibleField
     */
    public VisibleField(MineField mineField) {
        int row = mineField.numRows(); //--> which is better mineField or mField?
        int col = mineField.numCols();

        mField = mineField; // assign minefield correspond to this visible field
        visible = new int[row][col]; // create visible field arr
        resetGameDisplay(); // reset to initial state
    }


    /**
     Reset the object to its initial state (see constructor comments), using the same underlying MineField.
     all the mines covered up, no mines guessed, and the game not over.
     */
    public void resetGameDisplay() {
        numMinesLeft = mField.numMines(); // no mines guessed
        gameOver = false; // game not over
        numUncover = 0; // no uncovered mine at initial state

        // all the mines covered up
        for ( int i = 0; i < visible.length; i++){
            for ( int j = 0; j < visible[0].length; j++){
                visible[i][j] = COVERED;
            }
        }
    }


    /**
     Returns a reference to the mineField that this VisibleField "covers"
     @return the minefield
     */
    public MineField getMineField() {
        return mField;
    }


    /**
     get the visible status of the square indicated.
     @param row  row of the square
     @param col  col of the square
     @return the status of the square at location (row, col).  See the public constants at the beginning of the class
     for the possible values that may be returned, and their meanings.
     PRE: getMineField().inRange(row, col)
     */
    public int getStatus(int row, int col) {
        return visible[row][col];
    }


    /**
     Return the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
     or not.  Just gives the user an indication of how many more mines the user might want to guess.  So the value can
     be negative, if they have guessed more than the number of mines in the minefield.
     @return the number of mines left to guess.
     */
    public int numMinesLeft() {
        return numMinesLeft;

    }


    /**
     Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
     changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
     changes it to COVERED again; call on an uncovered square has no effect.
     @param row  row of the square
     @param col  col of the square
     PRE: getMineField().inRange(row, col)
     */
    public void cycleGuess(int row, int col) {
        // a COVERED square changes its status to MINE_GUESS
        if (visible[row][col] == COVERED){
            visible[row][col] = MINE_GUESS;
            numMinesLeft--;
        }
        // a MINE_GUESS square changes it to QUESTION
        else if (visible[row][col] == MINE_GUESS ){
            visible[row][col] = QUESTION;
            numMinesLeft++;
        }
        //a QUESTION square changes it to COVERED again
        else if (visible[row][col] == QUESTION){
            visible[row][col] = COVERED;
        }
    }


    /**
     Uncovers this square and returns false iff you uncover a mine here.
     If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in
     the neighboring area that are also not next to any mines, possibly uncovering a large region.
     Any mine-adjacent squares you reach will also be uncovered, and form
     (possibly along with parts of the edge of the whole field) the boundary of this region.
     Does not uncover, or keep searching through, squares that have the status MINE_GUESS.
     @param row  of the square
     @param col  of the square
     @return false   iff you uncover a mine at (row, col)
     PRE: getMineField().inRange(row, col)
     */
    public boolean uncover(int row, int col) {
        boolean coverMine = !(mField.hasMine(row, col)); // false iff uncover mine at (row, col)

        // when uncover the mine at (row, col)
        if (! coverMine){
            visible[row][col] = EXPLODED_MINE;
            gameOver = true;
            gameLost();
            return false;
        }
        //no mine at (row, col) : open the square
        else {
            int numRow = visible.length;
            int numCol = visible[0].length;
            int totalMines = mField.numMines(); // --> is it better to make constant?

            //proceed to open squares
            coverMine  = uncoverFloodFill(row, col); // always true because no mine at (row, col)
                                                     // -->separate? coverMine = true; uncoverFloodFill(row,col)??
            // if open all the squares except for mines : win
            if (numUncover == (numRow * numCol - totalMines)) {
                gameOver = true;
                gameWin();
            }
        }
        return coverMine;
    }


    /**
     Returns whether the game is over.
     @return whether game over
     */
    public boolean isGameOver() {
        return gameOver;
    }


    /**
     Return whether this square has been uncovered.  (i.e., is in any one of the uncovered states,
     vs. any one of the covered states).
     @param row of the square
     @param col of the square
     @return whether the square is uncovered
     PRE: getMineField().inRange(row, col)
     */
    public boolean isUncovered(int row, int col) {
        return (visible[row][col] >= 0) ;
    }


    // <put private methods here>

    /** Set the visible field in case of loosing game
     *  set incorrect guesses as INCORRECT_GUESS('X) and uncovered mines as MINE(black)
     */
    private void gameLost(){
        int numRow = visible.length;
        int numCol = visible[0].length;

        for (int i = 0; i < numRow; i++){
            for (int j = 0; j < numCol; j++){
                //iff incorrect MINE_GUESS, change status to INCORRECT_GUESS
                if (visible[i][j] == MINE_GUESS){
                    if (! mField.hasMine(i,j)){
                        visible[i][j] = INCORRECT_GUESS;
                    }
                }
                // when has mine and uncovered, change status to MINE(BLACK)
                else if (mField.hasMine(i,j)){
                    if (visible[i][j] == COVERED || visible[i][j] == QUESTION){
                        visible[i][j] = MINE;
                    }
                }
            }
        }
    }


    /** Set the visible field in case of winning game
     *  set all the mines including uncovered mines as MINE_GUESS(yellow)
     */
    private void gameWin(){
        int numRow = visible.length; // the number of row in visible array
        int numCol = visible[0].length; // the number of column in visible arry

        for (int i = 0; i < numRow; i++){
            for (int j = 0; j < numCol; j++){
                // change all the square with mine to MINE_GUESS (yellow)
                if (mField.hasMine(i,j)){
                    visible[i][j] = MINE_GUESS;
                }
            }
        }
    }


    /**
     * uncover helper method
     Uncovers this square and returns true if you complete.
     If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in
     the neighboring area that are also not next to any mines, possibly uncovering a large region.
     Any mine-adjacent squares you reach will also be uncovered, and form
     (possibly along with parts of the edge of the whole field) the boundary of this region.
     Does not uncover, or keep searching through, squares that have the status MINE_GUESS.
     @param row  of the square
     @param col  of the square
     @return true  iff you complete
     PRE: getMineField().inRange(row, col)
     */
    private boolean uncoverFloodFill( int row, int col){
        int lowestRow = 0; // the first row in visible array
        int biggestRow = visible.length - 1; //the last row in visible array
        int lowestCol = 0; // the first column in visible array
        int biggestCol = visible[0].length - 1; // the last column in visible array
        int noMine = 0;
        int adjacentMine = mField.numAdjacentMines(row, col); // number of adjacentMine
        boolean isMine = mField.hasMine(row, col); // iff mine at (row, col)
        boolean complete = false; // --> is it better to use instead of true??

        // (row, col) already uncovered : stop
        if ( isUncovered(row, col) ) { return true;}
        //iff mine at (row, col) : stop
        else if ( isMine ) { return true; }
        else {
            // iff the status at this square is no MINE_GUESS : open
            if (visible[row][col] != MINE_GUESS){
                visible[row][col] = adjacentMine;
                numUncover++;
            }
            // stop when adjacent Mine at (row, col)
            if ( adjacentMine > noMine ) {
                return true;
            }
            //not mine, not adjacent to mines
            else if (adjacentMine == noMine ) {
                // first row of visible array
                if (row != lowestRow){
                    if ( col != lowestCol) {
                        uncoverFloodFill(row - 1, col - 1);
                    }
                    if ( col != biggestCol) {
                        uncoverFloodFill(row - 1, col + 1);
                    }
                    uncoverFloodFill(row - 1, col);
                }
                // the last row of visible array
                if ( row != biggestRow) {
                    if ( col != lowestCol) {
                        uncoverFloodFill(row + 1, col - 1);
                    }
                    if ( col != biggestCol) {
                        uncoverFloodFill(row + 1, col + 1);
                    }
                    uncoverFloodFill(row + 1, col);
                }
                //0 < row of visible array < the end of the row
                if ( col != lowestCol) {
                    uncoverFloodFill(row, col - 1);
                }
                if ( col != biggestCol) {
                    uncoverFloodFill(row, col + 1);
                }
            }
        }
        return true;
    }
    

}




