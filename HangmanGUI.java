import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanGUI extends JFrame {
    private final HangmanGame game;
    private final Database db;
    private final Stats stats;

    private final JLabel wordLabel;
    private final JTextField guessField;
    private final JLabel attemptsLabel;
    private final JLabel messageLabel;
    private final JComboBox<String> difficultyComboBox;

    public HangmanGUI() {
        db = new Database();
        stats = new Stats();
        game = new HangmanGame(db);

        setTitle("Gra w Wisielca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        wordLabel = new JLabel();
        add(wordLabel);

        guessField = new JTextField(5);
        add(guessField);

        JButton guessButton = new JButton("Zgadnij");
        guessButton.addActionListener(new GuessButtonListener());
        add(guessButton);

        attemptsLabel = new JLabel();
        add(attemptsLabel);

        messageLabel = new JLabel();
        add(messageLabel);

        String[] difficulties = {"łatwy", "średni", "trudny"};
        difficultyComboBox = new JComboBox<>(difficulties);
        add(difficultyComboBox);

        JButton newGameButton = new JButton("Nowa Gra");
        newGameButton.addActionListener(new NewGameButtonListener());
        add(newGameButton);

        JButton addWordButton = new JButton("Dodaj Słowo");
        addWordButton.addActionListener(new AddWordButtonListener());
        add(addWordButton);

        JButton statsButton = new JButton("Statystyki");
        statsButton.addActionListener(new StatsButtonListener());
        add(statsButton);

        newGame();
    }

    private void newGame() {
        game.newGame((String) difficultyComboBox.getSelectedItem());
        updateDisplay();
    }

    private void updateDisplay() {
        wordLabel.setText(game.getGuessedWord());
        attemptsLabel.setText("Próby: " + game.getAttempts() + "/" + game.getMaxAttempts());
        messageLabel.setText("");
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String guess = guessField.getText().toLowerCase();
            if (guess.length() != 1) {
                messageLabel.setText("Wprowadź jedną literę.");
                return;
            }
            boolean correct = game.guessLetter(guess.charAt(0));
            if (game.isGameOver()) {
                if (game.isWin()) {
                    messageLabel.setText("Wygrałeś!");
                    stats.incrementWins();
                } else {
                    messageLabel.setText("Przegrałeś! Słowo to: " + game.getCurrentWord());
                    stats.incrementLosses();
                }
                if (game.hasReachedMaxAttempts()) {
                    JOptionPane.showMessageDialog(null, "Przegrałeś po 10 próbach!");
                    newGame();
                }
            } else if (correct) {
                messageLabel.setText("Dobra litera!");
            } else {
                messageLabel.setText("Zła litera!");
            }
            updateDisplay();
            guessField.setText("");
        }
    }

    private class NewGameButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newGame();
        }
    }

    private class AddWordButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newWord = JOptionPane.showInputDialog("Wprowadź nowe słowo:");
            if (newWord != null && !newWord.trim().isEmpty()) {
                db.addWord(newWord.trim().toLowerCase());
                JOptionPane.showMessageDialog(null, "Słowo dodane!");
            }
        }
    }

    private class StatsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Wygrane: " + stats.getWins() + "\nPrzegrane: " + stats.getLosses());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HangmanGUI().setVisible(true);
        });
    }


}
