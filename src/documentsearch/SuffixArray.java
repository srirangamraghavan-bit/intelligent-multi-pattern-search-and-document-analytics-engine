package documentsearch;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Suffix Array Data Structure & Search.
 * 
 * Concept:
 * A Suffix Array is a sorted array of all suffixes of a text string.
 * It enables fast substring search by storing starting positions of sorted suffixes.
 * 
 * Construction: O(n^2 log n) using standard suffix sorting for maximum clarity in academic viva.
 * Search: O(m log n) using suffix comparisons.
 */
public class SuffixArray {

    private String text;
    private Integer[] suffixArray;
    private int n;

    /**
     * Constructs a SuffixArray object for the given text.
     * 
     * @param text Input text document content
     */
    public SuffixArray(String text) {
        this.text = text != null ? text : "";
        this.n = this.text.length();
        this.suffixArray = new Integer[n];
        buildArray();
    }

    /**
     * Builds the suffix array by generating all suffix indices and sorting them lexicographically.
     */
    private void buildArray() {
        for (int i = 0; i < n; i++) {
            suffixArray[i] = i;
        }

        // Sort suffix indices based on their corresponding substring values
        Arrays.sort(suffixArray, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return text.substring(i1).compareTo(text.substring(i2));
            }
        });
    }

    public Integer[] getSuffixArray() {
        return suffixArray;
    }

    public int[] getSuffixArrayAsIntArray() {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = suffixArray[i];
        }
        return arr;
    }

    /**
     * Searches for a pattern using the constructed Suffix Array.
     * 
     * @param doc     Document instance
     * @param pattern Keyword pattern
     * @return SearchResult with occurrences and timing
     */
    public SearchResult search(Document doc, String pattern) {
        SearchResult result = new SearchResult("Suffix Array Search", doc.getId(), doc.getTitle(), pattern);

        if (pattern == null || pattern.isEmpty() || text.isEmpty()) {
            return result;
        }

        long startTime = System.nanoTime();
        int m = pattern.length();

        // Search through the sorted suffixes for occurrences starting with pattern
        for (int i = 0; i < n; i++) {
            int suffixIndex = suffixArray[i];
            if (suffixIndex + m <= n) {
                String sub = text.substring(suffixIndex, suffixIndex + m);
                if (sub.equals(pattern)) {
                    result.addPosition(suffixIndex);
                }
            }
        }

        // Sort matching positions in increasing order of text appearance
        result.getMatchingPositions().sort(Integer::compareTo);

        long endTime = System.nanoTime();
        result.setExecutionTimeNs(endTime - startTime);
        return result;
    }

    /**
     * Displays the Suffix Array in a clean tabular format for demonstration and viva.
     */
    public void displaySuffixArray() {
        System.out.println("==================================================");
        System.out.println("SUFFIX ARRAY VISUALIZATION");
        System.out.println("==================================================");
        System.out.printf("%-6s | %-12s | %-30s\n", "Rank", "Suffix Index", "Suffix String");
        System.out.println("--------------------------------------------------");
        int limit = Math.min(n, 20); // Display up to 20 suffixes for readability
        for (int i = 0; i < limit; i++) {
            int idx = suffixArray[i];
            String suf = text.substring(idx);
            if (suf.length() > 25) {
                suf = suf.substring(0, 22) + "...";
            }
            // Replace newlines for clean printing
            suf = suf.replace("\n", " ").replace("\r", " ");
            System.out.printf("%-6d | %-12d | %-30s\n", i, idx, suf);
        }
        if (n > 20) {
            System.out.printf("... and %d more suffixes.\n", (n - 20));
        }
        System.out.println("==================================================");
    }
}
