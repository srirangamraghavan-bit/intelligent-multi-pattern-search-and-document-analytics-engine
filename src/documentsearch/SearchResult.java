package documentsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the result of a pattern matching search operation.
 * Stores match positions, occurrence counts, execution timing, and metadata.
 */
public class SearchResult {
    private String algorithmName;
    private int docId;
    private String docTitle;
    private String pattern;
    private List<Integer> matchingPositions;
    private long executionTimeNs; // Execution time in nanoseconds

    /**
     * Constructor for SearchResult.
     */
    public SearchResult(String algorithmName, int docId, String docTitle, String pattern) {
        this.algorithmName = algorithmName;
        this.docId = docId;
        this.docTitle = docTitle;
        this.pattern = pattern;
        this.matchingPositions = new ArrayList<>();
        this.executionTimeNs = 0;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getDocId() {
        return docId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public String getPattern() {
        return pattern;
    }

    public List<Integer> getMatchingPositions() {
        return matchingPositions;
    }

    public void addPosition(int position) {
        this.matchingPositions.add(position);
    }

    public int getMatchCount() {
        return matchingPositions.size();
    }

    public long getExecutionTimeNs() {
        return executionTimeNs;
    }

    public void setExecutionTimeNs(long executionTimeNs) {
        this.executionTimeNs = executionTimeNs;
    }

    public double getExecutionTimeMs() {
        return executionTimeNs / 1_000_000.0;
    }

    /**
     * Displays a clean formatted summary of the search result.
     */
    public void displaySummary() {
        System.out.println("==================================================");
        System.out.println("SEARCH RESULT SUMMARY");
        System.out.println("==================================================");
        System.out.println("Algorithm Used    : " + algorithmName);
        System.out.println("Document ID       : " + docId);
        System.out.println("Document Title    : " + docTitle);
        System.out.println("Search Pattern    : \"" + pattern + "\"");
        System.out.println("Total Matches     : " + getMatchCount());
        System.out.println("Matching Indices  : " + (matchingPositions.isEmpty() ? "None" : matchingPositions.toString()));
        System.out.printf("Execution Time    : %.4f ms (%d ns)\n", getExecutionTimeMs(), executionTimeNs);
        System.out.println("==================================================");
    }
}
