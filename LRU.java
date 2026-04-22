/**
 * CS 4310 - Operating Systems
 * Project #2 - Page Replacement Algorithms
 *
 * LRU.java - Least Recently Used Algorithm
 * For this algorithm, we use a linked list to maintain the order of page usage.
 * When evicting, we will remove the least recently used page (the one at the back of the list).
 *
 * Description:
 *   Simulates the LRU page replacement algorithm.
 *   Reads 50 reference strings from TestingData.txt,
 *   runs each with frame sizes 3, 4, 5, and 6,
 *   prints a detailed frame table for each string,
 *   and reports the average page faults per frame size.
 */

import java.io.*;
import java.util.*;

public class LRU {

    /**
     * Runs LRU page replacement on a single reference string.
     * Prints the frame state at each step and marks page faults.
     *
     * @param refString  array of page references
     * @param numFrames  number of available page frames
     * @return           total number of page faults
     */
    public static int lru(int[] refString, int numFrames) {
        // LinkedList: front = most recently used, back = least recently used
        LinkedList<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        // Print header row (reference string)
        System.out.print("Ref String: ");
        for (int page : refString) System.out.printf("%3d", page);
        System.out.println();

        // Track frame state at each step for display
        int[][] frameStates = new int[numFrames][refString.length];
        boolean[] faults = new boolean[refString.length];

        // Initialize frame states to -1 (empty)
        for (int[] row : frameStates) Arrays.fill(row, -1);

        for (int i = 0; i < refString.length; i++) {
            int page = refString[i];

            if (!frames.contains(page)) {
                pageFaults++;
                faults[i] = true;

                if (frames.size() == numFrames) {
                    frames.removeLast(); // remove least recently used
                }
                frames.addFirst(page); // add as most recently used
            } else {
                // Move to front (most recently used)
                frames.remove((Integer) page);
                frames.addFirst(page);
            }

            // Capture current frame state
            for (int f = 0; f < numFrames; f++) {
                if (f < frames.size()) frameStates[f][i] = frames.get(f);
                else frameStates[f][i] = -1;
            }
        }

        // Print frame rows
        for (int f = 0; f < numFrames; f++) {
            System.out.printf("Frame  %d  : ", f + 1);
            for (int i = 0; i < refString.length; i++) {
                if (frameStates[f][i] == -1) System.out.printf("%3s", " ");
                else System.out.printf("%3d", frameStates[f][i]);
            }
            System.out.println();
        }

        // Print page fault row
        System.out.print("Page Fault: ");
        for (boolean fault : faults) {
            System.out.printf("%3s", fault ? "F" : " ");
        }
        System.out.println();
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println();

        return pageFaults;
    }

    public static void main(String[] args) {
        String filename = "TestingData.txt";
        int[] frameSizes = {3, 4, 5, 6};
        List<String> lines = new ArrayList<>();

        // Read reference strings from file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
            return;
        }

        System.out.println("===========================================");
        System.out.println("   LRU Page Replacement Algorithm");
        System.out.println("===========================================\n");

        // For each frame size, run all 50 strings and compute average
        for (int numFrames : frameSizes) {
            System.out.println("===========================================");
            System.out.println("   Frame Size: " + numFrames);
            System.out.println("===========================================");

            int totalFaults = 0;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                int[] refString = new int[line.length()];
                for (int j = 0; j < line.length(); j++) {
                    refString[j] = Character.getNumericValue(line.charAt(j));
                }

                System.out.println("--- Reference String #" + (i + 1) + " ---");
                totalFaults += lru(refString, numFrames);
            }

            double average = (double) totalFaults / lines.size();
            System.out.printf("Average Page Faults (Frame Size %d): %.2f%n%n", numFrames, average);
        }
    }
}