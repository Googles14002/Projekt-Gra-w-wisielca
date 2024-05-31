import java.util.List;
import java.util.Random;

public class HangmanGame {
    private String currentWord;
    private final List<String> wordList;
    private StringBuilder guessedWord;
    private int maxAttempts;
    private int attempts;

    public HangmanGame(Database db) {
        this.wordList = db.getWords();
        this.maxAttempts = 6;
    }

    public void newGame(String difficulty) {
        Random rand = new Random();
        currentWord = wordList.get(rand.nextInt(wordList.size()));
        guessedWord = new StringBuilder("_".repeat(currentWord.length()));
        attempts = 0;
        setDifficulty(difficulty);
    }

    private void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "łatwy":
                maxAttempts = 10;
                break;
            case "trudny":
                maxAttempts = 4;
                break;
            case "średni":
            default:
                maxAttempts = 6;
                break;
        }
    }

    public boolean guessLetter(char letter) {
        boolean found = false;
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == letter) {
                guessedWord.setCharAt(i, letter);
                found = true;
            }
        }
        if (!found) {
            attempts++;
        }
        return found;
    }

    public boolean isGameOver() {
        return attempts >= maxAttempts || guessedWord.toString().equals(currentWord);
    }

    public boolean isWin() {
        return guessedWord.toString().equals(currentWord);
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public String getGuessedWord() {
        return guessedWord.toString();
    }

    public int getAttempts() {
        return attempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public boolean hasReachedMaxAttempts() {
        return attempts >= 10;
    }
}
