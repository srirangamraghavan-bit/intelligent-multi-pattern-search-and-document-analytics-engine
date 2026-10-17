package documentsearch;

/**
 * Z Algorithm Pattern Matching.
 * 
 * Concept:
 * Constructs a Z-array for the concatenated string (Pattern + '$' + Text).
 * Z[i] stores the length of the longest substring starting from index i that matches
 * the prefix of the concatenated string.
 * 
 * Time Complexity: O(n + m) where n = text length, m = pattern length.
 * Space Complexity: O(n + m) for the Z-array.
 */
public class ZAlgorithm {

    /**
     * Performs Z Algorithm pattern matching on the given document content.
     * 
     * @param doc     The Document object to search within
     * @param pattern The keyword or phrase pattern to search for
     * @return SearchResult containing matching positions and metrics
     */
    public static SearchResult search(Document doc, String pattern) {
        SearchResult result = new SearchResult("Z Algorithm", doc.getId(), doc.getTitle(), pattern);

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

        // Step 1: Create concatenated string: Pattern + Separator + Text
        // Using '$' as a special delimiter assuming it does not appear in text
        String concat = pattern + "$" + text;
        int concatLen = concat.length();

        // Step 2: Construct Z-array
        int[] Z = constructZArray(concat);

        // Step 3: Find matches where Z[i] == pattern length
        for (int i = 0; i < concatLen; i++) {
            if (Z[i] == m) {
                // Calculate original text position (offset by pattern length + 1 separator)
                int textPosition = i - (m + 1);
                result.addPosition(textPosition);
            }
        }

        long endTime = System.nanoTime();
        result.setExecutionTimeNs(endTime - startTime);
        return result;
    }

    /**
     * Constructs the Z-array for a given string in linear O(N) time.
     * 
     * @param s Input concatenated string
     * @return Z array containing prefix match lengths
     */
    public static int[] constructZArray(String s) {
        int n = s.length();
        int[] Z = new int[n];

        // [L, R] represents the interval of the prefix match window
        int L = 0, R = 0;

        for (int i = 1; i < n; i++) {
            if (i > R) {
                // Outside current window: reset L and R
                L = R = i;
                while (R < n && s.charAt(R - L) == s.charAt(R)) {
                    R++;
                }
                Z[i] = R - L;
                R--;
            } else {
                // Inside current window [L, R]
                int k = i - L;
                if (Z[k] < R - i + 1) {
                    // Result already computed inside window
                    Z[i] = Z[k];
                } else {
                    // Operate beyond R
                    L = i;
                    while (R < n && s.charAt(R - L) == s.charAt(R)) {
                        R++;
                    }
                    Z[i] = R - L;
                    R--;
                }
            }
        }

        return Z;
    }
}
