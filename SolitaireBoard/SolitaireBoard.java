// Name: Seongoh Ryoo

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {

    public static final int NUM_FINAL_PILES = 9;
    // number of piles in a final configuration
    // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)

    public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
    // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
    // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
    // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

    // Note to students: you may not use an ArrayList -- see assgt description for details.


    /**
     Representation invariant:

     0 < the number of cards in each pile as an input <= CARD_TOTAL
     1 <= numPiles <= CARD_TOTAL : index
     the number of cards in each pile doesn't need to be in order
     The sum of the numbers in piles should be CARD_TOTAL
     no 0s between the values between first in an array and the last

     */

    // <add instance variables here>
    private int numPiles;//to track the actual size of valid data in pileArr
    private int[] pileArr;//partially filled array
    private Random generator;
    private final int MIN = 0;


    /**
     Creates a solitaire board with the configuration specified in piles.
     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
     */
    public SolitaireBoard(ArrayList<Integer> piles) {
        pileArr = new int [CARD_TOTAL];
        numPiles = piles.size();

        for (int i = 0; i < piles.size(); i++) {
            pileArr[i] = piles.get(i);
        }

        assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
    }


    /**
     Construct a solitaire board with a random configuration.
     */
    public SolitaireBoard() {
        pileArr = new int[CARD_TOTAL];//at most piles of CARD_TOTAL
        generator = new Random();
        randomInitialization();

        assert isValidSolitaireBoard();
    }


    /**
     Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
     of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
     The old piles that are left will be in the same relative order as before,
     and the new pile will be at the end.
     */
    public void playRound() {
        int i = 0;
        int newPileNum = numPiles;

        if (isDone() == false) {
            while ( i < numPiles ) {
                if (pileArr[i] > MIN + 1) {
                    pileArr[i]--;
                    i++;
                }
                else {   removeZero(i);   }
            }
            addNewPile(newPileNum);
        }
        assert isValidSolitaireBoard();
    }

    /**
     Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
     piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
     */

    public boolean isDone() { 
        int checkArr[] = new int[NUM_FINAL_PILES];

        if (numPiles == NUM_FINAL_PILES) {
            for (int i = 0; i < NUM_FINAL_PILES; i++) {
                if (pileArr[i] > 0 && pileArr[i] <= NUM_FINAL_PILES) {
                    if (checkArr[pileArr[i] - 1] < 1) {
                        checkArr[pileArr[i] - 1]++;
                    }
                    else {
                        assert isValidSolitaireBoard();
                        return false;
                    }
                }
                else {
                    assert isValidSolitaireBoard();
                    return false;
                }
            }
            assert isValidSolitaireBoard();
            return true;
        }
        assert isValidSolitaireBoard();
        return false;
    }


    /**
     Returns current board configuration as a string with the format of
     a space-separated list of numbers with no leading or trailing spaces.
     The numbers represent the number of cards in each non-empty pile.
     */
    public String configString() {
        String allNumbers = Integer.toString(pileArr[0]);
        for (int i = 1; i < numPiles; i++) {
            if ( i < numPiles ) {   allNumbers += " ";   }

            allNumbers += Integer.toString(pileArr[i]);
        }

        assert isValidSolitaireBoard();

        return allNumbers; 
    }


    /**
     Returns true iff the solitaire board data is in a valid state
     (See representation invariant comment for more details.)
     */
    private boolean isValidSolitaireBoard() {

        if (cardSumCalculator() == CARD_TOTAL && numValidator() == true &&
                (numPiles > MIN  && numPiles <= CARD_TOTAL))
        {
            return true;
        }
        return false;

    }


    // <add any additional private methods here>
   
    /**
     Create initial numbers by random generator and stores them to array
     */
    private void randomInitialization(){

        int cardSum = 0; // track the sum of randomly generated card numbers so far

        numPiles = 0;//to track the actual size of valid data in pileArr

        while ( CARD_TOTAL - cardSum != 0) {
            pileArr[numPiles] = generator.nextInt(CARD_TOTAL - cardSum) + 1;

            cardSum += pileArr[numPiles];
            numPiles++;
        }
    }

    /**
     Returns a sum of all numbers in array
     */
    private int cardSumCalculator() {
        int sum = 0;

        for (int i = 0; i < numPiles; i++)
        {
            sum += pileArr[i];
        }

        return sum;
    }

    /**
     Returns boolean results whether each number in the array is valid or not

     */
    private boolean numValidator() {
        for (int i = 0; i < numPiles; i++)
        {
            if (pileArr[i] == MIN || pileArr[i] > CARD_TOTAL) // ! (0 < each number <= CARD_TOTAL) -> invalid
            {
                return false; //invalid
            }
        }
        return true;//valid
    }

    /**
     Removes zeros in an arry and update numPiles

     */
    private void removeZero(int startLoc) {
        for (int j = startLoc ; j < numPiles -1 ; j++) {
            pileArr[j] = pileArr[j+1];
        }
        numPiles--;
    }
    
   /**
     Add a numbers in a new pile after a simulation

     */
    private void addNewPile(int newPileValue) {
        pileArr[numPiles] = newPileValue;
        numPiles++;

        if ( numPiles < newPileValue) {
            for (int i = numPiles; i < newPileValue; i++) {
                pileArr[i] = 0;
            }
        }
    }
}


