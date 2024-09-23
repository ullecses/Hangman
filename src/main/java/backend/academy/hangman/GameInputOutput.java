package backend.academy.hangman;

public interface GameInputOutput {
    void displayMessage(String message);

    void displayHangman(int wrongGuesses);

    String getUserInput();

    String selectCategory(String[] categories);

    DifficultyLevel selectDifficulty();
}
