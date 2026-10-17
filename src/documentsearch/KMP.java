package documentsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Knuth-Morris-Pratt (KMP) Pattern Matching Algorithm.
 * 
 * Concept:
 * KMP avoids re-examining characters that have already been matched by utilizing
 * a precomputed Longest Prefix Suffix (LPS) table.
 * 
 * Time Complexity: O(n + m) where n = text length, m = pattern length.
 * Space Complexity: O(m) for the LPS array.
 */
public class KMP {

    /**
     * Performs KMP pattern matching on the given document content.
     * 
     * @param doc     The Document object to search within
     * @param pattern The keyword or phrase pattern to search for
     * @return SearchResult containing matching positions and metrics
     */
    public static SearchResult search(Document doc, String pattern) {
        SearchResult result = new SearchResult("KMP (Knuth-Morris-Pratt)", doc.getId(), doc.getTitle(), pattern);

        if (pattern == null || pattern.isEmpty() || doc.getContent() == null || doc.getContent().isEmpty()) {
            return result;
        }

        String text = doc.getContent();
        long startTime = System.nanoTime();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            result.setExecutionTimeNs(System.nanoTime() - startTime);
            return result;
        }

        // Step 1: Precompute Longest Prefix Suffix (LPS) array
        int[] lps = computeLPSArray(pattern);

        // Step 2: Search for pattern in text
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == m) {
                // Found pattern match at index (i - j)
                result.addPosition(i - j);
                // Reset j using LPS table to find overlapping matches
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                // Mismatch after j matches
                if (j != 0) {
                    j = lps[j - 1]; // Skip unnecessary comparisons using LPS
                } else {
                    i++;
                }
            }
        }

        long endTime = System.nanoTime();
        result.setExecutionTimeNs(endTime - startTime);
        return result;
    }

    /**
     * Helper method to construct the LPS (Longest Prefix Suffix) array.
     * lps[i] stores the length of the longest proper prefix of pattern[0..i]
     * that is also a suffix of pattern[0..i].
     * 
     * @param pattern Pattern string
     * @return Precomputed LPS array
     */
    public static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0; // length of previous longest prefix suffix
        int i = 1;

        lps[0] = 0; // lps[0] is always 0

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}
