import java.util.Random;

/**
 * CS 4310 - Operating Systems
 * Project #2 - Page Replacement Algorithms
 *
 * RandomNumStringGenerator.java - Generates 50 random reference strings of length 30.
 * Each page reference is a random integer from 0 to 7 (inclusive).
 *
 * Description:
 *   Generates and prints 50 random reference strings, each of length 30,
 *   with page references in the range [0, 7].
 *   Then you manually copy these strings into TestingData.txt for use in the page replacement algorithms.
*/

public class RandomNumStringGenerator {
    public static void main(String[] args) {
        Random rand = new Random(4310);
        for (int i = 0; i < 50; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 30; j++) {
                sb.append(rand.nextInt(8)); 
            }
            System.out.println(sb.toString());
        }
    }
}