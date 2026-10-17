package documentsearch;

/**
 * Rabin-Karp Pattern Matching Algorithm.
 * 
 * Concept:
 * Uses a rolling hash function to convert string pattern and text windows into numerical hash values.
 * When hashes match, exact character verification is performed to handle hash collisions.
 * 
 * Expected Time Complexity: O(n + m)
 * Worst Case Time Complexity: O(n * m) (when many hash collisions occur)
 */
public class RabinKarp {

    // Number of characters in the input alphabet (ASCII)
    private static final int d = 256;
    // A prime number used for modulo arithmetic to minimize hash collisions
    private static final int q = 101;

    /**
     * Performs Rabin-Karp pattern matching on the given document content.
     * 
     * @param doc     The Document object to search within
     * @param pattern The keyword or phrase pattern to search for
     * @return SearchResult containing matching positions and metrics
     */
    public static SearchResult search(Document doc, String pattern) {
        SearchResult result = new SearchResult("Rabin-Karp", doc.getId(), doc.getTitle(), pattern);

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

        int pHash = 0; // Hash value for pattern
        int tHash = 0; // Hash value for text window
        int h = 1;

        // The value of h would be "pow(d, m-1) % q"
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }

        // Calculate initial hash value of pattern and first window of text
        for (int i = 0; i < m; i++) {
            pHash = (d * pHash + pattern.charAt(i)) % q;
            tHash = (d * tHash + text.charAt(i)) % q;
        }

        // Slide pattern over text one by one
        for (int i = 0; i <= n - m; i++) {
            // Check if hash values match
            if (pHash == tHash) {
                // Perform character-by-character verification to confirm match (resolves collisions)
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    result.addPosition(i);
                }
            }

            // Calculate hash value for next window of text:
            // Remove leading digit, add trailing digit
            if (i < n - m) {
                tHash = (d * (tHash - text.charAt(i) * h) + text.charAt(i + m)) % q;

                // We might get negative value of tHash, converting it to positive
                if (tHash < 0) {
                    tHash = (tHash + q);
                }
            }
        }

        long endTime = System.nanoTime();
        result.setExecutionTimeNs(endTime - startTime);
        return result;
    }
}
