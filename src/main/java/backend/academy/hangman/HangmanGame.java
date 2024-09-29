package backend.academy.hangman;


public class HangmanGame {
    public static final int MAX_ATTEMPTS = 6;
    private static final String HINT_PROMPT = "Hint: ";

    private final WordRepository wordRepository;
    private final GameInputOutput io;
    private boolean hintUsed = false;

    public HangmanGame(GameInputOutput io, WordRepository wordRepository) {
        this.io = io;
        this.wordRepository = wordRepository;
    }

    public void start() {
        String selectedCategory = io.selectCategory(wordRepository.getCategories());
        DifficultyLevel selectedDifficulty = io.selectDifficulty();

        Word word = wordRepository.getWord(selectedCategory, selectedDifficulty);
        int wrongGuesses = 0;
        io.displayMessage("You have 6 attempts! The game has begun!");

        while (wrongGuesses < MAX_ATTEMPTS && !word.isWordGuessed()) {
            io.displayHangman(wrongGuesses);
            io.displayMessage(String.format(
                "Current state of the word: %s\nYou have %d incorrect attempts left.",
                word.getCurrentState(),
                MAX_ATTEMPTS - wrongGuesses
            ));


            if (hintUsed) {
                io.displayMessage(HINT_PROMPT + word.getHint() + "\nEnter letter: ");
            } else {
                io.displayMessage("Enter a letter or press Enter to see a hint: ");
            }

            String guess = io.getUserInput();

            if (guess == null || guess.isEmpty()) {
                io.displayMessage(HINT_PROMPT + word.getHint());
                hintUsed = true;
                continue;
            } else if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                io.displayMessage("Incorrect input! Enter one letter.");
                continue;
            } else if (!(guess.charAt(0) >= 'a' && guess.charAt(0) <= 'z')) {
                io.displayMessage("Enter one English letter.");
                continue;
            }

            char letter = guess.charAt(0);
            if (!word.guessLetter(letter)) {
                wrongGuesses++;
            }
        }

        if (word.isWordGuessed()) {
            io.displayMessage("Congratulations! You guessed the word: " + word.getWord());
        } else {
            io.displayMessage("You lost! The word you were trying to guess was: " + word.getWord());
        }
    }
}
