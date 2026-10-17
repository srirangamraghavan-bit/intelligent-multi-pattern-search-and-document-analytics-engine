package documentsearch;

import java.util.*;

/**
 * Document Analytics Component.
 * 
 * Provides textual analytics for documents, including character count, word count,
 * line count, word frequency distribution, and search keyword density.
 */
public class DocumentAnalytics {

    /**
     * Performs analytics on a document with respect to a search pattern.
     * 
     * @param doc     Target Document
     * @param pattern Keyword pattern (optional, can be empty or null)
     */
    public static void generateReport(Document doc, String pattern) {
        if (doc == null || doc.getContent() == null) {
            System.out.println("Error: Document is empty or invalid.");
            return;
        }

        String content = doc.getContent();
        int charCount = content.length();
        int lineCount = content.isEmpty() ? 0 : content.split("\r\n|\r|\n").length;
        String[] words = content.trim().split("\\s+");
        int wordCount = (content.trim().isEmpty()) ? 0 : words.length;

        double avgWordLength = 0;
        if (wordCount > 0) {
            long totalCharLengthOfWords = 0;
            for (String w : words) {
                totalCharLengthOfWords += w.replaceAll("[^a-zA-Z0-9]", "").length();
            }
            avgWordLength = (double) totalCharLengthOfWords / wordCount;
        }

        // Keyword analysis using KMP algorithm
        int patternOccurrences = 0;
        double patternDensity = 0.0;
        if (pattern != null && !pattern.trim().isEmpty()) {
            SearchResult sr = KMP.search(doc, pattern);
            patternOccurrences = sr.getMatchCount();
            if (wordCount > 0) {
                patternDensity = ((double) patternOccurrences / wordCount) * 100.0;
            }
        }

        // Top 5 most frequent words analysis
        Map<String, Integer> freqMap = new HashMap<>();
        for (String w : words) {
            String cleaned = w.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            if (cleaned.length() > 2) { // filter out minor words like 'is', 'it', 'a', 'in'
                freqMap.put(cleaned, freqMap.getOrDefault(cleaned, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedFreq = new ArrayList<>(freqMap.entrySet());
        sortedFreq.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Displaying formatted report
        System.out.println("==================================================");
        System.out.println("DOCUMENT ANALYTICS REPORT");
        System.out.println("==================================================");
        System.out.println("Document ID          : " + doc.getId());
        System.out.println("Title                : " + doc.getTitle());
        System.out.println("Category             : " + doc.getCategory());
        System.out.println("--------------------------------------------------");
        System.out.println("Total Characters     : " + charCount);
        System.out.println("Total Words          : " + wordCount);
        System.out.println("Total Lines          : " + lineCount);
        System.out.printf("Avg Word Length      : %.2f characters\n", avgWordLength);
        
        if (pattern != null && !pattern.trim().isEmpty()) {
            System.out.println("--------------------------------------------------");
            System.out.println("Analyzed Keyword     : \"" + pattern + "\"");
            System.out.println("Keyword Occurrences  : " + patternOccurrences);
            System.out.printf("Keyword Density      : %.2f%% of total words\n", patternDensity);
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Top Frequent Words   :");
        int topLimit = Math.min(sortedFreq.size(), 5);
        if (topLimit == 0) {
            System.out.println("  (No significant words found)");
        } else {
            for (int i = 0; i < topLimit; i++) {
                Map.Entry<String, Integer> entry = sortedFreq.get(i);
                System.out.printf("  %d. %-15s : %d occurrences\n", (i + 1), entry.getKey(), entry.getValue());
            }
        }
        System.out.println("==================================================");
    }
}
