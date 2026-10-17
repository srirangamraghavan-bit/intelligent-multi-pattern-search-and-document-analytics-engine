package documentsearch;

/**
 * LCP (Longest Common Prefix) Array.
 * 
 * Concept:
 * Stores the length of the longest common prefix between consecutive suffixes in a sorted Suffix Array.
 * LCP[i] represents the length of the common prefix between SuffixArray[i-1] and SuffixArray[i].
 * 
 * Time Complexity: O(n) using Kasai's Algorithm or O(n^2) suffix comparisons.
 */
public class LCPArray {

    private String text;
    private int[] suffixArray;
    private int[] lcp;
    private int n;

    /**
     * Constructor for LCPArray.
     * 
     * @param text        Document text
     * @param suffixArray Precomputed Suffix Array
     */
    public LCPArray(String text, int[] suffixArray) {
        this.text = text != null ? text : "";
        this.suffixArray = suffixArray;
        this.n = this.text.length();
        this.lcp = new int[n];
        buildLCPArray();
    }

    /**
     * Constructs LCP Array by comparing adjacent suffixes in the sorted Suffix Array.
     */
    private void buildLCPArray() {
        if (n == 0) return;

        lcp[0] = 0; // First element has no predecessor

        for (int i = 1; i < n; i++) {
            int prevSuffixIdx = suffixArray[i - 1];
            int currSuffixIdx = suffixArray[i];

            int lcpLen = 0;
            while (prevSuffixIdx + lcpLen < n && currSuffixIdx + lcpLen < n
                    && text.charAt(prevSuffixIdx + lcpLen) == text.charAt(currSuffixIdx + lcpLen)) {
                lcpLen++;
            }
            lcp[i] = lcpLen;
        }
    }

    public int[] getLcpArray() {
        return lcp;
    }

    /**
     * Displays the Suffix Array along with LCP values for demonstration.
     */
    public void displayLCPArray() {
        System.out.println("==================================================");
        System.out.println("SUFFIX ARRAY WITH LCP (LONGEST COMMON PREFIX) VALUES");
        System.out.println("==================================================");
        System.out.printf("%-6s | %-12s | %-10s | %-25s\n", "Rank", "Suffix Index", "LCP Value", "Suffix String");
        System.out.println("--------------------------------------------------");

        int limit = Math.min(n, 20);
        for (int i = 0; i < limit; i++) {
            int idx = suffixArray[i];
            String suf = text.substring(idx);
            if (suf.length() > 20) {
                suf = suf.substring(0, 17) + "...";
            }
            suf = suf.replace("\n", " ").replace("\r", " ");
            System.out.printf("%-6d | %-12d | %-10d | %-25s\n", i, idx, lcp[i], suf);
        }
        if (n > 20) {
            System.out.printf("... and %d more entries.\n", (n - 20));
        }
        System.out.println("==================================================");
    }

    /**
     * Explains the significance of LCP Array for viva defense.
     */
    public void printExplanation() {
        System.out.println("\n--- VIVA EXPLANATION: LCP ARRAY ---");
        System.out.println("1. LCP[i] stores the length of the longest prefix shared by suffixArray[i-1] and suffixArray[i].");
        System.out.println("2. Purpose: Prevents redundant character comparisons during substring searching.");
        System.out.println("3. Combined with Suffix Array and Binary Search, LCP reduces search time complexity to O(m + log n).");
        System.out.println("-----------------------------------\n");
    }
}
