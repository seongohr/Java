// Name: Seongoh Ryoo

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Iterator;


/**
 A dictionary of all anagram sets.
 Note: the processing is case-sensitive; so if the dictionary has all lower
 case words, you will likely want any string you test to have all lower case
 letters too, and likewise if the dictionary words are all upper case.
 */

public class AnagramDictionary {
    private Map<String, ArrayList<String>> data;


    /**
     Create an anagram dictionary from the list of words given in the file
     indicated by fileName.
     PRE: The strings in the file are unique.
     @param fileName  the name of the file to read from
     @throws FileNotFoundException  if the file is not found
     */
    public AnagramDictionary(String fileName) throws FileNotFoundException {
        File inFile = new File(fileName);
        try (Scanner in = new Scanner(inFile))
        {
            append(in);
        }
    }


    /**
     Get all anagrams of the given string. This method is case-sensitive.
     E.g. "CARE" and "race" would not be recognized as anagrams.
     @param word string to process
     @return a list of the anagrams of s
     */
    public ArrayList<String> getAnagramsOf(String word) {
        String key = sortInput(word);
        if ( data.containsKey((key))) {
            return data.get(key);
        }
        return null;
    }

    /**
     * Organize the words in the dictionary by the set of letters each one contains
     * @param in dictionary words
     */
    private void append(Scanner in){
        data = new HashMap<String, ArrayList<String>>();
        ArrayList<String> anagrams;
        while (in.hasNext()){
            String current = in.next();
            String temp = sortInput(current);

            if(data.containsKey(temp)) {
                anagrams = data.get(temp);
            }
            else {
                anagrams  = new ArrayList<>();
                data.put(temp, anagrams);
            }
            anagrams.add(current);
        }

    }

    /**
     * Sort the string by ascending alphabetical order
     * @param current string to sort
     * @return alphabetically sorted string input
     */
    private String sortInput(String current) {
        char[] charArray = current.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
}