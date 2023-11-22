import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Language python = new Language("python", "python.txt", Language.Type.INTERPRETED);
        System.out.println(python);
        System.out.println(python.getKwd(29));
        File languageFile = new File("languages.txt");
        LanguageManager languageManager;
        try {
            languageManager = new LanguageManager(languageFile);
        } catch (FileNotFoundException e) {
            return;
        }
        System.out.println(languageManager.getLanguageCount());
        python.findLongestKwdLength();
        python.findShortestKwdLength();
        System.out.println(languageManager.findShortestKwdLength());
        System.out.println(languageManager.findLongestKwdLength());
        System.out.println(languageManager.getLanguage(3));
        System.out.println(languageManager.findLangWithFewestKwds());
        System.out.println(languageManager.findLangWithMostKwds());
        languageManager.findLangKwdMatches("if");

    }
}
