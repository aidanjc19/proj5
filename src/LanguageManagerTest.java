import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;


class LanguageManagerTest {

    public static LanguageManager languageManager;
    public static LanguageManager languageManagerTest;

    @BeforeAll
    public static void initialize() {
        File languageFile = new File("languages.txt");
        try {
            languageManager = new LanguageManager(languageFile);
        } catch (FileNotFoundException e) {
            fail("language file not found. Terminating tests.");
        }
        File lmTestFile = new File("LMTest.txt");
        try {
            languageManagerTest = new LanguageManager(lmTestFile);
        } catch (FileNotFoundException e) {
            fail("language file not found. Terminating tests.");
        }
    }

    @Test
    public void languageManagerMethods () {
        assertEquals(27, languageManager.getLanguageCount());

        Language c = new Language("C", "c.txt", Language.Type.COMPILED);
        assertEquals(c.toString(), languageManager.getLanguage(5).toString());
        assertEquals(2, languageManager.findShortestKwdLength());
        assertEquals(25, languageManager.findLongestKwdLength());
        assertEquals(7, languageManager.findLangWithMostKwds());
        assertEquals(3, languageManager.findLangWithFewestKwds());

    }

    @Test
    public void emptyKeywords() {
        assertEquals(-1, languageManagerTest.findShortestKwdLength());
        assertEquals(-1, languageManagerTest.findLongestKwdLength());
        assertEquals(-1, languageManagerTest.findLangWithMostKwds());
        assertEquals(-1, languageManagerTest.findLangWithFewestKwds());
    }

    @Test
    public void testFindArrayOfMatchesMethods() {
        int[] ifMatches = {3, 5, 6, 7, 10, 12, 18, 20};
        assertArrayEquals(ifMatches, languageManager.findLangKwdMatches("if"));
        int[] noMatches = {};
        assertArrayEquals(noMatches, languageManager.findLangKwdMatches("spaghetti"));
        int[] compiledMatches = {0,5,6,7,8,10,12,23,25};
        assertArrayEquals(compiledMatches, languageManager.findLangsOfType(Language.Type.COMPILED));
    }

    @Test
    public void testFileExceptions() {
        assertThrows(IllegalArgumentException.class, () ->
                new LanguageManager(null));
        File noFile = new File("noFile.txt");
        assertThrows(IllegalArgumentException.class, () ->
                new LanguageManager(noFile));
        File emptyFile = new File("emptyFile.txt");
        assertThrows(IllegalArgumentException.class, () ->
                new LanguageManager(emptyFile));


    }
}