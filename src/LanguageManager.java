import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LanguageManager implements LanguageManagerInterface {

    private final Language[] languages;

    public LanguageManager(File languageFile) throws FileNotFoundException {
        if (languageFile == null || languageFile.length() == 0) {
            throw new IllegalArgumentException("languageFile must not be null or blank");
        }
        Scanner languageScan = new Scanner(languageFile);
        languages = new Language[languageScan.nextInt()];
        languageScan.nextLine();
        languageScan.nextLine();
        for (int languageIndex = 0; languageIndex < languages.length; languageIndex++) {
            String[] languageParams = languageScan.nextLine().split(",");
            languages[languageIndex] = new Language(languageParams[0], languageParams[1], Language.Type.valueOf(languageParams[2].toUpperCase()));
        }
        sortLangs();
    }

    /**
     * Returns the number of languages currently managed
     * @return      number of languages
     */
    public int getLanguageCount() {
        return languages.length;
    }

    /**
     * Returns the language at specified index in the list
     * @param index     position of the language
     * @return          language at that position
     */
    public Language getLanguage(int index) {
        return languages[index];
    }

    /**
     * Across languages, finds and returns the length of the shortest keyword.  Ignores languages with no keywords.
     * @return      the length of the shortest keyword
     */
    public int findShortestKwdLength() {
        int shortestKwdIdx = 0;
        while (languages[shortestKwdIdx].getKwdCount() == 0) {
            shortestKwdIdx += 1;
            if (shortestKwdIdx >= languages.length) {
                return -1;
            }
        }
        for (int index = shortestKwdIdx + 1; index < languages.length; index++) {
            if (languages[index].getKwdCount() > 0 &&
                languages[index].findShortestKwdLength() > languages[shortestKwdIdx].findShortestKwdLength()) {
                shortestKwdIdx = index;
            }
        }
        return languages[shortestKwdIdx].findShortestKwdLength();
    }

    /**
     * Across languages, finds and returns the length of the longest keyword.  Ignores languages with no keywords.
     * @return      the length of the longest keyword
     */
    public int findLongestKwdLength() {
            int longestKwdIdx = 0;
            while (languages[longestKwdIdx].getKwdCount() == 0) {
                longestKwdIdx += 1;
                if (longestKwdIdx >= languages.length) {
                    return -1;
                }
            }
            for (int index = longestKwdIdx + 1; index < languages.length; index++) {
                if (languages[index].getKwdCount() > 0 &&
                    languages[index].findLongestKwdLength() > languages[longestKwdIdx].findLongestKwdLength()) {
                    longestKwdIdx = index;
                }
            }
            return languages[longestKwdIdx].findLongestKwdLength();
    }

    /**
     * Across languages, find the language with the fewest keywords and returns its position.
     * Ignores languages with no keywords.  Returns -1 if there are no languages with keywords.
     * @return      the position of the language with the fewest keywords
     */
    public int findLangWithFewestKwds() {
        int fewestKwdsIdx = 0;
        while (languages[fewestKwdsIdx].getKwdCount() == 0) {
            fewestKwdsIdx += 1;
            if (fewestKwdsIdx >= languages.length) {
                return -1;
            }
        }
        for (int index = fewestKwdsIdx + 1; index < languages.length; index++) {
            if (languages[index].getKwdCount() > 0 &&
                languages[index].getKwdCount() < languages[fewestKwdsIdx].getKwdCount()) {
                fewestKwdsIdx = index;
            }
        }
        return fewestKwdsIdx;

    }

    /**
     * Across languages, find the language with the most keywords and returns its position.
     * Ignores languages with no keywords.  Returns -1 if there are no languages with keywords.
     * @return      the position of the language with the most keywords
     */
    public int findLangWithMostKwds() {
        int mostKwdsIdx = 0;
        while (languages[mostKwdsIdx].getKwdCount() == 0) {
            mostKwdsIdx += 1;
            if (mostKwdsIdx >= languages.length) {
                return -1;
            }
        }
        for (int index = mostKwdsIdx + 1; index < languages.length; index++) {
            if (languages[index].getKwdCount() > 0 &&
                languages[index].getKwdCount() > languages[mostKwdsIdx].getKwdCount()) {
                mostKwdsIdx = index;
            }
        }
        return mostKwdsIdx;
    }

    /**
     * Looks for the specified keyword across all languages and returns an array of the positions of those languages.
     * Does not use an ArrayList or other data structure in the process.
     * @param keyword       keyword to search for
     * @return              array of indexes of (only) the languages that contain that keyword, a "right-sized" array
     */

    //read twice
    public int[] findLangKwdMatches(String keyword) {
        int kwdMatches = 0;
        for (Language language : languages) {
            if (language.findKwd(keyword) != -1) {
                kwdMatches += 1;
            }
        }
        int[] kwdMatchIndices = new int[kwdMatches];
        int kwdMatchIndex = 0;
        for (int index = 0; index < languages.length; index++) {
            if (languages[index].findKwd(keyword) != -1) {
                kwdMatchIndices[kwdMatchIndex] = index;
                kwdMatchIndex += 1;
            }
        }
        return kwdMatchIndices;
    }

    /**
     * Looks for languages of the specified type and returns an array of the positions of those languages.
     * Does not use an ArrayList or other data structure in the process.
     * @param type          the type of language to search for
     * @return              array of indexes of (only) the languages that match the specified type, a "right-sized" array
     */
    public int[] findLangsOfType(Language.Type type) {
        int typeMatches = 0;
        for (Language language : languages) {
            if (language.getType() == type) {
                typeMatches += 1;
            }
        }
        int[] typeMatchIndices = new int[typeMatches];
        int typeMatchIndex = 0;
        for (int index = 0; index < languages.length; index++) {
            if (languages[index].getType() == type) {
                typeMatchIndices[typeMatchIndex] = index;
                typeMatchIndex += 1;
            }
        }
        return typeMatchIndices;
    }

    /**
     * Sorts the languages alphabetically (case-sensitive).  Implements an Insertion Sort adapted from the
     *      code given in class (see slides on Sorting).
     */
    public void sortLangs() {
        for (int pass = 1; pass < languages.length; pass++) {
            Language temp = languages[pass];
            int checkPos = pass - 1;
            while (checkPos >= 0 && temp.getName().compareTo(languages[checkPos].getName()) < 0) {
                languages[checkPos + 1] = languages[checkPos];
                checkPos--;
            }
            languages[checkPos + 1] = temp;
        }
    }
}
