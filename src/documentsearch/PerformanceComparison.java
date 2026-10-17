package documentsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Performance Comparison Module.
 * 
 * Runs KMP, Z Algorithm, Rabin-Karp, and Suffix Array search sequentially
 * on the target document to benchmark actual execution times using System.nanoTime().
 * 
 * Note: Memory consumption measurement in Java standard libraries is subject to garbage collection cycles;
 * hence this benchmark strictly measures empirically observed execution time.
 */
public class PerformanceComparison {

    /**
     * Executes all four algorithms on the document and returns a list of SearchResults.
     * 
     * @param doc     The Document to search
     * @param pattern Keyword pattern
     * @return List of SearchResult objects from each algorithm
     */
    public static List<SearchResult> compareAlgorithms(Document doc, String pattern) {
        List<SearchResult> results = new ArrayList<>();

        if (doc == null || pattern == null || pattern.trim().isEmpty()) {
            System.out.println("Invalid document or empty search pattern.");
            return results;
        }

        // Warmup JVM execution (to prevent cold JIT overhead skewing first algorithm)
        KMP.search(doc, pattern);
        ZAlgorithm.search(doc, pattern);
        RabinKarp.search(doc, pattern);
        
        SuffixArray tempSa = new SuffixArray(doc.getContent());
        tempSa.search(doc, pattern);

        // 1. KMP Algorithm Benchmark
        SearchResult resKMP = KMP.search(doc, pattern);
        results.add(resKMP);

        // 2. Z Algorithm Benchmark
        SearchResult resZ = ZAlgorithm.search(doc, pattern);
        results.add(resZ);

        // 3. Rabin-Karp Benchmark
        SearchResult resRK = RabinKarp.search(doc, pattern);
        results.add(resRK);

        // 4. Suffix Array Search Benchmark
        SuffixArray sa = new SuffixArray(doc.getContent());
        SearchResult resSA = sa.search(doc, pattern);
        results.add(resSA);

        return results;
    }

    /**
     * Prints a comparative benchmark table to the console.
     * 
     * @param doc     Target Document
     * @param pattern Search Pattern
     */
    public static void displayComparisonTable(Document doc, String pattern) {
        List<SearchResult> results = compareAlgorithms(doc, pattern);

        System.out.println("========================================================================");
        System.out.println("ALGORITHM PERFORMANCE COMPARISON BENCHMARK");
        System.out.println("========================================================================");
        System.out.println("Document ID    : " + doc.getId() + " (" + doc.getTitle() + ")");
        System.out.println("Text Length    : " + doc.getCharacterCount() + " characters");
        System.out.println("Search Pattern : \"" + pattern + "\" (Length: " + pattern.length() + ")");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-22s | %-12s | %-16s | %-12s\n", "Algorithm", "Matches", "Time (ms)", "Time (ns)");
        System.out.println("------------------------------------------------------------------------");

        for (SearchResult res : results) {
            System.out.printf("%-22s | %-12d | %-16.4f | %-12d\n",
                    res.getAlgorithmName(),
                    res.getMatchCount(),
                    res.getExecutionTimeMs(),
                    res.getExecutionTimeNs());
        }
        System.out.println("========================================================================");
        System.out.println("Observation Note:");
        System.out.println("- Empirical timings recorded live using System.nanoTime().");
        System.out.println("- Exact timings vary based on CPU load and JVM JIT compilation.");
        System.out.println("========================================================================\n");
    }
}
