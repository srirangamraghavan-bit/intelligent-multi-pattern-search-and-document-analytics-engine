package documentsearch;

/**
 * Represents a text document in the search and analytics engine.
 * Stores document attributes including ID, title, content, and category metadata.
 */
public class Document {
    private int id;
    private String title;
    private String content;
    private String category;

    /**
     * Constructor to initialize a Document object.
     * 
     * @param id       Unique integer identifier
     * @param title    Title of the document
     * @param content  Text content of the document
     * @param category Category or topic of the document
     */
    public Document(int id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = (category != null && !category.trim().isEmpty()) ? category : "General";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCharacterCount() {
        return content != null ? content.length() : 0;
    }

    public int getWordCount() {
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }
        return content.trim().split("\\s+").length;
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s (Category: %s, Chars: %d, Words: %d)",
                id, title, category, getCharacterCount(), getWordCount());
    }
}
