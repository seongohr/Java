// Name: Seongoh Ryoo

/**
 * class CoinTossSimulator
 * 
 * Simulates trials of repeatedly tossing two coins and allows the user to access the
 * cumulative results.
 * 
 * NOTE: we have provided the public interface for this class.  Do not change
 * the public interface.  You can add private instance variables, constants, 
 * and private methods to the class.  You will also be completing the 
 * implementation of the methods given. 
 * 
 * Invariant: getNumTrials() = getTwoHeads() + getTwoTails() + getHeadTails()
 * 
 */

import java.util.Random; //import to use random 

public class CoinTossSimulator {
   
   private Random generator;
   private int twoHeads;
   private int twoTails;
   private int headTails;
   private int countTrials;
   

   /**
      Creates a coin toss simulator with no trials done yet.
   */
   public CoinTossSimulator() {
      twoHeads = 0;
      twoTails = 0;
      headTails = 0;
      generator = new Random();
   }


   /**
      Runs the simulation for numTrials more trials. Multiple calls to this method
      without a reset() between them *add* these trials to the current simulation.
      
      @param numTrials  number of trials to for simulation; must be >= 1
    */
   public void run(int numTrials) {
      final int head = 1;
      final int tail = 2;
      int coinOne = 0;
      int coinTwo = 0;
   
      //this loop repeats as much as the input value of the number of trials(numTrials)
      while (numTrials >= 1 )
      {
         coinOne = generator.nextInt(2) + 1;
         coinTwo = generator.nextInt(2) + 1;
         
         if (coinOne == head)
         {
            if (coinTwo == head)
            {
               twoHeads++;
            }
            else
            {
               headTails++;
            }
         }
         else
         {
            if (coinTwo == head)
            {
               headTails++;
            }
            else
            {
               twoTails++;
            }
         }
         numTrials--;
      }
      
   }
   
      
   /**
      Get number of trials performed since last reset.
   */
   public int getNumTrials() {
       return twoHeads + twoTails + headTails;
   }


   /**
      Get number of trials that came up two heads since last reset.
   */
   public int getTwoHeads() {
       return twoHeads;
   }


   /**
     Get number of trials that came up two tails since last reset.
   */  
   public int getTwoTails() {
       return twoTails;
   }


   /**
     Get number of trials that came up one head and one tail since last reset.
   */
   public int getHeadTails() {
       return headTails; 
   }


   /**
      Resets the simulation, so that subsequent runs start from 0 trials done.
    */
   public void reset() {
      //countTrials = 0;
      twoHeads = 0;
      twoTails = 0;
      headTails = 0;

   }

}
