package backend.academy.hangman;

import java.io.PrintStream;

public class HangmanDisplay { //отвечает за отображение состояния виселицы и оставшихся попыток
    private final PrintStream output;

    public HangmanDisplay(PrintStream output) {
        this.output = output;
    }

    public void displayHangman(int wrongGuesses) {
        String[] hangmanStages = {
            """
               -----
               |   |
                   |
                   |
                   |
                   |
                   |
            =========""",
            """
               -----
               |   |
               O   |
                   |
                   |
                   |
                   |
            =========""",
            """
               -----
               |   |
               O   |
               |   |
                   |
                   |
                   |
            =========""",
            """
               -----
               |   |
               O   |
              /|   |
                   |
                   |
                   |
            =========""",
            """
               -----
               |   |
               O   |
              /|\\  |
                   |
                   |
                   |
            =========""",
            """
               -----
               |   |
               O   |
              /|\\  |
              /    |
                   |
                   |
            =========""",
            """
               -----
               |   |
               O   |
              /|\\  |
              / \\  |
                   |
                   |
            ========="""
        };
        output.println(hangmanStages[wrongGuesses]);
    }
}
