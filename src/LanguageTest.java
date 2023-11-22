import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LanguageTest {

    @Test
    @DisplayName("Check the basic components of a language object are correctly assigned")
    public void languageTestProperties() {
        Language python = new Language("python", "python.txt", Language.Type.INTERPRETED);
        assertEquals("python", python.getName());
        assertEquals("python.txt", python.getFileName());
        assertEquals(Language.Type.INTERPRETED, python.getType());
        assertFalse(python.toString().startsWith("Language@"));
        assertFalse(python.toString().isBlank());
    }

    @Test
    @DisplayName("Check that keywords are being read into arrays correctly")
    public void languageTestKeywords() {
        Language c = new Language("c", "c.txt", Language.Type.COMPILED);
        assertEquals(33, c.getKwdCount());
        assertEquals("while", c.getKwd(32));
        assertEquals("_Packed", c.getKwd(0));
        assertEquals(32, c.findKwd("while"));
        Language noKwds = new Language("noKwds", "notHere.txt", LanguageInterface.Type.INTERPRETED);
        assertEquals(0, noKwds.getKwdCount());
        assertEquals(-1, c.findKwd("spaghetti"));
    }

    @Test
    @DisplayName("Tests Language class methods to find the longest and shortest length keywords")
    public void languageTestLengthMethods() {
        Language cplusplus = new Language("C++", "cplusplus.txt", Language.Type.COMPILED);
        assertEquals(25, cplusplus.findLongestKwdLength());
        assertEquals(2, cplusplus.findShortestKwdLength());
        Language noKwds = new Language("noKwds", "notHere.txt", LanguageInterface.Type.INTERPRETED);
        assertEquals(-1, noKwds.findLongestKwdLength());
        assertEquals(-1, noKwds.findShortestKwdLength());
    }

    @Test
    @DisplayName("Check preconditions for values that cannot be null or blank.")
    public void languageTestExceptions() {
        assertThrows(IllegalArgumentException.class, () ->
            new Language(null, "python.txt", Language.Type.INTERPRETED));
        assertThrows(IllegalArgumentException.class, () ->
                new Language("", "python.txt", Language.Type.INTERPRETED));
        assertThrows(IllegalArgumentException.class, () ->
                new Language("  ", "python.txt", Language.Type.INTERPRETED));
        assertThrows(IllegalArgumentException.class, () ->
                new Language("python", null, Language.Type.INTERPRETED));
        assertThrows(IllegalArgumentException.class, () ->
                new Language("python", "", Language.Type.INTERPRETED));
        assertThrows(IllegalArgumentException.class, () ->
                new Language("python", "    ", Language.Type.INTERPRETED));
        assertThrows(IllegalArgumentException.class, () ->
                new Language("python", "python.txt", null));

    }

}