import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Language implements LanguageInterface {

     private String name;
     private String fileName;
     private Type type;
     private String[] keywords;

     Language(String name, String fileName, Type type) {
          if (name == null || name.isBlank()) {
               throw new IllegalArgumentException("name must not be null or empty.");
          }
          this.name = name;
          if (fileName == null || fileName.isBlank()) {
               throw new IllegalArgumentException("fileName must not be null or empty.");
          }
          this.fileName = fileName;
          if (type == null) {
               throw new IllegalArgumentException("type must not be null.");
          }
          this.type = type;
          readInKeywords();
          sortKwds();
     }

     /**
      *
      * Retrieves the name of the language
      * @return      language name
      */
     public String getName() {
          return name;
     }

     /**
      * Retrieves the filename for keywords
      * @return      filename in which keywords do (or would) reside
      */
     public String getFileName() {
          return fileName;
     }

     /**
      * Retrieves the typical implementation type for the language
      * @return      typical implementation type
      */
     public Type getType() {
          return type;
     }


     private void readInKeywords() {
          File keywordFile = new File(fileName);
          Scanner textScan;
          try {
               textScan = new Scanner(keywordFile);
          } catch (FileNotFoundException e) {
               keywords = new String[0];
               return;
          }
          keywords = new String[textScan.nextInt()];
          textScan.nextLine();
          for (int index = 0; index < keywords.length; index++) {
               keywords[index] = textScan.nextLine().strip();
          }
          textScan.close();
     }

     /**
      * Retrieves a count of keywords for the language
      * @return      count of keywords
      */
     public int getKwdCount() {
          return keywords.length;
     }

     /**
      * Retrieves the keyword at the specified index
      * @param index     index of the keyword; must be a valid index in the range 0 to count - 1
      * @return          keyword at the specified index in the list
      */
     public String getKwd(int index) {
          return keywords[index];
     }

     /**
      * Retrieves the index associated with the specified keyword
      * @param keyword       keyword to search for
      * @return              index of keyword, or -1 if no match is found
      */
     public int findKwd(String keyword) {
          for (int index = 0; index < keywords.length; index++) {
               if (keyword.equalsIgnoreCase(keywords[index])) {
                    return index;
               }
          }
          return -1;
     }

     /**
      * determines the length of the shortest keyword in the keyword list
      * @return  length of the shortest keyword, or -1 if there are no keywords in the list
      */
     public int findShortestKwdLength() {
          if (keywords.length == 0) {
               return -1;
          } else {
               int shortestWordIndex = 0;
               for (int index = 0; index < keywords.length; index++)
                    if (keywords[index].length() < keywords[shortestWordIndex].length()) {
                         shortestWordIndex = index;
                    }
               ;
               return keywords[shortestWordIndex].length();
          }
     }

     /**
      * determines the length of the longest keyword in the keyword list
      * @return  length of the longest keyword, or -1 if there are no keywords in the list
      */
     public int findLongestKwdLength() {
          if (keywords.length == 0) {
               return -1;
          } else {
               int longestWordIndex = 0;
               for (int index = 0; index < keywords.length; index++)
                    if (keywords[index].length() > keywords[longestWordIndex].length()) {
                         longestWordIndex = index;
                    }
               ;
               return keywords[longestWordIndex].length();
          }
     }

     /**
      * Sorts keywords alphabetically (case-sensitive).  Implements an Insertion Sort adapted from the
      *      code given in class (see slides on Sorting).
      */
     public void sortKwds() {
          for (int pass = 1; pass < keywords.length; pass++) {
               String temp = keywords[pass];
               int checkPos = pass - 1;
               while (checkPos >= 0 && temp.compareTo(keywords[checkPos]) < 0) {
                    keywords[checkPos + 1] = keywords[checkPos];
                    checkPos--;
               }
               keywords[checkPos + 1] = temp;
          }
     }

     /**
      * Retrieves a representation of basic language information
      * @return      string representation of the language
      */
     public String toString() {
          return String.format("Language Name: %s, Language type: %s, Number of Keywords: %d",
                                name, type, getKwdCount());
     }
}