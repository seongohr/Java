// Name: Seongoh Ryoo

/**
 * CoinTossSimulatorTester class
 *
 * Performs a test for CoinTossSimulator. 
 * It gives the number of trials to CoinTossSimulator
 * and get the results from it and compare the result with expected value
 * to check that CoinTossSimulator is properly working.
 *
 */
public class CoinTossSimulatorTester{
   private int trialNum; //actual trial numbers
   private int twoHeads;
   private int twoTails;
   private int headTails;
   
   /** construct a CoinTossSimulator, 
       run the simulator as much time as the value specified,
       get the results from the simulator and check the simulator is correctly working
   */   
   public static void main(String[] args)
   {
      CoinTossSimulatorTester tester = new CoinTossSimulatorTester();
      CoinTossSimulator simulator = new CoinTossSimulator();
      
      tester.getResults(simulator);
      tester.testPrint("After constructor: ", 0);

      simulator.run(1);
      tester.getResults(simulator);
      tester.testPrint("After run: ", 1);
      
      simulator.run(10);
      tester.getResults(simulator);
      tester.testPrint("After run: ", 11);
      
      simulator.run(100);
      tester.getResults(simulator);
      tester.testPrint("After run: ", 111);
      
      simulator.reset();
      tester.getResults(simulator);
      tester.testPrint("After reset: ", 0);
      
      simulator.run(1000);
      tester.getResults(simulator);
      tester.testPrint("After run: ", 1000);
      
   }
   
   /** This constructor has no initial variables 
       but for using methods here in this class
   */
   public CoinTossSimulatorTester(){

   }
   
   /** get the number of each case of CoinTossSimulator
       and total number of trials
       @param sim CoinTossSimulator object
   */
   private void getResults(CoinTossSimulator sim)
   {
      twoHeads = sim.getTwoHeads();
      twoTails = sim.getTwoTails();
      headTails = sim.getHeadTails();
      trialNum = sim.getNumTrials();
   }
   
   /** prints out the results of the CoinTossSimulator
       @param title specifies the result after which behavior(methods or constructor)
       @param expNum expected number of trials - given value
   */      
   private void testPrint(String title, int expNum)
   {  
      System.out.println(title);
      System.out.print("Number of trials [exp:" + Integer.toString(expNum) + "]: "); 
      System.out.println(trialNum);
      System.out.print("Two-head tosses: ");
      System.out.println(twoHeads);
      System.out.print("Two-tail tosses: ");
      System.out.println(twoTails);
      System.out.print("One-head one-tail tosses: ");
      System.out.println(headTails);
      System.out.print("Tosses add up correctly? ");
      System.out.println(expNum == trialNum);
      System.out.println();
   }
      
   

}
