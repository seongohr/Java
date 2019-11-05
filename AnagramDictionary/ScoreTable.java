// Name: Seongoh Ryoo

import java.util.Map;
import java.util.HashMap;

/**
 Compute a score table of a string inputs.
 Note : Scores are not case-sensitive.
 */

public class ScoreTable {
    private final static int[] SCORE_TABLE =  {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
                                            1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    private String in;
    private Map<String, Integer> resultTable;

    public ScoreTable(Map<String, Integer>allScores){
        resultTable = new HashMap<>(allScores);
    }

    /**
     * Returns a score table of words
     * @return a score table
     */
    public Map<String, Integer> getScore(){
        calcScore();
        return resultTable;
    }

    /**
     * Calculates scores of strings 
     * Note : scores are not case-sensitive
     */
    private void calcScore() {
        String in;
        for (Map.Entry<String, Integer> entry : resultTable.entrySet()) {
            int score = 0;
            in = entry.getKey().toLowerCase();
            for (int i = 0; i < in.length(); i++) {
                score += SCORE_TABLE[in.charAt(i) - 'a'];
            }
            entry.setValue(score);
        }

    }
}
