// Name: Seongoh Ryoo

import java.util.Scanner;
import java.util.ArrayList;

/**
 * BulgarianSolitaireSimulator class
 *
 * Call the methods simulating BugarianSolitaire and print the simulation result. 
 * Performing two modes by Command-line argument 
 * -u Prompts for the initial configuration from the user, instead of generating a random configuration.
 * -s Stops between every round of the game. The game only continues when the user hits enter (a.k.a., return).
 * 
 */

public class BulgarianSolitaireSimulator{

    public static void main(String[] args) {

        boolean singleStep = false;
        boolean userConfig = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-u")) {
                userConfig = true;
            }
            else if (args[i].equals("-s")) {
                singleStep = true;
            }
        }

        // <add code here>
        String validInput = "";
        SolitaireBoard simSolitaire;

        if (userConfig == true) { // argument -u : get the input from the user(consol)
           simSolitaire = new SolitaireBoard();
           int totalCard = simSolitaire.CARD_TOTAL;
           ArrayList<Integer> inputValuesArrList = new ArrayList<Integer>();
                      
           userInputMessage(totalCard);
            
           Scanner in = new Scanner(System.in);

           while(in.hasNextLine()) {
                String line = in.nextLine();
                validInput = userInput(line, totalCard);
                if(validInput != ""){
                    inputValuesArrList = stringToArrayList(validInput);
                    break;
                }
            }
            simSolitaire = new SolitaireBoard(inputValuesArrList);
            initialConfig(simSolitaire);
        }
        else {// randomly initialize the configuration
            simSolitaire = new SolitaireBoard();
            randomInput(simSolitaire);
        }
        if(singleStep == true)   {   playOne(simSolitaire);}//argument -s : only continues when the user hits enter
        else                     {    playByEnd(simSolitaire); }//continues by the end of the simulation
    }




    // <add private static methods here>

    /**
    check the user's input and return the result as a string
    if user's input is valid : return a set of user's input
    if not valid : return ""
    
    valid intput condition: 
    the sum of total integer input == total number of cards which should be satisfied
    0 < each integer input <= total number of cards
    no character
    
    @param line       a set of user's input in a string 
    @param cardTotal  total number of card(invariant)
    
   */ 
    private static String userInput(String line, int cardTotal) {
        final int MIN = 0;
        
        int sum = 0;
        String newInput = "";
        Scanner lineScanner = new Scanner(line);

        while (lineScanner.hasNext()) {
            if (lineScanner.hasNextInt()) {
                int nextInt = lineScanner.nextInt();

                if (nextInt > MIN && nextInt <= cardTotal) {// check 0 < each int in a user input <= total number of card
                    sum += nextInt;
                    if ( sum <= cardTotal ) {   newInput += Integer.toString(nextInt) + " ";   }
                    else {
                        errorMessage(cardTotal);
                        return "";
                    }
                }
                else {
                    errorMessage(cardTotal);
                    return "";
                }
            }
            else {//in case of type double or String : no int
                errorMessage(cardTotal);
                return "";
            }
        }
        if (sum == cardTotal) {   return newInput;   }// check total number of input number == total number of card which should be satisfied
        else {
            errorMessage(cardTotal);
            return "";
        }
    }
   
    /**
    Convert each integer in a string and store in an ArrayList
    @param input  a valid set of integer in a string
  
   */ 
    private static ArrayList<Integer> stringToArrayList(String input) {
        Scanner lineScanner = new Scanner(input);
        ArrayList<Integer> userInputs = new ArrayList<Integer>();

        while(lineScanner.hasNextInt())
        {
            userInputs.add(lineScanner.nextInt());
        }

        return userInputs;
    }

    /**
    Initialize SolitaireBoard with randomly generated numbers and print the result
    @param simRandom  SolitaireBoard object 
  
   */ 
    private static void randomInput(SolitaireBoard simRandom) {
        initialConfig(simRandom);

    }
   
    /**
    Run one simulation when the user hits enter(return).
    @param simOne  SolitaireBoard object 

   */ 
    private static void playOne(SolitaireBoard simOne) {
        int count = 1;
        boolean done = false;

        while (done == false)
        {
            System.out.print("<Type return to continue>");
            Scanner enter = new Scanner(System.in);
            String pressReturn = enter.nextLine();

            done = simOne.isDone();

            if (pressReturn.isEmpty())
            {
                playAndPrint(simOne, ++count);
            }

        }
    }

    /**
    Implementing simulation by the end(until isDone()) and print the results of each simulation.
    @param simAtOnce  SolitaireBoard object
  
   */ 
    private static void playByEnd(SolitaireBoard simAtOnce) {
        int count = 1;
        boolean end = false;

        while (! end)
        {
            end = simAtOnce.isDone();
            playAndPrint(simAtOnce, ++count);
        }

    }

    /**
    Run one simulation and print the result
    @param simOneByOne  SolitaireBoard object
    @param count  the number trials
  
   */ 
    private static void playAndPrint(SolitaireBoard simOneByOne, int count) {
        if (simOneByOne.isDone()) {
            System.out.println("Done!");
        }
        else {
            simOneByOne.playRound();//call method for running one simulation
            printRound(simOneByOne, count); //call method for print
        }
    }

    /**
    Print the the result including initial configuration and the result of one trial 
    in case of initial value != isDone() after SolitairedBoard is constructed.
    @param initConfig    SolitairedBoard object which is constructed
   */ 
    private static void initialConfig(SolitaireBoard initConfig) {
        System.out.println("Initial configuration: " + initConfig.configString());

        if (! (initConfig.isDone()))
        {
            playAndPrint(initConfig, 1);
        }
    }
   
    /**
    Prompt the message in user input mode (-u)
    @param cardTotal  totalCard total number of card should be satisfied(invariant)
  
   */ 
    private static void userInputMessage(int cardTotal) {
        System.out.println("Number of total cards is " + cardTotal);
        System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
        System.out.println("Please enter a space-separated list of positive integers followed by newline:");
    }

    /**
    Print the error message when the input is not valid.    
    @param totalCard    total number of card should be satisfied(invariant)
    
   */ 
    private static void errorMessage(int totalCard) {
        System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " + totalCard);
        System.out.println("Please enter a space-separated list of positive integers followed by newline:");
    }

    /**
    Print the result of each simulation.
    @param resultRound  SolitaireBoard object
    @param count        the number of trials 
    
   */ 
    private static void printRound(SolitaireBoard resultRound, int count) {
        System.out.println("[" + count + "] Current configuration: " + resultRound.configString());
    }
}











