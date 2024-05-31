import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String WORDS_FILE = "words.txt";
    private final List<String> words;

    public Database() {
        words = new ArrayList<>();
        loadWords();
    }

    private void loadWords() {
        try (BufferedReader br = new BufferedReader(new FileReader(WORDS_FILE))) {
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
            words.add("java");
            words.add("programming");
            words.add("hangman");
            words.add("developer");
            words.add("algorithm");
            words.add("database");
            words.add("interface");
            words.add("statistics");
            words.add("difficulty");
            words.add("challenge");
            saveWords();
        }
    }

    public void saveWords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(WORDS_FILE))) {
            for (String word : words) {
                bw.write(word);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        words.add(word);
        saveWords();
    }
}
