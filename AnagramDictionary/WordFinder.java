// Name: Seongoh Ryoo

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

/**
 WordFinder class
 This class controls the program: it gets input from a user, handles error, controls all over the classes
 and print the output.
 Note: the processing is case-sensitive; so if the dictionary has all lower
 case words, you will likely want any string you test to have all lower case
 letters too, and likewise if the dictionary words are all upper case.
 */
public class WordFinder {

    public static void main(String[] args) {
        String fileName = "sowpods.txt";
        try {
            //check and get fileName from command line
            if (args.length > 0) {
                fileName = args[0];
            }
            // organize the dictionary
            AnagramDictionary dictionary = new AnagramDictionary(fileName);

            System.out.println("Type . to quit.");
            Scanner in = new Scanner(System.in);

            //get the input string from the command
            while(true) {
                System.out.print("Rack? ");
                String input = in.next();
                //iff ".", then quit.
                if (input.equals(".")) { break; }

                //get all subsets of the input string
                Rack rack = new Rack();
                ArrayList<String> allSubSet = rack.getAllSubSet(input);

                //look up all subset strings from the dictionary
                Map<String, Integer> result = lookUp(dictionary, allSubSet);

                //calculate the scores of string sets found in the dictionary
                ScoreTable scores = new ScoreTable(result);
                result =  scores.getScore();

                //sort the result by scores of string and ascending alphabetical order
                ArrayList<Map.Entry<String, Integer>> finalResult = new ArrayList<>(result.entrySet());
                Collections.sort(finalResult, new SortbyValue());

                //print result
                System.out.println("We can make "+  finalResult.size() +  " words from \"" + input + "\"");
                reversePrint(finalResult);
            }
        }
        catch (FileNotFoundException exception){
            System.out.println("File not found: " + fileName);
        }
    }


    /**
     * Look up all subset strings in the dictionary
     * @param dic Organized dictionary in which we look up words
     * @param subsets all subsets of the input
     * @return all the sub-words of the user input in the dictionary
     */
    private static Map<String, Integer> lookUp(AnagramDictionary dic, ArrayList<String> subsets) {
        Map<String, Integer> allWords = new HashMap<>();

        for(String word: subsets) {
            if (word != "") {
                ArrayList<String> eachAnagram = dic.getAnagramsOf(word);
                if (eachAnagram != null) {
                    for(String each: eachAnagram) {
                        allWords.put(each, 0);
                    }
                }
            }
        }
        return allWords;
    }

    /**
     * Print Map value and Map key in an arraylist.
     * @param resultFinal the arraylist of a Map to be printed
     */
    private static void reversePrint(ArrayList<Map.Entry<String, Integer>> resultFinal){
        if (resultFinal.size() != 0) {
            System.out.println("All of the words with their scores (sorted by score):");
            for (Map.Entry<String, Integer> list : resultFinal) {
                System.out.println(list.getValue() + ": " + list.getKey());
            }
        }
    }

}

/**
 SortbyValue class.
 Sort the Map by the value : descending order
 If the values are the same, then sort by alphabetical order.
 */
class SortbyValue implements Comparator<Map.Entry<String, Integer>>{
    public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b)
    {
        int compareValue = b.getValue() - a.getValue();
        if (compareValue == 0) {
            return a.getKey().compareTo(b.getKey());
        }
        return compareValue;
    }
}