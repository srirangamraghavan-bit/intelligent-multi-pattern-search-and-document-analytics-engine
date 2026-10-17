package documentsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the collection of documents stored in the system.
 * Supports adding documents, retrieving by ID, displaying documents,
 * and pre-loading initial sample documents for presentation.
 */
public class DocumentManager {

    private List<Document> documents;
    private int nextId;

    public DocumentManager() {
        this.documents = new ArrayList<>();
        this.nextId = 1;
        preloadSampleDocuments();
    }

    /**
     * Pre-loads realistic sample documents so the application is ready
     * for instant demonstration during college project viva.
     */
    private void preloadSampleDocuments() {
        addDocument(new Document(
                nextId++,
                "Data Structures Overview",
                "Data structures are fundamental building blocks in computer science. They allow organizing, "
                + "managing, and storing data efficiently. Common data structures include arrays, linked lists, "
                + "stacks, queues, trees, graphs, suffix arrays, and hash tables. Searching pattern matching algorithms "
                + "like KMP and Z Algorithm operate on textual data structures.",
                "Computer Science"
        ));

        addDocument(new Document(
                nextId++,
                "Artificial Intelligence and Machine Learning",
                "Artificial Intelligence (AI) enables machines to perform tasks requiring human intelligence. "
                + "Machine Learning (ML) is a subset of AI focusing on statistical learning models. Document analytics "
                + "engine uses keyword extraction, pattern search, and text analytics to index massive textual "
                + "corpora for intelligent information retrieval.",
                "Artificial Intelligence"
        ));

        addDocument(new Document(
                nextId++,
                "Algorithms and Complexity Analysis",
                "An algorithm is a step-by-step procedure for solving a computational problem. String pattern matching "
                + "algorithms are evaluated using asymptotic time complexity and space complexity. Knuth-Morris-Pratt (KMP) "
                + "and Z Algorithm achieve linear time O(n + m) performance, while Rabin-Karp relies on rolling hash functions.",
                "Algorithms"
        ));

        addDocument(new Document(
                nextId++,
                "Document Search Engine Architecture",
                "Modern document search engines combine pattern matching algorithms with advanced indexing structures "
                + "such as Suffix Arrays and Longest Common Prefix (LCP) Arrays. By preprocessing documents into suffix arrays, "
                + "keyword search operations achieve logarithmic response times, significantly boosting search performance.",
                "Search Engines"
        ));
    }

    /**
     * Adds a new document to the collection.
     * 
     * @param doc Document object to add
     */
    public void addDocument(Document doc) {
        if (doc != null) {
            // Ensure unique auto-increment ID if needed
            if (getDocumentById(doc.getId()) != null) {
                doc.setId(nextId++);
            } else if (doc.getId() >= nextId) {
                nextId = doc.getId() + 1;
            }
            documents.add(doc);
        }
    }

    /**
     * Creates and adds a new document using title, content, and category.
     */
    public Document createAndAddDocument(String title, String content, String category) {
        Document doc = new Document(nextId++, title, content, category);
        documents.add(doc);
        return doc;
    }

    public List<Document> getAllDocuments() {
        return documents;
    }

    /**
     * Retrieves a document by its integer ID.
     * 
     * @param id Target document ID
     * @return Document object or null if not found
     */
    public Document getDocumentById(int id) {
        for (Document doc : documents) {
            if (doc.getId() == id) {
                return doc;
            }
        }
        return null;
    }

    /**
     * Displays summary of all stored documents.
     */
    public void displayAllDocuments() {
        System.out.println("==================================================");
        System.out.println("STORED DOCUMENTS (" + documents.size() + " total)");
        System.out.println("==================================================");
        if (documents.isEmpty()) {
            System.out.println("No documents available in the system.");
        } else {
            for (Document doc : documents) {
                System.out.println(doc.toString());
                System.out.println("Preview: " + truncateContent(doc.getContent(), 80));
                System.out.println("--------------------------------------------------");
            }
        }
        System.out.println("==================================================\n");
    }

    private String truncateContent(String content, int maxLen) {
        if (content == null) return "";
        content = content.replace("\n", " ").replace("\r", " ");
        if (content.length() <= maxLen) return content;
        return content.substring(0, maxLen - 3) + "...";
    }
}
