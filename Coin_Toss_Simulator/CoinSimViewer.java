// Name: Seongoh Ryoo

import java.util.Scanner;
import javax.swing.JFrame;


/**
 * CoinSimViewer class
 *
 * Receive the input value of number of coin toss trials from the keyboard.
 * The input value should be > 0 
 *
 * prompt the window with size 800 * 500 which contains the bar graph.
 * 
 */
public class CoinSimViewer
{
   public static void main(String[] args)
   {  
      Scanner in = new Scanner(System.in);
      int numOfTrial = 0;
      int inputCount = 1;
      
      //this loop repeats until the valid input is received ( > 0 )
      do
      {
         if (inputCount != 1) // the comment below shouldn't be printed when receive the value at the first time
         {
            System.out.println("ERROR: Number entered must be greater than 0.");
         }
         
         System.out.print("Enter number of trials: ");
         numOfTrial = in.nextInt();
         
         inputCount++;
      }while (numOfTrial <= 0);
      
      //draw the window
      JFrame window = new JFrame();
      window.setSize(800, 500);
      window.setTitle("CoinSim");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);
      
      CoinSimComponent coinSim = new CoinSimComponent(numOfTrial);
      
      window.add(coinSim);
      
      window.setVisible(true);
      
      
   }
}
      