# INTELLIGENT MULTI-PATTERN SEARCH AND DOCUMENT ANALYTICS ENGINE

**Academic Level**: 2nd Year B.Tech (Data Structures and Algorithms / Software Project)  
**Language**: Java (SE 8+)  
**UI Options**: Menu-driven Command Line Interface (`Main.java`) & Swing GUI (`DocumentSearchGUI.java`)  
**IDE Compatibility**: Eclipse IDE (Single-click Import compatible)

---

## 1. Project Overview

The **Intelligent Multi-Pattern Search and Document Analytics Engine** is an integrated software system designed to store, manage, search, index, and analyze text documents using classical and advanced pattern matching algorithms.

The project implements and compares five key algorithmic techniques:
1. **Knuth-Morris-Pratt (KMP)** - Pattern matching via Longest Prefix Suffix (LPS) table ($O(n+m)$ time).
2. **Z Algorithm** - Linear-time pattern matching using concatenated Z-array ($O(n+m)$ time).
3. **Rabin-Karp Algorithm** - Rolling hash pattern search with character collision verification ($O(n+m)$ expected time).
4. **Suffix Array Indexing** - Lexicographically sorted suffix table enabling fast substring retrieval.
5. **LCP (Longest Common Prefix) Array** - Auxiliary indexing structure reducing redundant string comparisons.

---

## 2. Problem Statement

"Existing document search systems frequently rely on simple sequential text scanning or isolated search techniques, which can become inefficient for larger document collections. There is a need to integrate multiple pattern-matching algorithms and advanced indexing techniques into a single system that provides fast document search, relevance-based retrieval, document analytics, and live performance evaluation."

---

## 3. Research Gap & Literature Basis

Based on literature review insights:
- **Traditional Search Systems**: Rely heavily on naive linear scans ($O(n \cdot m)$ time complexity), leading to bottlenecks as document sizes scale.
- **Isolated Algorithm Applications**: Existing educational tools demonstrate algorithms (KMP, Rabin-Karp, Z) in isolation rather than benchmarking them side-by-side within a unified document engine.
- **Lack of Integrated Analytics**: Basic search engines lack embedded document-level analytics (word frequency distributions, keyword density percentages, and metrics).

**Proposed Solution**: An integrated Java engine uniting **KMP + Z Algorithm + Rabin-Karp + Suffix Arrays + LCP Arrays**, combined with empirical execution timing and document analytics.

---

## 4. Project Objectives

1. Implement core pattern-matching algorithms (**KMP, Z Algorithm, Rabin-Karp**).
2. Implement **Suffix Array and LCP Array** indexing structures for text indexing.
3. Provide fast keyword and phrase-based document searching across document collections.
4. Measure and compare practical execution times using high-resolution Java timing (`System.nanoTime()`).
5. Provide text analytics including character count, word count, line count, average word length, and keyword density.
6. Provide dual interface options: a menu-driven CLI (`Main.java`) for viva demonstration and a graphical user interface (`DocumentSearchGUI.java`) for presentation.

---

## 5. Technology Stack

- **Core Programming Language**: Pure Java (No external third-party dependencies required).
- **GUI Framework**: Java Swing (`javax.swing.*`) & AWT.
- **IDE Compatibility**: Built for Eclipse IDE (includes `.project` and `.classpath`).
- **Timing Facility**: `System.nanoTime()` for empirical high-resolution benchmarking.

---

## 6. Project Directory Structure

```
IntelligentMultiPatternSearch/
│
├── .classpath                   # Eclipse classpath configuration
├── .project                     # Eclipse project metadata
├── README.md                    # Project documentation & viva guide
├── PPT_Presentation_Guide.md    # Markdown slide-by-slide PPT reference
├── generate_ppt.py              # Python script generating .pptx presentation file
├── Intelligent_MultiPattern_Search_Presentation.pptx # Generated PPT presentation
│
└── src/
    └── documentsearch/
        ├── Document.java               # Document data model
        ├── SearchResult.java           # Search result and execution metadata encapsulation
        ├── KMP.java                    # Knuth-Morris-Pratt algorithm
        ├── ZAlgorithm.java             # Z Algorithm implementation
        ├── RabinKarp.java              # Rabin-Karp rolling hash search
        ├── SuffixArray.java            # Suffix Array construction & search
        ├── LCPArray.java               # Longest Common Prefix array
        ├── DocumentAnalytics.java      # Document statistics & keyword density analysis
        ├── PerformanceComparison.java  # Empirical benchmark runner
        ├── DocumentManager.java        # In-memory document storage & preloaded samples
        ├── Main.java                   # Console menu-driven interactive application
        └── DocumentSearchGUI.java      # Java Swing graphical user interface
```

---

## 7. Algorithms & Time Complexity Summary

| Algorithm | Preprocessing Time | Search Time Complexity | Space Complexity | Key Feature / Mechanism |
|---|---|---|---|---|
| **KMP** | $O(m)$ | $O(n + m)$ | $O(m)$ | Uses Longest Prefix Suffix (LPS) table to skip character re-checks. |
| **Z Algorithm** | $O(n + m)$ | $O(n + m)$ | $O(n + m)$ | Computes Z-array on `pattern + "$" + text` concatenated string. |
| **Rabin-Karp** | $O(m)$ | $O(n + m)$ expected | $O(1)$ | Uses rolling hash; performs character verification on hash match. |
| **Suffix Array** | $O(n^2 \log n)$ | $O(m \log n)$ | $O(n)$ | Stores lexicographically sorted starting indices of text suffixes. |
| **LCP Array** | $O(n)$ | $O(m + \log n)$ w/ SA | $O(n)$ | Stores longest common prefixes between adjacent sorted suffixes. |

*Note: $n$ = text length, $m$ = pattern length.*

---

## 8. How to Import and Run in Eclipse IDE

### Step 1: Open Eclipse
1. Launch Eclipse IDE.
2. Select your workspace.

### Step 2: Import Project
1. Go to **File -> Import...**
2. Choose **General -> Existing Projects into Workspace** and click **Next**.
3. Select **Select root directory** and browse to `IntelligentMultiPatternSearch`.
4. Click **Finish**.

### Step 3: Run the Program
- **Console Menu (`Main.java`)**: Right-click `Main.java` -> **Run As -> Java Application**.
- **Graphical UI (`DocumentSearchGUI.java`)**: Right-click `DocumentSearchGUI.java` -> **Run As -> Java Application**.

---

## 9. Sample Execution & Expected Output

### Console Menu Interface:
```
==================================================
  INTELLIGENT MULTI-PATTERN SEARCH AND
  DOCUMENT ANALYTICS ENGINE
==================================================

--------------------------------------------------
                MAIN MENU OPTIONS                 
--------------------------------------------------
 1. Add Document
 2. Display All Documents
 3. KMP Pattern Search
 4. Z Algorithm Search
 5. Rabin-Karp Search
 6. Suffix Array Search & Visualization
 7. LCP Array Analysis & Viva Explanation
 8. Compare Search Algorithm Performance
 9. Document Analytics Report
10. Launch Swing GUI (Visual Frontend)
11. Exit
--------------------------------------------------
```

### Benchmark Output (Option 8):
```
========================================================================
ALGORITHM PERFORMANCE COMPARISON BENCHMARK
========================================================================
Document ID    : 1 (Data Structures Overview)
Text Length    : 346 characters
Search Pattern : "Search" (Length: 6)
------------------------------------------------------------------------
Algorithm              | Matches      | Time (ms)        | Time (ns)   
------------------------------------------------------------------------
KMP (Knuth-Morris)     | 1            | 0.0450           | 45000       
Z Algorithm            | 1            | 0.0380           | 38000       
Rabin-Karp             | 1            | 0.0620           | 62000       
Suffix Array Search    | 1            | 0.0870           | 87000       
========================================================================
```

---

## 10. Viva Questions & Answers Guide

**Q1: What is the main advantage of KMP over naive pattern matching?**  
*A: Naive search re-checks characters from scratch upon a mismatch ($O(n \cdot m)$ time). KMP uses the LPS table to shift the pattern to the longest valid prefix, ensuring linear $O(n + m)$ execution time.*

**Q2: How does Rabin-Karp handle hash collisions?**  
*A: When the numerical hash of the pattern matches the numerical hash of the text window (spurious hit), Rabin-Karp performs an explicit character-by-character check to verify if it is an exact match.*

**Q3: What is the role of the Z-array in the Z Algorithm?**  
*A: The Z-array stores the length of the longest substring starting at index $i$ that matches the prefix of the string. By appending `pattern + "$" + text`, any $Z[i] = m$ indicates an exact pattern occurrence.*

**Q4: How does LCP Array assist Suffix Array searching?**  
*A: Suffix Array enables binary search on suffixes. The LCP array tracks shared prefix lengths between adjacent suffixes, avoiding repeated character comparisons during binary search steps.*

---

## 11. Limitations & Future Scope

### Limitations:
- Storage is currently in-memory (`ArrayList`) rather than persistent disk database.
- Suffix array construction uses standard sorting for simplicity ($O(n^2 \log n)$) rather than complex linear-time construction algorithms like SA-IS ($O(n)$).

### Future Scope:
- Integrate inverted file indexing for multi-document full-text retrieval engines.
- Add support for fuzzy pattern matching and regular expressions (Regex).
- Persistent storage integration using SQLite or PostgreSQL.
