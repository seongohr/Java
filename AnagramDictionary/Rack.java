// Name: Seongoh Ryoo

import java.util.ArrayList;
import java.util.Arrays;


/**
 A Rack of Scrabble tiles
 */

public class Rack {
    private int[] numOfMult;

    /**
     Finds all subsets of the multiset starting at position k in unique and mult.
     unique and mult describe a multiset such that mult[i] is the multiplicity of the char
     unique.charAt(i).
     PRE: mult.length must be at least as big as unique.length()
     0 <= k <= unique.length()
     @param unique a string of unique letters
     @param mult the multiplicity of each letter from unique.
     @param k the smallest index of unique and mult to consider.
     @return all subsets of the indicated multiset
     @author Claire Bono
     */
    private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
        ArrayList<String> allCombos = new ArrayList<>();

        if (k == unique.length()) {  // multiset is empty
            allCombos.add("");
            return allCombos;
        }

        // get all subsets of the multiset without the first unique char
        ArrayList<String> restCombos = allSubsets(unique, mult, k+1);

        // prepend all possible numbers of the first char (i.e., the one at position k)
        // to the front of each string in restCombos.  Suppose that char is 'a'...

        String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
        for (int n = 0; n <= mult[k]; n++) {
            for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets
                // we found in the recursive call
                // create and add a new string with n 'a's in front of that subset
                allCombos.add(firstPart + restCombos.get(i));
            }
            firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
        }

        return allCombos;
    }

    /**
     * Returns all sub-set of the input string (input)
     * @param input string to find all sub-set
     * @return all subset of the input string
     */
    public ArrayList<String> getAllSubSet(String input) {
        String unique = countUniqueChar(input);
        return allSubsets(unique, numOfMult, 0);
    }

    /**
     sort string by alphabetical order
     @param in string to sort
     */
    private char[] sortString(String in) {
        char[] temp = in.toCharArray();
        Arrays.sort(temp);
        return temp;
    }

    /**
     * convert an arraylist to an array
     * @param counts arraylist to convert to array
     * @return array
     */
    private int[] toArray(ArrayList<Integer> counts){
        int[] multNums = new int[counts.size()];
        int multIndex = 0;
        for(Integer element: counts) {
            multNums[multIndex] = element;
            multIndex++;
        }
        return multNums;
    }

    /**
     * Extract unique letters and count the number of a letter's occurrence from the string input
     * @param input string to process
     * @return a string with unique letters
     */
    private String countUniqueChar(String input) {
        //sort by ascending alphabetical order
        char[] stringIn = sortString(input);

        String unique = Character.toString(stringIn[0]);
        ArrayList<Integer> charCount = new ArrayList<>();;
        char temp = stringIn[0];
        int count = 1;

        //create unique letter set of string and count the occurence of each letter
        for(int i = 1; i < stringIn.length; i++) {
            if ( stringIn[i] == temp) {
                count++;
            }
            else {
                charCount.add(count);
                temp = stringIn[i];
                unique += temp;
                count = 1;
            }
        }
        charCount.add(count);

        //arraylist to array
        numOfMult = toArray(charCount);
        return unique;
    }
}